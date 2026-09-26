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
    String vista = (String) request.getAttribute("vista");
    String titulo = "agregar".equals(vista) ? (usuariosActivo ? "Añadir usuario" : "Añadir enfermedad")
            : "editar".equals(vista) ? (usuariosActivo ? "Editar usuario" : "Editar enfermedad")
            : (usuariosActivo ? "Usuarios" : "Enfermedades");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><%= usuariosActivo ? "Usuarios" : "Enfermedades" %></title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/app.css">
    <style>
        .app-shell { display: grid; grid-template-columns: 230px minmax(0, 1fr); min-height: 100vh; }
        .app-shell main { min-width: 0; padding: 32px; }
        .sidebar { display: flex; flex-direction: column; gap: 22px; padding: 24px 18px; background: #245b4b; color: #fff; }
        .side-brand { display: flex; gap: 11px; align-items: center; }
        .side-brand strong, .side-brand small, .side-user strong, .side-user small { display: block; }
        .side-brand small, .side-user small, .side-label { color: #c7dbd0; font-size: 13px; }
        .brand-mark { display: grid; width: 36px; aspect-ratio: 1; place-items: center; border-radius: 4px; background: #fff; color: #245b4b; font-size: 24px; }
        .side-label { text-transform: uppercase; }
        .nav { display: grid; gap: 5px; }
        .nav a { padding: 10px 11px; border-radius: 4px; color: #fff; text-decoration: none; }
        .nav a:hover, .nav a.active { background: #367460; }
        .side-user { display: grid; gap: 12px; margin-top: auto; padding-top: 16px; border-top: 1px solid #558273; overflow-wrap: anywhere; }
        .logout { color: #fff; font-size: 14px; }
        .topline { margin-bottom: 24px; padding-bottom: 18px; border-bottom: 1px solid #d9e1dd; }
        .topline h1, .topline p { margin: 0; }
        .topline h1 { color: #245b4b; }
        .topline p, .eyebrow { color: #66766f; }
        .eyebrow { font-size: 13px; }
        .dashboard-panel { padding: 22px; border: 1px solid #d9e1dd; border-radius: 5px; background: #fff; }
        .dashboard-actions { display: flex; justify-content: flex-end; margin-bottom: 16px; }
        .dashboard-actions a, .form-actions a, .record-form .btn { padding: 9px 13px; border: 0; border-radius: 4px; background: #287052; color: #fff; font: inherit; text-align: center; text-decoration: none; cursor: pointer; }
        .notice { margin-bottom: 16px; padding: 10px 12px; border: 1px solid #b6d3c1; background: #e9f4ed; color: #245b4b; }
        .section-title { margin: 0 0 16px; color: #245b4b; font-size: 19px; }
        .badge { padding: 3px 8px; border-radius: 4px; background: #e4f0e9; color: #245b4b; }
        .empty { padding: 20px !important; color: #66766f; text-align: center; }
        .form-actions { display: flex; justify-content: flex-start; margin-top: 14px; }
        @media (max-width: 760px) {
            .app-shell { grid-template-columns: 1fr; }
            .sidebar { gap: 12px; padding: 15px; }
            .nav { grid-template-columns: repeat(2, minmax(0, 1fr)); }
            .side-user { margin-top: 0; }
            .app-shell main { padding: 20px 14px; }
            .dashboard-panel { padding: 16px; }
        }
    </style>
</head>
<body>
    <div class="app-shell">
        <jsp:include page="/views/partials/sidebar.jsp" />
        <main>
            <header class="topline">
                <div><div class="eyebrow">Panel de administración</div><h1><%= titulo %></h1><p><%= "listar".equals(vista) ? (usuariosActivo ? " " : " ") : " " %></p></div>
            </header>
            <% if (mensaje != null && !mensaje.isBlank()) { %><div class="notice" role="status"><%= escapeHtml(mensaje) %></div><% } %>
            <section class="dashboard-panel">
                <% if ("listar".equals(vista)) { %>
                    <% if (usuariosActivo) { %><jsp:include page="/views/dashboard/usuarios.jsp" /><% }
                       else { %><jsp:include page="/views/dashboard/enfermedades.jsp" /><% } %>
                <% } else if ("agregar".equals(vista)) { %>
                    <% if (usuariosActivo) { %><jsp:include page="/views/user/agregar.jsp" /><% }
                       else { %><jsp:include page="/views/enfermedades/agregar.jsp" /><% } %>
                <% } else if (usuariosActivo) { %>
                    <jsp:include page="/views/user/editar.jsp" />
                <% } else { %>
                    <jsp:include page="/views/enfermedades/editar.jsp" />
                <% } %>
            </section>
        </main>
    </div>
</body>
</html>