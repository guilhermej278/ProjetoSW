package br.com.pizzaria.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class CadastroServletTest {

    @Test
    void deveImpedirCadastroQuandoSenhaForMenorQueOitoCaracteres() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(request.getParameter("nome")).thenReturn("Cliente Teste");
        when(request.getParameter("email")).thenReturn("cliente@email.com");
        when(request.getParameter("senha")).thenReturn("1234567");
        when(request.getRequestDispatcher("/view/cadastro.jsp")).thenReturn(dispatcher);

        new CadastroServlet().doPost(request, response);

        verify(request).setCharacterEncoding("UTF-8");
        verify(request).setAttribute("erro", "Preencha todos os campos corretamente.");
        verify(dispatcher).forward(request, response);
        verify(response, never()).sendRedirect(anyString());
    }
}
