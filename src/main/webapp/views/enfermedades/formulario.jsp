<%@page import="org.unicartagena.protocolo.enfermedades.model.Enfermedades"%>
<%@include file="/views/partials/escape.jspf"%>
<%
    Enfermedades enfermedadEditar = (Enfermedades) request.getAttribute("enfermedadEditar");
    String gravedad = enfermedadEditar == null ? "" : enfermedadEditar.getNivelGravedad();
%>
<form class="side-form" action="<%= request.getContextPath() %>/enfermedades" method="post">
    <h2 class="section-title"><%= enfermedadEditar == null ? "Agregar enfermedad" : "Editar enfermedad" %></h2>
    <input type="hidden" name="accion" value="<%= enfermedadEditar == null ? "agregar" : "actualizar" %>">
    <% if (enfermedadEditar != null) { %><input type="hidden" name="id" value="<%= enfermedadEditar.getId() %>"><% } %>
    <div class="field"><label for="disease-name">Nombre</label><input id="disease-name" name="nombre" required maxlength="100" value="<%= enfermedadEditar == null ? "" : escapeHtml(enfermedadEditar.getNombre()) %>"></div>
    <div class="field"><label for="scientific-name">Nombre científico</label><input id="scientific-name" name="nombreCientifico" required maxlength="100" value="<%= enfermedadEditar == null ? "" : escapeHtml(enfermedadEditar.getNombreCientifico()) %>"></div>
    <div class="field"><label for="severity">Nivel de gravedad</label><select id="severity" name="nivelGravedad" required><option value="">Seleccionar</option><% for (String nivel : new String[]{"Leve", "Moderada", "Grave", "Critica"}) { %><option value="<%= nivel %>" <%= nivel.equalsIgnoreCase(gravedad) ? "selected" : "" %>><%= "Critica".equals(nivel) ? "Crítica" : nivel %></option><% } %></select></div>
    <div class="field"><label for="symptoms">Síntomas</label><textarea id="symptoms" name="sintomas" required maxlength="500"><%= enfermedadEditar == null ? "" : escapeHtml(enfermedadEditar.getSintomas()) %></textarea></div>
    <div class="field"><label for="medicines">Medicamentos</label><textarea id="medicines" name="medicamentos" required maxlength="200"><%= enfermedadEditar == null ? "" : escapeHtml(enfermedadEditar.getMedicamentos()) %></textarea></div>
    <label class="check-row"><input type="checkbox" name="esContagiosa" value="true" <%= enfermedadEditar != null && enfermedadEditar.isEsContagiosa() ? "checked" : "" %>> Es contagiosa</label>
    <label class="check-row"><input type="checkbox" name="esCubiertaPorPos" value="true" <%= enfermedadEditar != null && enfermedadEditar.isEsCubiertaPorPos() ? "checked" : "" %>> Cubierta por POS</label>
    <label class="check-row"><input type="checkbox" name="requiereIncapacidad" value="true" <%= enfermedadEditar != null && enfermedadEditar.isRequiereIncapacidad() ? "checked" : "" %>> Requiere incapacidad</label>
    <button class="btn full" type="submit"><%= enfermedadEditar == null ? "Guardar enfermedad" : "Guardar cambios" %></button>
    <% if (enfermedadEditar != null) { %><a class="form-link" href="<%= request.getContextPath() %>/dashboard?modulo=enfermedades">Cancelar edición</a><% } %>
</form>