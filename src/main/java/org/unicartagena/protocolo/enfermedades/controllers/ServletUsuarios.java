package org.unicartagena.protocolo.enfermedades.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.unicartagena.protocolo.enfermedades.model.User;
import org.unicartagena.protocolo.enfermedades.model.UserCRUD;

@WebServlet(name = "ServletUsuarios", urlPatterns = {"/usuarios"})
public class ServletUsuarios extends HttpServlet {

    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("usuario.login") == null) {
            response.sendRedirect(request.getContextPath() + "/views/index.jsp");
            return;
        }

        String accion = request.getParameter("accion");
        String destino = request.getContextPath() + "/dashboard?modulo=usuarios";
        try {
            if (accion == null) {
                throw new IllegalArgumentException("Acción de usuario no especificada");
            }

            switch (accion) {
                case "agregar" -> {
                    User usuario = mapearUsuario(request);
                    if (usuario.getPassword().length() < 6) {
                        throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres");
                    }
                    UserCRUD.createUser(usuario);
                    destino += "&mensaje=" + codificar("Usuario creado correctamente");
                }
                case "buscar" -> {
                    int id = obtenerId(request);
                    request.getSession().setAttribute("usuario.editar", UserCRUD.getUserById(id));
                }
                case "actualizar" -> {
                    User usuario = mapearUsuario(request);
                    usuario.setId(Integer.toString(obtenerId(request)));
                    if (usuario.getPassword().isEmpty()) {
                        usuario.setPassword(UserCRUD.getUserById(Integer.parseInt(usuario.getId())).getPassword());
                    } else if (usuario.getPassword().length() < 6) {
                        throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres");
                    }
                    UserCRUD.updateUser(usuario);
                    destino += "&mensaje=" + codificar("Usuario actualizado correctamente");
                }
                case "eliminar" -> {
                    UserCRUD.deleteUser(obtenerId(request));
                    destino += "&mensaje=" + codificar("Usuario eliminado correctamente");
                }
                default -> throw new IllegalArgumentException("Acción de usuario desconocida");
            }
        } catch (Exception e) {
            destino += "&mensaje=" + codificar(e.getMessage() == null ? "No fue posible completar la operación" : e.getMessage());
        }

        response.sendRedirect(destino);
    }

    private User mapearUsuario(HttpServletRequest request) {
        User usuario = new User();
        usuario.setNombre(valor(request, "nombre"));
        usuario.setEmail(valor(request, "email"));
        usuario.setPassword(request.getParameter("password") == null ? "" : request.getParameter("password"));
        String role = valor(request, "role");
        if (!"Administrador".equals(role) && !"Medico".equals(role) && !"Usuario".equals(role)) {
            throw new IllegalArgumentException("El rol seleccionado no es válido");
        }
        usuario.setRole(role);
        return usuario;
    }

    private String valor(HttpServletRequest request, String campo) {
        String valor = request.getParameter(campo);
        if (valor == null || valor.trim().isEmpty()) {
            throw new IllegalArgumentException("El campo " + campo + " es obligatorio");
        }
        return valor.trim();
    }

    private int obtenerId(HttpServletRequest request) {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            if (id > 0) return id;
        } catch (Exception ignored) {
        }
        throw new IllegalArgumentException("El id del usuario no es válido");
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
}