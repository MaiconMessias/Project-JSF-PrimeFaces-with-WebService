/*******************************************************************************
 * Product test class
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

/** Product test class
 * <p>This class tests the assignment and retrieval of properties that are linked 
 * to other entities.</p>
 * @version 1.0
 * @author Maicon Messias
 */
public class ProdutoTest {
    private static String cnpjFornecedor;
    private static Long idCliente;
    private static Long idFunci;
    
    public ProdutoTest() {}
    
    @BeforeClass
    public static void setUpClass() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
//          Criando um fornecedor teste
            Fornecedor fornecedor = new Fornecedor();
            cnpjFornecedor = "1234";
            fornecedor.setCnpj(cnpjFornecedor);
            fornecedor.setNomeFantasia("Fornecedor teste");
            session.save(fornecedor);
            session.flush();
            
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
//          Recupera e exclui o fornecedor usado nos testes
            Fornecedor fornecedor = (Fornecedor) session.get(Fornecedor.class, cnpjFornecedor);
            session.delete(fornecedor);
            session.flush();

//          Recupera e exclui o funcionario de teste
            Funcionario funci = (Funcionario) session.get(Funcionario.class, idFunci);
            session.delete(funci);
            session.flush();
            
//          Recuperando e excluindo o cliente teste
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
     * Test of setFornecedor and getFornecedor methods, of Product class.
     */
    @Test
    public void testSetGetFornecedor() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
            Fornecedor fornecedor = (Fornecedor) session.get(Fornecedor.class, cnpjFornecedor);
            Produto prod = new Produto();
            prod.setDescricao("Produto Teste");
            prod.setDetalhe("Testando atribuição de fornecedor");
            prod.setFornecedor(fornecedor);
            Long idProd = (Long) session.save(prod);
            session.flush();
            
//          Verificando se o fornecedor foi setado
            prod = (Produto) session.get(Produto.class, idProd);
            String result = prod.getFornecedor().getCnpj();
            String expResult = cnpjFornecedor;
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
     * Test of setItensvenda and getItensvenda methods, of Product class.
     */
    @Test
    public void testSetGetItensvenda() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
            Fornecedor fornecedor = (Fornecedor) session.get(Fornecedor.class, cnpjFornecedor);
//          Funcionario necessario para cricao da venda
            Funcionario funci = (Funcionario) session.get(Funcionario.class, idFunci);
//          Cliente necessario para cricao da venda
            Cliente cli = (Cliente) session.get(Cliente.class, idCliente);
            Venda venda = new Venda();
            venda.setFunci(funci);
            venda.setCli(cli);
            Long idVenda = (Long) session.save(venda);
            session.flush();
            
            Produto prod = new Produto();
            prod.setDescricao("Produto Teste");
            prod.setDetalhe("Testando atribuição de fornecedor");
            prod.setFornecedor(fornecedor);
            
            ItemVenda itvenda = new ItemVenda();
            itvenda.setProd(prod);
            itvenda.setVenda(venda);
            List<ItemVenda> itensVenda = new ArrayList();
            itensVenda.add(itvenda);
            prod.setItensvenda(itensVenda);
            Long idProd = (Long) session.save(prod);
            session.flush();
            
//          Verificando se o item venda criado foi setado
            prod = (Produto) session.get(Produto.class, idProd);
            Long result = prod.getItensvenda().get(0).getVenda().getId();
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
