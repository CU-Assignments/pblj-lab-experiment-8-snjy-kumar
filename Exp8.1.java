Steps to Implement
1. Set Up Your Environment
- Install Java Development Kit (JDK)
- Install Apache Tomcat (Servlet Container)
- Set up an IDE (Eclipse, IntelliJ, or VScode)

2. Create an HTML Login Form (login.html)
This form collects the username and password from the user.

===============================================================================================================
  <!-- login.html -->
<!DOCTYPE html>
<html>
<head><title>Login</title></head>
<body>
  <form action="login" method="post">
    Username: <input type="text" name="username" required><br/>
    Password: <input type="password" name="password" required><br/>
    <input type="submit" value="Login">
  </form>
</body>
</html>
===============================================================================================================


3. Create the Java Servlet to Process Login (LoginServlet.java)
This servlet reads username and password from the request.
It checks the credentials.
- If valid, it displays a welcome message.
- If invalid, it redirects back to the login page.

  
===============================================================================================================
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LoginServlet extends HttpServlet {
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String user = request.getParameter("username");
    String pass = request.getParameter("password");

    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    if ("admin".equals(user) && "password123".equals(pass)) {
      out.println("<h2>Welcome, " + user + "!</h2>");
    } else {
      response.sendRedirect("login.html");
    }
  }
}


===============================================================================================================

4.  Configure web.xml
===============================================================================================================
  <!-- web.xml -->
<web-app>
  <servlet>
    <servlet-name>LoginServlet</servlet-name>
    <servlet-class>LoginServlet</servlet-class>
  </servlet>
  <servlet-mapping>
    <servlet-name>LoginServlet</servlet-name>
    <url-pattern>/login</url-pattern>
  </servlet-mapping>
</web-app>
====================================================================================================================
  
5. Deploy and Run
- Place the login.html file inside the WebContent (for VScode) or webapp (for Maven projects).
- Compile and deploy the servlet in Tomcat.
Access the form in your browser:
http://localhost:8080/login.html

===============================================================================================================
http://localhost:8080/LoginPage/login.html
===============================================================================================================
