package br.edu.infnet.biblioteca_irlan_api.service;

import br.edu.infnet.biblioteca_irlan_api.domain.Professor;
import br.edu.infnet.biblioteca_irlan_api.dto.ProfessorRequestDTO;
import br.edu.infnet.biblioteca_irlan_api.dto.ProfessorResponseDTO;
import br.edu.infnet.biblioteca_irlan_api.repository.ProfessorRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class ProfessorService extends BaseService<Professor, ProfessorRepository> {

    public ProfessorService(ProfessorRepository repository) {
        super(repository);
    }

    public ProfessorResponseDTO obterProfessorPorId(Long id) {
        return toResponseDTO(this.obterPorId(id));
    }

    public Collection<ProfessorResponseDTO> obterProfessores() {
        return this.obterLista().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public ProfessorResponseDTO cadastrarProfessor(ProfessorRequestDTO dto) {
        Professor professor = toEntity(dto);
        this.incluir(professor);
        return toResponseDTO(professor);
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
        return toResponseDTO(professorExistente);
    }

    public ProfessorResponseDTO obterProfessorPorRegistroProfissional(String registroProfissional) {
        if (registroProfissional == null || registroProfissional.isEmpty()) {
            throw new IllegalArgumentException("Registro profissional inválido.");
        }
        Professor professor = repository.findByRegistroProfissional(registroProfissional)
                .orElseThrow(() -> new IllegalArgumentException("Não foi encontrado Professor com o registro profissional " + registroProfissional));
        return toResponseDTO(professor);
    }

    public boolean isProfessorExistente(Long idProfessor) {
        return repository.existsById(idProfessor);
    }

    public Professor toEntity(ProfessorRequestDTO dto) {
        Professor professor = new Professor();
        professor.setNome(dto.nome());
        professor.setCpf(dto.cpf());
        professor.setEmail(dto.email());
        professor.setDataNascimento(dto.dataNascimento());
        professor.setRegistroProfissional(dto.registroProfissional());
        professor.setAtivo(true);
        return professor;
    }

    public ProfessorResponseDTO toResponseDTO(Professor professor) {
        return new ProfessorResponseDTO(
                professor.getId(),
                professor.getNome(),
                professor.getCpf(),
                professor.getEmail(),
                professor.getDataNascimento(),
                professor.getRegistroProfissional(),
                professor.isAtivo()
        );
    }
}
