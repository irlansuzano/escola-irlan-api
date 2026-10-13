package br.edu.infnet.escola_irlan_api.livro;

import br.edu.infnet.escola_irlan_api.common.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class LivroService extends BaseService<Livro, LivroRepository> {

    private final LivroMapper livroMapper;

    public LivroService(LivroRepository repository, LivroMapper livroMapper) {
        super(repository);
        this.livroMapper = livroMapper;
    }

    public Collection<LivroResponseDTO> obterLivros() {
        return this.obterLista().stream()
                .map(livroMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public LivroResponseDTO obterLivroPorId(Long id) {
        return livroMapper.toResponseDto(this.obterPorId(id));
    }

    public LivroResponseDTO cadastrarLivro(LivroRequestDTO dto) {
        Livro livro = livroMapper.toEntity(dto);
        this.incluir(livro);
        return livroMapper.toResponseDto(livro);
    }

    public LivroResponseDTO alterarLivro(Long idLivro, LivroRequestDTO dto) {
        Livro livroExistente = this.obterPorId(idLivro);

        livroExistente.setTitulo(dto.titulo());
        livroExistente.setAutor(dto.autor());
        this.alterar(livroExistente);
        return livroMapper.toResponseDto(livroExistente);
    }

    public LivroResponseDTO buscarLivroPorTitulo(String titulo) {
        Livro livro = repository.findByTitulo(titulo)
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado com o título: " + titulo));
        return livroMapper.toResponseDto(livro);
    }
}
