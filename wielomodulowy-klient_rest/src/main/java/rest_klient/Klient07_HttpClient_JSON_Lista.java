package rest_klient;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;

public class Klient07_HttpClient_JSON_Lista {

    public static void main(String[] args) {
        try {
            JsonArray array = pobierzJsona(Ustawienia.ADRES_USLUGI + "/products.json");
            for(JsonValue jsonValue : array) {
                JsonObject jsonObject = jsonValue.asJsonObject();
                System.out.println(jsonObject.getString("productName"));
                if(jsonObject.containsKey("description")) {
                    System.out.println("    opis: " + jsonObject.getString("description", ""));
                }
                System.out.println("    cena: " + jsonObject.getJsonNumber("price").bigDecimalValue());
            }
        } catch (IOException | InterruptedException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private static JsonArray pobierzJsona(String adres) throws IOException, InterruptedException, URISyntaxException {
        HttpClient httpClient = HttpClient.newHttpClient();
        URI uri = new URI(adres);
        HttpRequest request = HttpRequest.newBuilder(uri).build();
        HttpResponse<InputStream> response = httpClient.send(request, BodyHandlers.ofInputStream());
        System.out.println("response " + response);
        System.out.println("status: " + response.statusCode());
        System.out.println("Content-Type: " + response.headers().firstValue("Content-Type").orElse("BRAK"));
        return wczytajJsona(response.body());
    }

    private static JsonArray wczytajJsona(InputStream input) {
        try(JsonReader reader = Json.createReader(input)) {
            return reader.readArray();
        }
    }

}
