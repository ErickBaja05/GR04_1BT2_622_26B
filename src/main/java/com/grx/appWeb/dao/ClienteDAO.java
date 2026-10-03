package com.grx.appWeb.dao;

import com.grx.appWeb.config.HibernateUtil;
import com.grx.appWeb.model.Cliente;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ClienteDAO {

    /*
     * el ORM Hibernate abre una sesión (Session) contra la base de datos e inicia
     * una transacción. Al ejecutar session.persist(cliente), el ORM mapea los
     * atributos del objeto, construye una sentencia SQL INSERT, la ejecuta y
     * realiza un commit para guardar los cambios definitivamente
     */
    public void guardarCliente(Cliente cliente) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.persist(cliente);
                transaction.commit();
            } catch (RuntimeException ex) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                throw ex;
            }
        }
    }

    public List<Cliente> buscarPorNombre(String nombre) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            /*
             * A simple vista parece SQL, pero se llama HQL (Hibernate Query Language). Nota
             * que está seleccionando de la palabra Cliente con "C" mayúscula (tu clase
             * Java), no de la tabla clientes en minúscula. El ORM toma esa consulta
             * orientada a objetos y él mismo se encarga de traducirla al SQL nativo
             * correcto para MariaDB
             */
            try {
                List<Cliente> clientes = session.createQuery(
                        "from Cliente c where lower(c.nombre) like lower(:nombre)",
                        Cliente.class)
                        .setParameter("nombre", "%" + nombre + "%")
                        .getResultList();
                transaction.commit();
                return clientes;
            } catch (RuntimeException ex) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                throw ex;
            }
        }
    }

    public Cliente buscarPorId(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                Cliente cliente = session.find(Cliente.class, id);
                transaction.commit();
                return cliente;
            } catch (RuntimeException ex) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                throw ex;
            }
        }
    }

    public void actualizarCliente(Cliente cliente) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                session.merge(cliente);
                transaction.commit();
            } catch (RuntimeException ex) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                throw ex;
            }
        }
    }

    public void eliminarCliente(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                Cliente cliente = session.find(Cliente.class, id);
                if (cliente != null) {
                    session.remove(cliente);
                }
                transaction.commit();
            } catch (RuntimeException ex) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                throw ex;
            }
        }
    }
}
