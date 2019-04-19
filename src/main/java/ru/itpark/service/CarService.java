package ru.itpark.service;

import ru.itpark.domain.Car;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarService {
    private final DataSource source;

    public CarService() throws NamingException {
        InitialContext context = new InitialContext();
        source = (DataSource) context.lookup("java:comp/env/jdbc/db");
    }

    public boolean create() {
        boolean execute = false;
        try(Connection conn = source.getConnection();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM cars")) {

            resultSet.next();
            String id = resultSet.getString(1);
            execute = statement.execute("INSERT INTO cars id VALUES "+id);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return execute;
    }

    public boolean create(String model, String enginePower, String year,
                          String color, String description, String imageUrl) {
        boolean execute = false;
        try(Connection conn = source.getConnection();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM cars")) {

            resultSet.next();
            String id = resultSet.getString(1);

            PreparedStatement prepStatement = conn.prepareStatement("INSERT INTO cars VALUES (?, ?, ?, ?, ?, ?, ?)");
            prepStatement.setString(1, id);
            prepStatement.setString(2, model);
            prepStatement.setString(3, enginePower);
            prepStatement.setString(4, year);
            prepStatement.setString(5, color);
            prepStatement.setString(6, description);
            prepStatement.setString(7, imageUrl);
            execute = prepStatement.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return execute;
    }

    public List<Car> getAll() {
        List<Car> cars = new ArrayList<>();
        try(Connection conn = source.getConnection();
            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM cars")) {

            while(resultSet.next()) {
                String id = resultSet.getString(1);
                String model = resultSet.getString(2);
                String enginePower = resultSet.getString(3);
                String year = resultSet.getString(4);
                String color = resultSet.getString(5);
                String description = resultSet.getString(6);
                String imageUrl = resultSet.getString(7);
                cars.add(new Car(id, model, enginePower, year, color, description, imageUrl));
            }
            return cars;

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
            String model = resultSet.getString(2);
            String enginePower = resultSet.getString(3);
            String year = resultSet.getString(4);
            String color = resultSet.getString(5);
            String description = resultSet.getString(6);
            String imageUrl = resultSet.getString(7);
            car = new Car(id, model, enginePower, year, color, description, imageUrl);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return car;
    }

    public boolean updateById(String id, String model, String enginePower, String year,
                              String color, String description, String imageUrl) {
        boolean execute = false;
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
            execute = prepStatement.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return execute;
    }

    public boolean deleteAll() {
        boolean execute = false;
        try(Connection conn = source.getConnection();
            Statement statement = conn.createStatement()) {
            execute = statement.execute("DELETE FROM cars");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return execute;
    }

    public boolean deleteById(String id) {
        boolean execute = false;
        try(Connection conn = source.getConnection();
            Statement statement = conn.createStatement()) {
            execute = statement.execute("DELETE FROM cars WHERE id="+id);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return execute;
    }
}
