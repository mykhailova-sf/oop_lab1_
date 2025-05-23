<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Пацієнти</title>
    <style>
        body {
            background-color: #ffe6f0;
            font-family: Arial, sans-serif;
            color: #333;
            margin: 0;
            padding: 20px;
        }

        h1 {
            color: #cc0066;
            text-align: center;
        }

        .patient-list {
            max-width: 600px;
            margin: 0 auto;
            background-color: #fff0f5;
            border-radius: 15px;
            box-shadow: 0 0 10px rgba(204, 0, 102, 0.2);
            padding: 20px;
        }

        .patient {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 10px 0;
            border-bottom: 1px solid #ffcce0;
        }

        .patient:last-child {
            border-bottom: none;
        }

        .view-button {
            background-color: #ff66a3;
            border: none;
            color: white;
            padding: 8px 12px;
            border-radius: 8px;
            cursor: pointer;
            transition: background-color 0.3s ease;
            text-decoration: none;
        }

        .view-button:hover {
            background-color: #e60073;
        }
    </style>
</head>
<body>
<h1>Список пацієнтів</h1>

<div class="patient-list">
    <c:forEach var="patient" items="${patients}">
        <div class="patient">
            <span>${patient.firstName} ${patient.lastName}</span>
            <a class="view-button" href="/patients/${patient.id}">Переглянути</a>
        </div>
    </c:forEach>
</div>
</body>
</html>
