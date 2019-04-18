package ru.itpark.servlet;

import ru.itpark.domain.Car;
import ru.itpark.service.CarService;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class Servlet extends HttpServlet {
    private CarService service;

    @Override
    public void init() {
        try {
            InitialContext context = new InitialContext();
            service = (CarService) context.lookup("java:/comp/env/bean/car-service");
        }
        catch(NamingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getPathInfo() != null) {
            String[] split = req.getPathInfo().split("/");
            if(split.length == 2) {
                String id = split[1];
                Car car = service.getById(id);
                req.setAttribute("car", car);
                req.getRequestDispatcher("/WEB-INF/details.jsp").forward(req, resp);
                return;
            }
        }

        List<Car> list = service.getAll();
        req.setAttribute("cars", list);
        req.getRequestDispatcher("/WEB-INF/catalog.jsp").forward(req, resp);
    }
}
