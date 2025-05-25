<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Призначення</title>
  <style>
    body {
      background-color: #ffe6f0;
      font-family: Arial, sans-serif;
      margin: 0;
      padding: 20px;
    }

    h1 {
      text-align: center;
      color: #cc0066;
    }

    .section {
      background-color: #fff0f5;
      border-radius: 15px;
      box-shadow: 0 0 10px rgba(204, 0, 102, 0.2);
      padding: 20px;
      margin-bottom: 20px;
      max-width: 800px;
      margin-left: auto;
      margin-right: auto;
    }

    .item {
      display: flex;
      justify-content: space-between;
      padding: 5px 0;
    }

    .btn {
      background-color: #ff66a3;
      border: none;
      color: white;
      padding: 6px 10px;
      border-radius: 6px;
      cursor: pointer;
      transition: background-color 0.3s ease;
      text-decoration: none;
    }

    .btn:hover {
      background-color: #e60073;
    }

    .form-inline {
      display: flex;
      gap: 10px;
      margin-top: 10px;
    }

    input[type="text"] {
      padding: 5px;
      flex: 1;
    }

    .center {
      text-align: center;
    }
  </style>
</head>
<header><div style="display: flex; justify-content: space-between; align-items: center;
            background-color: #cc0066; padding: 10px 20px; color: white;
            font-family: Arial, sans-serif; border-radius: 8px; margin-bottom: 20px;">

  <a href="/appointments" style="color: white; text-decoration: none; font-weight: bold;">
    ← Назад до призначень
  </a>

  <form action="/logout" method="post" style="margin: 0;">
    <button type="submit"
            style="background-color: #ff66a3; border: none; padding: 6px 12px;
                       border-radius: 6px; cursor: pointer; color: white; font-weight: bold;">
      Вийти
    </button>
  </form>
</div>
</header>
<body>

<h1>Призначення для пацієнта #${patient.fullName}</h1>
<br>
<p>${appointment.info}<p>
<br>
<div class="section">
  <h2>Операції</h2>
  <c:forEach var="op" items="${appointment.operations}">
    <div class="item">
      <span>${op}</span>
      <form method="post" action="/appointments/${appointment.id}/remove-operation">
        <input type="hidden" name="operation" value="${op}"/>
        <button class="btn" type="submit">Виконано</button>
      </form>
    </div>
  </c:forEach>

  <form class="form-inline" method="post" action="/appointments/${appointment.id}/add-operation">
    <input type="text" name="operation" placeholder="Назва операції" required/>
    <button class="btn" type="submit">Додати</button>
  </form>
</div>

<div class="section">
  <h2>Процедури</h2>
  <c:forEach var="proc" items="${appointment.procedures}">
    <div class="item">
      <span>${proc}</span>
      <form method="post" action="/appointments/${appointment.id}/remove-procedure">
        <input type="hidden" name="procedure" value="${proc}"/>
        <button class="btn" type="submit">Виконано</button>
      </form>
    </div>
  </c:forEach>

  <form class="form-inline" method="post" action="/appointments/${appointment.id}/add-procedure">
    <input type="text" name="procedure" placeholder="Назва процедури" required/>
    <button class="btn" type="submit">Додати</button>
  </form>
</div>

<div class="section">
  <h2>Ліки</h2>
  <c:forEach var="drug" items="${appointment.drugs}">
    <div class="item">
      <span>${drug}</span>
    </div>
  </c:forEach>

  <form class="form-inline" method="post" action="/appointments/${appointment.id}/add-drug">
    <input type="text" name="drug" placeholder="Назва ліків" required/>
    <button class="btn" type="submit">Додати</button>
  </form>
</div>

<c:if test="${empty appointment.operations and empty appointment.procedures and empty appointment.drugs}">
  <div class="center">
    <form method="post" action="/appointments/${appointment.id}/discharge">
      <label for="diagnosis">Остаточний діагноз:</label><br>
      <textarea name="diagnosis" id="diagnosis" rows="3" required placeholder="Напишіть остаточний діагноз..."></textarea><br><br>
      <button class="btn" type="submit">Виписати з лікарні</button>
    </form>

  </div>
</c:if>

</body>
</html>
