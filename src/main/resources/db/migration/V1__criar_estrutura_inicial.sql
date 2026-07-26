CREATE TABLE pedidos (
    id UUID NOT NULL,
    cliente_id UUID NOT NULL,
    valor_total NUMERIC(15, 2) NOT NULL,
    status VARCHAR(30) NOT NULL,
    criado_em TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT pk_pedidos PRIMARY KEY (id),

    CONSTRAINT ck_pedidos_status CHECK (
        status IN (
            'CRIADO',
            'AGUARDANDO_PAGAMENTO',
            'PAGO',
            'CANCELADO'
        )
    ),

    CONSTRAINT ck_pedidos_valor_total CHECK (
        valor_total > 0
    )
);

CREATE TABLE itens_pedido (
    id UUID NOT NULL,
    pedido_id UUID NOT NULL,
    produto_id UUID NOT NULL,
    nome_produto VARCHAR(150) NOT NULL,
    quantidade INTEGER NOT NULL,
    preco_unitario NUMERIC(15, 2) NOT NULL,

    CONSTRAINT pk_itens_pedido PRIMARY KEY (id),

    CONSTRAINT fk_itens_pedido_pedido
        FOREIGN KEY (pedido_id)
        REFERENCES pedidos (id)
        ON DELETE CASCADE,

    CONSTRAINT ck_itens_pedido_quantidade CHECK (
        quantidade > 0
    ),

    CONSTRAINT ck_itens_pedido_preco CHECK (
        preco_unitario > 0
    )
);

CREATE INDEX idx_pedidos_cliente_id
    ON pedidos (cliente_id);

CREATE INDEX idx_pedidos_status
    ON pedidos (status);

CREATE INDEX idx_itens_pedido_pedido_id
    ON itens_pedido (pedido_id);

CREATE INDEX idx_itens_pedido_produto_id
    ON itens_pedido (produto_id);