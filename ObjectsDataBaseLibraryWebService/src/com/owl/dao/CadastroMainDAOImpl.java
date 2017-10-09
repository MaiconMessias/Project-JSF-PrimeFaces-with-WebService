/*******************************************************************************
 * Class responsible for implementing the SELECT, INSERT, UPDATE, REFRESH, DELETE, 
 * CANCEL, DESTROY methods called by the user through the ManagedBean of each 
 * .xhtml screen.
 ******************************************************************************/
package com.owl.dao;

import com.owl.util.HibernateUtil;
import java.util.List;
import org.hibernate.Session;

/** Main cadastre DAO class
 * @version 1.0
 * @author Maicon Messias
 */
public class CadastroMainDAOImpl<T> implements CadastroMainDAO<T> {
    private List<T> lista;
    private T selecionado;
    private String namedQuery;

    public CadastroMainDAOImpl(String namedQuery) {
        this.namedQuery = namedQuery;
    }

//  Method invoked in the ManagedBean @PreDestroy
    @Override
    public void onDestroy() {}

    @Override
    public void editar() {}

    @Override
    public void inserir(Class<T> novaClasse) throws InstantiationException, IllegalAccessException {
        this.selecionado = novaClasse.newInstance();
    }

    @Override
    public boolean deletar() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
            session.delete(selecionado);
            session.getTransaction().commit();
            return true;
        } catch (RuntimeException rex) {
            session.getTransaction().rollback();
            rex.printStackTrace();
            return false;
        } finally {
            session.close();
            atualizar();
        }
    }

    @Override
    public void salvar() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
            session.saveOrUpdate(selecionado);
            session.getTransaction().commit();
        } catch (RuntimeException rex) {
            session.getTransaction().rollback();
            rex.printStackTrace();
        } finally {
            session.close();
            atualizar();
        }
    }

    @Override
    public void cancelar() {
        atualizar();
    }

    @Override
    public void atualizar() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
            lista = session.getNamedQuery(namedQuery).list();
        } catch (RuntimeException rex) {
//            session.getTransaction().rollback();
            rex.printStackTrace();
        } finally {
//            session.getTransaction().commit();
            session.close();
        }
    }

//  Variables accessed by .xhtml pages
    public List<T> getLista() {
        return lista;
    }

    public void setLista(List<T> lista) {
        this.lista = lista;
    }

    public T getSelecionado() {
        return selecionado;
    }

    public void setSelecionado(T selecionado) {
        this.selecionado = selecionado;
    }
}
