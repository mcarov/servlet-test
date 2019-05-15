<%@ page import="ru.itpark.domain.Car" %>
<%@ page import="org.apache.commons.validator.routines.UrlValidator" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <%@include file="bootstrap-css.jsp"%>

    <title>Details</title>
</head>
<body>
<div class="container-fluid">
    <br>
    <% if(request.getAttribute("car") != null) { %>
        <% Car car = (Car) request.getAttribute("car"); %>
        <div class="row">
            <div class="col-xl-3">
                <form action="<%=request.getContextPath()%>/details/<%=car.getId()%>">
                    <div class="form-group">
                        <label for="model">Model</label>
                        <input id="model" name="model" class="form-control" type="text" value="<%=car.getModel()%>">
                    </div>
                    <div class="form-group">
                        <label for="engine-power">Engine power (PS)</label>
                        <input id="engine-power" name="engine-power" class="form-control" type="text" value="<%=car.getEnginePower()%>">
                    </div>
                    <div class="form-group">
                        <label for="year">Year</label>
                        <input id="year" name="year" class="form-control" type="text" value="<%=car.getYear()%>">
                    </div>
                    <div class="form-group">
                        <label for="color">Color</label>
                        <input id="color" name="color" class="form-control" type="text" value="<%=car.getColor()%>">
                    </div>
                    <div class="form-group">
                        <label for="description">Description</label>
                        <input id="description" name="description" class="form-control" type="text" value="<%=car.getDescription()%>">
                    </div>
                    <div class="form-group">
                        <label for="image-url">Image URL</label>
                        <input id="image-url" name="image-url" class="form-control" type="text" value="<%=car.getImageUrl()%>">
                    </div>
                    <div align="right">
                        <button type="submit" class="btn btn-primary">Save changes</button>
                        <a class="btn btn-primary" href="<%=request.getContextPath()%>/">To catalog</a>
                    </div>
                </form>
                <form action="<%=request.getContextPath()%>/">
                    <div align="left">
                        <button name="delete" class="btn btn-danger" value="<%=car.getId()%>">Delete record</button>
                    </div>
                </form>
            </div>
            <div class="col-xl-7">
                <% UrlValidator validator = new UrlValidator();
                    if(validator.isValid(car.getImageUrl())) { %>
                    <img src="<%=car.getImageUrl()%>" class="card-img-top" alt="">
                <% }
                else { %>
                    <img src="<%=request.getContextPath()%>/image/<%=car.getImageUrl()%>" class="card-img-top" alt="">
                <% } %>
            </div>
        </div>
    <% } %>
</div>
</body>
</html>
