package br.edu.infnet.escola_irlan_api.curso;

import br.edu.infnet.escola_irlan_api.common.domain.Pessoa;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-10-13T21:11:27-0300",
    comments = "version: 1.6.2, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class CursoMapperImpl implements CursoMapper {

    @Override
    public Curso toEntity(CursoRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Curso curso = new Curso();

        curso.setNome( dto.nome() );
        curso.setDescricao( dto.descricao() );

        return curso;
    }

    @Override
    public CursoResponseDTO toResponseDto(Curso entity) {
        if ( entity == null ) {
            return null;
        }

        String coordenadorNome = null;
        Long id = null;
        String nome = null;
        String descricao = null;
        boolean ativo = false;

        coordenadorNome = entityCoordenadorNome( entity );
        id = entity.getId();
        nome = entity.getNome();
        descricao = entity.getDescricao();
        ativo = entity.isAtivo();

        CursoResponseDTO cursoResponseDTO = new CursoResponseDTO( id, nome, descricao, coordenadorNome, ativo );

        return cursoResponseDTO;
    }

    private String entityCoordenadorNome(Curso curso) {
        Pessoa coordenador = curso.getCoordenador();
        if ( coordenador == null ) {
            return null;
        }
        return coordenador.getNome();
    }
}
