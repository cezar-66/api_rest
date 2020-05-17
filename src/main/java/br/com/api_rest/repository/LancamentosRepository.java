package br.com.api_rest.repository;

import br.com.api_rest.model.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentosRepository extends JpaRepository<Lancamento, Integer> {

}
