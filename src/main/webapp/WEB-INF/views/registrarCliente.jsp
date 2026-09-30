<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Registro de clientes</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/registrarCliente.css">
</head>

<body>
<h1>Registro de clientes</h1>
<p role="status" class="${param.estado == 'duplicado' ? 'error' : ''}">${param.estado == 'ok' ? 'Cliente guardado correctamente.' : (param.estado == 'duplicado' ? 'Ese email ya está registrado.' : '')}</p>
<form action="${pageContext.request.contextPath}/clientes" method="post">
    <input type="hidden" name="accion" value="registrar">
    <p>
        <label for="nombre">Nombre</label>
        <input id="nombre" name="nombre" type="text" required>
    </p>
    <p>
        <label for="email">Email</label>
        <input id="email" name="email" type="email" required>
    </p>
    <button type="submit">Guardar cliente</button>
    <a href="${pageContext.request.contextPath}/index.jsp" class="btn-volver">Volver al inicio</a>
</form>
</body>

</html>
