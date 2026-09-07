package br.edu.infnet.biblioteca_irlan_api.dto;

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
