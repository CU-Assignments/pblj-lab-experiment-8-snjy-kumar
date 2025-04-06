Steps to Develop a JSP-Based Student Portal with Attendance Submission
  
1. Set Up the Database (MySQL)
Create a database named StudentDB.
Create a table attendance with columns: id, student_name, date, status.
Insert sample records for testing.
=============================================================================================================================

CREATE DATABASE StudentDB;
USE StudentDB;

CREATE TABLE attendance (
  id INT AUTO_INCREMENT PRIMARY KEY,
  student_name VARCHAR(100),
  date DATE,
  status VARCHAR(10)
);

-- Sample Records
INSERT INTO attendance (student_name, date, status) VALUES
('Alice', '2024-04-01', 'Present'),
('Bob', '2024-04-01', 'Absent');


=============================================================================================================================


2. Configure Your Java Project
Add MySQL JDBC Driver (via Maven or manually).
Set up Apache Tomcat in your IDE (Eclipse/VScode).
=============================================================================================================================

<dependency>
  <groupId>mysql</groupId>
  <artifactId>mysql-connector-java</artifactId>
  <version>8.0.33</version>
</dependency>


=============================================================================================================================


3. Create a Database Connection Class (DBConnection.java)
This utility class connects to MySQL.
=============================================================================================================================

// DBConnection.java
import java.sql.*;

public class DBConnection {
  public static Connection getConnection() throws Exception {
    Class.forName("com.mysql.cj.jdbc.Driver");
    return DriverManager.getConnection("jdbc:mysql://localhost:3306/StudentDB", "root", "sanjay123");
  }
}


=============================================================================================================================

4. Create the JSP Form (attendance.jsp)
A form where users enter student name, date, and attendance status (Present/Absent).
=============================================================================================================================
<!-- attendance.jsp -->
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<html>
<head><title>Attendance Form</title></head>
<body>
  <h2>Submit Attendance</h2>
  <form action="AttendanceServlet" method="post">
    Name: <input type="text" name="student_name" required><br/>
    Date: <input type="date" name="date" required><br/>
    Status:
    <select name="status">
      <option value="Present">Present</option>
      <option value="Absent">Absent</option>
    </select><br/>
    <input type="submit" value="Submit">
  </form>
</body>
</html>



=============================================================================================================================

5. Develop the Servlet (AttendanceServlet.java)
Handles form submission and saves attendance to the database.
Uses JDBC to insert data into MySQL.
=============================================================================================================================
// AttendanceServlet.java
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AttendanceServlet extends HttpServlet {
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String name = request.getParameter("student_name");
    String date = request.getParameter("date");
    String status = request.getParameter("status");

    try {
      Connection conn = DBConnection.getConnection();
      String sql = "INSERT INTO attendance (student_name, date, status) VALUES (?, ?, ?)";
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1, name);
      ps.setDate(2, java.sql.Date.valueOf(date));
      ps.setString(3, status);

      ps.executeUpdate();
      conn.close();

      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      out.println("<h3>Attendance Submitted Successfully!</h3>");
      out.println("<a href='attendance.jsp'>Back to Form</a>");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}



=============================================================================================================================

6. Configure web.xml (If Needed)
Map AttendanceServlet to handle form submissions.
=============================================================================================================================

<web-app>
  <servlet>
    <servlet-name>AttendanceServlet</servlet-name>
    <servlet-class>AttendanceServlet</servlet-class>
  </servlet>
  <servlet-mapping>
    <servlet-name>AttendanceServlet</servlet-name>
    <url-pattern>/AttendanceServlet</url-pattern>
  </servlet-mapping>
</web-app>


=============================================================================================================================

7. Deploy & Test
Run on Tomcat.
Access via http://localhost:8080/YourAppName/attendance.jsp.
Submit attendance, check database for saved records.
=============================================================================================================================
http://localhost:8080/AttenceApp/attendance.jsp

=============================================================================================================================

Enhancements (Optional)
- Display submitted attendance records in attendance.jsp.
- Add CSS/Bootstrap for better UI.
- Implement session handling for authentication.
