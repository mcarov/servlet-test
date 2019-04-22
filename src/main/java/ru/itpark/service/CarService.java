package ru.itpark.service;

import ru.itpark.domain.Car;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

public class CarService {
    private final DataSource source;

    public CarService() throws NamingException {
        InitialContext context = new InitialContext();
        source = (DataSource) context.lookup("java:comp/env/jdbc/db");
    }

    public List<Car> search(String request) {
        List<Car> cars = new ArrayList<>();
        try(Connection conn = source.getConnection();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM cars")) {

            Car testCar;
            while(resultSet.next()) {
                testCar = getCar(resultSet);
                if(testCar.hasRequestedValue(request))
                    cars.add(testCar);
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public void updateFromList(List<Car> newList) {
        Map<String, Car> map = new TreeMap<>();
        getAll().forEach(car -> map.put(car.getId(), car));
        newList.forEach(newCar -> map.put(newCar.getId(), newCar));
        deleteAll();
        try(Connection conn = source.getConnection();
            PreparedStatement statement =
                    conn.prepareStatement("INSERT INTO cars VALUES (?, ?, ?, ?, ?, ?, ?)")) {
            for(Car car : map.values()) {
                statement.setString(1, car.getId());
                statement.setString(2, car.getModel());
                statement.setString(3, car.getEnginePower());
                statement.setString(4, car.getYear());
                statement.setString(5, car.getColor());
                statement.setString(6, car.getDescription());
                statement.setString(7, car.getImageUrl());
                statement.execute();
            }
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void create() {
        try(Connection conn = source.getConnection();
            PreparedStatement statement =
                    conn.prepareStatement("INSERT INTO cars (model, enginePower, year, color, description, imageUrl)" +
                            " VALUES (?, ?, ?, ?, ?, ?)")) {

            statement.setString(1, "");
            statement.setString(2, "");
            statement.setString(3, "");
            statement.setString(4, "");
            statement.setString(5, "");
            statement.setString(6, "");
            statement.execute();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Car> getAll() {
        List<Car> cars = new ArrayList<>();
        try(Connection conn = source.getConnection();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM cars")) {

            while(resultSet.next()) {
                cars.add(getCar(resultSet));
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    public Car getById(String id) {
        Car car = null;
        try(Connection conn = source.getConnection();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM cars WHERE id="+id)){

            resultSet.next();
            car = getCar(resultSet);
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
        return car;
    }

    public void updateById(String id, String model, String enginePower, String year,
                              String color, String description, String imageUrl) {
        try(Connection conn = source.getConnection()){
            PreparedStatement prepStatement = conn.prepareStatement(
                    "UPDATE cars SET model=?, enginePower=?, year=?, color=?, description=?, imageUrl=? WHERE id=?");
            prepStatement.setString(1, model);
            prepStatement.setString(2, enginePower);
            prepStatement.setString(3, year);
            prepStatement.setString(4, color);
            prepStatement.setString(5, description);
            prepStatement.setString(6, imageUrl);
            prepStatement.setString(7, id);
            prepStatement.execute();
        }
        catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAll() {
        try(Connection conn = source.getConnection();
            Statement statement = conn.createStatement()) {
            statement.execute("DELETE FROM cars");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteById(String id) {
        try(Connection conn = source.getConnection();
            Statement statement = conn.createStatement()) {
            statement.execute("DELETE FROM cars WHERE id="+id);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Car getCar(ResultSet rs) throws SQLException {
        String id = rs.getString(1);
        String model = rs.getString(2);
        String enginePower = rs.getString(3);
        String year = rs.getString(4);
        String color = rs.getString(5);
        String description = rs.getString(6);
        String imageUrl = rs.getString(7);
        return new Car(id, model, enginePower, year, color, description, imageUrl);
    }
}
