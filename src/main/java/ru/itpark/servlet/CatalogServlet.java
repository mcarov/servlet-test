package ru.itpark.servlet;

import ru.itpark.domain.Car;
import ru.itpark.service.CarService;
import ru.itpark.service.CsvFileService;
import ru.itpark.service.ImageService;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class CatalogServlet extends HttpServlet {
    private static final long serialVersionUID = 5544322580192273147L;

    private transient CarService carService;
    private transient CsvFileService csvFileService;
    private transient ImageService imageService;

    @Override
    public void init() {
        try {
            InitialContext context = new InitialContext();
            carService = (CarService) context.lookup("java:/comp/env/bean/car-service");
            csvFileService = (CsvFileService) context.lookup("java:/comp/env/bean/csv-file-service");
            imageService = (ImageService) context.lookup("java:/comp/env/bean/image-service");
        }
        catch(NamingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            List<Car> list = null;
            Map<String, String[]> map = req.getParameterMap();
            if(map.size() == 1) {
                if(map.containsKey("search")) {
                    list = carService.search(req.getParameter("search"));
                }
                else if(map.containsKey("delete-all")) {
                    carService.deleteAll();
                }
                else if(map.containsKey("delete")) {
                    carService.deleteById(req.getParameter("delete"));
                }
            }

            if(list == null) list = carService.getAll();
            req.setAttribute("cars", list);
            req.getRequestDispatcher("/WEB-INF/catalog.jsp").forward(req, resp);
        }
        catch (SQLException | ServletException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Map<String, String[]> map = req.getParameterMap();
            if(map.containsKey("import")) {
                Part part = req.getPart("csv-file");
                csvFileService.importFromCsvFile(part);
                resp.sendRedirect(String.join("/", req.getContextPath(), req.getServletPath()));
            }
            else if(map.containsKey("export")) {
                resp.setContentType("text/csv");
                resp.setHeader("Content-disposition", "attachment; filename=car_database.csv");
                csvFileService.exportToCsvFile(resp.getOutputStream());
            }
            else {
                String model = req.getParameter("model");
                String description = req.getParameter("description");
                Part part = req.getPart("image");

                String imageId = imageService.writeImage(part);
                carService.create(model, description, imageId);
                resp.sendRedirect(String.join("/", req.getContextPath(), req.getServletPath()));
            }
        }
        catch (SQLException | IOException | ServletException e) {
            e.printStackTrace();
        }
    }
}