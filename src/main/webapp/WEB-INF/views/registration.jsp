<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Реєстрація</title>
    <style>
        body {
            background-color: #ffe6f0;
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 40px 20px;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }

        form {
            background-color: #fff0f5;
            padding: 30px 40px;
            border-radius: 15px;
            box-shadow: 0 0 15px rgba(204, 0, 102, 0.3);
            display: flex;
            flex-direction: column;
            gap: 20px;
            width: 320px;
        }

        h1 {
            text-align: center;
            color: #cc0066;
            margin-bottom: 20px;
        }

        label {
            font-weight: bold;
            color: #cc0066;
            display: flex;
            flex-direction: column;
            font-size: 14px;
        }

        input, select {
            margin-top: 6px;
            padding: 10px;
            border-radius: 8px;
            border: 1px solid #ffcce0;
            font-size: 14px;
            transition: border-color 0.3s ease;
        }

        input:focus, select:focus {
            outline: none;
            border-color: #e60073;
        }

        button {
            background-color: #ff66a3;
            color: white;
            border: none;
            padding: 12px 0;
            border-radius: 8px;
            font-size: 16px;
            cursor: pointer;
            font-weight: bold;
            transition: background-color 0.3s ease;
        }

        button:hover {
            background-color: #e60073;
        }

        .error {
            color: #cc0033;
            text-align: center;
            font-weight: bold;
            margin-top: 10px;
        }
    </style>
</head>
<body>

<form method="post" action="${pageContext.request.contextPath}/reg">
    <h1>Реєстрація</h1>

    <label>ПІБ:
        <input id="fullName" type="text" name="fullName" placeholder="Батьків Батько Батькович" required minlength="5"/>
    </label>

    <label>Логін:
        <input type="text" name="login" placeholder="login" required/>
    </label>

    <label>Пароль:
        <input id="password" type="password" name="password" placeholder="password" required minlength="1" maxlength="20"/>
    </label>

    <label>Роль:
        <select name="role" required>
            <option value="">-- Оберіть роль --</option>
            <option value="DOCTOR">DOCTOR</option>
            <option value="NURSE">NURSE</option>
            <option value="USER">USER</option>
        </select>
    </label>

    <button type="submit">Зареєструватися</button>

    <c:if test="${requestScope.error != null}">
        <p class="error">${requestScope.error}</p>
    </c:if>
</form>

</body>
</html>
