<%@ page import="ru.itpark.domain.Car" %>
<%@ page import="java.util.List" %>
<%@ page import="org.apache.commons.validator.routines.UrlValidator" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <%@include file="bootstrap-css.jsp"%>

    <title>Сar catalog</title>
</head>
<body>
    <div class="container-fluid">
        <h1 class="text-center">Car catalog</h1>
        <br>
        <div class="row">
            <div class="col-lg-3" style="background-color: #FFFFFF">
                <form role="search" action="<%=request.getContextPath()%>">
                    <div class="input-group">
                        <input type="text" name="search" class="form-control" placeholder="Search for...">
                        <button type="submit" class="btn btn-primary">Go!</button>
                    </div>
                </form>
                <br>
                <form action="<%=request.getContextPath()%>/" method="post" enctype="multipart/form-data">
                    <div class="custom-file">
                        <label class="custom-file-label" for="csv-file">Choose csv file...</label>
                        <input type="file" class="custom-file-input" id="csv-file" name="csv-file" accept="text/csv" required>
                    </div>
                    <button type="submit" name="import" class="btn btn-primary btn-block">Import from CSV</button>
                </form>
                <br>
                <form action="<%=request.getContextPath()%>/" method="post" enctype="multipart/form-data">
                    <button type="submit" name="export" class="btn btn-primary btn-block">Export to CSV</button>
                </form>
                <br>
                <form action="<%=request.getContextPath()%>" method="post" enctype="multipart/form-data">
                    <div class="form-group">
                        <label for="model">Model</label>
                        <input id="model" name="model" class="form-control" type="text" required>
                    </div>
                    <div class="form-group">
                        <label for="description">Description</label>
                        <input id="description" name="description" class="form-control" type="text" required>
                    </div>
                    <br>
                    <div class="custom-file">
                        <label class="custom-file-label" for="image">Choose image...</label>
                        <input type="file" id="image" name="image" class="custom-file-input" accept="image/*" required>
                    </div>
                    <br><br><br>
                    <button type="submit" class="btn btn-primary btn-block">Create new record</button>
                </form>
                <br>
                <form action="<%=request.getContextPath()%>">
                    <button name="delete-all" class="btn btn-danger btn-block">Delete all records</button>
                </form>
            </div>
            <div class="col-lg-9">
                <div class="card-columns">
                    <% if(request.getAttribute("cars") != null) { %>
                    <% for (Car car : (List<Car>)request.getAttribute("cars")) { %>
                        <div class="card">
                            <% UrlValidator validator = new UrlValidator();
                            if(validator.isValid(car.getImageUrl())) { %>
                                <img src="<%=car.getImageUrl()%>" class="card-img-top" alt="" style="max-height:250px">
                            <% }
                            else { %>
                                <img src="<%=request.getContextPath()%>/image/<%=car.getImageUrl()%>" class="card-img-top" alt="" style="max-height:250px">
                            <% } %>
                            <div class="card-body">
                                <h5 class="card-title"><%=car.getModel()%></h5>
                                <p><%=car.getDescription()%></p>
                                <a class="btn btn-primary" href="<%=request.getContextPath()%>/details/<%=car.getId()%>">Details</a>
                            </div>
                        </div>
                    <% } %>
                    <% } %>
                </div>
            </div>
        </div>
    </div>
    <%@include file="bootstrap-scripts.jsp"%>
</body>
</html>
