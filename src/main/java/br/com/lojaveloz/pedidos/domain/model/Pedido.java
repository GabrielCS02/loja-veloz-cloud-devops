package br.com.lojaveloz.pedidos.domain.model;

import br.com.lojaveloz.pedidos.domain.enums.StatusPedido;
import br.com.lojaveloz.pedidos.domain.exception.RegraDeNegocioException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    private UUID id;

    @Column(name = "cliente_id", nullable = false)
    private UUID clienteId;

    @Column(
            name = "valor_total",
            nullable = false,
            precision = 15,
            scale = 2
    )
    private BigDecimal valorTotal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private StatusPedido status;

    @Column(name = "criado_em", nullable = false)
    private OffsetDateTime criadoEm;

    @Column(name = "atualizado_em", nullable = false)
    private OffsetDateTime atualizadoEm;

    @OneToMany(
            mappedBy = "pedido",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ItemPedido> itens = new ArrayList<>();

    protected Pedido() {
        // Construtor exigido pelo JPA
    }

    public Pedido(UUID clienteId, List<ItemPedido> itens) {
        if (clienteId == null) {
            throw new IllegalArgumentException(
                    "O cliente é obrigatório."
            );
        }

        if (itens == null || itens.isEmpty()) {
            throw new IllegalArgumentException(
                    "O pedido deve possuir pelo menos um item."
            );
        }

        this.id = UUID.randomUUID();
        this.clienteId = clienteId;
        this.status = StatusPedido.CRIADO;
        this.criadoEm = OffsetDateTime.now();
        this.atualizadoEm = this.criadoEm;

        itens.forEach(this::adicionarItem);

        this.valorTotal = calcularValorTotal();
    }

    private void adicionarItem(ItemPedido item) {
        if (item == null) {
            throw new IllegalArgumentException(
                    "O item do pedido não pode ser nulo."
            );
        }

        item.vincularAoPedido(this);
        this.itens.add(item);
    }

    private BigDecimal calcularValorTotal() {
        return itens.stream()
                .map(ItemPedido::calcularSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void iniciarPagamento() {
        validarStatus(
                StatusPedido.CRIADO,
                "Somente pedidos criados podem iniciar o pagamento."
        );

        this.status = StatusPedido.AGUARDANDO_PAGAMENTO;
        atualizarDataDeModificacao();
    }

    public void confirmarPagamento() {
        validarStatus(
                StatusPedido.AGUARDANDO_PAGAMENTO,
                "Somente pedidos aguardando pagamento podem ser marcados como pagos."
        );

        this.status = StatusPedido.PAGO;
        atualizarDataDeModificacao();
    }

    public void cancelar() {
        if (this.status != StatusPedido.CRIADO
                && this.status != StatusPedido.AGUARDANDO_PAGAMENTO) {
            throw new RegraDeNegocioException(
                    "O pedido não pode ser cancelado no status "
                            + this.status
                            + "."
            );
        }

        this.status = StatusPedido.CANCELADO;
        atualizarDataDeModificacao();
    }

    private void validarStatus(
            StatusPedido statusEsperado,
            String mensagem
    ) {
        if (this.status != statusEsperado) {
            throw new RegraDeNegocioException(mensagem);
        }
    }

    private void atualizarDataDeModificacao() {
        this.atualizadoEm = OffsetDateTime.now();
    }

    public UUID getId() {
        return id;
    }

    public UUID getClienteId() {
        return clienteId;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public OffsetDateTime getCriadoEm() {
        return criadoEm;
    }

    public OffsetDateTime getAtualizadoEm() {
        return atualizadoEm;
    }

    public List<ItemPedido> getItens() {
        return Collections.unmodifiableList(itens);
    }
}