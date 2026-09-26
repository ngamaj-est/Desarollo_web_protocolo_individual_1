<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.unicartagena.protocolo.enfermedades.model.User"%>
<%@include file="/views/partials/escape.jspf"%>
<%
    User usuarioActual = (User) session.getAttribute("usuario.login");
    if (usuarioActual == null) {
        response.sendRedirect(request.getContextPath() + "/views/index.jsp");
        return;
    }
    if (request.getAttribute("dashboardReady") == null) {
        request.getRequestDispatcher("/dashboard").forward(request, response);
        return;
    }
    String modulo = (String) request.getAttribute("modulo");
    String mensaje = request.getParameter("mensaje");
    if (mensaje == null) mensaje = (String) request.getAttribute("mensaje");
    boolean usuariosActivo = "usuarios".equals(modulo);
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><%= usuariosActivo ? "Usuarios" : "Enfermedades" %> | Salud</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/app.css">
</head>
<body>
    <div class="app-shell">
        <jsp:include page="/views/partials/sidebar.jsp" />
        <main>
            <header class="topline">
                <div><div class="eyebrow">Panel de administración</div><h1><%= usuariosActivo ? "Usuarios" : "Enfermedades" %></h1><p><%= usuariosActivo ? "Gestiona las cuentas y los roles registrados." : "Consulta y administra la información clínica." %></p></div>
            </header>
            <% if (mensaje != null && !mensaje.isBlank()) { %><div class="notice" role="status"><%= escapeHtml(mensaje) %></div><% } %>
            <% if (usuariosActivo) { %>
                <jsp:include page="/views/dashboard/usuarios.jsp" />
            <% } else { %>
                <jsp:include page="/views/dashboard/enfermedades.jsp" />
            <% } %>
        </main>
    </div>
</body>
</html>