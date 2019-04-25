package ru.itpark.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import ru.itpark.domain.Car;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvFileService {
    private CarService carService;
    private String[] headers = {"id", "model", "enginePower", "year", "color", "description", "imageUrl"};

    public CsvFileService() throws NamingException {
        InitialContext context = new InitialContext();
        carService = (CarService) context.lookup("java:/comp/env/bean/car-service");
    }

    public void importFromCsvFile(String filePath)  {
        try(Reader reader = Files.newBufferedReader(Paths.get(filePath));
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
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void exportToCsvFile(String filePath) {
        try(Writer output = Files.newBufferedWriter(Paths.get(filePath));
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
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
