package br.edu.infnet.biblioteca_irlan_api.dto;

public record LivroResponseDTO(
    Long id,
    String titulo,
    String autor
) {}
