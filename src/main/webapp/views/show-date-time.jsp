<?xml version="1.0" encoding="UTF-8" ?>
<%@ page contentType="text/html;charset=utf-8" %>

<html>
<head>
  <title>Дата и время</title>
  <link href="../css/style1.css" rel="stylesheet" type="text/css">
  <a href="info" class="backText" >Справка > О программе</a>
</head>
<body>

<div class="box">
  <form method="post">
    <h1>Дата и время:</h1>
    <br/>
    <h1> <%=request.getAttribute("date")%></h1>
    <br/>
    <a href="login" class="backText" >Вернуться на страницу входа</a>

  </form>
</div>
</body>
</html>
