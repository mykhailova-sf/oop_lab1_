<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Головна сторінка</title>
    <style>
        body {
            background-color: #ffe6f0;
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 40px;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            flex-direction: column;
        }

        h1 {
            color: #cc0066;
            margin-bottom: 40px;
        }

        .btn {
            background-color: #ff66a3;
            border: none;
            color: white;
            padding: 12px 24px;
            margin: 10px;
            border-radius: 8px;
            cursor: pointer;
            font-size: 18px;
            font-weight: bold;
            text-decoration: none;
            display: inline-block;
            transition: background-color 0.3s ease;
        }

        .btn:hover {
            background-color: #e60073;
        }
    </style>
</head>
<body>

<h1>Ласкаво просимо</h1>

<a href="/reg" class="btn">Реєстрація</a>
<a href="/login" class="btn">Вхід</a>
<a href="/appointments" class="btn">Призначення</a>

</body>
</html>
