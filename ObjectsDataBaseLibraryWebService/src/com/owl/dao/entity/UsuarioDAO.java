/*******************************************************************************
 * DAO Class of the User
*******************************************************************************/
package com.owl.dao.entity;

import com.owl.dao.CadastroMainDAO;
import com.owl.dao.CadastroMainDAOImpl;
import com.owl.entity.Usuario;
import com.owl.util.HibernateUtil;
import java.util.List;
import org.hibernate.Session;

/** DAO Class of the User
 * @version 1.0
 * @author Maicon Messias
 */
public class UsuarioDAO implements CadastroMainDAO<Usuario> {
    private final CadastroMainDAOImpl<Usuario> cadastroMainDAOImpl;

    public UsuarioDAO() {
        cadastroMainDAOImpl = new CadastroMainDAOImpl("getAllUsuarios");
    }
    
    protected boolean validarUsuario(String usuario, String senha) {
        Usuario user = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
            user = (Usuario) session.createQuery("from Usuario u "
                                                + "where upper(u.usuario) = upper(:usuario)"
                                                + "  and upper(u.senha) = upper(:senha)")
                                                .setParameter("usuario", usuario)
                                                .setParameter("senha", senha)
                                                .uniqueResult();
            session.getTransaction().commit();
        } catch (Exception ex) {
            if (session.getTransaction().isActive())
                session.getTransaction().rollback();
            if (session.isOpen())
                session.close();
            ex.printStackTrace();
            return false;
        } finally {
            if (session.isOpen())
                session.close();
        }
        if (user == null)
            return false;
        else
            return true;
    }

    @Override
    public void onDestroy() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void inserir(Class<Usuario> novaClasse) throws InstantiationException, IllegalAccessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deletar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void salvar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cancelar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void atualizar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //  Variables accessed by .xhtml pages
    public List<Usuario> getLista() {
        return cadastroMainDAOImpl.getLista();
    }

    public void setLista(List<Usuario> lista) {
        cadastroMainDAOImpl.setLista(lista);
    }

    public Usuario getSelecionado() {
        return cadastroMainDAOImpl.getSelecionado();
    }

    public void setSelecionado(Usuario selecionado) {
        cadastroMainDAOImpl.setSelecionado(selecionado);
    }

}
