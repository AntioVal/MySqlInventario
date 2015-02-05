/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import beans.Usuario;
import static beans.Validador.validaNombres;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static servicio.GestionUbicacion.*;

/**
 *
 * @author Luis-Valerio
 */
public class ControlUbicacion extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        boolean enviado = false;
        String urlRespuesta = null;
        Usuario usuarioActual = new Usuario();
        String ParametroAccion[] = null, varsRemove[] = {}, varsRemoves;
        String aux = new String("");

        HttpSession session = request.getSession(false);//Si existe regresa la session y no existe entonces regresa null
        if (session != null && session.getAttribute("usuarioActual") != null) {
            try {
                aux = request.getParameter("accion").trim();
                usuarioActual = (Usuario) session.getAttribute("usuarioActual");
                System.out.println("Accion: " + aux);
                ParametroAccion = aux.split(":");
                System.out.println("Class: ControlUbicacion, Accion= ** " + ParametroAccion[0] + " ** " + ParametroAccion[1]);
                int accion = Integer.parseInt(ParametroAccion[1]);

                switch (accion) {
                    /**
                     * Obtener informacion de la cuenta actual
                     *
                     */
                    case 1: {
                        request.setAttribute("busqueda", "ubicacion");
                        //Varibles a remover cuando se da click en nueva opcion del menu de inicio (TODAS LAS VARIABLES SE ELIMINAN)
                        varsRemoves = "";
                        //*Nueva Factura
                        varsRemoves += "idProveedorvalue-proveedorSeleccionado-";
                        //*Bucar Factura
                        varsRemoves += "provedores-detallesRecursos-detalleFactura-detalleProveedor-";
                        //*Detalles del recurso
                        varsRemoves += "detalleRecurso-detalleTipo-detalleUbicacion-detalleEstado-licencias-"
                                + "licenciasVinculadas-tipos-noSerie-costo-tiempoVida-fechaRenovacion-observaciones-oficinas-nombres-";
                        //*Nuevo Dispositivo
                        varsRemoves += "noSerie-costo-tiempoVida-fechaRenovacion-observaciones-tipos-idTipo-estados-tipoSeleccionado-idFactura-"
                                + "facturaSeleccionada-idUbicacion-ubicacionSeleccionada-usuarioActualF-usuarioAnteriorF-";
                        //*Buscar Dispositivo
                        varsRemoves += "estatus-licencias-recursosEncontrados";
                        //*Hoja de resguardo
                        varsRemoves += "idRecursosSelect-recSelect-nuevoUsuario-estadoSelect-";  
                        //*Nueva factura
                        varsRemoves += "folio-fechaAdquisicion-montoSinIVA-montoConIVA-articulos-observaciones";                          
                        String[] atrs = varsRemoves.split("-");

                        synchronized (session) {
                            //Se remueven los objetos especificados de la sesion
                            this.remueveAtributos(atrs, session);
                        }
                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Buscar.htm");
                        rd.forward(request, response);
                        break;
                    }

                    case 2: {
                        /**
                         * Este case realiza la busqueda del proveedor
                         */
                        Vector oficinas = null;
//                        System.out.println("Realizando petición de búsqueda de todas las Ubicaciones");
                        request.setAttribute("modificacion", "recurso");
                        oficinas = getAllOficinasDistinct();

                        if (oficinas != null && !oficinas.isEmpty()) {
                            session.setAttribute("oficinas", oficinas);
                            request.setAttribute("filtroUbicacion", "filtroUbicacion");
                        }

                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Modificar.htm");
                        rd.forward(request, response);
                        break;
                    }
                    case 3: {
                        /**
                         * Este case realiza la busqueda del proveedor
                         */
                        Vector resultBusqueda = null;
                        Map detalleUbicacion = new HashMap();
                        String oficina = request.getParameter("oficina");
                        String accionRec = request.getParameter("accionRec");//modificacionMasiva
                        detalleUbicacion.put("oficina", oficina);
//                        System.out.println("Búsqueda de Ubicacion por filtro en Select");

                        resultBusqueda = filtraGestionSelect(oficina, "");

                        if (resultBusqueda != null && !resultBusqueda.isEmpty() && resultBusqueda.size() == 3) {
                            request.setAttribute("idUbicaciones", resultBusqueda.get(0));
//                            request.setAttribute("oficinas", resultBusqueda.get(1));
                            session.setAttribute("areas", resultBusqueda.get(2));
                            request.setAttribute("detalleUbicacion", detalleUbicacion);
                            request.setAttribute("filtroUbicacion", "filtroUbicacion");
                        }

                        enviado = true;
                        RequestDispatcher rd = null;
                        
                        if (accionRec.equals("modificacionMasiva")) {
                            request.setAttribute("busqueda", "recurso");
                            request.setAttribute("resultado", "recurso");
                            request.setAttribute("modificacionMasiva", "modificacionMasiva");
                            rd = request.getRequestDispatcher("Buscar.htm");
                        } else {
                            if (accionRec.equals("nuevoResguardo")) {
                                rd = request.getRequestDispatcher("Resguardo.htm?nuevo=resguardo");                                
                            } else {
                                request.setAttribute("modificacion", "recurso");
                                rd = request.getRequestDispatcher("Modificar.htm");
                            }
                        }
                        rd.forward(request, response);
                        break;
                    }
                    case 4: {

                        Vector resultBusqueda = null;
                        String oficina = request.getParameter("oficina");
                        String area = request.getParameter("area");
//                        System.out.println("Búsqueda de Ubicacion por filtro Input");

                        resultBusqueda = filtraUbicacionInput(oficina, area);

                        if (resultBusqueda != null && !resultBusqueda.isEmpty()) {
                            request.setAttribute("oficina", oficina);
                            request.setAttribute("area", area);
                            request.setAttribute("resultBusqueda", resultBusqueda);
                        }

                        enviado = true;
                        RequestDispatcher rd = null;

                        request.setAttribute("resultado", "ubicacion");
                        request.setAttribute("busqueda", "ubicacion");
                        rd = request.getRequestDispatcher("Buscar.htm");
                        rd.forward(request, response);
                        break;
                    }
                    case 5: {

                        Map ubicacionRecurso = null;
                        String idUbicacion = request.getParameter("idUbicacion");
//                        System.out.println("Mostrar detalle de ubicacion de recurso");

                        ubicacionRecurso = getDetallesUbicacion(idUbicacion);

                        if (ubicacionRecurso != null && !ubicacionRecurso.isEmpty()) {
                            request.setAttribute("ubicacionRecurso", ubicacionRecurso);
                        }

                        enviado = true;
                        RequestDispatcher rd = null;

                        request.setAttribute("modificacion", "ubicacionRecurso");
                        rd = request.getRequestDispatcher("Modificar.htm");
                        rd.forward(request, response);
                        break;
                    }
                    case 6: {
                        /**
                         * Este case realiza la modificacion del tipo de recurso
                         * solicitado
                         */
                        Vector mensaje = null;
                        request.setAttribute("modificacion", "ubicacionRecurso");
                        Map ubicacionRecurso = new HashMap();
                        try {
                            ubicacionRecurso.put("idUbicacion", request.getParameter("idUbicacion"));
                            ubicacionRecurso.put("oficina", request.getParameter("oficina"));
                            ubicacionRecurso.put("area", request.getParameter("area"));

                            boolean actualizado = modificaDatosUbicacion(ubicacionRecurso);
                            if (actualizado) {
                                mensaje = new Vector();
                                mensaje.add("¡Datos actualizados correctamente!");
                            } else {
                                mensaje = new Vector();
                                mensaje.add("¡Error al actualizar la información!");
                            }
                            request.setAttribute("ubicacionRecurso", ubicacionRecurso);
                            session.setAttribute("msgErrores", mensaje);

                        } catch (Exception e) {
                            System.out.println("Exception:" + e.getMessage());
                        }
                        enviado = true;
                        urlRespuesta = "Modificar.htm";
                        RequestDispatcher rd = request.getRequestDispatcher("Modificar.htm");
                        rd.forward(request, response);
                        break;
                    }
                    case 7: {
                        /**
                         * Este case realiza la busqueda de una Ubicacion para
                         * seleccionar
                         */
//                        System.out.println("Habilita la ventana modal para la seleccion de una ubicacion");
                        request.setAttribute("filtroUbicacion", "filtroUbicacion");

                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Nuevo.htm?nuevo=recurso");
                        rd.forward(request, response);
                        break;
                    }

                    case 8: {
                        /**
                         * Este case realiza la busqueda de una Ubicacion para
                         * seleccionar
                         */
                        Vector resultBusqueda = null;
                        Map filtroUbica = new HashMap();
                        filtroUbica.put("oficina", request.getParameter("oficina"));
                        filtroUbica.put("area", request.getParameter("area"));

                        resultBusqueda = filtraUbicacionInput(filtroUbica.get("oficina").toString(), filtroUbica.get("area").toString());

                        if (resultBusqueda != null && !resultBusqueda.isEmpty()) {
                            request.setAttribute("oficinas", resultBusqueda);
                            request.setAttribute("resultado", "oficina");
                        }
                        request.setAttribute("filtroUbica", filtroUbica);
                        request.setAttribute("filtroUbicacion", "filtroUbicacion");

                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Nuevo.htm?nuevo=recurso");
                        rd.forward(request, response);
                        break;
                    }

                    case 9: {
                        /**
                         * Este case ingresa la seleccion de la factura a la que
                         * corresponde el nuevo recurso
                         */
                        try {
                            String idUbicacion = request.getParameter("idUbicacion");
                            String oficina = request.getParameter("oficina");
                            String area = request.getParameter("area");
                            session.setAttribute("idUbicacion", idUbicacion);
                            session.setAttribute("ubicacionSeleccionada", "ID:" + idUbicacion + "  " + oficina + "," + area);

                        } catch (Exception e) {
                            System.out.println("Exception:" + e.getMessage());
                        }
                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Nuevo.htm?nuevo=recurso");
                        rd.forward(request, response);
                        break;
                    }

                    case 10: {
                        /**
                         * Este case realiza la modificacion del tipo de recurso
                         * solicitado
                         */
                        Vector mensaje = null;
                        String oficina = "";
                        String area = "";
                        try {
                            if (!validaNombres(request.getParameter("oficina"))) {
                                mensaje = (mensaje == null) ? (new Vector()) : (mensaje);
                                mensaje.add("- La oficina no puede contener caracteres especiales ni numeros");
                            }
                            if (!validaNombres(request.getParameter("area"))) {
                                mensaje = (mensaje == null) ? (new Vector()) : (mensaje);
                                mensaje.add("- El area no puede contener caracteres especiales ni numeros");
                            }
                            if (mensaje == null) {
                                oficina = request.getParameter("oficina");
                                area = request.getParameter("area");

                                boolean existe = existeUnicaUbicacion(oficina, area);
                                if (!existe) {
                                    existe = insertaNuevaUbicacion(oficina, area);
                                    if (existe) {
                                        mensaje = new Vector();
                                        mensaje.add("¡La ubicación del recurso se creó correctamente!");
                                    } else {
                                        mensaje = new Vector();
                                        mensaje.add("¡Error al crear el nueva ubicación de recurso!");
                                    }
                                } else {
                                    mensaje = new Vector();
                                    mensaje.add("¡La ubicación de recurso ya existe en la Base de Datos!");
                                }
                            }
                            request.setAttribute("oficina", request.getParameter("oficina"));
                            request.setAttribute("area", request.getParameter("area"));
                            session.setAttribute("msgErrores", mensaje);

                        } catch (Exception e) {
                            System.out.println("Exception:" + e.getMessage());
                        }
                        enviado = true;
                        urlRespuesta = "Nuevo.htm";
                        RequestDispatcher rd = request.getRequestDispatcher("Nuevo.htm?nuevo=oficina");
                        rd.forward(request, response);
                        break;
                    }



                    default: {//varsRemoves = "accion-nombreP-apellidoPP-apellidoMP-correo-fechaCreacion-visibilidad-errores";
                    }
                }
                synchronized (session) {
                    //Se remueven los objetos especificados de la sesion
                    this.remueveAtributos(varsRemove, session);
                }

            } catch (Exception e) {
                System.out.println("Excepcion en Class: ControlUbicacion " + e.getMessage() + "accion: " + aux);
            }
            if (!enviado) {
                response.sendRedirect(urlRespuesta);
            }

        } else {
            response.sendRedirect("index.htm");
        }



    }

    private void remueveAtributos(String[] nombresAtributos, HttpSession session) {
//        System.out.println("Eliminando atributos de sesion...");
        for (int i = 0; i < nombresAtributos.length; i++) {
            session.removeAttribute(nombresAtributos[i]);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
