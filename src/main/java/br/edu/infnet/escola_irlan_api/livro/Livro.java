package br.edu.infnet.escola_irlan_api.livro;

import br.edu.infnet.escola_irlan_api.common.domain.Identificavel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Entity
@Table(name = "livro")
public class Livro implements Identificavel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O título é obrigatório")
    @Size(min = 1, max = 200, message = "O título deve ter entre 1 e 200 caracteres")
    @Column(name = "titulo")
    private String titulo;

    @NotBlank(message = "O autor é obrigatório")
    @Size(min = 2, max = 150, message = "O autor deve ter entre 2 e 150 caracteres")
    @Column(name = "autor")
    private String autor;

    public Livro() {
    }

    public Livro(Long id, String titulo, String autor) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Livro livro = (Livro) o;
        return Objects.equals(id, livro.id) && Objects.equals(titulo, livro.titulo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titulo);
    }

    @Override
    public String toString() {
        return String.format("Livro: %s - Autor: %s", titulo, autor);
    }
}
