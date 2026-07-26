package br.com.lojaveloz.pedidos.domain.repository;

import br.com.lojaveloz.pedidos.domain.model.Pedido;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PedidoRepository {

    Pedido salvar(Pedido pedido);

    Optional<Pedido> buscarPorId(UUID id);

    List<Pedido> buscarTodos();
}