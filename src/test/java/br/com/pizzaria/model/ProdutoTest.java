package br.com.pizzaria.model;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class ProdutoTest {

    @Test
    void deveCriarProdutoComDadosValidos() {
        Produto produto = new Produto(1, "Calabresa", "Pizza de calabresa",
                new BigDecimal("38.00"), "Pizzas");
        assertEquals("Calabresa", produto.getNome());
        assertEquals(new BigDecimal("38.00"), produto.getPreco());
    }

    @Test
    void naoDeveAceitarNomeVazio() {
        assertThrows(IllegalArgumentException.class, () ->
                new Produto(1, " ", "Descrição", new BigDecimal("10.00"), "Pizzas"));
    }

    @Test
    void naoDeveAceitarPrecoNegativo() {
        assertThrows(IllegalArgumentException.class, () ->
                new Produto(1, "Produto", "Descrição", new BigDecimal("-1.00"), "Pizzas"));
    }

    @Test
    void naoDeveAceitarPrecoNulo() {
        assertThrows(IllegalArgumentException.class, () ->
                new Produto(3, "Pizza", "Descrição", null, "Pizzas"));
    }
}
