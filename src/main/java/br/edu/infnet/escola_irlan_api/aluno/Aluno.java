package br.edu.infnet.escola_irlan_api.aluno;

import br.edu.infnet.escola_irlan_api.aluguel.Aluguel;
import br.edu.infnet.escola_irlan_api.curso.Curso;
import br.edu.infnet.escola_irlan_api.common.domain.Pessoa;
import br.edu.infnet.escola_irlan_api.turma.Turma;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Aluguel> alugueis = new ArrayList<>();

    public Aluno(Long id, String nome, String cpf, String email, LocalDate dataNascimento, String matricula, String turma, Curso curso) {
        super(id, nome, cpf, email, dataNascimento);
        this.matricula = matricula;
    }

    public Aluno() {

    }

    public List<Aluguel> getAlugueis() {
        return alugueis;
    }

    public void setAlugueis(List<Aluguel> alugueis) {
        this.alugueis = alugueis;
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
                getNome(), getCpf(), getEmail(), matricula, turma.getIdentificador());
    }
}
