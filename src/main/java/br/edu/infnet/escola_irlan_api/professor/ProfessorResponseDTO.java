package br.edu.infnet.escola_irlan_api.professor;

import java.time.LocalDate;

public record ProfessorResponseDTO(
    Long id,
    String nome,
    String cpf,
    String email,
    LocalDate dataNascimento,
    String registroProfissional,
    boolean ativo
) {}
