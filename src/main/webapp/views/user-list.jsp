<%@ page import="sus.chep1.database.User" %>
<%@ page import="sus.chep1.database.UserTable" %>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html;charset=utf-8" %>

<html>
<head>
    <title>Добавить пользователя</title>
    <link href="../css/style1.css" rel="stylesheet" type="text/css">
    <a href="info" >Справка > О программе</a>
    <script>
        function myFunction() {
            if (${id_User.getBlockStatus()})
            {
                document.getElementById('block').click();
            }
            if (${id_User.getPassLimitStatus()})
            {
                document.getElementById('pass_block').click();
            }
        }
    </script>
</head>
<body onload="javascript: myFunction()">

<div class="box">
    <form method="post">
        <h1>Админ - просмотр пользователей</h1>
        <div class="inputBox">
            <input type="text" name="login" value="${id_User.getLogin()}">
            <label>Логин пользователя</label>
        </div>

        <div class="checkBoxes">
            <p>Блокировка:</p> <input id="block" type="checkbox" name="block" value="1"/>
            <br/>
            <p>Парольное ограничение:</p> <input id="pass_block" type="checkbox" name="pass_block" value="2" />
            <br><br/>
        </div>

        <div class="listButtons">
            <input type="submit" name="<b" value="< Предыдущий" />
            <input type="submit" name="b>" value="Следующий >" />
        </div>

        <div class="menu">
            <input type="submit" name="b3" value="Сохранить"/>
            <p><%=request.getAttribute("Status")%></p>
        </div>

        <a href="admin-menu" class="backText"  >Вернуться</a>
        <br/>

    </form>
</div>
</body>
</html>
