<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Pedido - Pizzaria 10</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<header><nav>
    <a class="logo" href="${pageContext.request.contextPath}/">Pizzaria 10</a>
    <a href="${pageContext.request.contextPath}/">Início</a>
        <a href="${pageContext.request.contextPath}/carrinho">Carrinho</a>
    <a class="ativo" href="${pageContext.request.contextPath}/pedido">Pedido</a>
    <span class="usuario-nav">Olá, <c:out value="${sessionScope.usuarioLogado.nome}"/></span>
    <a href="${pageContext.request.contextPath}/logout">Sair</a>
</nav></header>
<main>
    <h1>Finalizar pedido</h1>
    <section class="cartao">
        <c:choose>
            <c:when test="${not empty pedidoConfirmado}">
                <div class="sucesso">
                    <h2>Pedido recebido!</h2>
                    <p>Seu pedido <strong>#<c:out value="${pedidoConfirmado.id}"/></strong> foi registrado.</p>
                    <p>Obrigado, <c:out value="${pedidoConfirmado.usuario.nome}"/>.</p>
                    <p>Total: <strong><fmt:formatNumber value="${pedidoConfirmado.total}" type="currency" currencyCode="BRL"/></strong></p>
                    <c:if test="${pedidoConfirmado.tipoPedido != 'ENTREGA'}"><p>Seu pedido pode ser retirado na loja.</p></c:if>
                    <a class="botao" href="${pageContext.request.contextPath}/">Voltar ao início</a>
                </div>
            </c:when>
            <c:otherwise>
                <p>Pedido para: <strong><c:out value="${sessionScope.usuarioLogado.nome}"/></strong></p>
                <c:if test="${not empty erro}"><p class="alerta"><c:out value="${erro}"/></p></c:if>
                <form action="${pageContext.request.contextPath}/pedido" method="post" class="formulario">
                    <label for="tipo">Tipo de pedido</label>
                    <select id="tipo" name="tipo" onchange="mostrarEndereco()" required>
                        <option value="ENTREGA">Entrega</option>
                        <option value="RETIRADA">Retirada na loja</option>
                    </select>
                    <div id="campoEndereco">
                        <label for="endereco">Endereço para entrega</label>
                        <input type="text" id="endereco" name="endereco" maxlength="255" placeholder="Rua, número, bairro e complemento">
                    </div>
                    <button class="botao" type="submit">Confirmar pedido</button>
                </form>
            </c:otherwise>
        </c:choose>
    </section>
</main>
<script>
    function mostrarEndereco() {
        var entrega = document.getElementById('tipo').value === 'ENTREGA';
        document.getElementById('campoEndereco').style.display = entrega ? 'block' : 'none';
        document.getElementById('endereco').required = entrega;
    }
    if (document.getElementById('tipo')) mostrarEndereco();
</script>
</body>
</html>
