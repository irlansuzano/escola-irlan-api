package br.edu.infnet.escola_irlan_api.professor;

public record FuncionarioCSV(
    String tipo, // PROFESSOR ou BIBLIOTECARIO
    String nome,
    String cpf,
    String email,
    String dataNascimento,
    String registroProfissional, // Para Professor
    String cargo, // Para Bibliotecário
    String turno // Para Bibliotecário
) {}
