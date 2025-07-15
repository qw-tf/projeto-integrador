-- triggers.sql

CREATE OR REPLACE FUNCTION remover_produto_se_zero()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.quantidade <= 0 THEN
        DELETE FROM produtos WHERE codigo = NEW.codigo;
        RETURN NULL;  -- Cancela o UPDATE porque o produto será removido
    ELSE
        RETURN NEW;
    END IF;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_remover_produto
BEFORE UPDATE ON produtos
FOR EACH ROW
WHEN (NEW.quantidade <= 0)
EXECUTE FUNCTION remover_produto_se_zero();