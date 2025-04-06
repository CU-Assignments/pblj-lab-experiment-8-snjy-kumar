Steps to Create a JDBC-Integrated Servlet for Employee Management
1. Set Up Database
Create a MySQL database (EmployeeDB).
Create an employees table with columns (id, name, department, salary).
Insert sample employee data.

=============================================================================================================================
  // Create a database:

CREATE DATABASE EmployeeDB;
USE EmployeeDB;

// Create the employees table:
CREATE TABLE employees (
  id INT PRIMARY KEY,
  name VARCHAR(100),
  department VARCHAR(100),
  salary DOUBLE
);

// Insert sample data:
INSERT INTO employees (id, name, department, salary) VALUES
(1, 'John Doe', 'IT', 60000),
(2, 'Jane Smith', 'HR', 55000),
(3, 'Alice Johnson', 'Finance', 70000);

=============================================================================================================================

2. Set Up Your Java Project
Add MySQL JDBC Driver (via Maven or manually).
Configure Apache Tomcat in your IDE.

=============================================================================================================================
// Maven dependency (in pom.xml)
  <dependency>
  <groupId>mysql</groupId>
  <artifactId>mysql-connector-java</artifactId>
  <version>8.0.33</version>
</dependency>
=============================================================================================================================

3. Create Database Connection Class
Write a utility class (DBConnection.java) to establish a connection with the MySQL database.

=============================================================================================================================
  // DBConnection.java
import java.sql.*;

public class DBConnection {
  public static Connection getConnection() throws Exception {
    Class.forName("com.mysql.cj.jdbc.Driver");
    return DriverManager.getConnection("jdbc:mysql://localhost:3306/EmployeeDB", "root", "sanjay123");
  }
}

=============================================================================================================================

4. Develop the Servlet (EmployeeServlet.java)
- Handle GET requests to fetch all employees or search by Employee ID.
- Use JDBC to query data and display it in HTML format.
- Implement a search form for filtering employees by ID.

=============================================================================================================================
  // EmployeeServlet.java
import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class EmployeeServlet extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    String empId = request.getParameter("id");
    try {
      Connection conn = DBConnection.getConnection();
      Statement stmt = conn.createStatement();
      String query;

      if (empId != null && !empId.trim().isEmpty()) {
        query = "SELECT * FROM employees WHERE id = " + empId;
      } else {
        query = "SELECT * FROM employees";
      }

      ResultSet rs = stmt.executeQuery(query);
      out.println("<html><head><title>Employees</title></head><body>");
      out.println("<h2>Employee Records</h2>");
      out.println("<form method='get'>Search by ID: <input type='text' name='id'><input type='submit' value='Search'></form>");
      out.println("<table border='1'><tr><th>ID</th><th>Name</th><th>Department</th><th>Salary</th></tr>");

      while (rs.next()) {
        out.println("<tr><td>" + rs.getInt("id") + "</td><td>" +
                              rs.getString("name") + "</td><td>" +
                              rs.getString("department") + "</td><td>" +
                              rs.getDouble("salary") + "</td></tr>");
      }

      out.println("</table></body></html>");
      conn.close();
    } catch (Exception e) {
      out.println("<p>Error: " + e.getMessage() + "</p>");
    }
  }
}

=============================================================================================================================

5. Deploy and Run
Deploy the servlet on Tomcat.
Access it via http://localhost:8080/YourAppName/EmployeeServlet.
(The page displays employee records and allows searching by ID.)

=============================================================================================================================
  // web.xml
  <web-app>
  <servlet>
    <servlet-name>EmployeeServlet</servlet-name>
    <servlet-class>EmployeeServlet</servlet-class>
  </servlet>
  <servlet-mapping>
    <servlet-name>EmployeeServlet</servlet-name>
    <url-pattern>/EmployeeServlet</url-pattern>
  </servlet-mapping>
</web-app>

=============================================================================================================================

Note : Enhancements (Optional)
Improve UI with CSS & Bootstrap.
==========================================================================================================
  http://localhost:8080/ECommerce/EmployeeServlet
==========================================================================================================

