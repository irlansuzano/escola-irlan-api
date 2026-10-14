package br.edu.infnet.escola_irlan_api.aluguel;

import br.edu.infnet.escola_irlan_api.aluno.Aluno;
import br.edu.infnet.escola_irlan_api.livro.Livro;
import br.edu.infnet.escola_irlan_api.aluno.AlunoRepository;
import br.edu.infnet.escola_irlan_api.livro.LivroRepository;
import br.edu.infnet.escola_irlan_api.common.exception.BadRequestException;
import br.edu.infnet.escola_irlan_api.common.exception.InternalServerErrorException;
import br.edu.infnet.escola_irlan_api.common.exception.RecursoNaoEncontradoException;
import br.edu.infnet.escola_irlan_api.common.service.BaseService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AluguelService extends BaseService<Aluguel, AluguelRepository> {

    private final LivroRepository livroRepository;
    private final AlunoRepository alunoRepository;
    private final AluguelMapper aluguelMapper;

    public AluguelService(AluguelRepository repository, LivroRepository livroRepository, AlunoRepository alunoRepository, AluguelMapper aluguelMapper) {
        super(repository);
        this.livroRepository = livroRepository;
        this.alunoRepository = alunoRepository;
        this.aluguelMapper = aluguelMapper;
    }

    public AluguelResponseDTO alugar(AluguelRequestDTO dto) {
        if (isLivroAlugado(dto.livroId())) {
            throw new InternalServerErrorException("Livro já está alugado.");
        }
        if (dto.dataInicioAluguel().isAfter(LocalDate.now())) {
            throw new BadRequestException("Não é possível efetivar o aluguel antes da data de início.");
        }

        Aluguel aluguel = toEntity(dto);
        aluguel.setAtivo(true);
        this.incluir(aluguel);
        return aluguelMapper.toResponseDto(aluguel);
    }

    public AluguelResponseDTO alterarLivroAlugado(Long idAluguel, Long idNovoLivro) {
        Aluguel aluguelAtual = this.obterPorId(idAluguel);
        if (Boolean.FALSE.equals(aluguelAtual.getAtivo())) {
            throw new BadRequestException("não existe aluguel ativo com esse livro.");
        }

        Livro novoLivro = livroRepository.findById(idNovoLivro)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Novo livro não encontrado"));

        aluguelAtual.setLivro(novoLivro);
        this.alterar(aluguelAtual);
        return aluguelMapper.toResponseDto(aluguelAtual);
    }

    public AluguelResponseDTO prolongarAluguel(Long idAluguel, LocalDate novoPrazo) {
        Aluguel aluguel = this.obterPorId(idAluguel);
        aluguel.setDataFimAluguel(novoPrazo);
        this.alterar(aluguel);
        return aluguelMapper.toResponseDto(aluguel);
    }

    public AluguelResponseDTO obterAluguelPorId(Long id) {
        return aluguelMapper.toResponseDto(this.obterPorId(id));
    }

    public Collection<AluguelResponseDTO> obterAlugueis() {
        return this.obterLista().stream()
                .map(aluguelMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public void finalizarAluguel(Long idAluguel) {
        Aluguel aluguel = repository.findById(idAluguel)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Aluguel não encontrado."));
        if (!Boolean.TRUE.equals(aluguel.getAtivo())) {
            throw new BadRequestException("Este aluguel já está finalizado.");
        }
        aluguel.setAtivo(false);
        this.alterar(aluguel);
    }

    public AluguelResponseDTO buscarAluguelPorLivro(Long idLivro) {
        Aluguel aluguel = livroRepository.findById(idLivro)
                .flatMap(repository::findByLivroAndIsAtivoTrue)
                .orElse(null);
        return aluguel != null ? aluguelMapper.toResponseDto(aluguel) : null;
    }

    public List<AluguelResponseDTO> buscarAlugueisPorLivro(Long idLivro) {
        return livroRepository.findById(idLivro)
                .map(livro -> repository.findByLivro(livro).stream()
                        .map(aluguelMapper::toResponseDto)
                        .collect(Collectors.toList()))
                .orElse(List.of());
    }

    public List<AluguelResponseDTO> buscarAlugueisPorAluno(Long idAluno) {
        return alunoRepository.findById(idAluno)
                .map(aluno -> repository.findByAluno(aluno).stream()
                        .map(aluguelMapper::toResponseDto)
                        .collect(Collectors.toList()))
                .orElse(List.of());
    }

    private boolean isLivroAlugado(Long idLivro) {
        return livroRepository.findById(idLivro)
                .map(livro -> repository.findByLivroAndIsAtivoTrue(livro).isPresent())
                .orElse(false);
    }

    public String obterListaAlgueisAtivos() {
        List<Aluguel> alugueisAtivos = repository.findByIsAtivoTrue();
        StringBuilder sb = new StringBuilder("Os seguintes Alugueis estão ativos:\n");
        alugueisAtivos.forEach(aluguel -> sb.append(aluguel.getLivro().getTitulo())
                .append(" alugado por ")
                .append(aluguel.getAluno().getNome())
                .append(" até ")
                .append(aluguel.getDataFimAluguel())
                .append("\n"));
        return sb.toString();
    }

    public Aluguel toEntity(AluguelRequestDTO dto) {
        Aluguel aluguel = aluguelMapper.toEntity(dto);

        Livro livro = livroRepository.findById(dto.livroId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Livro não encontrado"));
        aluguel.setLivro(livro);

        Aluno aluno = alunoRepository.findById(dto.alunoId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Aluno não encontrado"));
        aluguel.setAluno(aluno);

        return aluguel;
    }
}
