package com.grx.appWeb.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = { "/registrarCliente", "/eliminarCliente", "/modificarCliente" })
public class VistaClienteServlet extends HttpServlet {

    /*
     * captura la solicitud y evalúa la ruta mediante un bloque switch. Al
     * identificar por ejemplo /registrarCliente, utiliza un RequestDispatcher para
     * realizar un
     * "forward" (una redirección interna en el servidor) hacia la ubicación real de
     * la vista: /WEB-INF/views/registrarCliente.jsp. Esta es una práctica de
     * arquitectura estándar para proteger las vistas, ya que el directorio WEB-INF
     * es inaccesible directamente desde la URL del navegador
     *
     */

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String vista = switch (request.getServletPath()) {
            case "/registrarCliente" -> "/WEB-INF/views/registrarCliente.jsp";
            case "/eliminarCliente" -> "/WEB-INF/views/eliminarCliente.jsp";
            case "/modificarCliente" -> "/WEB-INF/views/modificarCliente.jsp";
            default -> null;
        };

        if (vista == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(vista);
        dispatcher.forward(request, response);
    }
}
