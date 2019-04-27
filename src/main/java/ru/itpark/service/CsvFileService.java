package ru.itpark.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import ru.itpark.domain.Car;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Part;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CsvFileService {
    private final String uploadPath;
    private final String csvFile;

    private CarService carService;
    private String[] headers = {"id", "model", "enginePower", "year", "color", "description", "imageUrl"};

    public CsvFileService() throws NamingException, IOException {
        csvFile = "cars_database.csv";
        uploadPath = System.getenv("UPLOAD_PATH");
        Files.createDirectories(Paths.get(uploadPath));

        InitialContext context = new InitialContext();
        carService = (CarService) context.lookup("java:/comp/env/bean/car-service");
    }

    public void importFromCsvFile(Part part) throws IOException, SQLException {
        String csvFileId = UUID.randomUUID().toString();
        part.write(Paths.get(uploadPath).resolve(csvFileId).toString());

        try(Reader reader = Files.newBufferedReader(Paths.get(uploadPath).resolve(csvFileId));
            CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.
                    withFirstRecordAsHeader().
                    withIgnoreHeaderCase().
                    withTrim())) {

            List<Car> csvList = new ArrayList<>();
            for(CSVRecord record : parser) {
                csvList.add(new Car(Integer.parseInt(record.get("id")),
                        record.get("model"),
                        Integer.parseInt(record.get("enginePower")),
                        Integer.parseInt(record.get("year")),
                        record.get("color"),
                        record.get("description"),
                        record.get("imageUrl")));
            }
            carService.updateFromList(csvList);
        }
    }

    public void exportToCsvFile(ServletOutputStream outputStream) throws IOException, SQLException {
        Path path = Paths.get(uploadPath).resolve(csvFile);
        try(Writer output = Files.newBufferedWriter(path);
            CSVPrinter printer = new CSVPrinter(output, CSVFormat.DEFAULT.withHeader(headers))) {

            for(Car car : carService.getAll()) {
                printer.printRecord(car.getId(),
                        car.getModel(),
                        car.getEnginePower(),
                        car.getYear(),
                        car.getColor(),
                        car.getDescription(),
                        car.getImageUrl());
            }
        }
        System.out.println(path.toFile().exists());
        Files.copy(path, outputStream);
    }
}
