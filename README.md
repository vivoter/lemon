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
<img src="http://images.cnblogs.com/cnblogs_com/luxiaofeng54/815456/o_lemon_framework.png" width="300" height="300"/>

## 三、脑图
<img src="http://images.cnblogs.com/cnblogs_com/luxiaofeng54/815456/o_lemon_naotu.png" width="600" height="400"/><br>
脑图地址: [戳这里](http://naotu.baidu.com/file/49cb90afc5275d88583dcff11996d8fe)

## 四、模块
### 4.1 IOC模块
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
    </property>
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

#### 4.1.2 IOC模块 Annotation 配置

### 4.2 网络交互模块

### 4.3 数据库模块

### 4.4 初始化模块

### 4.5 缓存模块

### 4.6 Annotations模块

### 4.7 配置模块

### 4.8 消息模块

## 使用方式

## 相关文档
