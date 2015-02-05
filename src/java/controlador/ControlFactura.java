/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import beans.Factura;
import beans.Usuario;
import beans.Validador;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import org.springframework.util.LinkedCaseInsensitiveMap;
import servicio.GestionProveedor;
import static servicio.GestionProveedor.*;
import static servicio.gestionFacturas.*;
import static beans.Validador.*;

/**
 *
 * @author luis-valerio
 */
public class ControlFactura extends HttpServlet {

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
                System.out.println("Class: ControlFactura, Accion= ** " + ParametroAccion[0] + " ** " + ParametroAccion[1]);
                int accion = Integer.parseInt(ParametroAccion[1]);

                switch (accion) {
                    /**
                     * Obtener informacion de la cuenta actual
                     *
                     */
                    case 1: {
//                        System.out.println("Mostrar panel de modificación de Factura");
//                String[] datosCuenta = getDatosBasicosCuenta(usuarioActual);
//                String[] datosCuenta=null;// = getDatosBasicosCuenta(usuarioActual);
                        request.setAttribute("modificacion", "factura");
                        varsRemoves = "";
                        varsRemove = varsRemoves.split("-");
                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Modificar.htm");
                        rd.forward(request, response);
                        break;
                    }
                    case 2: {
                        /**
                         * Este case muestra el formulario para la busqueda de
                         * facturas
                         */
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

//                        System.out.println("Mostrar panel de busqueda de Factura");
                        request.setAttribute("busqueda", "factura");
                        Vector provedores = GestionProveedor.getAllProvedores();
                        if (provedores != null) {
                            session.setAttribute("provedores", provedores);
                        }
                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Buscar.htm");
                        rd.forward(request, response);
                        break;
                    }
                    case 3: {
                        /**
                         * Este case realiza la busqueda de la factura
                         */
                        Vector<String[]> resultBusqueda = null;
                        String errorMensaje = "";
//                        System.out.println("Realizando petición de búsqueda de factura en la BD");
                        String Folio = request.getParameter("Folio");
                        String proveedor = request.getParameter("proveedor");
                        String fechaIni = request.getParameter("fechaIni");
                        String fechaFin = request.getParameter("fechaFin");
                        if (fechaIni.equals("") || fechaFin.equals("")) {
                            if (fechaIni.equals("") && fechaFin.equals("")) {
                                resultBusqueda = buscarFactura(Folio, "", proveedor);
                            } else if (fechaFin.equals("")) {
                                resultBusqueda = buscarFacturaFiltroFecha(Folio, proveedor, fechaIni, fechaFin);
                            } else {
                                errorMensaje = "La fecha inicial no puede estar vacia";
                            }
                        } else {
                            resultBusqueda = buscarFacturaFiltroFecha(Folio, proveedor, fechaIni, fechaFin);
                        }

                        if (resultBusqueda != null) {
                            request.setAttribute("resultado", "factura");
                            request.setAttribute("Coincidencias", "Se han encontrado " + resultBusqueda.size() + " coincidencias...");
                            request.setAttribute("resultBusqueda", resultBusqueda);
                        } else {
                            errorMensaje = "Error en la Base de Datos";
                        }
                        request.setAttribute("busqueda", "factura");
//                varsRemoves = "";
//                varsRemove = varsRemoves.split("-");
                        request.setAttribute("folio", Folio);
                        request.setAttribute("proveedor", proveedor);
                        request.setAttribute("fechaIni", fechaIni);
                        request.setAttribute("fechaFin", fechaFin);
//                urlRespuesta = "Buscar.htm";
                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Buscar.htm");
                        rd.forward(request, response);
                        break;
                    }
                    case 4: {
                        /**
                         * Este case muestra los detalles de una factura y sus
                         * recursos que contiene.
                         */
                        Vector resultBusquedaRec = null;
                        Map resultBusquedaPro = null;
                        request.setAttribute("modificacion", "factura");
                        String idFactura = request.getParameter("idFactura");
                        String Folio = request.getParameter("Folio");
                        String proveedor = request.getParameter("proveedor");
                        resultBusquedaRec = getRecursosFactura(idFactura, Folio);
                        session.setAttribute("detallesRecursos", resultBusquedaRec);
                        resultBusquedaPro = getDetallesFactura(idFactura, Folio);
                        session.setAttribute("detalleFactura", resultBusquedaPro);
                        resultBusquedaPro = getDetallesProveedor(null, proveedor);
                        session.setAttribute("detalleProveedor", resultBusquedaPro);
                        request.setAttribute("idFactura", idFactura);

                        Vector provedores = getAllProvedores();
                        provedores.remove(0);
                        request.setAttribute("provedores", provedores);

                        enviado = true;
                        urlRespuesta = "Modificar.htm";
                        RequestDispatcher rd = request.getRequestDispatcher("Modificar.htm");
                        rd.forward(request, response);
                        break;
                    }
                    case 5: {
                        /**
                         * Este case muestra los detalles de una factura y sus
                         * recursos que contiene.
                         */
                        Vector mensaje = new Vector();
                        mensaje.add("¡Error!");
                        Map detalleFactura = (Map) session.getAttribute("detalleFactura");
                        String idFactura = request.getParameter("idFactura");
                        String folio = request.getParameter("Folio");
                        String fecha = request.getParameter("FechaAdquisicion");
                        String montoSin = request.getParameter("montoSinIva");
                        String iva = request.getParameter("iva");
                        String montoTotal = request.getParameter("montoTotal");
                        String noArt = request.getParameter("noArticulos");
                        String observaciones = request.getParameter("Observaciones");
                        //Se valida la sintaxis de los campos a insertar
                        if (!Validador.validaAlfanumerico(folio)) {
                            mensaje.add("Nomenclatura de folio incorrecto");
                            detalleFactura.put("folio", request.getParameter("Folio"));
                        }
                        if (!Validador.validaAlfanumerico(fecha)) {
                            mensaje.add("Nomenclatura de fecha incorrecto");
                            detalleFactura.put("fechaAdquisicion", request.getParameter("FechaAdquisicion"));
                        }
                        montoSin = Validador.validaDecimal(montoSin);
                        if (montoSin.equals("")) {
                            mensaje.add("Formato de número para Monto sin I.V.A incorrecto");
                            detalleFactura.put("montoSinIVA", request.getParameter("montoSinIva"));
                        }
                        iva = Validador.validaDecimal(iva);
                        if (iva.equals("")) {
                            mensaje.add("Formato de número para I.V.A incorrecto");
                            detalleFactura.put("iva", request.getParameter("iva"));
                        }
                        montoTotal = Validador.validaDecimal(montoTotal);
                        if (montoTotal.equals("")) {
                            mensaje.add("Formato de número para Monto total incorrecto");
                            detalleFactura.put("montoConIVA", request.getParameter("montoTotal"));
                        }
                        noArt = Validador.validaEntero(noArt);
                        if (noArt.equals("")) {
                            mensaje.add("Formato de número para Articulos incorrecto");
                            detalleFactura.put("articulos", request.getParameter("noArticulos"));
                        }

                        boolean modificado = false;
                        if (mensaje.size() < 2) {
//                            System.out.println("Se modifica factura en BD");
                            modificado = modificaFactura(idFactura, folio, fecha, montoSin, iva, montoTotal, noArt, observaciones);
                            mensaje.clear();
                            if (modificado) {
                                mensaje.add("¡Factura modificada correctamente!");
                                detalleFactura = getDetallesFactura(idFactura, folio);
                                session.setAttribute("msgErrores", mensaje);
                            } else {
                                mensaje.add("¡Error al modificar la Factura!");
                                session.setAttribute("msgErrores", mensaje);
                            }
                        } else {
                            session.setAttribute("msgErrores", mensaje);
                        }
                        session.setAttribute("detalleFactura", detalleFactura);
                        request.setAttribute("modificacion", "factura");

                        enviado = true;
                        urlRespuesta = "Modificar.htm";
                        RequestDispatcher rd = request.getRequestDispatcher("Modificar.htm");
                        rd.forward(request, response);
                        break;
                    }
                    case 6: {
//                        System.out.println("Cambiar el proveedor de esta factura");
                        Map resultBusquedaPro = null;
                        String idProveedor = request.getParameter("idProveedor");
                        String idFactura = request.getParameter("idFactura");
                        String razonSocial = request.getParameter("razonSocial");
                        Vector mensaje = new Vector();
                        mensaje.add("¡Error!");
                        try {
                            int id = Integer.parseInt(idProveedor);
                            boolean modifica = modificaFacturaProveedor(idFactura, id);
                            if (modifica) {
                                mensaje.set(0, "¡Éxito!");
                                mensaje.add("Se ha modificado el proveedor para esta factura");
                                session.setAttribute("msgErrores", mensaje);
                                resultBusquedaPro = getDetallesProveedor(idProveedor, razonSocial);
                                session.setAttribute("detalleProveedor", resultBusquedaPro);
                            } else {
                                mensaje.add("Error al actulizar la Base de Datos");
                                session.setAttribute("msgErrores", mensaje);
                            }


                        } catch (Exception e) {
                            mensaje.add("Error al ejecutar proceso.");
                            session.setAttribute("msgErrores", mensaje);
                        }
                        request.setAttribute("modificacion", "factura");

                        enviado = true;
                        urlRespuesta = "Modificar.htm";
                        RequestDispatcher rd = request.getRequestDispatcher("Modificar.htm");
                        rd.forward(request, response);
                    }

                    case 7: {
                        /**
                         * Este case realiza la busqueda de una Factura para
                         * seleccionar
                         */
//                        System.out.println("Habilita la ventana modal para la seleccion de factura");
                        request.setAttribute("filtroFactura", "filtroFactura");

                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Nuevo.htm?nuevo=recurso");
                        rd.forward(request, response);
                        break;
                    }

                    case 8: {
                        /**
                         * Este case realiza la busqueda de una Factura para
                         * seleccionar
                         */
                        Vector resultBusqueda = null;
//                        System.out.println("Obtener todos los tipos de recurso");
                        Map filtroFac = new HashMap();
                        filtroFac.put("folio", request.getParameter("folio"));
                        filtroFac.put("fecha", request.getParameter("fecha"));
                        filtroFac.put("proveedor", request.getParameter("proveedor"));

                        resultBusqueda = buscarFactura(filtroFac.get("folio").toString(), filtroFac.get("fecha").toString(), filtroFac.get("proveedor").toString());

                        if (resultBusqueda != null && !resultBusqueda.isEmpty()) {
                            request.setAttribute("facturas", resultBusqueda);
                            request.setAttribute("resultado", "factura");
                        }
                        request.setAttribute("filtroFac", filtroFac);
                        request.setAttribute("filtroFactura", "filtroFactura");

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
                            String idFactura = request.getParameter("idFactura");
                            String folio = request.getParameter("folio");
                            session.setAttribute("idFactura", idFactura);
                            session.setAttribute("facturaSeleccionada", "ID:" + idFactura + "  " + folio);

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
                         * Este case inserta una nueva Factura en la BD
                         */
                        Vector mensaje = null;
                        String folio = folio = request.getParameter("folio");;
                        String fechaAdquisicion = "";
                        String montoSinIVA = "0";
                        String iva = "0.16";
                        String montoConIVA = "0";
                        String articulos = "0";
                        String observaciones = "";
                        String idProveedor = "";
                        try {
                            /*
                             Se hacen las validaciones de los campos para crear una nueva factura en la BD
                             */
                            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
                            Date fechaTemp = null;
                            if (request.getParameter("fechaAdquisicion") != null && !request.getParameter("fechaAdquisicion").equals("")) {
                                fechaTemp = formatoFecha.parse(request.getParameter("fechaAdquisicion"));
                                fechaAdquisicion = request.getParameter("fechaAdquisicion");
                            } else {
                                fechaAdquisicion = formatoFecha.format(new Date());
                            }

                            if (!request.getParameter("montoSinIVA").equals("")) {
                                montoSinIVA = request.getParameter("montoSinIVA");
                                String temp = validaDecimal(montoSinIVA);
                                if (temp.equals("")) {
                                    mensaje = (mensaje == null) ? (new Vector()) : (mensaje);
                                    mensaje.add("- El monto sin I.V.A debe de ser un número decimal");
                                } else {
                                    montoSinIVA = temp;
                                }
                            }
                            if (!request.getParameter("iva").equals("")) {
                                iva = request.getParameter("iva");
                                String temp = validaDecimal(iva);
                                if (temp.equals("")) {
                                    mensaje = (mensaje == null) ? (new Vector()) : (mensaje);
                                    mensaje.add("- El I.V.A debe de ser un número decimal");
                                } else {
                                    iva = temp;
                                }
                            }
                            if (!request.getParameter("montoConIVA").equals("")) {
                                montoConIVA = request.getParameter("montoConIVA");
                                String temp = validaDecimal(montoConIVA);
                                if (temp.equals("")) {
                                    mensaje = (mensaje == null) ? (new Vector()) : (mensaje);
                                    mensaje.add("- El monto sin I.V.A debe de ser un número decimal");
                                } else {
                                    montoConIVA = temp;
                                }
                            }
                            if (!request.getParameter("articulos").equals("")) {
                                articulos = request.getParameter("articulos");
                                String temp = validaDecimal(articulos);
                                if (temp.equals("")) {
                                    mensaje = (mensaje == null) ? (new Vector()) : (mensaje);
                                    mensaje.add("- El número de artículos debe de ser un número entero");
                                } else {
                                    articulos = temp;
                                }
                            }
                            observaciones = request.getParameter("observaciones");
                            idProveedor = request.getParameter("idProveedor");

                            boolean insertado = insertaNuevaFactura(folio, fechaAdquisicion, montoSinIVA, iva, montoConIVA, articulos, observaciones, idProveedor);
                            if (insertado) {
                                mensaje = new Vector();
                                mensaje.add("¡Factura insertada correctamente!");
                            } else {
                                mensaje = new Vector();
                                mensaje.add("¡Error al insertar nueva factura!");
                            }

                        } catch (ParseException pe) {
                            System.out.println("ControlFactura:case6: Error en el formato de fecha");
                            mensaje = new Vector();
                            mensaje.add("¡Error, favor de ingresar correctamente la fecha!");
                        } catch (NumberFormatException nfe) {
                            System.out.println("ControlFactura:case6: Error en el formato de numero");
                            mensaje = new Vector();
                            mensaje.add("¡Error, se requiere un valor decimal!");
                        } catch (Exception e) {
                            System.out.println("ControlFactura:case6: Error al obtener los datos del recurso solicitado");
                            mensaje = new Vector();
                            mensaje.add("¡Error, favor de ingresar correctamente la información solicitada!");
                        }
                        if (mensaje != null) {
                            session.setAttribute("msgErrores", mensaje);
                        }
                        request.setAttribute("folio", folio);
                        request.setAttribute("fechaAdquisicion", fechaAdquisicion);
                        request.setAttribute("montoSinIVA", montoSinIVA);
                        request.setAttribute("iva", iva);
                        request.setAttribute("montoConIVA", montoConIVA);
                        request.setAttribute("articulos", articulos);
                        request.setAttribute("observaciones", observaciones);

                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Nuevo.htm?nuevo=factura");
                        rd.forward(request, response);
                        break;
                    }
                    case 11: {
                        /**
                         * Este case realiza la busqueda del proveedor
                         */
                        request.setAttribute("modificacion", "recurso");
                        request.setAttribute("filtroFactura", "filtroFactura");

                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Modificar.htm");
                        rd.forward(request, response);
                        break;
                    }
                    case 12: {
                        /**
                         * Este case realiza la busqueda de la factura
                         */
                        Vector<Factura> resultBusqueda = null;
                        String errorMensaje = "";
                        String idFactura = request.getParameter("idFactura");
                        String folio = request.getParameter("folio");                        
                        String razonSocial = request.getParameter("razonSocial");
                        String articulos = request.getParameter("articulos");
                        String montoConIVA = request.getParameter("montoConIVA");
                        request.setAttribute("modificacion", "recurso");
                        
                        resultBusqueda = buscarFacturaSelect(idFactura, folio, razonSocial, articulos, montoConIVA);
                        if (resultBusqueda != null) {
                            request.setAttribute("resultado", "factura");
                            request.setAttribute("resultBusqueda", resultBusqueda);
                        } else {
                            errorMensaje = "Error en la Base de Datos";
                        }
                        request.setAttribute("filtroFactura", "filtroFactura");
                        request.setAttribute("idFactura", idFactura);
                        request.setAttribute("folio", folio);
                        request.setAttribute("razonSocial", razonSocial);
                        request.setAttribute("articulos", articulos);
                        request.setAttribute("montoConIVA", montoConIVA);
                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Modificar.htm");
                        rd.forward(request, response);
                        break;
                    }

                    default: {//varsRemoves = "accion-nombreP-apellidoPP-apellidoMP-correo-fechaCreacion-visibilidad-errores";
                        System.out.println("No se encuentra esta opcion de accion.");
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
        try {
            processRequest(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void remueveAtributos(String[] nombresAtributos, HttpSession session) {
//        System.out.println("Eliminando atributos de sesion...");
        for (int i = 0; i < nombresAtributos.length; i++) {
            session.removeAttribute(nombresAtributos[i]);
        }
    }
}
