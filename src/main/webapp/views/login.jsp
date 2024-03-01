<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html;charset=utf-8" %>


<html>
<head>
    <title>Login</title>
    <link href="../css/style1.css" rel="stylesheet" type="text/css">
    <a href="info" >Справка > О программе</a>
</head>

<body>
<div class="box">
<form method="post">
    <h1>Авторизация</h1>
    <div class="inputBox">
        <input type="text" name="login" autocomplete="off" readonly
               onfocus="this.removeAttribute('readonly');">
        <label>Логин</label>
    </div>
    <br/>

    <div class="inputBox">
        <input type="password" name="pass" autocomplete="off" readonly
               onfocus="this.removeAttribute('readonly');">
        <label>Пароль</label>
    </div>
    <p><%=request.getAttribute("LoginStatus")%></p>
    <br/>
    <div class="menu">
        <input type="submit" name="b1" value="Вход" />
        <input type="submit" name="b2" value="Выход из программы" />
    </div>

</form>
</div>
</body>

</html>
