/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import beans.Usuario;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
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
import static servicio.GestionEmpleados.filterUserInput;
import static servicio.GestionUbicacion.*;
import servicio.GestionResguardo;

/**
 *
 * @author Luis-Valerio
 */
public class ControlResguardo extends HttpServlet {

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
                System.out.println("Class: ControlResguardo, Accion= ** " + ParametroAccion[0] + " ** " + ParametroAccion[1]);
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
                        Vector result = new Vector();
                        result = getAllOficinasDistinct();
                        session.setAttribute("oficinas", result);

                        result = servicio.GestionEstado.getAllEstados();
                        session.setAttribute("estatus", result.get(0));

                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Resguardo.htm?nuevo=resguardo");
                        rd.forward(request, response);
                        break;
                    }

                    case 2: {
                        /**
                         * Este case guarda las variables:
                         * oficinaSelect-areaSelect-estatusSelect a la session
                         */
                        Map detalleUbicacion = new HashMap();
                        detalleUbicacion.put("oficina", request.getParameter("oficina"));
                        detalleUbicacion.put("area", request.getParameter("area"));

                        session.setAttribute("detalleUbicacion", detalleUbicacion);
                        session.setAttribute("estadoSelect", request.getParameter("estado"));

                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Resguardo.htm?nuevo=resguardo");
                        rd.forward(request, response);
                        break;
                    }

                    case 3: {
                        /**
                         * Este case guarda llos recursos seleccionados en la
                         * Session
                         */
                        String idRecursosSelect = "";

                        try {
                            //Obtenemos el vector de recursos que hasta ahora estaban en session seleccionados para agregar al resguardo
                            Object temp = session.getAttribute("idRecursosSelect");
                            if (temp != null) {
                                idRecursosSelect = temp.toString();
                                if (!idRecursosSelect.equals("")) {
                                    idRecursosSelect += "," + request.getParameter("idRecursos");
                                }
                            } else {
                                idRecursosSelect = request.getParameter("idRecursos");
                            }
                            idRecursosSelect = idRecursosSelect.substring(0, idRecursosSelect.length() - 1);

                            Vector recSelect = servicio.GestionRecurso.getRecursosPorId(idRecursosSelect);

                            session.setAttribute("idRecursosSelect", idRecursosSelect);
                            session.setAttribute("recSelect", recSelect);

                        } catch (Exception e) {
                        }

                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Resguardo.htm?nuevo=resguardo");
                        rd.forward(request, response);
                        break;
                    }

                    case 4: {
                        /**
                         * Este case guarda llos recursos seleccionados en la
                         * Session
                         */
                        Vector mensaje = null;
                        String idNuevoUsuario = "";
                        String nuevoUsuario = request.getParameter("nuevoUsuario");
                        String idNuevaUbicacion = "";
                        String oficina = request.getParameter("oficina");
                        String area = request.getParameter("area");
                        String idNuevoEstatus = "";
                        String estatus = request.getParameter("estado");
                        String logo = request.getParameter("logo");
                        String folio = "00000";
                        String listaRecursosId = "";

                        if (nuevoUsuario.equals("")) {
                            nuevoUsuario = "no-existe";
                        }
                        if (oficina.equals("---seleccione---")) {
                            oficina = "no-existe";
                        }
                        if (area.equals("---seleccione---")) {
                            area = "no-existe";
                        }
                        if (estatus.equals("---seleccione---")) {
                            estatus = "no-existe";
                        }

                        try {
                            boolean aprobado = false;
                            Vector recSelect = (Vector) session.getAttribute("recSelect");
                            if (recSelect != null && !recSelect.isEmpty()) {
                                //Se modifican los valores: usuarioActual, usuarioAnterior, ubicacion, y estatus, de cada recurso
                                //Se obtienen dichos registros a modificar
                                //ubicacion
                                Map ubicacion = servicio.GestionUbicacion.getDetallesPorOficinaArea(oficina, area);
                                if (ubicacion != null && !ubicacion.isEmpty()) {
                                    idNuevaUbicacion = ubicacion.get("idUbicacion").toString();
                                }
                                Integer tmpIdUsuario = servicio.GestionEmpleados.getIdNombreUnico(nuevoUsuario);
                                if (tmpIdUsuario != null && tmpIdUsuario > 0) {
                                    idNuevoUsuario = tmpIdUsuario.toString();
                                }
                                Map estado = servicio.GestionEstado.getDetalleEstado(estatus);
                                if (estado != null && !estado.isEmpty()) {
                                    idNuevoEstatus = estado.get("idEstadoRecurso").toString();
                                }
                                for (Object recursoT : recSelect) {
                                    aprobado = true;
                                    if (recursoT instanceof Map) {
                                        Map recurso = (Map) recursoT;
                                        boolean correcto = new GestionResguardo().modificaRecursoResguardo(recurso.get("idRecurso").toString(), recurso.get("noSerie").toString(), idNuevaUbicacion, idNuevoUsuario, idNuevoEstatus);
                                        if (correcto) {
                                            listaRecursosId += recurso.get("idRecurso").toString() + ",";
                                        } else {
                                            mensaje = (mensaje == null) ? (new Vector()) : (mensaje);
                                            mensaje.add("Error Modificando: ID[" + recurso.get("idRecurso").toString() + "]:" + recurso.get("noSerie").toString() + " . Favor de generar en una nueva orden de resguardo.");
                                        }
//                                        System.out.println("Modificando ID[" + recurso.get("idRecurso").toString() + "]:" + recurso.get("noSerie").toString());
                                    } else {
                                        mensaje = new Vector();
                                        mensaje.add("¡Error interno!");
                                        mensaje.add("¡No se puden obtener los recursos seleccionados!");
                                        aprobado = false;
                                        break;
                                    }
                                }
                                if (aprobado) {
                                    if (!listaRecursosId.isEmpty()) {
                                        listaRecursosId = listaRecursosId.substring(0, listaRecursosId.length() - 1);
                                        aprobado = new GestionResguardo().insertaResguardo(nuevoUsuario, listaRecursosId, idNuevaUbicacion, usuarioActual.getNombreU());
                                        if (aprobado) {
                                            folio = new GestionResguardo().lastIdResguardo();
                                        } else {
                                            mensaje = (mensaje == null) ? (new Vector()) : (mensaje);
                                            mensaje.add("ERROR: No se pudo crear la orden de resguardo en la Base de Datos");
                                        }
                                    }
                                }
                                //Se almacena la orden de resguardo en la BD
                                //Se prosigue a almacenar la orden de resguardo en la BD, aunque no todos los recursos hallan sido modificados correctamente(problema de usuarioNuevo remplazando al Actual)
                                if (aprobado) {
                                    String[] parametros = new String[9];
                                    parametros[0] = nuevoUsuario;
                                    parametros[1] = area;
                                    parametros[2] = oficina;
                                    parametros[3] = (folio.equals("")) ? "00000" : folio;
                                    parametros[4] = listaRecursosId;
                                    parametros[5] = getServletContext().getRealPath(File.separator);
                                    parametros[6] = logo;
                                    parametros[7] = servicio.GestionTipo.getDescripcionesPorIdRecurso(listaRecursosId);
                                    parametros[8] = servicio.AccionesUsuario.getNombrePersona(usuarioActual.getNombreU());
                                    GestionResguardo gestion = new GestionResguardo();
                                    aprobado = gestion.generaArchivo("resguardo", parametros);
                                    if (aprobado) {
                                        mensaje = new Vector();
                                        mensaje.add("El reporte fue generado correctamente");
                                        mensaje.add("Folio:");
                                        mensaje.add("<titulo>" + folio + "</titulo>");
                                        request.setAttribute("descargar", "descargar");
                                    } else {
                                        mensaje = new Vector();
                                        mensaje.add("¡Error al generar el reporte PDF!");
                                    }
                                }
                            } else {
                                mensaje = new Vector();
                                mensaje.add("¡No se ha seleccionado ningun recurso para generar la orden de resguardo!");
                            }


                        } catch (Exception e) {
                        }

                        session.setAttribute("msgErrores", mensaje);


                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Resguardo.htm?nuevo=resguardo");
                        rd.forward(request, response);
                        break;
                    }

                    case 7: {
                        /**
                         * Este case cambia el estado del recurso
                         */
                        Vector mensaje = null;
//                        System.out.println("Obtener datos para modificacion masiva de recursos");
//                        Vector resultado = new Vector();
                        Vector areas = new Vector();
//                        Vector estados = new Vector();
//                        resultado = getAllUbicaciones();
                        session.setAttribute("areas", areas);
//                        session.setAttribute("estados", estados);
                        request.setAttribute("agregar", "agregar");
                        request.setAttribute("resultado", "recurso");
                        session.setAttribute("msgErrores", mensaje);
                        session.removeAttribute("usuarioAnteriorFM");
                        session.removeAttribute("usuarioActualFM");

                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Resguardo.htm?nuevo=resguardo");
                        rd.forward(request, response);
                        break;
                    }
                    case 8: {
                        /**
                         * Este case descarga la orden de resguardo antes
                         * generada
                         */
                        String pdfFileName = "OrdenResguardo.pdf";
                        String urlReporte = getServletContext().getRealPath(File.separator) + "\\temp\\";
                        File pdfFile = new File(urlReporte + pdfFileName);
                        response.setContentType("aplication/pdf;");
                        response.addHeader("Content-Disposition", "attachment; filename=" + pdfFileName);
                        response.setContentLength((int) pdfFile.length());

                        FileInputStream fileInputStream = new FileInputStream(pdfFile);
                        OutputStream responseOutputStream = response.getOutputStream();
                        int bytes;
                        while ((bytes = fileInputStream.read()) != -1) {
                            responseOutputStream.write(bytes);
                        }

                        enviado = true;
                        break;
                    }

                    case 10: {
                        /**
                         * Este case busca el nombre de un usuario en la BD para
                         * moficiar recurso
                         */
                        /*
                         * Se agregan las variables del formulario a la session para no perderlas en las peticiones posteriores
                         */
                        Vector nombres = new Vector();
//                        System.out.println("Realizando petición de búsqueda de usuario");
                        String nombre = request.getParameter("nuevoUsuario");
                        if (nombre != null) {
                            nombres = filterUserInput(nombre);
                        }
                        if (nombres.size() == 2) {
                            request.setAttribute(ParametroAccion[0] + "_catch", nombres.get(1));
                        }

                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Resguardo.htm?nuevo=resguardo");

                        rd.forward(request, response);
                        break;
                    }

                    case 11: {
                        /**
                         * Este case guarda las variables del formulario para
                         * crear un recurso nuevo en Session
                         */
                        String nuevoUsuario = "";


                        if (request.getParameter("nuevoUsuario") != null) {
                            nuevoUsuario = request.getParameter("nuevoUsuario");
                        }

                        session.setAttribute("nuevoUsuario", nuevoUsuario);


                        enviado = true;
                        RequestDispatcher rd = null;
                        rd = request.getRequestDispatcher("Resguardo.htm?nuevo=resguardo");
                        rd.forward(request, response);
                        break;
                    }

                    case 12: {
                        /**
                         * Este case realiza busqueda de orden de resguardo
                         */
                        String folio = request.getParameter("folio");
                        String usuarioAsignacion = request.getParameter("usuarioAsignacion");
                        String fechaIni = request.getParameter("fechaIni");
                        String fechaFin = request.getParameter("fechaFin");

                        Vector resguardos = new GestionResguardo().buscarResguardo(folio, usuarioAsignacion, fechaIni, fechaFin);
                        if (resguardos != null && !resguardos.isEmpty()) {
                            request.setAttribute("resultado", "resguardo");
                            request.setAttribute("resguardos", resguardos);
                        }
                        request.setAttribute("folio", folio);
                        request.setAttribute("usuarioAsignacion", usuarioAsignacion);
                        request.setAttribute("fechaIni", fechaIni);
                        request.setAttribute("fechaFin", fechaFin);

                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Resguardo.htm?buscar=resguardo");
                        rd.forward(request, response);
                        break;
                    }

                    case 13: {

                        String folioD = request.getParameter("folioD");
                        String listaRecursosId = request.getParameter("listaRecursosId");
                        String usuarioD = request.getParameter("usuarioD");
                        String oficinaD = request.getParameter("oficinaD");
                        String areaD = request.getParameter("areaD");
                        String fechaD = request.getParameter("fechaD");

                        Vector recSelect = servicio.GestionRecurso.getRecursosPorId(listaRecursosId);
                        session.setAttribute("recSelect", recSelect);

                        request.setAttribute("folioD", folioD);
                        request.setAttribute("listaRecursosId", listaRecursosId);
                        request.setAttribute("usuarioD", usuarioD);
                        request.setAttribute("oficinaD", oficinaD);
                        request.setAttribute("areaD", areaD);
                        request.setAttribute("fechaD", fechaD);

                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Resguardo.htm?detalle=resguardo");
                        rd.forward(request, response);
                        break;

                    }


                    case 14: {
                        /**
                         * Este case realiza la generacion del PDF de la orden
                         * de resaguardo
                         */
                        String folioD = request.getParameter("folio");
                        String listaRecursosId = request.getParameter("listaRecursosId");
                        String usuarioD = request.getParameter("usuario");
                        String oficinaD = request.getParameter("oficina");
                        String areaD = request.getParameter("area");
                        String fechaD = request.getParameter("fecha");
                        String logo = request.getParameter("logo");

                        String[] parametros = new String[9];
                        parametros[0] = usuarioD;
                        parametros[1] = areaD;
                        parametros[2] = oficinaD;
                        parametros[3] = (folioD.equals("")) ? "00000" : folioD;
                        parametros[4] = listaRecursosId;
                        parametros[5] = getServletContext().getRealPath(File.separator);
                        parametros[6] = logo;
                        parametros[7] = servicio.GestionTipo.getDescripcionesPorIdRecurso(listaRecursosId);
                        parametros[8] = servicio.AccionesUsuario.getNombrePersona(usuarioActual.getNombreU());
                        boolean aprobado = new GestionResguardo().generaArchivo("resguardo", parametros);
                        if (aprobado) {
                            request.setAttribute("descargar", "descargar");
                            Vector mensaje = new Vector();
                            mensaje.add("Reporte generado correctamente");
                            session.setAttribute("msgErrores", mensaje);
                        }

                        request.setAttribute("folioD", folioD);
                        request.setAttribute("usuarioD", usuarioD);
                        request.setAttribute("oficinaD", oficinaD);
                        request.setAttribute("areaD", areaD);
                        request.setAttribute("fechaD", fechaD);

                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Resguardo.htm?detalle=resguardo");
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
                System.out.println("Excepcion en Class: ControlResguardo " + e.getMessage() + "accion: " + aux);
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
