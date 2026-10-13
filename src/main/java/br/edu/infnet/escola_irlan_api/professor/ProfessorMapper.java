package br.edu.infnet.escola_irlan_api.professor;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfessorMapper {
    Professor toEntity(ProfessorRequestDTO dto);
    ProfessorResponseDTO toResponseDto(Professor entity);
}
