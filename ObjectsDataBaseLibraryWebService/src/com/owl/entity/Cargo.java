/*******************************************************************************
 * This class represents an office entity
 ******************************************************************************/
package com.owl.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/** Office entity - Cargo
 * @version 1.0
 * @author Maicon Messias
 */
@Entity
@Table(name = "cargo")
@NamedQueries({@NamedQuery(name = "getAllCargos", query = "Select c from Cargo c "
                           + "order by c.id asc"
                          )
              })
public class Cargo implements Serializable {
    
    // Propriedade usada para o versionamento
    @Version
    @Column(name = "versao")
    private int versao;

    @Id
    @SequenceGenerator(name = "ID_CARGO", sequenceName = "cargo_id_seq", 
                       allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_CARGO")
    private Long id;
    
    private String descricao;
    private double salario;
    
    @OneToMany(mappedBy = "cargo", fetch = FetchType.LAZY)
    @Cascade(CascadeType.ALL)
    private List<Funcionario> listaFuncionario;

    public Cargo() {}

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public List<Funcionario> getListaFuncionario() {
        return listaFuncionario;
    }

    public void setListaFuncionario(List<Funcionario> listaFuncionario) {
        this.listaFuncionario = listaFuncionario;
    }

    public int getVersao() {
        return versao;
    }

    public void setVersao(int versao) {
        this.versao = versao;
    }
}
