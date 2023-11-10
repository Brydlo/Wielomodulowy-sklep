package rest_klient;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;

public class Klient02_URL_JSON {

	public static void main(String[] args) {
		try {
			URL url = new URL(Ustawienia.ADRES_USLUGI + "/products.json");
			try(InputStream inputStream = url.openStream();
				JsonReader reader = Json.createReader(inputStream)) {
				JsonArray array = reader.readArray();
				// System.out.println(array);
				for(JsonValue jsonValue : array) {
					//System.out.println(jsonValue);
					System.out.println(jsonValue.asJsonObject().getString("productName"));
					System.out.println("    opis: " + jsonValue.asJsonObject().getString("description", ""));
					System.out.println("    cena: " + jsonValue.asJsonObject().getJsonNumber("price").bigDecimalValue());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
