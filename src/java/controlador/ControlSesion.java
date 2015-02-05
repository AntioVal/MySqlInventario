/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import beans.Usuario;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.util.Enumeration;
import javax.servlet.RequestDispatcher;

/**
 *
 * @author luis-valerio
 */
public class ControlSesion extends HttpServlet{
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        
        boolean enviado=false,cerrarSesion=false;
        String urlRespuesta=null;
        Usuario usuarioActual= new Usuario();
        String ParametroAccion[] = null,varsRemove[]={},varsRemoves;
        String aux= new String("");

        HttpSession session = request.getSession(false);//Si existe regresa la session y no existe entonces regresa null
        if(session!=null && session.getAttribute("usuarioActual")!=null){
        try{
        aux = request.getParameter("accion").toString().trim();
        usuarioActual = (Usuario)session.getAttribute("usuarioActual");
        System.out.println("Accion: " + aux);
        ParametroAccion = aux.split(":");
        System.out.println("Class: ControlCuenta, Accion= ** " + ParametroAccion[0] + " ** " +ParametroAccion[1]);
        int accion = Integer.parseInt(ParametroAccion[1]);
        
        switch(accion){
            /**Obtener informacion de la cuenta actual
             * 
             */
            case 1:
            {  
//                System.out.println("Mostrar menu principal de modificación"); 
//                String[] datosCuenta = getDatosBasicosCuenta(usuarioActual);
//                String[] datosCuenta=null;// = getDatosBasicosCuenta(usuarioActual);
                request.setAttribute("modificacion", "principal");
                varsRemoves = "";
                varsRemove = varsRemoves.split("-");
                enviado = true;
                RequestDispatcher rd = request.getRequestDispatcher("Modificar.htm");
                rd.forward(request, response);
            break;
            }
            case 2:
            {  
//                System.out.println("Mostrar menu principal de búsqueda"); 
//                String[] datosCuenta = getDatosBasicosCuenta(usuarioActual);
//                String[] datosCuenta=null;// = getDatosBasicosCuenta(usuarioActual);
                request.setAttribute("busqueda", "principal");
                varsRemoves = "";
                varsRemove = varsRemoves.split("-");
                enviado = true;
                RequestDispatcher rd = request.getRequestDispatcher("Buscar.htm");
                rd.forward(request, response);
            break;
            }
            case 9:{
            /**
             * Case que hace cierre de sesion de usuario
             */
//                varsRemoves = "usuarioActual-accion";
//                varsRemove = varsRemoves.split("-");
//                cerrarSesion = true;
//                synchronized (session) {
//                        this.remueveAtributos(varsRemove, session);                        
//                    }
                session.invalidate();
                urlRespuesta = "index.htm";
            }
                
          default:
            {varsRemoves = "accion-nombreP-apellidoPP-apellidoMP-correo-fechaCreacion-visibilidad-errores";
        }
        }       if(!cerrarSesion)
                synchronized (session) {
                        //Se remueven los objetos especificados de la sesion
                        this.remueveAtributos(varsRemove, session);                        
                    }
        
        }
        catch(Exception e){
            System.out.println("Excepcion en Class: ControlCuenta " + e.getMessage()+ "accion: " + aux);
        }
        
        if(!enviado){
        response.sendRedirect(urlRespuesta);}
        }
        else{
        response.sendRedirect("index.htm");
        }
        
    }
    
        @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
        public void remueveAtributos(String nombresAtributos[], HttpSession session) {
        for (int i = 0; i < nombresAtributos.length; i++) {
            System.out.println("Removiendo de ControlSession: " + nombresAtributos[i]);
            session.removeAttribute(nombresAtributos[i]);
        }
    }
    
}
