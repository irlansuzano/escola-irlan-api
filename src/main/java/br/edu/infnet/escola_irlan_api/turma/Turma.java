package br.edu.infnet.biblioteca_irlan_api.turma;

import br.edu.infnet.biblioteca_irlan_api.aluno.Aluno;
import br.edu.infnet.biblioteca_irlan_api.curso.Curso;
import br.edu.infnet.biblioteca_irlan_api.common.domain.Identificavel;
import br.edu.infnet.biblioteca_irlan_api.professor.Professor;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "turma")
public class Turma implements Identificavel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O identificador da turma é obrigatório")
    @Size(min = 2, max = 50, message = "O identificador deve ter entre 2 e 50 caracteres")
    @Column(name = "identificador")
    private String identificador;

    @ManyToOne
    @JoinColumn(name = "id_professor_coordenador")
    private Professor professorCoordenador;

    @Column(name = "ativo")
    private boolean ativo;

    @ManyToOne
    @JoinColumn(name = "id_curso")
    @JsonBackReference
    private Curso curso;

    @OneToMany(mappedBy = "turma", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Aluno> alunos = new ArrayList<>();

    public Turma() {
    }

    public Turma(Long id, String identificador, Professor professorCoordenador, boolean ativo) {
        this.id = id;
        this.identificador = identificador;
        this.professorCoordenador = professorCoordenador;
        this.ativo = ativo;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public Professor getProfessorCoordenador() {
        if (professorCoordenador == null) {
            throw new IllegalArgumentException("O professor coordenador não pode ser nulo!!!");
        }
        return professorCoordenador;
    }

    public void setProfessorCoordenador(Professor professorCoordenador) {
        this.professorCoordenador = professorCoordenador;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        if (curso == null) {
            throw new IllegalArgumentException("O curso não pode ser nulo!!!");
        }
        this.curso = curso;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public List<Aluno> getAlunos() {
        return Collections.unmodifiableList(alunos);
    }

    public void setAlunos(List<Aluno> alunos) {
        if (alunos == null || alunos.isEmpty()) {
            throw new IllegalArgumentException("É necessário informar pelo menos um aluno para a turma");
        }
        this.alunos = alunos;
    }

    public void adicionarAluno(Aluno aluno) {
        if (aluno == null) {
            throw new IllegalArgumentException("O aluno não pode ser nulo!!!");
        }
        alunos.add(aluno);

        aluno.setTurma(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Turma turma = (Turma) o;
        return Objects.equals(id, turma.id)
                && Objects.equals(identificador, turma.identificador)
                && Objects.equals(professorCoordenador, turma.professorCoordenador);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, identificador, professorCoordenador);
    }

    @Override
    public String toString() {
        return String.format("Turma: %s, coordenador %s, %s, curso %s",
                identificador,
                professorCoordenador != null ? professorCoordenador.getNome() : "N/A",
                ativo ? "em atividade" : "inativo",
                curso != null ? curso.getNome() : "N/A");
    }
}
