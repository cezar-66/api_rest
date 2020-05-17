package br.com.api_rest.dao;

import br.com.api_rest.exception.ExcecaoExperada;
import br.com.api_rest.model.Lancamento;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class LancamentoDAO {

    @Autowired
    private EntityManager entityManager;

    public Lancamento findLancamentoByDescricao(String descricao) throws Exception {
        Query query = this.entityManager.createNativeQuery("select descricao from lancamentos descricao where like descricao '%1%'");
        query.setParameter(1, descricao);
        return (Lancamento) query.getResultList();
    }
}
