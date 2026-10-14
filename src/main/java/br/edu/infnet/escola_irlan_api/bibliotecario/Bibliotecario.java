package br.edu.infnet.escola_irlan_api.bibliotecario;

import br.edu.infnet.escola_irlan_api.common.domain.Pessoa;
import jakarta.persistence.*;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "bibliotecario")
public class Bibliotecario extends Pessoa {

    @Enumerated(EnumType.STRING)
    @Column(name = "cargo")
    private CargoBibliotecaEnum cargo;

    @Enumerated(EnumType.STRING)
    @Column(name = "turno")
    private TurnoBibliotecaEnum turno;

    public Bibliotecario() {
    }

    public Bibliotecario(Long id, String nome, String cpf, String email, LocalDate dataNascimento, CargoBibliotecaEnum cargo, TurnoBibliotecaEnum turno) {
        super(id, nome, cpf, email, dataNascimento);
        this.cargo = cargo;
        this.turno = turno;
    }

    public CargoBibliotecaEnum getCargo() {
        return cargo;
    }

    public void setCargo(CargoBibliotecaEnum cargo) {
        this.cargo = cargo;
    }

    public TurnoBibliotecaEnum getTurno() {
        return turno;
    }

    public void setTurno(TurnoBibliotecaEnum turno) {
        this.turno = turno;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Bibliotecario that = (Bibliotecario) o;
        return cargo == that.cargo && turno == that.turno;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cargo, turno);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("funcionário",this.getNome())
                .append("cargo", cargo)
                .append("turno", turno)
                .append("data de cadastro", this.getDataCadastro())
                .append("ativo", this.isAtivo())
                .toString();
    }
}
