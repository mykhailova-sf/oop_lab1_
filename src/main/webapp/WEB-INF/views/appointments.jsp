<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Призначення</title>
    <style>
        body {
            background-color: #ffe6f0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 40px 20px;
        }

        h1 {
            text-align: center;
            color: #cc0066;
            margin-bottom: 40px;
        }

        table {
            width: 90%;
            max-width: 1000px;
            margin: auto;
            background-color: #fff0f5;
            border-collapse: collapse;
            border-radius: 12px;
            overflow: hidden;
            box-shadow: 0 4px 20px rgba(204, 0, 102, 0.2);
        }

        th, td {
            padding: 16px 12px;
            border-bottom: 1px solid #ffcce0;
            text-align: center;
        }

        th {
            background-color: #ffb3d9;
            color: #660033;
            font-weight: bold;
            font-size: 1.05em;
        }

        tr:hover {
            background-color: #ffe0ec;
        }

        .btn {
            background-color: #ff66a3;
            border: none;
            color: white;
            padding: 8px 16px;
            border-radius: 8px;
            cursor: pointer;
            transition: background-color 0.3s ease, transform 0.2s ease;
            text-decoration: none;
            font-size: 0.95em;
        }

        .btn:hover {
            background-color: #e60073;
            transform: scale(1.05);
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

<h1>Усі призначення</h1>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>ID Пацієнта</th>
        <th>Інформація</th>
        <th>Дії</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="a" items="${appointments}">
        <tr>
            <td>${a.id}</td>
            <td>${a.userId}</td>
            <td>${a.info}</td>
            <td>
                <a class="btn" href="/appointments/${a.id}">Переглянути</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
