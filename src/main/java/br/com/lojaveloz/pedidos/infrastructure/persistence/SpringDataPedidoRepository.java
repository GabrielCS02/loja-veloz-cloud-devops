package br.com.lojaveloz.pedidos.infrastructure.persistence;

import br.com.lojaveloz.pedidos.domain.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataPedidoRepository
        extends JpaRepository<Pedido, UUID> {
}