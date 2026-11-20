package br.com.pizzaria.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class CarrinhoServletTest {

    @Test
    void deveInformarQuandoIdDoProdutoForInvalido() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        List<Object> carrinho = new ArrayList<>();

        when(request.getParameter("acao")).thenReturn("adicionar");
        when(request.getParameter("id")).thenReturn("abc");
        when(request.getSession()).thenReturn(session);
        when(request.getContextPath()).thenReturn("/pizzaria");
        when(session.getAttribute("carrinho")).thenReturn(carrinho);

        new CarrinhoServlet().doPost(request, response);

        verify(session).setAttribute("mensagemCarrinho", "Produto inválido.");
        verify(response).sendRedirect("/pizzaria/carrinho");
        assertTrue(carrinho.isEmpty());
    }
}
