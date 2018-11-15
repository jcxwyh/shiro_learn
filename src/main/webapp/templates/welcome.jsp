<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html>
    <head>
        <base href="<%=basePath%>">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <title>WELCOME</title>
    </head>
    <body>
        <h1>Welcome ${user.username} !</h1>

        <h3><a href="/logout">LOGOUT</a></h3>
    </body>
</html>