package sklep.web;

import java.io.IOException;
import java.math.BigDecimal;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sklep.db.DBConnection;
import sklep.db.DBException;
import sklep.db.ProductDAO;
import sklep.db.RecordNotFound;
import sklep.model.Product;

@WebServlet("/edit")
public class EditProduct extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String parametrId = request.getParameter("productId");
        if(parametrId != null) {
            int productId = Integer.parseInt(parametrId);
            try(DBConnection db = DBConnection.open()) {
                ProductDAO productDAO = db.productDAO();
                Product product = productDAO.findById(productId);
                // Gdy do obiektu request dodamy atrybut, to stanie się on dostępny dla kolejnych komponentów
                // naszej aplikacji, które będą obsługiwać to zapytanie.
                // W tym przypadku skrypt JSP może odwoływać się do obiektu product.
                // Obiekt request jest też nośnikiem danych, podobnie jak sesja i servletContext.
                // To działa jak Model w Spring MVC.

                // Tylko jeśli znajdę produkt, tylko wtedy dodaję go do requestu i JSP wyświetli jego dane.
                // Jeśli parametru productId nie było lub produktu nie znaleziono, to wyświetli się pusty formularz.
                request.setAttribute("product", product);
            } catch(DBException | RecordNotFound e) {
                e.printStackTrace();
            }
        }
        // Forward to "wewnętrzne przekierowanie" obsługi zapytania do innego komponentu aplikacji.
        // Tutaj "wyświetlamy" formularz edycji produktu.
        RequestDispatcher dispatcher = request.getRequestDispatcher("product_form.jsp");
        if(dispatcher != null)
            dispatcher.forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // W tej wersji nie obsługujemy błędów - w razie błędu wyświetli się strona z wyjątkiem
        // W przypadku braku ID zostanie utworzony nowy produkt, a w przypadku podania ID (gdy to była edycja istniejącego) - zostanie zastąpiony.

        request.setCharacterEncoding("UTF-8");

        String parametrId = request.getParameter("productId");
        Integer productId = (parametrId == null || parametrId.isEmpty()) ? null : Integer.valueOf(parametrId);
        String parametrPrice = request.getParameter("price");
        BigDecimal price = new BigDecimal(parametrPrice);
        String parametrVat = request.getParameter("vat");
        BigDecimal vat = (parametrVat == null || parametrVat.isEmpty()) ? null : new BigDecimal(parametrVat);
        String name = request.getParameter("productName");
        String description = request.getParameter("description");

        Product product = new Product(productId, name, price, vat, description);

        try(DBConnection db = DBConnection.open()) {
            ProductDAO productDAO = db.productDAO();
            productDAO.save(product);
            db.commit();
            
            // Gdy udało się zapisać, to przejdziemy z powrotem do listy.
            // To jest przekierowanie przeglądarki do inny adres.
            response.sendRedirect("products9.jsp");
        } catch (DBException e) {
            e.printStackTrace();
        }
    }
}
