package com.var.labka5.service;

import com.var.labka5.dao.AppointmentDAO;
import com.var.labka5.entity.Appointment;

import java.util.ArrayList;
import java.util.List;

public class AppointmentService {

    static AppointmentService INSTANCE = new AppointmentService();

    private final AppointmentDAO appDao = AppointmentDAO.getInstance();

    private AppointmentService() {}

    public static AppointmentService getInstance() {
        return INSTANCE;
    }

    public List<Appointment> getAllByUserId(int id) {
        var appointments = appDao.findAllByUserId(id);
        if (appointments.isEmpty()){
            System.out.println("No appointments");
        } else {
            System.out.println("Appointments been found");
        }
        return appointments;
    }

    public List<Appointment> getAllOpen() {
        var appointments = appDao.findAllUnDischarged();
        if (appointments.isEmpty()){
            System.out.println("No appointments");
        } else {
            System.out.println("Appointments been found");
        }
        return appointments;
    }

    public Appointment getById(int id){
        return appDao.findById(id);
    }

    public void addDrug(int id, String drug) {
        Appointment appointment = appDao.findById(id);
        if (appointment != null) {
            List<String> drugs = new ArrayList<>();
            if (appointment.getDrugs() != null) {
                drugs = new ArrayList<>(List.of(appointment.getDrugs()));
            }
            drugs.add(drug);
            appointment.setDrugs(drugs.toArray(new String[0]));
            appDao.update(appointment);
        }
    }

    public void removeDrug(int id, String drug) {
        Appointment appointment = appDao.findById(id);
        if (appointment != null && appointment.getDrugs() != null) {
            List<String> drugs = new ArrayList<>(List.of(appointment.getDrugs()));
            drugs.removeIf(d -> d.equalsIgnoreCase(drug));
            appointment.setDrugs(drugs.isEmpty() ? null : drugs.toArray(new String[0]));
            appDao.update(appointment);
        }
    }

    public void addProcedure(int id, String procedure) {
        Appointment appointment = appDao.findById(id);
        if (appointment != null) {
            List<String> procedures = new ArrayList<>();
            if (appointment.getProcedures() != null) {
                procedures = new ArrayList<>(List.of(appointment.getProcedures()));
            }
            procedures.add(procedure);
            appointment.setProcedures(procedures.toArray(new String[0]));
            appDao.update(appointment);
        }
    }

    public void removeProcedure(int id, String procedure) {
        Appointment appointment = appDao.findById(id);
        if (appointment != null && appointment.getProcedures() != null) {
            List<String> procedures = new ArrayList<>(List.of(appointment.getProcedures()));
            procedures.removeIf(p -> p.equalsIgnoreCase(procedure));
            appointment.setProcedures(procedures.isEmpty() ? null : procedures.toArray(new String[0]));
            appDao.update(appointment);
        }
    }

    public void addOperation(int id, String operation) {
        Appointment appointment = appDao.findById(id);
        if (appointment != null) {
            List<String> operations = new ArrayList<>();
            if (appointment.getOperations() != null) {
                operations = new ArrayList<>(List.of(appointment.getOperations()));
            }
            operations.add(operation);
            appointment.setOperations(operations.toArray(new String[0]));
            appDao.update(appointment);
        }
    }

    public void removeOperation(int id, String operation) {
        Appointment appointment = appDao.findById(id);
        if (appointment != null && appointment.getOperations() != null) {
            List<String> operations = new ArrayList<>(List.of(appointment.getOperations()));
            operations.removeIf(o -> o.equalsIgnoreCase(operation));
            appointment.setOperations(operations.isEmpty() ? null : operations.toArray(new String[0]));
            appDao.update(appointment);
        }
    }

    public void update (Appointment a){
        appDao.update(a);
    }


//    public void discharge(int id) {
//        Appointment appointment = appDao.findById(id);
//        if (appointment != null) {
//            appointment.setDischarged(true);
//            appDao.update(appointment);
//        }
//    }

    public Appointment save(Appointment appointment) {
        return appDao.save(appointment);
    }
}
