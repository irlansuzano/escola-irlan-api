package br.edu.infnet.biblioteca_irlan_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CursoRequestDTO(
    @NotBlank(message = "O nome do curso é obrigatório")
    @Size(min = 3, max = 100, message = "O nome do curso deve ter entre 3 e 100 caracteres")
    String nome,

    @NotBlank(message = "A descrição é obrigatória")
    @Size(min = 10, max = 500, message = "A descrição deve ter entre 10 e 500 caracteres")
    String descricao,

    Long coordenadorId
) {}
