package jm.task.core.jdbc;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        final UserService userService = new UserServiceImpl();
        userService.dropUsersTable();
        userService.createUsersTable();
        userService.saveUser("Ivan", "testLastName", (byte) 20);
        User user = userService.getAllUsers().get(0);
        System.out.println(user);
    }
}
