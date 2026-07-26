package br.com.lojaveloz.pedidos.application.service;

import br.com.lojaveloz.pedidos.application.dto.CriarPedidoRequest;
import br.com.lojaveloz.pedidos.application.dto.PedidoResponse;
import br.com.lojaveloz.pedidos.domain.model.ItemPedido;
import br.com.lojaveloz.pedidos.domain.model.Pedido;
import br.com.lojaveloz.pedidos.domain.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.lojaveloz.pedidos.infrastructure.exception.RecursoNaoEncontradoException;

import java.util.List;
import java.util.UUID;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    @Transactional
    public PedidoResponse criar(CriarPedidoRequest request) {
        List<ItemPedido> itens = request.itens()
                .stream()
                .map(item -> new ItemPedido(
                        item.produtoId(),
                        item.nomeProduto(),
                        item.quantidade(),
                        item.precoUnitario()
                ))
                .toList();

        Pedido pedido = new Pedido(
                request.clienteId(),
                itens
        );

        Pedido pedidoSalvo =
                pedidoRepository.salvar(pedido);

        return PedidoResponse.from(pedidoSalvo);
    }

    @Transactional(readOnly = true)
    public List<PedidoResponse> listar() {
        return pedidoRepository.buscarTodos()
                .stream()
                .map(PedidoResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public PedidoResponse buscarPorId(UUID id) {
        return PedidoResponse.from(
                buscarEntidadePorId(id)
        );
    }
    @Transactional
    public PedidoResponse iniciarPagamento(UUID id) {
        Pedido pedido = buscarEntidadePorId(id);

        pedido.iniciarPagamento();

        return PedidoResponse.from(pedido);
    }

    @Transactional
    public PedidoResponse confirmarPagamento(UUID id) {
        Pedido pedido = buscarEntidadePorId(id);

        pedido.confirmarPagamento();

        return PedidoResponse.from(pedido);
    }

    @Transactional
    public PedidoResponse cancelar(UUID id) {
        Pedido pedido = buscarEntidadePorId(id);

        pedido.cancelar();

        return PedidoResponse.from(pedido);
    }

    private Pedido buscarEntidadePorId(UUID id) {
        return pedidoRepository.buscarPorId(id)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Pedido não encontrado: " + id
                        )
                );
    }
}