package br.com.pizzaria.model;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class PedidoTest {

    @Test
    void deveCriarPedidoComStatusRecebido() {
        Pedido pedido = new Pedido(null, "Rua A, 123", "ENTREGA", new BigDecimal("59.90"));
        assertEquals("RECEBIDO", pedido.getStatus());
        assertEquals("Rua A, 123", pedido.getEndereco());
        assertEquals(new BigDecimal("59.90"), pedido.getTotal());
    }

    @Test
    void devePermitirAtualizarStatusDoPedido() {
        Pedido pedido = new Pedido();
        pedido.setStatus("EM_PREPARO");
        assertEquals("EM_PREPARO", pedido.getStatus());
    }
}
