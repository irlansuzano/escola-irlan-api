package br.edu.infnet.biblioteca_irlan_api.livro;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/livros")
@Tag(name = "Livro", description = "Endpoints para gestão de livros")
public class LivroController {

    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @GetMapping
    @Operation(summary = "Listar todos os livros", description = "Retorna uma lista de todos os livros cadastrados")
    public ResponseEntity<Collection<LivroResponseDTO>> obterLivros() {
        return ResponseEntity.ok(livroService.obterLivros());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter livro por ID", description = "Retorna os detalhes de um livro específico pelo seu ID")
    public ResponseEntity<LivroResponseDTO> obterLivroPorId(@PathVariable Long id) {
        return ResponseEntity.ok(livroService.obterLivroPorId(id));
    }

    @GetMapping("/titulo/{titulo}")
    @Operation(summary = "Obter livro por título", description = "Busca um livro através do seu título")
    public ResponseEntity<LivroResponseDTO> buscarLivroPorTitulo(@PathVariable String titulo) {
        return ResponseEntity.ok(livroService.buscarLivroPorTitulo(titulo));
    }

    @PostMapping
    @Operation(summary = "Incluir livro", description = "Cria um novo registro de livro no sistema")
    public ResponseEntity<LivroResponseDTO> incluirLivro(@Valid @RequestBody LivroRequestDTO livro) {
        return ResponseEntity.status(CREATED).body(livroService.cadastrarLivro(livro));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Alterar livro", description = "Atualiza os dados de um livro existente")
    public ResponseEntity<LivroResponseDTO> alterarLivro(@PathVariable Long id, @Valid @RequestBody LivroRequestDTO livro) {
        return ResponseEntity.ok(livroService.alterarLivro(id, livro));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir livro", description = "Exclui o registro de um livro do sistema")
    public ResponseEntity<Void> excluirLivro(@PathVariable Long id) {
        livroService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
