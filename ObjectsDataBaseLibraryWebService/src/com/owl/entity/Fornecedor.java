/*******************************************************************************
 * This class represents an provider entity
 ******************************************************************************/
package com.owl.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/** Provider entity - Fornecedor
 * @version 1.0
 * @author Maicon Messias
 */
@Entity
@Table(name = "fornecedor")
public class Fornecedor implements Serializable {
    
    // Propriedade usada para o versionamento
    @Version
    @Column(name = "versao")
    private int versao;
    
    @Id
    private String cnpj;
    
    @Column(name = "nome_fantasia")
    private String nomeFantasia;
    
    @OneToMany(mappedBy = "fornecedor", fetch = FetchType.LAZY)
    @Cascade(CascadeType.ALL)
    private List<Produto> produtos;

    public Fornecedor() {}

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public int getVersao() {
        return versao;
    }

    public void setVersao(int versao) {
        this.versao = versao;
    }
}
