package br.edu.infnet.escola_irlan_api.turma;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TurmaRequestDTO(
    @NotBlank(message = "O identificador da turma é obrigatório")
    @Size(min = 2, max = 50, message = "O identificador deve ter entre 2 e 50 caracteres")
    String identificador,

    @NotNull(message = "O ID do professor coordenador é obrigatório")
    Long professorCoordenadorId,

    @NotNull(message = "O ID do curso é obrigatório")
    Long cursoId,

    boolean ativo
) {}
