package br.com.lojaveloz.pedidos.infrastructure.exception;

import java.time.OffsetDateTime;
import java.util.Map;

public record ApiErrorResponse(
        OffsetDateTime timestamp,
        int status,
        String erro,
        String mensagem,
        String path,
        Map<String, String> campos
) {
}