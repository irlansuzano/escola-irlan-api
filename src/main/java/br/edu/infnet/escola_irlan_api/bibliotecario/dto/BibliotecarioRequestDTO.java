package br.edu.infnet.escola_irlan_api.bibliotecario.dto;

import br.edu.infnet.escola_irlan_api.bibliotecario.CargoBibliotecaEnum;
import br.edu.infnet.escola_irlan_api.bibliotecario.TurnoBibliotecaEnum;

public record BibliotecarioRequestDTO(
        String nome,
        CargoBibliotecaEnum cargo,
        TurnoBibliotecaEnum turno
) {}
