package br.com.pizzaria.controller;

import br.com.pizzaria.dao.ProdutoDAO;
import br.com.pizzaria.model.ItemCarrinho;
import br.com.pizzaria.model.Produto;

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
import java.util.Iterator;
import java.util.List;

@WebServlet("/carrinho")
public class CarrinhoServlet extends HttpServlet {

    private final ProdutoDAO produtoDAO = new ProdutoDAO();

    @SuppressWarnings("unchecked")
    private List<ItemCarrinho> obterCarrinho(HttpSession sessao) {
        List<ItemCarrinho> carrinho = (List<ItemCarrinho>) sessao.getAttribute("carrinho");
        if (carrinho == null) {
            carrinho = new ArrayList<>();
            sessao.setAttribute("carrinho", carrinho);
        }
        return carrinho;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<ItemCarrinho> carrinho = obterCarrinho(request.getSession());
        BigDecimal total = BigDecimal.ZERO;

        try {
            Iterator<ItemCarrinho> iterator = carrinho.iterator();
            while (iterator.hasNext()) {
                ItemCarrinho item = iterator.next();
                Produto atualizado = produtoDAO.consultarProduto(item.getProduto().getId());
                if (atualizado == null) {
                    iterator.remove();
                } else {
                    item.setProduto(atualizado);
                    total = total.add(item.getSubtotal());
                }
            }
        } catch (SQLException e) {
            getServletContext().log("Erro ao consultar produtos do carrinho", e);
            request.setAttribute("erro", "Não foi possível consultar os produtos no banco de dados.");
        }

        request.setAttribute("itens", carrinho);
        request.setAttribute("total", total);
        request.getRequestDispatcher("/view/carrinho.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String acao = request.getParameter("acao");
        List<ItemCarrinho> carrinho = obterCarrinho(request.getSession());

        try {
            if ("adicionar".equals(acao)) {
                int id = Integer.parseInt(request.getParameter("id"));
                Produto produto = produtoDAO.consultarProduto(id);
                if (produto == null) {
                    request.getSession().setAttribute("mensagemCarrinho", "Produto não encontrado.");
                } else {
                    ItemCarrinho existente = null;
                    for (ItemCarrinho item : carrinho) {
                        if (item.getProduto().getId() == id) {
                            existente = item;
                            break;
                        }
                    }
                    if (existente == null) {
                        carrinho.add(new ItemCarrinho(produto, 1));
                    } else {
                        existente.setQuantidade(existente.getQuantidade() + 1);
                        existente.setProduto(produto);
                    }
                    request.getSession().setAttribute("mensagemCarrinho", "Item adicionado ao carrinho.");
                }
            } else if ("remover".equals(acao)) {
                int id = Integer.parseInt(request.getParameter("id"));
                carrinho.removeIf(item -> item.getProduto().getId() == id);
            }
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("mensagemCarrinho", "Produto inválido.");
        } catch (SQLException e) {
            getServletContext().log("Erro ao atualizar o carrinho", e);
            request.getSession().setAttribute("mensagemCarrinho", "Não foi possível atualizar o carrinho.");
        }

        response.sendRedirect(request.getContextPath() + "/carrinho");
    }
}
