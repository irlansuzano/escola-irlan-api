package br.edu.infnet.escola_irlan_api.turma;

import br.edu.infnet.escola_irlan_api.aluno.Aluno;
import br.edu.infnet.escola_irlan_api.aluno.AlunoMapper;
import br.edu.infnet.escola_irlan_api.aluno.AlunoResponseDTO;
import br.edu.infnet.escola_irlan_api.curso.Curso;
import br.edu.infnet.escola_irlan_api.professor.Professor;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-10-13T20:45:05-0300",
    comments = "version: 1.6.2, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class TurmaMapperImpl implements TurmaMapper {

    @Autowired
    private AlunoMapper alunoMapper;

    @Override
    public Turma toEntity(TurmaRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Turma turma = new Turma();

        turma.setIdentificador( dto.identificador() );
        turma.setAtivo( dto.ativo() );

        return turma;
    }

    @Override
    public TurmaResponseDTO toResponseDto(Turma entity) {
        if ( entity == null ) {
            return null;
        }

        String professorCoordenadorNome = null;
        String cursoNome = null;
        Long id = null;
        String identificador = null;
        boolean ativo = false;
        List<AlunoResponseDTO> alunos = null;

        professorCoordenadorNome = entityProfessorCoordenadorNome( entity );
        cursoNome = entityCursoNome( entity );
        id = entity.getId();
        identificador = entity.getIdentificador();
        ativo = entity.isAtivo();
        alunos = alunoListToAlunoResponseDTOList( entity.getAlunos() );

        TurmaResponseDTO turmaResponseDTO = new TurmaResponseDTO( id, identificador, professorCoordenadorNome, cursoNome, ativo, alunos );

        return turmaResponseDTO;
    }

    private String entityProfessorCoordenadorNome(Turma turma) {
        Professor professorCoordenador = turma.getProfessorCoordenador();
        if ( professorCoordenador == null ) {
            return null;
        }
        return professorCoordenador.getNome();
    }

    private String entityCursoNome(Turma turma) {
        Curso curso = turma.getCurso();
        if ( curso == null ) {
            return null;
        }
        return curso.getNome();
    }

    protected List<AlunoResponseDTO> alunoListToAlunoResponseDTOList(List<Aluno> list) {
        if ( list == null ) {
            return null;
        }

        List<AlunoResponseDTO> list1 = new ArrayList<AlunoResponseDTO>( list.size() );
        for ( Aluno aluno : list ) {
            list1.add( alunoMapper.toResponseDto( aluno ) );
        }

        return list1;
    }
}
