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
import java.util.Map;

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
                if(req.getParameterMap().size() == 6) {
                   service.updateById(id,
                           req.getParameter("model"),
                           req.getParameter("enginePower"),
                           req.getParameter("year"),
                           req.getParameter("color"),
                           req.getParameter("description"),
                           req.getParameter("imageUrl"));
                }
                Car car = service.getById(id);
                req.setAttribute("car", car);
                req.getRequestDispatcher("/WEB-INF/details.jsp").forward(req, resp);
                return;
            }
        }

        List<Car> list = null;
        if(req.getParameterMap().size() == 1) {
            Map<String, String[]> map = req.getParameterMap();
            if(map.containsKey("search")) {
                list = service.search(req.getParameter("search"));
            }
            else if(map.containsKey("create")) {
                service.create();
            }
            else if(map.containsKey("deleteAll")) {

            }
            else if(map.containsKey("import")) {

            }
            else if(map.containsKey("export")) {

            }
            else if(map.containsKey("delete")) {
                service.deleteById(req.getParameter("delete"));
            }
        }

        if(list == null) list = service.getAll();
        req.setAttribute("cars", list);
        req.getRequestDispatcher("/WEB-INF/catalog.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    private void exportToCsv() {

    }
}