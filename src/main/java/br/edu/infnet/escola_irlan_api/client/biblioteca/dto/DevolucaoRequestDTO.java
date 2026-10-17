package br.edu.infnet.escola_irlan_api.client.biblioteca.dto;

import java.time.LocalDate;

public record DevolucaoRequestDTO(
    LocalDate dataDevolucao,
    Long aluguelId
) {}
