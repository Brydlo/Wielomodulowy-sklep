<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edycja zdjęcia</title>
<link rel="stylesheet" type="text/css" href="styl.css">
</head>
<body>
<h1>Wgraj zdjęcie produktu</h1>

<div>Produkt nr <strong>${param.productId}</strong></div>
<div>Aktualne zdjęcie:<br/>
<img class="photo" src="photo?productId=${param.productId}" alt="Brak zdjęcia">
</div>
<%-- action powoduje, że zapytanie z formularza jest wysyłane pod podany adres, a nie bieżący.
Aby wysłać zawartość pliku (a nie tylko jego nazwę), należy ustawić enctype jak poniżej.
Sam plik to pole formularza typu file; oprócz niego mogą być inne „zwykłe” pola.
Odpowiednio trzeba to też obsłużyć w serwlecie - patrz klasa DoUploadPhoto.
--%>
<form id="photo-form" method="post" action="photo_upload" enctype="multipart/form-data">
	<input type="hidden" name="productId" value="${param.productId}">
	<label for="plik">Wybierz plik ze zdjęciem</label>
	<input id="plik" type="file" name="plik" accept="image/jpeg">
	<br>
	<button>Wyślij</button>
</form>

<p>[<a href="products9.jsp">powrót do listy produktów</a>]</p>
<p>[<a href="index.html">powrót do spisu treści</a>]</p>

</body>
</html>
