package br.edu.infnet.escola_irlan_api.bibliotecario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BibliotecarioRepository extends JpaRepository<Bibliotecario, Long> {
    List<Bibliotecario> findByCargo(CargoBibliotecaEnum cargo);

    List<Bibliotecario> findByTurno(TurnoBibliotecaEnum turno);

    List<Bibliotecario> findByNome(String nome);

    List<Bibliotecario> findByEmail(String email);

    @Query("SELECT b FROM Bibliotecario b WHERE b.ativo = true")
    List<Bibliotecario> findByAtivo();

    @Modifying
    @Transactional
    @Query("UPDATE Bibliotecario b SET b.ativo = false WHERE b.id = :id")
    void desativarFuncionario(Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Bibliotecario b SET b.ativo = true WHERE b.id = :id")
    void ativarFuncionario(Long id);
}
