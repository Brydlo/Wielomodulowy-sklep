package rest_klient;

import java.math.BigDecimal;
import java.util.Scanner;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import sklep.model.Product;

public class Klient24_Interaktywna_Edycja {

	public static void main(String[] args) {
		System.out.println("Startujemy...");
		Scanner scanner = new Scanner(System.in);
		
		Client client = ClientBuilder.newClient();
		WebTarget path = client.target(Ustawienia.ADRES_USLUGI)
				.path("products")
				.path("{id}");
		
		System.out.println("Przygotowana ścieżka: " + path);

		while (true) {
			System.out.print("\nPodaj id: ");
			int id = scanner.nextInt();
			if(id == 0) break;
			
			Response response = path
					.resolveTemplate("id", id)
					.request(MediaType.APPLICATION_JSON)
					.get();
			
			System.out.println("Status: " + response.getStatus());
			System.out.println("Content-Type: " + response.getMediaType());
			if (response.getStatus() == 200) {
				Product product = response.readEntity(Product.class);
				System.out.println("Mam produkt:");
				System.out.println("  Nazwa: " + product.getProductName());
				System.out.println("  Cena: " + product.getPrice());
				System.out.println("  Opis: " + product.getDescription());
				System.out.println();
				System.out.println("Podaj zmianę ceny (0 aby nie zmieniać):");
				BigDecimal zmianaCeny = scanner.nextBigDecimal();
				if(zmianaCeny.compareTo(BigDecimal.ZERO) != 0) {
					BigDecimal newPrice = product.getPrice().add(zmianaCeny);
					System.out.println("PUT nowej ceny...");
					Response odpPut = path.path("price").resolveTemplate("id", id).request()
							.put(Entity.entity(newPrice, MediaType.TEXT_PLAIN_TYPE));
					System.out.println("PUT zakończył się kodem " + odpPut.getStatus());
				}
			} else {
				System.out.println("nie mogę odczytać");
			}
		}
	}

}
