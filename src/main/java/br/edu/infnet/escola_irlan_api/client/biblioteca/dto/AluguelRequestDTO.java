package br.edu.infnet.escola_irlan_api.client.biblioteca.dto;

import java.time.LocalDate;

public record AluguelRequestDTO(
    LocalDate dataInicioAluguel,
    LocalDate dataFimAluguel,
    Long livroId,
    Long alunoId,
    Boolean isAtivo
) {}
