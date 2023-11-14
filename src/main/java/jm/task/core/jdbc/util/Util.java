package jm.task.core.jdbc.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import jm.task.core.jdbc.model.User;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;
import org.hibernate.service.ServiceRegistry;

public class Util {
    private static Connection connection;
    private static SessionFactory sessionFactory;
    private static final String url = "jdbc:mysql://localhost:3306/test";
    private static final String user = "root";
    private static final String password = "admin";
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String dialect = "org.hibernate.dialect.MySQLDialect";

    public static SessionFactory getConnection() {
        try {
            Configuration configuration = new Configuration()
                    .setProperty("hibernate.connection.driver_class", driver)
                    .setProperty("hibernate.connection.url", url)
                    .setProperty("hibernate.connection.username", user)
                    .setProperty("hibernate.connection.password", password)
                    .setProperty("hibernate.dialect", dialect)
                    .addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sessionFactory;
    }
    public static void closeConnectionOrm() {
        if (sessionFactory != null) {
            sessionFactory.close();
            System.out.println("Соединение закрыто");
        }
    }

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