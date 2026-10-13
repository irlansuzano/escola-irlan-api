package br.edu.infnet.escola_irlan_api.bibliotecario;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BibliotecarioRepository extends JpaRepository<Bibliotecario, Long> {
    List<Bibliotecario> findByCargo(String cargo);

    List<Bibliotecario> findByTurno(String turno);

    List<Bibliotecario> findByNome(String nome);

    List<Bibliotecario> findByEmail(String email);

    List<Bibliotecario> findByAtivo();

    void cadastrarFuncionario(Bibliotecario bibliotecario);

    void desativarFuncionario(Long id);

    void ativarFuncionario(Long id);




}
