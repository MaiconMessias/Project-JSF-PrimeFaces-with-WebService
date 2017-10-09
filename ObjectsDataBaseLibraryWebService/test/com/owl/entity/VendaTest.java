/*******************************************************************************
 * Sale test class
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

/** Sale test class
 * <p>This class tests the assignment and retrieval of properties that are linked 
 * to other entities.</p>
 * @version 1.0
 * @author Maicon Messias
 */
public class VendaTest {
    private static Long idCliente;
    private static Long idFunci;
    private static Long idProd;
    
    public VendaTest() {}
    
    @BeforeClass
    public static void setUpClass() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
//          Funcionario usado na criacao de uma Venda
            Funcionario funci = new Funcionario();
            funci.setNome("Funcionário Teste");
            funci.setCpf("111.111.111-11");
            funci.setRg("11.111.111-1");
            idFunci = (Long) session.save(funci);
            session.flush();
            
//          Cliente usado na criacao de uma Venda
            Cliente cli = new Cliente();
            cli.setNome("Cliente teste FuncionarioTest");
            cli.setCpf("222.222.222-22");
            idCliente = (Long) session.save(cli);
            
//          Produto usado na criacao de um ItemVenda
            Produto prod = new Produto();
            prod.setDescricao("Produto Teste");
            prod.setDetalhe("Produto para testar classe Venda");
            idProd = (Long) session.save(prod);
            
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
//          Recupera e exclui o Produto usado nos testes
            Produto prod = (Produto) session.get(Produto.class, idProd);
            session.delete(prod);
            session.flush();

//          Recupera e exclui o funcionario usado nos testes
            Funcionario funci = (Funcionario) session.get(Funcionario.class, idFunci);
            session.delete(funci);
            session.flush();
            
//          Recuperando e excluindo o cliente usado nos testes
            Cliente cli = (Cliente) session.get(Cliente.class, idCliente);
            session.delete(cli);
            session.flush();
            
//          Executando um restart nas sequences para estabelecer o numero corrente
//          antes da execucao dos testes        
            session.createSQLQuery("alter sequence produto_id_seq restart with 1").executeUpdate();
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
     * Test of setFunci and getFunci method, of Sale class.
     */
    @Test
    public void testSetGetFunci() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
            Funcionario funci = (Funcionario) session.get(Funcionario.class, idFunci);
            Cliente cli = (Cliente) session.get(Cliente.class, idCliente);
            Venda venda = new Venda();
            venda.setFunci(funci);
            venda.setCli(cli);
            Long idVenda = (Long) session.save(venda);
            session.flush();
            
//          Verificanndo se o Funcionario foi setado na venda
            venda = (Venda) session.get(Venda.class, idVenda);
            Long result = venda.getFunci().getId();
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

    /**
     * Test of setCli and getCli methods, of Sale class.
     */
    @Test
    public void testSetGetCli() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
            Funcionario funci = (Funcionario) session.get(Funcionario.class, idFunci);
            Cliente cli = (Cliente) session.get(Cliente.class, idCliente);
            Venda venda = new Venda();
            venda.setFunci(funci);
            venda.setCli(cli);
            Long idVenda = (Long) session.save(venda);
            session.flush();
            
//          Verificanndo se o Cliente foi setado na venda
            venda = (Venda) session.get(Venda.class, idVenda);
            Long result = venda.getCli().getId();
            Long expResult = idCliente;
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
     * Test of setItensvenda and getItensvenda methods, of Sale class.
     */
    @Test
    public void testSetGetItensvenda() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
            Funcionario funci = (Funcionario) session.get(Funcionario.class, idFunci);
            Cliente cli = (Cliente) session.get(Cliente.class, idCliente);
            Produto prod = (Produto) session.get(Produto.class, idProd);
            Venda venda = new Venda();
            venda.setFunci(funci);
            venda.setCli(cli);
            ItemVenda itv = new ItemVenda();
            itv.setProd(prod);
            itv.setVenda(venda);
            List<ItemVenda> itensVenda = new ArrayList();
            itensVenda.add(itv);
            venda.setItensvenda(itensVenda);
            Long idVenda = (Long) session.save(venda);
            session.flush();
            
//          Verificanndo se o Item Venda foi setado na venda
            venda = (Venda) session.get(Venda.class, idVenda);
            Long result = venda.getItensvenda().get(0).getVenda().getId();
            Long expResult = idVenda;
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
