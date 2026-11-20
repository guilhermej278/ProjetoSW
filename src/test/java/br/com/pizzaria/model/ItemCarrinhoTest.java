package br.com.pizzaria.model;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class ItemCarrinhoTest {

    private Produto produto() {
        return new Produto(1, "Calabresa", "Pizza de calabresa",
                new BigDecimal("38.00"), "Pizzas");
    }

    @Test
    void deveCriarItemComProdutoEQuantidadeValidos() {
        ItemCarrinho item = new ItemCarrinho(produto(), 2);
        assertEquals(2, item.getQuantidade());
        assertEquals("Calabresa", item.getProduto().getNome());
    }

    @Test
    void deveCalcularSubtotalCorretamente() {
        ItemCarrinho item = new ItemCarrinho(produto(), 3);
        assertEquals(new BigDecimal("114.00"), item.getSubtotal());
    }

    @Test
    void naoDeveAceitarProdutoNulo() {
        assertThrows(IllegalArgumentException.class, () -> new ItemCarrinho(null, 1));
    }

    @Test
    void naoDeveAceitarQuantidadeZeroOuNegativa() {
        assertThrows(IllegalArgumentException.class, () -> new ItemCarrinho(produto(), 0));
        assertThrows(IllegalArgumentException.class, () -> new ItemCarrinho(produto(), -1));
    }
}
