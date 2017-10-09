/*******************************************************************************
 * Employee ManagedBean
 ******************************************************************************/
package com.owl.managedbean;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.owl.dao.onemenu.OneMenuCargo;
import com.owl.entity.Cargo;
import com.owl.entity.Funcionario;
import com.owl.util.ExclusionStratFuncionario;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

/** Employee ManagedBean
 * @version 1.0
 * @author Maicon Messias
 */
@ManagedBean(name = "funciBean")
@SessionScoped
public class FuncionarioMBean implements Serializable {
    private static final long serialVersionUID = 113154687465457L;
    private List<Funcionario> lista;
    private Funcionario selecionado;
//  Variaveis de internacionalizacao
    private ResourceBundle label;
    private FacesContext context;
    private OneMenuCargo cargoSelecionado;
    
    public FuncionarioMBean() {}

    @PostConstruct
    public void init() {
//      Check and assign the browser language in the application
        setLanguage();    
        atualiza();
        cargoSelecionado = new OneMenuCargo();
    }

    @PreDestroy
    public void onBeforeUnload() {}

    public String edita() {
//      Recovering the employee's profession, excluded with the strategy of exclusion
        cargoSelecionado.editaOneMenu(setCargoFuncionarios(selecionado.getId()));
        return "cadastrofuncionario";
    }

    public String insere() {
//      Creating a new instance of OneMenuCargo
        cargoSelecionado.insereOneMenu();
//      Creating a new instance of Employee (Funcionario)
        Client c = Client.create(); 
        Gson gson = new Gson();
        String url = "http://localhost:8080/WebService-REST-JSON-Jersey-JBOSS7/webresources/webservicemain/incluir/funcionario";
        WebResource wr = c.resource(url);
        String json = wr.get(String.class);
        selecionado = gson.fromJson(json, new TypeToken<Funcionario>() {}.getType());

        return "cadastrofuncionario";
    }

    public void deleta() {
        Client c = Client.create(); 
        Gson gson = new GsonBuilder()
                        .setExclusionStrategies(new ExclusionStratFuncionario())
                        //.serializeNulls() <-- uncomment to serialize NULL fields as well
                        .create();

        WebResource wr = c.resource("http://localhost:8080/WebService-REST-JSON-Jersey-JBOSS7/"
                                    + "webresources/webservicemain/delete/funcionario");
        ClientResponse response = wr.type("application/json")
                                    .accept("application/json")
                                    .post(ClientResponse.class, gson.toJson(selecionado));
        String json = response.getEntity(String.class);
        boolean sucesso = gson.fromJson(json, new TypeToken<Boolean>() {}.getType());

//      Check (and assign if necessary) the browser language in the application
        setLanguage();  
        
        if (sucesso) {           
            getMensagemDialog(FacesMessage.SEVERITY_INFO, 
                              label.getString("dialog_informacao.titulo"), 
                              label.getString("dialog_informacao.mensagem_excluir_sucesso"));
            atualiza();
        } else {
            getMensagemDialog(FacesMessage.SEVERITY_ERROR, 
                              label.getString("dialog_erro.titulo"), 
                              label.getString("dialog_informacao.mensagem_excluir_erro"));
            atualiza();
        }
    }

    public String salva() {
        selecionado.setCargo(setCargoSelecionado());
        Client c = Client.create(); 
        Gson gson = new GsonBuilder()
                        .setExclusionStrategies(new ExclusionStratFuncionario())
                        //.serializeNulls() <-- uncomment to serialize NULL fields as well
                        .create();

        WebResource wr = c.resource("http://localhost:8080/WebService-REST-JSON-Jersey-JBOSS7/"
                                    + "webresources/webservicemain/salvar/funcionario/" + cargoSelecionado.getInstance().getId());
        ClientResponse response = wr.type("application/json")
                                    .accept("application/json")
                                    .post(ClientResponse.class, gson.toJson(selecionado));
        String funciId = response.getEntity(String.class);

//      Check (and assign if necessary) the browser language in the application
        setLanguage();  

        if (funciId.equals("null")) {    
            getMensagemDialog(FacesMessage.SEVERITY_INFO, 
                              label.getString("dialog_informacao.titulo"), 
                              label.getString("dialog_informacao.mensagem_incluir_sucesso"));
            atualiza();
        }else {
            getMensagemDialog(FacesMessage.SEVERITY_INFO, 
                              label.getString("dialog_informacao.titulo"), 
                              label.getString("dialog_informacao.mensagem_editar_sucesso"));
            atualiza();
        }
        return "pesquisafuncionario";
    }

    public String cancela() {
        cargoSelecionado.cancelaOneMenu();
        atualiza();
        return "pesquisafuncionario";
    }

    public String atualiza() {
        Client c = Client.create(); 
        Gson gson = new Gson();
        String url = "http://localhost:8080/WebService-REST-JSON-Jersey-JBOSS7/webresources/webservicemain/get/funcionarios";
        WebResource wr = c.resource(url);
        String json = wr.get(String.class);
        lista = gson.fromJson(json, new TypeToken<List<Funcionario>>() {}.getType());
        
        return "pesquisafuncionario";
    }
    
    public Cargo setCargoFuncionarios(Long idFunci) {
//      Attributed to the employee's profession, which was withdrawn by the exclusion strategy
        Client c = Client.create(); 
        Gson gson = new Gson();
        String url = "http://localhost:8080/WebService-REST-JSON-Jersey-JBOSS7"
                     + "/webresources/webservicemain/set/setCargoFuncionarios/" + idFunci;
        WebResource wr = c.resource(url);
        String json = wr.get(String.class);
        return gson.fromJson(json, new TypeToken<Cargo>() {}.getType());
    }

//  Growl messages
    private void getMensagemDialog(FacesMessage.Severity icon, String titulo, String msg) {
        context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(icon, titulo, msg));
    }
    
    private void setLanguage() {
//      Internationalization resource
        UIViewRoot viewRoot = FacesContext.getCurrentInstance().getViewRoot();  
        Locale locale = viewRoot.getLocale();
   	label = ResourceBundle.getBundle("com.owl.util.language", locale);
    }
    
//  *** selectOneMenu methods ***
//  Checking if it is necessary assing proffission of the employee
    private Cargo setCargoSelecionado() {
        Cargo cargo;
        if (cargoSelecionado.getInstance().getId() > 0) {
            Client c = Client.create(); 
            Gson gson = new GsonBuilder()
                            .setExclusionStrategies(new ExclusionStratFuncionario())
                            //.serializeNulls() <-- uncomment to serialize NULL fields as well
                            .create();
            String url = "http://localhost:8080/WebService-REST-JSON-Jersey-JBOSS7/webresources/webservicemain/get/cargoselecionado";
            WebResource wr = c.resource(url);
            ClientResponse response = wr.type("application/json")
                                        .accept("application/json")
                                        .post(ClientResponse.class, gson.toJson(cargoSelecionado
                                                                                .getInstance()
                                                                                .getId()));
            String json = response.getEntity(String.class);
            cargo = gson.fromJson(json, new TypeToken<Cargo>() {}.getType());
        } else 
            cargo = null;
        return cargo;
    }
    
//  Variables of pages .xhtml        
    public List<Funcionario> getLista() {
        return lista;
    }

    public void setLista(List<Funcionario> lista) {
        this.lista = lista;
    }

    public Funcionario getSelecionado() {
        return selecionado;
    }

    public void setSelecionado(Funcionario selecionado) {
        this.selecionado = selecionado;
    }

    public OneMenuCargo getCargoSelecionado() {
        return cargoSelecionado.getInstance();
    }
}
