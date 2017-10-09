/*******************************************************************************
 * This class represents an employee contacts entity
 ******************************************************************************/
package com.owl.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/** Employee Contacts entity - Contatos do Funcionario
 * @version 1.0
 * @author Maicon Messias
 */
@Entity
@Table(name = "contato_funcionario")
public class ContatoFuncionario implements Serializable {

    // Propriedade usada para o versionamento
    @Version
    @Column(name = "versao")
    private int versao;
    
    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_funcionario", insertable = true, updatable = true)
    @Fetch(FetchMode.JOIN)
    private Funcionario funci;
    
    @Id
    private String cel;
    
    private String email;

    public ContatoFuncionario() {}

    public Funcionario getFunci() {
        return funci;
    }

    public void setFunci(Funcionario funci) {
        this.funci = funci;
    }

    public String getCel() {
        return cel;
    }

    public void setCel(String cel) {
        this.cel = cel;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getVersao() {
        return versao;
    }

    public void setVersao(int versao) {
        this.versao = versao;
    }
}
