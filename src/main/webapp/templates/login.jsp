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
        <title>login</title>
    </head>
    <body>
        <form action="/login" method="post">
            <input name="username" type="text"/><br/>
            <input name="password" type="password"/><br/>
            <button type="submit">LOGIN</button>
        </form>
    </body>
</html>