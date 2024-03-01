<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html;charset=utf-8" %>

<html>
<head>
    <title>Расшифровка базы данных</title>
    <link href="../css/style1.css" rel="stylesheet" type="text/css">
    <a href="info" >Справка > О программе</a>
</head>
<body>

<div class="box">
    <form method="post">
        <h1>Расшифровка базы данных</h1>
        <br>
        <div class="inputBox">
            <input type="password" name="key" value="" autocomplete="off" readonly
                   onfocus="this.removeAttribute('readonly');">
            <label>Парольная фраза</label>
        </div>
        <p><%=request.getAttribute("submitFlag")%></p>
        <br/>
        <div class="menu">
            <input type="submit" name="b1" value="Расшифровать" />
            <input type="submit" name="b2" value="Выход из программы" />
        </div>

    </form>
</div>
</body>
</html>
