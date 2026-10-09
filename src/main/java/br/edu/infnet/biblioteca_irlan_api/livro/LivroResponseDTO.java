package br.edu.infnet.biblioteca_irlan_api.livro;

public record LivroResponseDTO(
    Long id,
    String titulo,
    String autor
) {}
