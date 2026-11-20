<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Cardápio - Pizzaria 10</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<header><nav>
    <a class="logo" href="${pageContext.request.contextPath}/">Pizzaria 10</a>
    <a href="${pageContext.request.contextPath}/">Início</a>
        <a href="${pageContext.request.contextPath}/carrinho">Carrinho</a>
    <a href="${pageContext.request.contextPath}/view/perguntas.jsp">Perguntas frequentes</a>
    <c:choose><c:when test="${not empty sessionScope.usuarioLogado}">
        <span class="usuario-nav">Olá, <c:out value="${sessionScope.usuarioLogado.nome}"/></span>
        <a href="${pageContext.request.contextPath}/logout">Sair</a>
    </c:when><c:otherwise>
        <a href="${pageContext.request.contextPath}/login">Entrar</a>
        <a href="${pageContext.request.contextPath}/cadastro">Cadastrar</a>
    </c:otherwise></c:choose>
</nav></header>
<main>
    <h1>Cardápio</h1>
    <div class="filtros">
        <a class="link ${categoriaSelecionada == 'Pizzas' ? 'ativo' : ''}" href="${pageContext.request.contextPath}/produtos?categoria=Pizzas">Pizzas</a>
        <a class="link ${categoriaSelecionada == 'Bebidas' ? 'ativo' : ''}" href="${pageContext.request.contextPath}/produtos?categoria=Bebidas">Bebidas</a>
        <a class="link ${categoriaSelecionada == 'Sobremesas' ? 'ativo' : ''}" href="${pageContext.request.contextPath}/produtos?categoria=Sobremesas">Sobremesas</a>
    </div>
    <c:if test="${not empty erro}"><p class="alerta"><c:out value="${erro}"/></p></c:if>
    <c:choose>
        <c:when test="${empty produtos}"><p class="cartao">Nenhum produto encontrado.</p></c:when>
        <c:otherwise>
            <div class="lista">
                <c:forEach var="produto" items="${produtos}">
                    <article class="produto">
                        <span class="categoria"><c:out value="${produto.categoria}"/></span>
                        <h2><c:out value="${produto.nome}"/></h2>
                        <p><c:out value="${produto.descricao}"/></p>
                        <p class="preco"><fmt:formatNumber value="${produto.preco}" type="currency" currencyCode="BRL"/></p>
                        <form action="${pageContext.request.contextPath}/carrinho" method="post">
                            <input type="hidden" name="acao" value="adicionar">
                            <input type="hidden" name="id" value="${produto.id}">
                            <button class="botao" type="submit">Adicionar ao carrinho</button>
                        </form>
                    </article>
                </c:forEach>
            </div>
        </c:otherwise>
    </c:choose>
</main>
</body>
</html>
