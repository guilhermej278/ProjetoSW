<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Perguntas frequentes - Pizzaria 10</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<header><nav>
    <a class="logo" href="${pageContext.request.contextPath}/">Pizzaria 10</a>
    <a href="${pageContext.request.contextPath}/">Início</a>
        <a href="${pageContext.request.contextPath}/carrinho">Carrinho</a>
    <a class="ativo" href="${pageContext.request.contextPath}/view/perguntas.jsp">Perguntas frequentes</a>
    <c:choose>
        <c:when test="${not empty sessionScope.usuarioLogado}">
            <span class="usuario-nav">Olá, <c:out value="${sessionScope.usuarioLogado.nome}"/></span>
            <a href="${pageContext.request.contextPath}/logout">Sair</a>
        </c:when>
        <c:otherwise>
            <a href="${pageContext.request.contextPath}/login">Entrar</a>
            <a href="${pageContext.request.contextPath}/cadastro">Cadastrar</a>
        </c:otherwise>
    </c:choose>
</nav></header>
<main>
    <h1>Perguntas frequentes</h1>
    <section class="cartao"><h2>Como faço um pedido?</h2><p>Escolha os produtos no cardápio, confira o carrinho, entre na sua conta e preencha os dados na página de pedido.</p></section>
    <section class="cartao"><h2>Quais são as formas de pagamento?</h2><p>Pix, cartão na entrega e dinheiro.</p></section>
    <section class="cartao"><h2>Como retiro um item do carrinho?</h2><p>No carrinho, clique em "Remover" ao lado do item.</p></section>
</main>
</body>
</html>
