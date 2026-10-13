package br.edu.infnet.escola_irlan_api.livro;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LivroMapper {
    Livro toEntity(LivroRequestDTO dto);
    LivroResponseDTO toResponseDto(Livro entity);
}
