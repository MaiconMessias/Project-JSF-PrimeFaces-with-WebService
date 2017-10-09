/*******************************************************************************
 * User test class
 ******************************************************************************/
package com.owl.entity;

import com.owl.util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/** User test class
 * @version 1.0
 * @author Maicon Messias
 */
public class UsuarioTest {
    private static Long idFunci;
    
    public UsuarioTest() {}
    
    @BeforeClass
    public static void setUpClass() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
//          Criando um funcionario teste
            Funcionario funci = new Funcionario();
            funci.setNome("Funcionário Teste");
            funci.setCpf("111.111.111-11");
            funci.setRg("11.111.111-1");
            idFunci = (Long) session.save(funci);
            session.getTransaction().commit();
        } catch (Exception ex) {
            if (session.getTransaction().isActive())
                session.getTransaction().rollback();
            if (session.isOpen())
                session.close();
            ex.printStackTrace();
        } finally {
            if (session.isOpen())
                session.close();
        }
    }
    
    @AfterClass
    public static void tearDownClass() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
//          Recupera e exclui o funcionario de teste
            Funcionario funci = (Funcionario) session.get(Funcionario.class, idFunci);
            session.delete(funci);
            session.flush();
//          Executando um restart nas sequences para estabelecer o numero corrente
//          antes da execucao dos testes        
            session.createSQLQuery("alter sequence funcionario_id_seq restart with 3").executeUpdate();
            session.createSQLQuery("alter sequence usuario_id_seq restart with 1").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception ex) {
            if (session.getTransaction().isActive())
                session.getTransaction().rollback();
            if (session.isOpen())
                session.close();
            ex.printStackTrace();
        } finally {
            if (session.isOpen())
                session.close();
        }
    }

    /**
     * Test of setFunci and getFunci methods, of User class.
     */
    @Test
    public void testSetGetFunci() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
            Funcionario funci = (Funcionario) session.get(Funcionario.class, idFunci);
            Usuario user = new Usuario();
            user.setFunci(funci);
            user.setUsuario("Usuario Teste");
            user.setSenha("123");
            Long idUsuario = (Long) session.save(user);
            session.flush();
            List<Usuario> usuarios = new ArrayList();
            usuarios.add(user);
            funci.setUsuarios(usuarios);
            session.update(funci);
            session.flush();
            
//          Verificando se o funcionario foi setado no usuario
            user = (Usuario) session.get(Usuario.class, idUsuario);
            Long result = user.getFunci().getId();
            Long expResult = idFunci;
            assertEquals(expResult, result);
            session.getTransaction().commit();
        } catch (Exception ex) {
            if (session.getTransaction().isActive())
                session.getTransaction().rollback();
            if (session.isOpen())
                session.close();
            ex.printStackTrace();
        } finally {
            if (session.isOpen())
                session.close();
        }
    }
}
