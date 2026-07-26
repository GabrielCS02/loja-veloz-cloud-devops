ALTER TABLE pedidos
    ADD COLUMN atualizado_em TIMESTAMP WITH TIME ZONE;

UPDATE pedidos
SET atualizado_em = criado_em
WHERE atualizado_em IS NULL;

ALTER TABLE pedidos
    ALTER COLUMN atualizado_em SET NOT NULL;