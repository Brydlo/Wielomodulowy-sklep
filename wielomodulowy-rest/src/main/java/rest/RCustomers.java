package rest;

import java.net.URI;
import java.util.List;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import sklep.db.CustomerDAO;
import sklep.db.DBConnection;
import sklep.db.DBException;
import sklep.db.RecordNotFound;
import sklep.model.Customer;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
public class RCustomers {

	@GET
	@Path("/{id}")
	public Customer findById(@PathParam("id") final String email) throws DBException, RecordNotFound {
		try(DBConnection db = DBConnection.open()) {
			CustomerDAO customerDAO = db.customerDAO();
			return customerDAO.findByEmail(email);
		}
	}

	@GET
	public List<Customer> listAll() throws DBException {
		try(DBConnection db = DBConnection.open()) {
			CustomerDAO customerDAO = db.customerDAO();
			return customerDAO.readAll();
		}
	}

	@POST
	public Response save(final Customer customer) throws DBException {
		try(DBConnection db = DBConnection.open()) {
			CustomerDAO customerDAO = db.customerDAO();
			customerDAO.save(customer);
			db.commit();
			URI uri = UriBuilder
					.fromResource(RCustomers.class)
					.path(String.valueOf(customer.getEmail()))
					.build();
			return Response.created(uri).build();
		}
	}
	
	@PUT
	@Path("/{id}")
	public Response update(@PathParam("id") String email, final Customer customer) throws DBException {
		try(DBConnection db = DBConnection.open()) {
			CustomerDAO customerDAO = db.customerDAO();
			customer.setEmail(email);
			customerDAO.save(customer);
			db.commit();
		}
		return Response.noContent().build();
	}

	@DELETE
	@Path("/{id}")
	public Response deleteById(@PathParam("id") String email) throws DBException {
		try(DBConnection db = DBConnection.open()) {
			CustomerDAO customerDAO = db.customerDAO();
			customerDAO.delete(email);
			db.commit();
		}
		return Response.noContent().build();
	}

}
