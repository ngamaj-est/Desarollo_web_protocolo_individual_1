package org.unicartagena.protocolo.enfermedades.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.unicartagena.protocolo.enfermedades.model.Enfermedades;
import org.unicartagena.protocolo.enfermedades.model.User;
import org.unicartagena.protocolo.enfermedades.model.UserCRUD;
import org.unicartagena.protocolo.enfermedades.model.enfermedadesCRUD;

@WebServlet(name = "ServletDashboard", urlPatterns = {"/dashboard"})
public class ServletDashboard extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("usuario.login") == null) {
            response.sendRedirect(request.getContextPath() + "/views/index.jsp");
            return;
        }

        String modulo = request.getParameter("modulo");
        if (!"usuarios".equals(modulo)) {
            modulo = "enfermedades";
        }
        request.setAttribute("modulo", modulo);

        String vista = request.getParameter("vista");
        if (!"agregar".equals(vista) && !"editar".equals(vista)) {
            vista = "listar";
        }

        try {
            if ("usuarios".equals(modulo)) {
                request.setAttribute("usuarios", UserCRUD.getAllUsers());
                User usuarioEditar = (User) request.getSession().getAttribute("usuario.editar");
                request.setAttribute("usuarioEditar", usuarioEditar);
                request.getSession().removeAttribute("usuario.editar");
                if ("editar".equals(vista) && usuarioEditar == null) {
                    vista = "listar";
                }
            } else {
                request.setAttribute("enfermedades", enfermedadesCRUD.listarEnfermedades());
                Enfermedades enfermedadEditar = (Enfermedades) request.getSession().getAttribute("enfermedad.buscar");
                request.setAttribute("enfermedadEditar", enfermedadEditar);
                request.getSession().removeAttribute("enfermedad.buscar");
                if ("editar".equals(vista) && enfermedadEditar == null) {
                    vista = "listar";
                }
            }
        } catch (Exception e) {
            request.setAttribute("mensaje", "No fue posible cargar los datos: " + e.getMessage());
        }
        request.setAttribute("vista", vista);
        request.setAttribute("dashboardReady", Boolean.TRUE);
        request.getRequestDispatcher("/views/dashboard.jsp").forward(request, response);
    }
}