package br.com.lojaveloz.pedidos.application.dto;

import br.com.lojaveloz.pedidos.domain.model.ItemPedido;

import java.math.BigDecimal;
import java.util.UUID;

public record ItemPedidoResponse(
        UUID id,
        UUID produtoId,
        String nomeProduto,
        Integer quantidade,
        BigDecimal precoUnitario,
        BigDecimal subtotal
) {

    public static ItemPedidoResponse from(ItemPedido item) {
        return new ItemPedidoResponse(
                item.getId(),
                item.getProdutoId(),
                item.getNomeProduto(),
                item.getQuantidade(),
                item.getPrecoUnitario(),
                item.getSubtotal()
        );
    }
}