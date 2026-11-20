package br.com.pizzaria.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SenhaUtilTest {

    @Test
    void deveValidarSenhaCorreta() {
        String hash = SenhaUtil.gerarHash("senha-forte-123");
        assertTrue(SenhaUtil.verificar("senha-forte-123", hash));
    }

    @Test
    void deveRejeitarSenhaIncorreta() {
        String hash = SenhaUtil.gerarHash("senha-forte-123");
        assertFalse(SenhaUtil.verificar("outra-senha", hash));
    }
}
