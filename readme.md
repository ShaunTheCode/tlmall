### 项目启动
项目启动需要使用Nacos导入document/nacos-config/nacos_config_export.zip压缩包，不需要解压，直接导入即可，本压缩包注意及时更新最新版本，可能会随着课程进行内容变更




### 异步下单注意
需要添加RocketMQ的配置,配置添加到Nacos配置文件当中: tulingmall-order-dev.yml
```
rocketmq:
  name-server: 192.168.232.198:9876 #连接超时时间
  producer:
    send-message-timeout: 30000 #发送消息超时时间
    group: order-group
  tulingmall:
    scheduleTopic: order-status-check #定时任务
    cancelGroup: cancel-order #消费组业务逻辑,取消超时未支付订单
    transGroup: cart-delete #事务消息群组
    transTopic: order-cart #订单-购物车主题
    asyncOrderTopic: async-order #异步订单topic
    asyncOrderGroup: async-order-group #异步下单消息消费
```

）**配置host**

为了要大家在部署过程中不频繁的改动配置，源码中将数据库、zk、redis、es、mq等连接地址都使用域名方式

通过host解析到对应的资源中。

本机host配置如下 ，或者配置属于你自己适应的环境

```
127.0.0.1 tlshop.com
```

）**环境配置**
项目中*.yml文件里面的配置，大部分会被nacos远程的配置替换
所以只要上传nacos配置到nacos配置中心即可，修改相应的配置
也是在nacos配置中心修改，具体会在课上详细说


### 亿级流量电商项目微服务架构图
![jiagou.jpg](http://git.jiagouedu.com/java-vip/tuling-mall/raw/master/Document/tuling-mall%e5%95%86%e5%9f%8e%e5%be%ae%e6%9c%8d%e5%8a%a1%e6%9e%b6%e6%9e%84%e5%9b%be.jpg)