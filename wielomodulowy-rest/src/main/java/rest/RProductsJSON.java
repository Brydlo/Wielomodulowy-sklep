package rest;

import java.math.BigDecimal;
import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import sklep.db.DBConnection;
import sklep.db.DBException;
import sklep.db.ProductDAO;
import sklep.db.RecordNotFound;
import sklep.model.Product;

@Path("/products.json")
@Produces("application/json")
@Consumes("application/json")
// Adnotacje @Produces / @Consumes na poziomie klasy mówią co domyślnie produkują i konsumują metody.
// - metoda może nadpisać te ustawienia (np. metody dot. zdjęć)
// - adnotacje dotyczą tylko tych metod, które faktycznie coś pobierają lub zwracają
//   Np. metoda, która niczego nie konsumuje, nie zwróci uwagi na te adnotacje z poziomu klasy.
//     Metoda typu void nie zwraca uwagi na adnotację Produces
public class RProductsJSON {
	
	@GET
	public List<Product> readAll() throws DBException {
		try(DBConnection db = DBConnection.open()) {
			ProductDAO productDAO = db.productDAO();
			return productDAO.readAll();
		}
	}

	@GET
	@Path("/{id}")
	public Product readOne(@PathParam("id") int productId) throws DBException, RecordNotFound {
		try(DBConnection db = DBConnection.open()) {
			ProductDAO productDAO = db.productDAO();
			return productDAO.findById(productId);
		}
	}
	
	@POST
	// W metodach typu POST i PUT powinien znajdować się dokładnie jeden parametr nieozanczony żadną adnotacją.
	// Do tego parametru zostanie przekazana wartość utworzona na podstawie treści zapytania (content / body / entity).
	// W adnotacji @Consumes określamy format, w jakim te dane mają być przysłane.
	public InformacjaZwrotna saveProduct(Product product) throws DBException {
		try(DBConnection db = DBConnection.open()) {
			ProductDAO productDAO = db.productDAO();
			productDAO.save(product);
			db.commit();
			return new InformacjaZwrotna(product.getProductId());
		}
	}
	
	// klasa definiująca, co jest zwracane w wyniku POST po zapisaniu produktu
	public static class InformacjaZwrotna {
		private int noweId;

		public InformacjaZwrotna() {
		}

		public InformacjaZwrotna(int noweId) {
			this.noweId = noweId;
		}
		
		public int getNoweId() {
			return noweId;
		}
		
		public void setNoweId(int noweId) {
			this.noweId = noweId;
		}
	}
	
	
	// Ta metoda zwraca wartość wybranego pola w rekordzie.
	// W praktyce rzadko kiedy twozy się takie metody, ale gdybyśmy wiedzieli, że klient akurat takiej rzeczy może potrzebować,
	// to można taką dodatkową meotdę stworzyć.
	// Właściwą strukturą adresu będzie wtedy np. products/3/price
	@GET
	@Path("/{id}/price")
	public BigDecimal getPrice(@PathParam("id") int productId) throws DBException, RecordNotFound {
		try(DBConnection db = DBConnection.open()) {
			ProductDAO productDAO = db.productDAO();
			return productDAO.findById(productId).getPrice();
		}
	}
	
	// Metoda PUT służy w HTTP do zapisywania danych DOKŁADNIE POD PODANYM ADRESEM
	@PUT
	@Path("/{id}/price")
	public void setPrice(@PathParam("id") int productId, BigDecimal newPrice) throws DBException, RecordNotFound {
		try(DBConnection db = DBConnection.open()) {
			ProductDAO productDAO = db.productDAO();
			Product product = productDAO.findById(productId);
			product.setPrice(newPrice);
			productDAO.update(product);
			db.commit();
		}
	}
	
	@DELETE
	@Path("/{id}")
	public void delete(@PathParam("id") int productId) throws DBException {
		try(DBConnection db = DBConnection.open()) {
			ProductDAO productDAO = db.productDAO();
			productDAO.delete(productId);
			db.commit();
		}
	}
	
	@GET
	@Path("/{id}/photo")
	@Produces("image/jpeg")
	public byte[] getPhoto(@PathParam("id") int productId) throws DBException, RecordNotFound {
		return PhotoUtil.readBytes(productId);
	}
}
