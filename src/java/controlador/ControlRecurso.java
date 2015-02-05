/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import beans.*;
import static beans.Validador.validaNombres;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static servicio.GestionRecurso.*;
import static servicio.GestionTipo.*;
import static servicio.GestionUbicacion.*;
import static servicio.GestionEmpleados.*;
import static servicio.GestionLicencias.*;
import static servicio.GestionEstado.*;

/**
 *
 * @author Luis-Valerio
 */
public class ControlRecurso extends HttpServlet {

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
                aux = request.getParameter("accionRec").toString().trim();
                usuarioActual = (Usuario) session.getAttribute("usuarioActual");
                System.out.println("Accion: " + aux);
                ParametroAccion = aux.split(":");
                System.out.println("Class: ControlRecurso, Accion= ** " + ParametroAccion[0] + " ** " + ParametroAccion[1]);
                int accion = Integer.parseInt(ParametroAccion[1]);

                switch (accion) {
                    /**
                     * Obtener informacion de la cuenta actual
                     *
                     */
                    case 1: {
//                        System.out.println("Mostrar detalles de Recurso y panel de modificación");                        
                        Map detalleRecurso = null;
                        Map detalleTipo = null;
                        Map detalleUbicacion = null;
                        Map estadoRecurso = null;
                        Factura factura = null;
                        Vector licencias = null;
                        request.setAttribute("modificacion", "recurso");

                        String idRecurso = request.getParameter("idRecurso").toString().trim();
                        String noSerie = request.getParameter("noSerie").toString().trim();
                        if (idRecurso != null && noSerie != null) {
                            detalleRecurso = getDetallesRecurso(idRecurso, noSerie);
                            if (detalleRecurso != null) {
                                session.setAttribute("detalleRecurso", detalleRecurso);
                                session.setAttribute("usuarioAnteriorF", detalleRecurso.get("usuarioAnterior").toString());
                                session.setAttribute("usuarioActualF", detalleRecurso.get("usuarioActual").toString());
                                detalleTipo = getDetallesTipo(detalleRecurso.get("idTipo").toString());
                                if (detalleTipo != null) {
                                    session.setAttribute("detalleTipo", detalleTipo);
                                }
                                detalleUbicacion = getDetallesUbicacion(detalleRecurso.get("idUbicacion").toString());
                                if (detalleUbicacion != null) {
                                    session.setAttribute("detalleUbicacion", detalleUbicacion);
                                }
                                estadoRecurso = getDetallesEstadoRecurso(detalleRecurso.get("idEstadoRecurso").toString());
                                if (estadoRecurso != null) {
                                    session.setAttribute("detalleEstado", estadoRecurso);
                                }
                                factura = servicio.gestionFacturas.getDetalleFactura(detalleRecurso.get("idFactura").toString(), "");
                                if (factura != null) {
                                    session.setAttribute("detalleFactura", factura);
                                }
                                licencias = getLicenciasVinculadas(idRecurso);
                                if (licencias != null & !licencias.isEmpty()) {
                                    session.setAttribute("licencias", licencias);
                                    session.setAttribute("licenciasVinculadas", "licenciasVinculadas");
                                }
                            }
                        }
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
                        //Nueva factura
                        variables += "folio-fechaAdquisicion-montoSinIVA-montoConIVA-articulos-observaciones";
                        String[] atrs = variables.split("-");

                        synchronized (session) {
                            //Se remueven los objetos especificados de la sesion
                            this.remueveAtributos(atrs, session);
                        }
//                        System.out.println("Mostrar panel de busqueda de Recurso");
                        request.setAttribute("busqueda", "recurso");
                        //se inertan los comboBox: oficina;estatus;tipo;marca;modelo;proveedor
                        //El siguiente metodo obtiene un vector que contiene(3 vectores que son:
                        //0.idUbicacion   1.Oficina     1. area
                        // para este caso se obtendra el vector de Oficinas
//                        Vector oficinas = (Vector) servicio.GestionUbicacion.getAllUbicaciones().get(1);
                        //De ese vector se obtiene el vector de nombres de status
                        Vector estatus = (Vector) servicio.GestionEstado.getAllEstados().get(0);
                        session.setAttribute("estatus", estatus);
                        Vector licencias = (Vector) getDistinctLicencias();
                        session.setAttribute("licencias", licencias);
//                        Vector provedores = (Vector) servicio.GestionProveedor.getAllProvedores();
//                        Vector resultTipos = servicio.GestionTipo.getAllTipos();
                        //obtenemos el tipo
//                        Vector tipos = (Vector) resultTipos.get(0);
//                        //obtenemos el marca
//                        Vector marcas = (Vector) resultTipos.get(1);
//                        //obtenemos el modelo
//                        Vector modelos = (Vector) resultTipos.get(2);
//                        session.setAttribute("oficinas", oficinas);
//                        session.setAttribute("provedores", provedores);
//                        session.setAttribute("tipos", tipos);
//                        session.setAttribute("marcas", marcas);
//                        session.setAttribute("modelos", modelos);


                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Buscar.htm");
                        rd.forward(request, response);
                        break;
                    }
                    case 3: {
                        /**
                         * Este case cambia el tipo de recurso
                         */
                        Vector mensaje = new Vector();
//                        System.out.println("Cambiar el tipo de recurso");
                        Map detalleTipo = null;
                        String idRecurso = request.getParameter("idRecurso");
                        String tipo = request.getParameter("tipo");
                        String marca = request.getParameter("marca");
                        String modelo = request.getParameter("modelo");

                        Vector result = filtraTipoSelect(tipo, marca, modelo);
                        if (result != null) {
                            Vector idTipo = (Vector) result.get(0);
                            if (idTipo.size() == 1) {
                                try {
                                    if (modificaRecursoTipo(idRecurso, Integer.parseInt(idTipo.get(0).toString()))) {
                                        detalleTipo = getDetallesTipo(idTipo.get(0).toString());
                                        session.setAttribute("detalleTipo", detalleTipo);
                                        mensaje.add("¡Actualización correcta!");
                                    }
                                } catch (Exception e) {
                                    mensaje.add("¡Error!");
                                    mensaje.add("¡No se pudo actualizar la Base de Datos!");
                                }
                            } else {
                                mensaje.add("¡Error!");
                                mensaje.add("¡Especifique correctamente el tipo de recurso!");
                            }
                        } else {
                            mensaje.add("¡Error!");
                            mensaje.add("¡El tipo de recurso no existe!");
                        }
                        session.setAttribute("msgErrores", mensaje);
                        request.setAttribute("modificacion", "recurso");

                        if (detalleTipo != null) {
                            session.setAttribute("detalleTipo", detalleTipo);
                        }
                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Modificar.htm");
                        rd.forward(request, response);
                        break;
                    }
                    case 4: {
                        /**
                         * Este case cambia la ubicacion del recurso
                         */
                        Vector mensaje = new Vector();
//                        System.out.println("Cambiar la ubicacion del recurso");
                        Map detalleUbicacion = null;
                        String idRecurso = request.getParameter("idRecurso");
                        String oficina = request.getParameter("oficina");
                        String area = request.getParameter("area");

                        Vector result = filtraGestionSelect(oficina, area);
                        if (result != null) {
                            Vector idUbicacion = (Vector) result.get(0);
                            if (idUbicacion.size() == 1) {
                                try {
                                    if (modificaRecursoUbicacion(idRecurso, Integer.parseInt(idUbicacion.get(0).toString()))) {
                                        detalleUbicacion = getDetallesUbicacion(idUbicacion.get(0).toString());
                                        session.setAttribute("detalleUbicacion", detalleUbicacion);
                                        mensaje.add("¡Actualización correcta!");
                                    }
                                } catch (Exception e) {
                                    mensaje.add("¡Error!");
                                    mensaje.add("¡No se pudo actualizar la Base de Datos!");
                                }
                            } else {
                                mensaje.add("¡Error!");
                                mensaje.add("¡Especifique correctamente la ubicacion del recurso!");
                            }
                        } else {
                            mensaje.add("¡Error!");
                            mensaje.add("¡El tipo de recurso no existe!");
                        }
                        session.setAttribute("msgErrores", mensaje);
                        request.setAttribute("modificacion", "recurso");

                        if (detalleUbicacion != null) {
                            session.setAttribute("detalleUbicacion", detalleUbicacion);
                        }
                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Modificar.htm");
                        rd.forward(request, response);
                        break;
                    }
                    case 5: {
                        /**
                         * Este case cambia el estado del recurso
                         */
                        Vector mensaje = new Vector();
//                        System.out.println("Cambiar el estado del recurso");
                        Map detalleEstado = null;
                        String idRecurso = request.getParameter("idRecurso");
                        String idEstado = request.getParameter("idEstado");
                        String nombre = request.getParameter("nombre");

                        detalleEstado = getDetallesEstadoRecurso(idEstado);
                        if (detalleEstado != null) {
                            String nombreEdo = (String) detalleEstado.get("nombre");
                            if (nombreEdo.equals(nombre)) {
                                try {
                                    if (modificaRecursoEstado(idRecurso, Integer.parseInt(idEstado))) {
                                        session.setAttribute("detalleEstado", detalleEstado);
                                        mensaje.add("¡Actualización correcta!");
                                    }
                                } catch (Exception e) {
                                    mensaje.add("¡Error!");
                                    mensaje.add("¡No se pudo actualizar la Base de Datos!");
                                }
                            } else {
                                mensaje.add("¡Error!");
                                mensaje.add("¡Especifique correctamente la ubicacion del recurso!");
                            }
                        } else {
                            mensaje.add("¡Error!");
                            mensaje.add("¡El tipo de recurso no existe!");
                        }
                        session.setAttribute("msgErrores", mensaje);
                        request.setAttribute("modificacion", "recurso");

                        if (detalleEstado != null) {
                            session.setAttribute("detalleEstado", detalleEstado);
                        }
                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Modificar.htm");
                        rd.forward(request, response);
                        break;
                    }
                    case 6: {
                        /**
                         * Este case realiza una busqueda de recurso a travez de
                         * un filtro de campos
                         */
                        Vector mensaje = null;
//                        System.out.println("Realiza busqueda de recurso con filtro");
                        Tipo filtroTipo = new Tipo();
                        String fechaAdqIni = "";
                        String fechaAdqFin = "";
                        String fechaRenIni = "";
                        String fechaRenFin = "";
                        //Se obtienen parametros del formulario
                        String usuarioActualF = request.getParameter("usuarioActualF");
                        String usuarioAnteriorF = request.getParameter("usuarioAnteriorF");
                        String noSerie = request.getParameter("noSerie");
                        String limite = request.getParameter("limite");
                        //Obtenemos los datos referentes al Tipo de Producto
                        filtroTipo.setTipo(request.getParameter("tipo"));
                        filtroTipo.setMarca(request.getParameter("marca"));
                        filtroTipo.setModelo(request.getParameter("modelo"));
                        //Obtenemos los datos referentes a la Ubicacion del Recurso
                        String oficina = request.getParameter("oficina");
                        String area = request.getParameter("area");
                        String proveedor = request.getParameter("proveedor");
                        // Parametros de checkBox
                        String estatus = "";
                        String licencia = "";
                        String costo = "";
                        try {
                            if (limite.equals("")) {
                                limite = "100";
                            } else {
                                try {
                                    Integer.parseInt(limite);
                                } catch (Exception ex) {
                                    System.out.println("Parametro limite incorrecto:" + limite);
                                    limite = "100";
                                }
                            }
                            //Almacenamos la informacion obtenida de los checkBox
                            if (request.getParameter("estatus") != null && !request.getParameter("estatus").equals("-- Todo --")) {
                                estatus = request.getParameter("estatus");
                            }
                            if (request.getParameter("licencia") != null && !request.getParameter("licencia").equals("-- Todo --")) {
                                licencia = request.getParameter("licencia");
                            }
                            if (request.getParameter("costo") != null && !request.getParameter("costo").equals("-- Todo --")) {
                                costo = request.getParameter("costo");
                            }
                            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
                            Date fechaTemp = null;
                            if (request.getParameter("fechaAdqIni") != null && !request.getParameter("fechaAdqIni").equals("")) {
                                fechaTemp = formatoFecha.parse(request.getParameter("fechaAdqIni"));
                                fechaAdqIni = request.getParameter("fechaAdqIni");
                            }
                            if (request.getParameter("fechaAdqFin") != null && !request.getParameter("fechaAdqFin").equals("")) {
                                fechaTemp = formatoFecha.parse(request.getParameter("fechaAdqFin"));
                                fechaAdqFin = request.getParameter("fechaAdqFin");
                            }
                            if (request.getParameter("fechaRenIni") != null && !request.getParameter("fechaRenIni").equals("")) {
                                fechaTemp = formatoFecha.parse(request.getParameter("fechaRenIni"));
                                fechaRenIni = request.getParameter("fechaRenIni");
                            }
                            if (request.getParameter("fechaRenFin") != null && !request.getParameter("fechaRenFin").equals("")) {
                                fechaTemp = formatoFecha.parse(request.getParameter("fechaRenFin"));
                                fechaRenFin = request.getParameter("fechaRenFin");
                            }

                        } catch (ParseException pe) {
                            System.out.println("ControlRecurso:case6: Error en el formato de fecha");
                            mensaje = new Vector();
                            mensaje.add("¡Error, favor de ingresar correctamente la fecha!");
                        } catch (NumberFormatException nfe) {
                            System.out.println("ControlRecurso:case6: Error en el formato de numero");
                            mensaje = new Vector();
                            mensaje.add("¡Error, favor de ingresar el tipo de dato correcto para cada campo!");
                        } catch (Exception e) {
                            System.out.println("ControlRecurso:case6: Error al obtener los datos del recurso solicitado");
                            mensaje = new Vector();
                            mensaje.add("¡Error, favor de insertar correctamente la información solicitada!");
                        }
                        if (mensaje == null) {
                            Vector recursosEncontrados = buscarFiltroRecurso(usuarioAnteriorF, usuarioActualF, noSerie, filtroTipo, oficina, area, proveedor, estatus, licencia, costo, fechaAdqIni, fechaAdqFin, fechaRenIni, fechaRenFin, limite);
                            if (recursosEncontrados != null) {
                                if (recursosEncontrados.size() <= 0) {
                                    mensaje = new Vector();
                                    mensaje.add("¡No se encontraron resultados!");
                                } else {
                                    request.setAttribute("resultado", "recurso");
                                    session.setAttribute("recursosEncontrados", recursosEncontrados);
                                }
                            } else {
                                mensaje = new Vector();
                                mensaje.add("¡Error en petición!");
                            }
                        }
                        request.setAttribute("usuarioAnteriorF", usuarioAnteriorF);
                        request.setAttribute("usuarioActualF", usuarioActualF);
                        request.setAttribute("noSerie", noSerie);
                        request.setAttribute("filtroTipo", filtroTipo);
                        request.setAttribute("oficina", oficina);
                        request.setAttribute("area", area);
                        request.setAttribute("proveedor", proveedor);
                        request.setAttribute("selLic", licencia);
                        request.setAttribute("selSt", estatus);
                        request.setAttribute("fechaAdqIni", fechaAdqIni);
                        request.setAttribute("fechaAdqFin", fechaAdqFin);
                        request.setAttribute("fechaRenIni", fechaRenIni);
                        request.setAttribute("fechaRenFin", fechaRenFin);
                        request.setAttribute("limite", limite);
                        request.setAttribute("busqueda", "recurso");
                        session.setAttribute("msgErrores", mensaje);

                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Buscar.htm");
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
                        Vector oficinas = new Vector();
                        Vector areas = new Vector();
//                        Vector estados = new Vector();
//                        resultado = getAllUbicaciones();
                        oficinas = getAllOficinasDistinct();
//                        if (resultado != null && !resultado.isEmpty()) {
//                            oficinas = (Vector) resultado.get(1);
//                            areas = (Vector) resultado.get(2);
//                        }
//                        resultado.removeAllElements();
//                        resultado = servicio.GestionEstado.getAllEstados();
//                        if (resultado != null && !resultado.isEmpty()) {
//                            estados = (Vector) resultado.get(0);
//                        }
                        session.setAttribute("oficinas", oficinas);
                        session.setAttribute("areas", areas);
//                        session.setAttribute("estados", estados);
                        request.setAttribute("modificacionMasiva", "modificacionMasiva");
                        request.setAttribute("resultado", "recurso");
                        request.setAttribute("busqueda", "recurso");
                        session.setAttribute("msgErrores", mensaje);
                        session.removeAttribute("usuarioAnteriorFM");
                        session.removeAttribute("usuarioActualFM");

                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Buscar.htm");
                        rd.forward(request, response);
                        break;
                    }

                    case 8: {
                        /**
                         * Este case cambia el estado del recurso
                         */
                        String mensaje = null;
                        boolean actualizado = false;
                        Map detalleUbicacion = null;
                        Map detalleEstado = null;
//                        System.out.println("Modificacion masiva de recursos");
                        String idRecursos = "";
                        String oficina = "";
                        String area = "";
                        String estado = "";
                        String usuarioActualF = "";
                        String usuarioAnteriorF = "";
                        Vector<Integer> idRecursosInt = new Vector<Integer>();
                        if (request.getParameter("idRecursos") != null) {
                            idRecursos = request.getParameter("idRecursos");
                        }
                        if (request.getParameter("oficina") != null) {
                            oficina = request.getParameter("oficina");
                        }
                        if (request.getParameter("area") != null) {
                            area = request.getParameter("area");
                        }
                        if (request.getParameter("estado") != null) {
                            estado = request.getParameter("estado");
                        }
                        if (request.getParameter("usuarioActualF") != null) {
                            usuarioActualF = request.getParameter("usuarioActualF");
                        }
                        if (request.getParameter("usuarioAnteriorF") != null) {
                            usuarioAnteriorF = request.getParameter("usuarioAnteriorF");
                        }
                        if (!idRecursos.isEmpty()) {
                            String[] idRecursosArray = idRecursos.split(",");
                            for (String idRecurso : idRecursosArray) {
                                try {
                                    idRecursosInt.add(Integer.parseInt(idRecurso));
                                } catch (Exception e) {
                                    System.out.println("Error:los Id de recursos son incorrectos");
                                    mensaje = "Error al obtener el Id de recursos";
                                    break;
                                }
                            }

                        } else {
                            mensaje = "Favor de seleccionar correctamente los recursos a modificar";
                        }
                        if (!idRecursosInt.isEmpty()) {
                            //1.- Verificar que los datos de la ubicacion del recurso sean correctos y existan en la BD
                            if (!oficina.equals("---seleccione---") && !area.equals("---seleccione---")) {
                                detalleUbicacion = getDetallesPorOficinaArea(oficina, area);
                                if (detalleUbicacion == null) {
                                    mensaje = "¡Favor de especificar correctamente la oficina y área deseada!";
                                }
                            }
                            //2.- Inspeccionar si se desea cambiar el estado de los recursos
                            if (!estado.equals("---seleccione---")) {
                                detalleEstado = servicio.GestionEstado.getDetalleEstado(estado);
                                if (detalleEstado == null) {
                                    mensaje = "¡No se encuentra el estado especificado!";
                                }
                            }

                            int idEstado = -1;
                            int idUbicacion = -1;
                            int iduAnterior = -1;
                            int iduActual = -1;
                            if (detalleEstado != null) {
                                idEstado = Integer.parseInt(detalleEstado.get("idEstadoRecurso").toString());
                            }
                            if (detalleUbicacion != null) {
                                idUbicacion = Integer.parseInt(detalleUbicacion.get("idUbicacion").toString());
                            }
                            //3.- Inspeccionar si se desea modificar alguno de los campos de Usuarios                            
                            if (usuarioAnteriorF != null && !usuarioAnteriorF.equals("")) {
                                iduAnterior = getIdNombreUnico(usuarioAnteriorF);
                            }
                            if (usuarioActualF != null && !usuarioActualF.equals("")) {
                                iduActual = getIdNombreUnico(usuarioActualF);
                            }
                            if (idEstado == -1 && idUbicacion == -1 && iduAnterior == -1 && iduActual == -1) {
                                mensaje = "¡No se han ingresado datos a modificar!";
                            } else {
                                actualizado = modificacionMasiva(idUbicacion, idEstado, iduAnterior, iduActual, idRecursos);
                                if (actualizado) {
                                    mensaje = "¡Modificaciones realizadas correctamente.!";
                                    session.setAttribute("msgErroresMasiva", mensaje);
                                }
                            }
                        }
                        detalleUbicacion = new HashMap();
                        detalleUbicacion.put("oficina", request.getParameter("oficina"));
                        detalleUbicacion.put("area", request.getParameter("area"));
                        request.setAttribute("detalleUbicacion", detalleUbicacion);
                        request.setAttribute("estadoSelect", request.getParameter("estado"));
                        request.setAttribute("busqueda", "recurso");
                        request.setAttribute("resultado", "recurso");
                        request.setAttribute("modificacionMasiva", "modificacionMasiva");

                        session.setAttribute("msgErroresMasiva", mensaje);

                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Buscar.htm");
                        rd.forward(request, response);
                        break;
                    }

                    case 9: {
                        /**
                         * Este case cambia el tipo de recurso
                         */
                        Vector mensaje = new Vector();
//                        System.out.println("Modifciar informacion base de recurso");
                        Recurso recursoForm = new Recurso();
                        try {
                            recursoForm.setIdRecurso(Integer.parseInt(request.getParameter("idRecurso")));
                            recursoForm.setNoSerie(request.getParameter("noSerie"));
                            Integer usuarioActualForm = getIdNombreUnico(request.getParameter("usuarioActualF"));
                            Integer usuarioAnteriorForm = getIdNombreUnico(request.getParameter("usuarioAnteriorF"));
                            recursoForm.setCosto(Float.parseFloat(request.getParameter("costo")));
//                            recursoForm.setSistemaOperativo(request.getParameter("licencias"));
                            recursoForm.setTiempoVida(Integer.parseInt(request.getParameter("tiempoVida")));
                            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
                            Date fechaTemp = null;
                            fechaTemp = formatoFecha.parse(request.getParameter("fechaRenovacion"));
                            recursoForm.setFechaRenovacion(fechaTemp);
                            recursoForm.setObservacionesRec(request.getParameter("observacionesRec"));
                            if (usuarioActualForm != null && usuarioActualForm >= 0 && usuarioAnteriorForm != null && usuarioAnteriorForm >= 0) {
                                recursoForm.setUsuarioAnterior(usuarioAnteriorForm);
                                recursoForm.setUsuarioActual(usuarioActualForm);
                                if (modificaInfoBaseRecurso(recursoForm)) {
                                    mensaje.add("¡Actualización correcta!");

                                } else {
                                    mensaje.add("¡Error!");
                                    mensaje.add("¡No se pudo actualizar la Base de Datos!");
                                }
                            } else {
                                if (usuarioActualForm == null) {
                                    mensaje.add("¡Error!");
                                    mensaje.add("¡No se encuentra el empleado especificado!");
                                }
                                if (usuarioActualForm < 0) {
                                    mensaje.add("¡Error!");
                                    mensaje.add("¡El empleado actual tiene " + usuarioActualForm + " registros en la base de datos!");
                                }
                                if (usuarioAnteriorForm < 0) {
                                    mensaje.add("¡Error!");
                                    mensaje.add("¡El empleado anterior tiene " + usuarioAnteriorForm + " registros en la base de datos!");
                                }
                            }
                            request.setAttribute("modificacion", "recurso");

                        } catch (ParseException pe) {
                            System.out.println("Error en el formato de fecha");
                            mensaje.add("¡Error!");
                            mensaje.add("¡Favor de ingresar correctamente la fecha!");
                        } catch (NumberFormatException nfe) {
                            System.out.println("El formato de numero es incorrecto.");
                            mensaje.add("¡Error!");
                            mensaje.add("¡El formato de numero es incorrecto!");
                        } catch (Exception e) {
                            System.out.println("Se ha encontrado un error");
                        }
                        session.setAttribute("msgErrores", mensaje);
                        session.setAttribute("detalleRecurso", recursoForm);
                        request.setAttribute("usuarioAnteriorF", request.getParameter("usuarioAnteriorF"));
                        request.setAttribute("usuarioActualF", request.getParameter("usuarioActualF"));
                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Modificar.htm");
                        rd.forward(request, response);
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
                        String noSerie = "";
                        String costo = "";
                        String tiempoVida = "";
                        String fechaRenovacion = "";
                        String observaciones = "";

                        if (request.getParameter("noSerie") != null) {
                            noSerie = request.getParameter("noSerie");
                        }
                        if (request.getParameter("costo") != null) {
                            costo = request.getParameter("costo");
                        }
                        if (request.getParameter("tiempoVida") != null) {
                            tiempoVida = request.getParameter("tiempoVida");
                        }
                        if (request.getParameter("fechaRenovacion") != null) {
                            fechaRenovacion = request.getParameter("fechaRenovacion");
                        }
                        if (request.getParameter("observaciones") != null) {
                            observaciones = request.getParameter("observaciones");
                        }

                        session.setAttribute("noSerie", noSerie);
                        session.setAttribute("costo", costo);
                        session.setAttribute("tiempoVida", tiempoVida);
                        session.setAttribute("fechaRenovacion", fechaRenovacion);
                        session.setAttribute("observaciones", observaciones);


                        Vector nombres = new Vector();
//                        System.out.println("Realizando petición de búsqueda de usuario");
                        String nombre = request.getParameter("nombre");
                        String nuevo = request.getParameter("nuevo");
                        request.setAttribute("modificacion", "recurso");
                        if (nombre != null) {
                            nombres = filterUserInput(nombre);
                        }
                        if (nombres.size() == 2) {
                            request.setAttribute(ParametroAccion[0] + "_catch", nombres.get(1));
                        }

                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Modificar.htm");
                        if (nuevo != null && nuevo.equals("recurso")) {
                            session.setAttribute("usuarioActualF", request.getParameter("usuarioActualF"));
                            session.setAttribute("usuarioAnteriorF", request.getParameter("usuarioAnteriorF"));
                            rd = request.getRequestDispatcher("Nuevo.htm?nuevo=recurso");
                        } else {
                            request.setAttribute("usuarioActualF", request.getParameter("usuarioActualF"));
                            request.setAttribute("usuarioAnteriorF", request.getParameter("usuarioAnteriorF"));
                        }
                        rd.forward(request, response);
                        break;
                    }
                    case 11: {
                        /**
                         * Este case guarda las variables del formulario para
                         * crear un recurso nuevo en Session
                         */
                        if (ParametroAccion[0].equals("change")) {
                            String noSerie = "";
                            String costo = "";
                            String tiempoVida = "";
                            String fechaRenovacion = "";
                            String observaciones = "";

                            if (request.getParameter("noSerie") != null) {
                                noSerie = request.getParameter("noSerie");
                            }
                            if (request.getParameter("costo") != null) {
                                costo = request.getParameter("costo");
                            }
                            if (request.getParameter("tiempoVida") != null) {
                                tiempoVida = request.getParameter("tiempoVida");
                            }
                            if (request.getParameter("fechaRenovacion") != null) {
                                fechaRenovacion = request.getParameter("fechaRenovacion");
                            }
                            if (request.getParameter("observaciones") != null) {
                                observaciones = request.getParameter("observaciones");
                            }

                            session.setAttribute("noSerie", noSerie);
                            session.setAttribute("costo", costo);
                            session.setAttribute("tiempoVida", tiempoVida);
                            session.setAttribute("fechaRenovacion", fechaRenovacion);
                            session.setAttribute("observaciones", observaciones);
                        } else {

                            String usuarioAnteriorF = "";
                            String usuarioActualF = "";


                            if (request.getParameter("usuarioAnteriorF") != null) {
                                usuarioAnteriorF = request.getParameter("usuarioAnteriorF");
                            }
                            if (request.getParameter("usuarioActualF") != null) {
                                usuarioActualF = request.getParameter("usuarioActualF");
                            }
                            if (ParametroAccion[0].equals("setUserSessionMasiva")) {
                                session.setAttribute("usuarioAnteriorFM", usuarioAnteriorF);
                                session.setAttribute("usuarioActualFM", usuarioActualF);
                            } else {
                                session.setAttribute("usuarioAnteriorF", usuarioAnteriorF);
                                session.setAttribute("usuarioActualF", usuarioActualF);
                            }
                        }

                        enviado = true;
                        RequestDispatcher rd = null;
                        rd = request.getRequestDispatcher("Nuevo.htm?nuevo=recurso");
                        if (ParametroAccion[0].equals("setUserSessionMasiva")) {
                            //Se reenvia información del form para no perderla
                            Map detalleUbicacion = new HashMap();
                            detalleUbicacion.put("oficina", request.getParameter("oficina"));
                            detalleUbicacion.put("area", request.getParameter("area"));
                            request.setAttribute("detalleUbicacion", detalleUbicacion);
                            request.setAttribute("estadoSelect", request.getParameter("estado"));
                            request.setAttribute("busqueda", "recurso");
                            request.setAttribute("resultado", "recurso");
                            request.setAttribute("modificacionMasiva", "modificacionMasiva");
                            rd = request.getRequestDispatcher("Buscar.htm");
                        }
                        rd.forward(request, response);
                        break;
                    }

                    case 12: {
                        /**
                         * Este case busca el nombre de un usuario en la BD para
                         * moficiar recurso
                         */
                        String numeroEmpleado = request.getParameter("numeroEmpleado");
                        String nombre = request.getParameter("nombre");
                        request.setAttribute("numeroEmpleado", numeroEmpleado);
                        request.setAttribute("nombre", nombre);

                        Vector empleados = filterUserInput(numeroEmpleado, nombre);
//                        System.out.println("Realizando petición de búsqueda de usuario");
                        if (empleados != null && !empleados.isEmpty()) {
                            request.setAttribute("empleados", empleados);
                            request.setAttribute("resultado", "empleado");
                        }

                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Buscar.htm?busqueda=empleado");
                        rd.forward(request, response);
                        break;
                    }

                    case 13: {
                        /**
                         * Este case muestra el panel de modificacion de un
                         * empleado
                         */
                        String idEmpleado = request.getParameter("idEmpleado");
                        String nombreCompleto = request.getParameter("nombreCompleto");
                        request.setAttribute("idEmpleado", idEmpleado);
                        request.setAttribute("idEmpleadoAnterior", idEmpleado);
                        request.setAttribute("nombreCompleto", nombreCompleto);
                        request.setAttribute("modificacion", "empleado");

                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Modificar.htm");
                        rd.forward(request, response);
                        break;
                    }

                    case 14: {
                        // este se creo para la modificacion de un empleado
                        Vector mensaje = null;
//                        System.out.println("Modificar datos del empleado");
                        String idEmpleado = request.getParameter("idEmpleado");
                        String idEmpleadoAnterior = request.getParameter("idEmpleadoAnterior");
                        String nombreCompleto = request.getParameter("nombreCompleto");
                        try {
                            String resultado = "Sin_coincidencias";
                            if (!idEmpleadoAnterior.equals(idEmpleado)) {
                                resultado = getNombreUnicoPorId(idEmpleado);
                            }

                            if (resultado.equals("Sin_coincidencias")) {
                                if (modificaDatosEmpleado(idEmpleadoAnterior, idEmpleado, nombreCompleto)) {
                                    mensaje = new Vector();
                                    mensaje.add("¡Actualización correcta!");
                                } else {
                                    mensaje = new Vector();
                                    mensaje.add("¡Error al actualizar los datos del empleado!");
                                }
                            } else {
                                mensaje = new Vector();
                                mensaje.add("¡El número de empleado ya esta siendo utilizado!");
                            }
                            request.setAttribute("modificacion", "empleado");
                            session.setAttribute("msgErrores", mensaje);

                            varsRemoves = "";
                            varsRemove = varsRemoves.split("-");
                        } catch (Exception e) {
                            System.out.println("ExceptionControlRecursoCase14:" + e.getMessage());
                        }
                        request.setAttribute("idEmpleado", idEmpleado);
                        request.setAttribute("nombreCompleto", nombreCompleto);

                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Modificar.htm");
                        rd.forward(request, response);
                        break;
                    }

                    case 15: {
                        /**
                         * Este case busca el nombre de un usuario en la BD para
                         * moficiar recurso
                         */
                        String idDatosLicencia = request.getParameter("idDatosLicencia");
                        String nombre = request.getParameter("nombre");
                        String clave = request.getParameter("clave");
                        request.setAttribute("idDatosLicencia", idDatosLicencia);
                        request.setAttribute("nombre", nombre);
                        request.setAttribute("clave", clave);

                        Vector licencias = filterLicInput(idDatosLicencia, nombre, clave);
                        if (licencias != null && !licencias.isEmpty()) {
                            if (ParametroAccion[0].equals("busquedaSelectLicencia")) {
                                request.setAttribute("licenciasSelect", licencias);
                            } else {
                                session.setAttribute("licencias", licencias);
                            }
                            request.setAttribute("resultado", "licencia");
                        }

                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Buscar.htm?busqueda=licencia");
                        if (ParametroAccion[0].equals("busquedaSelectLicencia")) {
                            request.setAttribute("filtroLicencia", "filtroLicencia");
                            request.setAttribute("modificacion", "recurso");
                            rd = request.getRequestDispatcher("Modificar.htm");
                        }
                        rd.forward(request, response);
                        break;
                    }

                    case 16: {
                        /**
                         * Este case muestra el panel de modificacion de una
                         * licencia
                         */
                        String idDatosLicencia = request.getParameter("idDatosLicencia");
                        String nombre = request.getParameter("nombre");
                        String clave = request.getParameter("clave");
                        String observaciones = request.getParameter("observaciones");
                        request.setAttribute("idDatosLicencia", idDatosLicencia);
                        request.setAttribute("nombre", nombre);
                        request.setAttribute("clave", clave);
                        request.setAttribute("observaciones", observaciones);
                        request.setAttribute("modificacion", "licencia");

                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Modificar.htm");
                        rd.forward(request, response);
                        break;
                    }
                    case 17: {
                        // este se creo para la modificacion de un empleado
                        Vector mensaje = null;
                        String idDatosLicencia = request.getParameter("idDatosLicencia");
                        String nombre = request.getParameter("nombre");
                        String clave = request.getParameter("clave");
                        String observaciones = request.getParameter("observaciones");
                        request.setAttribute("idDatosLicencia", idDatosLicencia);
                        request.setAttribute("nombre", nombre);
                        request.setAttribute("clave", clave);
                        request.setAttribute("observaciones", observaciones);
                        request.setAttribute("modificacion", "licencia");

                        try {
                            if (modificaDatosLicencia(idDatosLicencia, nombre, clave, observaciones)) {
                                mensaje = new Vector();
                                mensaje.add("¡Actualización correcta!");
                            } else {
                                mensaje = new Vector();
                                mensaje.add("¡Error al actualizar los datos de la licencia!");
                            }

                            request.setAttribute("modificacion", "licencia");
                            session.setAttribute("msgErrores", mensaje);

                            varsRemoves = "";
                            varsRemove = varsRemoves.split("-");
                        } catch (Exception e) {
                            System.out.println("ExceptionControlRecursoCase14:" + e.getMessage());
                        }

                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Modificar.htm");
                        rd.forward(request, response);
                        break;
                    }

                    case 18: {
                        // case que habilita la ventana modal para seleccionar una licencia
                        request.setAttribute("filtroLicencia", "filtroLicencia");
                        request.setAttribute("modificacion", "recurso");

                        varsRemoves = "";
                        varsRemove = varsRemoves.split("-");
                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Modificar.htm");
                        rd.forward(request, response);
                        break;
                    }

                    case 19: {
                        /**
                         * Este case inserta un nuevo Recurso en la BD
                         */
                        Recurso recursoForm = new Recurso();
                        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
                        Vector mensaje = null;
                        try {

                            Date fechaTemp = null;

                            boolean insertado = false;

                            recursoForm.setNoSerie(request.getParameter("noSerie"));
                            if (!request.getParameter("observaciones").equals("")) {
                                recursoForm.setObservacionesRec(request.getParameter("observaciones").trim());
                            }
                            if (request.getParameter("costo") == null || request.getParameter("costo").equals("")) {
                                recursoForm.setCosto(0.0f);
                            } else {
                                recursoForm.setCosto(Float.parseFloat(request.getParameter("costo")));
                            }

                            if (request.getParameter("tiempoVida") == null || request.getParameter("tiempoVida").equals("")) {
                                recursoForm.setTiempoVida(0);
                            } else {
                                recursoForm.setTiempoVida(Integer.parseInt(request.getParameter("tiempoVida")));
                            }

                            if (request.getParameter("fechaRenovacion") != null && !request.getParameter("fechaRenovacion").equals("")) {
                                fechaTemp = formatoFecha.parse(request.getParameter("fechaRenovacion"));
                            } else {
                                fechaTemp = new Date();
                            }

                            recursoForm.setFechaRenovacion(fechaTemp);
                            int idUserTemp = getIdNombreUnico(request.getParameter("usuarioAnteriorF"));
                            if (idUserTemp >= 0) {
                                recursoForm.setUsuarioAnterior(idUserTemp);
                            } else {
                                recursoForm.setUsuarioAnterior(0);
                            }

                            idUserTemp = getIdNombreUnico(request.getParameter("usuarioActualF"));
                            if (idUserTemp >= 0) {
                                recursoForm.setUsuarioActual(idUserTemp);
                            } else {
                                recursoForm.setUsuarioActual(0);
                            }
//                            System.out.println("Pasa revision de usuarios");

                            recursoForm.setObservacionesRec(request.getParameter("observaciones"));
                            //Se validan las llaves externas que no esten vacias
                            if (request.getParameter("idTipo") != null && !request.getParameter("idTipo").equals("")) {
                                recursoForm.setTipo_idTipo(Integer.parseInt(request.getParameter("idTipo")));
                            } else {
                                mensaje = (mensaje == null) ? (new Vector()) : (mensaje);
                                mensaje.add("¡Favor de seleccionar un tipo de producto para el nuevo recurso!");
                            }

                            if (request.getParameter("idFactura") != null && !request.getParameter("idFactura").equals("")) {
                                recursoForm.setFactura_idFactura(Integer.parseInt(request.getParameter("idFactura")));
                            } else {
                                mensaje = (mensaje == null) ? (new Vector()) : (mensaje);
                                mensaje.add("¡Favor de seleccionar la factura a la que pertenece el nuevo recurso!");
                            }
//                            System.out.println("Revision de factura");

                            if (request.getParameter("idUbicacion") != null && !request.getParameter("idUbicacion").equals("")) {
                                recursoForm.setUbicacionRecurso_idUbicacion(Integer.parseInt(request.getParameter("idUbicacion")));
                            } else {
                                mensaje = (mensaje == null) ? (new Vector()) : (mensaje);
                                mensaje.add("¡Favor de seleccionar la oficina a la que pertenecerá el nuevo recurso!");
                            }
//                            System.out.println("revision de ubicacion");

                            if (request.getParameter("estatus") != null && !request.getParameter("estatus").equals("")) {
                                String estatus = request.getParameter("estatus");
//                                System.out.println("El estatus obtenido:" + estatus);
                                String tmpIdEstadoRecurso = getDetalleEstado(estatus).get("idEstadoRecurso").toString();
//                                System.out.println("El recurso obtenido:" + tmpIdEstadoRecurso);
                                recursoForm.setEstadoRecurso_idEstadoRecurso(Integer.parseInt(tmpIdEstadoRecurso));
                            } else {
                                mensaje = (mensaje == null) ? (new Vector()) : (mensaje);
                                mensaje.add("¡Favor de seleccionar el estatus del nuevo recurso!");
                            }
//                            System.out.println("revision de estatus");


                            if (mensaje == null) {
                                insertado = insertaNuevoRecurso(recursoForm);
                                if (insertado) {
                                    mensaje = new Vector();
                                    mensaje.add("¡Recurso insertado correctamente!");
                                } else {
                                    mensaje = new Vector();
                                    mensaje.add("¡Error al insertar nuevo recurso!");
                                }
                            }
//                            System.out.println("Termina inserciond e recurso");

                        } catch (ParseException pe) {
                            System.out.println("ControlRecurso:case19: Error en el formato de fecha");
                            System.out.println(pe.getMessage());
                            mensaje = new Vector();
                            mensaje.add("¡Error, favor de ingresar correctamente la fecha!");
                        } catch (NumberFormatException nfe) {
                            System.out.println("ControlRecurso:case19: Error en el formato de numero");
                            System.out.println(nfe.getMessage());
                            mensaje = new Vector();
                            mensaje.add("¡Error, se requiere un valor numérico!");
                        } catch (Exception e) {
                            System.out.println("ControlRecurso:case19: Error al obtener los datos del recurso solicitado");
                            System.out.println(e.getMessage());
                            mensaje = new Vector();
                            mensaje.add("¡Error, favor de ingresar correctamente la información solicitada!");
                        }
                        if (mensaje != null) {
                            session.setAttribute("msgErrores", mensaje);
                        }

                        session.setAttribute("noSerie", recursoForm.getNoSerie());
                        session.setAttribute("costo", recursoForm.getCosto());
                        session.setAttribute("tiempoVida", recursoForm.getTiempoVida());
                        session.setAttribute("fechaRenovacion", formatoFecha.format(recursoForm.getFechaRenovacion()));
                        session.setAttribute("observaciones", recursoForm.getObservacionesRec());

                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Nuevo.htm?nuevo=recurso");
                        rd.forward(request, response);
                        break;
                    }

                    case 20: {
                        /**
                         * Este case inserta en la BD un nuevo empleado
                         */
                        Vector mensaje = null;
                        Map empleado = new HashMap();
                        try {
                            empleado.put("idEmpleado", request.getParameter("idEmpleado"));
                            empleado.put("nombres", request.getParameter("nombres"));
                            empleado.put("apellidoPaterno", request.getParameter("apellidoPaterno"));
                            empleado.put("apellidoMaterno", request.getParameter("apellidoMaterno"));

                            if (Validador.validaEntero(empleado.get("idEmpleado").toString()).equals("")) {
                                mensaje = (mensaje == null) ? (new Vector()) : (mensaje);
                                mensaje.add("- El id del empleado debe de ser un número entero");
                            }
                            if (!validaNombres(empleado.get("nombres").toString())) {
                                mensaje = (mensaje == null) ? (new Vector()) : (mensaje);
                                mensaje.add("- El nombre del empleado no puede contener caracteres especiales ni numeros");
                            }
                            if (!validaNombres(empleado.get("apellidoPaterno").toString())) {
                                mensaje = (mensaje == null) ? (new Vector()) : (mensaje);
                                mensaje.add("- El apellido paterno del empleado no puede contener caracteres especiales ni numeros");
                            }
                            if (!validaNombres(empleado.get("apellidoMaterno").toString())) {
                                mensaje = (mensaje == null) ? (new Vector()) : (mensaje);
                                mensaje.add("- El apellido materno del empleado no puede contener caracteres especiales ni numeros");
                            }
                            if (mensaje == null) {

                                String coincidencias = getNombreUnicoPorId(empleado.get("idEmpleado").toString());
                                if (coincidencias.equals("Sin_coincidencias")) {
                                    String nombreCompleto = empleado.get("nombres").toString() + " ";
                                    nombreCompleto += empleado.get("apellidoPaterno").toString() + " ";
                                    nombreCompleto += empleado.get("apellidoMaterno").toString();
                                    boolean existe = insertaNuevoEmpleado(empleado.get("idEmpleado").toString(), nombreCompleto);
                                    if (existe) {
                                        mensaje = new Vector();
                                        mensaje.add("¡El empleado se insertó correctamente!");
                                    } else {
                                        mensaje = new Vector();
                                        mensaje.add("¡Error al crear nuevo empleado!");
                                    }
                                } else {
                                    mensaje = new Vector();
                                    mensaje.add("¡El Número de empleado ya existe en la Base de Datos!");
                                }
                            }
                            request.setAttribute("empleado", empleado);
                            session.setAttribute("msgErrores", mensaje);

                        } catch (Exception e) {
                            System.out.println("Exception:" + e.getMessage());
                        }
                        enviado = true;
                        urlRespuesta = "Nuevo.htm";
                        RequestDispatcher rd = request.getRequestDispatcher("Nuevo.htm?nuevo=empleado");
                        rd.forward(request, response);
                        break;
                    }

                    case 21: {
                        /**
                         * Este case realiza la modificacion del tipo de recurso
                         * solicitado
                         */
                        Vector mensaje = null;
                        Map licencia = new HashMap();
                        try {

                            licencia.put("nombre", request.getParameter("nombre"));
                            licencia.put("claveLicencia", request.getParameter("claveLicencia"));
                            licencia.put("observaciones", request.getParameter("observaciones"));

                            boolean existe = existeUnicaLicencia(licencia);
                            if (!existe) {
                                existe = insertaNuevaLicencia(licencia);
                                if (existe) {
                                    mensaje = new Vector();
                                    mensaje.add("¡Licencia creada correctamente!");
                                } else {
                                    mensaje = new Vector();
                                    mensaje.add("¡Error al insertar la licencia!");
                                }
                            } else {
                                mensaje = new Vector();
                                mensaje.add("¡La licencia ya existe en la Base de Datos!");
                            }
                            request.setAttribute("licencia", licencia);
                            session.setAttribute("msgErrores", mensaje);

                        } catch (Exception e) {
                            System.out.println("Exception:" + e.getMessage());
                        }
                        enviado = true;
                        urlRespuesta = "Modificar.htm";
                        RequestDispatcher rd = request.getRequestDispatcher("Nuevo.htm?nuevo=licencia");
                        rd.forward(request, response);
                        break;
                    }

                    case 22: {
                        /**
                         * Este case busca el nombre de un usuario en la BD para
                         * moficiar recurso en el modulo de MODIFICACION MASIVA
                         */
                        /*
                         * Se agregan las variables del formulario a la session para no perderlas en las peticiones posteriores
                         */
                        //Se guardan parametros de Form
                        Map detalleUbicacion = new HashMap();
                        detalleUbicacion.put("oficina", request.getParameter("oficina"));
                        detalleUbicacion.put("area", request.getParameter("area"));
                        request.setAttribute("detalleUbicacion", detalleUbicacion);
                        request.setAttribute("estadoSelect", request.getParameter("estado"));


                        Vector nombres = new Vector();
//                        System.out.println("Realizando petición de búsqueda de usuario");
                        String nombre = request.getParameter("nombre");
                        request.setAttribute("modificacion", "recurso");
                        if (nombre != null) {
                            nombres = filterUserInput(nombre);
                        }
                        if (nombres.size() == 2) {
                            request.setAttribute(ParametroAccion[0] + "_catch", nombres.get(1));
                        }

                        session.setAttribute("usuarioActualFM", request.getParameter("usuarioActualF"));
                        session.setAttribute("usuarioAnteriorFM", request.getParameter("usuarioAnteriorF"));

                        request.setAttribute("busqueda", "recurso");
                        request.setAttribute("resultado", "recurso");
                        request.setAttribute("modificacionMasiva", "modificacionMasiva");

                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Buscar.htm");
                        rd.forward(request, response);
                        break;
                    }

                    case 23: {
                        /**
                         * Este case vincula una licencia a un recurso
                         * individual
                         */
                        Vector mensaje = null;

                        String idDatosLicencia = request.getParameter("idDatosLicencia");
                        String idRecurso = request.getParameter("idRecurso");
                        try {
                            int idRecursoInt = Integer.parseInt(idRecurso);
                            int idDatosLicenciaInt = Integer.parseInt(idDatosLicencia);

                            boolean actualizado = vincularLicencia(idRecursoInt, idDatosLicenciaInt);

                            if (actualizado) {
                                Vector licencias = getLicenciasVinculadas(idRecurso);
                                session.setAttribute("licencias", licencias);
                                mensaje = new Vector();
                                mensaje.add("¡Licencia vinculada al recurso!");
                            } else {
                                mensaje = new Vector();
                                mensaje.add("¡Error al vincular la licencia seleccionada!");
                            }


                        } catch (Exception ex) {
                            mensaje = new Vector();
                            mensaje.add("¡Error al obtener los datos a modificar!");
                        }



                        session.setAttribute("msgErrores", mensaje);
                        request.setAttribute("modificacion", "recurso");

                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Modificar.htm");
                        rd.forward(request, response);
                        break;
                    }
                    case 24: {
                        /**
                         * Este case DESVINCULA una licencia de un recurso
                         * individual
                         */
                        Vector mensaje = null;

                        String idDatosLicencia = request.getParameter("idDatosLicencia");
                        String idRecurso = request.getParameter("idRecurso");
                        try {
                            int idRecursoInt = Integer.parseInt(idRecurso);
                            int idDatosLicenciaInt = Integer.parseInt(idDatosLicencia);

//                            System.out.println("idRecursoInt:" + idRecursoInt);
//                            System.out.println("idDatosLicenciaInt:" + idDatosLicenciaInt);

                            boolean actualizado = desvincularLicencia(idRecursoInt, idDatosLicenciaInt);

                            if (actualizado) {
                                Vector licencias = getLicenciasVinculadas(idRecurso);
                                session.setAttribute("licencias", licencias);
                                mensaje = new Vector();
                                mensaje.add("¡La licencia ha sido desvinculada del recurso actual!");
                            } else {
                                mensaje = new Vector();
                                mensaje.add("¡Error al desvincular la licencia!");
                            }


                        } catch (Exception ex) {
                            mensaje = new Vector();
                            mensaje.add("¡Error al obtener los datos a modificar!");
                        }



                        session.setAttribute("msgErrores", mensaje);
                        request.setAttribute("modificacion", "recurso");

                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Modificar.htm");
                        rd.forward(request, response);
                        break;
                    }


                    case 25: {
                        /**
                         * Este case realiza una busqueda de recurso básica para
                         * seleccinar y agregar a RESGUARDO
                         */
                        Vector mensaje = null;
                        //Se obtienen parametros del formulario
                        //Se obtienen los datos unicos del recurso
                        String idRecurso = request.getParameter("idRecurso");
                        String noSerie = request.getParameter("noSerie");
                        //Obtenemos los datos referentes al tipo del recurso
                        String marca = request.getParameter("marca");
                        String modelo = request.getParameter("modelo");
                        //Obtenemos los datos referentes a la Ubicacion del Recurso
                        String oficina = request.getParameter("oficina");
                        String area = request.getParameter("area");
//                        String usuarioAnteriorF = request.getParameter("usuarioAnteriorF");                        
                        String usuarioActualF = request.getParameter("usuarioActualF");

                        Vector recursosEncontrados = buscarRecursoBasica(idRecurso, noSerie, marca, modelo, oficina, area, usuarioActualF);
                        if (recursosEncontrados != null) {
                            if (recursosEncontrados.isEmpty()) {
                                request.setAttribute("recursosResguardo", null);
                            } else {
                                request.setAttribute("recursosResguardo", recursosEncontrados);
                            }
                            request.setAttribute("agregar", "agregar");
                        } else {
                            mensaje = new Vector();
                            mensaje.add("¡Error en petición!");
                        }

                        session.setAttribute("msgErrores", mensaje);

                        request.setAttribute("idRecurso", idRecurso);
                        request.setAttribute("noSerie", noSerie);
                        request.setAttribute("marca", marca);
                        request.setAttribute("modelo", modelo);
                        request.setAttribute("oficina", oficina);
                        request.setAttribute("area", area);
//                        request.setAttribute("usuarioAnteriorF", usuarioAnteriorF);                                                                   
                        request.setAttribute("usuarioActualF", usuarioActualF);

                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Resguardo.htm?nuevo=resguardo");
                        rd.forward(request, response);
                        break;
                    }

                    case 26: {
                        Vector mensaje = new Vector();
                        Factura detalleFactura = null;
                        String idRecurso = request.getParameter("idRecurso");
                        String idFactura = request.getParameter("idFactura");
                        String folio = request.getParameter("folio");


                        detalleFactura = servicio.gestionFacturas.getDetalleFactura(idFactura, folio);
                        if (detalleFactura != null) {
                            if (detalleFactura.getIdFactura() >= 0) {
                                try {
                                    if (modificaRecursoFactura(idRecurso, detalleFactura.getIdFactura())) {
                                        session.setAttribute("detalleFactura", detalleFactura);
                                        mensaje.add("¡Actualización correcta!");
                                    }
                                } catch (Exception e) {
                                    mensaje.add("¡Error!");
                                    mensaje.add("¡No se puede actualizar la base de datos!");
                                }
                            } else {
                                mensaje.add("¡Error!");
                                mensaje.add("¡Favor de verificar la factura seleccionada!");
                            }
                        } else {
                            mensaje.add("¡Error!");
                            mensaje.add("¡No se puede actualizar la base de datos!");
                        }
                        session.setAttribute("msgErrores", mensaje);
                        request.setAttribute("modificacion", "recurso");

                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Modificar.htm");
                        rd.forward(request, response);
                        break;
                    }

                    case 27: {
                        /**
                         * Este case busca el nombre de un usuario en la BD para
                         * moficiar recurso en el modulo de MODIFICACION MASIVA
                         */
                        /*
                         * Se agregan las variables del formulario a la session para no perderlas en las peticiones posteriores
                         */
                        //Se guardan parametros de Form
                        //Se obtienen los datos unicos del recurso
                        String idRecurso = request.getParameter("idRecurso");
                        String noSerie = request.getParameter("noSerie");
                        //Obtenemos los datos referentes al tipo del recurso
                        String marca = request.getParameter("marca");
                        String modelo = request.getParameter("modelo");
                        //Obtenemos los datos referentes a la Ubicacion del Recurso
                        String oficina = request.getParameter("oficina");
                        String area = request.getParameter("area");
//                        String usuarioAnteriorF = request.getParameter("usuarioAnteriorF");                        
                        String usuarioActualF = request.getParameter("usuarioActualF");


                        Vector nombres = new Vector();
//                        System.out.println("Realizando petición de búsqueda de usuario");
                        String nombre = request.getParameter("nombre");
                        request.setAttribute("agregar", "agregar");
                        if (nombre != null) {
                            nombres = filterUserInput(nombre);
                        }
                        if (nombres.size() == 2) {
                            request.setAttribute(ParametroAccion[0] + "_catch", nombres.get(1));
                        }

                        request.setAttribute("idRecurso", idRecurso);
                        request.setAttribute("noSerie", noSerie);
                        request.setAttribute("marca", marca);
                        request.setAttribute("modelo", modelo);
                        request.setAttribute("oficina", oficina);
                        request.setAttribute("area", area);
//                        request.setAttribute("usuarioAnteriorF", usuarioAnteriorF);                                                                   
                        request.setAttribute("usuarioActualF", usuarioActualF);

                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Resguardo.htm?nuevo=resguardo");
                        rd.forward(request, response);
                        break;
                    }
                    case 28: {
                        /**
                         * Este case se crea la consulta de un recurso de
                         * acuerdo a los parametros especificados por el usuario
                         */
                        Vector mensaje = null;
                        String seleccion = request.getParameter("seleccion");
                        String ordenamiento = request.getParameter("ordenamiento1");
                        if (!request.getParameter("ordenamiento2").equals("")) {
                            ordenamiento += "," + request.getParameter("ordenamiento2");
                        }
                        if (!request.getParameter("ordenamiento3").equals("")) {
                            ordenamiento += "," + request.getParameter("ordenamiento3");
                        }

                        Vector recursosEncontrados = consultaDinamica(seleccion.substring(0, seleccion.length() - 1), ordenamiento);
                        //Llamando a la funcion pasando por parametro: seleccion, se deberá obtener el formato correcto del encabezado para el reporte Excell
                        String encabezado = generaEncabezado(seleccion);
                        if (recursosEncontrados != null) {
                            if (recursosEncontrados.size() <= 0) {
                                mensaje = new Vector();
                                mensaje.add("¡No se encontraron resultados!");
                            } else {
                                session.setAttribute("recursosEncontrados", recursosEncontrados);
                                session.setAttribute("encabezado", "ID RECURSO," + encabezado);
                                mensaje = new Vector();
                                mensaje.add("¡Reporte generado correctamente!");
                                mensaje.add("<br>");
                                mensaje.add("<input type=\"button\" onclick=\"window.location.href = 'DescargaArchivo?tc=recursoConEncabezado';\" value=\"Descargar\" name=\"exportar\" />");
                            }
                        } else {
                            mensaje = new Vector();
                            mensaje.add("¡Error en petición!");
                        }
                        session.setAttribute("msgErrores", mensaje);

                        enviado = true;
                        RequestDispatcher rd = request.getRequestDispatcher("Exportar.htm?descargar=true");
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
                System.out.println("Excepcion en Class: ControlRecurso " + e.getMessage() + "accion: " + aux);
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
