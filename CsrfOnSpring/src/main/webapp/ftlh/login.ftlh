<#ftl encoding="UTF-8"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../css/login.css">
</head>
<body>

<div class="login-page">
    <div class="heading">
        <a id="register" class="active"><span>Login</span></a>
    </div>
    <div class="form">
        <form class="login-form" id="form" action="/signIn" method="post">
            <input type="hidden" value="${csrf_token}" name="csrf_token">
            <div class="input-field">
                <label for="username"><span>Username</span></label>
                <input type="text" id="username" name="username" placeholder="Username">
            </div>
            <div class="input-field">
                <label for="password"><span>Password</span></label>
                <input type="password" id="password" name="password" placeholder="Password">
            </div>
            <input type="submit" value="Sign In" class="input-button">
        </form>
    </div>
</div>
</body>
</html>