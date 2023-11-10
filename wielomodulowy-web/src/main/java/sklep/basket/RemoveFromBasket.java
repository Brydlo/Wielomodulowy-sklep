package sklep.basket;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/remove_from_basket")
public class RemoveFromBasket extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int productId = Integer.parseInt(request.getParameter("productId"));
			HttpSession sesja = request.getSession();
			Basket basket = (Basket) sesja.getAttribute("basket");
			basket.removeProduct(productId);
		} catch (Exception e) {
			// ignorujemy błędy
		}
		// Przekierowanie - każemy przeglądarce wejść pod ten adres.
		response.sendRedirect("products9.jsp");
	}

}
