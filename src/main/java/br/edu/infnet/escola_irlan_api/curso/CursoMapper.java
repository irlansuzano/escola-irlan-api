package br.edu.infnet.escola_irlan_api.curso;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CursoMapper {
    @Mapping(target = "coordenador", ignore = true)
    @Mapping(target = "turmas", ignore = true)
    Curso toEntity(CursoRequestDTO dto);

    @Mapping(target = "coordenadorNome", source = "coordenador.nome")
    CursoResponseDTO toResponseDto(Curso entity);
}
