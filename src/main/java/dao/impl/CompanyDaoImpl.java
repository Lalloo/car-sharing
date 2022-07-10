package dao.impl;

import dao.CompanyDao;
import domain.Company;
import util.DataBaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CompanyDaoImpl implements CompanyDao {

    private final String initTable = "CREATE TABLE IF NOT EXISTS COMPANY (" +
            "ID INTEGER PRIMARY KEY AUTO_INCREMENT, " +
            "NAME VARCHAR(255) UNIQUE NOT NULL" +
            ");";

    private final String addCompany = "INSERT INTO card(NAME) VALUES (?);";

    public CompanyDaoImpl() {
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
    public void save(Company company) {
        try (Connection connection = DataBaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(addCompany)) {
            preparedStatement.setString(1, company.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
