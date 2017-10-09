/*******************************************************************************
 * This class is reserved to test some system resource
 ******************************************************************************/
package com.owl.util;

import com.owl.entity.Funcionario;
import org.hibernate.Session;

/** Tests class
 * @author Maicon Messias
 */
public class MainLibraryTest {
    public static void main(String[] args) {
        Session s = HibernateUtil.getSessionFactory().openSession();
        try {
            s.getTransaction().begin();
            
//            Funcionario funci = (Funcionario) s.get(Funcionario.class, 2L);
//            System.out.println(funci.getNome());            
//            funci.setNome("Messias3");
//            List<ContatoFuncionario> contatos = new ArrayList();
//            ContatoFuncionario contato = new ContatoFuncionario();
//            contato.setCel("1995559-7520");
//            contato.setEmail("funcionario2_owl@owl.com");
//            contato.setFunci(funci);
//            contatos.add(contato);
//            funci.setContatos(contatos);
//            s.update(funci);

//            Funcionario f = new Funcionario();
//            Cargo cargo = (Cargo) s.get(Cargo.class, 1L);
//            f.setNome("Messias2");
//            f.setCpf("222.222.222-22");
//            f.setRg("22.222.222-2");
//            f.setCargo(cargo);
//            s.save(f);
//            System.out.println(new Date(System.currentTimeMillis()));

//            Venda venda = (Venda) s.get(Venda.class, 1L);
//            s.delete(venda);
//            s.flush();
//            System.out.println(venda.getItensvenda().get(0).getQtde());
//            System.out.println(venda.getDt());

//              ItemVenda itVenda = (ItemVenda) s.createQuery("from ItemVenda").uniqueResult();
//              System.out.println(itVenda.getQtde());
            s.getTransaction().commit();
        } catch(RuntimeException rex) {
            if (s.getTransaction().isActive())
                s.getTransaction().rollback();
            if (s.isOpen())
                s.close();
            rex.getStackTrace();
        } finally {
            if (s.isOpen())
                s.close();
        }
    }
}
