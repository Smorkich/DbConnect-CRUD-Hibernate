package jm.task.core.jdbc.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static Connection connection;
    private static final String url = "jdbc:mysql://localhost:3306/test";
    private static final String user = "root";
    private static final String password = "admin";

    public static Connection Connect() {
        try {
            connection = DriverManager.getConnection(url, user, password);
            if (!connection.isClosed()) {
                System.out.println("Соединение установлено");
            }
            if (connection.isClosed()) {
                System.out.println("Соединение закрыто");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Не удалось соединиться с БД");
        }
        return connection;
    }
    public static void connectionClose() {
        try {
            if (!connection.isClosed()) {
                connection.close();
                System.out.println("Соединение закрыто");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}