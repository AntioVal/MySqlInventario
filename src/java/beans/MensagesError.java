/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.Vector;

/**
 *
 * @author luis-valerio
 */
public class MensagesError {

    public static Vector errores;

    public static Vector getErrores() {
        return errores;
    }

    public static void setErrores(String error) {
        errores.add(error);
    }
    
    public MensagesError() {
        errores = new Vector();
    }
  
}
