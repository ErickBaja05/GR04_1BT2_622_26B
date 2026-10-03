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

        <!--
¿Qué es ese código entre llaves ${...} en el JSP?
Esa sintaxis se llama JSP Expression Language (EL).
Antes, para imprimir un dato dinámico en la vista, se tenía que mezclar HTML con código Java puro (algo llamado "scriptlets"), lo cual era muy sucio y difícil de leer, por ejemplo: <% out.print(request.getParameter("estado")); %>.

Para evitar eso, se inventó el Expression Language (${...}). Es una forma limpia y directa de acceder a variables desde el HTML.

param: Es una palabra reservada del EL que accede a los parámetros de la URL.

param.estado: Busca en la URL si existe algo como ?estado=algo.

-->

        <p role="status" class="${param.estado == 'duplicado' ? 'error' : ''}">${param.estado == 'ok' ? 'Cliente
            guardado correctamente.' : (param.estado == 'duplicado' ? 'Ese email ya está registrado.' : '')}</p>
        <!--
El formulario renderizado está configurado con el atributo method="post" y apunta a la ruta /clientes. Además, incluye un campo oculto <input type="hidden" name="accion" value="registrar"> -->

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
            <!-- Al hacer clic en el botón de tipo submit, el navegador agrupa los datos ingresados ("nombre" y "email") junto con el campo oculto "accion" y los envía de forma segura en el cuerpo de una petición HTTP POST hacia la ruta /clientes -->
            <button type="submit">Guardar cliente</button>
            <a href="${pageContext.request.contextPath}/index.jsp" class="btn-volver">Volver al inicio</a>
        </form>
    </body>

    </html>
