package rest_klient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Klient03_HttpClient {
    /* W Java 11 pojawiło się rozwiązanie "HttpClient", które umożliwia komunikację HTTP z dużą kontrolą nad szczegółami.
     * Wysyłając zapytanie, od razu trzeba podać odpowiedni "BodyHandler",
     * który pozwoli nam odczytać treść odpowiedzi we właściwy dla nas sposób.
     * 
     * W tej wersji wynik jest zapisywany do pliku.
     */

    public static void main(String[] args) {
        HttpClient httpClient = HttpClient.newHttpClient();
        try {
            URI uri = new URI(Ustawienia.ADRES_USLUGI + "/products.json");
            HttpRequest request = HttpRequest.newBuilder(uri).build();
            HttpResponse<Path> response = httpClient.send(request, BodyHandlers.ofFile(Paths.get("wynik03.json")));
            System.out.println("response " + response);
            System.out.println("status: " + response.statusCode());
            System.out.println("Content-Type: " + response.headers().firstValue("Content-Type").orElse("BRAK"));
            System.out.println("OK, zapisany plik: " + response.body());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
