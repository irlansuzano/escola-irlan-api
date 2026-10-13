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

    public static String mapearCargoPorId(int cargoId){
        return switch (cargoId) {
            case 1 -> ORGANIZACAO.descricao;
            case 2 -> RECEPCAO.descricao;
            case 3 -> ADMINISTRACAO.descricao;
            case 4 -> ESTOQUE.descricao;
            case 5 -> GERENCIA.descricao;
            default -> throw new IllegalArgumentException("Cargo inválido: " + cargoId);
        };
    }
}


