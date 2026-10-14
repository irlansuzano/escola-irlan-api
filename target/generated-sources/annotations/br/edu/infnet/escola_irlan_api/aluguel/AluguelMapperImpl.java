package br.edu.infnet.escola_irlan_api.aluguel;

import br.edu.infnet.escola_irlan_api.aluno.Aluno;
import br.edu.infnet.escola_irlan_api.livro.Livro;
import java.time.LocalDate;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-10-13T21:11:27-0300",
    comments = "version: 1.6.2, compiler: javac, environment: Java 21.0.2 (Oracle Corporation)"
)
@Component
public class AluguelMapperImpl implements AluguelMapper {

    @Override
    public Aluguel toEntity(AluguelRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Aluguel aluguel = new Aluguel();

        aluguel.setDataInicioAluguel( dto.dataInicioAluguel() );
        aluguel.setDataFimAluguel( dto.dataFimAluguel() );

        return aluguel;
    }

    @Override
    public AluguelResponseDTO toResponseDto(Aluguel entity) {
        if ( entity == null ) {
            return null;
        }

        String livroTitulo = null;
        String alunoNome = null;
        Long id = null;
        LocalDate dataInicioAluguel = null;
        LocalDate dataFimAluguel = null;

        livroTitulo = entityLivroTitulo( entity );
        alunoNome = entityAlunoNome( entity );
        id = entity.getId();
        dataInicioAluguel = entity.getDataInicioAluguel();
        dataFimAluguel = entity.getDataFimAluguel();

        Boolean isAtivo = null;

        AluguelResponseDTO aluguelResponseDTO = new AluguelResponseDTO( id, dataInicioAluguel, dataFimAluguel, livroTitulo, alunoNome, isAtivo );

        return aluguelResponseDTO;
    }

    private String entityLivroTitulo(Aluguel aluguel) {
        Livro livro = aluguel.getLivro();
        if ( livro == null ) {
            return null;
        }
        return livro.getTitulo();
    }

    private String entityAlunoNome(Aluguel aluguel) {
        Aluno aluno = aluguel.getAluno();
        if ( aluno == null ) {
            return null;
        }
        return aluno.getNome();
    }
}
