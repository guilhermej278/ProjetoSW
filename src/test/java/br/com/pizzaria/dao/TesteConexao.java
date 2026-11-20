package br.com.pizzaria.dao;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertTrue;

@EnabledIfSystemProperty(named = "executarTestesIntegracao", matches = "true")
class TesteConexao {

    @Test
    void deveConectarAoPostgreSQL() throws Exception {
        try (Connection conexao = ConexaoDB.getConnection()) {
            assertTrue(conexao.isValid(3));
        }
    }
}
