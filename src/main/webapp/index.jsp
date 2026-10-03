<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <!DOCTYPE html>
    <html lang="es">

    <!-- El JSP renderiza la estructura HTML del formulario y se la envía al navegador del usuario -->

    <!-- El navegador realiza una petición HTTP de tipo GET al servidor solicitando la ruta principal de la aplicación.
  El servidor procesa la solicitud y devuelve el archivo index.jsp, el cual es traducido a HTML puro que muestra el título
y el menú de navegación con los enlaces a las diferentes operaciones-->

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Sistema para la gestión de clientes</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/index.css">
    </head>

    <body>
        <header>
            <p>Sistema para la gestión de clientes</p>
        </header>
        <main>
            <h1>Bienvenido</h1>
            <p class="intro">Administra los clientes registrados. Selecciona una operación para continuar.</p>
            <nav aria-label="Operaciones de clientes">
                <!-- Al hacer clic en el botón "Registrar", la etiqueta <a> del index.jsp genera una nueva petición HTTP GET apuntando a la ruta /registrarCliente.   Esta ruta no corresponde directamente a un archivo físico público, sino que es interceptada por VistaClienteServlet.java, el cual está configurado explícitamente (@WebServlet) para escuchar las rutas de las vistass-->
                <a class="action" href="${pageContext.request.contextPath}/registrarCliente">Registrar</a>
                <a class="action action-delete" href="${pageContext.request.contextPath}/eliminarCliente">Eliminar</a>
                <a class="action action-edit" href="${pageContext.request.contextPath}/modificarCliente">Modificar</a>
            </nav>
        </main>
    </body>

    </html>
