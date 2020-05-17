package br.com.api_rest.resources;

import br.com.api_rest.model.Pessoa;
import br.com.api_rest.repository.PessoaRepository;
import br.com.api_rest.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private PessoaService pessoaService;

    @PostMapping
    public ResponseEntity<Pessoa> registreNewPessoa(@RequestBody @Valid Pessoa pessoa, HttpServletResponse response){
        Pessoa pessoaCadastrada = pessoaRepository.save(pessoa);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}").
                buildAndExpand(pessoaCadastrada.getCodigo()).toUri();

        response.setHeader("Location", uri.toASCIIString());
        return ResponseEntity.created(uri).body(pessoaCadastrada);
    }

    @GetMapping
    public List<Pessoa> findAllPessoas(){
        return pessoaRepository.findAll();
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Pessoa> findById(@PathVariable Integer codigo) {
        Optional<Pessoa> categoria = pessoaRepository.findById(codigo);

        //Retorna 404 caso não encontre nenhuma categoria com o código informado
        return categoria.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<Pessoa> updatePessoa(@PathVariable Integer codigo, @Valid @RequestBody Pessoa pessoa) {
        Pessoa pessoaSalva = pessoaService.updatePessoa(codigo, pessoa);
        return ResponseEntity.ok(pessoaSalva);
    }

    @PutMapping("/{codigo}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatusAtivoPessoa(@PathVariable Integer codigo,
                                        @RequestBody boolean ativo) {
        pessoaService.updateStatusAtivoPessoa(codigo, ativo);
    }

    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePessoa(@PathVariable Integer codigo){
        pessoaRepository.deleteById(codigo);
    }

}
