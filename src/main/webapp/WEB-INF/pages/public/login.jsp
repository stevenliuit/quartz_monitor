<%--
  Created by IntelliJ IDEA.
  User: louis
  Date: 2014/12/6
  Time: 16:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
<head>
    <title></title>
</head>
<body>
<div>
    <form id="ff" action='<c:url value="/public/toLogin"/>' method="post">
        <table>
            <tr>
                <td>用户名：</td>
                <td>
                    <input type="text" name="username" id="username"/>
                </td>
            </tr>
            <tr>
                <td>密　码：</td>
                <td>
                    <input type="password" name="password" id="password"/>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
