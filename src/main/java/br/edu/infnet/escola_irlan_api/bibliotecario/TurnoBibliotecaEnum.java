package br.edu.infnet.escola_irlan_api.bibliotecario;

public enum TurnoBibliotecaEnum {
    MATUTINO("MATUTINO"),
    VESPERTINO("VESPERTINO"),
    NOTURNO("NOTURNO");

    private final String descricao;

    TurnoBibliotecaEnum(String descricao) {
        this.descricao = descricao;
    }

    public static TurnoBibliotecaEnum mapearTurnoPorId(int turnoId){
        return switch (turnoId) {
            case 1 -> MATUTINO;
            case 2 -> VESPERTINO;
            case 3 -> NOTURNO;
            default -> throw new IllegalArgumentException("Turno inválido: " + turnoId);
        };
    }
}

