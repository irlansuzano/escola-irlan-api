package br.edu.infnet.escola_irlan_api.aluguel;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AluguelMapper {
    @Mapping(target = "livro", ignore = true)
    @Mapping(target = "aluno", ignore = true)
    Aluguel toEntity(AluguelRequestDTO dto);

    @Mapping(target = "livroTitulo", source = "livro.titulo")
    @Mapping(target = "alunoNome", source = "aluno.nome")
    AluguelResponseDTO toResponseDto(Aluguel entity);
}
