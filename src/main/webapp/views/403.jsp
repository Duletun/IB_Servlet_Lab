<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html;charset=utf-8" %>

<html>
<head>
    <title>403</title>
    <link href="../css/style1.css" rel="stylesheet" type="text/css">
    <a href="info" class="backText" >Справка > О программе</a>
</head>
<body>

<div class="box">
    <form method="post">
        <h1>403 - Данная страница недоступна</h1>
        <h1><%=request.getAttribute("submitFlag")%></h1>
        <br/>
        <a href="enter" class="backText"  >Вернуться на страницу расшифровки </a>

    </form>
</div>
</body>
</html>
