<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.grx.appWeb.model.Cliente, java.util.List" %>
<%!
    private String escapar(String valor) {
        if (valor == null) {
            return "";
        }
        return valor.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
%>
<%
    Cliente cliente = (Cliente) request.getAttribute("cliente");
    List<Cliente> clientes = (List<Cliente>) request.getAttribute("clientes");
%>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Modificar cliente</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/registrarCliente.css">
</head>

<body>
<h1>Modificar cliente</h1>
<p role="status" class="${not empty mensaje or param.estado == 'duplicado' ? 'error' : ''}">${not empty mensaje ? mensaje : (param.estado == 'ok' ? 'Cliente actualizado correctamente.' : (param.estado == 'duplicado' ? 'Ese email ya pertenece a otro cliente.' : ''))}</p>

<% if (cliente == null) { %>
<%-- Paso 1: buscar al cliente por nombre --%>
<form action="${pageContext.request.contextPath}/clientes" method="get">
    <input type="hidden" name="accion" value="buscar">
    <input type="hidden" name="destino" value="modificar">
    <p>
        <label for="buscar-nombre">Nombre</label>
        <input id="buscar-nombre" name="nombre" type="text" required>
    </p>
    <button type="submit">Buscar</button>
    <a href="${pageContext.request.contextPath}/index.jsp" class="btn-volver">Volver al inicio</a>
</form>

<% if (clientes != null && !clientes.isEmpty()) { %>
<ul>
    <% for (Cliente coincidencia : clientes) { %>
    <li>
        <a href="${pageContext.request.contextPath}/clientes?accion=buscar&amp;destino=modificar&amp;id=<%= coincidencia.getId() %>">
            <%= escapar(coincidencia.getNombre()) %> (<%= escapar(coincidencia.getEmail()) %>)
        </a>
    </li>
    <% } %>
</ul>
<% } %>
<% } else { %>
<%-- Paso 2: cliente encontrado --%>
<form action="${pageContext.request.contextPath}/clientes" method="post">
    <input type="hidden" name="accion" value="actualizar">
    <input type="hidden" id="id" name="id" value="<%= cliente.getId() %>">
    <p>
        <label for="nombre">Nombre</label>
        <input id="nombre" name="nombre" type="text" value="<%= escapar(cliente.getNombre()) %>" required>
    </p>
    <p>
        <label for="email">Email</label>
        <input id="email" name="email" type="email" value="<%= escapar(cliente.getEmail()) %>" required>
    </p>
    <button type="submit">Actualizar cliente</button>
    <a href="${pageContext.request.contextPath}/index.jsp" class="btn-volver">Volver al inicio</a>
</form>
<% } %>
</body>

</html>
