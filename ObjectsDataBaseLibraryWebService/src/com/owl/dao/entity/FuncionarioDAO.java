/*******************************************************************************
 * DAO Class of the Employee
*******************************************************************************/
package com.owl.dao.entity;

import com.owl.dao.CadastroMainDAO;
import com.owl.dao.CadastroMainDAOImpl;
import com.owl.entity.Funcionario;
import java.util.List;

/** DAO Class of the Employee
 * @version 1.0
 * @author Maicon Messias
 */
public class FuncionarioDAO implements CadastroMainDAO<Funcionario> {
    private final CadastroMainDAOImpl<Funcionario> cadastroMainDAOImpl;
    
    public FuncionarioDAO() {
        cadastroMainDAOImpl = new CadastroMainDAOImpl("getAllFuncionarios");
    }
    
    @Override
    public void onDestroy() {
        cadastroMainDAOImpl.onDestroy();
    }

    @Override
    public void editar() {
        cadastroMainDAOImpl.editar();
    }

    @Override
    public void inserir(Class<Funcionario> novaClasse) throws InstantiationException, IllegalAccessException {
        cadastroMainDAOImpl.inserir(novaClasse);
    }

    @Override
    public boolean deletar() {
        return cadastroMainDAOImpl.deletar();
    }

    @Override
    public void salvar() {
        cadastroMainDAOImpl.salvar();
    }

    @Override
    public void cancelar() {
        cadastroMainDAOImpl.cancelar();
    }

    @Override
    public void atualizar() {
        cadastroMainDAOImpl.atualizar();
    }
    
 // Variables accessed by .xhtml pages
    public List<Funcionario> getLista() {
        return cadastroMainDAOImpl.getLista();
    }

    public void setLista(List<Funcionario> lista) {
        cadastroMainDAOImpl.setLista(lista);
    }

    public Funcionario getSelecionado() {
        return cadastroMainDAOImpl.getSelecionado();
    }

    public void setSelecionado(Funcionario selecionado) {
        cadastroMainDAOImpl.setSelecionado(selecionado);
    }
}
