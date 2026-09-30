package com.grx.appWeb.dao;

import com.grx.appWeb.config.HibernateUtil;
import com.grx.appWeb.model.Cliente;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ClienteDAO {

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
}
