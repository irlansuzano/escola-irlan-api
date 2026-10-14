package br.edu.infnet.escola_irlan_api.bibliotecario;

import br.edu.infnet.escola_irlan_api.bibliotecario.dto.BibliotecarioRequestDTO;
import br.edu.infnet.escola_irlan_api.bibliotecario.dto.BibliotecarioResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bibliotecarios")
@Tag(name = "Bibliotecário", description = "Endpoints para gestão de funcionários da biblioteca")
public class BibliotecarioController {

    private final BibliotecarioService bibliotecarioService;

    public BibliotecarioController(BibliotecarioService bibliotecarioService) {
        this.bibliotecarioService = bibliotecarioService;
    }

    @GetMapping
    @Operation(summary = "Listar todos os bibliotecários", description = "Retorna uma lista de todos os bibliotecários cadastrados")
    public ResponseEntity<List<BibliotecarioResponseDTO>> buscarTodosFuncionarios() {
        return ResponseEntity.ok(bibliotecarioService.buscarTodosFuncionarios());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obter bibliotecário por ID", description = "Retorna os detalhes de um bibliotecário específico pelo seu ID")
    public ResponseEntity<BibliotecarioResponseDTO> findBibliotecarioById(@PathVariable Long id) {
        return ResponseEntity.ok(bibliotecarioService.obterBibliotecarioPorId(id));
    }

    @GetMapping("/ativos")
    @Operation(summary = "Listar bibliotecários ativos", description = "Retorna uma lista de todos os bibliotecários com status ativo")
    public ResponseEntity<List<BibliotecarioResponseDTO>> findBibliotecariosAtivos() {
        return ResponseEntity.ok(bibliotecarioService.buscarFuncionariosAtivos());
    }

    @GetMapping("/cargo/{id}")
    @Operation(summary = "Listar por cargo", description = "Busca bibliotecários filtrando pelo ID do cargo")
    public ResponseEntity<List<BibliotecarioResponseDTO>> findBibliotecariosPorCargo(@PathVariable Integer id) {
        return ResponseEntity.ok(bibliotecarioService.buscarPorCargo(id));
    }

    @GetMapping("/turno/{id}")
    @Operation(summary = "Listar por turno", description = "Busca bibliotecários filtrando pelo ID do turno")
    public ResponseEntity<List<BibliotecarioResponseDTO>> findBibliotecariosPorTurno(@PathVariable Integer id) {
        return ResponseEntity.ok(bibliotecarioService.buscarPorTurno(id));
    }

    @GetMapping("/nome")
    @Operation(summary = "Buscar por nome", description = "Busca bibliotecários cujo nome contenha o termo informado")
    public ResponseEntity<List<BibliotecarioResponseDTO>> findBibliotecariosPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(bibliotecarioService.buscarPorNome(nome));
    }

    @GetMapping("/email")
    @Operation(summary = "Buscar por e-mail", description = "Busca bibliotecários pelo e-mail exato")
    public ResponseEntity<List<BibliotecarioResponseDTO>> findBibliotecariosPorEmail(@RequestParam String email) {
        return ResponseEntity.ok(bibliotecarioService.buscarPorEmail(email));
    }

    @PostMapping("/cadastrar")
    @Operation(summary = "Cadastrar bibliotecário", description = "Cria um novo registro de bibliotecário no sistema")
    public ResponseEntity<BibliotecarioResponseDTO> cadastrarBibliotecario(@RequestBody BibliotecarioRequestDTO bibliotecario) {
        return ResponseEntity.ok(bibliotecarioService.cadastrarFuncionario(bibliotecario));
    }

    @PutMapping("/ativar/{id}")
    @Operation(summary = "Ativar bibliotecário", description = "Altera o status do bibliotecário para ativo")
    public ResponseEntity<BibliotecarioResponseDTO> ativarBibliotecario(@PathVariable Long id) {
        return ResponseEntity.ok(bibliotecarioService.ativarFuncionario(id));
    }

    @PutMapping("/desativar/{id}")
    @Operation(summary = "Desativar bibliotecário", description = "Altera o status do bibliotecário para inativo")
    public ResponseEntity<BibliotecarioResponseDTO> desativarBibliotecario(@PathVariable Long id) {
        return ResponseEntity.ok(bibliotecarioService.desativarFuncionario(id));
    }


}
