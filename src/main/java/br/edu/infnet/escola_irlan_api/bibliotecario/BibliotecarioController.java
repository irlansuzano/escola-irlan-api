package br.edu.infnet.escola_irlan_api.bibliotecario;

import br.edu.infnet.escola_irlan_api.bibliotecario.dto.BibliotecarioRequestDTO;
import br.edu.infnet.escola_irlan_api.bibliotecario.dto.BibliotecarioResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bibliotecarios")
public class BibliotecarioController {

    private final BibliotecarioService bibliotecarioService;

    public BibliotecarioController(BibliotecarioService bibliotecarioService) {
        this.bibliotecarioService = bibliotecarioService;
    }

    @GetMapping
    public ResponseEntity<List<BibliotecarioResponseDTO>> buscarTodosFuncionarios() {
        return ResponseEntity.ok(bibliotecarioService.buscarTodosFuncionarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BibliotecarioResponseDTO> findBibliotecarioById(@PathVariable Long id) {
        return ResponseEntity.ok(bibliotecarioService.obterBibliotecarioPorId(id));
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<BibliotecarioResponseDTO>> findBibliotecariosAtivos() {
        return ResponseEntity.ok(bibliotecarioService.buscarFuncionariosAtivos());
    }

    @GetMapping("/cargo/{id}")
    public ResponseEntity<List<BibliotecarioResponseDTO>> findBibliotecariosPorCargo(@PathVariable Integer id) {
        return ResponseEntity.ok(bibliotecarioService.buscarPorCargo(id));
    }

    @GetMapping("/turno/{id}")
    public ResponseEntity<List<BibliotecarioResponseDTO>> findBibliotecariosPorTurno(@PathVariable Integer id) {
        return ResponseEntity.ok(bibliotecarioService.buscarPorTurno(id));
    }

    @GetMapping("/nome")
    public ResponseEntity<List<BibliotecarioResponseDTO>> findBibliotecariosPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(bibliotecarioService.buscarPorNome(nome));
    }

    @GetMapping("/email")
    public ResponseEntity<List<BibliotecarioResponseDTO>> findBibliotecariosPorEmail(@RequestParam String email) {
        return ResponseEntity.ok(bibliotecarioService.buscarPorEmail(email));
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<BibliotecarioResponseDTO> cadastrarBibliotecario(@RequestBody BibliotecarioRequestDTO bibliotecario) {
        return ResponseEntity.ok(bibliotecarioService.cadastrarFuncionario(bibliotecario));
    }

    @PutMapping("/ativar/{id}")
    public ResponseEntity<BibliotecarioResponseDTO> ativarBibliotecario(@PathVariable Long id) {
        return ResponseEntity.ok(bibliotecarioService.ativarFuncionario(id));
    }

    @PutMapping("/desativar/{id}")
    public ResponseEntity<BibliotecarioResponseDTO> desativarBibliotecario(@PathVariable Long id) {
        return ResponseEntity.ok(bibliotecarioService.desativarFuncionario(id));
    }


}
