<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <!DOCTYPE html>
    <html lang="es">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Sistema para la gestión de clientes</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css" </head>

    <body>
        <header>
            <p>Sistema para la gestión de clientes</p>
        </header>
        <main>
            <h1>Bienvenido</h1>
            <p class="intro">Administra los clientes registrados. Selecciona una operación para continuar.</p>
            <nav aria-label="Operaciones de clientes">
                <a class="action" href="${pageContext.request.contextPath}/registrarCliente">Registrar</a>
                <a class="action action-delete" href="${pageContext.request.contextPath}/eliminarCliente">Eliminar</a>
                <a class="action action-edit" href="${pageContext.request.contextPath}/modificarCliente">Modificar</a>
            </nav>
        </main>
    </body>

    </html>


    </html>
