package rest_klient;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Klient01_URL {

	public static void main(String[] args) {
		// Najprostszy sposób w Javie, aby pobrać dane z adresu URL, to użyć klasy URL.
		try {
			URL url = new URL("http://localhost:8080/PC35-RestSerwer/products.json");
			System.out.println("Odczytuję dane...");
			try (InputStream input = url.openStream()) {
				// teraz z inputa możemy czytać ciąg bajtów
				// ja przerzucę bajty czytane z sieci bezpośrednio do System.out
				// (w przypadku obcego serwisu może być to niebezpieczne)
				input.transferTo(System.out);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
