/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package listenersApp;

import java.util.Date;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Web application lifecycle listener.
 *
 * @author luis-valerio
 */
@WebListener()
public class ListenerSession implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
//        System.out.println("----SESION CREADA");
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        se.getSession().removeAttribute("usuarioActual");
//        System.out.println("----Session destruida");
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
