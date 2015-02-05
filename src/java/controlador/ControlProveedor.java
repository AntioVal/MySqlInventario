/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import beans.Proveedor;
import beans.Usuario;
import beans.Validador;
import java.io.IOException;
import java.util.Map;
import java.util.Vector;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.springframework.util.LinkedCaseInsensitiveMap;
import servicio.GestionProveedor;
import static servicio.GestionProveedor.*;
import static servicio.gestionFacturas.*;

/**
 *
 * @author Luis-Valerio
 */
public class ControlProveedor extends HttpServlet {

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
                aux = request.getParameter("accion").toString().trim();
                usuarioActual = (Usuario) session.getAttribute("usuarioActual");
                System.out.println("Accion: " + aux);
                ParametroAccion = aux.split(":");
                System.out.println("Class: ControlProveedor, Accion= ** " + ParametroAccion[0] + " ** " + ParametroAccion[1]);
                int accion = Integer.parseInt(ParametroAccion[1]);

                switch (accion) {
                    /**
                     * Obtener informacion de la cuenta actual
                     *
                     */
                    case 1: {
                        /**
                         * Se muestra el formulario para realizar la busqueda de
                         * un proveedor
                         */
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
                        request.setAttribute("busqueda", "proveedor");
                        
                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Buscar.htm");
                        rd.forward(request, response);
                        break;

                    }
                    case 2: {
                        /**
                         * Este case muestra el formulario para la busqueda de
                         * un proveedor
                         */
//                        System.out.println("Mostrar panel de modificación de Proveedor");
                        String idProveedor = request.getParameter("idProveedor");
                        String razonSocial = request.getParameter("razonSocial");
                        Map detalleProveedor = getDetallesProveedor(idProveedor, razonSocial);
                        request.setAttribute("modificacion", "proveedor");
                        request.setAttribute("detalleProveedor", detalleProveedor);
                        varsRemoves = "";
                        varsRemove = varsRemoves.split("-");
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
                        String errorMensaje = "";
//                        System.out.println("Realizando petición de búsqueda de Proveedor en la BD");
                        String razonSocial = request.getParameter("razonSocial");
                        String rfc = request.getParameter("rfc");
                        String domicilioFiscal = request.getParameter("domicilioFiscal");

                        resultBusqueda = buscaProveedor(razonSocial, rfc, domicilioFiscal);

                        if (resultBusqueda != null && !resultBusqueda.isEmpty()) {
                            request.setAttribute("resultBusqueda", resultBusqueda);
                        }

                        request.setAttribute("busqueda", "proveedor");
                        request.setAttribute("resultado", "proveedor");
                        request.setAttribute("razonSocial", razonSocial);
                        request.setAttribute("rfc", rfc);
                        request.setAttribute("domicilioFiscal", domicilioFiscal);
//                urlRespuesta = "Buscar.htm";
                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Buscar.htm");
                        rd.forward(request, response);
                        break;
                    }
                    case 4: {
                        /**
                         * Este case muestra los detalles de un Proveedor como
                         * resultado de una busqueda
                         */
//                        System.out.println("Obtener datos de Proveedor especifico");
                        String accionString = ParametroAccion[0];
                        String razonSocial = "";
                        if (accionString != null && accionString != null && accionString.equals("verDetallesProveedor")) {
                            request.setAttribute("modificacion", "proveedor");
                            razonSocial = request.getParameter("razonSocial");
                        }
                        if (accionString != null && accionString != null && accionString.equals("getDatosProveedor")) {
                            request.setAttribute("modificacion", "factura");
                            razonSocial = request.getParameter("seleccionar");
                            Vector provedores = getAllProvedores();
                            provedores.remove(0);
                            request.setAttribute("provedores", provedores);
                        }
                        Map detalleProveedor = getDetallesProveedor(null, razonSocial);
                        //request.setAttribute("modificacion", "proveedor");
                        request.setAttribute("detalleProveedor", detalleProveedor);

                        varsRemoves = "";
                        varsRemove = varsRemoves.split("-");
                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Modificar.htm");
                        rd.forward(request, response);
                        break;
                    }
                    case 6: {// este se creo para la modificacion de recurso, borrando antes case 5 y comentando el case 6 de abajo
                        /**
                         * Este case muestra los detalles de un Proveedor como
                         * peticion (ver detalles de proveedor)
                         */
                        Vector mensaje = null;
//                        System.out.println("Modificar datos del proveedor");
                        Proveedor proveedorForm = new Proveedor();
                        try{
                        proveedorForm.setIdProveedor(Integer.parseInt(request.getParameter("idProveedor")));
                        proveedorForm.setRazonSocial(request.getParameter("razonSocial"));
                        proveedorForm.setRfc(request.getParameter("rfc"));
                        proveedorForm.setDomicilioFiscal(request.getParameter("domicilioFiscal"));
                        proveedorForm.setTelefonoContacto(request.getParameter("telefonoContacto"));
                        proveedorForm.setCorreoContacto(request.getParameter("correoContacto"));
                        
                        if(modificaDatosProveedor(proveedorForm)){
                        mensaje = new Vector();
                        mensaje.add("¡Actualización correcta!");
                        request.setAttribute("detalleProveedor", proveedorForm);
                        }
                        else{
                        mensaje = new Vector();
                        mensaje.add("¡Error al actualizar los datos del proveedor!");                        
                        }
                        request.setAttribute("modificacion", "proveedor");
                        session.setAttribute("msgErrores", mensaje);

                        varsRemoves = "";
                        varsRemove = varsRemoves.split("-");
                        }
                        catch(Exception e){
                            System.out.println("El idProveedor es incorrecto");
                        }
                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Modificar.htm");
                        rd.forward(request, response);
                        break;

                    }
                        
                    case 7: {
                        /**
                         * Este case realiza la busqueda de una Factura para
                         * seleccionar
                         */
                        session.setAttribute("folio", request.getParameter("folio"));
                        session.setAttribute("fechaAdquisicion", request.getParameter("fechaAdquisicion"));
                        session.setAttribute("montoSinIVA", request.getParameter("montoSinIVA"));
                        session.setAttribute("iva", request.getParameter("iva"));
                        session.setAttribute("montoConIVA", request.getParameter("montoConIVA"));
                        session.setAttribute("articulos", request.getParameter("articulos"));
                        session.setAttribute("observaciones", request.getParameter("observaciones"));
                        
//                        System.out.println("Habilita la ventana modal para la seleccion de un proveedor");

                        request.setAttribute("filtroProveedor", "filtroProveedor");

                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Nuevo.htm?nuevo=factura");
                        rd.forward(request, response);
                        break;
                    }       
                        
                    case 8: {
                        /**
                         * Este case realiza la busqueda de un proveedor para seleccionarlo
                         */
                        
                        Vector resultBusqueda = null;
                        String razonSocial = request.getParameter("razonSocial");
                        String rfc = request.getParameter("rfc");
                        String domicilioFiscal = request.getParameter("domicilioFiscal");

                        resultBusqueda = buscaProveedor(razonSocial, rfc, domicilioFiscal);

                        if (resultBusqueda != null && !resultBusqueda.isEmpty()) {
                            request.setAttribute("provedores", resultBusqueda);
                            request.setAttribute("resultado", "proveedor");
                        }
                        request.setAttribute("razonSocial", razonSocial);
                        request.setAttribute("rfc", rfc);
                        request.setAttribute("domicilioFiscal", domicilioFiscal);
                        request.setAttribute("filtroProveedor", "filtroProveedor");

                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Nuevo.htm?nuevo=factura");
                        rd.forward(request, response);
                        break;
                    }                        
                        
                    case 9: {
                        /**
                         * Este case ingresa la seleccion de la factura a la que
                         * corresponde el nuevo recurso
                         */
                        
                        try {
                            String idProveedor = request.getParameter("idProveedor");
                            String razonSocial = request.getParameter("razonSocial");
                            session.setAttribute("idProveedor", idProveedor);
                            session.setAttribute("proveedorSeleccionado", "ID:" + idProveedor + "  " + razonSocial);

                        } catch (Exception e) {
                            System.out.println("Exception:" + e.getMessage());
                        }
                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Nuevo.htm?nuevo=factura");
                        rd.forward(request, response);
                        break;
                    }
                        
                    case 10: {
                        /**
                         * Este case realiza la creación de un nuevo proveedor
                         */
                        Vector mensaje = null;
                        Proveedor proveedorForm = new Proveedor();
                        try {
                            proveedorForm.setRazonSocial(request.getParameter("razonSocial"));
                            proveedorForm.setRfc(request.getParameter("rfc"));
                            proveedorForm.setDomicilioFiscal(request.getParameter("domicilioFiscal"));
                            proveedorForm.setTelefonoContacto(request.getParameter("telefono"));
                            proveedorForm.setCorreoContacto(request.getParameter("correo"));

                            boolean existe = existeUnicoProveedor(proveedorForm);
                            if (!existe) {
                                existe = insertaNuevoProveedor(proveedorForm);
                                if (existe) {
                                    mensaje = new Vector();
                                    mensaje.add("¡El proveedor se creó correctamente!");
                                } else {
                                    mensaje = new Vector();
                                    mensaje.add("¡Error al crear el nuevo proveedor de producto!");
                                }
                            } else {
                                mensaje = new Vector();
                                mensaje.add("¡El R.F.C. del proveedor ya existe en la Base de Datos!");
                            }
                            request.setAttribute("proveedorForm", proveedorForm);
                            session.setAttribute("msgErrores", mensaje);

                        } catch (Exception e) {
                            System.out.println("Exception:" + e.getMessage());
                        }
                        enviado = true;
                        urlRespuesta = "Modificar.htm";
                        RequestDispatcher rd = request.getRequestDispatcher("Nuevo.htm?nuevo=proveedor");
                        rd.forward(request, response);
                        break;
                    }                        
                        

//                    case 6: {
//                        /**
//                         * Este case muestra todos los proveedores, obteniendo
//                         */
//                        Vector resultBusquedaRec = null;
//                        Map resultBusquedaPro = null;
//                        session.setAttribute("modificacion", "factura");
//                        String idFactura = request.getParameter("idFactura");
//                        String Folio = request.getParameter("Folio");
//                        String proveedor = request.getParameter("proveedor");
//                        resultBusquedaRec = getRecursosFactura(idFactura, Folio);
//                        session.setAttribute("detallesRecursos", resultBusquedaRec);
//                        resultBusquedaPro = getDetallesFactura(idFactura, Folio);
//                        session.setAttribute("detalleFactura", resultBusquedaPro);
//                        resultBusquedaPro = getDetallesProveedor("", proveedor);
//                        session.setAttribute("detalleProveedor", resultBusquedaPro);
//                        request.setAttribute("idFactura", idFactura);
//
//                        enviado = true;
//                        urlRespuesta = "Modificar.htm";
//                        RequestDispatcher rd = request.getRequestDispatcher("Modificar.htm");
//                        rd.forward(request, response);
//                        break;
//                    }


                    default: {//varsRemoves = "accion-nombreP-apellidoPP-apellidoMP-correo-fechaCreacion-visibilidad-errores";
                    }
                }
                synchronized (session) {
                    //Se remueven los objetos especificados de la sesion
                    this.remueveAtributos(varsRemove, session);
                }

            } catch (Exception e) {
                System.out.println("Excepcion en Class: ControlFactura " + e.getMessage() + "accion: " + aux);
            }
            if (!enviado) {
                response.sendRedirect(urlRespuesta);
            }

        } else {
            response.sendRedirect("index.htm");
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

    public void remueveAtributos(String[] nombresAtributos, HttpSession session) {
//        System.out.println("Eliminando atributos de sesion...");
        for (int i = 0; i < nombresAtributos.length; i++) {
            session.removeAttribute(nombresAtributos[i]);
        }
    }
}
