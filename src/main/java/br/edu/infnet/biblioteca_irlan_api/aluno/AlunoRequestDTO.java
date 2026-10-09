package br.edu.infnet.biblioteca_irlan_api.aluno;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record AlunoRequestDTO(
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

    @NotBlank(message = "A matrícula é obrigatória")
    @Size(min = 5, max = 20, message = "A matrícula deve ter entre 5 e 20 caracteres")
    String matricula,

    Long turmaId
) {}
