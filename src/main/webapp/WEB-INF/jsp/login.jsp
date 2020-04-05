<%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 3/18/2020
  Time: 12:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
</head>
<body>
    <form action="${pageContext.request.contextPath}/login" method="post">
        <label for="username">User name:
            <input id="username" type="text" name="username">
        </label><br/><br/>
        <label for="password">Password:
            <input id="password" type="text" name="password">
        </label><br/><br/>
        <input type="submit" value="Login"><br>
        <p>${loginMessage}</p>
    </form>
</body>
</html>
