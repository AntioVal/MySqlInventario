/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package listenersApp;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * Web application lifecycle listener.
 *
 * @author Luis-Valerio
 */
@WebListener()
public class ListenerAtrSession implements HttpSessionAttributeListener {

    @Override
    public void attributeAdded(HttpSessionBindingEvent hsbe) {
//        System.out.println("+++++++++ AddAtrSession:" + hsbe.getName() + "=" + hsbe.getValue());
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent hsbe) {
//        System.out.println("-------- RemovedAtrSession:" + hsbe.getName() + "=" + hsbe.getValue());
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent hsbe) {
//        System.out.println("~~~~~~~ ReplacedAtrSession:" + hsbe.getName() + "=" + hsbe.getValue());
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
