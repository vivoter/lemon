package com.lemon.bean;

import android.content.Context;

import com.lemon.annotation.Autowired;
import com.lemon.annotation.Component;
import com.lemon.annotation.InitMethod;
import com.lemon.annotation.RefBean;
import com.lemon.util.Inflector;
import com.lemon.util.LogUtils;
import com.lemon.util.PackageLoader;
import com.lemon.util.ParamUtils;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * 项目名称:  [Lemon]
 * 包:        [com.lemon.bean]
 * 类描述:    [简要描述]
 * 创建人:    [xflu]
 * 创建时间:  [2015/12/16 16:07]
 * 修改人:    [xflu]
 * 修改时间:  [2015/12/16 16:07]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public class BeanFactory {
    private volatile static BeanFactory beanFactory;
    private volatile Map<String, Object> beanMap = new HashMap<String, Object>();
    private volatile Map<String, String> initMethodMap = new HashMap<>();
    private static final String xml_bean_config_path = "config/bean.xml";
    private static final String lib_path = "config/lemon.cls";
    private static final String app_path = "config/app.cls";
    private Context mContext;

    private BeanFactory() {
    }

    public static BeanFactory getInstance() {
        synchronized (BeanFactory.class) {
            if (ParamUtils.isNull(beanFactory)) {
                beanFactory = new BeanFactory();
            }
        }
        return beanFactory;
    }

    public void parser() {
        try {
            parserXml();
            parserAnnotation();
        } catch (Exception e) {
            LogUtils.e("BeanFactory parser :" + e.getMessage());
        }
    }

    private void parserXml() throws IOException, SAXException, ParserConfigurationException, IllegalAccessException, InstantiationException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputStream is = mContext.getAssets().open(xml_bean_config_path);
        if (ParamUtils.isNull(is)) {
            LogUtils.e("BeanFactory parserXml can not find config bean file,path:" + xml_bean_config_path);
            return;
        }
        Document document = builder.parse(is);
        Element root = document.getDocumentElement();
        NodeList beanNodes = root.getElementsByTagName("bean");
        handleXmlClass(beanNodes);
        handleXmlProperty(beanNodes);
    }

    private void parserAnnotation() throws InstantiationException, IllegalAccessException {
        PackageLoader packageLoader = getBean("packageLoader");
        List<Class> classes = packageLoader.autoLoadClass();
        handleAnnotationClass(classes);
        handleAnnotationProperty(classes);
    }

    private void handleXmlClass(NodeList beanNodes) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        for (int i = 0; i < beanNodes.getLength(); i++) {//bean
            Element beanElement = (Element) beanNodes.item(i);
            String name = beanElement.getAttribute("name");
            String className = beanElement.getAttribute("class");
            Class cls = Class.forName(className);

            Object clsBean = null;
            //support constructor instance
            NodeList childNodes = beanElement.getChildNodes();//property
            for (int j = 0; j < childNodes.getLength(); j++) {
                if (childNodes.item(j) instanceof Element) {
                    Element constructorElement = (Element) childNodes.item(j);
                    if(constructorElement.getNodeName().equals("constructor")){
                        NodeList argList = constructorElement.getChildNodes();
                        List<Class> paramTypes = new ArrayList<>();
                        List<Object> params = new ArrayList<>();
                        for (int k =0;k<argList.getLength();k++){
                            if (argList.item(k) instanceof Element) {
                                Element argElement = (Element) argList.item(k);
                                String type = argElement.getAttribute("type");
                                String value = argElement.getAttribute("value");
                                String ref = argElement.getAttribute("ref");
                                paramTypes.add(Class.forName(type));
                                if(ParamUtils.isEmpty(ref)|| !ref.equals("true")){
                                    params.add(value);
                                }else {
                                    if (!ParamUtils.isEmpty(value)) {
                                        if (!beanMap.containsKey(value)) {
                                            LogUtils.e("BeanFactory handleXmlConstructor beanName:" + value + "   can not find");
                                        }
                                        params.add(beanMap.get(value));
                                    } else {
                                        LogUtils.e("BeanFactory handleXmlConstructor beanName Empty, not config");
                                    }
                                }
                            }
                        }


                        Class<?>[] paramTypeArray =  (Class<?>[])paramTypes.toArray(new Class[paramTypes.size()]);
                        Object[] paramArray =  (Object[])params.toArray(new Object[params.size()]);
                        clsBean = cls.getConstructor(paramTypeArray).newInstance(paramArray);
                    }
                }
            }

            beanMap.put(name, ParamUtils.isNull(clsBean)?cls.newInstance():clsBean);
            //get init-method
            String initMethod = beanElement.getAttribute("init-method");
            if (!ParamUtils.isEmpty(initMethod)) {
                initMethodMap.put(name, initMethod);
            }
        }
    }

    private void handleXmlProperty(NodeList beanNodes) throws IllegalAccessException, ClassNotFoundException {
        for (int i = 0; i < beanNodes.getLength(); i++) {//bean
            Element beanElement = (Element) beanNodes.item(i);
            String name = beanElement.getAttribute("name");
            String className = beanElement.getAttribute("class");
            Class cls = Class.forName(className);
            Object target = beanMap.get(name);
            if (ParamUtils.isEmpty(name) || ParamUtils.isNull(target)) {
                continue;
            }

            NodeList propertyNodes = beanElement.getChildNodes();//property
            for (int j = 0; j < propertyNodes.getLength(); j++) {
                if (propertyNodes.item(j) instanceof Element) {
                    if(propertyNodes.item(j).getNodeName().equals("constructor")){//no handle constructor
                        continue;
                    }
                    Element propertyElement = (Element) propertyNodes.item(j);
                    String fieldName = propertyElement.getAttribute("name");
                    String value = propertyElement.getAttribute("value");
                    String refBeanValue = propertyElement.getAttribute("value-ref");
                    //list or map or basic
                    if (propertyElement.getChildNodes().getLength() == 0) {
                        //basic type , normal set
                        Object fieldValue = null;
                        if (!ParamUtils.isEmpty(refBeanValue)) {
                            if (!beanMap.containsKey(refBeanValue)) {
                                LogUtils.e("BeanFactory handleXmlProperty beanName:" + name + " ,fieldName:" + fieldName + ",propertyName:" + refBeanValue + "   can not find");
                            }
                            fieldValue = beanMap.get(refBeanValue);
                        } else {
                            fieldValue = value;
                        }
                        FieldUtils.writeField(target, fieldName, fieldValue);
                    } else {//property config more than one child , only choice first
                        NodeList propertyChildrenNodes = propertyElement.getChildNodes();
                        for (int m = 0; m < propertyChildrenNodes.getLength(); m++) {
                            if (!(propertyChildrenNodes.item(m) instanceof Element)) {
                                continue;
                            }
                            String typeNodeName = propertyChildrenNodes.item(m).getNodeName();
                            if (typeNodeName.equalsIgnoreCase("list")) {
                                NodeList refNodeList = propertyChildrenNodes.item(m).getChildNodes();
                                for (int k = 0; k < refNodeList.getLength(); k++) {
                                    if (!(refNodeList.item(k) instanceof Element)) {
                                        continue;
                                    }
                                    Element refNode = (Element) refNodeList.item(k);
                                    String entityType = refNode.getAttribute("type");
                                    String entityValue = refNode.getAttribute("value");
                                    Object resObject = entityType.equals("ref")? beanMap.get(entityValue):entityValue;

                                    if (ParamUtils.isNull(resObject)) {
                                        LogUtils.e("BeanFactory handleXmlProperty cannot find entityValue:" + entityValue);
                                        continue;
                                    }
                                    //judge the field is null ? create it   //judge field
                                    if (ParamUtils.isNull(FieldUtils.readField(target, fieldName))) {
                                        FieldUtils.writeField(target, fieldName, new ArrayList());
                                    }

                                    //list add //get the field and convert
                                    Field propertyField = FieldUtils.getField(cls, fieldName);
                                    if (propertyField.get(target) instanceof ArrayList) {
                                        ((ArrayList) propertyField.get(target)).add(resObject);
                                    }
                                }
                            } else if (typeNodeName.equalsIgnoreCase("map")) {
                                NodeList entryNodeList = propertyChildrenNodes.item(m).getChildNodes();
                                for (int k = 0; k < entryNodeList.getLength(); k++) {
                                    if (!(entryNodeList.item(k) instanceof Element)) {
                                        continue;
                                    }
                                    Element refEntryNode = (Element) entryNodeList.item(k);
                                    String refBeanName = refEntryNode.getAttribute("value-ref");
                                    String refMapkey = refEntryNode.getAttribute("key");
                                    Object refBean = beanMap.get(refBeanName);
                                    if (ParamUtils.isNull(refBean)) {
                                        LogUtils.e("BeanFactory handleXmlProperty cannot find refBeanName:" + refBeanName);
                                        continue;
                                    }
                                    //judge the field is null ? create it   //judge field
                                    if (ParamUtils.isNull(FieldUtils.readField(target, fieldName))) {
                                        FieldUtils.writeField(target, fieldName, new HashMap());
                                    }

                                    //map put //get the field and convert
                                    Field propertyField = FieldUtils.getField(cls, fieldName);
                                    if (propertyField.get(target) instanceof HashMap) {
                                        ((HashMap) propertyField.get(target)).put(refMapkey, refBean);
                                    }
                                }
                            } else {
                                //support after
                            }
                        }
                    }
                }
            }
        }
    }

    private void handleAnnotationClass(List<Class> classes) throws IllegalAccessException, InstantiationException {
        for (Class cls : classes) {
            Component component = (Component) cls.getAnnotation(Component.class);
            if (ParamUtils.isNull(component)) {
                continue;
            }

            String key = component.name();
            if (ParamUtils.isEmpty(key)) {//不填写name , 则默认name=类名,首字母小写,如:BeanFactory->beanFactory
                key = Inflector.getInstance().camelCase(cls.getSimpleName(), false);
            }
            if (beanMap.containsKey(key)) {
                //throw exception , name redisplay
                LogUtils.e("key : " + cls.getName() + " - " + key + " ,   has bean exist , please check");
                continue;
            }
            beanMap.put(key, cls.newInstance());

            //get init-method with
            Method[] methods = cls.getMethods();
            if (ParamUtils.isNull(methods) || methods.length == 0) {
                continue;
            }
            for (Method method : methods) {
                if (!ParamUtils.isNull(method.getAnnotation(InitMethod.class))) {
                    initMethodMap.put(key, method.getName());
                    break;
                }
            }
        }
    }

    private void handleAnnotationProperty(List<Class> classes) throws IllegalAccessException {
        for (Class cls : classes) {
            String key = Inflector.getInstance().camelCase(cls.getSimpleName(), false);
            Component component = (Component) cls.getAnnotation(Component.class);
            if (ParamUtils.isNull(component) && !beanMap.containsKey(key)) {
                continue;
            }

            key = (ParamUtils.isNull(component)||ParamUtils.isEmpty(component.name())) ? key : component.name();
            if (!beanMap.containsKey(key)) {
                LogUtils.e("cann`t find key : " + cls.getName() + " - " + key + " , please check error ");
                continue;
            }

            Object target = beanMap.get(key);
            Field[] fields = cls.getFields();
            for (Field field : fields) {
                Autowired autowired = field.getAnnotation(Autowired.class);
                RefBean refBean = field.getAnnotation(RefBean.class);
                if (ParamUtils.isNull(autowired) && ParamUtils.isNull(refBean)) {
                    continue;
                }

                //refbean > autowired  priority
                field.setAccessible(true);
                String valueKey = !ParamUtils.isNull(refBean) ? refBean.name() : field.getName();
                Object fieldValue = beanMap.get(valueKey);
                if (ParamUtils.isNull(fieldValue)) {
                    LogUtils.e("cann`t find the valueKey " + valueKey + " in map , please check config");
                    continue;
                }

                FieldUtils.writeField(target, field.getName(), fieldValue);
            }
        }

    }

    /**
     * 获取
     *
     * @param beanName
     * @param <T>
     * @return
     */
    public <T> T getBean(String beanName) {
        if (!beanMap.containsKey(beanName)) {
            LogUtils.e("can not find bean,name:" + beanName);
            return null;
        }
        return (T) beanMap.get(beanName);
    }

    public <T> T getBean(Class<T> cls){
        String beanName = Inflector.getInstance().camelCase(cls.getSimpleName(),false);
        if (!beanMap.containsKey(beanName)) {
            LogUtils.e("can not find bean,name:" + beanName);
            return null;
        }
        return (T) beanMap.get(beanName);
    }

    public boolean containsBean(String beanName) {
        return beanMap.containsKey(beanName);
    }

    /**
     * 设置Context
     *
     * @param mContext
     */
    public void setContext(Context mContext) {
        this.mContext = mContext;
        beanMap.put("mContext", mContext);
    }

    public Map<String, Object> getBeanMap() {
        return beanMap;
    }

    public Map<String, String> getInitMethodMap() {
        return initMethodMap;
    }

    public void putBean(String key,Object obj){
        beanMap.put(key,obj);
    }

    public void putBean(Class cls,Object obj){
        String beanName = Inflector.getInstance().camelCase(cls.getSimpleName(),false);
        putBean(beanName,obj);
    }

}
