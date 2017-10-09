/*******************************************************************************
 * Item for Sale test class
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

/** Item for Sale test class
 * <p>This class tests the assignment and retrieval of properties that are linked 
 * to other entities.</p>
 * @version 1.0
 * @author Maicon Messias
 */
public class ItemVendaTest {
    private static Long idCliente;
    private static Long idFunci;
    private static Long idProd;
    
    public ItemVendaTest() {}
    
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
     * Test of setVenda and getVenda methods, of Item for Sale class.
     */
    @Test
    public void testSetGetVenda() {
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
            
//          Verificanndo se a Venda foi setado no Item Venda.
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

    /**
     * Test of setProd and getProd methods, of Item for Sale class.
     */
    @Test
    public void testSetGetProd() {
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
            
//          Verificanndo se o Produto foi setado no Item Venda.
            venda = (Venda) session.get(Venda.class, idVenda);
            Long result = venda.getItensvenda().get(0).getProd().getId();
            Long expResult = idProd;
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
