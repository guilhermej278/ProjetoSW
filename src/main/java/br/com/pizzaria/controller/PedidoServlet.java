package br.com.pizzaria.controller;

import br.com.pizzaria.dao.PedidoDAO;
import br.com.pizzaria.dao.ProdutoDAO;
import br.com.pizzaria.model.ItemCarrinho;
import br.com.pizzaria.model.Pedido;
import br.com.pizzaria.model.Produto;
import br.com.pizzaria.model.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/pedido")
public class PedidoServlet extends HttpServlet {

    private final ProdutoDAO produtoDAO = new ProdutoDAO();
    private final PedidoDAO pedidoDAO = new PedidoDAO();

    @SuppressWarnings("unchecked")
    private List<ItemCarrinho> obterCarrinho(HttpSession sessao) {
        List<ItemCarrinho> carrinho = (List<ItemCarrinho>) sessao.getAttribute("carrinho");
        return carrinho == null ? new ArrayList<>() : carrinho;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession sessao = request.getSession();
        List<ItemCarrinho> carrinho = obterCarrinho(sessao);
        if (carrinho.isEmpty() && sessao.getAttribute("pedidoConfirmado") == null) {
            sessao.setAttribute("mensagemCarrinho", "Adicione produtos antes de finalizar o pedido.");
            response.sendRedirect(request.getContextPath() + "/carrinho");
            return;
        }

        request.setAttribute("usuario", sessao.getAttribute("usuarioLogado"));
        request.setAttribute("pedidoConfirmado", sessao.getAttribute("pedidoConfirmado"));
        sessao.removeAttribute("pedidoConfirmado");
        request.getRequestDispatcher("/view/pedido.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        HttpSession sessao = request.getSession();
        Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
        List<ItemCarrinho> carrinhoSessao = obterCarrinho(sessao);

        if (usuario == null) {
            response.sendRedirect(request.getContextPath() + "/login?redirect=pedido");
            return;
        }
        if (carrinhoSessao.isEmpty()) {
            sessao.setAttribute("mensagemCarrinho", "O carrinho está vazio.");
            response.sendRedirect(request.getContextPath() + "/carrinho");
            return;
        }

        String tipo = request.getParameter("tipo");
        String endereco = request.getParameter("endereco");
        if (!"ENTREGA".equals(tipo) && !"RETIRADA".equals(tipo)) {
            request.setAttribute("erro", "Selecione entrega ou retirada na loja.");
            request.getRequestDispatcher("/view/pedido.jsp").forward(request, response);
            return;
        }
        if ("ENTREGA".equals(tipo) && (endereco == null || endereco.trim().isEmpty())) {
            request.setAttribute("erro", "Informe o endereço para entrega.");
            request.getRequestDispatcher("/view/pedido.jsp").forward(request, response);
            return;
        }

        try {
            List<ItemCarrinho> itensAtualizados = new ArrayList<>();
            BigDecimal total = BigDecimal.ZERO;

            for (ItemCarrinho item : carrinhoSessao) {
                Produto produtoAtual = produtoDAO.consultarProduto(item.getProduto().getId());
                if (produtoAtual == null) {
                    throw new SQLException("Um dos produtos do carrinho não está mais disponível.");
                }
                ItemCarrinho atualizado = new ItemCarrinho(produtoAtual, item.getQuantidade());
                itensAtualizados.add(atualizado);
                total = total.add(atualizado.getSubtotal());
            }

            String enderecoPedido = "ENTREGA".equals(tipo) ? endereco.trim() : null;
            Pedido pedido = new Pedido(usuario, enderecoPedido, tipo, total);
            int idPedido = pedidoDAO.salvarPedido(pedido, itensAtualizados);
            pedido.setId(idPedido);

            sessao.removeAttribute("carrinho");
            sessao.setAttribute("pedidoConfirmado", pedido);
            response.sendRedirect(request.getContextPath() + "/pedido?confirmado=1");
        } catch (SQLException e) {
            getServletContext().log("Erro ao salvar pedido", e);
            request.setAttribute("erro", "Não foi possível salvar o pedido. Confira o banco de dados e tente novamente.");
            request.setAttribute("usuario", usuario);
            request.getRequestDispatcher("/view/pedido.jsp").forward(request, response);
        }
    }
}
