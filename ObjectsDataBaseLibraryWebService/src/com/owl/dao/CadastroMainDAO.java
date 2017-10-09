/*******************************************************************************
 * Class responsible for declaring the methods SELECT, INSERT, UPDATE, REFRESH, 
 * DELETE, CANCEL, DESTROY implemented in class CadastroMainDAOImpl.
 ******************************************************************************/
package com.owl.dao;

/** Main cadastre DAO Interface
 * @version 1.0
 * @author Maicon Messias
 * @param <T> Generic type - will be assigned a type by the class that extends it
 */
public interface CadastroMainDAO<T> {
    void onDestroy();
    void editar();
    void inserir(Class<T> novaClasse) throws InstantiationException, IllegalAccessException;
    boolean deletar();
    void salvar();
    void cancelar();
    void atualizar();
}
