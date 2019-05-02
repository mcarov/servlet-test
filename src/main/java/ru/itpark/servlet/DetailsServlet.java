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
import java.sql.SQLException;

public class DetailsServlet extends HttpServlet {
    private static final long serialVersionUID = -2106984404887698404L;

    private transient CarService carService;

    @Override
    public void init() {
        try {
            InitialContext context = new InitialContext();
            carService = (CarService) context.lookup("java:/comp/env/bean/car-service");
        }
        catch(NamingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            if(req.getPathInfo() != null) {
                String[] split = req.getPathInfo().split("/");
                if(split.length == 2) {
                    String id = split[1];
                    if(req.getParameterMap().size() == 6) {
                        carService.updateById(req.getParameter("model"),
                                req.getParameter("engine-power"),
                                req.getParameter("year"),
                                req.getParameter("color"),
                                req.getParameter("description"),
                                req.getParameter("image-url"), id);

                    }
                    Car car = carService.getById(id);
                    req.setAttribute("car", car);
                    req.getRequestDispatcher("/WEB-INF/details.jsp").forward(req, resp);
                }
            }
        }
        catch (SQLException | ServletException | IOException e) {
            e.printStackTrace();
        }
    }
}
