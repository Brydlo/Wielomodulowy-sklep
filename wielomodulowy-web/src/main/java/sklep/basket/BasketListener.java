package sklep.basket;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

@WebListener
public class BasketListener implements HttpSessionListener, ServletContextListener {

    public void sessionCreated(HttpSessionEvent se) {
        HttpSession sesja = se.getSession();
        sesja.setMaxInactiveInterval(30); // pół minuty i sesja wygasa
        System.out.println("sessionCreated " + sesja.getId());
        Basket basket = new Basket();
        sesja.setAttribute("basket", basket);
    }

    public void sessionDestroyed(HttpSessionEvent se)  {
        HttpSession sesja = se.getSession();
        System.out.println("sessionDestroyed "  + sesja.getId());
    }

    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("contextInitialized");
    }

    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("contextDestroyed");
    }

}
