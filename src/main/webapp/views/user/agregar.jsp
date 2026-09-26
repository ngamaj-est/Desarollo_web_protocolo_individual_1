<%@page pageEncoding="UTF-8"%>
<%@include file="/views/partials/escape.jspf"%>
<style>
    .record-form { display: grid; max-width: 620px; gap: 16px; }
    .record-form .field { display: grid; gap: 6px; }
    .record-form label { color: #34453e; font-weight: 600; }
    .record-form input, .record-form select, .record-form textarea { width: 100%; padding: 10px 11px; border: 1px solid #bdcbc3; border-radius: 4px; background: #fff; color: #26332e; font: inherit; }
    .record-form input:focus, .record-form select:focus, .record-form textarea:focus { outline: 2px solid #8ab9a1; outline-offset: 1px; }
    .record-form .form-actions { display: flex; flex-wrap: wrap; gap: 10px; align-items: center; }
    .record-form .form-actions .secondary { background: #e8efeb; color: #245b4b; }
</style>
<form class="record-form" action="<%= request.getContextPath() %>/usuarios" method="post">
    <h2 class="section-title">Crear usuario</h2>
    <input type="hidden" name="accion" value="agregar">
    <div class="field"><label for="user-name">Nombre completo</label><input id="user-name" name="nombre" required maxlength="150" autocomplete="name"></div>
    <div class="field"><label for="user-email">Correo electrónico</label><input id="user-email" name="email" type="email" required maxlength="150" autocomplete="email"></div>
    <div class="field"><label for="user-password">Contraseña</label><input id="user-password" name="password" type="password" required minlength="6" maxlength="255" autocomplete="new-password"></div>
    <div class="field"><label for="user-role">Rol</label><select id="user-role" name="role" required><option value="Usuario">Usuario</option><option value="Medico">Médico</option><option value="Administrador">Administrador</option></select></div>
    <div class="form-actions"><button class="btn" type="submit">Guardar usuario</button><a class="secondary" href="<%= request.getContextPath() %>/dashboard?modulo=usuarios">Cancelar</a></div>
</form>