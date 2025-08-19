package com.Forumhub.ApiRest.Dto;

import com.Forumhub.ApiRest.model.Topico;
import java.time.LocalDateTime;

public record DadosDetalhamentoTopico(
        Long id,
        String titulo,
        String mensagem,
        String autor,
        String curso,
        String estado,
        LocalDateTime dataCriacao
) {
    public DadosDetalhamentoTopico(Topico topico) {
        this(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensagem(),
                topico.getAutor(),
                topico.getCurso(),
                topico.getEstado(),
                topico.getDataCriacao()
        );
    }
}
