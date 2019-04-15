package ru.itpark.service;

import ru.itpark.domain.Car;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CarService {
    private final DataSource source;

    public CarService() throws NamingException {
        InitialContext context = new InitialContext();
        source = (DataSource) context.lookup("java:comp/env/jdbc/db");
    }

    public List<Car> getAll() {
        List<Car> cars = new ArrayList<>();
        try(Connection conn = source.getConnection();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM cars ORDER BY id");) {
            while(resultSet.next()) {
                String id = resultSet.getString(1);
                String manufacturer = resultSet.getString(2);
                String model = resultSet.getString(3);
                String enginePower = resultSet.getString(4);
                String year = resultSet.getString(5);
                String color = resultSet.getString(6);
                String image = resultSet.getString(7);

                cars.add(new Car(id, manufacturer, model, enginePower, year, color, image));
            }
            return cars;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public Car getById() {
        return new Car();
    }
}
