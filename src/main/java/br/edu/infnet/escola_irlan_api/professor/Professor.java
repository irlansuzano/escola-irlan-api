package br.edu.infnet.escola_irlan_api.professor;

import br.edu.infnet.escola_irlan_api.curso.Curso;
import br.edu.infnet.escola_irlan_api.common.domain.Pessoa;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "professor")
public class Professor extends Pessoa {

    @NotBlank(message = "O registro profissional é obrigatório")
    @Size(min = 3, max = 50, message = "O registro profissional deve ter entre 3 e 50 caracteres")
    @Column(name = "registro_profissional")
    private String registroProfissional;

    @OneToOne(mappedBy = "coordenador")
    @JsonIgnore
    private Curso coordenacao;

    public Professor() {
    }

    public Professor(Long id, String nome, String cpf, String email, LocalDate dataNascimento, String registroProfissional, Curso coordenacao) {
        super(id, nome, cpf, email, dataNascimento);
        this.registroProfissional = registroProfissional;
        this.coordenacao = coordenacao;
    }

    public String getRegistroProfissional() {
        return registroProfissional;
    }

    public void setRegistroProfissional(String registroProfissional) {
        this.registroProfissional = registroProfissional;
    }

    public Curso getCoordenacao() {
        return coordenacao;
    }

    public void setCoordenacao(Curso coordenacao) {
        this.coordenacao = coordenacao;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Professor professor = (Professor) o;
        return Objects.equals(getId(), professor.getId()) && Objects.equals(registroProfissional, professor.registroProfissional);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), registroProfissional);
    }

    @Override
    public String toString() {
        return String.format("Professor %s, registro profissional: %s, coordenacao: %s",
                getNome(),
                registroProfissional,
                coordenacao != null ? coordenacao.getNome() : "N/A");
    }
}
