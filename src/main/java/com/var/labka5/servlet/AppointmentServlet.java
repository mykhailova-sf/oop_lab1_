package com.var.labka5.servlet;

import com.var.labka5.entity.Appointment;
import com.var.labka5.entity.Role;
import com.var.labka5.entity.User;
import com.var.labka5.service.AppointmentService;
import com.var.labka5.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet("/appointments/*")
public class AppointmentServlet extends HttpServlet {

    private final AppointmentService appService = AppointmentService.getInstance();
    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        var session = req.getSession();
        var user = (User) session.getAttribute("user");

        if (user == null) {
            resp.sendRedirect("/");
            return;
        }

        String pathInfo = req.getPathInfo();

        if (user.getRole() == Role.USER) {
            if (pathInfo == null || pathInfo.equals("/")) {
                List<Appointment> appointments = appService.getAllByUserId(user.getId());
                req.setAttribute("appointments", appointments);
                req.getRequestDispatcher("/WEB-INF/views/appointments-user.jsp").forward(req, resp);
            } else {
                handleAppointmentDetails(req, resp, pathInfo, "/WEB-INF/views/appointment-user.jsp");
            }
        } else {
            if (pathInfo == null || pathInfo.equals("/")) {
                List<Appointment> appointments = appService.getAllOpen();
                req.setAttribute("appointments", appointments);
                req.getRequestDispatcher("/WEB-INF/views/appointments.jsp").forward(req, resp);
            } else {
                String view = user.getRole() == Role.DOCTOR ?
                        "/WEB-INF/views/appointment-doctor.jsp" :
                        "/WEB-INF/views/appointment-nurse.jsp";
                handleAppointmentDetails(req, resp, pathInfo, view);
            }
        }
    }

    private void handleAppointmentDetails(HttpServletRequest req, HttpServletResponse resp,
                                          String pathInfo, String viewPath) throws IOException, ServletException {
        try {
            int appointmentId = Integer.parseInt(pathInfo.substring(1));
            Appointment appointment = appService.getById(appointmentId);
            if (appointment == null) {
                resp.sendRedirect("/");
                return;
            }
            var patient = userService.findById(appointment.getUserId());
            req.setAttribute("patient", patient);
            req.setAttribute("appointment", appointment);
            req.getRequestDispatcher(viewPath).forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid appointment ID");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        var session = req.getSession();
        var user = (User) session.getAttribute("user");

        if (user == null) {
            resp.sendRedirect("/");
            return;
        }

        String path = req.getRequestURI();
        String[] parts = path.split("/");

        if (user.getRole() == Role.USER && path.endsWith("/save")) {
            handleUserAppointmentSave(req, resp);
            return;
        }

        if (parts.length < 4) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        int appointmentId;
        try {
            appointmentId = Integer.parseInt(parts[2]);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String action = parts[3];
        String item;

        switch (action) {
            case "add-drug":
            case "add-procedure":
            case "add-operation":
                if (user.getRole() != Role.DOCTOR) {
                    resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Додавання дозволено лише лікарю");
                    return;
                }
                item = getParameterByAction(req, action);
                if (item != null && !item.isBlank()) {
                    switch (action) {
                        case "add-drug" -> appService.addDrug(appointmentId, item);
                        case "add-procedure" -> appService.addProcedure(appointmentId, item);
                        case "add-operation" -> appService.addOperation(appointmentId, item);
                    }
                }
                break;

            case "remove-operation":
                if (user.getRole() == Role.DOCTOR) {
                    item = req.getParameter("operation");
                    if (item != null) appService.removeOperation(appointmentId, item);
                } else {
                    resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Лише лікар може знімати операції");
                    return;
                }
                break;

            case "remove-procedure":
                if (user.getRole() == Role.DOCTOR || user.getRole() == Role.NURSE) {
                    item = req.getParameter("procedure");
                    if (item != null) appService.removeProcedure(appointmentId, item);
                } else {
                    resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Недостатньо прав для видалення процедури");
                    return;
                }
                break;

            case "remove-drug":
                if (user.getRole() == Role.USER) {
                    item = req.getParameter("drug");
                    if (item != null) appService.removeDrug(appointmentId, item);
                } else {
                    resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Ліки може видаляти лише пацієнт");
                    return;
                }
                break;

            case "discharge":
                String diagnosis = req.getParameter("diagnosis");
                if (diagnosis != null && !diagnosis.isBlank()) {
                    Appointment appointment = appService.getById(appointmentId);
                    if (appointment != null && !appointment.isDischarged()) {
                        String updatedInfo = appointment.getInfo() + "\nОстаточний діагноз: " + diagnosis;
                        appointment.setInfo(updatedInfo);
                        appointment.setDischarged(true);
                        appService.update(appointment);
                    }
                }
                resp.sendRedirect("/appointments/");
                return;

            default:
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
        }

        resp.sendRedirect("/appointments/" + appointmentId);
    }

    private String getParameterByAction(HttpServletRequest req, String action) {
        return switch (action) {
            case "add-drug" -> req.getParameter("drug");
            case "add-procedure" -> req.getParameter("procedure");
            case "add-operation" -> req.getParameter("operation");
            default -> null;
        };
    }



    private void handleUserAppointmentSave(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Appointment appointment = new Appointment(Integer.parseInt(req.getParameter("userId")),req.getParameter("info"));
        appService.save(appointment);
        resp.sendRedirect("/appointments/");
    }
}
