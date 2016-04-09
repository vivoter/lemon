package com.lemon.net;

import android.content.Context;

import com.lemon.annotation.Module;
import com.lemon.annotation.ParamType;
import com.lemon.annotation.ReturnType;
import com.lemon.bean.BeanFactory;
import com.lemon.config.Config;
import com.lemon.converter.BaseParamConverter;
import com.lemon.converter.BaseResultConverter;
import com.lemon.event.NetNotAvailableEvent;
import com.lemon.event.NetStartEvent;
import com.lemon.event.NetStopEvent;
import com.lemon.event.ParamErrorEvent;
import com.lemon.event.ServerErrorEvent;
import com.lemon.exception.AppException;
import com.lemon.model.BaseParam;
import com.lemon.model.BaseResult;
import com.lemon.model.StatusCode;
import com.lemon.util.Inflector;
import com.lemon.util.LogUtils;
import com.lemon.util.MethodUtil;
import com.lemon.util.NetUtil;
import com.lemon.util.ParamUtils;
import com.lemon.util.StringUtils;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * 项目名称:  [Lemon]
 * 包:        [com.lemon.net]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2015/12/16 14:07]
 * 修改人:    [xflu]
 * 修改时间:  [2015/12/16 14:07]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class NetEngine {
    protected static EventBus eventBus = EventBus.getDefault();
    public Context mContext;
    private static NetEngine netEngine;
    public Map<String,BaseParamConverter> paramConverterMap;
    public Map<String,BaseResultConverter> resultConverterMap;

    public static NetEngine getInstance() {
        if (ParamUtils.isNull(netEngine)) {
            netEngine = new NetEngine();
        }
        return netEngine;
    }

    /**
     * 执行请求(线程中执行)
     * @param param
     */
    public void invoke(BaseParam param) {

        String methodName = MethodUtil.getCurrentMethodName();
        String className = MethodUtil.getCurrentClassName();
        className = Inflector.getInstance().camelCase(className,false);
        Method method = MethodUtil.getMethod(BeanFactory.getInstance().getBean(className).getClass(), methodName, BaseParam.class);
        ReturnType returnType = method.getAnnotation(ReturnType.class);
        ParamType paramType = method.getAnnotation(ParamType.class);
        if (!handleBefore(param)) {
            return;
        }

        String website,params,strResult="";

        //通过参数,获取参数对应注解上的模块名称
        Module paramModule = param.getClass().getAnnotation(Module.class);
        if(ParamUtils.isNull(paramModule)){
            throw new AppException("not config "+param.getClass().getName()+" Annotation Module");
        }
        String keyServer = paramModule.server();
        String module = paramModule.name();
        String strHttpMethod =paramModule.httpMethod();
        String serverPath = Config.getValue(keyServer);
        if(ParamUtils.isEmpty(serverPath)){
            throw new AppException("NetEngine invoke serverPath error,param:"+param.getClass().getName()+",server:"+keyServer+",can not find in config.json");
        }
        if(ParamUtils.isEmpty(module)){
            throw new AppException("NetEngine invoke serverPath error,param:"+param.getClass().getName()+",module is empty");
        }

        if(ParamUtils.isNull(paramType.value())){
            throw new AppException("NetEngine invoke paramType error,className:"+className+",param:"+param.getClass().getName()+",methodName:"+methodName);
        }
        if(ParamUtils.isNull(returnType.value())){
            throw new AppException("NetEngine invoke returnType error,className:"+className+",param:"+param.getClass().getName()+",methodName:"+methodName);
        }

        website = serverPath+"/"+module+"/"+methodName;
        params = getParamConverter(param.getClass().getSimpleName()).convert(param);

        LemonThread lemonThread = new LemonThread(website,params,strHttpMethod,returnType.value());
        lemonThread.start();
    }

    /**
     * 访问网络地址,获取请求数据,GET
     *
     * @param website
     * @return
     */
    public String invokeNetGet(String website,String params) {
        try {
            website += "?"+params;
            LogUtils.d(getClass().getSimpleName(), "invokeNetGet: " + website);
            HttpURLConnection conn = (HttpURLConnection) new URL(website).openConnection();
            conn.setRequestProperty("Accept", "application/json");
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Charset", "utf-8");
            if (200 == conn.getResponseCode()) {//200
                InputStream inStream1 = conn.getInputStream();
                String jsonResult = StringUtils.inputStream2String(inStream1);
                LogUtils.d(getClass().getSimpleName(), "invokeNetGet Result: " + jsonResult);
                return jsonResult;
            }
        } catch (Exception e) {
            e.printStackTrace();
            String msg = "与服务器交互错误,请稍后重试";
            EventBus.getDefault().post(new ServerErrorEvent(msg));
        }
        return "";
    }

    /**
     * 访问网络地址,获取请求数据,GET
     *
     * @param website
     * @return
     */
    public String invokeNetPost(String website, String params) {
        try {
            LogUtils.d(getClass().getSimpleName(), "invokeNetPost: " + website);
            LogUtils.d(getClass().getSimpleName(), "invokeNetPost params: " + params);
            HttpURLConnection conn = (HttpURLConnection) new URL(website).openConnection();
            conn.setRequestProperty("Accept", "application/json");
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Charset", "utf-8");
            byte[] bypes = params.toString().getBytes("utf-8");
            conn.setDoOutput(true);
            conn.getOutputStream().write(bypes);
            if (200 == conn.getResponseCode()) {//200
                InputStream inStream1 = conn.getInputStream();
                String jsonResult = StringUtils.inputStream2String(inStream1);
                LogUtils.d(getClass().getSimpleName(), "invokeNetPost Result: " + jsonResult);
                return jsonResult;
            }
        } catch (Exception e) {
            e.printStackTrace();
            String msg = "与服务器交互错误,请稍后重试";
            EventBus.getDefault().post(new ServerErrorEvent(msg));
        }
        return "";
    }


    /**
     * 获取参数转换器
     * @return
     */
    private BaseParamConverter getParamConverter(String type) {
        String name = Inflector.getInstance().camelCase(type,false)+ Config.getConvertExt();
        return paramConverterMap.containsKey(name)?paramConverterMap.get(type):paramConverterMap.get("baseParamConverter");
    }

    /**
     * 获取结果转换器
     * @return
     */
    private BaseResultConverter getResultConverter(String type) {
        String name = Inflector.getInstance().camelCase(type,false)+ Config.getConvertExt();
        return resultConverterMap.containsKey(name)?resultConverterMap.get(type):resultConverterMap.get("baseResultParamConverter");
    }

    /**
     * 前置处理
     * @param param
     * @return
     */
    protected boolean handleBefore(BaseParam param) {
        if (ParamUtils.isNull(param)) {
            eventBus.post(new ParamErrorEvent());
            return false;
        }
        if (!NetUtil.isNetworkAvailable(mContext)) {
            eventBus.post(new NetNotAvailableEvent());
            return false;
        }
        if(param.getShowDialog()){
            eventBus.post(new NetStartEvent());
        }
        return true;
    }

    /**
     * 返回数据处理
     * @param result
     * @return
     */
    private boolean handleAfter(BaseResult result) {
        eventBus.post(new NetStopEvent());
        if (ParamUtils.isNull(result)) {
            eventBus.post(new ServerErrorEvent("Can not get result from server"));
            return false;
        }
        if(result.getRetCode().equals(StatusCode.SERVER_ERROR.getCode())){
            eventBus.post(new ServerErrorEvent("Can not get result from server"));
            return true;
        }
        eventBus.post(result);//send result to bus
        return true;
    }

    public class LemonThread extends Thread{
        Class  returnType;
        String website, params, httpMethod,strResult;
        public LemonThread(String website,String params,String httpMethod,Class returnType){
            this.httpMethod = httpMethod;
            this.params = params;
            this.website = website;
            this.returnType = returnType;
        }

        @Override
        public void run() {
            if (httpMethod.toLowerCase().equals("post")) {
                strResult = invokeNetPost(website, params);
            } else {
                strResult = invokeNetGet(website,params);
            }
            //handlerAfter
            if(ParamUtils.isEmpty(strResult)){
                eventBus.post(new ServerErrorEvent("Can not get result from server"));
                return;
            }
            Object baseResult = getResultConverter(returnType.getSimpleName()).convert(strResult,returnType);
            handleAfter((BaseResult)baseResult);
        }
    }
}
