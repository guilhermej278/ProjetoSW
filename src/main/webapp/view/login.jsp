<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login - Pizzaria 10</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<header><nav>
    <a class="logo" href="${pageContext.request.contextPath}/">Pizzaria 10</a>
    <a href="${pageContext.request.contextPath}/">Início</a>
        <a href="${pageContext.request.contextPath}/carrinho">Carrinho</a>
    <a href="${pageContext.request.contextPath}/view/perguntas.jsp">Perguntas frequentes</a>
    <c:choose>
        <c:when test="${not empty sessionScope.usuarioLogado}">
            <span class="usuario-nav">Olá, <c:out value="${sessionScope.usuarioLogado.nome}"/></span>
            <a href="${pageContext.request.contextPath}/logout">Sair</a>
        </c:when>
        <c:otherwise>
            <a class="ativo" href="${pageContext.request.contextPath}/login">Entrar</a>
            <a href="${pageContext.request.contextPath}/cadastro">Cadastrar</a>
        </c:otherwise>
    </c:choose>
</nav></header>
<main class="pagina-formulario">
    <section class="cartao">
        <h1>Entrar</h1>
        <c:if test="${not empty erro}"><p class="alerta" role="alert"><c:out value="${erro}"/></p></c:if>
        <form action="${pageContext.request.contextPath}/login" method="post" class="formulario">
            <input type="hidden" name="redirect" value="${param.redirect}" />
            <label for="email">E-mail</label>
            <input type="email" id="email" name="email" maxlength="150" required autocomplete="email">
            <label for="senha">Senha</label>
            <input type="password" id="senha" name="senha" required autocomplete="current-password">
            <button class="botao" type="submit">Entrar</button>
        </form>
        <p>Não possui cadastro? <a href="${pageContext.request.contextPath}/cadastro">Cadastre-se</a>.</p>
    </section>
</main>
</body>
</html>
