<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Title</title>
</head>
<%
    String username = String.valueOf(request.getAttribute("username"));
    String password = String.valueOf(request.getAttribute("password"));
%>
<body>
    <div>
        <h4><%=username%></h4>
        <h4><%=password%></h4>
    </div>
</body>
</html>
