package br.edu.infnet.escola_irlan_api.bibliotecario;

import br.edu.infnet.escola_irlan_api.bibliotecario.dto.BibliotecarioRequestDTO;
import br.edu.infnet.escola_irlan_api.bibliotecario.dto.BibliotecarioResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BibliotecarioMapper {
    Bibliotecario toEntity(BibliotecarioRequestDTO dto);
    BibliotecarioResponseDTO toResponseDto(Bibliotecario entity);
}
