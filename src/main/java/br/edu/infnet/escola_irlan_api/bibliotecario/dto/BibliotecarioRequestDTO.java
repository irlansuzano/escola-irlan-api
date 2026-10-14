package br.edu.infnet.escola_irlan_api.bibliotecario.dto;

import br.edu.infnet.escola_irlan_api.bibliotecario.CargoBibliotecaEnum;
import br.edu.infnet.escola_irlan_api.bibliotecario.TurnoBibliotecaEnum;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record BibliotecarioRequestDTO(
        @NotBlank(message = "O nome é obrigatório")
        String nome,
        @NotBlank(message = "O CPF é obrigatório")
        String cpf,
        @NotBlank(message = "O email é obrigatório")
        @Email(message = "Email inválido")
        String email,
        @NotNull(message = "A data de nascimento é obrigatória")
        LocalDate dataNascimento,
        CargoBibliotecaEnum cargo,
        TurnoBibliotecaEnum turno
) {}
