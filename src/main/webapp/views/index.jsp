<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="org.unicartagena.protocolo.enfermedades.model.User"%>
<%@include file="/views/partials/escape.jspf"%>
<%
    User usuarioActual = (User) session.getAttribute("usuario.login");
    String mensaje = request.getParameter("mensaje");
    boolean registro = "registro".equals(request.getParameter("vista"));
    if (usuarioActual != null) {
        response.sendRedirect(request.getContextPath() + "/dashboard");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><%= registro ? "Crear cuenta" : "Iniciar sesión" %> | Salud</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/assets/app.css">
</head>
<body>
    <div class="auth-shell">
        <section class="brand-panel">
            <div class="brand-mark" aria-hidden="true">+</div>
            <div class="eyebrow">Gestión clínica</div>
            <h1>Información clara para cuidar mejor.</h1>
            <p>Administra usuarios y consulta la información de enfermedades desde un solo espacio de trabajo.</p>
        </section>
        <section class="auth-card" aria-labelledby="form-title">
            <div class="eyebrow"><%= registro ? "Acceso al sistema" : "Bienvenido de nuevo" %></div>
            <h2 id="form-title"><%= registro ? "Crear una cuenta" : "Iniciar sesión" %></h2>
            <p class="subtext"><%= registro ? "Regístrate como usuario para comenzar." : "Ingresa tus credenciales para continuar." %></p>
            <% if (mensaje != null && !mensaje.isBlank()) { %><div class="notice" role="status"><%= escapeHtml(mensaje) %></div><% } %>
            <% if (registro) { %>
                <form action="<%= request.getContextPath() %>/auth" method="post">
                    <input type="hidden" name="accion" value="registrar">
                    <div class="field"><label for="nombre">Nombre completo</label><input id="nombre" name="nombre" autocomplete="name" required maxlength="150"></div>
                    <div class="field"><label for="email">Correo electrónico</label><input id="email" name="email" type="email" autocomplete="email" required maxlength="150"></div>
                    <div class="field"><label for="password">Contraseña</label><input id="password" name="password" type="password" autocomplete="new-password" required minlength="6" maxlength="255"></div>
                    <button class="btn full" type="submit">Crear cuenta</button>
                </form>
                <a class="form-link" href="<%= request.getContextPath() %>/views/index.jsp">Ya tengo una cuenta</a>
            <% } else { %>
                <form action="<%= request.getContextPath() %>/auth" method="post">
                    <input type="hidden" name="accion" value="login">
                    <div class="field"><label for="email">Correo electrónico</label><input id="email" name="email" type="email" autocomplete="username" required></div>
                    <div class="field"><label for="password">Contraseña</label><input id="password" name="password" type="password" autocomplete="current-password" required></div>
                    <button class="btn full" type="submit">Entrar</button>
                </form>
                <a class="form-link" href="<%= request.getContextPath() %>/views/index.jsp?vista=registro">Crear una cuenta nueva</a>
            <% } %>
        </section>
    </div>
</body>
</html>