/*******************************************************************************
 * This class represents an User entity
 ******************************************************************************/
package com.owl.entity;

import java.io.Serializable;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/** User entity - Usuario
 * @version 1.0
 * @author Maicon Messias
 */
@Entity
@Table(name = "usuario")
@NamedQueries({@NamedQuery(name = "getAllUsuarios", 
                           query = "Select u from Usuario u "
                           + "order by u.id asc"
                           )
              })
public class Usuario implements Serializable {
    
    // Propriedade usada para o versionamento
    @Version
    @Column(name = "versao")
    private int versao;
    
    @Id
    @SequenceGenerator(name = "ID_USUARIO", sequenceName = "usuario_id_seq", 
                       allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_USUARIO")
    private Long id;
    
    private String usuario;
    private String senha;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_funcionario", insertable = true, updatable = true)
    @Fetch(FetchMode.JOIN)
    private Funcionario funci;

    public Usuario() {}

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Funcionario getFunci() {
        return funci;
    }

    public void setFunci(Funcionario funci) {
        this.funci = funci;
    }

    public int getVersao() {
        return versao;
    }

    public void setVersao(int versao) {
        this.versao = versao;
    }
}
