package com.lemon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.lemon.annotation.FieldView;
import com.lemon.annotation.Layout;
import com.lemon.annotation.OnClick;
import com.lemon.event.ActivityEvent;
import com.lemon.event.StartLocationEvent;
import com.lemon.model.BaseResult;
import com.lemon.net.ApiManager;
import com.lemon.util.ParamUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import de.greenrobot.event.EventBus;

/**
 * 项目名称:  [Lemon]
 * 包:        [com.lemon.ui]
 * 类描述:    [类描述]
 * 创建人:    [XiaoFeng]
 * 创建时间:  [2015/12/21 20:34]
 * 修改人:    [XiaoFeng]
 * 修改时间:  [2015/12/21 20:34]
 * 修改备注:  [说明本次修改内容]
 * 版本:      [v1.0]
 */
public abstract class LemonActivity extends Activity {

    protected int layout;
    protected Context mContext;

    protected LemonCacheManager cacheManager;
    protected ApiManager apiManager;
    protected LemonDaoManager daoManager;
    protected LemonMessage lemonMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        injectLayout();
        setLayout();
        setContentView(layout);
        injectView();
        injectEvent();
        parentInit();
        initView();
        initData();
        init();
    }

    public <T> T findControl(int id) {
        return (T) findViewById(id);
    }

    /**
     * 设置对应的layout
     */
    protected void setLayout(){}

    private void parentInit() {
        mContext = this;
        EventBus.getDefault().register(this);
        EventBus.getDefault().post(new ActivityEvent(this));
        cacheManager = LemonContext.getBean(LemonCacheManager.class);
        apiManager = LemonContext.getBean(ApiManager.class);
        daoManager = LemonContext.getBean(LemonDaoManager.class);
        lemonMessage = LemonContext.getBean(LemonMessage.class);
    }

    private final void injectLayout() {
        //反射初始化布局
        Layout mLayout = getClass().getAnnotation(Layout.class);
        if (ParamUtils.isNull(mLayout)) {
            return;
        }

        layout = mLayout.id();
    }

    private final void injectView() {
        //反射初始化视图

        Field[] fields = getClass().getDeclaredFields();
        if(ParamUtils.isEmpty(fields)){
            return;
        }
        for (Field field : fields) {
            FieldView view = field.getAnnotation(FieldView.class);
            if(ParamUtils.isNull(view)){
                return;
            }

            try {
                field.setAccessible(true);
                field.set(this, findViewById(view.id()));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private final void injectEvent() {
        //反射初始化事件
        Method[] methods = getClass().getDeclaredMethods();
        if(ParamUtils.isEmpty(methods)){
            return;
        }


        for(final Method method:methods){
            OnClick onClick = method.getAnnotation(OnClick.class);
            if(ParamUtils.isNull(onClick)){
                continue;
            }

            View view = findViewById(onClick.id());
            if(ParamUtils.isNull(view)){
                continue;
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        method.invoke(mContext);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    protected void initView() {
    }

    protected void initData() {
    }

    protected void init() {
    }

    public void onEventMainThread(BaseResult event) {

    }

    public void backClick(View v) {
        finish();
    }

    protected void setLayoutValue(int layout) {
        this.layout = layout;
    }

    public void startNextActivity(Class<? extends Activity> nextActivity, boolean finishCurrent) {
        Intent intent = new Intent(this, nextActivity);
        startActivity(intent);
        if (finishCurrent) {
            finish();
        }
    }

    public void startNextActivity(Intent intent, boolean finishCurrent) {
        startActivity(intent);
        if (finishCurrent) {
            finish();
        }
    }

    protected String getIntentExtraStr(String name) {
        return getIntent().getStringExtra(name);
    }


    protected boolean getIntentExtraBoolean(String name) {
        return getIntent().getBooleanExtra(name, false);
    }

    protected int getIntentExtraInt(String name) {
        return getIntent().getIntExtra(name, -1);
    }


    protected String getEditTextValue(int id) {
        EditText tv = (EditText) findViewById(id);
        String value = tv.getText().toString();
        return value;
    }

    protected void setEditTextValue(int id, String text) {
        EditText tv = (EditText) findViewById(id);
        tv.setText(text);
    }

    protected void setTextViewValue(int id, String text) {
        TextView tv = (TextView) findViewById(id);
        tv.setText(text);
    }

    protected String getTextViewValue(int id) {
        TextView tv = (TextView) findViewById(id);
        String value = tv.getText().toString();
        return value;
    }

    protected boolean isEditTextEmpty(int id) {
        String value = getEditTextValue(id);
        return ParamUtils.isEmpty(value);
    }

    protected void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    protected void toast(int id) {
        Toast.makeText(this, mContext.getString(id), Toast.LENGTH_SHORT).show();
    }

    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            notificationMessage(msg);
        }
    };

    public void notificationMessage(Message msg) {

    }

    /**
     * 获取位置
     */
    protected void getLocation() {
        EventBus.getDefault().post(new StartLocationEvent());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
