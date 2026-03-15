package com.example;

//导入 jakarta.servlet 相关的类，这些类提供了处理 HTTP 请求和响应的功能
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//导入 Java IO 类，用于处理输入输出操作
import java.io.IOException;
import java.io.PrintWriter;

//导入 Java SQL 类，这些类提供了连接数据库、执行 SQL 语句和处理结果集的功能
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@WebServlet("/hello")

// 定义一个名为 dem 的 Servlet 类，继承自 HttpServlet，负责处理 HTTP 请求
public class dem extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // --- [初始化响应设置] ---
        // 设置响应内容为 HTML，并强制使用 UTF-8 编码，解决浏览器显示中文乱码问题
        response.setContentType("text/html;charset=UTF-8");
        // 获取输出流（类似于 C++ 的 cout），用于向浏览器页面写内容
        PrintWriter out = response.getWriter();

        try {
            /* --- [第一阶段 - 建立数据库连接] --- */

            // 加载 MySQL 驱动类的字节码。在 8.0 版本中，这一步是为了注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 数据库连接字符串（URL）。包含协议(jdbc:mysql)、主机(localhost)、端口(3306)、数据库名及各种参数
            String url = "jdbc:mysql://localhost:3306/student_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
            String user = "root"; // 数据库用户名
            String password = "******"; // 数据库密码

            // DriverManager 会根据 URL 建立物理连接，返回一个 Connection 对象（类似于文件句柄）
            Connection conn = DriverManager.getConnection(url, user, password);

            /* --- [第二阶段 - 执行 SQL 指令] --- */
            Statement stmt = conn.createStatement();
            // 定义你的多表联查 SQL 语句
            String sql = "SELECT s.name, s.score, s.age, c.class_name " +
                    "FROM students s " +
                    "INNER JOIN classes c ON s.class_id = c.class_id " +
                    "ORDER BY s.class_id ASC, s.score DESC";

            ResultSet rs = stmt.executeQuery(sql);

            /* --- [第三阶段 - 处理并渲染结果] --- */

            out.println("<html><body>");
            out.println("<h1>欢迎来到 Java 课程！</h1>");
            out.println("<h2>学生成绩单 (多表联查结果)：</h2>");
            out.println("<table border='1' cellspacing='0' cellpadding='5'>");
            out.println("<tr bgcolor='#cccccc'><th>学生姓名</th><th>分数</th><th>年龄</th><th>所属班级</th></tr>");

            int count = 0;
            while (rs.next()) {
                count++;
                out.println("<tr>");

                // 注意：这里的 getString 内容必须和 SQL 结果集的列名对应
                out.println("<td>" + rs.getString("name") + "</td>");
                out.println("<td>" + rs.getInt("score") + "</td>");
                out.println("<td>" + rs.getInt("age") + "</td>");
                out.println("<td>" + rs.getString("class_name") + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");

            /*
             * --- [第四阶段 - 资源回收] ---
             * Java 虽有 GC，但数据库连接属于有限的外部资源，必须手动关闭。
             * 遵循“后开先闭”原则：ResultSet -> Statement -> Connection。
             */
            rs.close(); // 释放结果集内存
            stmt.close(); // 释放 SQL 传输通道
            conn.close(); // 断开与数据库的物理连接

        } catch (Exception e) {
            /*
             * --- [第五阶段 - 异常处理] ---
             * 捕获驱动找不到、密码错误、SQL 语法错误等所有可能导致崩溃的问题。
             */
            out.println("<h3 style='color:red;'>数据库连接失败！</h3>");
            out.println("<pre>");
            e.printStackTrace(out); // 将具体报错的“栈追踪”打印到网页，辅助定位 Bug
            out.println("</pre>");
        }
    }
}