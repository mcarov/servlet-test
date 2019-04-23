<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <title>Start page</title>
</head>
<body>
    <div class="container">
        <br><br><br><br>
        <h3 class="text-center">Welcome to your workplace!</h3>
        <br>
        <div class="col text-center">
            <a class="btn btn-primary" href="<%=request.getContextPath()%>/catalog/">Enter</a>
        </div>
    </div>
</body>
</html>
