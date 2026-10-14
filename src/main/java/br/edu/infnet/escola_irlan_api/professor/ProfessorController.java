package br.edu.infnet.escola_irlan_api.professor;

import br.edu.infnet.escola_irlan_api.common.dto.MensagemResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/professores")
@Tag(name = "Professor", description = "Endpoints para gestão de professores")
public class ProfessorController {

    private final ProfessorService professorService;

    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }

    @GetMapping
    @Operation(summary = "Listar todos os professores", description = "Retorna uma lista de todos os professores cadastrados")
    public ResponseEntity<Collection<ProfessorResponseDTO>> obterProfessores() {
        return ResponseEntity.ok(professorService.obterProfessores());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter professor por ID", description = "Retorna os detalhes de um professor específico pelo seu ID")
    public ResponseEntity<ProfessorResponseDTO> obterProfessorPorId(@PathVariable Long id) {
        return ResponseEntity.ok(professorService.obterProfessorPorId(id));
    }

    @GetMapping("/registro/{registroProfissional}")
    @Operation(summary = "Obter professor por registro profissional", description = "Busca um professor através do seu registro profissional")
    public ResponseEntity<ProfessorResponseDTO> obterProfessorPorRegistro(@PathVariable String registroProfissional) {
        return ResponseEntity.ok(professorService.obterProfessorPorRegistroProfissional(registroProfissional));
    }

    @PostMapping
    @Operation(summary = "Cadastrar professor", description = "Cria um novo registro de professor no sistema")
    public ResponseEntity<ProfessorResponseDTO> cadastrarProfessor(@Valid @RequestBody ProfessorRequestDTO professor) {
        return ResponseEntity.status(CREATED).body(professorService.cadastrarProfessor(professor));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Alterar professor", description = "Atualiza os dados de um professor existente")
    public ResponseEntity<ProfessorResponseDTO> alterarProfessor(@PathVariable Long id, @Valid @RequestBody ProfessorRequestDTO professor) {
        return ResponseEntity.ok(professorService.alterarProfessor(id, professor));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover professor", description = "Exclui o registro de um professor do sistema")
    public ResponseEntity<MensagemResponse> removerProfessor(@PathVariable Long id) {
        professorService.removerProfessor(id);
        return ResponseEntity.ok(new MensagemResponse("Professor removido com sucesso."));
    }
}
