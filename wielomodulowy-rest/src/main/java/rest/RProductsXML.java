package rest;

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
import sklep.model.Price;
import sklep.model.Product;
import sklep.model.ProductList;

@Path("/products.xml")
@Produces("application/xml")
@Consumes("application/xml")
public class RProductsXML {
	
	@GET
	public ProductList readAll() throws DBException {
		try(DBConnection db = DBConnection.open()) {
			ProductDAO productDAO = db.productDAO();
			return new ProductList(productDAO.readAll());
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
	public void saveProduct(Product product) throws DBException {
		try(DBConnection db = DBConnection.open()) {
			ProductDAO productDAO = db.productDAO();
			productDAO.save(product);
			db.commit();
		}
	}
	
	@GET
	@Path("/{id}/price")
	public Price getPrice(@PathParam("id") int productId) throws DBException, RecordNotFound {
		try(DBConnection db = DBConnection.open()) {
			ProductDAO productDAO = db.productDAO();
			return new Price(productDAO.findById(productId).getPrice());
		}
	}
	
	@PUT
	@Path("/{id}/price")
	public void setPrice(@PathParam("id") int productId, Price newPrice) throws DBException, RecordNotFound {
		try(DBConnection db = DBConnection.open()) {
			ProductDAO productDAO = db.productDAO();
			Product product = productDAO.findById(productId);
			product.setPrice(newPrice.getValue());
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
