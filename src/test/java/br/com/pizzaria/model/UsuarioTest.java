package br.com.pizzaria.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UsuarioTest {

    @Test
    void deveCriarUsuarioComDadosInformados() {
        Usuario usuario = new Usuario(7, "Maria Silva", "maria@email.com", "CLIENTE");
        assertEquals(7, usuario.getId());
        assertEquals("Maria Silva", usuario.getNome());
        assertEquals("maria@email.com", usuario.getEmail());
        assertEquals("CLIENTE", usuario.getPerfil());
    }
}
