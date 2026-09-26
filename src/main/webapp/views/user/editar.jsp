<%@page pageEncoding="UTF-8"%>
<%@page import="org.unicartagena.protocolo.enfermedades.model.User"%>
<%@include file="/views/partials/escape.jspf"%>
<%
    User usuarioEditar = (User) request.getAttribute("usuarioEditar");
    String rol = usuarioEditar == null ? "Usuario" : usuarioEditar.getRole();
%>
<style>
    .record-form { display: grid; max-width: 620px; gap: 16px; }
    .record-form .field { display: grid; gap: 6px; }
    .record-form label { color: #34453e; font-weight: 600; }
    .record-form input, .record-form select, .record-form textarea { width: 100%; padding: 10px 11px; border: 1px solid #bdcbc3; border-radius: 4px; background: #fff; color: #26332e; font: inherit; }
    .record-form input:focus, .record-form select:focus, .record-form textarea:focus { outline: 2px solid #8ab9a1; outline-offset: 1px; }
    .record-form .form-actions { display: flex; flex-wrap: wrap; gap: 10px; align-items: center; }
    .record-form .form-actions .secondary { background: #e8efeb; color: #245b4b; }
</style>
<% if (usuarioEditar != null) { %>
<form class="record-form" action="<%= request.getContextPath() %>/usuarios" method="post">
    <h2 class="section-title">Editar usuario</h2>
    <input type="hidden" name="accion" value="actualizar">
    <input type="hidden" name="id" value="<%= escapeHtml(usuarioEditar.getId()) %>">
    <div class="field"><label for="user-name">Nombre completo</label><input id="user-name" name="nombre" required maxlength="150" value="<%= escapeHtml(usuarioEditar.getNombre()) %>"></div>
    <div class="field"><label for="user-email">Correo electrónico</label><input id="user-email" name="email" type="email" required maxlength="150" value="<%= escapeHtml(usuarioEditar.getEmail()) %>"></div>
    <div class="field"><label for="user-password">Nueva contraseña</label><input id="user-password" name="password" type="password" minlength="6" maxlength="255" placeholder="Deja vacío para conservarla"></div>
    <div class="field"><label for="user-role">Rol</label><select id="user-role" name="role" required><option value="Usuario" <%= "Usuario".equalsIgnoreCase(rol) ? "selected" : "" %>>Usuario</option><option value="Medico" <%= "Medico".equalsIgnoreCase(rol) ? "selected" : "" %>>Médico</option><option value="Administrador" <%= "Administrador".equalsIgnoreCase(rol) ? "selected" : "" %>>Administrador</option></select></div>
    <div class="form-actions"><button class="btn" type="submit">Guardar cambios</button><a class="secondary" href="<%= request.getContextPath() %>/dashboard?modulo=usuarios">Cancelar</a></div>
</form>
<% } else { %><p>No se encontró el usuario. <a href="<%= request.getContextPath() %>/dashboard?modulo=usuarios">Volver al listado</a></p><% } %>