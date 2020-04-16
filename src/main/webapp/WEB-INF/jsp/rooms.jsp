<%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 3/10/2020
  Time: 11:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/rooms" method="post">
    <div>
        <label for="number">Number:
            <input id="number" type="text" name="number">
        </label><br/><br/>
        <label for="places">Places:
            <input id="places" type="text" name="places">
            <span> Variants: ONE, TWO, THREE, FOUR</span>
        </label><br/><br/>
        <label for="level">Level:
            <input id="level" type="text" name="level">
            <span> Variants: BUDGET, MEDIUM, LUX</span>
        </label><br/><br/>
        <label for="rent">Rent:
            <input id="rent" type="text" name="rent">
        </label><br/><br/>
        <input type="submit" value="Add room"><br>
        <p id = "message">${validRoomMessage}</p>
    </div>
</form>
</body>
</html>
