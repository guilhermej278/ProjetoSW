package br.com.pizzaria.dao;

import br.com.pizzaria.model.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@EnabledIfSystemProperty(named = "executarTestesIntegracao", matches = "true")
class UsuarioDAOIntegrationTest {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private String email;

    @BeforeEach
    void prepararDados() {
        email = BancoTeste.emailUnico();
    }

    @AfterEach
    void limparDados() throws SQLException {
        BancoTeste.excluirUsuarioPorEmail(email);
    }

    @Test
    void deveCadastrarUsuarioEObterIdGerado() throws SQLException {
        Usuario usuario = usuarioDAO.cadastrar("  Cliente Teste  ", email.toUpperCase(), "SenhaTeste123!");
        assertTrue(usuario.getId() > 0);
        assertEquals("Cliente Teste", usuario.getNome());
        assertEquals(email, usuario.getEmail());
        assertEquals("CLIENTE", usuario.getPerfil());
    }

    @Test
    void deveAutenticarUsuarioComCredenciaisValidas() throws SQLException {
        BancoTeste.criarUsuario(email);
        Usuario usuario = usuarioDAO.autenticar(email.toUpperCase(), "SenhaTeste123!");
        assertNotNull(usuario);
        assertEquals(email, usuario.getEmail());
    }

    @Test
    void naoDeveAutenticarUsuarioComSenhaIncorreta() throws SQLException {
        BancoTeste.criarUsuario(email);
        Usuario usuario = usuarioDAO.autenticar(email, "senha-incorreta");
        assertNull(usuario);
    }
}
