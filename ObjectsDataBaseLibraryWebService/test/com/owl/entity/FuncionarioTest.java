/*******************************************************************************
 * Employee test class
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

/** Employee test class
 * <p>This class tests the assignment and retrieval of properties that are linked 
 * to other entities.</p>
 * @version 1.0
 * @author Maicon Messias
 */
public class FuncionarioTest {
    private static Long idFunci;
    private static Long idCliente;
    
    public FuncionarioTest() {}
    
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
//          Recupera e exclui o funcionario de teste
            Funcionario funci = (Funcionario) session.get(Funcionario.class, idFunci);
            session.delete(funci);
            session.flush();
//          Recupera e exclui o cliente usado nos testes
            Cliente cli = (Cliente) session.get(Cliente.class, idCliente);
            session.delete(cli);
            session.flush();
//          Executando um restart nas sequences para estabelecer o numero corrente
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
     * Test of setCargo and getCargo methods, of Employee class.
     */
    @Test
    public void testSetGetCargo() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
//          Setando o cargo para o funcionario de teste
            Funcionario funci = (Funcionario) session.get(Funcionario.class, idFunci);
            Cargo cargo = (Cargo) session.get(Cargo.class, 1L);
            funci.setCargo(cargo);
            session.update(funci);
            session.flush();
//          Verificando se o cargo foi setado corretamente
            funci = (Funcionario) session.get(Funcionario.class, idFunci);
            Long result = funci.getCargo().getId();
            Long expResult = 1L;
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
     * Test of setContatos and getContatos method, of Employee class.
     */
    @Test
    public void testSetGetContatos() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
//          Criando e setando contatos no funcionario teste
            Funcionario funci = (Funcionario) session.get(Funcionario.class, idFunci);
            List<ContatoFuncionario> contatos = new ArrayList();
            ContatoFuncionario contato = new ContatoFuncionario();
            contato.setFunci(funci);
            contato.setCel("1999999-9999");
            contato.setEmail("teste@teste.com");
            session.save(contato);
            session.flush();
            contatos.add(contato);
            funci.setContatos(contatos);
            session.update(funci);
            session.flush();
            
//          Verificando se o contato foi setado
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

    /**
     * Test of setVendas and getVendas method, of Employee class.
     */
    @Test
    public void testSetGetVendas() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
//          Recupera o funcionario de teste
            Funcionario funci = (Funcionario) session.get(Funcionario.class, idFunci);
//          Recupera o cliente de teste
            Cliente cli = (Cliente) session.get(Cliente.class, idCliente);
//          Cria uma venda teste
            Venda venda = new Venda();
            venda.setDt(Date.valueOf("2017-02-01"));
            venda.setCli(cli);
            venda.setFunci(funci);
            session.save(venda);
            session.flush();
//          Seta a lista de vendas do funcionario
            List<Venda> vendas = new ArrayList();
            vendas.add(venda);
            funci.setVendas(vendas);
            session.update(funci);
            session.flush();
            
//          Verifica se a venda foi setada
            funci = (Funcionario) session.get(Funcionario.class, idFunci);
            Long result = funci.getVendas().get(0).getFunci().getId();
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
