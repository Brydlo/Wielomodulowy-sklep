package rest_klient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class Klient05_HttpClient_Accept {

    public static void main(String[] args) {
        HttpClient httpClient = HttpClient.newHttpClient();
        try {
            URI uri = new URI(Ustawienia.ADRES_USLUGI + "/products");
            // W tej wersji do zapytania dodajemy nagłówek Accept
            HttpRequest request = HttpRequest.newBuilder(uri)
            		.header("Accept", "text/plain")
            		.build();
            HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
            System.out.println("response " + response);
            System.out.println("status: " + response.statusCode());
            System.out.println("Content-Type: " + response.headers().firstValue("Content-Type").orElse("BRAK"));
            System.out.println("Treść odpowiedzi:\n" + response.body());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
