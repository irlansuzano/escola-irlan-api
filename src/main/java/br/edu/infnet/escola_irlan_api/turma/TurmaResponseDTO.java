package br.edu.infnet.biblioteca_irlan_api.turma;

import br.edu.infnet.biblioteca_irlan_api.aluno.AlunoResponseDTO;

import java.util.List;

public record TurmaResponseDTO(
    Long id,
    String identificador,
    String professorCoordenadorNome,
    String cursoNome,
    boolean ativo,
    List<AlunoResponseDTO> alunos
) {}
