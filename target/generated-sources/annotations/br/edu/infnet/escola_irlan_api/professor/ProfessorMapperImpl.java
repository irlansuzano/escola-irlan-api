package br.edu.infnet.escola_irlan_api.professor;

import java.time.LocalDate;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-10-13T20:50:32-0300",
    comments = "version: 1.6.2, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class ProfessorMapperImpl implements ProfessorMapper {

    @Override
    public Professor toEntity(ProfessorRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Professor professor = new Professor();

        professor.setNome( dto.nome() );
        professor.setCpf( dto.cpf() );
        professor.setEmail( dto.email() );
        professor.setDataNascimento( dto.dataNascimento() );
        professor.setRegistroProfissional( dto.registroProfissional() );

        return professor;
    }

    @Override
    public ProfessorResponseDTO toResponseDto(Professor entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        String nome = null;
        String cpf = null;
        String email = null;
        LocalDate dataNascimento = null;
        String registroProfissional = null;
        boolean ativo = false;

        id = entity.getId();
        nome = entity.getNome();
        cpf = entity.getCpf();
        email = entity.getEmail();
        dataNascimento = entity.getDataNascimento();
        registroProfissional = entity.getRegistroProfissional();
        ativo = entity.isAtivo();

        ProfessorResponseDTO professorResponseDTO = new ProfessorResponseDTO( id, nome, cpf, email, dataNascimento, registroProfissional, ativo );

        return professorResponseDTO;
    }
}
