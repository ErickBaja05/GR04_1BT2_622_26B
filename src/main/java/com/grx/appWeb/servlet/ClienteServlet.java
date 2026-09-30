package com.grx.appWeb.servlet;

import com.grx.appWeb.dao.ClienteDAO;
import com.grx.appWeb.model.Cliente;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "ClienteServlet", urlPatterns = "/clientes")
public class ClienteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding(StandardCharsets.UTF_8.name());

        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        if (nombre == null || nombre.isBlank() || email == null || email.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Nombre y email son obligatorios.");
            return;
        }

        Cliente cliente = new Cliente(nombre.trim(), email.trim());
        try {
            new ClienteDAO().guardarCliente(cliente);
        } catch (RuntimeException ex) {
            log("No se pudo guardar el cliente.", ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "No se pudo guardar el cliente.");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/registrarCliente?estado=ok");
    }
}
