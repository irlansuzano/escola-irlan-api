package br.edu.infnet.escola_irlan_api.bibliotecario;

import br.edu.infnet.escola_irlan_api.bibliotecario.dto.BibliotecarioRequestDTO;
import br.edu.infnet.escola_irlan_api.bibliotecario.dto.BibliotecarioResponseDTO;
import br.edu.infnet.escola_irlan_api.common.exception.BadRequestException;
import br.edu.infnet.escola_irlan_api.common.exception.RecursoNaoEncontradoException;
import br.edu.infnet.escola_irlan_api.common.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BibliotecarioService extends BaseService<Bibliotecario, BibliotecarioRepository> {

    private final BibliotecarioMapper bibliotecarioMapper;

    public BibliotecarioService(BibliotecarioRepository repository, BibliotecarioMapper bibliotecarioMapper) {
        super(repository);
        this.bibliotecarioMapper = bibliotecarioMapper;
    }

    public List<BibliotecarioResponseDTO> buscarTodosFuncionarios() {
        return repository.findAll().stream().map(bibliotecarioMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public BibliotecarioResponseDTO obterBibliotecarioPorId(Long id) {
        return repository.findById(id)
                .map(bibliotecarioMapper::toResponseDto)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Funcionário não encontrado"));
    }

    public List<BibliotecarioResponseDTO> buscarPorCargo(Integer cargo) {
        if (cargo == null) {
            throw new BadRequestException("Cargo não pode ser nulo");
        }

        return repository.findByCargo(CargoBibliotecaEnum.mapearCargoPorId(cargo)).stream().map(bibliotecarioMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public List<BibliotecarioResponseDTO> buscarPorTurno(Integer turno) {
        return repository.findByTurno(TurnoBibliotecaEnum.mapearTurnoPorId(turno)).stream().map(bibliotecarioMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public List<BibliotecarioResponseDTO> buscarPorNome(String nome) {
        return repository.findByNome(nome).stream().map(bibliotecarioMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public List<BibliotecarioResponseDTO> buscarPorEmail(String email) {
        return repository.findByEmail(email).stream().map(bibliotecarioMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public List<BibliotecarioResponseDTO> buscarFuncionariosAtivos() {
        return repository.findByAtivo().stream().map(bibliotecarioMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public BibliotecarioResponseDTO desativarFuncionario(Long id) {
        Optional<Bibliotecario> bibliotecario = repository.findById(id);
        if (bibliotecario.isEmpty()) {
            throw new RecursoNaoEncontradoException("Funcionário não encontrado");
        }
        repository.desativarFuncionario(id);
        bibliotecario.ifPresent(b -> b.setAtivo(false));
        return bibliotecario.map(bibliotecarioMapper::toResponseDto).orElseThrow(() -> new RecursoNaoEncontradoException("Funcionário não encontrado"));
    }

    public BibliotecarioResponseDTO ativarFuncionario(Long id) {
        Optional<Bibliotecario> bibliotecario = repository.findById(id);
        if (bibliotecario.isEmpty()) {
            throw new RecursoNaoEncontradoException("Funcionário não encontrado");
        }
        repository.ativarFuncionario(id);
        bibliotecario.ifPresent(b -> b.setAtivo(true));
        return bibliotecario.map(bibliotecarioMapper::toResponseDto).orElseThrow(() -> new RecursoNaoEncontradoException("Funcionário não encontrado"));
    }

    public BibliotecarioResponseDTO cadastrarFuncionario(BibliotecarioRequestDTO bibliotecarioRequestDTO) {
        Bibliotecario bibliotecario = bibliotecarioMapper.toEntity(bibliotecarioRequestDTO);
        incluir(bibliotecario);
        return bibliotecarioMapper.toResponseDto(bibliotecario);
    }
}
