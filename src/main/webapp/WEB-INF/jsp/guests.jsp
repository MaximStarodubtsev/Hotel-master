<%--
  Created by IntelliJ IDEA.
  User: Acer
  Date: 3/8/2020
  Time: 11:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <div>
        <span>Guests</span><br/><br/>
        <c:forEach var="element" items="${guests}">
            <span>${element.toString()}</span><br/>
        </c:forEach>
    </div>
</body>
</html>
