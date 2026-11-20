package br.com.pizzaria.dao;

import br.com.pizzaria.model.Usuario;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

final class BancoTeste {

    private BancoTeste() {
    }

    static String emailUnico() {
        return "teste." + UUID.randomUUID().toString().replace("-", "") + "@example.com";
    }

    static Usuario criarUsuario(String email) throws SQLException {
        return new UsuarioDAO().cadastrar("Usuario de Teste", email, "SenhaTeste123!");
    }

    static int criarProduto() throws SQLException {
        String sql = "INSERT INTO produtos (nome, descricao, preco, categoria, ativo) "
                + "VALUES (?, ?, ?, ?, TRUE) RETURNING id";
        try (Connection conexao = ConexaoDB.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, "Produto de teste " + UUID.randomUUID());
            stmt.setString(2, "Produto criado pelos testes de integração");
            stmt.setBigDecimal(3, new BigDecimal("31.50"));
            stmt.setString(4, "Pizzas");
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        throw new SQLException("Não foi possível criar o produto de teste.");
    }

    static void excluirUsuarioPorEmail(String email) throws SQLException {
        executar("DELETE FROM usuarios WHERE email = ?", email);
    }

    static void excluirUsuario(int id) throws SQLException {
        executar("DELETE FROM usuarios WHERE id = ?", id);
    }

    static void excluirProduto(int id) throws SQLException {
        executar("DELETE FROM produtos WHERE id = ?", id);
    }

    static void excluirPedidosDoUsuario(int usuarioId) throws SQLException {
        executar("DELETE FROM pedidos WHERE usuario_id = ?", usuarioId);
    }

    static int contarPedidosDoUsuario(int usuarioId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM pedidos WHERE usuario_id = ?";
        try (Connection conexao = ConexaoDB.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    static int contarItensDoPedido(int pedidoId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM itens_pedido WHERE pedido_id = ?";
        try (Connection conexao = ConexaoDB.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, pedidoId);
            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    private static void executar(String sql, Object valor) throws SQLException {
        try (Connection conexao = ConexaoDB.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            if (valor instanceof Integer) {
                stmt.setInt(1, (Integer) valor);
            } else {
                stmt.setString(1, String.valueOf(valor));
            }
            stmt.executeUpdate();
        }
    }
}
