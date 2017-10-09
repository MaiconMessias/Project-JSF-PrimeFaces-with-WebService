/*******************************************************************************
 * Office ManagedBean
 ******************************************************************************/
package com.owl.managedbean;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.owl.entity.Cargo;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/** Office ManagedBean
 * @version 1.0
 * @author Maicon Messias
 */
@ManagedBean(name = "cargoBean")
@SessionScoped
public class CargoMBean implements Serializable{
    private static final long serialVersionUID = 113154687465458L;
    private List<Cargo> lista;
    private Cargo selecionado;

    public CargoMBean() {}
 
    @PostConstruct
    public void init() {
        atualiza();
    }

    public String atualiza() {
        Client c = Client.create(); 
        Gson gson = new Gson();
        String url = "http://localhost:8080/WebService-REST-JSON-Jersey-JBOSS7/webresources/webservicemain/get/cargos";
        WebResource wr = c.resource(url);
        String json = wr.get(String.class);
        lista = gson.fromJson(json, new TypeToken<List<Cargo>>() {}.getType());
        
        return "pesquisacargo";
    }
    
//  Variables of pages .xhtml        

    public List<Cargo> getLista() {
        return lista;
    }

    public void setLista(List<Cargo> lista) {
        this.lista = lista;
    }

    public Cargo getSelecionado() {
        return selecionado;
    }

    public void setSelecionado(Cargo selecionado) {
        this.selecionado = selecionado;
    }
}
