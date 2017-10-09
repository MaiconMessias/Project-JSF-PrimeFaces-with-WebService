/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.owl.managedbeaninterface;

/**
 *
 * @author Maicon Messias
 */
public interface CadastroMBeanMainInterface {
    void init();
    void onBeforeUnload();
    String edita();
    String insere() throws InstantiationException, IllegalAccessException;
    void deleta();
    String salva();
    String cancela();
    String atualiza();
}
