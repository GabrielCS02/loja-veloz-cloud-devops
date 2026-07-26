package br.com.lojaveloz.pedidos.application.dto;

import br.com.lojaveloz.pedidos.domain.enums.StatusPedido;
import br.com.lojaveloz.pedidos.domain.model.Pedido;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record PedidoResponse(
        UUID id,
        UUID clienteId,
        List<ItemPedidoResponse> itens,
        BigDecimal valorTotal,
        StatusPedido status,
        OffsetDateTime criadoEm,
        OffsetDateTime atualizadoEm
) {

    public static PedidoResponse from(Pedido pedido) {
        List<ItemPedidoResponse> itensResponse = pedido.getItens()
                .stream()
                .map(ItemPedidoResponse::from)
                .toList();

        return new PedidoResponse(
                pedido.getId(),
                pedido.getClienteId(),
                itensResponse,
                pedido.getValorTotal(),
                pedido.getStatus(),
                pedido.getCriadoEm(),
                pedido.getAtualizadoEm()
        );
    }
}