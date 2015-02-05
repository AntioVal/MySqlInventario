/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.util.Enumeration;
import beans.Usuario;
import static beans.Validador.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import javax.servlet.RequestDispatcher;
import static servicio.AccionesUsuario.*;

/**
 * Gestion de acciones del modulo Cuenta
 *
 * @author luis-valerio
 */
public class ControlCuenta extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        boolean enviado = false;
        String urlRespuesta = "index.htm";
        Usuario usuarioActual;;
        String ParametroAccion[] = null, varsRemove[] = {}, varsRemoves;
        String aux = new String("");

        HttpSession session = request.getSession(false);//Si existe regresa la session y no existe entonces regresa null
        if (session != null && session.getAttribute("usuarioActual") != null) {
            try {
                aux = request.getParameter("accion").toString().trim();
                usuarioActual = (Usuario) session.getAttribute("usuarioActual");
                System.out.println("Accion: " + aux);
                ParametroAccion = aux.split(":");
                System.out.println("Class: ControlCuenta, Accion= ** " + ParametroAccion[0] + " ** " + ParametroAccion[1]);
                int accion = Integer.parseInt(ParametroAccion[1]);

                if (usuarioActual != null) {
                    switch (accion) {
                        /**
                         * Obtener informacion de la cuenta actual
                         *
                         */
                        case 1: {
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
//                System.out.println("Obteniendo datos de la cuenta actual: " + usuarioActual); 
                            String[] datosCuenta = getDatosBasicosCuenta(usuarioActual);
                            if (datosCuenta != null) {
                                request.setAttribute("nombreP", datosCuenta[0]);
                                request.setAttribute("apellidoPP", datosCuenta[1]);
                                request.setAttribute("apellidoMP", datosCuenta[2]);
                                request.setAttribute("correo", datosCuenta[3]);
                                request.setAttribute("fechaCreacion", datosCuenta[4]);
                                request.setAttribute("visibilidad", "hidden");
                                enviado = true;
                                RequestDispatcher rd = request.getRequestDispatcher("Cuenta.htm");
                                rd.forward(request, response);
                            } else {
                                System.out.println("Error al obtener datos basicos de cuenta");
                                session.setAttribute("msgErrores", "Error al obtener datos basicos de cuenta");
                            }
//                urlRespuesta= "Cuenta.htm";
                            break;
                        }
                        /**
                         * Habilitar Campos para la Modificacion de la cuenta
                         * actual
                         *
                         */
                        case 2: {
//                System.out.println("Abriendo panel para modificacion de cuenta actual");
                            String[] datosCuenta = getDatosBasicosCuenta(usuarioActual);
                            varsRemoves = "";
                            varsRemove = varsRemoves.split("-");
                            if (datosCuenta != null) {
                                //Datos anteriores fijos
                                request.setAttribute("nombreP", datosCuenta[0]);
                                request.setAttribute("apellidoPP", datosCuenta[1]);
                                request.setAttribute("apellidoMP", datosCuenta[2]);
                                request.setAttribute("correo", datosCuenta[3]);
                                request.setAttribute("fechaCreacion", datosCuenta[4]);
                                request.setAttribute("visibilidad", "visible");
                                //Datos nuevos a ser insertados
                                request.setAttribute("mnombreU", usuarioActual.getNombreU());
                                request.setAttribute("mnombreP", datosCuenta[0]);
                                request.setAttribute("mapellidoPP", datosCuenta[1]);
                                request.setAttribute("mapellidoMP", datosCuenta[2]);
                                request.setAttribute("mcorreo", datosCuenta[3]);
                            } else {
                                System.out.println("Error al obtener datos basicos de cuenta");
                                session.setAttribute("msgErrores", "Error al obtener datos basicos de cuenta");
                            }
                            enviado = true;
                            RequestDispatcher rd = request.getRequestDispatcher("Cuenta.htm");
                            rd.forward(request, response);
//                urlRespuesta= "Cuenta.htm";
                            break;
                        }
                        /**
                         * Confirmacion de datos para la modificacion de datos
                         * de cuenta
                         *
                         */
                        case 3: {
                            varsRemoves = "accion-";
                            varsRemove = varsRemoves.split("-");
//                System.out.println("Confirmacion de datos para la modificacion de datos de cuenta");
                            String[] errores = new String[5];
                            String inombreU, inombreP, iapellidoPP, iapellidoMP, icorreo, iprivilegio;
                            inombreU = request.getParameter("inombreU");
                            inombreP = request.getParameter("inombreP");
                            iapellidoPP = request.getParameter("iapellidoPP");
                            iapellidoMP = request.getParameter("iapellidoMP");
//                iprivilegio = request.getParameter("iprivilegio");
                            iprivilegio = "Administrador";
                            icorreo = request.getParameter("icorreo");
                            boolean error = false, modificado = false;
                            //Comprobamos si los valores de los valores introducidos en los campos son correctos
                            errores = validarCamposCuenta(inombreU, inombreP, iapellidoPP, iapellidoMP, icorreo);
                            //Preguntamos si no hubo errores
                            if (errores == null) {
                                //no se encontraron errores en el formato de datos, entonces proseguimos con el proceso de modificacion
                                //si no cambia el nombre de usuario, entonces se realiza modificacion directa
                                if (!usuarioActual.getNombreU().equals(inombreU)) {
                                    //si cambia el nombre de usuario, entonces se verifica que el nuevo nombre de usuario no exista
                                    //preguntamos si el usuario existe
                                    error = verificaExistenciaCampo("Usuario", "nombreU", inombreU);
                                    if (error) {
                                        //El usuario ya existe, por lo que mandamos error
                                        session.setAttribute("msgErrores", "Error: el usuario ya existe");
                                    }
                                }
                                if (!error) {
                                    String rst = modificarDatosCuenta(usuarioActual.getNombreU(), inombreU, inombreP, iapellidoPP, iapellidoMP, iprivilegio, icorreo);
                                    if (rst == null) {
                                        modificado = true;
                                        session.setAttribute("msgErrores", "Usuario modificado");
                                        session.removeAttribute("usuarioActual");
                                        usuarioActual = new Usuario(inombreU);
                                        usuarioActual.setPrivilegio(iprivilegio);
                                        usuarioActual.setNombreCompleto(inombreP + " " + iapellidoPP);
                                        session.setAttribute("usuarioActual", usuarioActual);
                                    } else {
                                        session.setAttribute("msgErrores", "Error al actualizar: " + rst);
                                    }
                                }
                            } else {
                                session.setAttribute("errores", errores);
                                session.setAttribute("msgErrores", "Por favor verifica la información");
                            }
                            String[] datosCuenta = getDatosBasicosCuenta(usuarioActual);
                            //Datos anteriores fijos
                            request.setAttribute("nombreP", datosCuenta[0]);
                            request.setAttribute("apellidoPP", datosCuenta[1]);
                            request.setAttribute("apellidoMP", datosCuenta[2]);
                            request.setAttribute("correo", datosCuenta[3]);
                            request.setAttribute("fechaCreacion", datosCuenta[4]);
                            request.setAttribute("visibilidad", "hidden");
                            if (!modificado) {
                                //Datos nuevos a ser insertados
                                request.setAttribute("mnombreU", inombreU);
                                request.setAttribute("mnombreP", inombreP);
                                request.setAttribute("mapellidoPP", iapellidoPP);
                                request.setAttribute("mapellidoMP", iapellidoMP);
                                request.setAttribute("mcorreo", icorreo);
                                request.setAttribute("visibilidad", "visible");
                            }
                            enviado = true;
                            RequestDispatcher rd = request.getRequestDispatcher("Cuenta.htm");
                            rd.forward(request, response);
                            urlRespuesta = "Cuenta.htm";
                            break;
                        }
                        case 4: {
                            /**
                             * Cancelacion de modificacion de cuenta
                             */
//                System.out.println("Obteniendo datos de la cuenta actual"); 
                            String[] datosCuenta = getDatosBasicosCuenta(usuarioActual);
                            varsRemoves = "accion-";
                            varsRemove = varsRemoves.split("-");
                            if (datosCuenta != null) {
                                request.setAttribute("nombreP", datosCuenta[0]);
                                request.setAttribute("apellidoPP", datosCuenta[1]);
                                request.setAttribute("apellidoMP", datosCuenta[2]);
                                request.setAttribute("correo", datosCuenta[3]);
                                request.setAttribute("fechaCreacion", datosCuenta[4]);
                                request.setAttribute("visibilidad", "hidden");
                                enviado = true;
                                RequestDispatcher rd = request.getRequestDispatcher("Cuenta.htm");
                                rd.forward(request, response);
                            } else {
                                System.out.println("Error al obtener datos basicos de cuenta");
                                session.setAttribute("msgErrores", "Error al obtener datos basicos de cuenta");
                            }
                            break;
                        }

                        case 5: {
                            /**
                             * En este caso, se abre el formulario para cambio
                             * de contraseña
                             *
                             */
//                System.out.println("Hablilitación del panel de Modificacion de contraseña de usuario");
                            request.setAttribute("passwordChange", "passwordChange");
                            request.setAttribute("nombreU", request.getParameter("inombreU"));
                            enviado = true;
                            RequestDispatcher rd = request.getRequestDispatcher("CambiaPassword.htm");
                            rd.forward(request, response);
                            break;
                        }
                        case 6: {
                            /**
                             * Confirmacion del cambio de contraseña para la
                             * cuenta actual, GuardarModificarPassword:6
                             */
//                System.out.println("Confirmación de modificación de la contraseña de usuario actual");
                            request.setAttribute("passwordChange", "passwordChange");
                            String nombreU = request.getParameter("nombreU");
                            String mensaje = "", url = "CambiaPassword.htm";
                            if (CompruebaUsuario(nombreU, request.getParameter("oldPass")) != null) {
                                String modPass = modificaPassword(nombreU, request.getParameter("newPass1"));
                                if (modPass == null) {
                                    mensaje = "Contraseña modificada exitosamente";
                                    url = "Menu.htm";
                                } else {
                                    mensaje = "Error en la Base de Datos";
                                }
                            } else {
                                mensaje = "Contraseña actual inválida, favor de verificar.";
                            }
                            enviado = true;
                            session.setAttribute("msgErrores", mensaje);
                            request.setAttribute("nombreU", nombreU);
                            RequestDispatcher rd = request.getRequestDispatcher(url);
                            rd.forward(request, response);

                        }
                        case 7: {
                            /**
                             * Confirmacion del cambio de contraseña para la
                             * cuenta actual, GuardarModificarPassword:6
                             */
//                System.out.println("Mostrar panel de busqueda de cuentas de usuario");
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
                            variables += "idRecursosSelect-recSelect-";
                        //*Nueva factura
                        variables += "folio-fechaAdquisicion-montoSinIVA-montoConIVA-articulos-observaciones";                             
                            String[] atrs = variables.split("-");

                            synchronized (session) {
                                //Se remueven los objetos especificados de la sesion
                                this.remueveAtributos(atrs, session);
                            }
                            request.setAttribute("busqueda", "cuenta");
                            enviado = true;
                            RequestDispatcher rd = request.getRequestDispatcher("Buscar.htm");
                            rd.forward(request, response);
                            break;
                        }
                        case 8: {
                            /**
                             * Confirmacion del cambio de contraseña para la
                             * cuenta actual, GuardarModificarPassword:6
                             */
//                System.out.println("Busqueda de cuentas de usuario");
                            request.setAttribute("busqueda", "cuenta");
                            request.setAttribute("resultado", "cuenta");

                            enviado = true;
                            RequestDispatcher rd = request.getRequestDispatcher("Buscar.htm");
                            rd.forward(request, response);
                            break;
                        }
                        case 9: {
                            /**
                             * Confirmacion del cambio de contraseña para la
                             * cuenta actual, GuardarModificarPassword:6
                             */
                            Vector resultBusqueda = null;
//                        System.out.println("Realizando petición de búsqueda por filtro Input");
                            request.setAttribute("busqueda", "cuenta");
                            String usuario = request.getParameter("usuario");
                            String nombre = request.getParameter("nombre");
                            String apellidoPaterno = request.getParameter("apellidoPaterno");
                            String apellidoMaterno = request.getParameter("apellidoMaterno");

                            resultBusqueda = filtraCuentasInput(usuario, nombre, apellidoPaterno, apellidoMaterno, usuarioActual.getPrivilegio());
                            if (resultBusqueda != null && !resultBusqueda.isEmpty()) {
                                request.setAttribute("resultBusqueda", resultBusqueda);
                                request.setAttribute("resultado", "cuenta");
                            }
                            request.setAttribute("usuario", usuario);
                            request.setAttribute("nombre", nombre);
                            request.setAttribute("apellidoPaterno", apellidoPaterno);
                            request.setAttribute("apellidoMaterno", apellidoMaterno);

                            enviado = true;
                            RequestDispatcher rd = request.getRequestDispatcher("Buscar.htm");
                            rd.forward(request, response);
                            break;
                        }
                        case 10: {
                            /**
                             * Confirmacion del cambio de contraseña para la
                             * cuenta actual, GuardarModificarPassword:6
                             */
//                        System.out.println("Obtener datos de cuenta especifica");
                            request.setAttribute("modificacion", "cuenta");
                            String idUsuario = request.getParameter("idUsuario");

                            Map detalleUsuario = getDetallesCuenta(idUsuario);
                            request.setAttribute("detalleUsuario", detalleUsuario);

                            enviado = true;
                            urlRespuesta = "Modificar.htm";
                            RequestDispatcher rd = request.getRequestDispatcher("Modificar.htm");
                            rd.forward(request, response);
                            break;

                        }

                        case 11: {
                            /**
                             * Este case realiza la modificacion del tipo de
                             * recurso solicitado
                             */
                            Vector mensaje = null;
                            request.setAttribute("modificacion", "cuenta");
                            Map detalleUsuario = new HashMap();
                            detalleUsuario.put("idUsuario", request.getParameter("idUsuario"));
                            detalleUsuario.put("nombreU", request.getParameter("nombreU"));
                            detalleUsuario.put("nombreP", request.getParameter("nombreP"));
                            detalleUsuario.put("apellidoPP", request.getParameter("apellidoPP"));
                            detalleUsuario.put("apellidoMP", request.getParameter("apellidoMP"));
                            detalleUsuario.put("privilegio", request.getParameter("privilegio"));
                            detalleUsuario.put("fechaCreacion", request.getParameter("fechaCreacion"));
                            detalleUsuario.put("correo", request.getParameter("correo"));
                            detalleUsuario.put("estado", request.getParameter("estado"));
                            try {
                                boolean actualizado = modificaDatosTipoRecurso(detalleUsuario);
                                if (actualizado) {
                                    mensaje = new Vector();
                                    mensaje.add("¡Datos actualizados correctamente!");
                                } else {
                                    mensaje = new Vector();
                                    mensaje.add("¡Error al actualizar la información!");
                                }
                                request.setAttribute("detalleUsuario", detalleUsuario);
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

                        case 12: {
                            /**
                             * Este case realiza la modificacion del tipo de
                             * recurso solicitado
                             */
                            Vector mensaje = null;
                            Map detalleUsuario = new HashMap();
                            try {
                                if (!validaNombreU(request.getParameter("nombreU"))) {
                                    mensaje = (mensaje == null) ? (new Vector()) : (mensaje);
                                    mensaje.add("- El nombre de Usuario no cumple la nomenclatura especifica");
                                }
                                if (!validaNombres(request.getParameter("nombreP"))) {
                                    mensaje = (mensaje == null) ? (new Vector()) : (mensaje);
                                    mensaje.add("- El nombre no puede contener caracteres especiales ni numeros");
                                }
                                if (!validaNombres(request.getParameter("apellidoPP"))) {
                                    mensaje = (mensaje == null) ? (new Vector()) : (mensaje);
                                    mensaje.add("- El apellido paterno no puede contener caracteres especiales ni numeros");
                                }
                                if (!validaNombres(request.getParameter("apellidoMP"))) {
                                    mensaje = (mensaje == null) ? (new Vector()) : (mensaje);
                                    mensaje.add("- El apellido materno no puede contener caracteres especiales ni numeros");
                                }
                                if (!validaCorreo(request.getParameter("correo"))) {
                                    mensaje = (mensaje == null) ? (new Vector()) : (mensaje);
                                    mensaje.add("- El correo no cumple con las especificaciones de nomenclatura");
                                }
                                detalleUsuario.put("nombreU", request.getParameter("nombreU"));
                                detalleUsuario.put("nombreP", request.getParameter("nombreP"));
                                detalleUsuario.put("apellidoPP", request.getParameter("apellidoPP"));
                                detalleUsuario.put("apellidoMP", request.getParameter("apellidoMP"));
                                detalleUsuario.put("correo", request.getParameter("correo"));
                                detalleUsuario.put("privilegio", request.getParameter("privilegio"));
                                detalleUsuario.put("estado", request.getParameter("estado"));

                                if (mensaje == null) {
                                    String contrasena = beans.Validador.creaPassword();
                                    detalleUsuario.put("contrasena", contrasena);

                                    boolean existe = existeUnicoUsuario(detalleUsuario.get("nombreU").toString());
                                    if (!existe) {
                                        existe = insertaNuevoUsuario(detalleUsuario);
                                        if (existe) {
                                            mensaje = new Vector();
                                            mensaje.add("¡Cuenta de usuario creada correctamente!");
                                            mensaje.add("La contraseña es:");
                                            mensaje.add("<titulo>" + detalleUsuario.get("contrasena") + "</titulo>");
                                        } else {
                                            mensaje = new Vector();
                                            mensaje.add("¡Error al insertar cuenta de usuario!");
                                        }
                                    } else {
                                        mensaje = new Vector();
                                        mensaje.add("¡El nombre de usuario ya existe en la Base de Datos!");
                                    }
                                }
                                request.setAttribute("detalleUsuario", detalleUsuario);
                                session.setAttribute("msgErrores", mensaje);

                            } catch (Exception e) {
                                System.out.println("Exception:" + e.getMessage());
                            }
                            enviado = true;
                            urlRespuesta = "Modificar.htm";
                            RequestDispatcher rd = request.getRequestDispatcher("Nuevo.htm?nuevo=cuenta");
                            rd.forward(request, response);
                            break;
                        }

                        default: {
                            varsRemoves = "accion-nombreP-apellidoPP-apellidoMP-correo-fechaCreacion-visibilidad-errores";
                        }
                    }
                }
                synchronized (session) {
                    //Se remueven los objetos especificados de la sesion
                    this.remueveAtributos(varsRemove, session);
                }

            } catch (Exception e) {
                System.out.println("Excepcion en Class: ControlCuenta " + e.getMessage() + "accion: " + aux);
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
//            System.out.println("Eliminando atributos de sesion...");
        for (int i = 0; i < nombresAtributos.length; i++) {
            session.removeAttribute(nombresAtributos[i]);
        }
    }
}
