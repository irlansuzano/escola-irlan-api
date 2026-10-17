package br.edu.infnet.escola_irlan_api.client.biblioteca.dto;

import java.time.LocalDate;

public record AluguelResponseDTO(
    Long id,
    LocalDate dataInicioAluguel,
    LocalDate dataFimAluguel,
    String livroTitulo,
    Long alunoId,
    Boolean isAtivo
) {}
