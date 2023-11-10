package rest;

import java.time.LocalDateTime;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/dt")
public class RTime {
	// Obiekt tej klasy jest tworzony za każdym razem do obsługi każdego pojedynczego zapytania.	
	private LocalDateTime dt = LocalDateTime.now();
	
	{ System.out.println("Jest tworzony obiekt RTime , dt = " + dt); }
	
	// działa pod adresem: /dt
	@GET
	public String getDateTime() {
		return dt.toString();
	}
	
	// działa pod adresem: /dt/date
	@GET
	@Path("/date")
	public String getDate() {
		return dt.toLocalDate().toString();
	}
	
	@GET
	@Path("/time")
	public String getTime() {
		return dt.toLocalTime().toString();
	}
	
	@GET
	@Path("/time/second")
	public int getSecond() {
		return dt.getSecond();
	}

}
