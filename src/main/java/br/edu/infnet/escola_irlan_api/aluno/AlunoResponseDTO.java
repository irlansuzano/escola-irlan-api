package br.edu.infnet.escola_irlan_api.aluno;

import java.time.LocalDate;

public record AlunoResponseDTO(
    Long id,
    String nome,
    String cpf,
    String email,
    LocalDate dataNascimento,
    String matricula,
    String turmaIdentificador,
    boolean ativo
) {}
