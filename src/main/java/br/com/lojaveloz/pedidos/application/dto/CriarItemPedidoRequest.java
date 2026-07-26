package br.com.lojaveloz.pedidos.application.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record CriarItemPedidoRequest(

        @NotNull(message = "O produto é obrigatório.")
        UUID produtoId,

        @NotBlank(message = "O nome do produto é obrigatório.")
        String nomeProduto,

        @NotNull(message = "A quantidade é obrigatória.")
        @Min(
                value = 1,
                message = "A quantidade deve ser maior que zero."
        )
        Integer quantidade,

        @NotNull(message = "O preço unitário é obrigatório.")
        @DecimalMin(
                value = "0.01",
                message = "O preço unitário deve ser maior que zero."
        )
        BigDecimal precoUnitario

) {
}