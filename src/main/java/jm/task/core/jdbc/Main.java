package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserService us = new UserServiceImpl();

        // Создание таблицы User(ов)
        us.createUsersTable();

        //Добавление 4 User(ов) в таблицу с данными на свой выбор.
        // После каждого добавления должен быть вывод в консоль
        // ( User с именем – name добавлен в базу данных )
        List<User> users = Arrays.asList(
                new User("Abraham", "Lincoln", (byte) 56),
                new User("George", "Washington", (byte) 48),
                new User("Thomas", "Jefferson", (byte) 52),
                new User("Albert", "Einstein", (byte) 55)
        );
        for (User user : users) {
            us.saveUser(user.getName(), user.getLastName(), user.getAge());
        }

        //Получение всех User из базы и вывод в консоль
        // ( должен быть переопределен toString в классе User)
        List<User> userList = us.getAllUsers();
        for (User user : userList) {
            System.out.println(user);
        }

        //Очистка таблицы User(ов)
        us.cleanUsersTable();

        //Удаление таблицы
        us.dropUsersTable();
    }
}
