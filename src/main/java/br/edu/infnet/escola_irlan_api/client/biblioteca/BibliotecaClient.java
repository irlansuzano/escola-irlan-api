package br.edu.infnet.escola_irlan_api.client.biblioteca;

import br.edu.infnet.escola_irlan_api.client.biblioteca.dto.AluguelRequestDTO;
import br.edu.infnet.escola_irlan_api.client.biblioteca.dto.AluguelResponseDTO;
import br.edu.infnet.escola_irlan_api.client.biblioteca.dto.DevolucaoRequestDTO;
import br.edu.infnet.escola_irlan_api.client.biblioteca.dto.DevolucaoResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@FeignClient(name = "biblioteca-client", url = "${biblioteca.service.url}")
public interface BibliotecaClient {

    @GetMapping("/alugueis")
    ResponseEntity<List<AluguelResponseDTO>> obterAlugueis(
            @RequestParam(required = false) Long idLivro,
            @RequestParam(required = false) Long idAluno);

    @GetMapping("/alugueis/{id}")
    ResponseEntity<AluguelResponseDTO> obterAluguelPorId(@PathVariable Long id);

    @PostMapping("/alugueis/alugar")
    ResponseEntity<AluguelResponseDTO> alugar(@RequestBody AluguelRequestDTO aluguel);

    @PutMapping("/alugueis/{id}/renovar")
    ResponseEntity<AluguelResponseDTO> renovar(@PathVariable Long id, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate novaDataFim);

    @PostMapping("/devolucoes")
    ResponseEntity<DevolucaoResponseDTO> devolver(@RequestBody DevolucaoRequestDTO devolucao);

    @GetMapping("/devolucoes")
    ResponseEntity<List<DevolucaoResponseDTO>> obterDevolucoes();

    @GetMapping("/devolucoes/{id}")
    ResponseEntity<DevolucaoResponseDTO> obterDevolucaoPorId(@PathVariable Long id);
}
