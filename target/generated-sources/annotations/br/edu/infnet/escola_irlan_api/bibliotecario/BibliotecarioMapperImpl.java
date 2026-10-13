package br.edu.infnet.escola_irlan_api.bibliotecario;

import br.edu.infnet.escola_irlan_api.bibliotecario.dto.BibliotecarioRequestDTO;
import br.edu.infnet.escola_irlan_api.bibliotecario.dto.BibliotecarioResponseDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-10-13T20:45:05-0300",
    comments = "version: 1.6.2, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class BibliotecarioMapperImpl implements BibliotecarioMapper {

    @Override
    public Bibliotecario toEntity(BibliotecarioRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Bibliotecario bibliotecario = new Bibliotecario();

        bibliotecario.setNome( dto.nome() );
        bibliotecario.setCargo( dto.cargo() );
        bibliotecario.setTurno( dto.turno() );

        return bibliotecario;
    }

    @Override
    public BibliotecarioResponseDTO toResponseDto(Bibliotecario entity) {
        if ( entity == null ) {
            return null;
        }

        String nome = null;
        CargoBibliotecaEnum cargo = null;
        TurnoBibliotecaEnum turno = null;

        nome = entity.getNome();
        cargo = entity.getCargo();
        turno = entity.getTurno();

        BibliotecarioResponseDTO bibliotecarioResponseDTO = new BibliotecarioResponseDTO( nome, cargo, turno );

        return bibliotecarioResponseDTO;
    }
}
