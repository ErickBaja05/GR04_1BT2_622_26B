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
import java.sql.SQLException;
import java.util.List;

//El ClienteServlet.java está configurado para escuchar la ruta /clientes

@WebServlet(name = "ClienteServlet", urlPatterns = "/clientes")
public class ClienteServlet extends HttpServlet {

    /*
     * . Al recibir una petición POST, su método doPost entra en acción
     */

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        configurarCodificacion(request, response);
        String accion = request.getParameter("accion");
        if (accion == null || accion.isBlank()) {
            accion = "registrar";
        }

        // El servlet extrae el valor del parámetro "accion", evalúa que se trata de por
        // ejemplo "registrar", y llama al método privado registrarCliente()

        switch (accion) {
            case "registrar" -> registrarCliente(request, response);
            case "actualizar" -> actualizarCliente(request, response);
            case "eliminar" -> eliminarCliente(request, response);
            default -> response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        configurarCodificacion(request, response);
        String accion = request.getParameter("accion");

        switch (accion == null ? "" : accion) {
            case "buscar" -> buscarCliente(request, response);
            default -> response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida.");
        }
    }

    // Dentro de este método, se instancian los datos limpios en un nuevo objeto del
    // Modelo: Cliente cliente = new Cliente(nombre.trim(), email.trim());
    private void registrarCliente(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        if (nombre == null || nombre.isBlank() || email == null || email.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Nombre y email son obligatorios.");
            return;
        }

        Cliente cliente = new Cliente(nombre.trim(), email.trim());
        try {
            // El Servlet transfiere el control a la capa de persistencia ejecutando:
            new ClienteDAO().guardarCliente(cliente);
        } catch (RuntimeException ex) {
            if (esEmailDuplicado(ex)) {
                response.sendRedirect(request.getContextPath() + "/registrarCliente?estado=duplicado");
                return;
            }
            log("No se pudo guardar el cliente.", ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "No se pudo guardar el cliente.");
            return;
        }

        // Si el proceso es exitoso, el flujo vuelve al ClienteServlet.java, el cual
        // finaliza el ciclo devolviendo al navegador una respuesta HTTP de redirección
        // (response.sendRedirect(...)) hacia la ruta /registrarCliente?estado=ok

        /*
         * Esta redirección instruye al navegador a realizar una nueva petición GET
         * pero esta vez enviando el parámetro de éxito en la
         * URL. El JSP lee este parámetro mediante ${param.estado == 'ok'} y renderiza
         * el mensaje verde: "Cliente guardado correctamente.". Este mecanismo se conoce
         * como el patrón Post-Redirect-Get y es vital en aplicaciones web para evitar
         * que, si el usuario refresca la página, el formulario POST se vuelva a enviar
         * y se creen registros duplicados
         */

        response.sendRedirect(request.getContextPath() + "/registrarCliente?estado=ok");
    }

    private void buscarCliente(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String destino = request.getParameter("destino");
        String vista = switch (destino == null ? "" : destino) {
            case "modificar" -> "/WEB-INF/views/modificarCliente.jsp";
            case "eliminar" -> "/WEB-INF/views/eliminarCliente.jsp";
            default -> null;
        };
        if (vista == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Destino no válido.");
            return;
        }

        String nombre = request.getParameter("nombre");
        String idParam = request.getParameter("id");
        ClienteDAO clienteDAO = new ClienteDAO();
        try {
            if (nombre != null && !nombre.isBlank()) {
                List<Cliente> clientes = clienteDAO.buscarPorNombre(nombre.trim());
                if (clientes.size() == 1) {
                    request.setAttribute("cliente", clientes.get(0));
                } else if (clientes.size() > 1) {
                    request.setAttribute("clientes", clientes);
                } else {
                    request.setAttribute("mensaje", "El cliente no está registrado.");
                }
            } else if (idParam != null && !idParam.isBlank()) {
                Long id = parsearId(idParam, response);
                if (id == null) {
                    return;
                }
                Cliente cliente = clienteDAO.buscarPorId(id);
                if (cliente != null) {
                    request.setAttribute("cliente", cliente);
                } else {
                    request.setAttribute("mensaje", "El cliente no está registrado.");
                }
            } else {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Debe indicar nombre o id.");
                return;
            }
        } catch (RuntimeException ex) {
            log("No se pudo buscar el cliente.", ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "No se pudo buscar el cliente.");
            return;
        }

        request.getRequestDispatcher(vista).forward(request, response);
    }

    private void actualizarCliente(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String idParam = request.getParameter("id");
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        if (idParam == null || idParam.isBlank()
                || nombre == null || nombre.isBlank()
                || email == null || email.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Id, nombre y email son obligatorios.");
            return;
        }

        Long id = parsearId(idParam, response);
        if (id == null) {
            return;
        }

        Cliente cliente = new Cliente(nombre.trim(), email.trim());
        cliente.setId(id);
        try {
            new ClienteDAO().actualizarCliente(cliente);
        } catch (RuntimeException ex) {
            if (esEmailDuplicado(ex)) {
                response.sendRedirect(request.getContextPath() + "/modificarCliente?estado=duplicado");
                return;
            }
            log("No se pudo actualizar el cliente.", ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "No se pudo actualizar el cliente.");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/modificarCliente?estado=ok");
    }

    private void eliminarCliente(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El id es obligatorio.");
            return;
        }

        Long id = parsearId(idParam, response);
        if (id == null) {
            return;
        }

        try {
            new ClienteDAO().eliminarCliente(id);
        } catch (RuntimeException ex) {
            log("No se pudo eliminar el cliente.", ex);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "No se pudo eliminar el cliente.");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/eliminarCliente?estado=ok");
    }

    private Long parsearId(String idParam, HttpServletResponse response) throws IOException {
        try {
            return Long.valueOf(idParam.trim());
        } catch (NumberFormatException ex) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El id no es válido.");
            return null;
        }
    }

    private boolean esEmailDuplicado(Throwable ex) {
        for (Throwable causa = ex; causa != null; causa = causa.getCause()) {
            if (causa instanceof SQLException sqlException
                    && (sqlException.getErrorCode() == 1062
                            || "23505".equals(sqlException.getSQLState()))) {
                return true;
            }
        }
        return false;
    }

    private void configurarCodificacion(HttpServletRequest request, HttpServletResponse response)
            throws java.io.UnsupportedEncodingException {
        request.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
    }
}
