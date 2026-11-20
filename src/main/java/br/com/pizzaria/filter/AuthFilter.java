package br.com.pizzaria.filter;

import br.com.pizzaria.model.Usuario;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/pedido")
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession sessao = req.getSession(false);
        Usuario usuario = sessao == null ? null : (Usuario) sessao.getAttribute("usuarioLogado");

        if (usuario == null) {
            resp.sendRedirect(req.getContextPath() + "/login?redirect=pedido");
            return;
        }

        chain.doFilter(request, response);
    }
}
