package br.edu.infnet.escola_irlan_api.aluguel;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record AluguelRequestDTO(
    @NotNull(message = "A data de início do aluguel é obrigatória")
    LocalDate dataInicioAluguel,

    @NotNull(message = "A data de fim do aluguel é obrigatória")
    LocalDate dataFimAluguel,

    @NotNull(message = "O ID do livro é obrigatório")
    Long livroId,

    @NotNull(message = "O ID do aluno é obrigatório")
    Long alunoId,

    Boolean isAtivo
) {}
