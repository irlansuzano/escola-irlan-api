package br.edu.infnet.escola_irlan_api.aluno;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AlunoMapper {
    Aluno toEntity(AlunoRequestDTO dto);
    Aluno toEntity(AlunoTurmaRequestDTO dto);

    @Mapping(target = "turmaIdentificador", source = "turma.identificador")
    AlunoResponseDTO toResponseDto(Aluno entity);
}
