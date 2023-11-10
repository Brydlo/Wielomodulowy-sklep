package rest;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/hello")
public class RHello {

	@GET
	public String hello() {
		return "Hello <b>REST</b>";
	}
	
}
