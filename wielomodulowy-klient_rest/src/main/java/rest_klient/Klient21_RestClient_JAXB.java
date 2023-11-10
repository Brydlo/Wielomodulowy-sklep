package rest_klient;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Response;
import sklep.model.Product;
import sklep.model.ProductList;

public class Klient21_RestClient_JAXB {

	public static void main(String[] args) {
		Client client = ClientBuilder.newClient();
		
		Response response = client.target(Ustawienia.ADRES_USLUGI)
			.path("products.xml")
			.request()
			.buildGet()
			.invoke();
		
		System.out.println("Mam odpowiedź: " + response);
		System.out.println("Status: " + response.getStatus());
		System.out.println("C-Type: " + response.getMediaType());
		System.out.println("Length: " + response.getLength());
		
		ProductList products = response.readEntity(ProductList.class);
		
		System.out.println("Otrzymane dane:");
		for (Product product : products.getProducts()) {
			System.out.println(product);
		}
	}

}
