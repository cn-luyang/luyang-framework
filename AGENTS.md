# AGENTS.md

**LUYANG-FRAMEWORK** 是一个基于 Java 21 & Spring Boot 4.0+ 的企业级快速开发脚手架

---

## 项目结构与依赖拓扑
### 模块职责

- **dependencies**：统一管理所有第三方及内部模块依赖版本（BOM）。**所有版本号严禁在子模块硬编码。**
- **luyang-framework-base-util**：纯工具包，绝对禁止引入 Spring 容器依赖。
- **luyang-framework-starter-base**：核心基础能力（核心依赖、统一返回值、异常、枚举、校验等）。
- **业务 Starters**：web, mybatis, redisson, security 等，按需引入。

### 拓扑关系

```
base-util (无 Spring, 仅 servlet-api + slf4j)
    ↑
starter-base (依赖 base-util, Spring Context, MapStruct, Jackson)
    ↑
各业务 Starter (依赖 starter-base + 对应技术组件)
    ↑
使用者项目（引用所需 starter）
```

### Starter 标准目录结构
所有新建或重构的 Starter 必须严格遵循以下结构（包名统一为 io.github.luyang.starter.{模块名}）：
```
luyang-framework-starter-xxx/
├── pom.xml
└── src/
    └── main/
        ├── java/
        │   └── io/github/luyang/starter/模块名/
        │       ├── annotation/          # 自定义注解
        │       ├── config               # 自动配置类
        │       ├── properties           # 配置属性类
        │       ├── common/              # 模块内部公共基类
        │       │   ├── constant         # 常量定义
        │       │   ├── enums            # 枚举定义
        │       │   └── model            # 模块内通用模型
        │       ├── features             # 可选特性功能（按子功能分包，如 queue/desensitize）
        │       ├── support              # 技术支撑层（filter/interceptor/handler/resolver/advice/mapper）
        │       ├── context              # Spring 上下文扩展（initializer/listener/publisher）
        │       ├── helper               # 第三方库封装辅助类
        │       ├── util                 # 模块内工具类
        │       └── remote               # 远程调用封装（dubbo/openfeign）
        └── resources/
            └── META-INF/
                └── spring/
                    └── org.springframework.boot.autoconfigure.AutoConfiguration.imports
```

---

## 编码与构建规范

### Javadoc 注释规范
- **核心要求**：必须使用**通俗、地道的中文**，杜绝无意义的机翻。
- **类/接口**：必须包含功能描述与作者信息（`@author yang.lu`）。
- **方法**：明确说明 `@param`（是否可空、边界）、`@return`（特殊值含义）及 `@throws`。
- **单行注释**：一律使用 `//`；枚举/字段状态说明必须对齐。
```
/**
 * 用户管理服务实现类
 * @author yang.lu
 */
public class UserServiceImpl implements UserService {

    /** 用户状态: 0-禁用, 1-启用, 2-锁定 */
    private Integer status;

    /**
     * 根据用户ID查询用户信息
     * @param userId 用户ID，不能为 null
     * @return 可空的用户视图对象
     */
    @Override
    public UserVO getUserById(Long userId) { ... }
}
```
### 代码风格：
- 优先复用已有实现，禁止重复造轮子
- 修改代码时保持现有风格一致
- 优先小范围修改，避免无关重构
- 未经要求禁止大规模格式化

### 日志规范
- **工具**：统一使用 SLF4J（`LoggerFactory.getLogger()`）。
- **格式**：严格采用 `[模块名] [操作] [关键参数/结果]`。例如：`log.info("[Dict] 加载字典数据成功，耗时 {}ms", duration);`

### Maven 构建规范
- **单点版本**：全工程基于 `<revision>` 属性实现单点版本修改。
- **编译参数**：JDK 21，UTF-8，必须开启 `-Xlint:unchecked`。

---

## Agent 的核心执行指令（红线）
1. **安全红线**：严禁提交 `.env` 文件。严禁在代码、配置（`application.yml`）、测试或注释中硬编码任何密钥、密码、Token。敏感凭证必须通过环境变量注入。
2. **前置确认**：收到任何代码修改指令时，**严禁直接动手**。必须先分析受影响的文件，给出具体的修改方案列表，**等待用户显式回复后方可编码**。
3. **测试红线**：任何代码变更必须同步编写或更新单元测试。交付前必须在本地运行通过，**严禁破坏既有测试**。
4. **现代 Java 特性**：优先使用 `record` 代替传统 DTO，利用 `switch` 模式匹配（Pattern Matching）和 `SequencedCollection` 优化集合操作。
5. **Spring Boot 4.0+ 适配**：自动配置必须通过 `AutoConfiguration.imports` 注册。编写配置类时，`@ConditionalOnProperty` 等条件注解必须严谨，防止启动冲突。
6. **Git 规范**：Commit Message 必须遵循 Conventional Commits 规范（如 `feat(dict): 增加缓存刷新` 或 `fix(base): 修复空指针`）。
7. **信息不明确时禁止猜测**：涉及业务逻辑、数据库结构、接口语义时，必须先查阅相关代码后再给出方案。 如无法确认，应主动向用户提问。