package br.edu.infnet.biblioteca_irlan_api.curso;

public record CursoResponseDTO(
    Long id,
    String nome,
    String descricao,
    String coordenadorNome,
    boolean ativo
) {}
