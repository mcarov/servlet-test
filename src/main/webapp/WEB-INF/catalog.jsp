<%@ page import="ru.itpark.domain.Car" %>
<%@ page import="java.util.List" %>

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
    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-3" style="background-color: #FFFFFF">
                <form action="#" role="search">
                    <div class="input-group">
                        <input type="text" name="query" class="form-control" placeholder="What needs to be found?" value="">
                        <span class="input-group-btn">
                            <button type="submit" class="btn btn-primary">Search</button>
                        </span>
                    </div>
                </form>
            </div>
            <div class="col-lg-9">
                <div class="card-columns">
                    <% if(request.getAttribute("cars") != null) { %>
                    <% for (Car item : (List<Car>)request.getAttribute("cars")) { %>
                        <div class="card">
                            <img src="<%=item.getImage()%>" class="card-img-top" alt="">
                            <div class="card-body">
                                <h5 class="card-title"><%=item.getManufacturer()%> <%=item.getModel()%></h5>
                                <a href="<%=request.getContextPath()%>/catalog/<%= item.getId()%>" class="btn btn-primary">Details</a>
                            </div>
                        </div>
                    <% } %>
                    <% } %>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
