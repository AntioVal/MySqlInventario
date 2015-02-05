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
import static servicio.GestionEstado.*;

/**
 *
 * @author Luis-Valerio
 */
public class ControlEstado extends HttpServlet {

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
                System.out.println("Class: ControlEstado, Accion= ** " + ParametroAccion[0] + " ** " + ParametroAccion[1]);
                int accion = Integer.parseInt(ParametroAccion[1]);

                switch (accion) {
                    /**
                     * Obtener informacion de la cuenta actual
                     *
                     */
                    case 1: {
                        /**
                         * Este case realiza la busqueda del proveedor
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
//                        System.out.println("Buscar los tipos de estatus en los que se puede encontrar un recurso");
                        request.setAttribute("busqueda", "estatus");

                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Buscar.htm");
                        rd.forward(request, response);
                        break;
                    }
                    case 2: {
                        /**
                         * Este case realiza la busqueda del proveedor
                         */
                        Vector resultBusqueda = null;
//                        System.out.println("Obtener todos los Estados en que puede encontrarse un recurso");
                        request.setAttribute("modificacion", "recurso");
//                        request.setAttribute("detalleEstado", session.getAttribute("detalleEstado"));

                        resultBusqueda = getAllEstados();

                        if (resultBusqueda != null && !resultBusqueda.isEmpty() && resultBusqueda.size() == 2) {
                            session.setAttribute("nombres", resultBusqueda.get(0));
                            request.setAttribute("filtroEstado", "filtroEstado");
                        }

                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Modificar.htm");
                        rd.forward(request, response);
                        break;
                    }


                    case 3: {
                        /**
                         * Búsqueda de Tipo por filtro en Select
                         */
//                        System.out.println("Búsqueda de Estado por filtro en Select");
                        Map detalleEstado = new HashMap();
                        String nombre = request.getParameter("nombre");

                        detalleEstado = getDetalleEstado(nombre);

                        if (detalleEstado != null && !detalleEstado.isEmpty()) {
                            request.setAttribute("detalleEstado", detalleEstado);
                            if (ParametroAccion[0].equals("verDetallesEstado")) {
                                request.setAttribute("modificacion", "estadoRecurso");
                            } else {
//                                System.out.println("Se pone el filtroEstado");
                                request.setAttribute("filtroEstado", "filtroEstado");
                                request.setAttribute("modificacion", "recurso");
                            }
                        }

                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Modificar.htm");
                        rd.forward(request, response);
                        break;
                    }

                    case 4: {
                        /**
                         * Este case obtiene todas las ubicaciones en la BD
                         */
                        Vector resultBusqueda = null;
//                        System.out.println("Realizando petición de búsqueda por filtro Input");
                        request.setAttribute("busqueda", "estatus");
                        String nombre = request.getParameter("nombre");
                        String descripcion = request.getParameter("descripcion");

                        resultBusqueda = filtraEstatusInput(nombre, descripcion);
                        if (resultBusqueda != null && !resultBusqueda.isEmpty()) {
                            request.setAttribute("resultBusqueda", resultBusqueda);
                            request.setAttribute("resultado", "estatus");
                        }

                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Buscar.htm");
                        rd.forward(request, response);
                        break;
                    }
                    case 5: {
                        /**
                         * Este case realiza la modificacion del tipo de recurso
                         * solicitado
                         */
                        Vector mensaje = null;
                        request.setAttribute("modificacion", "estadoRecurso");
                        Map detalleEstado = new HashMap();
                        detalleEstado.put("idEstadoRecurso", request.getParameter("idEstadoRecurso"));
                        detalleEstado.put("nombre", request.getParameter("nombre"));
                        detalleEstado.put("descripcion", request.getParameter("descripcion"));
                        try {
                            boolean actualizado = modificaDatosEstadoRecurso(detalleEstado);
                            if (actualizado) {
                                mensaje = new Vector();
                                mensaje.add("¡Datos actualizados correctamente!");
                            } else {
                                mensaje = new Vector();
                                mensaje.add("¡Error al actualizar la información!");
                            }
                            request.setAttribute("detalleEstado", detalleEstado);
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

                    case 6: {
                        /**
                         * Este case realiza la creación de un nuevo estatus
                         */
                        Vector mensaje = null;
                        String nombre = request.getParameter("estatus");
                        String descripcion = request.getParameter("descripcion");
                        try {

                            if (!validaNombres(nombre)) {
                                mensaje = (mensaje == null) ? (new Vector()) : (mensaje);
                                mensaje.add("- El estatus no puede contener caracteres especiales ni numeros");
                            }
                            if (mensaje == null) {
                                boolean existe = existeUnicoEstado(nombre);
                                if (!existe) {
                                    existe = insertaNuevoEstado(nombre, descripcion);
                                    if (existe) {
                                        mensaje = new Vector();
                                        mensaje.add("¡El estatus se creó correctamente!");
                                    } else {
                                        mensaje = new Vector();
                                        mensaje.add("¡Error al crear el nuevo estatus de producto!");
                                    }
                                } else {
                                    mensaje = new Vector();
                                    mensaje.add("¡El estatus existe en la Base de Datos!");
                                }
                            }
                            request.setAttribute("estatus", nombre);
                            request.setAttribute("descripcion", descripcion);
                            session.setAttribute("msgErrores", mensaje);

                        } catch (Exception e) {
                            System.out.println("Exception:" + e.getMessage());
                        }
                        enviado = true;
                        urlRespuesta = "Nuevo.htm";
                        RequestDispatcher rd = request.getRequestDispatcher("Nuevo.htm?nuevo=estatus");
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
                System.out.println("Excepcion en Class: ControlTipo " + e.getMessage() + "accion: " + aux);
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

    private void remueveAtributos(String[] nombresAtributos, HttpSession session) {
//        System.out.println("Eliminando atributos de sesion...");
        for (int i = 0; i < nombresAtributos.length; i++) {
            session.removeAttribute(nombresAtributos[i]);
        }
    }
}
