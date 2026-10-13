package br.edu.infnet.escola_irlan_api.turma;

import br.edu.infnet.escola_irlan_api.aluno.AlunoMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AlunoMapper.class})
public interface TurmaMapper {
    @Mapping(target = "professorCoordenador", ignore = true)
    @Mapping(target = "curso", ignore = true)
    @Mapping(target = "alunos", ignore = true)
    Turma toEntity(TurmaRequestDTO dto);

    @Mapping(target = "professorCoordenadorNome", source = "professorCoordenador.nome")
    @Mapping(target = "cursoNome", source = "curso.nome")
    TurmaResponseDTO toResponseDto(Turma entity);
}
