package br.edu.infnet.escola_irlan_api.aluguel;

import br.edu.infnet.escola_irlan_api.common.dto.MensagemResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/alugueis")
@Tag(name = "Aluguel", description = "Endpoints para gestão de aluguéis de livros")
public class AluguelController {

    private final AluguelService aluguelService;

    public AluguelController(AluguelService aluguelService) {
        this.aluguelService = aluguelService;
    }

    @GetMapping
    @Operation(summary = "Listar todos os aluguéis", description = "Retorna uma lista de todos os registros de aluguéis")
    public ResponseEntity<List<AluguelResponseDTO>> obterAlugueis(
            @RequestParam(required = false) Long idLivro,
            @RequestParam(required = false) Long idAluno) {

        if (idLivro != null) {
            return ResponseEntity.ok(aluguelService.buscarAlugueisPorLivro(idLivro));
        }
        if (idAluno != null) {
            return ResponseEntity.ok(aluguelService.buscarAlugueisPorAluno(idAluno));
        }

        return ResponseEntity.ok(aluguelService.obterAlugueis().stream().toList());
    }

    @GetMapping("/ativos")
    @Operation(summary = "Listar aluguéis ativos", description = "Retorna uma representação em texto dos aluguéis que estão ativos no momento")
    public ResponseEntity<String> obterListaAlgueisAtivos() {
        return ResponseEntity.ok(aluguelService.obterListaAlgueisAtivos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter aluguel por ID", description = "Retorna os detalhes de um aluguel específico através do seu identificador")
    public ResponseEntity<AluguelResponseDTO> obterAluguelPorId(@PathVariable Long id) {
        return ResponseEntity.ok(aluguelService.obterAluguelPorId(id));
    }

    @GetMapping("/livro")
    @Operation(summary = "Obter aluguel por ID do Livro", description = "Busca o registro de aluguel associado a um livro específico")
    public ResponseEntity<AluguelResponseDTO> obterAluguelPorLivro(@RequestParam Long idLivro) {
        return ResponseEntity.ok(aluguelService.buscarAluguelPorLivro(idLivro));
    }

    @PostMapping("/alugar")
    @Operation(summary = "Registrar novo aluguel", description = "Cria um novo registro de aluguel de livro")
    public ResponseEntity<AluguelResponseDTO> alugar(@Valid @RequestBody AluguelRequestDTO aluguel) {
        return ResponseEntity.status(CREATED).body(aluguelService.alugar(aluguel));
    }

    @PutMapping("/{id}/alterar")
    @Operation(summary = "Alterar livro de um aluguel", description = "Atualiza o livro associado a um aluguel existente")
    public ResponseEntity<AluguelResponseDTO> alterarLivroAlugado(@PathVariable Long id, @RequestParam Long idNovoLivro) {
        return ResponseEntity.ok(aluguelService.alterarLivroAlugado(id, idNovoLivro));
    }

    @PutMapping("/{id}/devolver")
    @Operation(summary = "Finalizar aluguel (Devolução)", description = "Marca um aluguel como inativo, indicando a devolução do livro")
    public ResponseEntity<MensagemResponse> devolver(@PathVariable Long id) {
        aluguelService.finalizarAluguel(id);
        return ResponseEntity.ok(new MensagemResponse("Livro devolvido com sucesso."));
    }
}
