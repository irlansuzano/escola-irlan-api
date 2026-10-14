package br.edu.infnet.escola_irlan_api.aluno;

import br.edu.infnet.escola_irlan_api.common.exception.RecursoNaoEncontradoException;
import br.edu.infnet.escola_irlan_api.turma.Turma;
import br.edu.infnet.escola_irlan_api.turma.TurmaRepository;
import br.edu.infnet.escola_irlan_api.common.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class AlunoService extends BaseService<Aluno, AlunoRepository> {

    private final TurmaRepository turmaRepository;
    private final AlunoMapper alunoMapper;

    public AlunoService(AlunoRepository repository, TurmaRepository turmaRepository, AlunoMapper alunoMapper) {
        super(repository);
        this.turmaRepository = turmaRepository;
        this.alunoMapper = alunoMapper;
    }

    public AlunoResponseDTO obterAlunoPorId(Long id) {
        return alunoMapper.toResponseDto(this.obterPorId(id));
    }

    public Collection<AlunoResponseDTO> obterAlunos() {
        return this.obterLista().stream()
                .map(alunoMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public AlunoResponseDTO cadastrarAluno(AlunoRequestDTO dto) {
        Aluno aluno = alunoMapper.toEntity(dto);
        aluno.setAtivo(true);
        this.incluir(aluno);
        return alunoMapper.toResponseDto(aluno);
    }

    public void removerAlunoPorId(Long id) {
        if (!isAlunoExistente(id)) {
            throw new RecursoNaoEncontradoException("Cadastro do Aluno não encontrado.");
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

        this.alterar(alunoExistente);
        return alunoMapper.toResponseDto(alunoExistente);
    }

    public AlunoResponseDTO obterAlunoPorMatricula(String matricula) {
        Aluno aluno = repository.findByMatricula(matricula)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Aluno não encontrado com a matrícula: " + matricula));
        return alunoMapper.toResponseDto(aluno);
    }

    private boolean isAlunoExistente(Long idAluno) {
        return repository.existsById(idAluno);
    }
}
