<%@page pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="org.unicartagena.protocolo.enfermedades.model.User"%>
<%@include file="/views/partials/escape.jspf"%>
<% List<User> usuarios = (List<User>) request.getAttribute("usuarios"); %>
<section>
    <h2 class="section-title">Usuarios registrados <span class="badge"><%= usuarios == null ? 0 : usuarios.size() %></span></h2>
    <div class="table-wrap"><table class="data-table"><thead><tr><th>Usuario</th><th>Correo</th><th>Rol</th><th>Acciones</th></tr></thead><tbody>
    <% if (usuarios != null) for (User item : usuarios) { %>
        <tr><td><strong><%= escapeHtml(item.getNombre()) %></strong><small>ID <%= escapeHtml(item.getId()) %></small></td><td><%= escapeHtml(item.getEmail()) %></td><td><span class="badge"><%= escapeHtml(item.getRole()) %></span></td><td><div class="actions"><a class="btn btn-secondary btn-small" href="<%= request.getContextPath() %>/usuarios?accion=buscar&id=<%= escapeHtml(item.getId()) %>">Editar</a><form class="inline-form" action="<%= request.getContextPath() %>/usuarios" method="post" onsubmit="return confirm('¿Eliminar este usuario?')"><input type="hidden" name="accion" value="eliminar"><input type="hidden" name="id" value="<%= escapeHtml(item.getId()) %>"><button class="btn btn-danger btn-small" type="submit">Eliminar</button></form></div></td></tr>
    <% } %>
    <% if (usuarios == null || usuarios.isEmpty()) { %><tr><td colspan="4" class="empty">No hay usuarios registrados.</td></tr><% } %>
    </tbody></table></div>
</section>