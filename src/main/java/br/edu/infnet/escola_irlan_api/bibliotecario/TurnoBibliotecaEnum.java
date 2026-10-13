package br.edu.infnet.escola_irlan_api.bibliotecario;

public enum TurnoBibliotecaEnum {
    MATUTINO("MATUTINO"),
    VESPERTINO("VESPERTINO"),
    NOTURNO("NOTURNO");

    private final String descricao;

    TurnoBibliotecaEnum(String descricao) {
        this.descricao = descricao;
    }

    public static String mapearTurnoPorId(int turnoId){
        return switch (turnoId) {
            case 1 -> MATUTINO.descricao;
            case 2 -> VESPERTINO.descricao;
            case 3 -> NOTURNO.descricao;
            default -> throw new IllegalArgumentException("Turno inválido: " + turnoId);
        };
    }
}

