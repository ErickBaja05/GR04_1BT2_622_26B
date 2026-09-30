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
    <title>Eliminar cliente</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/registrarCliente.css">
</head>

<body>
<h1>Eliminar cliente</h1>
<p role="status" class="${not empty mensaje ? 'error' : ''}">${not empty mensaje ? mensaje : (param.estado == 'ok' ? 'Cliente eliminado correctamente.' : '')}</p>

<% if (cliente == null) { %>
<%-- Paso 1: buscar al cliente por nombre --%>
<form action="${pageContext.request.contextPath}/clientes" method="get">
    <input type="hidden" name="accion" value="buscar">
    <input type="hidden" name="destino" value="eliminar">
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
        <a href="${pageContext.request.contextPath}/clientes?accion=buscar&amp;destino=eliminar&amp;id=<%= coincidencia.getId() %>">
            <%= escapar(coincidencia.getNombre()) %> (<%= escapar(coincidencia.getEmail()) %>)
        </a>
    </li>
    <% } %>
</ul>
<% } %>
<% } else { %>
<%-- Paso 2: cliente encontrado --%>
<form action="${pageContext.request.contextPath}/clientes" method="post"
      onsubmit="return confirm('¿Está seguro de que desea eliminar este cliente?');">
    <input type="hidden" name="accion" value="eliminar">
    <input type="hidden" name="id" value="<%= cliente.getId() %>">
    <p>
        <label for="nombre">Nombre</label>
        <input id="nombre" type="text" value="<%= escapar(cliente.getNombre()) %>" readonly>
    </p>
    <p>
        <label for="email">Email</label>
        <input id="email" type="email" value="<%= escapar(cliente.getEmail()) %>" readonly>
    </p>
    <button type="submit">Eliminar cliente</button>
    <a href="${pageContext.request.contextPath}/index.jsp" class="btn-volver">Volver al inicio</a>
</form>
<% } %>
</body>

</html>
