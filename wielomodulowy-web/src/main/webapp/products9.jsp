<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Lista produktów 9</title>
<link rel="stylesheet" type="text/css" href="styl.css">
</head>
<body>
<h1>Lista produktów - wersja 9</h1>

<div class="koszyk">
<h4>Koszyk</h4>
<ul>
<%-- Zauważmy, że dla obiektu koszyk nie wykonujemy już useBean.
     Po prostu zakładamy, że jest obecny (w sesji). Gdyby go nie było, to pętla się nie wykona. --%>
<c:forEach var="elm" items="${basket.elements}">
    <li>${elm.productName} (${elm.quantity}) za <b>${elm.value}</b> <a href="remove_from_basket?productId=${elm.productId}">(–)</a></li>
</c:forEach>
</ul>
<p class="total">Do zapłaty: ${basket.totalValue}</p>
</div>

<form id="wyszukiwarka" method="get">
<h2>Filtr cen</h2>
<table class="formularz">
<tr><td><label for="min_price">Cena minimalna:</label></td>
    <td><input id="min_price" name="min_price" type="number" value="${param.min_price}"></td></tr>
<tr><td><label for="max_price">Cena maksymalna:</label></td>
    <td><input id="max_price" name="max_price" type="number" value="${param.max_price}"></td></tr>
<tr><td><button>Filtruj</button></td></tr>
</table>
</form>

<jsp:useBean id="productBean" class="sklep.web.ProductBean"/>
<jsp:setProperty name="productBean" property="minPrice" param="min_price"/>
<jsp:setProperty name="productBean" property="maxPrice" param="max_price"/>

<c:forEach var="product" items="${productBean.filteredProducts}">
    <div class="product">
   		<img class="photo" src="photo?productId=${product.productId}" alt=""/>
        <h3>${product.productName}</h3>
        <div class="price">Cena: ${product.price}</div>
        <div class="price">VAT ${product.vat * 100}%</div>
        <c:if test="${not empty(product.description)}">
            <p class="description">${product.description}</p>
        </c:if>
        <div class="action"><a href="add_to_basket?productId=${product.productId}">dodaj do koszyka</a></div>
        <div class="action"><a href="edit?productId=${product.productId}">edytuj</a></div>
        <div class="action"><a href="photo_upload.jsp?productId=${product.productId}">zmień zdjęcie</a></div>
    </div>
</c:forEach>
<div class="action"><a href="edit">Dodaj nowy produkt</a></div>
</body>
</html>
