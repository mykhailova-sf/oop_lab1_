package com.var.labka5.entity;

public class User {
    private int id;
    private String login;
    private String password;
    private Role role;
    private String fullName;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


    public User(String login, String password, Role role, String fullName) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.fullName = fullName;
    }

    public User(int id, String login, String password, Role role, String fullName) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.role = role;
        this.fullName = fullName;
    }
}
