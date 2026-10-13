package br.edu.infnet.escola_irlan_api.aluguel;

import java.time.LocalDate;

public record AluguelResponseDTO(
    Long id,
    LocalDate dataInicioAluguel,
    LocalDate dataFimAluguel,
    String livroTitulo,
    String alunoNome,
    Boolean isAtivo
) {}
