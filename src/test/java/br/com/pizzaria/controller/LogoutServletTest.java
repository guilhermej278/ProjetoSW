package br.com.pizzaria.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class LogoutServletTest {

    @Test
    void deveEncerrarSessaoERedirecionarParaPaginaInicial() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);

        when(request.getSession(false)).thenReturn(session);
        when(request.getContextPath()).thenReturn("/pizzaria");

        new LogoutServlet().doGet(request, response);

        verify(session).invalidate();
        verify(response).sendRedirect("/pizzaria/");
    }
}
