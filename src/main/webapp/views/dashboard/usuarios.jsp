<%@page pageEncoding="UTF-8"%>
<div class="dashboard-actions"><a href="<%= request.getContextPath() %>/dashboard?modulo=usuarios&amp;vista=agregar">Añadir usuario</a></div>
<jsp:include page="/views/user/listar.jsp" />