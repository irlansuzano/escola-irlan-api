package br.edu.infnet.escola_irlan_api.aluno;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/alunos")
@Tag(name = "Aluno", description = "Endpoints para gestão de alunos")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @GetMapping
    @Operation(summary = "Listar todos os alunos", description = "Retorna uma lista de todos os alunos cadastrados")
    public ResponseEntity<Collection<AlunoResponseDTO>> obterAlunos() {
        return ResponseEntity.ok(alunoService.obterAlunos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter aluno por ID", description = "Retorna os detalhes de um aluno específico pelo seu ID")
    public ResponseEntity<AlunoResponseDTO> obterAlunoPorId(@PathVariable Long id) {
        return ResponseEntity.ok(alunoService.obterAlunoPorId(id));
    }

    @GetMapping("/matricula/{matricula}")
    @Operation(summary = "Obter aluno por matrícula", description = "Busca um aluno através do seu número de matrícula")
    public ResponseEntity<AlunoResponseDTO> obterAlunoPorMatricula(@PathVariable String matricula) {
        return ResponseEntity.ok(alunoService.obterAlunoPorMatricula(matricula));
    }

    @PostMapping
    @Operation(summary = "Cadastrar aluno", description = "Cria um novo registro de aluno no sistema")
    public ResponseEntity<AlunoResponseDTO> cadastrarAluno(@Valid @RequestBody AlunoRequestDTO aluno) {
        return ResponseEntity.status(CREATED).body(alunoService.cadastrarAluno(aluno));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Alterar aluno", description = "Atualiza os dados de um aluno existente")
    public ResponseEntity<AlunoResponseDTO> alterarAluno(@PathVariable Long id, @Valid @RequestBody AlunoRequestDTO aluno) {
        return ResponseEntity.ok(alunoService.alterarAluno(id, aluno));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover aluno", description = "Exclui o registro de um aluno do sistema")
    public ResponseEntity<Void> removerAluno(@PathVariable Long id) {
        alunoService.removerAlunoPorId(id);
        return ResponseEntity.noContent().build();
    }
}
