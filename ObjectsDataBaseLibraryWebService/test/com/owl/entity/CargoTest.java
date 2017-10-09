/*******************************************************************************
 * Office test class
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

/** Office test class
 * <p>This class tests the assignment and retrieval of properties that are linked 
 * to other entities.</p>
 * @version 1.0
 * @author Maicon Messias
 */
public class CargoTest {
    private static Long idFunci;
    private static Long idCargo;
    
    public CargoTest() {}
    
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
            
//          Criando um cargo teste
            Cargo cargo = new Cargo();
            cargo.setDescricao("Cargo Teste");
            cargo.setSalario(1900);
            idCargo = (Long) session.save(cargo);
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
            
//          Recupera e exclui o cargo usado nos testes
            Cargo cargo = (Cargo) session.get(Cargo.class, idCargo);
            session.delete(cargo);
            session.flush();
            
//          Executando um restart nas sequences para estabelecer o numero corrente
//          antes da execucao dos testes        
            session.createSQLQuery("alter sequence funcionario_id_seq restart with 3").executeUpdate();
            session.createSQLQuery("alter sequence cargo_id_seq restart with 2").executeUpdate();
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
     * Test of setListaFuncionario and getListaFuncionario methods, of Office class.
     */
    @Test
    public void testGetListaFuncionario() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
            Funcionario funci = (Funcionario) session.get(Funcionario.class, idFunci);
            Cargo cargo = (Cargo) session.get(Cargo.class, idCargo);
            List<Funcionario> funcionarios = new ArrayList();
            funcionarios.add(funci);
            cargo.setListaFuncionario(funcionarios);
            session.update(cargo);
            session.flush();
            
//            Verificar se o funcionario foi setado
            cargo = (Cargo) session.get(Cargo.class, idCargo);
            Long result = cargo.getListaFuncionario().get(0).getId();
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
