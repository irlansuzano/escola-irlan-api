package br.edu.infnet.biblioteca_irlan_api.livro;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LivroRequestDTO(
    @NotBlank(message = "O título é obrigatório")
    @Size(min = 1, max = 200, message = "O título deve ter entre 1 e 200 caracteres")
    String titulo,

    @NotBlank(message = "O autor é obrigatório")
    @Size(min = 2, max = 150, message = "O autor deve ter entre 2 e 150 caracteres")
    String autor
) {}
