package ru.itpark.servlet;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import ru.itpark.domain.Car;
import ru.itpark.service.CarService;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class Servlet extends HttpServlet {
    private CarService service;
    private String[] headers = {"id", "model", "enginePower", "year", "color", "description", "imageUrl"};

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
        Map<String, String[]> map = req.getParameterMap();
        if(map.size() == 1) {
            if(map.containsKey("search")) {
                list = service.search(req.getParameter("search"));
            }
            else if(map.containsKey("create")) {
                service.create();
            }
            else if(map.containsKey("deleteAll")) {

            }
            else if(map.containsKey("delete")) {
                service.deleteById(req.getParameter("delete"));
            }
        }
        else if(map.size() == 2) {
            if(map.containsKey("import")) {
                //System.out.println("Import from: "+req.getParameter("filePath"));
                importFromCsv(req.getParameter("filePath"));
            }
            else if(map.containsKey("export")) {
                //System.out.println("Export to: "+req.getParameter("filePath"));
                exportToCsv(req.getParameter("filePath"));
            }
        }

        if(list == null) list = service.getAll();
        req.setAttribute("cars", list);
        req.getRequestDispatcher("/WEB-INF/catalog.jsp").forward(req, resp);
    }

    private void importFromCsv(String filePath)  {
        try(Reader reader = Files.newBufferedReader(Paths.get(filePath));
            CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.
                    withFirstRecordAsHeader().
                    withIgnoreHeaderCase().
                    withTrim())) {
            for(CSVRecord record : parser) {
                if(service.getById(record.get("id")) != null) {
                    service.updateById(record.get("id"),
                            record.get("model"),
                            record.get("enginePower"),
                            record.get("year"),
                            record.get("color"),
                            record.get("description"),
                            record.get("imageUrl"));
                }
                else {
                    service.create(record.get("id"),
                            record.get("model"),
                            record.get("enginePower"),
                            record.get("year"),
                            record.get("color"),
                            record.get("description"),
                            record.get("imageUrl"));
                }
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void exportToCsv(String filePath) {
        try(Writer output = Files.newBufferedWriter(Paths.get(filePath));
            CSVPrinter printer = new CSVPrinter(output, CSVFormat.DEFAULT.withHeader(headers))) {

            for(Car car : service.getAll()) {
                printer.printRecord(car.getId(),
                        car.getModel(),
                        car.getEnginePower(),
                        car.getYear(),
                        car.getColor(),
                        car.getDescription(),
                        car.getImageUrl());
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}