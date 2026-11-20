package br.com.pizzaria.dao;

import br.com.pizzaria.model.Produto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@EnabledIfSystemProperty(named = "executarTestesIntegracao", matches = "true")
class ProdutoDAOIntegrationTest {

    private final ProdutoDAO produtoDAO = new ProdutoDAO();
    private int produtoId;

    @BeforeEach
    void prepararBanco() throws SQLException {
        produtoId = BancoTeste.criarProduto();
    }

    @AfterEach
    void limparBanco() throws SQLException {
        BancoTeste.excluirProduto(produtoId);
    }

    @Test
    void deveListarProdutosAtivos() throws SQLException {
        List<Produto> produtos = produtoDAO.listarProdutos(null);
        assertFalse(produtos.isEmpty());
        assertTrue(produtos.stream().anyMatch(p -> p.getId() == produtoId));
    }

    @Test
    void deveFiltrarProdutosPorCategoria() throws SQLException {
        List<Produto> produtos = produtoDAO.listarProdutos("Pizzas");
        assertFalse(produtos.isEmpty());
        assertTrue(produtos.stream().allMatch(p -> "Pizzas".equals(p.getCategoria())));
        assertTrue(produtos.stream().anyMatch(p -> p.getId() == produtoId));
    }

    @Test
    void deveConsultarProdutoPeloIdentificador() throws SQLException {
        Produto produto = produtoDAO.consultarProduto(produtoId);
        assertNotNull(produto);
        assertEquals(produtoId, produto.getId());
        assertEquals("Pizzas", produto.getCategoria());
    }
}
