package dao.impl;

import dao.CustomerDao;
import domain.Customer;
import util.DataBaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomerDaoImpl implements CustomerDao {

    private final String initTable = "CREATE TABLE IF NOT EXISTS CUSTOMER (" +
            "ID INTEGER PRIMARY KEY AUTO_INCREMENT, " +
            "NAME VARCHAR(255) UNIQUE NOT NULL," +
            "RENTED_CAR_ID INTEGER," +
            "FOREIGN KEY (RENTED_CAR_ID) REFERENCES CAR (ID)" +
            ");";

    private final String addCustomer = "INSERT INTO CUSTOMER (NAME) VALUES (?);";

    private final String selectAll = "SELECT NAME, ID, RENTED_CAR_ID  FROM CUSTOMER;";

    private final String update = "UPDATE CUSTOMER SET RENTED_CAR_ID = ? WHERE NAME = ?;";

    private final String delete = "UPDATE CUSTOMER SET RENTED_CAR_ID = NULL WHERE NAME = ?;";

    private final String getAllRentedCarIds = "SELECT RENTED_CAR_ID FROM CUSTOMER WHERE RENTED_CAR_ID IS NOT NULL;";

    public CustomerDaoImpl() {
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
    public void save(Customer customer) {
        try (Connection connection = DataBaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(addCustomer)) {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<Customer> getAll() {
        List<Customer> companies = new ArrayList<>();
        try (
                Connection connection = DataBaseUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(selectAll);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            while (resultSet.next()) {
                companies.add(new Customer(
                        resultSet.getString("NAME"),
                        resultSet.getInt("ID"),
                        null,
                        resultSet.getInt("RENTED_CAR_ID")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return companies;
    }

    @Override
    public void update(Customer customer) {
        try (Connection connection = DataBaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(update)) {
            preparedStatement.setInt(1, customer.getCar().getId());
            preparedStatement.setString(2, customer.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Customer customer) {
        try (Connection connection = DataBaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(delete)) {
            preparedStatement.setString(1, customer.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<Integer> getRentedCarIds() {
        Set<Integer> rentedCarIds = new HashSet<>();
        try (
                Connection connection = DataBaseUtil.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(getAllRentedCarIds);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            while (resultSet.next()) {
                rentedCarIds.add(resultSet.getInt("RENTED_CAR_ID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rentedCarIds;
    }

}
