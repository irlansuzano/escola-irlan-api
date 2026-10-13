package br.edu.infnet.escola_irlan_api.aluno;

import br.edu.infnet.escola_irlan_api.turma.Turma;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-10-13T20:45:05-0300",
    comments = "version: 1.6.2, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class AlunoMapperImpl implements AlunoMapper {

    @Override
    public Aluno toEntity(AlunoRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Aluno aluno = new Aluno();

        aluno.setNome( dto.nome() );
        aluno.setCpf( dto.cpf() );
        aluno.setEmail( dto.email() );
        aluno.setDataNascimento( dto.dataNascimento() );
        aluno.setMatricula( dto.matricula() );

        return aluno;
    }

    @Override
    public Aluno toEntity(AlunoTurmaRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Aluno aluno = new Aluno();

        aluno.setNome( dto.nome() );
        aluno.setCpf( dto.cpf() );
        aluno.setEmail( dto.email() );
        aluno.setDataNascimento( dto.dataNascimento() );
        aluno.setMatricula( dto.matricula() );

        return aluno;
    }

    @Override
    public AlunoResponseDTO toResponseDto(Aluno entity) {
        if ( entity == null ) {
            return null;
        }

        String turmaIdentificador = null;
        Long id = null;
        String nome = null;
        String cpf = null;
        String email = null;
        LocalDate dataNascimento = null;
        String matricula = null;
        boolean ativo = false;

        turmaIdentificador = entityTurmaIdentificador( entity );
        id = entity.getId();
        nome = entity.getNome();
        cpf = entity.getCpf();
        email = entity.getEmail();
        dataNascimento = entity.getDataNascimento();
        matricula = entity.getMatricula();
        ativo = entity.isAtivo();

        AlunoResponseDTO alunoResponseDTO = new AlunoResponseDTO( id, nome, cpf, email, dataNascimento, matricula, turmaIdentificador, ativo );

        return alunoResponseDTO;
    }

    private String entityTurmaIdentificador(Aluno aluno) {
        Turma turma = aluno.getTurma();
        if ( turma == null ) {
            return null;
        }
        return turma.getIdentificador();
    }
}
