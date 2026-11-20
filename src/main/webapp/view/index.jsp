<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Início - Pizzaria 10</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<header>
    <nav>
        <a class="logo" href="${pageContext.request.contextPath}/">Pizzaria 10</a>
        <a class="ativo" href="${pageContext.request.contextPath}/">Início</a>
        <a href="${pageContext.request.contextPath}/produtos">Cardápio</a>
        <a href="${pageContext.request.contextPath}/carrinho">Carrinho</a>
        <a href="${pageContext.request.contextPath}/view/perguntas.jsp">Perguntas frequentes</a>
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
    </nav>
</header>
<main>
    <section class="cartao destaque">
        <div>
            <p class="sobretitulo">Pizzaria 10</p>
            <h1>Pizza feita na hora, entregue quente</h1>
            <p>Massa fina, ingredientes frescos e entrega rápida. Escolha o que quer no cardápio, adicione ao carrinho e finalize o pedido em poucos passos.</p>
            <a class="botao-cardapio" href="${pageContext.request.contextPath}/produtos">Ver cardápio</a>
        </div>
        <img src="${pageContext.request.contextPath}/img/pizza.jpg" alt="Pizza de tomate e manjericão saindo do forno">
    </section>
</main>
</body>
</html>
