/*******************************************************************************
 * Custumer test class
 ******************************************************************************/
package com.owl.entity;

import com.owl.util.HibernateUtil;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/** Custumer test class
 * <p>This class tests the assignment and retrieval of properties that are linked 
 * to other entities.</p>
 * @version 1.0
 * @author Maicon Messias
 */
public class ClienteTest {
    private static Long idCli;
    private static Long idFunci;
    
    public ClienteTest() {}
    
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
            session.flush();
            
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
//          Recupera e exclui o funcionario de teste
            Funcionario funci = (Funcionario) session.get(Funcionario.class, idFunci);
            session.delete(funci);
            session.flush();
            
//          Recuperando e excluindo o cliente teste
            Cliente cli = (Cliente) session.get(Cliente.class, idCli);
            session.delete(cli);
            session.flush();
//          Executando um restart na sequence para estabelecer o numero corrente
//          antes da execucao dos testes        
            session.createSQLQuery("alter sequence funcionario_id_seq restart with 3").executeUpdate();
            session.createSQLQuery("alter sequence cliente_id_seq restart with 1").executeUpdate();
            session.createSQLQuery("alter sequence venda_id_seq restart with 1").executeUpdate();
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
     * Test of setContatos and getContatos methods, of Custumer class.
     */
    @Test
    public void testSetGetContatos() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
//          Setando um contato
            Cliente cli = (Cliente) session.get(Cliente.class, idCli);
            ContatoCliente ctc = new ContatoCliente();
            ctc.setCliente(cli);
            ctc.setCel("1999999-9999");
            ctc.setEmail("teste@teste.com");
            List<ContatoCliente> contatos = new ArrayList();
            contatos.add(ctc);
            cli.setContatos(contatos);
            session.update(cli);
            session.flush();
            
//          Verificando se o contato foi setado
            cli = (Cliente) session.get(Cliente.class, idCli);
            String result = cli.getContatos().get(0).getCel();
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

    /**
     * Test of setVendas and getVendas methods, of Custumer class.
     */
    @Test
    public void testSetGetVendas() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
//          Setando uma venda
            Cliente cli = (Cliente) session.get(Cliente.class, idCli);
            Funcionario funci = (Funcionario) session.get(Funcionario.class, idFunci);
            Venda venda = new Venda();
            venda.setCli(cli);
            venda.setFunci(funci);
            venda.setDt(Date.valueOf("2017-02-01"));
            List<Venda> vendas = new ArrayList();
            vendas.add(venda);
            cli.setVendas(vendas);
            session.update(cli);
            session.flush();
            
//          Verificando se a venda foi setada
            cli = (Cliente) session.get(Cliente.class, idCli);
            Long result = cli.getVendas().get(0).getCli().getId();
            Long expResult = idCli;
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
