package br.edu.infnet.escola_irlan_api.client.biblioteca.dto;

import java.time.LocalDate;

public record DevolucaoResponseDTO(
    Long id,
    LocalDate dataDevolucao,
    Long aluguelId,
    String livroTitulo,
    Long alunoId
) {}
