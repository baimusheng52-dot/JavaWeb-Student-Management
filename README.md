# JavaWeb 学生信息管理系统

### 📌 项目简介
本项目是大二期间学习 JavaWeb 开发的实践项目，主要练习后端逻辑处理与数据库的交互。

### 🛠 技术栈
* **后端**: Java Servlet, JDBC
* **前端**: JSP, HTML, CSS
* **数据库**: MySQL 8.0
* **服务器**: Apache Tomcat 9.0
* **IDE**: Visual Studio Code

### 🚀 实现功能
1. **学生信息展示**：通过 JDBC 从 MySQL 读取数据并渲染至 JSP 表格。
2. **数据库连接管理**：封装了数据库连接工具类，处理异常捕获。
3. **Servlet 路由**：实现请求的分发与业务逻辑处理。

### ⚙️ 环境配置
1. 导入项目根目录下的 `database.sql` 到本地 MySQL。
2. 修改 `src` 目录下数据库连接工具类的 `url`、`user` 和 `password`。
3. 部署至 Tomcat 并在浏览器访问 `http://localhost:8080/项目名/index.jsp`。
