package br.edu.infnet.biblioteca_irlan_api.common.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "pessoa")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pessoa implements Identificavel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 2, max = 150, message = "O nome deve ter entre 2 e 150 caracteres")
    @Column(name = "nome")
    private String nome;

    @NotBlank(message = "O CPF é obrigatório")
    @Column(name = "cpf")
    private String cpf;

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "Email inválido")
    @Column(name = "email")
    private String email;

    @NotNull(message = "A data de nascimento é obrigatória")
    @PastOrPresent(message = "A data de nascimento não pode ser futura")
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(name = "ativo")
    private boolean ativo;

    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;

    protected Pessoa() {
    }

    protected Pessoa(Long id, String nome, String cpf, String email, LocalDate dataNascimento) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.ativo = true;
        this.dataCadastro = LocalDate.now();
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pessoa pessoa = (Pessoa) o;
        return Objects.equals(id, pessoa.id) && Objects.equals(nome, pessoa.nome) && Objects.equals(cpf, pessoa.cpf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, cpf);
    }

    @Override
    public String toString() {
        return String.format("Pessoa - nome: %s, cpf: %s, email:%s, dataNascimento: %s, %b, data de Cadastro: %s}",
                nome, cpf, email, dataNascimento, ativo ? "ativo" : "inativo", dataCadastro);
    }
}
