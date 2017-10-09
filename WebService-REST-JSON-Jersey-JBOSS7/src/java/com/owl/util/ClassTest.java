/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.owl.util;

/**
 *
 * @author Maicon Messias
 */
public class ClassTest {
    public static void main(String[] args) {
// *******  Resumo dos tipos de metodos do web service utilizados no sistema ***

////      @GET Produzindo (Todos os funcionarios)
//        Client c = Client.create(); 
//        Gson gson = new Gson();
//        String url = "http://localhost:8080/WebServiceRESTJSONJerseyJBOSS7/webresources/webservicemain/funcionarios";
//        WebResource wr = c.resource(url);
//        String json = wr.get(String.class);
//        List<Funcionario> lista = gson.fromJson(json, new TypeToken<List<Funcionario>>() {}.getType());
//        for (Funcionario element : lista)
//            System.out.println(element);

//      @GET Produzindo (Um Funcionario por id)
//        Client c = Client.create(); 
//        Gson gson = new Gson();
//        String url = "http://localhost:8080/WebServiceRESTJSONJerseyJBOSS7/webresources/webservicemain/funcionario/editar/" + 1L;
//        WebResource wr = c.resource(url);
//        String json = wr.get(String.class);
//        Funcionario funci = gson.fromJson(json, new TypeToken<Funcionario>() {}.getType());
//        System.out.println(funci);
//        
//        funci.setNome(funci.getNome() + " (Alterado)");
//        
////      @POST Consumindo (Um Funcionario por id)
//        Client c1 = Client.create(); 
//        Gson gson1 = new GsonBuilder()
//                        .setExclusionStrategies(new ExclusionStratFuncionario())
//                        //.serializeNulls() <-- uncomment to serialize NULL fields as well
//                        .create();
//
//        WebResource wr1 = c1.resource("http://localhost:8080/WebServiceRESTJSONJerseyJBOSS7"
//                                      + "/webresources/webservicemain/salvar/funcionario");
//        ClientResponse response = wr1.type("application/json")
//                                           .accept("application/json")
//                                           .post(ClientResponse.class, gson1.toJson(funci));
//        String funciId = response.getEntity(String.class);
//        String msg = String.format("O funcionário de id: %s, foi alterado!!", funciId);
//        System.out.println(msg);
    }


}
