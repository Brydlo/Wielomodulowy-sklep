package rest_klient;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import sklep.model.Product;

public class Klient23_RestClient_JSON_JedenProdukt {

	public static void main(String[] args) {
		Client client = ClientBuilder.newClient();
		
		Response response = client.target(Ustawienia.ADRES_USLUGI)
			.path("products")
			.path("1")
			.request()
			.accept(MediaType.APPLICATION_JSON)
			.buildGet()
			.invoke();
		
		System.out.println("Mam odpowiedź: " + response);
		System.out.println("Status: " + response.getStatus());
		System.out.println("C-Type: " + response.getMediaType());
		System.out.println("Length: " + response.getLength());
		
		Product product = response.readEntity(Product.class);
		System.out.println("Odczytany produkt: " + product);
	}

}
