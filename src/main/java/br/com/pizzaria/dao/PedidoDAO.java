package br.com.pizzaria.dao;

import br.com.pizzaria.model.ItemCarrinho;
import br.com.pizzaria.model.Pedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class PedidoDAO {
    public int salvarPedido(Pedido pedido, List<ItemCarrinho> itens) throws SQLException {
        String sqlPedido = "INSERT INTO pedidos "
                + "(usuario_id, nome_cliente, endereco, tipo_pedido, status, total) "
                + "VALUES (?, ?, ?, ?, 'RECEBIDO', ?)";
        String sqlItem = "INSERT INTO itens_pedido "
                + "(pedido_id, produto_id, nome_produto, quantidade, preco_unitario, subtotal) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexao = ConexaoDB.getConnection()) {
            boolean autoCommitOriginal = conexao.getAutoCommit();
            conexao.setAutoCommit(false);

            try {
                int idPedido;
                try (PreparedStatement stmt = conexao.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS)) {
                    stmt.setInt(1, pedido.getUsuario().getId());
                    stmt.setString(2, pedido.getUsuario().getNome());
                    if (pedido.getEndereco() == null || pedido.getEndereco().trim().isEmpty()) {
                        stmt.setNull(3, java.sql.Types.VARCHAR);
                    } else {
                        stmt.setString(3, pedido.getEndereco());
                    }
                    stmt.setString(4, pedido.getTipoPedido());
                    stmt.setBigDecimal(5, pedido.getTotal());
                    stmt.executeUpdate();

                    try (ResultSet chaves = stmt.getGeneratedKeys()) {
                        if (!chaves.next()) {
                            throw new SQLException("O banco não retornou o ID do pedido.");
                        }
                        idPedido = chaves.getInt(1);
                    }
                }

                try (PreparedStatement stmtItem = conexao.prepareStatement(sqlItem)) {
                    for (ItemCarrinho item : itens) {
                        stmtItem.setInt(1, idPedido);
                        stmtItem.setInt(2, item.getProduto().getId());
                        stmtItem.setString(3, item.getProduto().getNome());
                        stmtItem.setInt(4, item.getQuantidade());
                        stmtItem.setBigDecimal(5, item.getProduto().getPreco());
                        stmtItem.setBigDecimal(6, item.getSubtotal());
                        stmtItem.addBatch();
                    }
                    stmtItem.executeBatch();
                }

                conexao.commit();
                return idPedido;
            } catch (SQLException e) {
                conexao.rollback();
                throw e;
            } finally {
                conexao.setAutoCommit(autoCommitOriginal);
            }
        }
    }
}
