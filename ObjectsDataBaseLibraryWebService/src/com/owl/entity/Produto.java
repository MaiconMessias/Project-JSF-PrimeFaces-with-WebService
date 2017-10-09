/*******************************************************************************
 * This class represents an product entity
 ******************************************************************************/
package com.owl.entity;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.persistence.Version;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/** Product entity - Produto
 * @version 1.0
 * @author Maicon Messias
 */
@Entity
@Table(name = "produto")
public class Produto implements Serializable {
    
    // Propriedade usada para o versionamento
    @Version
    @Column(name = "versao")
    private int versao;
    
    @Id
    @SequenceGenerator(name = "ID_PRODUTO", sequenceName = "produto_id_seq", 
                       allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_PRODUTO")
    private Long id;
    
    private String descricao;
    private String detalhe;
    private double preco_compra;
    private double preco_venda;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cnpj_fornecedor", insertable = true, updatable = true)
    @Fetch(FetchMode.JOIN)
    private Fornecedor fornecedor;
    
    @OneToMany(mappedBy = "prod", fetch = FetchType.LAZY)
    @Cascade(CascadeType.ALL)
    private List<ItemVenda> itensvenda;


    public Produto() {}

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

    public String getDetalhe() {
        return detalhe;
    }

    public void setDetalhe(String detalhe) {
        this.detalhe = detalhe;
    }

    public double getPreco_compra() {
        return preco_compra;
    }

    public void setPreco_compra(double preco_compra) {
        this.preco_compra = preco_compra;
    }

    public double getPreco_venda() {
        return preco_venda;
    }

    public void setPreco_venda(double preco_venda) {
        this.preco_venda = preco_venda;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
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
