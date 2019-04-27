package ru.itpark.service;

import ru.itpark.domain.Car;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class CarService {
    private final DataSource source;

    public CarService() throws NamingException {
        InitialContext context = new InitialContext();
        source = (DataSource) context.lookup("java:comp/env/jdbc/db");
        try(Connection conn = source.getConnection();
            Statement statement = conn.createStatement()) {

            statement.execute("CREATE TABLE IF NOT EXISTS cars (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "model TEXT," +
                    "enginePower INTEGER," +
                    "year INTEGER," +
                    "color TEXT," +
                    "description TEXT," +
                    "imageUrl TEXT)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Car> search(String request) throws SQLException {
        List<Car> cars = new ArrayList<>();
        try(Connection conn = source.getConnection();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id, model, enginePower, year, color, description, imageUrl FROM cars")) {

            while(resultSet.next()) {
                Car testCar = getCar(resultSet);
                if(testCar.hasRequestedValue(request))
                    cars.add(testCar);
            }
        }
        return cars;
    }

    public void updateFromList(List<Car> newList) throws SQLException {
        Map<Integer, Car> map = getAll().stream().collect(Collectors.toMap(Car::getId, c -> c));
        map.putAll(newList.stream().collect(Collectors.toMap(Car::getId, c -> c)));

        try(Connection conn = source.getConnection();
            PreparedStatement statement =
                    conn.prepareStatement("INSERT INTO cars (id, model, enginePower, year, color, description, imageUrl)" +
                            "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                            "ON CONFLICT(id) DO UPDATE SET model=?, enginePower=?, year=?, color=?, description=?, imageUrl=?")) {
            for(Car car : map.values()) {
                statement.setInt(1, car.getId());
                statement.setString(2, car.getModel());
                statement.setInt(3, car.getEnginePower());
                statement.setInt(4, car.getYear());
                statement.setString(5, car.getColor());
                statement.setString(6, car.getDescription());
                statement.setString(7, car.getImageUrl());
                statement.setString(8, car.getModel());
                statement.setInt(9, car.getEnginePower());
                statement.setInt(10, car.getYear());
                statement.setString(11, car.getColor());
                statement.setString(12, car.getDescription());
                statement.setString(13, car.getImageUrl());
                statement.execute();
            }
        }
    }

    public void create(String model, String description, String imageId) throws SQLException {
        try(Connection conn = source.getConnection();
            PreparedStatement statement = conn.prepareStatement(
                    "INSERT INTO cars (model, enginePower, year, color, description, imageUrl) VALUES (?, ?, ?, ?, ?, ?)")) {

            statement.setString(1, model);
            statement.setInt(2, 0);
            statement.setInt(3, 0);
            statement.setString(4, "");
            statement.setString(5, description);
            statement.setString(6, imageId);
            statement.execute();
        }
    }

    public List<Car> getAll() throws SQLException {
        List<Car> cars = new ArrayList<>();
        try(Connection conn = source.getConnection();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT id, model, enginePower, year, color, description, imageUrl FROM cars")) {

            while(resultSet.next()) {
                cars.add(getCar(resultSet));
            }
        }
        return cars;
    }

    public Car getById(String id) throws SQLException {
        Car car = null;
        try(Connection conn = source.getConnection();
            PreparedStatement statement = conn.prepareStatement(
                    "SELECT id, model, enginePower, year, color, description, imageUrl FROM cars WHERE id=?")){

            statement.setInt(1, Integer.parseInt(id));
            try(ResultSet resultSet = statement.executeQuery()) {
                if(resultSet.next()) car = getCar(resultSet);
            }
        }
        return car;
    }

    public void updateById(String id, String model, String enginePower,
                           String year, String color, String description, String imageUrl) throws SQLException {
        try(Connection conn = source.getConnection();
            PreparedStatement statement = conn.prepareStatement(
                    "UPDATE cars SET model=?, enginePower=?, year=?, color=?, description=?, imageUrl=? WHERE id=?")) {

            statement.setString(1, model);
            statement.setInt(2, Integer.parseInt(enginePower));
            statement.setInt(3, Integer.parseInt(year));
            statement.setString(4, color);
            statement.setString(5, description);
            statement.setString(6, imageUrl);
            statement.setInt(7, Integer.parseInt(id));
            statement.execute();
        }
    }

    public void deleteAll() throws SQLException {
        try(Connection conn = source.getConnection();
            Statement statement = conn.createStatement()) {

            statement.execute("DELETE FROM cars");
        }
    }

    public void deleteById(String id) throws SQLException {
        try(Connection conn = source.getConnection();
            PreparedStatement statement = conn.prepareStatement("DELETE FROM cars WHERE id=?")) {

            statement.setString(1, id);
            statement.execute();
        }
    }

    private Car getCar(ResultSet rs) throws SQLException {
        int id = rs.getInt(1);
        String model = rs.getString(2);
        int enginePower = rs.getInt(3);
        int year = rs.getInt(4);
        String color = rs.getString(5);
        String description = rs.getString(6);
        String imageUrl = rs.getString(7);
        return new Car(id, model, enginePower, year, color, description, imageUrl);
    }
}
