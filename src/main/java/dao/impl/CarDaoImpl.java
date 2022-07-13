package dao.impl;

import dao.CarDao;
import dao.CompanyDao;
import domain.Car;
import domain.Company;
import domain.Customer;
import util.DataBaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CarDaoImpl implements CarDao {
    private final String initTable = "CREATE TABLE IF NOT EXISTS CAR (" +
            "ID INTEGER PRIMARY KEY AUTO_INCREMENT, " +
            "NAME VARCHAR(255) UNIQUE NOT NULL," +
            "COMPANY_ID INTEGER NOT NULL," +
            "FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY (ID)" +
            ");";

    private final String addCar = "INSERT INTO CAR (NAME, COMPANY_ID) VALUES (?, ?);";

    private final String selectAll = "SELECT * FROM CAR WHERE COMPANY_ID = (?);";

    private final String getCarName = "SELECT NAME, COMPANY_ID FROM CAR WHERE ID = (?);";


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
            preparedStatement.setInt(2, car.getCompany().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<Car> getAllByCompany(Company company) {
        List<Car> cars = new ArrayList<>();
        try (
                Connection connection = DataBaseUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(selectAll)
        ) {
            preparedStatement.setInt(1, company.getId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    cars.add(new Car(
                            resultSet.getInt("ID"),
                            resultSet.getString("NAME"),
                            company
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    @Override
    public Car get(Customer customer) {
        Car car = null;
        try (Connection connection = DataBaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getCarName)) {
            preparedStatement.setInt(1, customer.getRentCarId());
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    car = new Car(
                            resultSet.getString("NAME"),
                            resultSet.getInt("COMPANY_ID")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return car;
    }
}
