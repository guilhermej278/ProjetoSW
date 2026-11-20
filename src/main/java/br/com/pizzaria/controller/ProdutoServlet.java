package br.com.pizzaria.controller;

import br.com.pizzaria.dao.ProdutoDAO;
import br.com.pizzaria.model.Produto;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/produtos")
public class ProdutoServlet extends HttpServlet {

    private final ProdutoDAO produtoDAO = new ProdutoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String categoria = request.getParameter("categoria");
        try {
            List<Produto> produtos = produtoDAO.listarProdutos(categoria);
            request.setAttribute("produtos", produtos);
            request.setAttribute("categoriaSelecionada", categoria == null ? "" : categoria);
            request.getRequestDispatcher("/view/cardapio.jsp").forward(request, response);
        } catch (SQLException e) {
            getServletContext().log("Erro ao listar produtos", e);
            request.setAttribute("erro", "Não foi possível carregar o cardápio. Verifique a conexão com o banco de dados.");
            request.getRequestDispatcher("/view/cardapio.jsp").forward(request, response);
        }
    }
}
