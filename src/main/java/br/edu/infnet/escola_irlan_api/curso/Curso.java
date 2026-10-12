package br.edu.infnet.biblioteca_irlan_api.curso;

import br.edu.infnet.biblioteca_irlan_api.common.domain.Identificavel;
import br.edu.infnet.biblioteca_irlan_api.common.domain.Pessoa;
import br.edu.infnet.biblioteca_irlan_api.professor.Professor;
import br.edu.infnet.biblioteca_irlan_api.turma.Turma;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "curso")
public class Curso implements Identificavel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do curso é obrigatório")
    @Size(min = 3, max = 100, message = "O nome do curso deve ter entre 3 e 100 caracteres")
    @Column(name = "nome")
    private String nome;

    @NotBlank(message = "A descrição é obrigatória")
    @Size(min = 10, max = 500, message = "A descrição deve ter entre 10 e 500 caracteres")
    @Column(name = "descricao")
    private String descricao;

    @Column(name = "ativo")
    private boolean ativo;

    @OneToOne
    @JoinColumn(name = "id_coordenador")
    private Professor coordenador;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL)
    private List<Turma> turmas = new ArrayList<>();

    public Curso() {
    }

    public Curso(Long id, String nome, String descricao, Professor coordenador) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.coordenador = coordenador;
        this.ativo = true;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Pessoa getCoordenador() {
        return coordenador;
    }

    public void setCoordenador(Professor coordenador) {
        if (coordenador == null) {
            throw new IllegalArgumentException("O coordenador não pode ser nulo!!!");
        }
        this.coordenador = coordenador;
        coordenador.setCoordenacao(this);
    }

    public List<Turma> getTurmas() {
        return Collections.unmodifiableList(turmas);
    }

    public void setTurmas(List<Turma> turmas) {
        if (turmas == null || turmas.isEmpty()) {
            throw new IllegalArgumentException("é necessário informar pelo menos uma turma para o curso");
        }
        this.turmas = turmas;
    }

    public void adicionarTodasTurmas(List<Turma> turmas) {
        if (turmas == null || turmas.isEmpty()) {
            throw new IllegalArgumentException("é necessário informar pelo menos uma turma para o curso");
        }
        this.turmas.addAll(turmas);
    }

    public void adicionarTurma(Turma turma) {
        if (turma == null) {
            throw new IllegalArgumentException("A turma não pode ser nula!!!");
        }
        turmas.add(turma);
        turma.setCurso(this);
    }

    public void removerTurma(Turma turma) {
        if (turma == null) {
            throw new IllegalArgumentException("A turma não pode ser nula!!!");
        }
        if (!turmas.isEmpty()) {
            turmas.remove(turma);
        }
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Curso curso = (Curso) o;
        return ativo == curso.ativo
                && Objects.equals(id, curso.id)
                && Objects.equals(nome, curso.nome)
                && Objects.equals(descricao, curso.descricao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, descricao);
    }

    @Override
    public String toString() {
        return String.format("Curso %s, descricao: %s, %s, coordenador: %s, quantidade de turmas: %d",
                nome, descricao, ativo ? "ativo" : "inativo", coordenador.getNome(), turmas.size());
    }
}
