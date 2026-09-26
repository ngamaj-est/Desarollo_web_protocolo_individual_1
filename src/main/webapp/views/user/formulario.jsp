<%@page import="org.unicartagena.protocolo.enfermedades.model.User"%>
<%@include file="/views/partials/escape.jspf"%>
<%
    User usuarioEditar = (User) request.getAttribute("usuarioEditar");
    String rol = usuarioEditar == null ? "Usuario" : usuarioEditar.getRole();
%>
<form class="side-form" action="<%= request.getContextPath() %>/usuarios" method="post">
    <h2 class="section-title"><%= usuarioEditar == null ? "Crear usuario" : "Editar usuario" %></h2>
    <input type="hidden" name="accion" value="<%= usuarioEditar == null ? "agregar" : "actualizar" %>">
    <% if (usuarioEditar != null) { %><input type="hidden" name="id" value="<%= escapeHtml(usuarioEditar.getId()) %>"><% } %>
    <div class="field"><label for="user-name">Nombre completo</label><input id="user-name" name="nombre" required maxlength="150" value="<%= usuarioEditar == null ? "" : escapeHtml(usuarioEditar.getNombre()) %>"></div>
    <div class="field"><label for="user-email">Correo electrónico</label><input id="user-email" name="email" type="email" required maxlength="150" value="<%= usuarioEditar == null ? "" : escapeHtml(usuarioEditar.getEmail()) %>"></div>
    <div class="field"><label for="user-password">Contraseña</label><input id="user-password" name="password" type="password" <%= usuarioEditar == null ? "required" : "" %> minlength="6" maxlength="255" placeholder="<%= usuarioEditar == null ? "Mínimo 6 caracteres" : "Deja vacío para conservarla" %>"></div>
    <div class="field"><label for="user-role">Rol</label><select id="user-role" name="role" required><option value="Usuario" <%= "Usuario".equalsIgnoreCase(rol) ? "selected" : "" %>>Usuario</option><option value="Medico" <%= "Medico".equalsIgnoreCase(rol) ? "selected" : "" %>>Médico</option><option value="Administrador" <%= "Administrador".equalsIgnoreCase(rol) ? "selected" : "" %>>Administrador</option></select></div>
    <button class="btn full" type="submit"><%= usuarioEditar == null ? "Guardar usuario" : "Guardar cambios" %></button>
    <% if (usuarioEditar != null) { %><a class="form-link" href="<%= request.getContextPath() %>/dashboard?modulo=usuarios">Cancelar edición</a><% } %>
</form>