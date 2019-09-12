# gateway-auth

#### 介绍
基于zuul网关项目
有如下功能:
1. token校验
2.关键字过滤
3.基本权限处理
4.xss关键字转义
5.流量限制(这里偷个懒用sentinel限流,具体使用可以百度 ali sentinel)
6.后续添加(token颁布, token自动后台刷新,流量控制???)

#### 软件架构
软件架构说明
基于zuul路由
注册中心和配置中心可以自选,目前项目中并未加入这些(推荐使用阿里的nacos 简单粗暴)
使用框架版本如下:
springcloud[Greenwich.SR2]
springboot[2.1.7.RELEASE]
mybatis-plus[3.1.2]
#### 模块说明

1. zkh-gateway 网关模块:主要是做token验证、url拦截、url转发路由功能、xss转义、关键字过滤等功能
2. zkh-api 服务模块:提供服务的api 可以有多个同级别服务
3. zkh-common 工具模块:各种工具类提供[此项目非springboot项目]

#### 使用说明

1. 首先启动zkh-gateway
2. 其次启动zkh-api
3. 可以打开浏览器访问http://localhost:8888/api/token/accessToken?id=1
[里面是我自己用mybatis-plus写的用户信息获取,user表很简单建表语句在zkh-api/src/main/reources/user_info.sql]

#### 码云特技

1. 使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2. 码云官方博客 https://gitee.com/Caine/gateway-auth
3. 你可以 [https://gitee.com/Caine/gateway-auth]这个地址来了解码云上的优秀开源项目
4. [GVP](https://gitee.com/gvp) 全称是码云最有价值开源项目，是码云综合评定出的优秀开源项目
5. 码云官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6. 码云封面人物是一档用来展示码云会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)