/*******************************************************************************
 * This class represents an custumer entity
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/** Custumer entity - Cliente
 * @version 1.0
 * @author Maicon Messias
 */
@Entity
@Table(name = "cliente")
public class Cliente implements Serializable {

    // Propriedade usada para o versionamento
    @Version
    @Column(name = "versao")
    private int versao;

    @Id
    @SequenceGenerator(name = "ID_CLIENTE", sequenceName = "cliente_id_seq", 
                       allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_CLIENTE")
    private Long id;
    
    private String nome;
    private String cpf;
    
    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    @Cascade(CascadeType.ALL)
    private List<ContatoCliente> contatos;
    
    @OneToMany(mappedBy = "cli", fetch = FetchType.LAZY)
    @Cascade(CascadeType.ALL)
    private List<Venda> vendas;

    public Cliente() {}

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

    public List<ContatoCliente> getContatos() {
        return contatos;
    }

    public void setContatos(List<ContatoCliente> contatos) {
        this.contatos = contatos;
    }

    public List<Venda> getVendas() {
        return vendas;
    }

    public void setVendas(List<Venda> vendas) {
        this.vendas = vendas;
    }

    public int getVersao() {
        return versao;
    }

    public void setVersao(int versao) {
        this.versao = versao;
    }
}
