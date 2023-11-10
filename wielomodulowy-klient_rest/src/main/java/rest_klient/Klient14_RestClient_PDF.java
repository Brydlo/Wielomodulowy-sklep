package rest_klient;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class Klient14_RestClient_PDF {
	
	private static final MediaType PDF_TYPE = new MediaType("application", "pdf");

	public static void main(String[] args) {
		int productId = 1;
		
		System.out.println("Startujemy...");
		Client client = ClientBuilder.newClient();
		
		WebTarget root = client.target(Ustawienia.ADRES_USLUGI);
		
		Response response = root
				.path("products")
				.path("{id}")
				.resolveTemplate("id", productId)
				.request()
				.accept(PDF_TYPE)
				.buildGet()
				.invoke();

		System.out.println("Otrzymałem response: " + response);
		System.out.println("Status: " + response.getStatus());
		System.out.println("Content-Type: " + response.getMediaType());
		
		if(response.getStatus() != 200) {
			System.out.println("Chyba coś nie tak, więc przerywam.");
			return;
		}
		
		String nazwaPliku = "wynik.pdf";
		String contentDisposition = response.getHeaderString("Content-Disposition");
		if(contentDisposition != null && contentDisposition.contains(";filename=")) {
			nazwaPliku = contentDisposition.split(";filename=")[1];
		}
		
		try(InputStream strumienDanych = response.readEntity(InputStream.class)) {
			long ileBajtow = Files.copy(strumienDanych, Paths.get(nazwaPliku), StandardCopyOption.REPLACE_EXISTING);
			System.out.printf("Zapisano %d bajtów do pliku %s\n", ileBajtow, nazwaPliku);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Gotowe");
	}

}
