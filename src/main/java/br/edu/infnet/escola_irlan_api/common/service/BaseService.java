package br.edu.infnet.biblioteca_irlan_api.common.service;

import br.edu.infnet.biblioteca_irlan_api.common.domain.Identificavel;
import br.edu.infnet.biblioteca_irlan_api.common.exception.RecursoNaoEncontradoException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public abstract class BaseService<T extends Identificavel, R extends JpaRepository<T, Long>> {

    protected final R repository;

    protected BaseService(R repository) {
        this.repository = repository;
    }

    public void incluir(T objeto) {
        repository.save(objeto);
    }

    public void alterar(T objeto) {
        repository.save(objeto);
    }

    public void excluir(Long id) {
        validarExistencia(id);
        repository.deleteById(id);
    }

    public T buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Recurso não encontrado com o ID " + id));
    }

    public Collection<T> obterLista() {
        return repository.findAll();
    }

    public T obterPorId(Long id) {
        return buscarPorId(id);
    }

    protected void validarExistencia(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("O ID não pode ser nulo.");
        }

        if (!repository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Recurso não encontrado com o ID " + id);
        }
    }
}
