package br.com.pizzaria.dao;

import br.com.pizzaria.model.ItemCarrinho;
import br.com.pizzaria.model.Pedido;
import br.com.pizzaria.model.Produto;
import br.com.pizzaria.model.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@EnabledIfSystemProperty(named = "executarTestesIntegracao", matches = "true")
class PedidoDAOIntegrationTest {

    private final PedidoDAO pedidoDAO = new PedidoDAO();
    private Usuario usuario;
    private int produtoId;

    @BeforeEach
    void prepararDados() throws SQLException {
        usuario = BancoTeste.criarUsuario(BancoTeste.emailUnico());
        produtoId = BancoTeste.criarProduto();
    }

    @AfterEach
    void limparDados() throws SQLException {
        if (usuario != null) {
            BancoTeste.excluirPedidosDoUsuario(usuario.getId());
            BancoTeste.excluirUsuario(usuario.getId());
        }
        if (produtoId > 0) {
            BancoTeste.excluirProduto(produtoId);
        }
    }

    @Test
    void deveSalvarPedidoEItensNoPostgreSQL() throws SQLException {
        Produto produto = new Produto(produtoId, "Produto de teste", "Descrição", new BigDecimal("31.50"), "Pizzas");
        ItemCarrinho item = new ItemCarrinho(produto, 2);
        Pedido pedido = new Pedido(usuario, "Rua de Teste, 100", "ENTREGA", item.getSubtotal());

        int pedidoId = pedidoDAO.salvarPedido(pedido, Collections.singletonList(item));

        assertTrue(pedidoId > 0);
        assertEquals(1, BancoTeste.contarPedidosDoUsuario(usuario.getId()));
        assertEquals(1, BancoTeste.contarItensDoPedido(pedidoId));
    }

    @Test
    void deveDesfazerPedidoQuandoItemNaoPodeSerSalvo() throws SQLException {
        Produto produtoInexistente = new Produto(Integer.MAX_VALUE, "Produto inexistente",
                "Descrição", new BigDecimal("10.00"), "Pizzas");
        ItemCarrinho item = new ItemCarrinho(produtoInexistente, 1);
        Pedido pedido = new Pedido(usuario, "Rua de Teste, 100", "ENTREGA", item.getSubtotal());

        assertThrows(SQLException.class,
                () -> pedidoDAO.salvarPedido(pedido, Collections.singletonList(item)));
        assertEquals(0, BancoTeste.contarPedidosDoUsuario(usuario.getId()));
    }
}
