package br.edu.infnet.escola_irlan_api.professor;

import br.edu.infnet.escola_irlan_api.common.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class ProfessorService extends BaseService<Professor, ProfessorRepository> {

    private final ProfessorMapper professorMapper;

    public ProfessorService(ProfessorRepository repository, ProfessorMapper professorMapper) {
        super(repository);
        this.professorMapper = professorMapper;
    }

    public ProfessorResponseDTO obterProfessorPorId(Long id) {
        return professorMapper.toResponseDto(this.obterPorId(id));
    }

    public Collection<ProfessorResponseDTO> obterProfessores() {
        return this.obterLista().stream()
                .map(professorMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public ProfessorResponseDTO cadastrarProfessor(ProfessorRequestDTO dto) {
        Professor professor = professorMapper.toEntity(dto);
        professor.setAtivo(true);
        this.incluir(professor);
        return professorMapper.toResponseDto(professor);
    }

    public void removerProfessor(Long id) {
        if (!isProfessorExistente(id)) {
            throw new IllegalArgumentException("Cadastro do Professor não encontrado.");
        }
        this.excluir(id);
    }

    public ProfessorResponseDTO alterarProfessor(Long id, ProfessorRequestDTO dto) {
        Professor professorExistente = this.obterPorId(id);

        professorExistente.setNome(dto.nome());
        professorExistente.setCpf(dto.cpf());
        professorExistente.setEmail(dto.email());
        professorExistente.setDataNascimento(dto.dataNascimento());
        professorExistente.setRegistroProfissional(dto.registroProfissional());

        this.alterar(professorExistente);
        return professorMapper.toResponseDto(professorExistente);
    }

    public ProfessorResponseDTO obterProfessorPorRegistroProfissional(String registroProfissional) {
        if (registroProfissional == null || registroProfissional.isEmpty()) {
            throw new IllegalArgumentException("Registro profissional inválido.");
        }
        Professor professor = repository.findByRegistroProfissional(registroProfissional)
                .orElseThrow(() -> new IllegalArgumentException("Não foi encontrado Professor com o registro profissional " + registroProfissional));
        return professorMapper.toResponseDto(professor);
    }

    public boolean isProfessorExistente(Long idProfessor) {
        return repository.existsById(idProfessor);
    }
}
