package com.Forumhub.ApiRest.Controller;


import com.Forumhub.ApiRest.Dto.DadosAtualizacaoTopico;
import com.Forumhub.ApiRest.Dto.DadosCadastroTopico;
import com.Forumhub.ApiRest.Dto.DadosDetalhamentoTopico;
import com.Forumhub.ApiRest.model.Topico;
import com.Forumhub.ApiRest.repository.TopicoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    @Autowired
    private TopicoRepository repository;

    @GetMapping
    public ResponseEntity<List<DadosDetalhamentoTopico>> listar() {
        List<Topico> topicos = repository.findAll();
        List<DadosDetalhamentoTopico> resposta = topicos.stream()
                .map(DadosDetalhamentoTopico::new)
                .toList();
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/ordenado")
    public ResponseEntity<List<DadosDetalhamentoTopico>> listarOrdenado() {
        List<Topico> topicos = repository.findAllByOrderByDataCriacaoAsc();
        List<DadosDetalhamentoTopico> resposta = topicos.stream()
                .map(DadosDetalhamentoTopico::new)
                .toList();
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/filtro")
    public ResponseEntity<List<DadosDetalhamentoTopico>> listarPorCursoEAno(
            @RequestParam String curso,
            @RequestParam int ano) {

        LocalDateTime inicio = LocalDateTime.of(ano, 1, 1, 0, 0);
        LocalDateTime fim = LocalDateTime.of(ano, 12, 31, 23, 59);

        List<Topico> topicos = repository.findByCursoAndDataCriacaoBetweenOrderByDataCriacaoAsc(
                curso, inicio, fim);

        List<DadosDetalhamentoTopico> resposta = topicos.stream()
                .map(DadosDetalhamentoTopico::new)
                .toList();

        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/pagina")
    public ResponseEntity<Page<DadosDetalhamentoTopico>> listarComPagina(
            @PageableDefault(size = 10, sort = "dataCriacao") Pageable pageable) {

        Page<Topico> page = repository.findAll(pageable);
        Page<DadosDetalhamentoTopico> resposta = page.map(DadosDetalhamentoTopico::new);
        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoTopico> detalhar(@PathVariable Long id) {
        var topico = repository.findById(id);
        if (topico.isPresent()) {
            return ResponseEntity.ok(new DadosDetalhamentoTopico(topico.get()));
        } else {
            return ResponseEntity.status(404)
                    .body(null); // ou use ErroDTO com ResponseEntity<ErroDTO>
        }
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody @Valid DadosCadastroTopico dados) {
        // Verifica se já existe um tópico com mesmo título e mensagem
        var existente = repository.findByTituloAndMensagem(dados.titulo(), dados.mensagem());
        if (existente.isPresent()) {
            return ResponseEntity.badRequest().body("Já existe um tópico com este título e mensagem.");
        }

        var topico = new Topico(dados.titulo(), dados.mensagem(), dados.autor(), dados.curso());
        repository.save(topico);

        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id,
                                       @RequestBody @Valid DadosAtualizacaoTopico dados) {

        // Verifica se o tópico existe
        var optionalTopico = repository.findById(id);
        if (optionalTopico.isEmpty()) {
            return ResponseEntity.status(404)
                    .body("Tópico com ID " + id + " não encontrado");
        }

        // Verifica duplicidade (não contar o próprio tópico)
        var duplicado = repository.findByTituloAndMensagem(dados.titulo(), dados.mensagem());
        if (duplicado.isPresent() && !duplicado.get().getId().equals(id)) {
            return ResponseEntity.badRequest()
                    .body("Já existe outro tópico com este título e mensagem.");
        }

        // Atualiza os dados do tópico
        var topico = optionalTopico.get();
        topico.setTitulo(dados.titulo());
        topico.setMensagem(dados.mensagem());
        topico.setAutor(dados.autor());
        topico.setCurso(dados.curso());

        repository.save(topico);

        return ResponseEntity.ok(new DadosDetalhamentoTopico(topico));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {

        // Verifica se o tópico existe
        var optionalTopico = repository.findById(id);
        if (optionalTopico.isEmpty()) {
            return ResponseEntity.status(404)
                    .body("Tópico com ID " + id + " não encontrado");
        }

        // Deleta o tópico
        repository.deleteById(id);

        return ResponseEntity.ok("Tópico com ID " + id + " excluído com sucesso");
    }

}
