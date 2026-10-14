package br.edu.infnet.escola_irlan_api.bibliotecario;

import br.edu.infnet.escola_irlan_api.bibliotecario.dto.BibliotecarioRequestDTO;
import br.edu.infnet.escola_irlan_api.bibliotecario.dto.BibliotecarioResponseDTO;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-10-13T21:11:27-0300",
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
        bibliotecario.setCpf( dto.cpf() );
        bibliotecario.setEmail( dto.email() );
        bibliotecario.setDataNascimento( dto.dataNascimento() );
        bibliotecario.setCargo( dto.cargo() );
        bibliotecario.setTurno( dto.turno() );

        return bibliotecario;
    }

    @Override
    public BibliotecarioResponseDTO toResponseDto(Bibliotecario entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        String nome = null;
        String cpf = null;
        String email = null;
        LocalDate dataNascimento = null;
        CargoBibliotecaEnum cargo = null;
        TurnoBibliotecaEnum turno = null;
        boolean ativo = false;

        id = entity.getId();
        nome = entity.getNome();
        cpf = entity.getCpf();
        email = entity.getEmail();
        dataNascimento = entity.getDataNascimento();
        cargo = entity.getCargo();
        turno = entity.getTurno();
        ativo = entity.isAtivo();

        BibliotecarioResponseDTO bibliotecarioResponseDTO = new BibliotecarioResponseDTO( id, nome, cpf, email, dataNascimento, cargo, turno, ativo );

        return bibliotecarioResponseDTO;
    }
}
