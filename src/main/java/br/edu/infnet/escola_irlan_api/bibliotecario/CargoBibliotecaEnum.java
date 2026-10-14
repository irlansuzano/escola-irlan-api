package br.edu.infnet.escola_irlan_api.bibliotecario;

public enum CargoBibliotecaEnum {

    ORGANIZACAO("ORGANIZACAO"),
    RECEPCAO("RECEPCAO"),
    ADMINISTRACAO("ADMINISTRACAO"),
    ESTOQUE("ESTOQUE"),
    GERENCIA("GERENCIA");

    private final String descricao;

    CargoBibliotecaEnum(String descricao) {
        this.descricao = descricao;
    }

    public static CargoBibliotecaEnum mapearCargoPorId(int cargoId){
        return switch (cargoId) {
            case 1 -> ORGANIZACAO;
            case 2 -> RECEPCAO;
            case 3 -> ADMINISTRACAO;
            case 4 -> ESTOQUE;
            case 5 -> GERENCIA;
            default -> throw new IllegalArgumentException("Cargo inválido: " + cargoId);
        };
    }
}


