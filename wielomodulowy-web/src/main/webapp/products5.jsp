<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Lista produktów 5</title>
<link rel="stylesheet" type="text/css" href="styl.css">
</head>
<body>
<h1>Lista produktów - wersja 5</h1>

<jsp:useBean id="productBean" class="sklep.web.ProductBean"/>
<c:forEach var="product" items="${productBean.allProducts}">
    <div class="product">
        <h3>${product.productName}</h3>
        <div class="price">Cena: ${product.price}</div>
        <div class="price">VAT ${product.vat * 100}%</div>
        <c:if test="${not empty(product.description)}">
            <p class="description">${product.description}</p>
        </c:if>
    </div>
</c:forEach>

</body>
</html>
