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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/view/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        if (email == null || email.trim().isEmpty() || senha == null || senha.isEmpty()) {
            request.setAttribute("erro", "Informe o e-mail e a senha.");
            request.getRequestDispatcher("/view/login.jsp").forward(request, response);
            return;
        }

        try {
            Usuario usuario = usuarioDAO.autenticar(email, senha);
            if (usuario == null) {
                request.setAttribute("erro", "E-mail ou senha inválidos.");
                request.getRequestDispatcher("/view/login.jsp").forward(request, response);
                return;
            }

            request.changeSessionId();
            request.getSession().setAttribute("usuarioLogado", usuario);
            String destino = "pedido".equals(request.getParameter("redirect"))
                    ? "/pedido" : "/produtos";
            response.sendRedirect(request.getContextPath() + destino);
        } catch (SQLException e) {
            getServletContext().log("Erro ao autenticar usuário", e);
            request.setAttribute("erro", "Não foi possível realizar o login.");
            request.getRequestDispatcher("/view/login.jsp").forward(request, response);
        }
    }
}
