<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html;charset=utf-8" %>

<html>
<head>
    <title>Вход - админ</title>
    <link href="../css/style1.css" rel="stylesheet" type="text/css">
    <a href="info" class="backText" >Справка > О программе</a>
</head>
<body>

<div class="box">
    <form action="${pageContext.request.contextPath}/admin-menu" method="post">
        <h1>Режим администратора</h1>
        <div class="menu">
            <input type="submit" name="b1" value="Изменить пароль" />
            <input type="submit" name="b2" value="Добавить нового пользователя" />
            <input type="submit" name="b3" value="Просмотр пользователей"/>
            <p><%=request.getAttribute("aMenuStatus")%></p>
        </div>

        <br/>
        <a href="login" class="backText"  >Вернуться на страницу входа</a>

    </form>
</div>
</body>
</html>
