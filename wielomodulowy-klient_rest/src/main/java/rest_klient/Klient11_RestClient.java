package rest_klient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;

// Ten i kolejne przykłady pokazują jak aplikacja kliencka napisana w Javie może wysyłać
// zapytania do usługi REST-owej (głównie GET, jest też gdzieś POST)
// korzystając z technologii JAX-RS "po stronie klienta".
// Aby z tego skorzystać, do projektu trzeba dodać bibliotekę z implementacją JAX-RS.
// Tutaj jest to resteasy-client.

public class Klient11_RestClient {

	public static void main(String[] args) {
		System.out.println("Startujemy");
		Client client = ClientBuilder.newClient();
		
		System.out.println("Przygotowuję zapytanie");
		WebTarget target = client.target(Ustawienia.ADRES_USLUGI).path("products.json");
		Invocation invocation = target.request().buildGet();
		
		System.out.println("Wysyłam zapytanie");
		Response response = invocation.invoke();
        // Wynikiem jest obiekt klasy Response - tej samej, co na serwerze (używaliśmy np. do generowania kodów 404).
        // W obiekcie można sprawdzić informacji o odpowiedzi: media type, status code.
		
		System.out.println("Mam odpowiedź: " + response);
		System.out.println("Status: " + response.getStatus());
		System.out.println("C-Type: " + response.getMediaType());
		System.out.println("Length: " + response.getLength());
		
        // Aby odczytać zawartość zwróconą przez serwer, używamy metody readEntity.
        // (przy domyślnych ustawieniach) tę metodę można wywołać tylko raz.
	    // Dopiero w tym momencie podajemy typ, na który zostanie skonwertowana treść odpowiedzi
		// (w miarę możliwości - po prostu niektóre typy zadziałają, a niektóre nie).
		byte[] dane = response.readEntity(byte[].class);
		System.out.println("Dane mają " + dane.length + " bajtów.");
		try {
			Files.write(Paths.get("wynik11.json"), dane);
			System.out.println("Zapisałem w pliku");
		} catch (IOException e) {
			System.err.println(e);
		}
		System.out.println("Koniec");
	}

}
