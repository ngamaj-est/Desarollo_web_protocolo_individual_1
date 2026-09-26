<%@page pageEncoding="UTF-8"%>
<div class="dashboard-actions"><a href="<%= request.getContextPath() %>/dashboard?modulo=enfermedades&amp;vista=agregar">Añadir enfermedad</a></div>
<jsp:include page="/views/enfermedades/listar.jsp" />