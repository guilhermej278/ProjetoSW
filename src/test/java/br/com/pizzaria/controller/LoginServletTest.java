package br.com.pizzaria.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class LoginServletTest {

    @Test
    void deveExibirErroQuandoEmailOuSenhaNaoForemInformados() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(request.getParameter("email")).thenReturn(" ");
        when(request.getParameter("senha")).thenReturn("");
        when(request.getRequestDispatcher("/view/login.jsp")).thenReturn(dispatcher);

        new LoginServlet().doPost(request, response);

        verify(request).setCharacterEncoding("UTF-8");
        verify(request).setAttribute("erro", "Informe o e-mail e a senha.");
        verify(dispatcher).forward(request, response);
        verify(response, never()).sendRedirect(anyString());
    }
}
