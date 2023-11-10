package rest.ext;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.MessageBodyWriter;
import jakarta.ws.rs.ext.Provider;
import sklep.model.Product;

@Provider
public class Product2HtmlWriter implements MessageBodyWriter<Product> {

	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		System.out.println("Sprawdzam isWriteable " + type + " " + mediaType);
		// Na podstawie informacji odczytanych z kodu zw. z metodą zwracającą wynik
		// mamy odpowiedzieć na pytanie "czy ten writer sobie z tym poradzi".
		return type == Product.class && mediaType.isCompatible(MediaType.TEXT_HTML_TYPE);
	}

	@Override
	public void writeTo(Product product, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders, OutputStream out)
			throws IOException, WebApplicationException {
		System.out.println("Wykonuję writeTo " + type + " " + mediaType + " dla obiektu " + product);

		// Dla konkretnego obiektu mamy go wypisać w podanym formacie przez przekazany nam OutputStream.
		String html = "<!DOCTYPE html>\n<html><body>" + product.toHtml() + "</body></html>";
		httpHeaders.add("Content-Type", "text/html;charset=utf-8");
		out.write(html.getBytes("utf-8"));
	}

}
