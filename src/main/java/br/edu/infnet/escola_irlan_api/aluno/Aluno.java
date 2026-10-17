package br.edu.infnet.escola_irlan_api.aluno;

import br.edu.infnet.escola_irlan_api.curso.Curso;
import br.edu.infnet.escola_irlan_api.common.domain.Pessoa;
import br.edu.infnet.escola_irlan_api.turma.Turma;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "aluno")
public class Aluno extends Pessoa {

    @NotBlank(message = "A matrícula é obrigatória")
    @Size(min = 5, max = 20, message = "A matrícula deve ter entre 5 e 20 caracteres")
    @Column(name = "matricula")
    private String matricula;

    @ManyToOne
    @JoinColumn(name = "id_turma")
    @JsonBackReference
    private Turma turma;

    public Aluno(Long id, String nome, String cpf, String email, LocalDate dataNascimento, String matricula, String turma, Curso curso) {
        super(id, nome, cpf, email, dataNascimento);
        this.matricula = matricula;
    }

    public Aluno() {

    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public Turma getTurma() {
        return turma;
    }

    public void setTurma(Turma turma) {
        this.turma = turma;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Aluno aluno = (Aluno) o;
        return Objects.equals(getId(), aluno.getId()) && Objects.equals(matricula, aluno.matricula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), matricula);
    }

    @Override
    public String toString() {
        return String.format("Aluno %s, cpf: %s, email: %s, matricula: %s, turma: %s",
                getNome(), getCpf(), getEmail(), matricula, turma != null ? turma.getIdentificador() : "N/A");
    }
}
