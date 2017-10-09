/*******************************************************************************
 * WebService Resource
 ******************************************************************************/
package com.owl.webservicemain;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.owl.dao.entity.CargoDAO;
import com.owl.dao.entity.FuncionarioDAO;
import com.owl.dao.onemenu.OneMenuCargo;
import com.owl.entity.Cargo;
import com.owl.entity.ContatoFuncionario;
import com.owl.entity.Funcionario;
import com.owl.entity.Usuario;
import com.owl.entity.Venda;
import com.owl.util.ExclusionStratFuncionario;
import com.owl.util.HibernateUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import org.hibernate.Session;

/**
 * REST Web Service JSON
 * @version 1.0
 * @author Maicon Messias
 * Main path: http://localhost:8080/WebService-REST-JSON-Jersey-JBOSS7/webresources/webservicemain/
 */
@Stateless
@Path("webservicemain")
public class WebservicemainResource {
    private final FuncionarioDAO funciDAO;
    private final CargoDAO cargoDAO;
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of WebservicemainResource
     */
    public WebservicemainResource() {
        funciDAO = new FuncionarioDAO();
        cargoDAO = new CargoDAO();
    }

    /** Employee
     * Retrieves representation of an instance of com.owl.webservicemain.WebservicemainResource
     * @return an instance of java.lang.String
     */
//  Get all Employees
    @GET
    @Path("get/funcionarios")
    @Produces(MediaType.APPLICATION_JSON)
    public String obterTodosFuncionarios() {
        funciDAO.atualizar();
        Gson gson = new GsonBuilder()
                        .setExclusionStrategies(new ExclusionStratFuncionario())
                        //.serializeNulls() <-- uncomment to serialize NULL fields as well
                        .create();
        return gson.toJson(funciDAO.getLista());
    }

//  Recovering the Employee's profession, excluded with the strategy of exclusion
    @GET
    @Path("set/setCargoFuncionarios/{idFunci}")
    @Produces(MediaType.APPLICATION_JSON)
    public String setCargoFuncionarios(@PathParam("idFunci") Long idFunci) {
        Cargo cargo = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            session.getTransaction().begin();
            cargo = (Cargo) session.createQuery("Select f.cargo from Funcionario f where f.id=:pid")
                                   .setLong("pid", idFunci)
                                   .uniqueResult();
        } catch (RuntimeException rex) {
            rex.printStackTrace();
        } finally {
            session.close();
        }
        Gson gson = new GsonBuilder()
                        .setExclusionStrategies(new ExclusionStratFuncionario())
                        //.serializeNulls() <-- uncomment to serialize NULL fields as well
                        .create();
        return gson.toJson(cargo);
    }
    
//  Creating a new instance of Employee
    @GET
    @Path("incluir/funcionario")
    @Produces(MediaType.APPLICATION_JSON)
    public String incluirFuncionario() {
        Gson gson = new Gson();
        try {
            funciDAO.inserir(Funcionario.class);
        } catch (InstantiationException ex) {
            Logger.getLogger(WebservicemainResource.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(WebservicemainResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        return gson.toJson(funciDAO.getSelecionado());
    }
    
//  Saving a Employee
    @POST
    @Path("salvar/funcionario/{idCargo}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String salvarFuncionario(String funcionario, @PathParam("idCargo") Long idCargo) {
        try {
            Gson gson = new Gson();
            Funcionario funci = gson.fromJson(funcionario, Funcionario.class);

//          Adding employee to related tables, removed when using exclusion strategy

//          Checks whether the employee have a profission
            Cargo cargo = null;
            Session session = HibernateUtil.getSessionFactory().openSession();
            try {
                session.getTransaction().begin();
                cargo = (Cargo) session.get(Cargo.class, idCargo);
            } catch (RuntimeException rex) {
                rex.printStackTrace();
            } finally {
                session.close();
            }

//          Cargo
            if (cargo != null) {
                funci.setCargo(cargo);
                List<Funcionario> listaFunci = new ArrayList();
                listaFunci.add(funci);
                funci.getCargo().setListaFuncionario(listaFunci);
            }
//          ContatoFuncionario
            if (funci.getContatos() != null) {
              Iterator<ContatoFuncionario> itContato = funci.getContatos().iterator();
              while (itContato.hasNext())
                  itContato.next().setFunci(funci);
            }
//          Venda
            if (funci.getVendas() != null) {
              Iterator<Venda> itVenda = funci.getVendas().iterator();
              while (itVenda.hasNext())
                  itVenda.next().setFunci(funci);
            }
//          Usuario
            if (funci.getUsuarios() != null) {
              Iterator<Usuario> itUsuario = funci.getUsuarios().iterator();
              while (itUsuario.hasNext())
                  itUsuario.next().setFunci(funci);
            }
            Long idFunci;
            if (funci.getId() != null)
                idFunci = funci.getId();
            else
                idFunci = null;
            funciDAO.setSelecionado(funci);
            funciDAO.salvar();
            return gson.toJson(idFunci);
        } catch (RuntimeException rex) {
            return rex.getMessage();
        }
    }
    
//  Deleting a Employee
//  The type @DELETE not suport output
    @POST
    @Path("delete/funcionario")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String deletarFuncionario(String funcionario) {
        Gson gson = new Gson();
        Funcionario funci = gson.fromJson(funcionario, Funcionario.class);
        
//      Adding employee to related tables, removed when using exclusion strategy

//      ContatoFuncionario
        if (funci.getContatos() != null) {
          Iterator<ContatoFuncionario> itContato = funci.getContatos().iterator();
          while (itContato.hasNext())
              itContato.next().setFunci(funci);
        }
//      Venda
        if (funci.getVendas() != null) {
          Iterator<Venda> itVenda = funci.getVendas().iterator();
          while (itVenda.hasNext())
              itVenda.next().setFunci(funci);
        }
//      Usuario
        if (funci.getUsuarios() != null) {
          Iterator<Usuario> itUsuario = funci.getUsuarios().iterator();
          while (itUsuario.hasNext())
              itUsuario.next().setFunci(funci);
        }
        funciDAO.setSelecionado(funci);
        return gson.toJson(funciDAO.deletar());
    }
    
    /** Office selectOneMenu
     * Retrieves representation of an instance of com.owl.webservicemain.WebservicemainResource
     * @return an instance of java.lang.String
     */
//  Assing proffission of the employee
    @POST
    @Path("get/cargoselecionado")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String getCargoFuncionario(String idCargoSeleci) {
        Gson gsonEntrada = new Gson();
        Long idCargo = gsonEntrada.fromJson(idCargoSeleci, Long.class);
        OneMenuCargo oneMenuCargo = new OneMenuCargo();
        oneMenuCargo.setInstance(new OneMenuCargo());
        oneMenuCargo.getInstance().setId(idCargo);
        
        Cargo cargo = oneMenuCargo.salvaOneMenu();
        Gson gsonSaida = new GsonBuilder()
                             .setExclusionStrategies(new ExclusionStratFuncionario())
                             //.serializeNulls() <-- uncomment to serialize NULL fields as well
                             .create();
        return gsonSaida.toJson(cargo);
    }
    
    /** Office
     * Retrieves representation of an instance of com.owl.webservicemain.WebservicemainResource
     * @return an instance of java.lang.String
     */
//  Get all Offices (Profission)
    @GET
    @Path("get/cargos")
    @Produces(MediaType.APPLICATION_JSON)
    public String obterTodosCargos() {
        cargoDAO.atualizar();
        Gson gson = new GsonBuilder()
                        .setExclusionStrategies(new ExclusionStratFuncionario())
                        //.serializeNulls() <-- uncomment to serialize NULL fields as well
                        .create();
        return gson.toJson(cargoDAO.getLista());
    }
    
}
