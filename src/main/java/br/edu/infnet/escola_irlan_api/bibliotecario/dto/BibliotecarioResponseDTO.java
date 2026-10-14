package br.edu.infnet.escola_irlan_api.bibliotecario.dto;

import br.edu.infnet.escola_irlan_api.bibliotecario.CargoBibliotecaEnum;
import br.edu.infnet.escola_irlan_api.bibliotecario.TurnoBibliotecaEnum;

import java.time.LocalDate;

public record BibliotecarioResponseDTO(
        Long id,
        String nome,
        String cpf,
        String email,
        LocalDate dataNascimento,
        CargoBibliotecaEnum cargo,
        TurnoBibliotecaEnum turno,
        boolean ativo
) {}
