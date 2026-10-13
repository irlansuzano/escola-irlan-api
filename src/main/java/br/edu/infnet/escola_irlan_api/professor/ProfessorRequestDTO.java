package br.edu.infnet.escola_irlan_api.professor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record ProfessorRequestDTO(
    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 2, max = 150, message = "O nome deve ter entre 2 e 150 caracteres")
    String nome,

    @NotBlank(message = "O CPF é obrigatório")
    String cpf,

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Email inválido")
    String email,

    @NotNull(message = "A data de nascimento é obrigatória")
    @PastOrPresent(message = "A data de nascimento não pode ser futura")
    LocalDate dataNascimento,

    @NotBlank(message = "O registro profissional é obrigatório")
    @Size(min = 3, max = 50, message = "O registro profissional deve ter entre 3 e 50 caracteres")
    String registroProfissional
) {}
