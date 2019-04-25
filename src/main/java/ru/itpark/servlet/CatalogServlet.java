package ru.itpark.servlet;

import ru.itpark.domain.Car;
import ru.itpark.service.CarService;
import ru.itpark.service.CsvFileService;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;

public class CatalogServlet extends HttpServlet {
    private static final long serialVersionUID = 5544322580192273147L;

    private transient CarService carService;
    private transient CsvFileService csvFileService;

    @Override
    public void init() {
        try {
            InitialContext context = new InitialContext();
            carService = (CarService) context.lookup("java:/comp/env/bean/car-service");
            csvFileService = (CsvFileService) context.lookup("java:/comp/env/bean/csv-file-service");
        }
        catch(NamingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Car> list = null;
        Map<String, String[]> map = req.getParameterMap();
        if(map.size() == 1) {
            if(map.containsKey("search")) {
                list = carService.search(req.getParameter("search"));
            }
            else if(map.containsKey("create")) {
                carService.create();
            }
            else if(map.containsKey("delete-all")) {
                carService.deleteAll();
            }
            else if(map.containsKey("delete")) {
                carService.deleteById(req.getParameter("delete"));
            }
        }

        if(map.size() == 2) {
            if(map.containsKey("import")) {
                csvFileService.importFromCsvFile(req.getParameter("file-path"));
            }
            else if(map.containsKey("export")) {
                csvFileService.exportToCsvFile(req.getParameter("file-path"));
            }
        }

        if(list == null) list = carService.getAll();
        req.setAttribute("cars", list);
        req.getRequestDispatcher("/WEB-INF/catalog.jsp").forward(req, resp);
    }
}