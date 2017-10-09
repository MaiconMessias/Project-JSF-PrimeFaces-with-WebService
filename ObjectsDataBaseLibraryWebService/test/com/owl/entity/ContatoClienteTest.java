/*******************************************************************************
 * Custumer Contacts test class
 ******************************************************************************/
package com.owl.entity;

import com.owl.util.HibernateUtil;
import org.hibernate.Session;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/** Custumer Contacts test class
 * <p>This class tests the assignment and retrieval of properties that are linked 
 * to other entities.</p>
 * @version 1.0
 * @author Maicon Messias
 */
public class ContatoClienteTest {
    private static Long idCli;
    
    public ContatoClienteTest() {}
    
    @BeforeClass
    public static void setUpClass() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
//          Criando um cliente teste
            Cliente cli = new Cliente();
            cli.setNome("Cliente teste JUnit");
            cli.setCpf("111.111.111-11");
            idCli = (Long) session.save(cli);
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
//          Recuperando e excluindo o cliente teste
            Cliente cli = (Cliente) session.get(Cliente.class, idCli);
            session.delete(cli);
            session.flush();
//          Executando um restart na sequence para estabelecer o numero corrente
//          antes da execucao dos testes        
            session.createSQLQuery("alter sequence cliente_id_seq restart with 1").executeUpdate();
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
     * Test of setCliente and getCliente methods, of Custumer Contacts class.
     */
    @Test
    public void testSetGetCliente() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
//          Criando um Contato para o cliente teste
            Cliente cli = (Cliente) session.get(Cliente.class, idCli);
            ContatoCliente ctc = new ContatoCliente();
            ctc.setCliente(cli);
            ctc.setCel("1999999-9999");
            ctc.setEmail("teste@teste.com");
            session.save(ctc);
            session.flush();
            
//          Verificando se no cliente foi setado no contato criado
            cli = (Cliente) session.get(Cliente.class, idCli);
            String result = cli.getContatos().get(0).getCel();
            String expResult = "1999999-9999";
            Assert.assertEquals(expResult, result);
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
