package br.com.lojaveloz.pedidos.application.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record CriarPedidoRequest(

        @NotNull(message = "O cliente é obrigatório.")
        UUID clienteId,

        @Valid
        @NotEmpty(
                message = "O pedido deve possuir pelo menos um item."
        )
        List<CriarItemPedidoRequest> itens

) {
}