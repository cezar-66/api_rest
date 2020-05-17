package br.com.api_rest.resources;

import br.com.api_rest.model.Lancamento;
import br.com.api_rest.repository.LancamentosRepository;
import br.com.api_rest.service.LancamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {

    @Autowired
    private LancamentosRepository lancamentosRepository;

    @Autowired
    private LancamentoService lancamentoService;

    @PostMapping
    public ResponseEntity<Lancamento> registreNewLancamento(@RequestBody @Valid Lancamento lancamento,
                                                            HttpServletResponse response){
        Lancamento lancamentoSalvo = lancamentoService.saveLancamento(lancamento);

        //Salva na uri da requisição o codigo que foi criado ao cadastrar uma nova categoria
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().
                path("/{codigo}").buildAndExpand(lancamentoSalvo.getCodigo()).toUri();

        response.setHeader("Location", uri.toASCIIString());
        return ResponseEntity.created(uri).body(lancamentoSalvo);

    }

    @GetMapping("/{descricao}")
    public Lancamento filterLancamentoByDescricao(@PathVariable String descricao) throws Exception {
        return lancamentoService.findLancamentoByDescricao(descricao);
    }

    @GetMapping
    public List<Lancamento> findAllLancamentos(){
        return lancamentosRepository.findAll();
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Lancamento> listLancamentoById(@PathVariable Integer codigo){
        Optional<Lancamento> lancamento = lancamentosRepository.findById(codigo);

        return lancamento.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
