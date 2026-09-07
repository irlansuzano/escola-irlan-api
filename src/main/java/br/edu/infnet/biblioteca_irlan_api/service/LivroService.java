package br.edu.infnet.biblioteca_irlan_api.service;

import br.edu.infnet.biblioteca_irlan_api.domain.Livro;
import br.edu.infnet.biblioteca_irlan_api.dto.LivroRequestDTO;
import br.edu.infnet.biblioteca_irlan_api.dto.LivroResponseDTO;
import br.edu.infnet.biblioteca_irlan_api.repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class LivroService extends BaseService<Livro, LivroRepository> {

    public LivroService(LivroRepository repository) {
        super(repository);
    }

    public Collection<LivroResponseDTO> obterLivros() {
        return this.obterLista().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public LivroResponseDTO obterLivroPorId(Long id) {
        return toResponseDTO(this.obterPorId(id));
    }

    public LivroResponseDTO cadastrarLivro(LivroRequestDTO dto) {
        Livro livro = toEntity(dto);
        this.incluir(livro);
        return toResponseDTO(livro);
    }

    public LivroResponseDTO alterarLivro(Long idLivro, LivroRequestDTO dto) {
        Livro livroExistente = this.obterPorId(idLivro);

        livroExistente.setTitulo(dto.titulo());
        livroExistente.setAutor(dto.autor());
        this.alterar(livroExistente);
        return toResponseDTO(livroExistente);
    }

    public LivroResponseDTO buscarLivroPorTitulo(String titulo) {
        Livro livro = repository.findByTitulo(titulo)
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado com o título: " + titulo));
        return toResponseDTO(livro);
    }

    public Livro toEntity(LivroRequestDTO dto) {
        Livro livro = new Livro();
        livro.setTitulo(dto.titulo());
        livro.setAutor(dto.autor());
        return livro;
    }

    public LivroResponseDTO toResponseDTO(Livro livro) {
        return new LivroResponseDTO(
                livro.getId(),
                livro.getTitulo(),
                livro.getAutor()
        );
    }
}
