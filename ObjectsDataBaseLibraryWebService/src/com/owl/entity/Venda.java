/*******************************************************************************
 * This class represents an sale entity
 ******************************************************************************/
package com.owl.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/** Sale entity - Venda
 * @version 1.0
 * @author Maicon Messias
 */
@Entity
@Table(name = "venda")
public class Venda implements Serializable {

    // Propriedade usada para o versionamento
    @Version
    @Column(name = "versao")
    private int versao;

    @Id
    @SequenceGenerator(name = "ID_VENDA", sequenceName = "venda_id_seq", 
                       allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_VENDA")
    private Long id;
    
    @Temporal(TemporalType.DATE)
    private Date dt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_funcionario", insertable = true, updatable = true, 
                nullable = false)
    @Fetch(FetchMode.JOIN)
    private Funcionario funci;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cliente", insertable = true, updatable = true, 
                nullable = false)
    @Fetch(FetchMode.JOIN)
    private Cliente cli;
    
    @OneToMany(mappedBy = "venda", fetch = FetchType.LAZY)
    @Cascade(CascadeType.ALL)
    private List<ItemVenda> itensvenda;
    
    public Venda() {}

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public Funcionario getFunci() {
        return funci;
    }

    public void setFunci(Funcionario funci) {
        this.funci = funci;
    }

    public Cliente getCli() {
        return cli;
    }

    public void setCli(Cliente cli) {
        this.cli = cli;
    }

    public List<ItemVenda> getItensvenda() {
        return itensvenda;
    }

    public void setItensvenda(List<ItemVenda> itensvenda) {
        this.itensvenda = itensvenda;
    }

    public int getVersao() {
        return versao;
    }

    public void setVersao(int versao) {
        this.versao = versao;
    }
}
