package br.com.api_rest.service;

import br.com.api_rest.model.Pessoa;
import br.com.api_rest.repository.PessoaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public Pessoa updatePessoa(Integer codigo, Pessoa pessoa) {
        Optional<Pessoa> pessoaSalva = pessoaRepository.findById(codigo);
        if (pessoaSalva.isPresent()){
            BeanUtils.copyProperties(pessoa, pessoaSalva.get(), "codigo");
            return pessoaRepository.save(pessoaSalva.get());
        }else {
            throw new EmptyResultDataAccessException (1);
        }
    }

    public void updateStatusAtivoPessoa(Integer codigo, boolean ativo) {
        Optional<Pessoa> pessoaSalva = pessoaRepository.findById(codigo);
        if (!pessoaSalva.isPresent()){
            throw new EmptyResultDataAccessException (1);
        }
        pessoaSalva.get().setAtivo(ativo);
        pessoaRepository.save(pessoaSalva.get());
    }
}
