package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection conn = Util.Connect();
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try(Statement statement = conn.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Users(Id MEDIUMINT NOT NULL AUTO_INCREMENT," +
                    "Name VARCHAR(255),LastName VARCHAR(255),Age TINYINT,PRIMARY KEY (Id))");
            System.out.println("Таблица создана");
        } catch (SQLException ex) {
            System.out.println("Create error");
            System.out.println(ex.getMessage());
        }
    }

    public void dropUsersTable() {
        try(Statement statement = conn.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS Users");
            System.out.println("Таблица удалена");
        } catch (SQLException ex) {
            System.out.println("Delete error");
            System.out.println(ex.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String query = "INSERT INTO Users (Name, LastName, Age) VALUES(?,?,?)";
        try(PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1,name);
            statement.setString(2, lastName);
            statement.setByte(3,age);
            statement.executeUpdate();
            System.out.println("User с именем –" + name + " добавлен в базу данных");
        } catch (SQLException ex) {
            System.out.println("Ошибка добавления");
            System.out.println(ex.getMessage());
        }
    }

    public void removeUserById(long id) {
        try(Statement statement = conn.createStatement()) {
            statement.executeUpdate("DELETE FROM test.Users WHERE Id = " + id);
            System.out.println("Успешно удалено");
        } catch (SQLException ex) {
            System.out.println("Ошибка удаления");
            System.out.println(ex.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try(Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Users");
            while(resultSet.next()) {
                User user = new User(resultSet.getString(2),resultSet.getString(3),
                        resultSet.getByte(4));
                user.setId(resultSet.getLong(1));
                users.add(user);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return users;
    }
    public void cleanUsersTable() {
        try(Statement statement = conn.createStatement()) {
            statement.executeUpdate("DELETE FROM test.Users");
            System.out.println("Все записи успешно удалены");
        } catch (SQLException ex) {
            System.out.println("Ошибка удаления");
            System.out.println(ex.getMessage());
        }
    }
}
