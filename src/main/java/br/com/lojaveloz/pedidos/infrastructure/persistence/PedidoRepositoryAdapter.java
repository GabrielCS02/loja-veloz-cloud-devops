package br.com.lojaveloz.pedidos.infrastructure.persistence;

import br.com.lojaveloz.pedidos.domain.model.Pedido;
import br.com.lojaveloz.pedidos.domain.repository.PedidoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PedidoRepositoryAdapter implements PedidoRepository {

    private final SpringDataPedidoRepository springDataRepository;

    public PedidoRepositoryAdapter(
            SpringDataPedidoRepository springDataRepository
    ) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public Pedido salvar(Pedido pedido) {
        return springDataRepository.save(pedido);
    }

    @Override
    public Optional<Pedido> buscarPorId(UUID id) {
        return springDataRepository.findById(id);
    }

    @Override
    public List<Pedido> buscarTodos() {
        return springDataRepository.findAll();
    }
}