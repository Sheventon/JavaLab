<#ftl encoding="UTF-8"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>About Me</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../css/about_me.css">
</head>
<body>
<div class="main">
    <div class="main-content">
        <div class="top-text">
            <div class="main-info">

                <h1>${user.firstName} ${user.lastName}</h1>
                <h3>Age: ${user.age}</h3>
                <h3>Username: ${user.username}</h3>
            </div>
            <#if user.description??>
                <p>${user.description}</p>
            <#else>
                <p>Not found</p>
            </#if>
        </div>
    </div>
    <div class="photo">
        <div class="photo-full-width">
            <h1>The Dreamer</h1>
        </div>
    </div>
    <div class="row">
        <#list friends as listFriend>
            <div class="secondary">
                <h2>${listFriend.firstName} ${listFriend.lastName}</h2>
                <h3>Age: ${listFriend.age}</h3>
                <p>${listFriend.description}</p>
            </div>
        </#list>
    </div>
</div>

<div class="footer">
    <h2 class=the-end">Design your life.</h2>
</div>

</body>
</html>