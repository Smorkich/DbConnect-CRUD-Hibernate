package jm.task.core.jdbc;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        final UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("testName","testLastName", (byte) 20);
        userService.saveUser("testName2","testLastName2", (byte) 19);
        userService.saveUser("testName3","testLastName3", (byte) 21);
        userService.saveUser("testName4","testLastName4", (byte) 18);
        List<User> users = userService.getAllUsers();
        System.out.println(users);
        userService.cleanUsersTable();
        userService.dropUsersTable();
        Util.connectionClose();
    }
}
