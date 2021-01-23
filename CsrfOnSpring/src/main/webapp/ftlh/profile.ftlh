<#ftl encoding="UTF-8"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Profile</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
<body>
<div>
    <h4>Username: ${user.username}</h4>
    <h4>ID: ${user.id}</h4>
    <h4>Paswword: ${user.password}</h4>
    <h4>CSRF Token: ${csrf_token}</h4>
</div>
<form action="/profile?action=delete&userId=${user.id}" method="post">
    <input type="hidden" value="${csrf_token}" name="csrf_token">
    <input type="submit" value="Delete">
</form>
</body>
</html>