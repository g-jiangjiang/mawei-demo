# Mawei Demo 微服务项目

基于 Spring Boot 2.7.x 构建的微服务演示项目，包含 Service A (核心计算)、Service B (Feign 调用与降级)、Service C (MQ 监听) 三个模块。

## 🛠️ 环境准备

在启动服务前，请确保本地已安装并启动以下中间件，或修改 `application.yml` 中的地址：

1.  **MySQL 8.0+**
    * 地址: `localhost:3306`
    * 账号/密码:  (请在 service-a 中修改)
    * **初始化**: 请执行根目录下的 `init.sql` 脚本建表。
2.  **Redis**
    * 地址: `localhost:6379`
3.  **RabbitMQ**
    * 地址: `localhost:5672`
    * 账号/密码: (请在 service-a 中修改)

## 🚀 启动顺序

请务必按照以下顺序启动服务，以避免依赖报错：

1.  **启动 Service A** (`com.jiangjiang.servicea.ServiceAApplication`)
    * 端口: 8080
    * 作用: 提供计算服务，操作数据库和 Redis。
2.  **启动 Service C** (`com.jiangjiang.servicec.ServiceCApplication`)
    * 端口: 8082
    * 作用: 监听 RabbitMQ 队列，打印计算日志。
3.  **启动 Service B** (`com.jiangjiang.serviceb.ServiceBApplication`)
    * 端口: 8081
    * 作用: 通过 Feign 调用 A，演示服务降级。

## 🧪 测试步骤

### 1. 验证 Service A (核心计算)
执行以下命令：
```bash
sh curl.sh