<%@ page import="ru.itpark.domain.Car" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <title>Сar catalog</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <% if(request.getAttribute("car") != null) { %>
    <%     Car car = (Car) request.getAttribute("car");%>
    <div class="row">
        <div class="col-xl-8">
            <img src="<%=car.getImage()%>" class="img-fluid" alt="">
        </div>
        <div class="col-xl-4">
            <ul>
                <li><h5>Производитель</h5><%=car.getManufacturer()%></li>
                <li><h5>Модель</h5><%=car.getModel()%></li>
                <li><h5>Мощность двигателя</h5><%=car.getEnginePower()%></li>
                <li><h5>Год выпуска</h5><%=car.getYear()%></li>
                <li><h5>Цвет</h5><%=car.getColor()%></li>
            </ul>
            <br><br>
            <a href="<%=request.getContextPath()%>/catalog/" class="btn btn-primary">Catalog</a>
        </div>
    </div>
    <% }%>
</div>
</body>
</html>
