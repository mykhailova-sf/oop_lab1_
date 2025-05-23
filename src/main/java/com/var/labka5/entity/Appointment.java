package com.var.labka5.entity;

public class Appointment {

    private int id;

    private int userId;

    private String[] operations;

    private String[] drugs;

    private String[] procedures;

    private String info;

    private boolean discharged;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public boolean isDischarged() {
        return discharged;
    }

    public void setDischarged(boolean discharged) {
        this.discharged = discharged;
    }

    public Appointment(int id, int userId, String[] operations, String[] drugs, String[] procedures, String info, boolean discharged) {
        this.id = id;
        this.userId = userId;
        this.operations = operations;
        this.drugs = drugs;
        this.procedures = procedures;
        this.info = info;
        this.discharged = discharged;
    }

    public Appointment(int userId,String info) {
        this.userId = userId;
        this.operations = null;
        this.drugs = null;
        this.procedures = null;
        this.info = info;
        this.discharged = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String[] getOperations() {
        return operations;
    }

    public void setOperations(String[] operations) {
        this.operations = operations;
    }

    public String[] getDrugs() {
        return drugs;
    }

    public void setDrugs(String[] drugs) {
        this.drugs = drugs;
    }

    public String[] getProcedures() {
        return procedures;
    }

    public void setProcedures(String[] procedures) {
        this.procedures = procedures;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
