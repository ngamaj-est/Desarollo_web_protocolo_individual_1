<%@page pageEncoding="UTF-8"%>
<%@page import="org.unicartagena.protocolo.enfermedades.model.Enfermedades"%>
<%@include file="/views/partials/escape.jspf"%>
<%
    Enfermedades enfermedadEditar = (Enfermedades) request.getAttribute("enfermedadEditar");
    String gravedad = enfermedadEditar == null ? "" : enfermedadEditar.getNivelGravedad();
%>
<style>
    .record-form { display: grid; max-width: 700px; gap: 16px; }
    .record-form .field { display: grid; gap: 6px; }
    .record-form label { color: #34453e; font-weight: 600; }
    .record-form input, .record-form select, .record-form textarea { width: 100%; padding: 10px 11px; border: 1px solid #bdcbc3; border-radius: 4px; background: #fff; color: #26332e; font: inherit; }
    .record-form textarea { min-height: 95px; resize: vertical; }
    .record-form input:focus, .record-form select:focus, .record-form textarea:focus { outline: 2px solid #8ab9a1; outline-offset: 1px; }
    .record-form .check-row { display: flex; gap: 9px; align-items: center; font-weight: 400; }
    .record-form .check-row input { width: auto; }
    .record-form .form-actions { display: flex; flex-wrap: wrap; gap: 10px; align-items: center; }
    .record-form .form-actions .secondary { background: #e8efeb; color: #245b4b; }
</style>
<% if (enfermedadEditar != null) { %>
<form class="record-form" action="<%= request.getContextPath() %>/enfermedades" method="post">
    <h2 class="section-title">Editar enfermedad</h2>
    <input type="hidden" name="accion" value="actualizar">
    <input type="hidden" name="id" value="<%= enfermedadEditar.getId() %>">
    <div class="field"><label for="disease-name">Nombre</label><input id="disease-name" name="nombre" required maxlength="100" value="<%= escapeHtml(enfermedadEditar.getNombre()) %>"></div>
    <div class="field"><label for="scientific-name">Nombre científico</label><input id="scientific-name" name="nombreCientifico" required maxlength="100" value="<%= escapeHtml(enfermedadEditar.getNombreCientifico()) %>"></div>
    <div class="field"><label for="severity">Nivel de gravedad</label><select id="severity" name="nivelGravedad" required><option value="">Seleccionar</option><% for (String nivel : new String[]{"Leve", "Moderada", "Grave", "Critica"}) { %><option value="<%= nivel %>" <%= nivel.equalsIgnoreCase(gravedad) ? "selected" : "" %>><%= "Critica".equals(nivel) ? "Crítica" : nivel %></option><% } %></select></div>
    <div class="field"><label for="symptoms">Síntomas</label><textarea id="symptoms" name="sintomas" required maxlength="500"><%= escapeHtml(enfermedadEditar.getSintomas()) %></textarea></div>
    <div class="field"><label for="medicines">Medicamentos</label><textarea id="medicines" name="medicamentos" required maxlength="200"><%= escapeHtml(enfermedadEditar.getMedicamentos()) %></textarea></div>
    <label class="check-row"><input type="checkbox" name="esContagiosa" value="true" <%= enfermedadEditar.isEsContagiosa() ? "checked" : "" %>> Es contagiosa</label>
    <label class="check-row"><input type="checkbox" name="esCubiertaPorPos" value="true" <%= enfermedadEditar.isEsCubiertaPorPos() ? "checked" : "" %>> Cubierta por POS</label>
    <label class="check-row"><input type="checkbox" name="requiereIncapacidad" value="true" <%= enfermedadEditar.isRequiereIncapacidad() ? "checked" : "" %>> Requiere incapacidad</label>
    <div class="form-actions"><button class="btn" type="submit">Guardar cambios</button><a class="secondary" href="<%= request.getContextPath() %>/dashboard?modulo=enfermedades">Cancelar</a></div>
</form>
<% } else { %><p>No se encontró la enfermedad. <a href="<%= request.getContextPath() %>/dashboard?modulo=enfermedades">Volver al listado</a></p><% } %>