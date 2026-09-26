package org.unicartagena.protocolo.enfermedades.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.unicartagena.protocolo.enfermedades.model.User;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.unicartagena.protocolo.enfermedades.model.UserCRUD;

@WebServlet(name = "ServletAuth", urlPatterns = {"/auth", "/auth/*"}, loadOnStartup = 1)
public class ServletAuth extends HttpServlet {
    private static final long serialVersionUID = 1L;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        try {
            String accion = request.getParameter("accion");

            if (null == accion) {
                response.sendRedirect(request.getContextPath() + "/views/index.jsp?mensaje=Accion%20no%20valida");
            } else switch (accion) {
                case "login" -> {
                    String email = request.getParameter("email");
                    String password = request.getParameter("password");
                    
                    if (email == null || password == null || email.trim().isEmpty() || password.trim().isEmpty()) {
                        response.sendRedirect(request.getContextPath() + "/views/index.jsp?mensaje=Email%20y%20password%20requeridos");
                        return;
                    }
                    
                    User user = UserCRUD.Login(email, password);
                    if (user == null) {
                        response.sendRedirect(request.getContextPath() + "/views/index.jsp?mensaje=Credenciales%20invalidas");
                        return;
                    }
                    
                    request.getSession().setAttribute("usuario.login", user);
                    response.sendRedirect(request.getContextPath() + "/dashboard?mensaje=" + codificar("Bienvenido " + user.getNombre()));
                }

                case "registrar" -> {
                    String nombre = request.getParameter("nombre");
                    String email = request.getParameter("email");
                    String password = request.getParameter("password");
                    if (nombre == null || nombre.trim().isEmpty() || email == null || email.trim().isEmpty()
                            || password == null || password.length() < 6) {
                        response.sendRedirect(request.getContextPath() + "/views/index.jsp?vista=registro&mensaje="
                                + codificar("Completa los campos y usa una contraseña de al menos 6 caracteres"));
                        return;
                    }
                    User nuevoUsuario = new User();
                    nuevoUsuario.setNombre(nombre.trim());
                    nuevoUsuario.setEmail(email.trim());
                    nuevoUsuario.setPassword(password);
                    nuevoUsuario.setRole("Usuario");
                    UserCRUD.createUser(nuevoUsuario);
                    response.sendRedirect(request.getContextPath() + "/views/index.jsp?mensaje="
                            + codificar("Cuenta creada. Ya puedes iniciar sesión"));
                }

                case "logout" -> {
                    request.getSession().invalidate();
                    response.sendRedirect(request.getContextPath() + "/views/index.jsp?mensaje=Sesion%20cerrada");
                }

                default -> {
                    response.sendRedirect(request.getContextPath() + "/views/index.jsp?mensaje=Accion%20no%20valida");
                }

            }
        } catch (Exception e) {
            String vista = "registrar".equals(request.getParameter("accion")) ? "?vista=registro&" : "?";
            response.sendRedirect(request.getContextPath() + "/views/index.jsp" + vista + "mensaje="
                    + codificar(e.getMessage() == null ? "No fue posible completar la operación" : e.getMessage()));
        }
    }

    private String codificar(String valor) {
        return URLEncoder.encode(valor, StandardCharsets.UTF_8);
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
