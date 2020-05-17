package br.com.api_rest.resources;

import br.com.api_rest.repository.CategoriaRepository;
import br.com.api_rest.model.Categoria;
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
@RequestMapping("/categorias")
public class CategoriaResource {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @PostMapping
    public ResponseEntity<Categoria> registrerNewCategoria(@RequestBody @Valid Categoria categoria,
                                                           HttpServletResponse response){

        Categoria categoriaSalva = categoriaRepository.save(categoria);

        //Salva na uri da requisição o codigo que foi criado ao cadastrar uma nova categoria
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().
                path("/{codigo}").buildAndExpand(categoriaSalva.getCodigo()).toUri();

        response.setHeader("Location", uri.toASCIIString());
        return ResponseEntity.created(uri).body(categoriaSalva);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Categoria> findById(@PathVariable Integer codigo) {
        Optional<Categoria> categoria = categoriaRepository.findById(codigo);

        //Retorna 404 caso não encontre nenhuma categoria com o código informado
        return categoria.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    private List<Categoria> listAllCategorias(){
        return categoriaRepository.findAll();
    }

}
