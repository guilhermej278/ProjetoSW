<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Carrinho - Pizzaria 10</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<header><nav>
    <a class="logo" href="${pageContext.request.contextPath}/">Pizzaria 10</a>
    <a href="${pageContext.request.contextPath}/">Início</a>
        <a class="ativo" href="${pageContext.request.contextPath}/carrinho">Carrinho</a>
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
    <h1>Carrinho</h1>
    <c:if test="${not empty sessionScope.mensagemCarrinho}">
        <p class="mensagem"><c:out value="${sessionScope.mensagemCarrinho}"/></p>
        <c:remove var="mensagemCarrinho" scope="session"/>
    </c:if>
    <c:if test="${not empty erro}"><p class="alerta" role="alert"><c:out value="${erro}"/></p></c:if>
    <section class="cartao">
        <c:choose>
            <c:when test="${empty itens}">
                <p>O carrinho está vazio.</p>
                <a class="botao" href="${pageContext.request.contextPath}/produtos">Ver cardápio</a>
            </c:when>
            <c:otherwise>
                <div class="tabela-scroll"><table>
                    <thead><tr><th>Produto</th><th>Qtd</th><th>Subtotal</th><th></th></tr></thead>
                    <tbody>
                    <c:forEach var="item" items="${itens}">
                        <tr>
                            <td><c:out value="${item.produto.nome}"/></td>
                            <td><c:out value="${item.quantidade}"/></td>
                            <td><fmt:formatNumber value="${item.subtotal}" type="currency" currencyCode="BRL"/></td>
                            <td><form action="${pageContext.request.contextPath}/carrinho" method="post">
                                <input type="hidden" name="acao" value="remover">
                                <input type="hidden" name="id" value="${item.produto.id}">
                                <button class="link excluir" type="submit">Remover</button>
                            </form></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table></div>
                <div class="resumo"><strong>Total: <fmt:formatNumber value="${total}" type="currency" currencyCode="BRL"/></strong></div>
                <div class="acoes">
                    <a class="botao" href="${pageContext.request.contextPath}/pedido">Finalizar pedido</a>
                </div>
            </c:otherwise>
        </c:choose>
    </section>
</main>
</body>
</html>
