<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html;charset=utf-8" %>


<html>
<head>
    <title>Изменение пароля</title>
    <link href="../css/style1.css" rel="stylesheet" type="text/css">
    <script>
        function myFunction() {
            if ("<%=request.getSession().getAttribute("isNew")%>" == "true") {
                document.getElementById("old-pass-div").style.display = "none"
            }
            if ("<%=request.getSession().getAttribute("isNew")%>" == "true") {
                document.getElementById("old-pass-div").style.display = "none"
            }
        }
    </script>
    <a href="info" class="backText" >Справка > О программе</a>
</head>
<body onload="javascript: myFunction()">

<div class="box">
    <form method="post">
        <h1>Изменение пароля <%=request.getAttribute("newStatus")%></h1>

        <b>Логин: <%=request.getSession().getAttribute("Logged")%></b>
        <h4><%=request.getAttribute("PassLimitStatus")%></h4>
        <br/>
        <br/>
        <br/>
        <div id="old-pass-div" class="inputBox">
            <input type="password" name="old_pass" value="">
            <label>Старый пароль</label>
        </div>

        <br/>
        <div class="inputBox">
            <input type="password" name="new_pass">
            <label>Новый пароль</label>
        </div>
        <br/>
        <div class="inputBox">
            <input type="password" name="check_pass">
            <label>Подтверждение</label>
        </div>

        <p><%=request.getAttribute("status")%></p>
        <br/>
        <div class="menu">
            <input type="submit" name="b1" value="Сохранить" />
        </div>
        <a href="<%=request.getAttribute("link")%>" class="backText" >Вернуться</a>


    </form>
</div>
</body>
</html>
