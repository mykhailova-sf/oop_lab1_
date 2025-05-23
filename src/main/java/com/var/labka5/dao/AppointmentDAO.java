package com.var.labka5.dao;

import com.var.labka5.entity.Appointment;
import com.var.labka5.util.ConnectionManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class AppointmentDAO {

    static AppointmentDAO INSTANCE = new AppointmentDAO();

    private AppointmentDAO() {
    }

    public static AppointmentDAO getInstance() {
        return INSTANCE;
    }

    public Appointment findById(int id) {
        try (var connection = ConnectionManager.open()) {
            var statement = connection.prepareStatement("SELECT * FROM appointment WHERE id = ?;");
            statement.setInt(1, id);
            var res = statement.executeQuery();
            if (res.next()) {
                System.out.println("Appointment found: " + id);
                return new Appointment(
                        res.getInt("id"),
                        res.getInt("user_id"),
                        res.getString("operations") != null ? res.getString("operations").split(",") : null,
                        res.getString("drugs") != null ? res.getString("drugs").split(",") : null,
                        res.getString("procedures") != null ? res.getString("procedures").split(",") : null,
                        res.getString("info"),
                        res.getBoolean("discharged"));
            } else {
                System.out.println("Appointment not found: " + id);
                return null;
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error finding appointment by id " + id);
            return null;
        }
    }

    public List<Appointment> findAllByUserId(int id) {
        var appointments = new ArrayList<Appointment>();
        try (var connection = ConnectionManager.open()) {
            var statement = connection.prepareStatement("SELECT * FROM appointment WHERE user_id = ?;");
            statement.setInt(1, id);
            var res = statement.executeQuery();
            while (res.next()) {
                appointments.add(new Appointment(
                        res.getInt("id"),
                        res.getInt("user_id"),
                        res.getString("operations") != null ? res.getString("operations").split(",") : null,
                        res.getString("drugs") != null ? res.getString("drugs").split(",") : null,
                        res.getString("procedures") != null ? res.getString("procedures").split(",") : null,
                        res.getString("info"),
                        res.getBoolean("discharged")));
            }
            System.out.println("Found appointments by user_id " + id);
            return appointments;
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error finding appointment by user_id " + id);
            return null;
        }
    }

    public Appointment save(Appointment appointment) {
        try (var connection = ConnectionManager.open()) {
            var statement = connection.prepareStatement(
                    "INSERT INTO appointment (user_id,info) VALUES (?, ?)",
                    RETURN_GENERATED_KEYS
            );
            statement.setInt(1, appointment.getUserId());
            statement.setString(2, appointment.getInfo());
            statement.executeUpdate();

            try (var generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    appointment.setId(generatedKeys.getInt(1));
                }
            }
            System.out.println("Appointment for user " + appointment.getUserId() + " saved with id " + appointment.getId());
            return appointment;
        } catch (ClassNotFoundException e) {
            System.err.println("Driver class not found while saving appointment");
            return null;
        } catch (SQLException e) {
            System.err.println("Database error at appointment save");
            throw new RuntimeException(e);
        }
    }

    public List<Appointment> findAllUnDischarged() {
        var appointments = new ArrayList<Appointment>();
        try (var connection = ConnectionManager.open()) {
            var statement = connection.prepareStatement("SELECT * FROM appointment where discharged = false");
            var res = statement.executeQuery();
            while (res.next()) {
                appointments.add(new Appointment(
                        res.getInt("id"),
                        res.getInt("user_id"),
                        res.getString("operations") != null ? res.getString("operations").split(",") : null,
                        res.getString("drugs") != null ? res.getString("drugs").split(",") : null,
                        res.getString("procedures") != null ? res.getString("procedures").split(",") : null,
                        res.getString("info"),
                        res.getBoolean("discharged")
                ));
            }
            System.out.println("Found appointments");
            return appointments;
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error finding appointments");
            return null;
        }
    }

    public void update(Appointment appointment) {
        try (var connection = ConnectionManager.open()) {
            var statement = connection.prepareStatement(" UPDATE appointment SET operations = ?, procedures = ?, drugs = ?, info = ?, discharged = ? WHERE id = ?;"
            );
            statement.setString(1, appointment.getOperations() != null ? String.join(",", appointment.getOperations()) : null);
            statement.setString(2, appointment.getProcedures() != null ? String.join(",", appointment.getProcedures()) : null);
            statement.setString(3, appointment.getDrugs() != null ? String.join(",", appointment.getDrugs()) : null);
            statement.setString(4, appointment.getInfo());
            statement.setBoolean(5, appointment.isDischarged());
            statement.setInt(6, appointment.getId());

            statement.executeUpdate();
            System.out.println("Appointment with id " + appointment.getId() + " updated");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error updating appointment with id " + appointment.getId());
            throw new RuntimeException(e);
        }
    }

}
