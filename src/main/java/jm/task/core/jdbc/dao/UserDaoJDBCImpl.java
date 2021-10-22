package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            String sql = String.format(
                    "CREATE TABLE IF NOT EXISTS %s (" +
                    "id INT NOT NULL AUTO_INCREMENT," +
                    "name VARCHAR(32)," +
                    "last_name VARCHAR(32)," +
                    "age INT," +
                    "PRIMARY KEY (id)" +
                    ");", "users");
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            String sql = String.format(
                    "DROP TABLE IF EXISTS %s", "users");
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Statement statement = Util.getConnection().createStatement()) {
            String sql = String.format(
                    "INSERT INTO %s (name, last_name, age) " +
                    "VALUES('%s', '%s', '%d');", "users", name, lastName, age);
            statement.execute(sql);
            System.out.printf("User с именем %s добавлен в базу данных%n", name);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Statement statement = Util.getConnection().createStatement()) {
            String sql = String.format(
                    "DELETE FROM %s " +
                    "WHERE id = %d;", "users", id);
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = String.format(
                "SELECT %s, %s, %s FROM %s", "name", "last_name", "age", "users");
        try (PreparedStatement statement = Util.getConnection().prepareStatement(sql)) {
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    users.add(new User(
                            rs.getString("name"),
                            rs.getString("last_name"),
                            rs.getByte("age")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Statement statement = Util.getConnection().createStatement()) {
            String sql = String.format(
                    "DELETE FROM %s", "users");
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
