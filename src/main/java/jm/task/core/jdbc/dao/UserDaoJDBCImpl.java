package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static Connection connection = Util.getConnection();
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("Create table IF NOT EXISTS Users (" +
                    " id INT PRIMARY KEY AUTO_INCREMENT," +
                    " name VARCHAR(40) NOT NULL," +
                    " lastName VARCHAR(40) NOT NULL," +
                    " age int(3) NOT NULL);");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS Users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void saveUser(String name, String lastName, byte age) throws SQLException {
        String sqlScript = "INSERT INTO Users(name, lastName, age) values (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlScript)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String sqlScript = "DELETE FROM Users WHERE id =?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlScript)) {
            preparedStatement.setLong(1, id);
            try {
                preparedStatement.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public List<User> getAllUsers() {
        try (Statement statement = connection.createStatement()) {
            ResultSet res = statement.executeQuery("SELECT * FROM Users;");
            List<User> userList = new ArrayList<User>();
            while (res.next()) {
                User user = new User(res.getString(2), res.getString(3),
                        res.getByte(4));
                user.setId(res.getLong(1));
                userList.add(user);
            }
            return userList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;


    }

    public void cleanUsersTable() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            try {
                statement.execute("TRUNCATE Users;");
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
