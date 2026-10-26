# 养老管理系统（控制台版）

基于 Java + MySQL 的养老机构管理系统，采用原生 JDBC 与 MVC 五层架构实现，通过控制台菜单交互，完成客户、床位、护理、健康管家、用户等业务的增删改查。

## 技术栈

- 开发语言：Java JDK 17
- 数据库：MySQL 8.0（库名 `yiyang_db`，端口 3306）
- 数据库驱动：mysql-connector-j 8.0.31（位于 `lib/` 目录）
- 架构模式：原生 JDBC + MVC 五层架构（View → Controller → Service → Dao → Entity）
- 构建方式：javac 命令行编译，无框架

## 运行环境配置

数据库连接信息在 `src/com/neusoft/common/Common.java`：

```
DB_URL      = jdbc:mysql://localhost:3306/yiyang_db?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
DB_USER     = root
DB_PASSWORD = 123456
```

运行前请确保：

1. 本地 MySQL 服务已启动（3306 端口）；
2. 已创建数据库 `yiyang_db` 并导入对应表结构；
3. `lib/mysql-connector-j-8.0.31.jar` 驱动包存在。

## 编译与运行

在项目根目录 `D:\mycode\nursing-home` 下执行：

```bash
# 编译（输出到 build 目录）
javac -encoding UTF-8 -cp lib\mysql-connector-j-8.0.31.jar -d build (Get-ChildItem -Recurse src\*.java)

# 运行入口
java -cp "build;lib\mysql-connector-j-8.0.31.jar" com.neusoft.Test
```

程序入口为 `com.neusoft.Test`，main 方法中 `new HomeView()` 启动主菜单。

## 系统功能模块

主菜单（`HomeView`）：

```
========== 养老管理系统==========
1. 客户管理
2. 床位管理
3. 护理管理
4. 健康管家
5. 用户管理
0. 退出系统
```

| 模块 | 说明 |
|---|---|
| 客户管理 | 入住老人/客户信息维护 |
| 床位管理 | 床位、房间、床位详情管理 |
| 护理管理 | 护理内容、护理等级、等级项目关联、护理记录、客户护理项目分配 |
| 健康管家 | 健康巡查/护理记录查看 |
| 用户管理 | 系统用户（护理人员）维护 |
| 其他 | 请假外出（Outward）、退住（Backdown）、日常护理（DailyCare）等业务 |

## 项目目录结构

```
nursing-home
├── lib/                          # MySQL 驱动包
│   └── mysql-connector-j-8.0.31.jar
└── src/com/neusoft/
    ├── Test.java                 # 程序入口 main
    ├── common/
    │   └── Common.java           # 数据库连接常量
    ├── util/
    │   └── JDBCUtil.java         # JDBC 工具类（连接/预编译/执行/关闭）
    ├── entity/                   # 实体层：与数据库表一一对应
    │   ├── Customer.java
    │   ├── Bed.java
    │   ├── Room.java
    │   ├── BedDetails.java
    │   ├── NurseContent.java
    │   ├── NurseLevel.java
    │   ├── NurseLevelItem.java
    │   ├── NurseRecord.java
    │   ├── CustomerNurseItem.java
    │   ├── Outward.java
    │   ├── Backdown.java
    │   └── User.java
    ├── dao/                      # Dao 接口层
    │   └── impl/                 # Dao 实现层：编写 SQL 操作数据库
    ├── service/                  # Service 接口层（I 开头）
    │   └── Impl/                 # Service 实现层：业务校验与调度
    ├── controller/               # Controller 控制层：接收参数、转发请求
    └── view/                     # View 视图层：控制台菜单交互
        ├── HomeView.java         # 主菜单
        ├── NurseManageView.java  # 护理管理子菜单
        ├── NurseContentView.java
        ├── NurseLevelView.java
        ├── NurseLevelItemView.java
        ├── NurseRecordView.java
        └── CustomerNurseItemView.java
```

## 分层说明

- **View 视图层**：控制台打印菜单、Scanner 接收用户输入、展示结果。
- **Controller 控制层**：接收 View 传入的参数，组装实体对象，转发调用 Service，不写业务逻辑。
- **Service 业务层**：接口 + 实现类结构，负责业务校验（如日期格式、数字输入），调用 Dao。
- **Dao 数据访问层**：接口 + 实现类结构，编写 SQL，通过 JDBCUtil 与 MySQL 交互。
- **Entity 实体层**：与数据库表字段一一对应，封装数据在各层间传递。

调用链路：`View → Controller → Service → Dao → MySQL`，结果沿原路逐层返回。

## 数据库设计要点

- 业务主表采用**逻辑删除**：`is_deleted` 字段（0 有效 / 1 已删除），查询统一带 `WHERE is_deleted = 0`。
- 护理等级与护理项目为**多对多关系**，通过中间表 `nurselevelitem`（level_id + item_id）维护。
- 护理记录查询使用 `LEFT JOIN` 关联客户表、护理内容表、用户表，一次性查出客户姓名、项目名称、护理人昵称。
- 所有数据库参数均使用 `PreparedStatement` 的 `?` 占位符绑定，防止 SQL 注入。
