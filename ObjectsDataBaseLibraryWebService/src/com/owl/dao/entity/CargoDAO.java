/*******************************************************************************
 * DAO Class of the Office
*******************************************************************************/
package com.owl.dao.entity;

import com.owl.dao.CadastroMainDAO;
import com.owl.dao.CadastroMainDAOImpl;
import com.owl.entity.Cargo;
import java.util.List;

/** DAO Class of the Office
 * @version 1.0
 * @author Maicon Messias
 */
public class CargoDAO implements CadastroMainDAO<Cargo> {
    private final CadastroMainDAOImpl<Cargo> cadastroMainDAOImpl;

    public CargoDAO() {
        cadastroMainDAOImpl = new CadastroMainDAOImpl("getAllCargos");
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
    public void inserir(Class<Cargo> novaClasse) throws InstantiationException, IllegalAccessException {
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
    
//  Variables accessed by .xhtml pages
    public List<Cargo> getLista() {
        return cadastroMainDAOImpl.getLista();
    }

    public void setLista(List<Cargo> lista) {
        cadastroMainDAOImpl.setLista(lista);
    }

    public Cargo getSelecionado() {
        return cadastroMainDAOImpl.getSelecionado();
    }

    public void setSelecionado(Cargo selecionado) {
        cadastroMainDAOImpl.setSelecionado(selecionado);
    }

}
