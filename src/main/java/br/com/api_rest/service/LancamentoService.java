package br.com.api_rest.service;

import br.com.api_rest.dao.LancamentoDAO;
import br.com.api_rest.model.Lancamento;
import br.com.api_rest.model.Pessoa;
import br.com.api_rest.repository.LancamentosRepository;
import br.com.api_rest.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LancamentoService {

    @Autowired
    private LancamentosRepository lancamentosRepository;

    private LancamentoDAO lancamentoDAO = new LancamentoDAO();

    @Autowired
    private PessoaRepository pessoaRepository;

    public Lancamento saveLancamento(Lancamento lancamento){
        Optional<Pessoa> pessoa = pessoaRepository.findById(lancamento.getPessoa().getCodigo());
        if(!pessoa.isPresent()){
            throw new EmptyResultDataAccessException(1);
        }
        if (pessoa.get().isInativo()){
            throw new EmptyResultDataAccessException(1);
        }
        lancamentosRepository.save(lancamento);
        return lancamento;
    }

    public Lancamento findLancamentoByDescricao(String descricao) throws Exception {
        return lancamentoDAO.findLancamentoByDescricao(descricao);
    }

}
