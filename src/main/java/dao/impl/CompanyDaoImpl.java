package dao.impl;

import carsharing.dao.CompanyDao;
import util.DataBaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CompanyDaoImpl implements CompanyDao {

    private final String initTable = "CREATE TABLE COMPANY (" +
            "ID INTEGER PRIMARY KEY, " +
            "NAME VARCHAR(255) " +
            ");";

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

}
