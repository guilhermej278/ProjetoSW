package br.com.pizzaria.controller;

import br.com.pizzaria.dao.UsuarioDAO;
import br.com.pizzaria.model.Usuario;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/cadastro")
public class CadastroServlet extends HttpServlet {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/view/cadastro.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String nome = valor(request.getParameter("nome"));
        String email = valor(request.getParameter("email")).toLowerCase();
        String senha = request.getParameter("senha");

        if (nome.isEmpty() || email.isEmpty() || senha == null || senha.length() < 8) {
            request.setAttribute("erro", "Preencha todos os campos corretamente.");
            request.getRequestDispatcher("/view/cadastro.jsp").forward(request, response);
            return;
        }

        try {
            Usuario usuario = usuarioDAO.cadastrar(nome, email, senha);
            request.changeSessionId();
            request.getSession().setAttribute("usuarioLogado", usuario);
            response.sendRedirect(request.getContextPath() + "/produtos");
        } catch (SQLException e) {
            getServletContext().log("Erro ao cadastrar usuário", e);
            if ("23000".equals(e.getSQLState())) {
                request.setAttribute("erro", "Este e-mail já está cadastrado.");
            } else {
                request.setAttribute("erro", "Não foi possível realizar o cadastro.");
            }
            request.getRequestDispatcher("/view/cadastro.jsp").forward(request, response);
        }
    }

    private String valor(String texto) {
        return texto == null ? "" : texto.trim();
    }
}
