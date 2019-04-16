<%@ page import="ru.itpark.domain.Car" %>
<%@ page import="java.util.List" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Catalog of cars</title>
</head>
<body>
    <% if(request.getAttribute("cars") != null) { %>
        <% for (Car item : (List<Car>)request.getAttribute("cars")) { %>
            <%=String.format("%10s%50s%50s", item.getId(), item.getManufacturer(), item.getModel())%>
            <br>
    <% } %>
    <% } %>
</body>
</html>
