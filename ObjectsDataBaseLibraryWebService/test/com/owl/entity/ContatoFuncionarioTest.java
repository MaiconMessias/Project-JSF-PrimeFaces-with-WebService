/*******************************************************************************
 * Employee Contacts test class
 ******************************************************************************/
package com.owl.entity;

import com.owl.util.HibernateUtil;
import org.hibernate.Session;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/** Employee Contacts test class
 * <p>This class tests the assignment and retrieval of properties that are linked 
 * to other entities.</p>
 * @version 1.0
 * @author Maicon Messias
 */
public class ContatoFuncionarioTest {
    private static Long idFunci;
    
    public ContatoFuncionarioTest() {}
    
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
//          Recupera e exclui o funcionario usado nos testes
            Funcionario funci = (Funcionario) session.get(Funcionario.class, idFunci);
            session.delete(funci);
            session.flush();
            
//          Executando um restart nas sequences para estabelecer o numero corrente
//          antes da execucao dos testes        
            session.createSQLQuery("alter sequence funcionario_id_seq restart with 3").executeUpdate();
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
     * Test of setFunci and getFunci methods, of Employee Contacts class.
     */
    @Test
    public void testSetGetFunci() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
            Funcionario funci = (Funcionario) session.get(Funcionario.class, idFunci);
            ContatoFuncionario ctf = new ContatoFuncionario();
            ctf.setFunci(funci);
            ctf.setCel("1999999-9999");
            ctf.setEmail("teste@teste.com");
            session.save(ctf);
            session.flush();
            
//          Verificando se o funcionario foi setado no contato
            funci = (Funcionario) session.get(Funcionario.class, idFunci);
            String result = funci.getContatos().get(0).getCel();
            String expResult = "1999999-9999";
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
