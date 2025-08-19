package com.Forumhub.ApiRest.repository;

import com.Forumhub.ApiRest.model.Topico;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
    Optional<Topico> findByTituloAndMensagem(String titulo, String mensagem);

    List<Topico> findAllByOrderByDataCriacaoAsc();

    List<Topico> findByCursoAndDataCriacaoBetweenOrderByDataCriacaoAsc(String curso, LocalDateTime inicio, LocalDateTime fim);
}
