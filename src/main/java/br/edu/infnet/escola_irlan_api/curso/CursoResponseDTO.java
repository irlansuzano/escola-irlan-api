package br.edu.infnet.escola_irlan_api.curso;

public record CursoResponseDTO(
    Long id,
    String nome,
    String descricao,
    String coordenadorNome,
    boolean ativo
) {}
