<%@ page import="ru.itpark.domain.Car" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <!-- Optional JavaScript -->
    <!-- jQuery first, then Popper.js, then Bootstrap JS -->
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
            integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
            integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
            integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>

    <title>Details</title>
</head>
<body>
<div class="container-fluid">
    <br>
    <% if(request.getAttribute("car") != null) { %>
    <% Car car = (Car) request.getAttribute("car");%>
    <div class="row">
        <div class="col-xl-3">
            <form action="<%=request.getContextPath()%>/catalog/<%=car.getId()%>">
                <div class="form-group">
                    <label for="model">Model</label>
                    <input id="model" name="model" class="form-control" type="text" value="<%=car.getModel()%>">
                </div>
                <div class="form-group">
                    <label for="enginePower">Engine power (PS)</label>
                    <input id="enginePower" name="enginePower" class="form-control" type="text" value="<%=car.getEnginePower()%>">
                </div>
                <div class="form-group">
                    <label for="year">Year</label>
                    <input id="year" name="year" class="form-control" type="text" value="<%=car.getYear()%>">
                </div>
                <div class="form-group">
                    <label for="name">Color</label>
                    <input id="name" name="color" class="form-control" type="text" value="<%=car.getColor()%>">
                </div>
                <div class="form-group">
                    <label for="description">Description</label>
                    <input id="description" name="description" class="form-control" type="text" value="<%=car.getDescription()%>">
                </div>
                <div class="form-group">
                    <label for="imageUrl">Image URL</label>
                    <input id="imageUrl" name="imageUrl" class="form-control" type="text" value="<%=car.getImageUrl()%>">
                </div>
                <div align="right">
                    <button type="submit" class="btn btn-primary">Save changes</button>
                    <a class="btn btn-primary" href="<%=request.getContextPath()%>/catalog/">To catalog</a>
                </div>
            </form>
            <form action="<%=request.getContextPath()%>/catalog/">
                <div align="left">
                    <button name="delete" class="btn btn-danger" value="<%=car.getId()%>">Delete record</button>
                </div>
            </form>
        </div>
        <div class="col-xl-7">
            <img src="<%=car.getImageUrl()%>" class="img-fluid" alt="">
        </div>
    </div>
    <% }%>
</div>
</body>
</html>
