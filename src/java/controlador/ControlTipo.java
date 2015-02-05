/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import beans.Tipo;
import beans.Usuario;
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
import static servicio.GestionTipo.*;

/**
 *
 * @author Luis-Valerio
 */
public class ControlTipo extends HttpServlet {

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
                System.out.println("Class: ControlTipo, Accion= ** " + ParametroAccion[0] + " ** " + ParametroAccion[1]);
                int accion = Integer.parseInt(ParametroAccion[1]);

                switch (accion) {
                    /**
                     * Obtener informacion de la cuenta actual
                     *
                     */
                    case 1: {
//                        System.out.println("Mostrar panel de busqueda de Tipo de Recurso");
                        request.setAttribute("busqueda", "tipoRecurso");
                        //Varibles a remover cuando se da click en nueva opcion del menu de inicio (TODAS LAS VARIABLES SE ELIMINAN)
                        String variables = "";
                        //*Nueva Factura
                        variables += "idProveedorvalue-proveedorSeleccionado-";
                        //*Bucar Factura
                        variables += "provedores-detallesRecursos-detalleFactura-detalleProveedor-";
                        //*Detalles del recurso
                        variables += "detalleRecurso-detalleTipo-detalleUbicacion-detalleEstado-licencias-"
                                + "licenciasVinculadas-tipos-noSerie-costo-tiempoVida-fechaRenovacion-observaciones-oficinas-nombres-";
                        //*Nuevo Dispositivo
                        variables += "noSerie-costo-tiempoVida-fechaRenovacion-observaciones-tipos-idTipo-estados-tipoSeleccionado-idFactura-"
                                + "facturaSeleccionada-idUbicacion-ubicacionSeleccionada-usuarioActualF-usuarioAnteriorF-";
                        //*Buscar Dispositivo
                        variables += "estatus-licencias-recursosEncontrados";
                        //*Hoja de resguardo
                        variables += "idRecursosSelect-recSelect-nuevoUsuario-estadoSelect-";  
                        //*Nueva factura
                        variables += "folio-fechaAdquisicion-montoSinIVA-montoConIVA-articulos-observaciones";                          
                        String[] atrs = variables.split("-");

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
                        Vector resultBusqueda = null;
//                        System.out.println("Obtener todos los tipos de recurso");
                        request.setAttribute("modificacion", "recurso");
                        resultBusqueda = getAllTipos();

                        if (resultBusqueda != null && !resultBusqueda.isEmpty() && resultBusqueda.size() == 3) {
                            session.setAttribute("tipos", resultBusqueda.get(0));
                            request.setAttribute("marcas", resultBusqueda.get(1));
                            request.setAttribute("modelos", resultBusqueda.get(2));
                            request.setAttribute("filtroTipo", "filtroTipo");
                        }

                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Modificar.htm");
                        if (ParametroAccion[0].equals("allTiposNewRec")) {
                            rd = request.getRequestDispatcher("Nuevo.htm?nuevo=recurso");
                        }
                        rd.forward(request, response);
                        break;
                    }


                    case 3: {
                        /**
                         * Búsqueda de Tipo por filtro en Select
                         */
                        Vector resultBusqueda = null;
//                        System.out.println("Búsqueda de Tipo por filtro en Select");
                        Map detalleTipo = new HashMap();
                        String tipo = request.getParameter("tipo");
                        detalleTipo.put("tipo", tipo);
                        String marca = request.getParameter("marca");
                        detalleTipo.put("marca", marca);
                        String modelo = request.getParameter("modelo");
                        detalleTipo.put("modelo", modelo);
                        if (ParametroAccion[0].substring(12).equals("1")) {
                            marca = modelo = "";
                        }
                        if (ParametroAccion[0].substring(12).equals("2")) {
                            modelo = "";
                        }
                        request.setAttribute("modificacion", "recurso");

                        resultBusqueda = filtraTipoSelect(tipo, marca, modelo);

                        if (resultBusqueda != null && !resultBusqueda.isEmpty() && resultBusqueda.size() == 4) {
                            request.setAttribute("idTipos", resultBusqueda.get(0));
                            request.setAttribute("marcas", resultBusqueda.get(2));
                            request.setAttribute("modelos", resultBusqueda.get(3));
                            request.setAttribute("detalleTipo", detalleTipo);
                            request.setAttribute("filtroTipo", "filtroTipo");
                        }

                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Modificar.htm");
//                        System.out.println("nuevo:" + request.getAttribute("nuevo"));
                        rd.forward(request, response);

                        break;
                    }

                    case 4: {
                        /**
                         * Este case muestra los detalles de una factura y sus
                         * recursos que contiene.
                         */
                        request.setAttribute("modificacion", "tipoRecurso");
                        String idTipo = request.getParameter("idTipo");

                        Map detalleTipoRecurso = getDetallesTipo(idTipo);
                        request.setAttribute("detalleTipoRecurso", detalleTipoRecurso);

                        enviado = true;
                        urlRespuesta = "Modificar.htm";
                        RequestDispatcher rd = request.getRequestDispatcher("Modificar.htm");
                        rd.forward(request, response);
                        break;
                    }

                    case 5: {
                        /**
                         * Búsqueda de Tipo por filtro en Select
                         */
                        Vector resultBusqueda = null;
//                        System.out.println("Búsqueda de Tipo por input");
                        Tipo filtroTipoRec = new Tipo();
                        if (request.getParameter("tipo") != null) {
                            filtroTipoRec.setTipo(request.getParameter("tipo"));
                        }
                        if (request.getParameter("marca") != null) {
                            filtroTipoRec.setMarca(request.getParameter("marca"));
                        }
                        if (request.getParameter("modelo") != null) {
                            filtroTipoRec.setModelo(request.getParameter("modelo"));
                        }

                        resultBusqueda = filtraTipoInput(filtroTipoRec.getTipo(), filtroTipoRec.getMarca(), filtroTipoRec.getModelo());

                        if (resultBusqueda != null && !resultBusqueda.isEmpty() && resultBusqueda.size() == 4) {
                            request.setAttribute("idTipos", resultBusqueda.get(0));
                            request.setAttribute("tipos", resultBusqueda.get(1));
                            request.setAttribute("marcas", resultBusqueda.get(2));
                            request.setAttribute("modelos", resultBusqueda.get(3));
                            request.setAttribute("filtroTipoRec", filtroTipoRec);
                            request.setAttribute("busqueda", "tipoRecurso");
                            request.setAttribute("resultado", "tipoRecurso");
                        }

                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Buscar.htm");
                        if (ParametroAccion[0].equals("selectTipoRecurso")) {
                            rd = request.getRequestDispatcher("Nuevo.htm?nuevo=recurso");
                            request.setAttribute("filtroTipo", "filtroTipo");
                        }
                        rd.forward(request, response);
                        break;
                    }

                    case 6: {
                        /**
                         * Este case realiza la modificacion del tipo de recurso
                         * solicitado
                         */
                        Vector mensaje = null;
                        request.setAttribute("modificacion", "tipoRecurso");
                        Tipo detalleTipoRecurso = new Tipo();
                        try {
                            detalleTipoRecurso.setIdTipo(Integer.parseInt(request.getParameter("idTipo")));
                            detalleTipoRecurso.setTipo(request.getParameter("tipo"));
                            detalleTipoRecurso.setMarca(request.getParameter("marca"));
                            detalleTipoRecurso.setModelo(request.getParameter("modelo"));
                            detalleTipoRecurso.setDescripcion(request.getParameter("descripcion"));

                            boolean actualizado = modificaDatosTipoRecurso(detalleTipoRecurso);
                            if (actualizado) {
                                mensaje = new Vector();
                                mensaje.add("¡Datos actualizados correctamente!");
                            } else {
                                mensaje = new Vector();
                                mensaje.add("¡Error al actualizar la información!");
                            }
                            request.setAttribute("detalleTipoRecurso", detalleTipoRecurso);
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
                         * Este case ingresa la seleccion del tipo de recurso
                         * para crear un nuevo recurso
                         */
                        try {
                            Vector estados = null;
                            estados = servicio.GestionEstado.getAllEstados();
                            String idTipo = request.getParameter("idTipo");
                            String marca = request.getParameter("marca");
                            String modelo = request.getParameter("modelo");
                            session.setAttribute("idTipo", idTipo);
                            session.setAttribute("estados", estados.get(0));
                            session.setAttribute("tipoSeleccionado", "ID:" + idTipo + "  " + marca + "," + modelo);

                        } catch (Exception e) {
                            System.out.println("Exception:" + e.getMessage());
                        }
                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Nuevo.htm?nuevo=recurso");
                        rd.forward(request, response);
                        break;
                    }
                    case 8: {
                        /**
                         * Este case realiza la modificacion del tipo de recurso
                         * solicitado
                         */
                        Vector mensaje = null;
                        Tipo detalleTipoRecurso = new Tipo();
                        try {
                            detalleTipoRecurso.setTipo(request.getParameter("tipo"));
                            detalleTipoRecurso.setMarca(request.getParameter("marca"));
                            detalleTipoRecurso.setModelo(request.getParameter("modelo"));
                            detalleTipoRecurso.setDescripcion(request.getParameter("descripcion"));

                            boolean existe = existeUnicoTipoRecurso(detalleTipoRecurso);
                            if (!existe) {
                                existe = insertaNuevoTipoRecurso(detalleTipoRecurso);
                                if (existe) {
                                    mensaje = new Vector();
                                    mensaje.add("¡El tipo de producto se creó correctamente!");
                                } else {
                                    mensaje = new Vector();
                                    mensaje.add("¡Error al crear el nuevo tipo de producto!");
                                }
                            } else {
                                mensaje = new Vector();
                                mensaje.add("¡El tipo de producto ya existe en la Base de Datos!");
                            }
                            request.setAttribute("detalleTipoRecurso", detalleTipoRecurso);
                            session.setAttribute("msgErrores", mensaje);

                        } catch (Exception e) {
                            System.out.println("Exception:" + e.getMessage());
                        }
                        enviado = true;
                        urlRespuesta = "Modificar.htm";
                        RequestDispatcher rd = request.getRequestDispatcher("Nuevo.htm?nuevo=tipoRecurso");
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

    public void remueveAtributos(String[] nombresAtributos, HttpSession session) {
//        System.out.println("Eliminando atributos de sesion...");
        for (int i = 0; i < nombresAtributos.length; i++) {
            session.removeAttribute(nombresAtributos[i]);
        }
    }
}
