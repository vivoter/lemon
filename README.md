# Lemon Android Framework

## 一、介绍
lemon初衷是让android开发更简单，核心实现了一个轻量级别的控制反转(IoC) ，通过配置XML和注解的方式使得代码之间**松耦合**，当应用了IoC，一个对象依赖的其它对象会通过被动的方式传递进来，而不是这个对象自己创建或者查找依赖对象。构建与IOC核心之上做了如下几个模块：<br>
1. 网络模块<br>
2. 数据库模块<br>
3. 配置模块<br>
4. 初始化模块<br>
5. 缓存模块<br>
6. Annotations模块<br>
7. 消息模块<br>
8. 更新模块<br>
(Spring Android Lemon)

## 二、框架图
<img src="http://images.cnblogs.com/cnblogs_com/luxiaofeng54/815456/o_lemon_framework.png" width="600" height="400"/>

## 三、脑图
<img src="http://images.cnblogs.com/cnblogs_com/luxiaofeng54/815456/o_lemon_naotu.png" width="600" height="400"/><br>
脑图地址: [戳这里](http://naotu.baidu.com/file/49cb90afc5275d88583dcff11996d8fe)

## 四、模块
### 4.1 IOC模块
此模块设计思路是加载xml文件和扫描类，根据xml配置和类注解，将类对象，属性，方法写入上下文缓存。关键类**BeanFactory**
#### 4.1.1 IOC模块 Xml 配置
Xml配置模块是类似于spring中xml配置方式，配置文件位置asserts/config/bean.xml。可支持如下配置:<br>
##### 4.1.1.1. 基础配置
\<bean name="config" class="com.lemon.config.Config"\>
\</bean\>
##### 4.1.1.2. 初始化方法 **init-method**
\<bean name="config" class="com.lemon.config.Config" **init-method="parser"**\>
\</bean\>
##### 4.1.1.3. 普通属性 **value**
\<bean name="config" class="com.lemon.config.Config"\>
    \<property name="configPath" value="config/config.json" /\>
\</bean\>
##### 4.1.1.4. 引用对象 **value-ref**
\<bean name="config" class="com.lemon.config.Config"\>
    \<property name="mContext" value-ref="mContext" /\>
\</bean\>
##### 4.1.1.5. list配置 **list**
\<bean name="config" class="com.lemon.config.Config"\>
    \<property name="paths"\>
        \<list\>
            \<entity type="basic" value="config/config.json"/\>
            \<entity type="basic" value="config/config.json"/\>
            \<entity type="basic" value="config/config.json"/\>
        \</list\>
    \</property\>
\</bean\>
##### 4.1.1.6. map配置
\<bean name="config" class="com.lemon.config.Config"\>
    \<property name="converts"\>
        \<map\>
            \<entry key="baseParamConverter" value-ref="baseParamConverter"/\>
            \<entry key="baseResultParamConverter" value-ref="baseResultParamConverter"/\>
        \</map\>
    \</property\>
\</bean\>

### 4.1.2 IOC模块 Annotation 配置
#### 4.1.2.1 Component
在类上添加Component注解，相当于配置文件中的<bean id="" class=""/>。Component 有个name属性，配置name属性表示在BeanFactory cacheMap里面存此对象的key是配置的name，若默认不配置则使用该类的首字母小写为关键字<br>
@Component
public class Demo1Model {}

#### 4.1.2.2 Autowired
使用在属性上，自动装配属性，默认使用属性的名字<br>
@Autowired
public Context mContext;

#### 4.1.2.3 RefBean
使用在属性上，有个name属性，根据名称装配<br>
@RefBean(name = "lemonMessage")
public LemonMessage lemonMessage;

#### 4.1.2.4 InitMethod
使用在方法上，配置了InitMethod方法，在app启动的时候会被执行<br>
@InitMethod
public void init() {
    name = "demo1  model";
}

### 4.2 网络交互模块


### 4.3 数据库模块

### 4.4 初始化模块

### 4.5 缓存模块
建议能用缓存存储的尽量不存数据库<br>
Activity传递数据也可以通过共享缓存传递<br>
缓存可以直接通过class存,或者取<br>
put:LemonContext.getBean(LemonCacheManager.class).putBean(CarModel.class,new CarModel());<br>
get:LemonContext.getBean(LemonCacheManager.class).getBean(CarModel.class);

### 4.6 Annotations模块
主要是Activity 和 Fragment的注解使用，@Layout @FieldView @OnClick 三个注解用法<br>
@Layout 替代 setContentView(layout)<br>
@FieldView 替代 findViewById(view.id())<br>
@OnClick 替代 setOnClickListener<br>
@Layout(id = R.layout.activity_anotations)<br>

public class AnnotationsActivity extends LemonActivity {

    @FieldView(id = R.id.btnShow)
    public Button btnShow;
    @FieldView(id = R.id.tvValue)
    public TextView tvValue;

    @Override
    protected void initView() {
        String message ="主要学习 @Layout @FieldView @OnClick 三个注解用法";

        tvValue.setText(message);
    }

    @OnClick(id = R.id.btnShow)
    public void showClick() {
        lemonMessage.sendMessage("showClick");
    }
}


### 4.7 配置模块
配置模块是一个通用模块，可以来配置一些常量<br>
配置方式:打开asserts/config/config.json,将需要配置的数据配置到json文件<br>
使用方式:Config.getValue("key"),Config.getIntValue("key"),Config.getBooleanValue("key")

### 4.8 消息模块
### 4.8.1 Toast 消息
任意位置,想toast消息,不需要考虑线程子线程<br>
LemonContext.getBean(LemonMessage.class).sendMessage("message")

## 使用方式

## 相关文档

## 作者
