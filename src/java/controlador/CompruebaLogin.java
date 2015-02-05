
package controlador;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import beans.Usuario;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import static servicio.AccionesUsuario.*;


/**
 *
 * @author luis-valerio
 */
public class CompruebaLogin extends HttpServlet{
    
    
    /**
     * Servlet que verifica la autenticacion del usuarioActual en la BD
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        String accion=null,usuario=null,contrasenna=null,urlRespuesta=null;
//        HttpSession session;
        boolean exception=false;
        Usuario userLogin=null;
//        session = request.getSession();
        
        accion = request.getParameter("accion").toString().trim();
        usuario= request.getParameter("usuario").toString().trim();
        contrasenna= request.getParameter("contrasenna").toString().trim();
        
        String[] vars = {"usuario","accion","contrasenna"};
   
        if (accion != null && accion.compareTo("") != 0 && accion.equals("login")) {
//            System.out.println("Class: CompruebaLogin --> Accion: Login");
            try{
            userLogin = CompruebaUsuario(usuario, contrasenna);}
            catch(SQLException sqle){
                HttpSession session = request.getSession();
                exception = true;
                System.out.println("Exception. SQL CompruebaUsuario");
                session.setAttribute("msgErrores", "Error en Base de Datos");
                response.sendRedirect("index.htm");
            }
            catch(Exception e){   
                HttpSession session = request.getSession();
                exception = true;
                System.out.println("Exception. Exception CompruebaUsuario");
//                RequestDispatcher rd= request.getRequestDispatcher("index.htm");
                session.setAttribute("msgErrores", "Error de Conexión");
//                rd.forward(request, response);
                response.sendRedirect("index.htm");
            }
            if(userLogin!=null){
                HttpSession session = request.getSession(true);
                session.setAttribute("Mensaje","Usuario: ");                
//                System.out.println("Logeado::::: " + userLogin);
                session.setAttribute("usuarioActual",userLogin); 
                response.sendRedirect("Menu.htm");
            }
            else{ if(!exception){
                HttpSession session = request.getSession();
                session.setAttribute("msgErrores", "Usuario o Contraseña inválidos");
                response.sendRedirect("index.htm");
            }}
//        synchronized (session) {
//                        //Se remueven los objetos especificados de la sesion
//                        this.remueveAtributos(vars, session);                        
//                    }
       
//           RequestDispatcher salida = request.getRequestDispatcher(urlRespuesta);
//           salida.forward(request, response);
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
            session.removeAttribute(nombresAtributos[i]);
        }
    }
    
}
