package br.edu.infnet.biblioteca_irlan_api.turma;

import br.edu.infnet.biblioteca_irlan_api.aluno.Aluno;
import br.edu.infnet.biblioteca_irlan_api.aluno.AlunoResponseDTO;
import br.edu.infnet.biblioteca_irlan_api.aluno.AlunoService;
import br.edu.infnet.biblioteca_irlan_api.aluno.AlunoTurmaRequestDTO;
import br.edu.infnet.biblioteca_irlan_api.curso.Curso;
import br.edu.infnet.biblioteca_irlan_api.professor.Professor;
import br.edu.infnet.biblioteca_irlan_api.curso.CursoRepository;
import br.edu.infnet.biblioteca_irlan_api.professor.ProfessorRepository;
import br.edu.infnet.biblioteca_irlan_api.common.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TurmaService extends BaseService<Turma, TurmaRepository> {

    private final ProfessorRepository professorRepository;
    private final CursoRepository cursoRepository;
    private final AlunoService alunoService;

    public TurmaService(TurmaRepository repository, ProfessorRepository professorRepository, CursoRepository cursoRepository, AlunoService alunoService) {
        super(repository);
        this.professorRepository = professorRepository;
        this.cursoRepository = cursoRepository;
        this.alunoService = alunoService;
    }

    public Collection<TurmaResponseDTO> obterTurmas() {
        return this.obterLista().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public TurmaResponseDTO obterTurmaPorId(Long id) {
        return toResponseDTO(this.obterPorId(id));
    }

    public TurmaResponseDTO cadastrarTurma(TurmaRequestDTO dto) {
        Turma turma = toEntity(dto);
        this.incluir(turma);
        return toResponseDTO(turma);
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

    public void incluirAlunos(Long idTurma, List<AlunoTurmaRequestDTO> alunos) {
        Turma turma = this.obterPorId(idTurma);

        if (turma != null && alunos != null) {
            for (AlunoTurmaRequestDTO aluno : alunos) {
                turma.adicionarAluno(toAluno(aluno));
            }
            this.alterar(turma);
        }
    }

    public void incluirAluno(Long idTurma, AlunoTurmaRequestDTO aluno) {
        Turma turma = this.obterPorId(idTurma);

        if (turma != null && aluno != null) {
            turma.adicionarAluno(toAluno(aluno));
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
        return toResponseDTO(turma);
    }

    public TurmaResponseDTO obterTurmaPorIdentificador(String identificador) {
        Turma turma = repository.findByIdentificador(identificador)
                .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada com o identificador: " + identificador));
        return toResponseDTO(turma);
    }

    private Turma toEntity(TurmaRequestDTO dto) {
        Turma turma = new Turma();
        turma.setIdentificador(dto.identificador());
        turma.setAtivo(dto.ativo());

        Professor professor = professorRepository.findById(dto.professorCoordenadorId())
                .orElseThrow(() -> new IllegalArgumentException("Professor não encontrado"));
        turma.setProfessorCoordenador(professor);

        Curso curso = cursoRepository.findById(dto.cursoId())
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));
        turma.setCurso(curso);

        return turma;
    }

    public TurmaResponseDTO toResponseDTO(Turma turma) {
        return new TurmaResponseDTO(
                turma.getId(),
                turma.getIdentificador(),
                turma.getProfessorCoordenador() != null ? turma.getProfessorCoordenador().getNome() : null,
                turma.getCurso() != null ? turma.getCurso().getNome() : null,
                turma.isAtivo(),
                turma.getAlunos() != null ? turma.getAlunos().stream()
                        .map(aluno -> new AlunoResponseDTO(
                                aluno.getId(),
                                aluno.getNome(),
                                aluno.getCpf(),
                                aluno.getEmail(),
                                aluno.getDataNascimento(),
                                aluno.getMatricula(),
                                turma.getIdentificador(),
                                aluno.isAtivo()
                        )).collect(Collectors.toList()) : List.of()
        );
    }

    public Aluno toAluno(AlunoTurmaRequestDTO dto) {
        Aluno aluno = new Aluno();
        aluno.setNome(dto.nome());
        aluno.setCpf(dto.cpf());
        aluno.setEmail(dto.email());
        aluno.setDataNascimento(dto.dataNascimento());
        aluno.setMatricula(dto.matricula());

        aluno.setAtivo(true);

        return aluno;
    }
}
