package br.edu.infnet.biblioteca_irlan_api.controller;

import br.edu.infnet.biblioteca_irlan_api.domain.Aluno;
import br.edu.infnet.biblioteca_irlan_api.dto.AlunoRequestDTO;
import br.edu.infnet.biblioteca_irlan_api.dto.AlunoTurmaRequestDTO;
import br.edu.infnet.biblioteca_irlan_api.dto.TurmaRequestDTO;
import br.edu.infnet.biblioteca_irlan_api.dto.TurmaResponseDTO;
import br.edu.infnet.biblioteca_irlan_api.service.TurmaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/turmas")
@Tag(name = "Turma", description = "Endpoints para gestão de turmas")
public class TurmaController {

    private final TurmaService turmaService;

    public TurmaController(TurmaService turmaService) {
        this.turmaService = turmaService;
    }

    @GetMapping
    @Operation(summary = "Listar todas as turmas", description = "Retorna uma lista de todas as turmas cadastradas")
    public ResponseEntity<Collection<TurmaResponseDTO>> obterTurmas() {
        return ResponseEntity.ok(turmaService.obterTurmas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter turma por ID", description = "Retorna os detalhes de uma turma específica pelo seu ID")
    public ResponseEntity<TurmaResponseDTO> obterTurmaPorId(@PathVariable Long id) {
        return ResponseEntity.ok(turmaService.obterTurmaPorId(id));
    }

    @GetMapping("/identificador/{identificador}")
    @Operation(summary = "Obter turma por identificador", description = "Busca uma turma através do seu código identificador")
    public ResponseEntity<TurmaResponseDTO> obterTurmaPorIdentificador(@PathVariable String identificador) {
        return ResponseEntity.ok(turmaService.obterTurmaPorIdentificador(identificador));
    }

    @GetMapping("/{id}/ativa")
    @Operation(summary = "Verificar se turma está ativa", description = "Retorna um booleano indicando se a turma está ativa")
    public ResponseEntity<Boolean> isTurmaAtiva(@PathVariable Long id) {
        return ResponseEntity.ok(turmaService.isTurmaAtiva(id));
    }

    @PostMapping
    @Operation(summary = "Cadastrar turma", description = "Cria um novo registro de turma no sistema")
    public ResponseEntity<TurmaResponseDTO> cadastrarTurma(@Valid @RequestBody TurmaRequestDTO turma) {
        return ResponseEntity.status(CREATED).body(turmaService.cadastrarTurma(turma));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Alterar turma", description = "Atualiza os dados de uma turma existente")
    public ResponseEntity<TurmaResponseDTO> alterarTurma(@PathVariable Long id, @Valid @RequestBody TurmaRequestDTO turma) {
        return ResponseEntity.ok(turmaService.alterarTurma(id, turma));
    }

    @PutMapping("/{id}/desativar")
    @Operation(summary = "Desativar turma", description = "Marca uma turma como inativa")
    public ResponseEntity<Void> desativarTurma(@PathVariable Long id) {
        turmaService.desativarTurma(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/alunos")
    @Operation(summary = "Incluir lista de alunos", description = "Adiciona múltiplos alunos a uma turma")
    public ResponseEntity<Void> incluirAlunos(@PathVariable Long id, @RequestBody List<AlunoTurmaRequestDTO> alunos) {
        turmaService.incluirAlunos(id, alunos);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/aluno")
    @Operation(summary = "Incluir um aluno", description = "Adiciona um único aluno a uma turma")
    public ResponseEntity<Void> incluirAluno(@PathVariable Long id, @RequestBody AlunoTurmaRequestDTO aluno) {
        turmaService.incluirAluno(id, aluno);
        return ResponseEntity.noContent().build();
    }
}
