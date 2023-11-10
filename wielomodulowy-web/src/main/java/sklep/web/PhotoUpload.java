package sklep.web;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

// Włączona obsługa zapytań multipart ("z załącznikami"). Maks rozmiar zapytania/pliku: 16M
@WebServlet("/photo_upload")
@MultipartConfig(maxRequestSize = 16 * 1024 * 1024)
public class PhotoUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String paramId = request.getParameter("productId");
			if(paramId != null) {
				int productId = Integer.parseInt(paramId);
				Part part = request.getPart("plik");
				if(part != null) {
					// przysłano plik
					// Tutaj nazwa pliku jest dla nas bez znaczenia, ale gdybyśmy potrzebowali, to w ten sposób:
					// String nazwaPliku = part.getSubmittedFileName();
					// Przypisujemy bajty ze strumienia do pliku w katalogu ze zdjęciami:
					PhotoUtil.writeStream(productId, part.getInputStream());
				}
			}
		} catch (Exception e) {
			// wypisujemy błąd, ale metoda kończy się normalnie
			e.printStackTrace();
		}
		response.sendRedirect("products9.jsp");
	}

}
