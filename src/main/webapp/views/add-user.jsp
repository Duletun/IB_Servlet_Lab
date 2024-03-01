<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html;charset=utf-8" %>

<html>
<head>
    <title>Добавить пользователя</title>
    <link href="../css/style1.css" rel="stylesheet" type="text/css">
    <a href="info" >Справка > О программе</a>
</head>
<body>

<div class="box">
    <form method="post">
        <h1>Админ - добавление пользователя</h1>
        <div class="inputBox">
            <input type="text" name="login" value="">
            <label>Логин нового пользователя</label>
        </div>
        <p><%=request.getAttribute("submitFlag")%></p>
        <br/>
        <div class="confirm">
            <button>Добавить</button>
        </div>
        <a href="admin-menu" class="backText"  >Вернуться</a>
        <br/>

    </form>
</div>
</body>
</html>
