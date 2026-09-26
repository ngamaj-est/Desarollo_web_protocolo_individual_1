<%@page import="org.unicartagena.protocolo.enfermedades.model.User"%>
<%@include file="/views/partials/escape.jspf"%>
<% User usuarioSidebar = (User) session.getAttribute("usuario.login"); %>
<aside class="sidebar">
    <div class="side-brand"><div class="brand-mark" aria-hidden="true">+</div><div><strong>Salud / Gestión</strong><small>Panel de información</small></div></div>
    <div class="side-label">Módulos</div>
    <nav class="nav" aria-label="Módulos principales">
        <a class="<%= "usuarios".equals(request.getAttribute("modulo")) ? "active" : "" %>" href="<%= request.getContextPath() %>/dashboard?modulo=usuarios">Usuarios</a>
        <a class="<%= "enfermedades".equals(request.getAttribute("modulo")) ? "active" : "" %>" href="<%= request.getContextPath() %>/dashboard?modulo=enfermedades">Enfermedades</a>
    </nav>
    <div class="side-user"><div><strong><%= usuarioSidebar == null ? "" : escapeHtml(usuarioSidebar.getNombre()) %></strong><small><%= usuarioSidebar == null ? "" : escapeHtml(usuarioSidebar.getRole()) %></small></div><a class="logout" href="<%= request.getContextPath() %>/auth?accion=logout">Cerrar sesión</a></div>
</aside>