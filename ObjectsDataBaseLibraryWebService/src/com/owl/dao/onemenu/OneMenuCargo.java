/*******************************************************************************
 * Procedures that facilitate the creation of a OneMenu that lists the 
 * existing professions.
 ******************************************************************************/
package com.owl.dao.onemenu;

import com.owl.dao.onemenu.interfaceonemenu.OneMenuCargoInterface;
import com.owl.entity.Cargo;
import com.owl.util.HibernateUtil;
import org.hibernate.Session;

/** OneMenu Office class implementation
 * @version 1.0
 * @author Maicon Messias
 */
public class OneMenuCargo extends Cargo implements OneMenuCargoInterface {
    private Long id;
    private OneMenuCargo instance;
    
    public OneMenuCargo() {}
    
    @Override
    public void editaOneMenu(Cargo cargoFunci) {
        if (cargoFunci != null) {
            this.instance = new OneMenuCargo();
            this.instance.setId(cargoFunci.getId());
            this.instance.setDescricao(cargoFunci.getDescricao());
            this.instance.setSalario(cargoFunci.getSalario());
            this.instance.setListaFuncionario(cargoFunci.getListaFuncionario());
        } else {
            this.instance = new OneMenuCargo();
        }
    }

    @Override
    public void insereOneMenu() {
        this.instance = new OneMenuCargo();
    }

    @Override
    public Cargo salvaOneMenu() {
        Cargo cargo = null;
        if (this.instance.getId() != null) {
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                session.getTransaction().begin();
                cargo = (Cargo) session.get(Cargo.class, this.instance.getId());
            } catch (RuntimeException rex) {
                session.getTransaction().rollback();
                rex.printStackTrace();
            } finally {
                session.getTransaction().commit();
                session.close();
            } 
        } else {
            this.instance = null;
        }
        return cargo;
    }

    @Override
    public void cancelaOneMenu() {
        this.instance = null;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OneMenuCargo getInstance() {
        return instance;
    }

    public void setInstance(OneMenuCargo instance) {
        this.instance = instance;
    }
    
    
    
}