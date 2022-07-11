package dao.impl;

import dao.CarDao;
import domain.Car;
import domain.Company;
import util.DataBaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CarDaoImpl implements CarDao {
    private final String initTable = "CREATE TABLE IF NOT EXISTS CAR (" +
            "ID INTEGER PRIMARY KEY AUTO_INCREMENT, " +
            "NAME VARCHAR(255) UNIQUE NOT NULL," +
            "COMPANY_ID INTEGER NOT NULL," +
            "FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY (ID)" +
            ");";

    private final String addCar = "INSERT INTO CAR (NAME) VALUES (?);";

    private final String selectAll = "SELECT * FROM CAR;";

    private final String deleteAll = "DROP TABLE CAR;";

    public CarDaoImpl() {
        createTable();
    }

    private void createTable() {
        try (Connection connection = DataBaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(initTable)) {
            connection.setAutoCommit(true);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void save(Car car) {
        try (Connection connection = DataBaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(addCar)) {
            preparedStatement.setString(1, car.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteAll() {
        try (Connection connection = DataBaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteAll)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printAll() {
        try (Connection connection = DataBaseUtil.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(selectAll);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println("id: " + resultSet.getString("ID"));
                System.out.println("car name: " + resultSet.getString("NAME"));
                System.out.println("company_id: " + resultSet.getString("COMPANY_ID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
