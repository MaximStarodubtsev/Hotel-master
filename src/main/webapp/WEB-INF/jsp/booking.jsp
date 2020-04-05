<%@ page contentType="text/html;charset=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Order Page</title>
</head>
<body>
<style>
    #container {
        height: 100%;
        width: 100%;
        display: flex;
    }
    #leftThing {
        width: 25%;
        background-color: #0097ff;
    }
    #rightThing {
        width: 50%;
        background-color: #00d309;
    }
</style>
    <form action="${pageContext.request.contextPath}/booking" method="post">
        <div id="container">
            <div id="leftThing">
                <label for="name">Name:
                    <input id="name" type="text" name="name">
                </label><br/><br/>
                <label for="surname">Surname:
                    <input id="surname" type="text" name="surname">
                </label><br/><br/>
                <label for="patronymic">Patronymic:
                    <input id="patronymic" type="text" name="patronymic">
                </label><br/><br/>
                <label for="birthdate">Birthdate:
                    <input id="birthdate" type="text" name="birthdate">
                </label><br/><br/>
                <label for="phonenumber">Phone Number:
                    <input id="phonenumber" type="text" name="phonenumber">
                </label><br/><br/>
                <label for="settlementdate">Settlementdate:
                    <input id="settlementdate" type="text" name="settlementdate">
                </label><br/><br/>
                <label for="leavedate">Leavedate:
                    <input id="leavedate" type="text" name="leavedate">
                </label><br/><br/>
                <input type="submit" value="Make Order"><br>
                <p2 id = "validDataMessage">${validDataMessage}</p2>
            </div>
            <div id="rightThing">
                <select id = "room" name="room">
                    <c:forEach var="element" items="${rooms}">
                        <option items="${element.toString()}">${element.toString()}</option>
                    </c:forEach>
                </select><br/><br/>
                <p id = "message">${message}</p>
                <c:forEach var="element" items="${orders}">
                    <span>Room number ${element.room.number} is reserved for: ${element.settlementDate}-${element.leaveDate}</span><br/>
                </c:forEach>
            </div>
        </div>
    </form>
</body>
</html>
