package rest_klient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

public class Klient04_HttpClient_String {

    public static void main(String[] args) {
        HttpClient httpClient = HttpClient.newHttpClient();
        try {
            URI uri = new URI(Ustawienia.ADRES_USLUGI + "/products.json");
            HttpRequest request = HttpRequest.newBuilder(uri).build();
            // Body z odpowiedzi pobierzemy jako obiekt String z całą treścią
            HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
            System.out.println("response " + response);
            System.out.println("status: " + response.statusCode());
            System.out.println("Content-Type: " + response.headers().firstValue("Content-Type").orElse("BRAK"));
            System.out.println("Treść odpowiedzi:\n" + response.body()); // tutaj
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
