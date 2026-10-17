package br.edu.infnet.escola_irlan_api.livro;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-10-17T15:16:54-0300",
    comments = "version: 1.6.2, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class LivroMapperImpl implements LivroMapper {

    @Override
    public Livro toEntity(LivroRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Livro livro = new Livro();

        livro.setTitulo( dto.titulo() );
        livro.setAutor( dto.autor() );

        return livro;
    }

    @Override
    public LivroResponseDTO toResponseDto(Livro entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        String titulo = null;
        String autor = null;

        id = entity.getId();
        titulo = entity.getTitulo();
        autor = entity.getAutor();

        LivroResponseDTO livroResponseDTO = new LivroResponseDTO( id, titulo, autor );

        return livroResponseDTO;
    }
}
