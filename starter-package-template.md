## 📂 项目目录结构模板

```azure
project
    └── src/main/java/com/xxx/starter
    ├── config                       # 配置入口（Spring Boot Starter 的核心配置）
    │   ├── StarterConfig            # 配置
    │   └── StarterAutoConfig.java   # 自动配置
    │
    ├── properties                   # 配置属性类（@ConfigurationProperties）
    │   └── StarterProperties.java
    │
    ├── annotation                   # 自定义注解（如 @EnableXxx、@XxxFeature）
    │   └── EnableStarter.java
    │
    ├── common                       # 公共基础模块（通用支持层）
    │   ├── constant                 # 系统常量定义
    │   │   └── SystemConstants.java
    │   ├── enums                    # 枚举类（状态码、业务类型等）
    │   │   └── ErrorCodeEnum.java
    │   ├── exception                # 异常体系（自定义异常类）
    │   │   └── BizException.java
    │   └── model                    # 通用模型（DTO/VO/响应包装类）
    │       └── Result.java
    │
    ├── features                     # 功能模块（可选特性）
    │   ├── cache                    # 缓存功能封装
    │   │   └── CacheManager.java
    │   ├── idempotent               # 幂等控制
    │   │   └── IdempotentAspect.java
    │   ├── limit                    # 限流控制
    │   │   └── RateLimiterHandler.java
    │   ├── lock                     # 分布式锁
    │   │   └── DistributedLockTemplate.java
    │   ├── queue                    # 消息队列封装
    │   │   ├── local                # 本地队列
    │   │   │   └── LocalQueueProcessor.java
    │   │   ├── delay                # 延迟队列
    │   │   │   └── DelayQueueScheduler.java
    │   │   └── stream               # 流式队列（Kafka/Redis Stream 等）
    │   │       └── StreamQueueConsumer.java
    │   └── retry                    # 重试机制
    │       └── RetryExecutor.java
    │
    ├── support                      # Web 层支持（与业务无关）
    │   ├── resolver                 # 参数解析器
    │   │   └── UserArgumentResolver.java
    │   ├── advice                   # 全局异常/响应处理
    │   │   └── GlobalExceptionAdvice.java
    │   ├── filter                   # Servlet 过滤器
    │   │   └── TraceIdFilter.java
    │   ├── interceptor              # Spring MVC 拦截器
    │   │   └── AuthenticationInterceptor.java
    │   └── handler                  # 请求处理器（认证/鉴权等）
    │       └── SecurityAuthorizationHandler.java
    │
    ├── context                      # Spring 上下文扩展
    │   ├── initializer              # 上下文初始化器
    │   │   └── StarterContextInitializer.java
    │   ├── listener                 # 应用事件监听器
    │   │   └── ApplicationStartedListener.java
    │   └── publisher                # 事件发布器
    │       └── EventPublisher.java
    │
    ├── remote                       # 远程调用封装
    │   ├── dubbo                    # Dubbo 客户端代理
    │   │   └── DubboClientProxy.java
    │   └── openfeign                # OpenFeign 客户端代理
    │       └── FeignClientProxy.java
    │
    ├── util                         # 工具类（无业务依赖的通用方法）
    │   └── JsonUtils.java
    │
    └── helper                       # 辅助类（对第三方库的封装，便于替换）
        └── RedisHelper.java

```