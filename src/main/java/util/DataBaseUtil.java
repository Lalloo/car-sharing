package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DataBaseUtil {
    private DataBaseUtil() {
    }

    private static String DB_URL = "jdbc:h2:D:./src/carsharing/db/carsharing";
    private static String USER = "sa";
    private static String PASSWORD = "sa";


    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

}
