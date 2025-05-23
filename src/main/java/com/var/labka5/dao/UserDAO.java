package com.var.labka5.dao;

import com.var.labka5.entity.Role;
import com.var.labka5.entity.User;
import com.var.labka5.util.ConnectionManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class UserDAO {

    static UserDAO INSTANCE = new UserDAO();

    private UserDAO() {}

    public static UserDAO getInstance() {
        return INSTANCE;
    }

    public User findByLogin(String login) {
        try (var connection = ConnectionManager.open()) {
            var statement = connection.prepareStatement("SELECT * FROM users WHERE login = ?;");
            statement.setString(1, login);
            var res = statement.executeQuery();
            if (res.next()) {
                System.out.println("User found: " + login);
                return new User(res.getInt("id"), res.getString("login"), res.getString("password"), Role.valueOf(res.getString("role")), res.getString("full_name"));
            } else {
                System.out.println("User not found: " + login);
                return null;
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error finding user by login: " + login + e);
            return null;
        }
    }

    public List<User> findAllByRole(String role) {
        var patients = new ArrayList<User>();
        try (var connection = ConnectionManager.open()) {
            var statement = connection.prepareStatement("SELECT * FROM users WHERE role = ?");
            statement.setString(1, role);
            var res = statement.executeQuery();
            while (res.next()) {
                patients.add(new User(
                        res.getInt("id"),
                        res.getString("login"),
                        res.getString("password"),
                        Role.valueOf(res.getString("role")),
                        res.getString("full_name")));
            }
            System.out.println("Found patients with role: " + role);
            return patients;
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error finding patients with role: " + role);
            return null;
        }
    }

    public User save(User user) {
        try (var connection = ConnectionManager.open()) {
            var statement = connection.prepareStatement("INSERT INTO users (login, password, role, full_name) VALUES (?, ?, ?, ?)", RETURN_GENERATED_KEYS);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getRole().name());
            statement.setString(4, user.getFullName());
            statement.executeUpdate();
            try (var generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                }
            }
            System.out.println("User '" + user.getLogin() + "' with role '" + user.getRole() + "' saved successfully");
            return user;
        } catch (ClassNotFoundException e) {
            System.err.println("Driver class not found while saving user '" + user.getLogin() + "'");
            return null;
        } catch (SQLException e) {
            System.err.println("Database error while saving user '" + user.getLogin() + "'");
            throw new RuntimeException(e);
        }
    }

    public User findById(int id) {
        try (var connection = ConnectionManager.open()) {
            var statement = connection.prepareStatement("SELECT * FROM users WHERE id = ?;");
            statement.setInt(1, id);
            var res = statement.executeQuery();
            if (res.next()) {
                System.out.println("User found by id: " + id);
                return new User(res.getInt("id"), res.getString("login"), res.getString("password"), Role.valueOf(res.getString("role")),
                        res.getString("full_name"));
            } else {
                System.out.println("User not found by id: " + id);
                return null;
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error finding user by id: " + id);
            return null;
        }
    }
}
