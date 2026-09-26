package org.unicartagena.protocolo.enfermedades.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import org.unicartagena.protocolo.enfermedades.model.Enfermedades;
import org.unicartagena.protocolo.enfermedades.model.enfermedadesCRUD;



@WebServlet(name = "ServletEnfermedades", urlPatterns = {"/enfermedades"})
public class ServletEnfermedades extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        if (request.getSession().getAttribute("usuario.login") == null) {
            response.sendRedirect(request.getContextPath() + "/views/index.jsp");
            return;
        }

        try {
            String accion = request.getParameter("accion");
            if (accion == null || accion.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/dashboard?modulo=enfermedades&mensaje=Accion%20no%20especificada");
                return;
            }

            switch (accion) {
                case "agregar" -> {
                    Enfermedades nuevaEnfermedad = mapearEnfermedadDesdeRequest(request);
                    enfermedadesCRUD crudAgregar = new enfermedadesCRUD();
                    crudAgregar.setEnfermedad(nuevaEnfermedad);
                    crudAgregar.agregarEnfermedad();
                    response.sendRedirect(request.getContextPath() + "/dashboard?modulo=enfermedades&mensaje=Enfermedad%20agregada%20exitosamente");
                }

                case "listartodo" -> {
                    Enfermedades[] listado = enfermedadesCRUD.listarEnfermedades();
                    request.getSession().setAttribute("enfermedades.listar", listado);
                    response.sendRedirect(request.getContextPath() + "/dashboard?modulo=enfermedades");
                }


                case "buscar" -> {
                    int idBuscar = obtenerId(request, "id", "El id de la enfermedad es obligatorio para buscarla");
                    enfermedadesCRUD crudBuscar = new enfermedadesCRUD();
                    Enfermedades enfermedadEncontrada = crudBuscar.buscarPorId(idBuscar);
                    if (enfermedadEncontrada == null) {
                        response.sendRedirect(request.getContextPath() + "/dashboard?modulo=enfermedades&mensaje=Enfermedad%20no%20encontrada");
                        return;
                    }
                    request.getSession().setAttribute("enfermedad.buscar", enfermedadEncontrada);
                    response.sendRedirect(request.getContextPath() + "/dashboard?modulo=enfermedades&mensaje=Enfermedad%20encontrada");
                }

                case "actualizar" -> {
                    int idActualizar = obtenerId(request, "id", "El id de la enfermedad es obligatorio para actualizarla");
                    Enfermedades enfermedadActualizar = mapearEnfermedadDesdeRequest(request);
                    enfermedadActualizar.setId(idActualizar);

                    enfermedadesCRUD crudActualizar = new enfermedadesCRUD();
                    crudActualizar.setEnfermedad(enfermedadActualizar);
                    crudActualizar.actualizarEnfermedad(idActualizar);
                    response.sendRedirect(request.getContextPath() + "/dashboard?modulo=enfermedades&mensaje=Enfermedad%20actualizada%20exitosamente");
                }

                case "eliminar" -> {
                    int idEliminar = obtenerId(request, "id", "El id de la enfermedad es obligatorio para eliminarla");
                    enfermedadesCRUD crudEliminar = new enfermedadesCRUD();
                    crudEliminar.eliminarEnfermedad(idEliminar);
                    response.sendRedirect(request.getContextPath() + "/dashboard?modulo=enfermedades&mensaje=Enfermedad%20eliminada%20exitosamente");
                }

                default -> response.sendRedirect(request.getContextPath() + "/dashboard?modulo=enfermedades&mensaje=Accion%20desconocida");
            }
        } catch (Exception e) {
            String mensaje = URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8);
            response.sendRedirect(request.getContextPath() + "/dashboard?modulo=enfermedades&mensaje=" + mensaje);
        }
    }

    private Enfermedades mapearEnfermedadDesdeRequest(HttpServletRequest request) throws Exception {
        Enfermedades enfermedad = new Enfermedades();

        enfermedad.setNombre(obtenerTexto(request, "nombre", "El nombre de la enfermedad es obligatorio"));
        enfermedad.setNombreCientifico(obtenerTexto(request, "nombreCientifico", "El nombre científico es obligatorio"));
        enfermedad.setNivelGravedad(obtenerTexto(request, "nivelGravedad", "El nivel de gravedad es obligatorio"));
        enfermedad.setSintomas(obtenerTexto(request, "sintomas", "Los síntomas son obligatorios"));
        enfermedad.setMedicamentos(obtenerTexto(request, "medicamentos", "Los medicamentos son obligatorios"));
        enfermedad.setEsContagiosa(obtenerBoolean(request, "esContagiosa"));
        enfermedad.setEsCubiertaPorPos(obtenerBoolean(request, "esCubiertaPorPos"));
        enfermedad.setRequiereIncapacidad(obtenerBoolean(request, "requiereIncapacidad"));

        return enfermedad;
    }

    private String obtenerTexto(HttpServletRequest request, String campo, String mensajeError) throws Exception {
        String valor = request.getParameter(campo);
        if (valor == null || valor.trim().isEmpty()) {
            throw new Exception(mensajeError);
        }
        return valor.trim();
    }

    private boolean obtenerBoolean(HttpServletRequest request, String campo) {
        String valor = request.getParameter(campo);
        return valor != null && ("1".equals(valor) || "true".equalsIgnoreCase(valor) || "on".equalsIgnoreCase(valor) || "yes".equalsIgnoreCase(valor));
    }

    private int obtenerId(HttpServletRequest request, String campo, String mensajeError) throws Exception {
        String valor = request.getParameter(campo);
        if (valor == null || valor.trim().isEmpty()) {
            throw new Exception(mensajeError);
        }

        try {
            int id = Integer.parseInt(valor.trim());
            if (id <= 0) {
                throw new Exception(mensajeError);
            }
            return id;
        } catch (NumberFormatException e) {
            throw new Exception(mensajeError);
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
}