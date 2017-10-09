/*******************************************************************************
 * This class represents an employee entity
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/** Employee entity - Funcionario
 * @version 1.0
 * @author Maicon Messias
 */
@Entity
@Table(name = "funcionario")
@NamedQueries({@NamedQuery(name = "getAllFuncionarios", 
                           query = "Select f from Funcionario f "
                           + "order by f.id asc"
                          ),
               @NamedQuery(name = "findById", query = "Select f from Funcionario f "
                           + " where f.id = :pid")
              })
public class Funcionario implements Serializable {
    
    // Propriedade usada para o versionamento
    @Version
    @Column(name = "versao")
    private int versao;
    
    @Id
    @SequenceGenerator(name = "ID_FUNCIONARIO", sequenceName = "funcionario_id_seq", 
                       allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_FUNCIONARIO")
    private Long id;

    private String nome;
    private String cpf;
    private String rg;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cargo", insertable = true, updatable = true)
    @Fetch(FetchMode.JOIN)
    private Cargo cargo;

    @OneToMany(mappedBy = "funci", fetch = FetchType.LAZY)
    @Cascade(CascadeType.ALL)
    private List<ContatoFuncionario> contatos;
    
    @OneToMany(mappedBy = "funci", fetch = FetchType.LAZY)
    @Cascade(CascadeType.ALL)
    private List<Venda> vendas;
    
    @OneToMany(mappedBy = "funci", fetch = FetchType.LAZY)
    @Cascade(CascadeType.ALL)
    private List<Usuario> usuarios;
    
    public Funcionario() {}

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public List<ContatoFuncionario> getContatos() {
        return contatos;
    }

    public void setContatos(List<ContatoFuncionario> contatos) {
        this.contatos = contatos;
    }

    public List<Venda> getVendas() {
        return vendas;
    }

    public void setVendas(List<Venda> vendas) {
        this.vendas = vendas;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public int getVersao() {
        return versao;
    }

    public void setVersao(int versao) {
        this.versao = versao;
    }
    
    @Override
    public String toString(){
        return String.format("ID: %d\nNome: %s", this.id, this.nome);
    }
}
