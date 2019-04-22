<%@ page import="ru.itpark.domain.Car" %>
<%@ page import="java.util.List" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

    <title>Сar catalog</title>
</head>
<body>
    <div class="container-fluid">
        <h1 class="text-center">Car catalog</h1>
        <br>
        <div class="row">
            <div class="col-lg-3" style="background-color: #FFFFFF">
                <form role="search" action="<%=request.getContextPath()%>/catalog/">
                    <div class="input-group">
                        <input type="text" name="search" class="form-control" placeholder="Search for">
                        <button type="submit" class="btn btn-primary">Go!</button>
                    </div>
                </form>
                <br>
                <form action="<%=request.getContextPath()%>/catalog/">
                    <button name="create" class="btn btn-primary btn-block">Create new record</button>
                    <br>
                    <button name="deleteAll" class="btn btn-danger btn-block"
                            data-toggle="modal" data-target="#deletingModal">Delete all records</button>
                    <br>
                    <h5 class="text-center">CSV</h5>
                </form>
                <form action="<%= request.getContextPath() %>/catalog/">
                    <div class="input-group">
                        <input type="text" id="filePath" name="filePath" placeholder="File path" class="form-control">
                        <div class="btn-group btn-block">
                            <button type="submit" name="import" class="btn btn-primary">Import</button>
                            <button type="submit" name="export" class="btn btn-primary">Export</button>
                        </div>
                    </div>
                </form>
            </div>
            <div class="col-lg-9">
                <div class="card-columns">
                    <% if(request.getAttribute("cars") != null) { %>
                    <% for (Car item : (List<Car>)request.getAttribute("cars")) { %>
                        <div class="card">
                            <img src="<%=item.getImageUrl()%>" class="card-img-top" alt="" style="max-height:250px">
                            <div class="card-body">
                                <h5 class="card-title"><%=item.getModel()%></h5>
                                <p><%=item.getDescription()%></p>
                                <a class="btn btn-primary" href="<%=request.getContextPath()%>/catalog/<%=item.getId()%>">Details</a>
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
