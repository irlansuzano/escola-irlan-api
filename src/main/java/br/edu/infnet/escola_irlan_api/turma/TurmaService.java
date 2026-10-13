package br.edu.infnet.escola_irlan_api.turma;

import br.edu.infnet.escola_irlan_api.aluno.Aluno;
import br.edu.infnet.escola_irlan_api.aluno.AlunoMapper;
import br.edu.infnet.escola_irlan_api.aluno.AlunoResponseDTO;
import br.edu.infnet.escola_irlan_api.aluno.AlunoService;
import br.edu.infnet.escola_irlan_api.aluno.AlunoTurmaRequestDTO;
import br.edu.infnet.escola_irlan_api.curso.Curso;
import br.edu.infnet.escola_irlan_api.professor.Professor;
import br.edu.infnet.escola_irlan_api.curso.CursoRepository;
import br.edu.infnet.escola_irlan_api.professor.ProfessorRepository;
import br.edu.infnet.escola_irlan_api.common.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TurmaService extends BaseService<Turma, TurmaRepository> {

    private final ProfessorRepository professorRepository;
    private final CursoRepository cursoRepository;
    private final AlunoService alunoService;
    private final TurmaMapper turmaMapper;
    private final AlunoMapper alunoMapper;

    public TurmaService(TurmaRepository repository, ProfessorRepository professorRepository, CursoRepository cursoRepository, AlunoService alunoService, TurmaMapper turmaMapper, AlunoMapper alunoMapper) {
        super(repository);
        this.professorRepository = professorRepository;
        this.cursoRepository = cursoRepository;
        this.alunoService = alunoService;
        this.turmaMapper = turmaMapper;
        this.alunoMapper = alunoMapper;
    }

    public Collection<TurmaResponseDTO> obterTurmas() {
        return this.obterLista().stream()
                .map(turmaMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public TurmaResponseDTO obterTurmaPorId(Long id) {
        return turmaMapper.toResponseDto(this.obterPorId(id));
    }

    public TurmaResponseDTO cadastrarTurma(TurmaRequestDTO dto) {
        Turma turma = toEntity(dto);
        this.incluir(turma);
        return turmaMapper.toResponseDto(turma);
    }

    public boolean isTurmaAtiva(Long id) {
        Turma turma = this.obterPorId(id);
        return turma != null && turma.isAtivo();
    }

    public void desativarTurma(Long id) {
        Turma turma = this.obterPorId(id);

        if (turma != null) {
            turma.setAtivo(false);
            this.alterar(turma);
        }
    }

    public void matricularAluno(Long idTurma, Long idAluno) {
        Turma turma = this.obterPorId(idTurma);
        Aluno aluno = alunoService.obterPorId(idAluno);

        boolean alunoJaMatriculado = turma.getAlunos().stream().anyMatch(a -> a.getId().equals(idAluno));

        if (alunoJaMatriculado) {
            throw new IllegalArgumentException("Aluno já está matriculado nesta turma.");
        }

        if (turma != null && aluno != null) {
            turma.adicionarAluno(aluno);
            this.alterar(turma);
        }
    }

    public TurmaResponseDTO alterarTurma(Long id, TurmaRequestDTO dto) {
        Turma turma = this.obterPorId(id);
        turma.setIdentificador(dto.identificador());
        turma.setAtivo(dto.ativo());

        Professor professor = professorRepository.findById(dto.professorCoordenadorId())
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado"));
        turma.setProfessorCoordenador(professor);

        Curso curso = cursoRepository.findById(dto.cursoId())
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));
        turma.setCurso(curso);

        this.alterar(turma);
        return turmaMapper.toResponseDto(turma);
    }

    public TurmaResponseDTO obterTurmaPorIdentificador(String identificador) {
        Turma turma = repository.findByIdentificador(identificador)
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada com o identificador: " + identificador));
        return turmaMapper.toResponseDto(turma);
    }

    private Turma toEntity(TurmaRequestDTO dto) {
        Turma turma = turmaMapper.toEntity(dto);

        Professor professor = professorRepository.findById(dto.professorCoordenadorId())
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado"));
        turma.setProfessorCoordenador(professor);

        Curso curso = cursoRepository.findById(dto.cursoId())
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));
        turma.setCurso(curso);

        return turma;
    }
}
