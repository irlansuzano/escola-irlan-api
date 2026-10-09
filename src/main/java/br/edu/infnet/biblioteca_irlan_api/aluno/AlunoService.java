package br.edu.infnet.biblioteca_irlan_api.aluno;

import br.edu.infnet.biblioteca_irlan_api.turma.Turma;
import br.edu.infnet.biblioteca_irlan_api.turma.TurmaRepository;
import br.edu.infnet.biblioteca_irlan_api.common.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class AlunoService extends BaseService<Aluno, AlunoRepository> {

    private final TurmaRepository turmaRepository;

    public AlunoService(AlunoRepository repository, TurmaRepository turmaRepository) {
        super(repository);
        this.turmaRepository = turmaRepository;
    }

    public AlunoResponseDTO obterAlunoPorId(Long id) {
        return toResponseDTO(this.obterPorId(id));
    }

    public Collection<AlunoResponseDTO> obterAlunos() {
        return this.obterLista().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public AlunoResponseDTO cadastrarAluno(AlunoRequestDTO dto) {
        Aluno aluno = toEntity(dto);
        this.incluir(aluno);
        return toResponseDTO(aluno);
    }

    public void removerAlunoPorId(Long id) {
        if (!isAlunoExistente(id)) {
            throw new IllegalArgumentException("Cadastro do Aluno não encontrado.");
        }
        this.excluir(id);
    }

    public AlunoResponseDTO alterarAluno(Long id, AlunoRequestDTO dto) {
        Aluno alunoExistente = this.obterPorId(id);

        alunoExistente.setNome(dto.nome());
        alunoExistente.setCpf(dto.cpf());
        alunoExistente.setEmail(dto.email());
        alunoExistente.setDataNascimento(dto.dataNascimento());
        alunoExistente.setMatricula(dto.matricula());

        if (dto.turmaId() != null) {
            Turma turma = turmaRepository.findById(dto.turmaId())
                    .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada"));
            alunoExistente.setTurma(turma);
        }

        this.alterar(alunoExistente);
        return toResponseDTO(alunoExistente);
    }

    public AlunoResponseDTO obterAlunoPorMatricula(String matricula) {
        Aluno aluno = repository.findByMatricula(matricula)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado com a matrícula: " + matricula));
        return toResponseDTO(aluno);
    }

    private boolean isAlunoExistente(Long idAluno) {
        return repository.existsById(idAluno);
    }

    public Aluno toEntity(AlunoRequestDTO dto) {
        Aluno aluno = new Aluno();
        aluno.setNome(dto.nome());
        aluno.setCpf(dto.cpf());
        aluno.setEmail(dto.email());
        aluno.setDataNascimento(dto.dataNascimento());
        aluno.setMatricula(dto.matricula());
        aluno.setAtivo(true);

        if (dto.turmaId() != null) {
            Turma turma = turmaRepository.findById(dto.turmaId())
                    .orElseThrow(() -> new IllegalArgumentException("Turma não encontrada"));
            aluno.setTurma(turma);
        }

        return aluno;
    }

    public AlunoResponseDTO toResponseDTO(Aluno aluno) {
        return new AlunoResponseDTO(
                aluno.getId(),
                aluno.getNome(),
                aluno.getCpf(),
                aluno.getEmail(),
                aluno.getDataNascimento(),
                aluno.getMatricula(),
                aluno.getTurma() != null ? aluno.getTurma().getIdentificador() : null,
                aluno.isAtivo()
        );
    }
}
