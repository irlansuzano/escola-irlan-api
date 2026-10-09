package br.edu.infnet.biblioteca_irlan_api.aluguel;

import br.edu.infnet.biblioteca_irlan_api.aluno.Aluno;
import br.edu.infnet.biblioteca_irlan_api.livro.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AluguelRepository extends JpaRepository<Aluguel, Long> {
    List<Aluguel> findByIsAtivoTrue();

    Optional<Aluguel> findByLivroAndIsAtivoTrue(Livro livro);

    List<Aluguel> findByLivro(Livro livro);

    List<Aluguel> findByAluno(Aluno aluno);
}
