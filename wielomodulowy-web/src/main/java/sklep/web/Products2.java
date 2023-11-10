package sklep.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sklep.db.DBConnection;
import sklep.db.DBException;
import sklep.db.ProductDAO;
import sklep.model.Product;

@WebServlet("/products2")
public class Products2 extends HttpServlet {
	   
    @Override
    protected void doGet(HttpServletRequest requets, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.println("""
	        <!DOCTYPE html>
	        <html>
	        <head>
	        <title>Lista produktów</title>
	        <link rel='stylesheet' type='text/css' href='styl.css'>
	        </head>
	        <body>
	        <h1>Produkty</h1>
        """);
        
        try (DBConnection db = DBConnection.open()) {
			ProductDAO productDAO = db.productDAO();
			List<Product> products = productDAO.readAll();
			for (Product product : products) {
				out.println(product.toHtml());
			}
		} catch (DBException e) {
			out.println("Wielka bieda!");
			out.print("<pre>");
			e.printStackTrace(out);
			out.print("</pre>");
		}
        
        out.println("</body></html>");
        
    }
}
