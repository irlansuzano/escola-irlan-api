package br.edu.infnet.escola_irlan_api.aluno;

public record AlunoCSV(
    String nome,
    String cpf,
    String email,
    String dataNascimento,
    String matricula,
    Long turmaId
) {}
