<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html;charset=utf-8" %>

<html>
<head>
    <title>Вход - пользователь</title>
    <link href="../css/style1.css" rel="stylesheet" type="text/css">
    <a href="info" class="backText" >Справка > О программе</a>
</head>
<body>

<div class="box">
    <form action="${pageContext.request.contextPath}user-menu" method="post">
        <h1>Добро пожаловать, "<%=request.getSession().getAttribute("Logged")%>"</h1>
        <div class="menu">
            <input type="submit" name="b1" value="Изменить пароль" />
        </div>
        <br/>
        <a href="login" class="backText"  >Выйти из аккаунта</a>

    </form>
</div>
</body>
</html>
