package com.var.labka5.service;

import com.var.labka5.entity.Role;
import com.var.labka5.entity.User;
import com.var.labka5.dao.UserDAO;

import java.util.List;
import java.util.Objects;

public class UserService {

    static UserService INSTANCE = new UserService();

    private final UserDAO userDao = UserDAO.getInstance();

    private UserService() {}

    public static UserService getInstance() {
        return INSTANCE;
    }

    public User register(User user) {
        if (userDao.findByLogin(user.getLogin()) != null) {
            System.out.println("Registration failed: user with login '" + user.getLogin() + "' already exists");
            return null;
        } else {
            if (userDao.save(user) != null) {
                System.out.println("User '" + user.getLogin() + "' registered successfully");
                return user;
            } else {
                System.err.println("Registration failed for '" + user.getLogin() + "': unexpected database error");
                return null;
            }
        }
    }

    public User login(String login, String password) {
        var user = userDao.findByLogin(login);
        if (user != null && Objects.equals(user.getPassword(), password)) {
            System.out.println("Successful login: " + user.getLogin() + " with role " + user.getRole());
            return user;
        }
        System.out.println("Login failed for user '" + login + "'");
        return null;
    }

    public List<User> getAllByRole(Role role) {
        var patients = userDao.findAllByRole(role.name());
        if (patients.isEmpty()){
            System.out.println("No patients");
        } else {
            System.out.println("Patients been found");
        }
        return patients;
    }

    public User findById(int id){
        return userDao.findById(id);
    }
}
