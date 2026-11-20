package br.com.pizzaria.dao;

import br.com.pizzaria.model.Usuario;
import br.com.pizzaria.util.SenhaUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UsuarioDAO {

    public Usuario cadastrar(String nome, String email, String senha) throws SQLException {
        String sql = "INSERT INTO usuarios (nome, email, senha_hash, perfil) VALUES (?, ?, ?, 'CLIENTE')";
        String hash = SenhaUtil.gerarHash(senha);

        try (Connection conexao = ConexaoDB.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, nome.trim());
            stmt.setString(2, email.trim().toLowerCase());
            stmt.setString(3, hash);
            stmt.executeUpdate();

            try (ResultSet chaves = stmt.getGeneratedKeys()) {
                if (chaves.next()) {
                    return new Usuario(chaves.getInt(1), nome.trim(), email.trim().toLowerCase(), "CLIENTE");
                }
            }
        }
        throw new SQLException("Não foi possível obter o ID do usuário cadastrado.");
    }

    public Usuario autenticar(String email, String senha) throws SQLException {
        String sql = "SELECT id, nome, email, senha_hash, perfil FROM usuarios WHERE email = ?";

        try (Connection conexao = ConexaoDB.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, email.trim().toLowerCase());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && SenhaUtil.verificar(senha, rs.getString("senha_hash"))) {
                    return new Usuario(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("perfil")
                    );
                }
            }
        }
        return null;
    }
}
