<#ftl encoding="UTF-8"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Form</title>
</head>
<body>
<form action="${form.action}" method="${form.method}">
    <#list inputs as listInput>
        <input type="${listInput.type}" name="${listInput.name}" placeholder="${listInput.placeholder}">
    </#list>
</form>

</body>
</html>