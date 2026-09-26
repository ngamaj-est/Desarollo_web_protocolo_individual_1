package org.unicartagena.protocolo.enfermedades.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
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

        try {
            if ("usuarios".equals(modulo)) {
                request.setAttribute("usuarios", UserCRUD.getAllUsers());
                request.setAttribute("usuarioEditar", request.getSession().getAttribute("usuario.editar"));
                request.getSession().removeAttribute("usuario.editar");
            } else {
                request.setAttribute("enfermedades", enfermedadesCRUD.listarEnfermedades());
                request.setAttribute("enfermedadEditar", request.getSession().getAttribute("enfermedad.buscar"));
                request.getSession().removeAttribute("enfermedad.buscar");
            }
        } catch (Exception e) {
            request.setAttribute("mensaje", "No fue posible cargar los datos: " + e.getMessage());
        }
        request.setAttribute("dashboardReady", Boolean.TRUE);
        request.getRequestDispatcher("/views/dashboard.jsp").forward(request, response);
    }
}