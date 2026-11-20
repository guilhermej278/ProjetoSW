package br.com.pizzaria.dao;

import br.com.pizzaria.model.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    public List<Produto> listarProdutos(String categoria) throws SQLException {
        List<Produto> produtos = new ArrayList<>();
        String sql;

        if (categoria == null || categoria.trim().isEmpty()) {
            sql = "SELECT id, nome, descricao, preco, categoria FROM produtos WHERE ativo = TRUE ORDER BY id";
        } else {
            sql = "SELECT id, nome, descricao, preco, categoria FROM produtos "
                    + "WHERE ativo = TRUE AND categoria = ? ORDER BY id";
        }

        try (Connection conexao = ConexaoDB.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {

            if (categoria != null && !categoria.trim().isEmpty()) {
                stmt.setString(1, categoria);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    produtos.add(new Produto(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("descricao"),
                            rs.getBigDecimal("preco"),
                            rs.getString("categoria")
                    ));
                }
            }
        }
        return produtos;
    }

    public Produto consultarProduto(int id) throws SQLException {
        String sql = "SELECT id, nome, descricao, preco, categoria FROM produtos WHERE id = ? AND ativo = TRUE";

        try (Connection conexao = ConexaoDB.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Produto(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("descricao"),
                            rs.getBigDecimal("preco"),
                            rs.getString("categoria")
                    );
                }
            }
        }
        return null;
    }
}
