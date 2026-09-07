package br.edu.infnet.biblioteca_irlan_api.service;

import br.edu.infnet.biblioteca_irlan_api.domain.Curso;
import br.edu.infnet.biblioteca_irlan_api.domain.Professor;
import br.edu.infnet.biblioteca_irlan_api.dto.CursoRequestDTO;
import br.edu.infnet.biblioteca_irlan_api.dto.CursoResponseDTO;
import br.edu.infnet.biblioteca_irlan_api.repository.CursoRepository;
import br.edu.infnet.biblioteca_irlan_api.repository.ProfessorRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CursoService extends BaseService<Curso, CursoRepository> {

    private final ProfessorRepository professorRepository;

    public CursoService(CursoRepository repository, ProfessorRepository professorRepository) {
        super(repository);
        this.professorRepository = professorRepository;
    }

    public Collection<CursoResponseDTO> obterCursos() {
        return this.obterLista().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public CursoResponseDTO obterCursoPorId(Long id) {
        return toResponseDTO(this.obterPorId(id));
    }

    public CursoResponseDTO cadastrarCurso(CursoRequestDTO dto) {
        Curso curso = toEntity(dto);
        this.incluir(curso);
        return toResponseDTO(curso);
    }

    public CursoResponseDTO alterarCurso(Long idCurso, CursoRequestDTO dto) {
        Curso cursoExistente = this.obterPorId(idCurso);

        cursoExistente.setNome(dto.nome());
        cursoExistente.setDescricao(dto.descricao());

        if (dto.coordenadorId() != null) {
            Professor coordenador = professorRepository.findById(dto.coordenadorId())
                    .orElseThrow(() -> new IllegalArgumentException("Professor coordenador não encontrado"));
            cursoExistente.setCoordenador(coordenador);
        }

        this.alterar(cursoExistente);
        return toResponseDTO(cursoExistente);
    }

    public CursoResponseDTO buscarCursoPorNome(String nome) {
        Curso curso = repository.findByNome(nome)
                .orElseThrow(() -> new IllegalArgumentException("não existe curso com o nome " + nome));
        return toResponseDTO(curso);
    }

    public boolean isCursoExistente(Long idCurso) {
        return repository.existsById(idCurso);
    }

    public Curso toEntity(CursoRequestDTO dto) {
        Curso curso = new Curso();
        curso.setNome(dto.nome());
        curso.setDescricao(dto.descricao());
        curso.setAtivo(true);

        if (dto.coordenadorId() != null) {
            Professor coordenador = professorRepository.findById(dto.coordenadorId())
                    .orElseThrow(() -> new IllegalArgumentException("Professor coordenador não encontrado"));
            curso.setCoordenador(coordenador);
        }

        return curso;
    }

    public CursoResponseDTO toResponseDTO(Curso curso) {
        return new CursoResponseDTO(
                curso.getId(),
                curso.getNome(),
                curso.getDescricao(),
                curso.getCoordenador() != null ? curso.getCoordenador().getNome() : null,
                curso.isAtivo()
        );
    }
}
