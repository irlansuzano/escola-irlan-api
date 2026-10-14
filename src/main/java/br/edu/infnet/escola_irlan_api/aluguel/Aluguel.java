package br.edu.infnet.escola_irlan_api.aluguel;

import br.edu.infnet.escola_irlan_api.aluno.Aluno;
import br.edu.infnet.escola_irlan_api.common.domain.Identificavel;
import br.edu.infnet.escola_irlan_api.livro.Livro;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "aluguel")
public class Aluguel implements Identificavel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_inicio_aluguel")
    private LocalDate dataInicioAluguel;

    @Column(name = "data_fim_aluguel")
    private LocalDate dataFimAluguel;

    @Column(name = "is_ativo")
    private Boolean isAtivo;

    @ManyToOne
    @JoinColumn(name = "id_livro")
    private Livro livro;

    @ManyToOne
    @JoinColumn(name = "id_aluno")
    @JsonBackReference
    private Aluno aluno;

    public Aluguel() {
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getAtivo() {
        return isAtivo;
    }

    public void setAtivo(Boolean ativo) {
        isAtivo = ativo;
    }

    public LocalDate getDataInicioAluguel() {
        return dataInicioAluguel;
    }

    public void setDataInicioAluguel(LocalDate dataInicioAluguel) {
        this.dataInicioAluguel = dataInicioAluguel;
    }

    public LocalDate getDataFimAluguel() {
        return dataFimAluguel;
    }

    public void setDataFimAluguel(LocalDate dataFimAluguel) {
        this.dataFimAluguel = dataFimAluguel;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    private void validarSeAluguelValido(Livro livro, Aluno aluno) {
        if (livro == null) {
            throw new IllegalArgumentException("O livro não pode ser nulo!!!");
        }
        if (aluno == null) {
            throw new IllegalArgumentException("O aluno não pode ser nulo!!!");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Aluguel aluguel = (Aluguel) o;
        return Objects.equals(id, aluguel.id)
                && Objects.equals(dataInicioAluguel, aluguel.dataInicioAluguel)
                && Objects.equals(dataFimAluguel, aluguel.dataFimAluguel)
                && Objects.equals(livro, aluguel.livro)
                && Objects.equals(aluno, aluguel.aluno);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dataInicioAluguel, dataFimAluguel, livro, aluno);
    }

    @Override
    public String toString() {
        return String.format("Aluguel do Livro %s feito pelo Aluno %s. Alugado de %s até %s", livro.getTitulo(), aluno.getNome(), dataInicioAluguel, dataFimAluguel);
    }
}
