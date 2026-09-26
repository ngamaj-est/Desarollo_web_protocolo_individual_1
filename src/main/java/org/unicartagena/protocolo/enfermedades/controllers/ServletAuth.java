package org.unicartagena.protocolo.enfermedades.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.unicartagena.protocolo.enfermedades.model.User;

import java.io.IOException;

@WebServlet(name = "ServletAuth", urlPatterns = {"/auth", "/auth/*"}, loadOnStartup = 1)
public class ServletAuth extends HttpServlet {
    private static final long serialVersionUID = 1L;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try {
            String accion = request.getParameter("accion");

            if ("login".equals(accion)) {
                String email = request.getParameter("email");
                String password = request.getParameter("password");

                if (email == null || password == null || email.trim().isEmpty() || password.trim().isEmpty()) {
                    response.sendRedirect("usuario/login.jsp?mensaje=Email y password requeridos");
                    return;
                }

                User user = CRUDUsuario.iniciarSesion(email, password);
                if (user == null) {
                    response.sendRedirect("usuario/login.jsp?mensaje=Credenciales invalidas");
                    return;
                }

                request.getSession().setAttribute("usuario.login", user);
                response.sendRedirect("index.jsp?mensaje=Bienvenido " + user.getNombre());
                return;

            } else if ("logout".equals(accion)) {
                request.getSession().invalidate();
                response.sendRedirect("usuario/login.jsp?mensaje=Sesion cerrada");
                return;
            } else {
                response.sendRedirect("mensaje.jsp?mensaje=Accion no valida");
                return;
            }
        } catch (Exception e) {
            response.sendRedirect("usuario/login.jsp?mensaje=" + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet para autenticacion de usuarios";
    }
}
