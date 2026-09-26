<%@page import="org.unicartagena.protocolo.enfermedades.model.Enfermedades"%>
<%@include file="/views/partials/escape.jspf"%>
<% Enfermedades[] enfermedades = (Enfermedades[]) request.getAttribute("enfermedades"); %>
<section>
    <h2 class="section-title">Enfermedades registradas <span class="badge"><%= enfermedades == null ? 0 : enfermedades.length %></span></h2>
    <div class="table-wrap"><table class="data-table"><thead><tr><th>Enfermedad</th><th>Gravedad</th><th>Síntomas / medicamentos</th><th>Características</th><th>Acciones</th></tr></thead><tbody>
    <% if (enfermedades != null) for (Enfermedades item : enfermedades) { %>
        <tr>
            <td><strong><%= escapeHtml(item.getNombre()) %></strong><small><%= escapeHtml(item.getNombreCientifico()) %> · ID <%= item.getId() %></small></td>
            <td><span class="badge"><%= escapeHtml(item.getNivelGravedad()) %></span></td>
            <td><strong>Síntomas</strong><small><%= escapeHtml(item.getSintomas()) %></small><strong>Medicamentos</strong><small><%= escapeHtml(item.getMedicamentos()) %></small></td>
            <td><small>Contagiosa: <%= item.isEsContagiosa() ? "Sí" : "No" %></small><small>Cubierta por POS: <%= item.isEsCubiertaPorPos() ? "Sí" : "No" %></small><small>Requiere incapacidad: <%= item.isRequiereIncapacidad() ? "Sí" : "No" %></small></td>
            <td><div class="actions"><a class="btn btn-secondary btn-small" href="<%= request.getContextPath() %>/enfermedades?accion=buscar&id=<%= item.getId() %>">Editar</a><form class="inline-form" action="<%= request.getContextPath() %>/enfermedades" method="post" onsubmit="return confirm('¿Eliminar esta enfermedad?')"><input type="hidden" name="accion" value="eliminar"><input type="hidden" name="id" value="<%= item.getId() %>"><button class="btn btn-danger btn-small" type="submit">Eliminar</button></form></div></td>
        </tr>
    <% } %>
    <% if (enfermedades == null || enfermedades.length == 0) { %><tr><td colspan="5" class="empty">No hay enfermedades registradas.</td></tr><% } %>
    </tbody></table></div>
    <p class="table-scroll-note">Desliza horizontalmente la tabla en pantallas pequeñas.</p>
</section>