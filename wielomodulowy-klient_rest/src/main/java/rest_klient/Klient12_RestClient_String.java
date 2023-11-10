package rest_klient;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.Response;

public class Klient12_RestClient_String {

	public static void main(String[] args) {
		Client client = ClientBuilder.newClient();
		
		// Taki styl programowania to "fluent API"
		Response response = client.target(Ustawienia.ADRES_USLUGI)
			.path("products.json")
			.request()
			.buildGet()
			.invoke();
		
		System.out.println("Mam odpowiedź: " + response);
		System.out.println("Status: " + response.getStatus());
		System.out.println("C-Type: " + response.getMediaType());
		System.out.println("Length: " + response.getLength());
		
		// readEntity(OKREŚLENIE TYPU) stara się odczytać tresc odpowiedzi jako obiekt podanego typu
		// Obsługiwane typy to m.in: byte[], String, InputStream, File
		// Dodając odpowiednie "MeassgeBodyReader", możemy obsługiwać dowolne typy.
		// W szczególności, gdy dodamy do projektu obsługę XML lub JSON (zob. zależności Mavena),
		// będziemy mogli odczytywać dane w postaci obiektów naszego modelu, np. Product.
		String dane = response.readEntity(String.class);
		System.out.println("Otrzymane dane:");
		System.out.println(dane);
	}

}
