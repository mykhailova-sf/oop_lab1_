<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Мої призначення</title>
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
            padding: 10px 0;
            border-bottom: 1px solid #ffcce0;
        }

        .item:last-child {
            border-bottom: none;
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

        .form-group {
            display: flex;
            flex-direction: column;
            gap: 10px;
            margin-bottom: 20px;
        }

        textarea {
            resize: vertical;
            padding: 10px;
            font-size: 14px;
            min-height: 60px;
        }

        .center {
            text-align: center;
        }
    </style>
</head><header><div style="display: flex; justify-content: space-between; align-items: center;
            background-color: #cc0066; padding: 10px 20px; color: white;
            font-family: Arial, sans-serif; border-radius: 8px; margin-bottom: 20px;">

    <a href="/" style="color: white; text-decoration: none; font-weight: bold;">
        ← Назад на головну
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

<h1>Мої призначення</h1>

<div class="section">
    <h2>Створити нове призначення</h2>
    <form method="post" action="/appointments/save">
        <input type="hidden" name="userId" value="${user.id}" />
        <div class="form-group">
            <label for="info">Причина звернення:</label>
            <textarea name="info" id="info" placeholder="Опишіть вашу проблему..." required></textarea>
            <button class="btn" type="submit">Створити</button>
        </div>
    </form>
</div>

<div class="section">
    <h2>Активні призначення</h2>
    <c:set var="hasActive" value="false"/>
    <c:forEach var="appointment" items="${appointments}">
        <c:if test="${!appointment.discharged}">
            <c:set var="hasActive" value="true"/>
            <div class="item">
                <strong>Причина:</strong> ${appointment.info} <br/>
                <a class="btn" href="/appointments/${appointment.id}">Детальніше</a>
            </div>
        </c:if>
    </c:forEach>

    <c:if test="${!hasActive}">
        <p>Немає активних призначень.</p>
    </c:if>
</div>

<div class="section">
    <h2>Виписані призначення</h2>
    <c:set var="hasDischarged" value="false"/>
    <c:forEach var="appointment" items="${appointments}">
        <c:if test="${appointment.discharged}">
            <c:set var="hasDischarged" value="true"/>
            <div class="item">
                <strong>Причина:</strong> ${appointment.info} <br/>
                <a class="btn" href="/appointments/${appointment.id}">Детальніше</a>
            </div>
        </c:if>
    </c:forEach>

    <c:if test="${!hasDischarged}">
        <p>Немає виписаних призначень.</p>
    </c:if>
</div>

</body>
</html>
