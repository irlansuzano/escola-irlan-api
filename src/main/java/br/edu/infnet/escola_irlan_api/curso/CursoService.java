package br.edu.infnet.escola_irlan_api.curso;

import br.edu.infnet.escola_irlan_api.professor.Professor;
import br.edu.infnet.escola_irlan_api.professor.ProfessorRepository;
import br.edu.infnet.escola_irlan_api.common.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CursoService extends BaseService<Curso, CursoRepository> {

    private final ProfessorRepository professorRepository;
    private final CursoMapper cursoMapper;

    public CursoService(CursoRepository repository, ProfessorRepository professorRepository, CursoMapper cursoMapper) {
        super(repository);
        this.professorRepository = professorRepository;
        this.cursoMapper = cursoMapper;
    }

    public Collection<CursoResponseDTO> obterCursos() {
        return this.obterLista().stream()
                .map(cursoMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public CursoResponseDTO obterCursoPorId(Long id) {
        return cursoMapper.toResponseDto(this.obterPorId(id));
    }

    public CursoResponseDTO cadastrarCurso(CursoRequestDTO dto) {
        Curso curso = cursoMapper.toEntity(dto);
        curso.setAtivo(true);

        if (dto.coordenadorId() != null) {
            Professor coordenador = professorRepository.findById(dto.coordenadorId())
                    .orElseThrow(() -> new IllegalArgumentException("Professor coordenador não encontrado"));
            curso.setCoordenador(coordenador);
        }

        this.incluir(curso);
        return cursoMapper.toResponseDto(curso);
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
        return cursoMapper.toResponseDto(cursoExistente);
    }

    public CursoResponseDTO buscarCursoPorNome(String nome) {
        Curso curso = repository.findByNome(nome)
                .orElseThrow(() -> new IllegalArgumentException("não existe curso com o nome " + nome));
        return cursoMapper.toResponseDto(curso);
    }

    public boolean isCursoExistente(Long idCurso) {
        return repository.existsById(idCurso);
    }
}
