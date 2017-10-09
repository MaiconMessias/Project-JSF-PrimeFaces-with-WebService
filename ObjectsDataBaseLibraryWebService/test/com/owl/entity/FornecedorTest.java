/*******************************************************************************
 * Provider test class
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

/** Provider test class
 * <p>This class tests the assignment and retrieval of properties that are linked 
 * to other entities.</p>
 * @version 1.0
 * @author Maicon Messias
 */
public class FornecedorTest {
    private static String cnpjFornecedor;
    
    public FornecedorTest() {}
    
    @BeforeClass
    public static void setUpClass() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
//          Criando um funcionario teste
            Fornecedor fornecedor = new Fornecedor();
            cnpjFornecedor = "1234";
            fornecedor.setCnpj(cnpjFornecedor);
            fornecedor.setNomeFantasia("Fornecedor teste");
            session.save(fornecedor);
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
            
//          Executando um restart nas sequences para estabelecer o numero corrente
//          antes da execucao dos testes        
            session.createSQLQuery("alter sequence produto_id_seq restart with 1").executeUpdate();
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
     * Test of setProdutos and getProdutos methods, of Provider class.
     */
    @Test
    public void testGetProdutos() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
            Fornecedor fornecedor = (Fornecedor) session.get(Fornecedor.class, cnpjFornecedor);
            Produto prod = new Produto();
            prod.setDescricao("Produto Teste");
            prod.setDetalhe("Detalhe produto teste");
            prod.setFornecedor(fornecedor);
            session.save(prod);
            session.flush();
            List<Produto> produtos = new ArrayList();
            produtos.add(prod);
            fornecedor.setProdutos(produtos);
            session.update(fornecedor);
            session.flush();
            
//          Verificando se o produto foi vinculado ao fornecedor
            fornecedor = (Fornecedor) session.get(Fornecedor.class, cnpjFornecedor);
            String result = fornecedor.getProdutos().get(0).getDescricao();
            String expResult = "Produto Teste";
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
