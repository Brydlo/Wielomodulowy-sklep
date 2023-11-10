package rest_klient;

import java.util.List;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.Response;
import sklep.model.Product;

public class Klient22_RestClient_JSON {

    public static void main(String[] args) {
        Client client = ClientBuilder.newClient();
        
        Response response = client.target(Ustawienia.ADRES_USLUGI)
            .path("products.json")
            .request()
            .buildGet()
            .invoke();
        
        System.out.println("Mam odpowiedź: " + response);
        System.out.println("Status: " + response.getStatus());
        System.out.println("C-Type: " + response.getMediaType());
        System.out.println("Length: " + response.getLength());
        
        // Ponieważ wersja JSON na serwerze zwraca wynik typu List<Product>, to tutaj musimy podać "typ generyczny",
        // a nie wystarczy zwykła klasa.
        // Nie zadziała:
        // List<Product> products = response.readEntity(List.class);
            
        GenericType<List<Product>> typListy = new GenericType<>() {};
        List<Product> products = response.readEntity(typListy);
        // albo jednolinijkowo:
        // List<Product> products = response.readEntity(new GenericType<List<Product>>() {});
        for(Product product : products) {
            System.out.println(product);
        }
    }

}
