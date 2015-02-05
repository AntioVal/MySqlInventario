<%-- 
    Document   : Modificar
    Created on : 22/01/2014, 12:20:48 PM
    Author     : luis-valerio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<!DOCTYPE html>
<c:choose>
    <c:when test="${sessionScope.usuarioActual != null && sessionScope.usuarioActual.nombreU != null}">

        <html>
            <head>
                <link rel="stylesheet" type="text/css" href="css/Modificar.css">
                <!--<link rel="stylesheet" type="text/css" href="css/Buscar.css">-->
                <jsp:include page="VentanasModales.jsp" flush="true" />
                <!--<link rel="stylesheet" type="text/css" href="css/VentanasModales.css">-->
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <title>Modificar</title>
                <!-- Especificamos el Logo en pequeño -->
                <link rel="shortcut icon" href="Imagen/gp_logo.png">
            </head>
            <body>
                <%--<jsp:include page="Home.jsp" flush="true" />--%>
                <jsp:include page="Menu.jsp" flush="true" />
                <script async  language="javascript" type="text/javascript" src="scripts/login.js"></script>
                <c:if test="${modificacion != null && modificacion== 'principal'}">
                    <br><br>
                    <table align="center" width="50%" class='tablaModificar'>
                        <tr><td colspan="6">
                                <div class="TituloModificar" align='center'> 
                                    Modificaci&oacute;n <br><br>
                                </div>
                            </td></tr>
                        <tr><td colspan="6"></td></tr>
                        <tr align='center'>
                            <td colspan="2">
                                <form action="ControlFactura" method="POST">
                                    <input type="hidden" name="accion" value="busquedaFactura:2"/>
                                    <button class="buttonModificar">
                                        <img src="Imagen/facturaIcon.png" width="90" height="90"> </img>
                                        <br><br>
                                        Factura
                                    </button> 
                                </form>
                            </td>
                            <td colspan="2">
                                <form action="ControlRecurso" method="POST">
                                    <input type="hidden" name="accionRec" value="busquedaRecurso:2"/>
                                    <button class="buttonModificar">
                                        <img src="Imagen/recursoIcon.png" width="90" height="90"> </img>
                                        <br><br>
                                        Recurso
                                    </button> 
                                </form>   
                            </td>
                            <td colspan="2">
                                <form action="ControlTipo">
                                    <input type="hidden" name="accion" value="busquedaTipoRecurso:1"/>
                                    <button class="buttonModificar">
                                        <img src="Imagen/tipoRecursoIcon.png" width="90" height="90"> </img>
                                        <br><br>
                                        Tipo Recurso
                                    </button> 
                                </form>
                            </td>
                            <td colspan="2">
                                <form action="ControlUbicacion">
                                    <input type="hidden" name="accion" value="busquedaUbicacion:1"/>
                                    <button class="buttonModificar">
                                        <img src="Imagen/oficinaIcon.png" width="90" height="90"> </img>
                                        <br><br>
                                        Oficina
                                    </button> 
                                </form>
                            </td>                             
                        </tr>
                        <tr><td colspan="6">&nbsp;</td></tr>
                        <tr align="center"> 
                            <td colspan="1">&nbsp;</td>
                            <td colspan="2">
                                <form action="ControlProveedor">
                                    <input type="hidden" name="accion" value="busquedaProveedor:1"/>
                                    <button class="buttonModificar">
                                        <img src="Imagen/proveedorIcon.png" width="90" height="90"> </img>
                                        <br><br>
                                        Proveedor
                                    </button> 
                                </form>
                            </td>
                            <c:if test="${sessionScope.usuarioActual != null && sessionScope.usuarioActual.privilegio == 'Estandar'}">
                                <td colspan="2">&nbsp;</td>
                            </c:if>
                            <c:if test="${sessionScope.usuarioActual != null && sessionScope.usuarioActual.privilegio == 'Administrador' || sessionScope.usuarioActual.privilegio == 'Super'}">
                                <td colspan="2">
                                    <form action="ControlCuenta">
                                        <input type="hidden" name="accion" value="busquedaCuentas:7"/>
                                        <button class="buttonModificar">
                                            <img src="Imagen/usuarioIcon.png" width="90" height="90"> </img>
                                            <br><br>
                                            Cuenta
                                        </button> 
                                    </form>
                                </td>
                            </c:if>
                            <td colspan="2">
                                <form action="ControlEstado">
                                    <input type="hidden" name="accion" value="busquedaEstatus:1"/>
                                    <button class="buttonModificar">
                                        <img src="Imagen/estatusIcon.png" width="90" height="90"> </img>
                                        <br><br>
                                        Estatus
                                    </button> 
                                </form>
                            </td>   
                            <td colspan="1">&nbsp;</td>
                        </tr>
                        <tr><td colspan="6">&nbsp;</td></tr>
                    </table> 
                </c:if>
                <c:if test="${modificacion != null && modificacion== 'factura' && detalleFactura!=null}">
                    <br>
                    <table align="center" width="60%" class='cuadroContenido'>
                        <tr><td colspan="6">&nbsp;</td></tr>
                        <tr><td colspan="6" align='center'>
                        <titulo>Detalles de factura</titulo>
                    </td></tr>    
                <tr><td colspan="6">&nbsp;</td></tr>
                <c:if test="${sessionScope.msgErrores != null}">
                    <tr align="center"><td colspan="6">
                            <div class="msgErr">
                                <c:forEach items='${sessionScope.msgErrores}' var='error'>
                                    ${error} <br>
                                </c:forEach>
                            </div>
                            <c:remove var="msgErrores" scope="session" />
                        </td></tr> 
                    <tr><td colspan="6">&nbsp;</td></tr>                    
                </c:if>                    
                <tr align="center"> 
                    <td><form name="datosFactura" action="ControlFactura" method="POST">
                            <table>
                                <tbody>
                                    <!--                            <form name="datosFactura" action="ControlFactura" method="POST">-->
                                <input type="hidden" name="accion"/>
                                <input type="hidden" name="idFactura" value="${requestScope.idFactura}"/>
                                <tr>
                                    <td colspan="6" rowspan="1"></td>
                                </tr>
                                <tr>
                                    <td>Folio</td>
                                    <td><input type="text" size="10" name="Folio" disabled="true" value="${detalleFactura.folio}"/></td>
                                    <td>Fecha de adquisici&oacute;n</td>
                                    <td><input type="text" size="10" name="FechaAdquisicion" disabled="true" value="${detalleFactura.fechaAdquisicion}"/></td>
                                    <td>Art&iacute;culos</td>
                                    <td><input type="text" size="10" name="noArticulos" disabled="true" value="${detalleFactura.articulos}"/></td>                                                              
                                </tr>
                                <tr>
                                    <td>IVA</td>
                                    <td><input type="text" size="10" name="iva" disabled="true" value="${detalleFactura.iva}"/></td>                                  
                                    <td>Monto sin iva</td>
                                    <td><input type="text"  size="10" name="montoSinIva" disabled="true" value="${detalleFactura.montoSinIVA}"/></td>
                                    <td>Monto total</td>
                                    <td><input type="text" size="10" name="montoTotal" disabled="true" value="${detalleFactura.montoConIVA}"/></td>
                                </tr>
                                <tr>                                    
                                    <td>Comentarios</td>
                                    <td><textarea disabled="true" name="Observaciones">${detalleFactura.observacionesFactura}</textarea></td>
                                    <td colspan="2">&nbsp;</td>
                                    <c:if test="${sessionScope.usuarioActual != null && sessionScope.usuarioActual.privilegio == 'Administrador' || sessionScope.usuarioActual.privilegio == 'Super'}">
                                        <td align="center"><input type="button" value="Modificar" onClick="return habilitar(datosFactura);" name="botonHabilita"/></td>
                                        <td align="center"><input type="button"  value="Guardar" onClick="return guardarModificacion(datosFactura, 'modificarFactura:5');" name="botonGuarda"/></td>
                                        </c:if>
                                </tr>
                                <tr><td>&nbsp;</td></tr>
                                <tr><td>&nbsp;</td></tr>
                                </tbody>
                            </table>
                        </form>
                    </td></tr>
                    <%-- Mostrar detalles del proveedor --%>
                <tr><td colspan="6" align="center">
                <titulo>Proveedor</titulo>
            </td></tr>  
        <tr><td colspan="6">&nbsp;</td></tr>


        <tr align="center"> 
            <td>
                <form name="datosProveedor" action="ControlProveedor" method="POST">
                    <table>
                        <tbody>
                        <input type="hidden" name="accion"/>
                        <input type="hidden" name="idFactura" value="${detalleFactura.idFactura}"/>
                        <input type="hidden" name="razonSocial" value="${detalleProveedor.razonSocial}"/>
                        <input type="hidden" name="idProveedor" value="${detalleProveedor.idProveedor}"/>
                        <tr>
                            <td>Razon social</td>
                            <%--<td><input type="text" class="textInput" size="60" name="razonSocial" disabled="true" value="${detalleProveedor.razonSocial}"/></td>--%>
                            <td><select name="seleccionar" width="100px" onchange="return enviar(datosProveedor, 'getDatosProveedor:4');" disabled="true">
                                    <c:forEach items="${provedores}" var="proveedor">
                                        <c:choose>
                                            <c:when test="${proveedor eq detalleProveedor.razonSocial}">
                                                <option selected>${proveedor}</option>
                                            </c:when>   
                                            <c:otherwise>
                                                <option>${proveedor}</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                        <tr>                                    
                            <td>Correo de contacto</td>
                            <td><input type="text" width="100px" name="correoContacto" disabled="true" value="${detalleProveedor.correoContacto}"/></td>
                        </tr>
                        <tr>
                            <td>Tel&eacute;fono de contacto</td>
                            <td><input type="text" width="100px" name="telefonoContacto" disabled="true" value="${detalleProveedor.telefonoContacto}"/></td>                                   
                        </tr>
                        <c:if test="${sessionScope.usuarioActual != null && sessionScope.usuarioActual.privilegio == 'Administrador' || sessionScope.usuarioActual.privilegio == 'Super'}">
                            <tr>
                                <td align="center"><input type="button" value="Cambiar" onClick="return seleccionarOtro(datosProveedor);" name="botonHabilita"/></td>
                                <td align="center"><input type="button"  value="Guardar" onClick="return guardarModificacion(datosProveedor, 'cambiarProveedorDeFactura:6', 'ControlFactura');" name="botonGuarda"/></td>                              
                            </tr>
                        </c:if>
                        </tbody>
                    </table>
                </form>

            </td></tr>

        <tr align="right"><td><input type="button" value="Ver detalles del proveedor" onClick="return enviar(datosProveedor, 'verDetallesProveedor:4');" name="botonGuarda"/></td></tr>                                                                                

    </table> 
    <br><br>
    <table align="center" width="60%" class='cuadroContenido'>
        <tr><td colspan="6">&nbsp;</td></tr>
        <tr align="center"><td>
                <table>
                    <tr align="center">
                        <td colspan="6"> <titulo>Recursos contenidos en la factura</titulo> </td>
        </tr> 
        <tr><td>&nbsp;</td></tr>
        <c:if test='${detallesRecursos.size() gt 0}'>
            <tr>
                <td colspan="6">
                    <table class="showResult" width="100%">
                        <thead>
                            <tr>
                                <th>Tipo</th>
                                <th>Marca</th>
                                <th>Oficina</th>
                                <th>Serie </th>
                                <th>Costo</th>
                                <th>Empleado actual</th>
                                <th>Descripci&oacute;n</th>
                            </tr>
                        </thead>                                                 
                        <tbody>
                            <c:forEach items='${detallesRecursos}' var='registro' varStatus='indice'>
                                <tr>
                                    <c:forEach items='${registro}' varStatus='indiceInterno' var='campo'>
                                        <c:if test='${indiceInterno.index ne 0}'>
                                            <td> ${campo}  </td>
                                        </c:if>
                                    </c:forEach>
                                    <td align="center"><form action="ControlRecurso" method="POST">
                                            <input type="hidden" name="accionRec" value="mostrarDetallesRecurso:1"/>
                                            <input type="hidden" name="idRecurso" value="${registro['0']}"/>
                                            <input type="hidden" name="noSerie" value="${registro['4']}"/>
                                            <input type="submit" value="Ver"/>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </td>
            </tr>
        </c:if>
        <tr align="center">
            <td colspan="6" rowspan="1"></td>
        </tr>
    </tbody>
</table>
</td></tr>                                
</table> 
</c:if>
<%-- Mostrar detalles del proveedor con opcion de modificacion --%>
<c:if test="${modificacion != null && modificacion== 'proveedor' && detalleProveedor!=null}">
    <br><br>

    <table align="center" width="60%" class='cuadroContenido'>
        <tr><td colspan="6" align="center">
        <titulo> Detalles del proveedor </titulo>
    </td></tr>      
    <c:if test="${sessionScope.msgErrores != null}">
    <tr align="center"><td colspan="6">
            <div class="msgErr">
                <c:forEach items='${sessionScope.msgErrores}' var='error'>
                    ${error} <br>
                </c:forEach>
            </div>
        </td></tr> 
    <tr><td colspan="6">&nbsp;</td></tr>  
    <c:remove var="msgErrores" scope="session" />
</c:if>                    
<%-- Formulario detalles del proveedor --%>                   
<tr align="center"> 
    <td><form name="datosProveedor" action="ControlProveedor" method="POST">
            <table>
                <tbody>
                <input type="hidden" name="accion"/>
                <input type="hidden" name="idProveedor" value="${detalleProveedor.idProveedor}"/>
                <tr>
                    <td>Razon social</td>
                    <td><input type="text" size="60" name="razonSocial" disabled="true" value="${detalleProveedor.razonSocial}"/></td>                                                                
                </tr>
                <tr>                                    
                    <td>RFC</td>
                    <td><input type="text" size="60" name="rfc" disabled="true" value="${detalleProveedor.rfc}"/></td>
                </tr>
                <tr>                                    
                    <td>Domicilio fiscal</td>
                    <td><input type="text" size="60" name="domicilioFiscal" disabled="true" value="${detalleProveedor.domicilioFiscal}"/></td>
                </tr>
                <tr>
                    <td>Tel&eacute;fono de contacto</td>
                    <td><input type="text" size="60" name="telefonoContacto" disabled="true" value="${detalleProveedor.telefonoContacto}"/></td>                                   
                </tr>                              
                <tr>                                    
                    <td>Correo de contacto</td>
                    <td><input type="text" size="60" name="correoContacto" disabled="true" value="${detalleProveedor.correoContacto}"/></td>
                </tr>
                <tr><td>&nbsp;</td></tr>
                <c:if test="${sessionScope.usuarioActual != null && sessionScope.usuarioActual.privilegio == 'Administrador' || sessionScope.usuarioActual.privilegio == 'Super'}">
                    <tr>                                    
                        <td>&nbsp; &nbsp; &nbsp;</td>
                        <td align="center"><input type="button" value="Modificar" onClick="return habilitar(datosProveedor);" name="botonHabilita"/>
                            &nbsp;<input type="button" value="Guardar" onClick="return confirmaModificacion(datosProveedor, 'modificarProveedor:6');" name="botonGuarda"/></td>
                    </tr>                              
                    <tr><td>&nbsp;</td></tr>
                </c:if>
                </tbody>
            </table>
        </form>
    </td></tr>
</table> 
<br><br>
</c:if>

<%-- Mostrar detalles de empleado y posibilidad para modificar --%>
<c:if test="${modificacion != null && modificacion== 'empleado'}">
    <br><br>

    <table align="center" width="60%" class='cuadroContenido'>
        <tr><td colspan="6" align="center">
        <titulo> Datos del empleado </titulo>
    </td></tr>      
    <c:if test="${sessionScope.msgErrores != null}">
    <tr align="center"><td colspan="6">
            <div class="msgErr">
                <c:forEach items='${sessionScope.msgErrores}' var='error'>
                    ${error} <br>
                </c:forEach>
            </div>
            <c:remove var="msgErrores" scope="session" />
        </td></tr> 
    <tr><td colspan="6">&nbsp;</td></tr>                    
</c:if>                    
<%-- Formulario detalles del empleado --%>                   
<tr align="center"> 
    <td><form name="datosEmpleado" action="ControlRecurso" method="POST">
            <table>
                <tbody>
                <input type="hidden" name="accionRec"/>
                <input type="hidden" name="idEmpleadoAnterior" value="${idEmpleadoAnterior}"/>
                <tr>
                    <td>N&uacute;mero de empleado</td>
                    <td><input type="text" size="30" name="idEmpleado" disabled="true" value="${idEmpleado}"/></td>                                                                
                </tr>
                <tr>                                    
                    <td>Nombre completo</td>
                    <td><input type="text" size="30" name="nombreCompleto" disabled="true" value="${nombreCompleto}"/></td>
                </tr>
                <tr><td>&nbsp;</td></tr>
                <c:if test="${sessionScope.usuarioActual != null && sessionScope.usuarioActual.privilegio == 'Administrador' || sessionScope.usuarioActual.privilegio == 'Super'}">
                    <tr>                                    
                        <td>&nbsp; &nbsp; &nbsp;</td>
                        <td align="center"><input type="button" value="Modificar" onClick="return habilitar(datosEmpleado);" name="botonHabilita"/>
                            &nbsp;<input type="button" value="Guardar" onClick="return confirmaModificacionRec(datosEmpleado, 'modificarEmpleado:14');" name="botonGuarda"/></td>
                    </tr>                              
                    <tr><td>&nbsp;</td></tr>
                </c:if>
                </tbody>
            </table>
        </form>
    </td></tr>
</table> 
<br><br>
</c:if>    


<%-- Mostrar detalles de liencia y posibilidad para modificar --%>
<c:if test="${modificacion != null && modificacion== 'licencia'}">
    <br><br>

    <table align="center" width="60%" class='cuadroContenido'>
        <tr><td colspan="6" align="center">
        <titulo> Datos de licencia </titulo>
    </td></tr>      
    <c:if test="${sessionScope.msgErrores != null}">
    <tr align="center"><td colspan="6">
            <div class="msgErr">
                <c:forEach items='${sessionScope.msgErrores}' var='error'>
                    ${error} <br>
                </c:forEach>
            </div>
            <c:remove var="msgErrores" scope="session" />
        </td></tr> 
    <tr><td colspan="6">&nbsp;</td></tr>                    
</c:if>                    
<%-- Formulario detalles del empleado --%>                   
<tr align="center"> 
    <td><form name="datosLicencia" action="ControlRecurso" method="POST">
            <table>
                <tbody>
                <input type="hidden" name="accionRec"/>
                <tr>
                    <td>N&uacute;mero de licencia</td>
                    <td align="left" style="font-weight: bold;">&nbsp;<c:out value='${idDatosLicencia}'/></td>   
                <input type="hidden" name="idDatosLicencia" value="${idDatosLicencia}"/>
                </tr>
                <tr>                                    
                    <td>Tipo de licencia</td>
                    <td><input type="text" size="30" name="nombre" disabled="true" value="${nombre}"/></td>
                </tr>
                <tr>
                    <td>Clave de licencia</td>
                    <td><input type="text" size="30" name="clave" disabled="true" value="${clave}"/></td>                                                                
                </tr>
                <tr>                                    
                    <td>Observaciones</td>
                    <td><input type="text" size="30" name="observaciones" disabled="true" value="${observaciones}"/></td>
                </tr>
                <tr><td>&nbsp;</td></tr>
                <c:if test="${sessionScope.usuarioActual != null && sessionScope.usuarioActual.privilegio == 'Administrador' || sessionScope.usuarioActual.privilegio == 'Super'}">
                    <tr>                                    
                        <td>&nbsp; &nbsp; &nbsp;</td>
                        <td align="center"><input type="button" value="Modificar" onClick="return habilitar(datosLicencia);" name="botonHabilita"/>
                            &nbsp;<input type="button" value="Guardar" onClick="return confirmaModificacionRec(datosLicencia, 'modificarEmpleado:17');" name="botonGuarda"/></td>
                    </tr>                              
                    <tr><td>&nbsp;</td></tr>
                </c:if>
                </tbody>
            </table>
        </form>
    </td></tr>
</table> 
<br><br>
</c:if>    

<%-- Mostrar detalles de la ubicacion del recurso con opcion de modificacion --%>
<c:if test="${modificacion != null && modificacion== 'ubicacionRecurso'}">
    <br><br>

    <table align="center" width="60%" class='cuadroContenido'>
        <tr><td colspan="6" align="center">
        <titulo> Detalles de ubicaci&oacute;n de recurso </titulo>
    </td></tr>      
    <c:if test="${sessionScope.msgErrores != null}">
    <tr align="center"><td colspan="6">
            <div class="msgErr">
                <c:forEach items='${sessionScope.msgErrores}' var='error'>
                    ${error} <br>
                </c:forEach>
            </div>
            <c:remove var="msgErrores" scope="session" />
        </td></tr> 
    <tr><td colspan="6">&nbsp;</td></tr>                    
</c:if>                    
<%-- Formulario detalles del proveedor --%>                   
<tr align="center"> 
    <td><form name="datosUbicacionRecurso" action="ControlUbicacion" method="POST">
            <table>
                <tbody>
                <input type="hidden" name="accion"/>
                <input type="hidden" name="idUbicacion" value="${ubicacionRecurso.idUbicacion}"/>
                <tr>
                    <td>Oficina</td>
                    <td><input type="text" size="30" name="oficina" disabled="true" value="${ubicacionRecurso.oficina}"/></td>                                                                
                </tr>
                <tr>                                    
                    <td>&Aacute;rea</td>
                    <td><input type="text" size="30" name="area" disabled="true" value="${ubicacionRecurso.area}"/></td>
                </tr>
                <tr><td>&nbsp;</td></tr>
                <c:if test="${sessionScope.usuarioActual != null && sessionScope.usuarioActual.privilegio == 'Administrador' || sessionScope.usuarioActual.privilegio == 'Super'}">
                    <tr>                                    
                        <td>&nbsp; &nbsp; &nbsp;</td>
                        <td align="center"><input type="button"  value="Modificar" onClick="return habilitar(datosUbicacionRecurso);" name="botonHabilita"/>
                            &nbsp;<input type="button" value="Guardar" onClick="return confirmaModificacion(datosUbicacionRecurso, 'modificarUbicacionRecurso:6');" name="botonGuarda"/></td>
                    </tr>                              
                    <tr><td>&nbsp;</td></tr>
                </c:if>
                </tbody>
            </table>
        </form>
    </td></tr>
</table> 
<br><br>
</c:if>
<%-- Mostrar detalles del tipo de recurso seleccionado --%>
<c:if test="${modificacion != null && modificacion== 'tipoRecurso'}">

    <table align="center" width="60%" class="cuadroContenido">
        <tr><td colspan="6" align="center">
        <titulo> Detalles de tipo de producto</titulo>
    </td></tr> 
<tr><td colspan="6">&nbsp;</td></tr>
<c:if test="${sessionScope.msgErrores != null}">
    <tr align="center"><td colspan="6">
            <div class="msgErr">
                <c:forEach items='${sessionScope.msgErrores}' var='error'>
                    ${error} <br>
                </c:forEach>
            </div>
            <c:remove var="msgErrores" scope="session" />
        </td></tr> 
    <tr><td colspan="6">&nbsp;</td></tr>                    
</c:if>                    
<%-- Formulario detalles del tipo de recurso --%>                   
<tr align="center"> 
    <td><form name="datosTipoRecurso" action="ControlTipo" method="POST">
            <table>
                <tbody>
                <input type="hidden" name="accion"/>
                <input type="hidden" name="idTipo" value="${detalleTipoRecurso.idTipo}"/>
                <tr>                                    
                    <td>Tipo</td>
                    <td><input type="text"size="30" name="tipo" disabled="true" value="${detalleTipoRecurso.tipo}"/></td>
                </tr>
                <tr>                                    
                    <td>Marca</td>
                    <td><input type="text" size="30" name="marca" disabled="true" value="${detalleTipoRecurso.marca}"/></td>
                </tr>
                <tr>
                    <td>Modelo</td>
                    <td><input type="text" size="30" name="modelo" disabled="true" value="${detalleTipoRecurso.modelo}"/></td>                                   
                </tr>  
                <tr align="center">
                    <td align="left"> Descripci&oacute;n</td>
                    <td align="left"> 
                        <textarea disabled="true"  name="descripcion" rows="4" cols="17">${detalleTipoRecurso.descripcion}</textarea>  
                    </td>
                </tr>                  
                <tr><td>&nbsp;</td></tr>
                <c:if test="${sessionScope.usuarioActual != null && sessionScope.usuarioActual.privilegio == 'Administrador' || sessionScope.usuarioActual.privilegio == 'Super'}">
                    <tr>                                    
                        <td>&nbsp; &nbsp; &nbsp;</td>
                        <td align="center"><input type="button" value="Modificar" onClick="return habilitar(datosTipoRecurso);" name="botonHabilita"/>
                            &nbsp;<input type="button" value="Guardar" onClick="return confirmaModificacion(datosTipoRecurso, 'modificarTipoRecurso:6');" name="botonGuarda"/></td>
                    </tr>                              
                    <tr><td>&nbsp;</td></tr>
                </c:if>
                </tbody>
            </table>
        </form>
    </td></tr>
</table> 
<br><br>
</c:if>                   

<%-- Mostrar detalles de una cuenta de usuario --%>
<c:if test="${modificacion != null && modificacion== 'cuenta'}">
    <br><br>

    <table align="center" width="60%" class="cuadroContenido">
        <tr><td colspan="6" align="center">
        <titulo> 
            Detalles de cuenta de usuario
        </titulo>
    </td></tr>      
<tr><td>&nbsp;</td></tr>      
<c:if test="${sessionScope.msgErrores != null}">
    <tr align="center"><td colspan="6">
            <div class="msgErr">
                <c:forEach items='${sessionScope.msgErrores}' var='error'>
                    ${error} <br>
                </c:forEach>
            </div>
            <c:remove var="msgErrores" scope="session" />
        </td></tr> 
    <tr><td colspan="6">&nbsp;</td></tr>                    
</c:if>                    
<%-- Formulario detalles del tipo de recurso --%>                   
<tr align="center"> 
    <td><form name="datosUsuario" action="ControlCuenta" method="POST">
            <table>
                <tbody>
                <input type="hidden" name="accion"/>
                <input type="hidden" name="idUsuario" value="${detalleUsuario.idUsuario}"/>
                <tr>                                    
                    <td>Usuario</td>
                    <td><input type="text" size="30" name="nombreU" disabled="true" value="${detalleUsuario.nombreU}"/></td>
                </tr>
                <tr>
                    <td>Nombre</td>
                    <td><input type="text" size="30" name="nombreP" disabled="true" value="${detalleUsuario.nombreP}"/></td>                                   
                </tr>                              
                <tr>                                    
                    <td>Apellido Paterno</td>
                    <td><input type="text"  size="30" name="apellidoPP" disabled="true" value="${detalleUsuario.apellidoPP}"/></td>
                </tr>
                <tr>                                    
                    <td>Apellido Materno</td>
                    <td><input type="text" size="30" name="apellidoMP" disabled="true" value="${detalleUsuario.apellidoMP}"/></td>
                </tr>
                <tr>                                    
                    <td>Correo</td>
                    <td><input type="text" size="30" name="correo" disabled="true" value="${detalleUsuario.correo}"/></td>
                </tr>                
                <tr>
                    <td>Privilegio</td>
                    <td>
                        <select name="privilegio" disabled="true">
                            <option value="Estandar">Estandar</option>
                            <option value="Administrador">Administrador</option>
                            <option value="Super">Super</option>
                        </select>
                    </td> 
                </tr>                 
                <tr>
                    <td>Estatus</td>
                    <td>
                        <select type="select" name="estado" disabled="true">
                            <option value="A">Activo</option>
                            <option value="B">Bloqueado</option>
                        </select>
                    </td>                                   
                </tr> 
                <tr>                                    
                    <td>Fecha de creaci&oacute;n</td>
                    <td class="soloLectura">${detalleUsuario.fechaCreacion}</td>
                </tr>                
                <c:if test="${sessionScope.usuarioActual != null && sessionScope.usuarioActual.privilegio == 'Administrador' || sessionScope.usuarioActual.privilegio == 'Super'}">
                    <tr>                                    
                        <td>&nbsp; &nbsp; &nbsp;</td>
                        <td align="center"><input type="button" value="Modificar" onClick="return habilitar(datosUsuario);" name="botonHabilita"/>
                            &nbsp;<input type="button" value="Guardar" onClick="return confirmaModificacion(datosUsuario, 'modificarDatosCuenta:11');" name="botonGuarda"/></td>
                    </tr>                              
                    <tr><td>&nbsp;</td></tr>
                </c:if>
                </tbody>
            </table>
        </form>
    </td></tr>
</table> 
<br><br>
</c:if>


<%-- Mostrar detalles del estatus del recurso --%>
<c:if test="${modificacion != null && modificacion== 'estadoRecurso'}">

    <table align="center" width="60%" class="cuadroContenido">
        <tr><td colspan="6" align="center">
        <titulo> 
            Descripci&oacute;n del estatus
        </titulo>
    </td></tr>      
    <c:if test="${sessionScope.msgErrores != null}">
    <tr align="center"><td colspan="6">
            <div class="msgErr">
                <c:forEach items='${sessionScope.msgErrores}' var='error'>
                    ${error} <br>
                </c:forEach>
            </div>
            <c:remove var="msgErrores" scope="session" />
        </td></tr> 
    <tr><td colspan="6">&nbsp;</td></tr>                    
</c:if>                    
<%-- Formulario detalles del tipo de recurso --%>                   
<tr align="center"> 
    <td><form name="datosEstadoRecurso" action="ControlEstado" method="POST">
            <table>
                <tbody>
                <input type="hidden" name="accion"/>
                <input type="hidden" name="idEstadoRecurso" value="${detalleEstado.idEstadoRecurso}" />
                <tr>                                    
                    <td>Estado</td>
                    <td><input type="text" size="25" name="nombre" disabled="true" value="${detalleEstado.nombre}"/></td>
                </tr>
                <tr>                                    
                    <td>Descripcion</td>
                    <td><textarea rows="3" cols="25" disabled="true" name="descripcion">${detalleEstado.descripcion}</textarea></td>
                </tr>                             
                <tr><td>&nbsp;</td></tr>
                <c:if test="${sessionScope.usuarioActual != null && sessionScope.usuarioActual.privilegio == 'Administrador' || sessionScope.usuarioActual.privilegio == 'Super'}">
                    <tr>                                    
                        <td>&nbsp; &nbsp; &nbsp;</td>
                        <td align="center"><input type="button" value="Modificar" onClick="return habilitar(datosEstadoRecurso);" name="botonHabilita"/>
                            &nbsp;<input type="button"value="Guardar" onClick="return confirmaModificacion(datosEstadoRecurso, 'modificarEstadoRecurso:5');" name="botonGuarda"/></td>
                    </tr>                              
                    <tr><td>&nbsp;</td></tr>
                </c:if>
                </tbody>
            </table>
        </form>
    </td></tr>
</table> 
<br><br>
</c:if>


<c:if test="${modificacion != null && modificacion== 'factura' && detalleFactura==null}">
    <table align="center" width="50%" class='tablaModificar'>
        <tr><td colspan="6">
                <div class="TituloModificar" align='center'> 
                    Error de Petici&oacute;n<br><br>
                </div>
            </td></tr>
    </table>            
</c:if>
<c:if test="${modificacion != null && modificacion== 'recurso'}">    
    <c:if test="${detalleTipo!=null}">
        <table align="center" width="60%" class='cuadroContenido'>
            <tr><td colspan="6" align="center">
            <titulo> Tipo de recurso </titulo>
        </td></tr>      
        <c:if test="${sessionScope.msgErrores != null}">
        <tr align="center"><td colspan="6">
                <div class="msgErr">
                    <c:forEach items='${sessionScope.msgErrores}' var='error'>
                        ${error} <br>
                    </c:forEach>
                </div>
                <c:remove var="msgErrores" scope="session" />
            </td></tr> 
        <tr><td colspan="6">&nbsp;</td></tr>                    
    </c:if>                    
    <tr align="center"> 
        <td><form name="datosTipo" action="ControlTipo" method="POST">
                <input type="hidden" name="accion"/>
                <table>
                    <tbody>
                        <tr>
                            <td colspan="6" rowspan="1"></td>
                        </tr>
                        <tr>
                            <td>Tipo</td>                                            
                            <td><input type="text" size="10" disabled="true" value="${sessionScope.detalleTipo.tipo}"/></td>
                            <td>Marca</td>                                                 
                            <td><input type="text" size="10" disabled="true" value="${sessionScope.detalleTipo.marca}"/></td>
                            <td>Modelo</td>                                               
                            <td><input type="text" size="10" disabled="true" value="${sessionScope.detalleTipo.modelo}"/></td>
                        </tr>
                        <tr><td>&nbsp;</td></tr>
                        <c:if test="${sessionScope.usuarioActual != null && sessionScope.usuarioActual.privilegio == 'Administrador' || sessionScope.usuarioActual.privilegio == 'Super'}">
                            <tr>
                                <td align="center" colspan="6"><a onclick="return enviar(datosTipo, 'getAllTipos:2');" class="aHref" href="#cambiarTipoRecurso"> Cambiar </a></td>                                                
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </form>
        </td></tr>
</table> 
<c:if test="${filtroTipo!=null && filtroTipo=='filtroTipo'}">
    <script>
                                            window.location.href = '#cambiarTipoRecurso';
//                                window.open('_self');
    </script>
</c:if>

</c:if>
<c:if test="${detalleRecurso!=null}">
    <br><br>
    <table align="center" width="60%" class='cuadroContenido'>
        <tr><td colspan="6" align="center">
        <titulo> Detalles de recurso </titulo>
    </td></tr>      
    <c:if test="${sessionScope.msgErrores != null}">
    <tr align="center"><td colspan="6">
            <div class="msgErr">
                <c:forEach items='${sessionScope.msgErrores}' var='error'>
                    ${error} <br>
                </c:forEach>
            </div>
            <c:remove var="msgErrores" scope="session" />
        </td></tr> 
    <tr><td colspan="6">&nbsp;</td></tr>                    
</c:if>                    
<tr align="center"> 
    <td><form name="datosRecurso" action="ControlRecurso" method="POST">
            <table>
                <tbody>
                <input type="hidden" name="accionRec"/>
                <input type="hidden" name="nombre"/>
                <input type="hidden" name="idRecurso" value="${detalleRecurso.idRecurso}"/>
                <tr>
                    <td colspan="6" rowspan="1"></td>
                </tr>
                <tr>
                    <td>No. de Serie</td>
                    <td><input type="text" size="10" name="noSerie" disabled="true" value="${detalleRecurso['noSerie']}"/></td>
                    <td>Usuario Anterior</td>
                    <!--<td><input type="text" class="textInput" size="10" name="usuarioAnterior" disabled="true" value="" style='cursor: pointer;' onclick="return buscaUsuario(datosRecurso, 'buscaUsuario:10');"/></td>-->                                                     
                    <td>
                        <input type="text" disabled="true" name="usuarioAnteriorF" id="usuarioAnteriorF" onchange="return buscaUsuario(datosRecurso, 'usuarioAnteriorF:10', 'usuarioAnteriorF');"  value="${usuarioAnteriorF}"/>
                        <div class="dropdown">
                            <ul class="result" id="sugerenciaAnt">
                                <c:forEach items="${usuarioAnteriorF_catch}" var="nombre">
                                    <li onclick="selectSugerencia(datosRecurso, this.innerHTML, 'usuarioAnteriorF')">${nombre}</li>
                                    </c:forEach>
                            </ul>                                                  
                        </div>     
                    </td>                                                 
                    <td>Usuario Actual:</td>
                    <td>
                        <input type="text" disabled="true" name="usuarioActualF" id="usuarioActualF" onchange="return buscaUsuario(datosRecurso, 'usuarioActualF:10', 'usuarioActualF');"  value="${usuarioActualF}"/>
                        <div class="dropdown">
                            <ul class="result" id="sugerenciaAct">
                                <c:forEach items="${usuarioActualF_catch}" var="nombre">
                                    <li onclick="selectSugerencia(datosRecurso, this.innerHTML, 'usuarioActualF')">${nombre}</li>
                                    </c:forEach>
                            </ul>                                                  
                        </div>     

                    </td>  
                </tr>
                <tr>
                    <td>Costo</td>
                    <td><input type="text" size="10" name="costo" disabled="true" value="${detalleRecurso['costo']}"/></td>                                                                                             
                    <td>Tiempo de vida:</td>
                    <td><input type="text" size="10" name="tiempoVida" disabled="true" value="${detalleRecurso['tiempoVida']}"/></td>
                    <td>Fecha de renovaci&oacute;n:</td>
                    <c:set var="fechaRenovacion" value="${detalleRecurso['fechaRenovacion']}" scope="request"/>
                    <td><input type="text" size="10" name="fechaRenovacion" disabled="true" value="<%=new java.text.SimpleDateFormat("yyyy-MM-dd").format((java.util.Date)request.getAttribute("fechaRenovacion"))%>"/></td>
                </tr>
                <tr>
                    <td>Observaciones:</td>
                    <td><textarea disabled="true" name="observacionesRec">${detalleRecurso['observacionesRec']}</textarea></td>
                    <td colspan="2">&nbsp;</td>
                    <c:if test="${sessionScope.usuarioActual != null && sessionScope.usuarioActual.privilegio == 'Administrador' || sessionScope.usuarioActual.privilegio == 'Super'}">
                        <td align="center"><input type="button" class="aHref" value="Modificar" onClick="return habilitar(datosRecurso);" name="botonHabilita"/></td>
                        <td align="center"><input type="button" class="aHref" value="Guardar" onClick="return confirmaModificacionRec(datosRecurso, 'modificarRecurso:9');" name="botonGuarda"/></td>
                        </c:if>
                </tr>
                <tr><td>&nbsp;</td></tr>
                </tbody>
            </table>
        </form>
    </td></tr>
</table>  
</c:if>
<c:if test="${selectUser!=null && selectUser=='selectUser'}">
    <script>
                                            document.getElementById("nombre").focus();
                                            window.location.href = '#selectUser';
    </script>
</c:if>                          
<c:if test="${detalleUbicacion!=null}">
    <br><br>
    <table align="center" width="40%" class='cuadroContenido'>
        <tr><td colspan="6" align="center">
        <titulo> Ubicaci&oacute;n del recurso </titulo>
    </td></tr>      
    <c:if test="${sessionScope.msgErrores != null}">
    <tr align="center"><td colspan="6">
            <div class="msgErr">
                <c:forEach items='${sessionScope.msgErrores}' var='error'>
                    ${error} <br>
                </c:forEach>
            </div>
            <c:remove var="msgErrores" scope="session" />
        </td></tr> 
    <tr><td colspan="6">&nbsp;</td></tr>                    
</c:if>                    
<tr align="center"> 
    <td><form name="datosUbicacion" action="ControlUbicacion" method="POST">
            <table>
                <tbody>
                <input type="hidden" name="accion"/>
                <tr>
                    <td colspan="6" rowspan="1"></td>
                </tr>
                <tr>
                    <td>Oficina</td>
                    <td><input type="text" size="20" name="oficina" disabled="true" value="${sessionScope.detalleUbicacion['oficina']}"/></td>
                    <td>&Aacute;rea</td>
                    <td>
                        <input type="text" size="20" name="area" disabled="true" value="${sessionScope.detalleUbicacion['area']}"/>
                    </td>
                </tr>
                <tr><td>&nbsp;</td></tr>
                <c:if test="${sessionScope.usuarioActual != null && sessionScope.usuarioActual.privilegio == 'Administrador' || sessionScope.usuarioActual.privilegio == 'Super'}">
                    <tr>
                        <td align="center" colspan="6" ><a onclick="return enviar(datosUbicacion, 'getAllUbicaciones:2');" class="aHref" href="#cambiarUbicacionRecurso"> Cambiar </a></td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </form>
    </td></tr>
</table>

<c:if test="${filtroUbicacion!=null && filtroUbicacion=='filtroUbicacion'}">
    <script>
                                            window.location.href = '#cambiarUbicacionRecurso';
    </script>
</c:if>                                            
</c:if>        
<c:if test="${detalleEstado!=null}">
    
<br><br>
<table class="cuadroContenido" align="center" cellspacing="20">
    <tr colspan="6">
        <td align="center">
    <titulo> Licencias del recurso </titulo>                                  
</td>                                                       
</tr>                            
<tr colspan="6" align="right">
    <td colspan="1">
        <div> 
            <form name="seleccionaLicencia" action="ControlRecurso" method="POST">
                <input type="hidden" name="accionRec" value="obtenDatosMasiva:18"/>                                        
                <input type="submit" value="Vincular licencia" name="enviar" />                                                                                    
            </form>
            <c:if test="${filtroLicencia!=null && filtroLicencia=='filtroLicencia'}">
                <script>
                                            window.location.href = '#seleccionarLicencia';
                </script>
            </c:if>                                    
        </div>                                    
    </td>                                                       
</tr>   
<c:if test="${licenciasVinculadas != null && licenciasVinculadas== 'licenciasVinculadas'}">     
    <tr><td>
            <table class="showResult" align="center" width="100%">
                <thead>
                    <tr>
                        <th>N&uacute;mero licencia</th>
                        <th>Tipo licencia</th>
                        <th>Clave licencia</th>
                        <th>Observaciones</th>
                        <th>&nbsp;</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${licencias}" var="licencia">
                        <tr>
                            <td>${licencia.idDatosLicencia}</td>
                            <td>${licencia.nombre}</td>
                            <td>${licencia.clave}</td>
                            <td>${licencia.observaciones}</td>
                            <td><form action="ControlRecurso" name="desvincularLicencia" method="POST">
                                    <input type="hidden" name="accionRec" value="desvincularLicencia:24"/>
                                    <input type="hidden" name="idRecurso" value="${detalleRecurso.idRecurso}"/>
                                    <input type="hidden" name="idDatosLicencia" value="${licencia.idDatosLicencia}"/>
                                    <button value="enviar" onclick="return desvincularLicenciaF(desvincularLicencia);"> Desvincular </button>
                                </form>
                            </td>                                                        
                            <%--                            <td>
                                                            <a class="aHref" href="ControlRecurso?accionRec=desvincularLicencia:24&idRecurso=${detalleRecurso.idRecurso}&&idDatosLicencia=${licencia.idDatosLicencia}">DESVINCULAR</a>
                                                        </td>                                                        --%>
                        </tr>                                            
                    </c:forEach> 
                </tbody>
            </table>  
        </td></tr>
    </c:if>    
</table>      
    
    <br><br>
    <table align="center" width="40%" class='cuadroContenido'>
        <tr><td colspan="6" align="center">
        <titulo> Estatus del Recurso </titulo>
    </td></tr>                      
<tr align="center"> 
    <td><form name="datosEstado" action="ControlEstado" method="POST">
            <table>
                <tbody>
                <input type="hidden" name="accion"/>
                <tr>
                    <td colspan="6" rowspan="1"></td>
                </tr>
                <tr>
                    <td>Estatus</td>
                    <td><input type="text" name="nombre" disabled="true" value="${sessionScope.detalleEstado['nombre']}"/></td>
                    <td>Descripci&oacute;n</td>
                    <td><textarea rows="3" cols="25" disabled="true" name="descripcion">${sessionScope.detalleEstado['descripcion']}</textarea></td>
                </tr>
                <tr><td>&nbsp;</td></tr>
                <c:if test="${sessionScope.usuarioActual != null && sessionScope.usuarioActual.privilegio == 'Administrador' || sessionScope.usuarioActual.privilegio == 'Super'}">
                    <tr>
                        <td align="center" colspan="6" ><a onclick="return enviar(datosEstado, 'getAllEstados:2');" class="aHref" href="#cambiarEstadoRecurso"> Cambiar </a></td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </form>
    </td></tr>
</table>
               
<c:if test="${modificacionMasiva!=null && modificacionMasiva=='modificacionMasiva'}">
    <script>
                                            window.location.href = '#modificacionMasiva';
//                                window.open('_self');
    </script>
</c:if>                        


</table> 
<c:if test="${filtroEstado!=null && filtroEstado=='filtroEstado'}">
    <script>
        window.location.href = '#cambiarEstadoRecurso';
    </script>
</c:if>                                                 
</c:if>  

    <c:if test="${detalleFactura!=null}">
    <br><br>
    <table align="center" width="40%" class='cuadroContenido'>
        <tr><td colspan="6" align="center">
        <titulo> Factura correspondiente </titulo>
    </td></tr>      
    <c:if test="${sessionScope.msgErrores != null}">
    <tr align="center"><td colspan="6">
            <div class="msgErr">
                <c:forEach items='${sessionScope.msgErrores}' var='error'>
                    ${error} <br>
                </c:forEach>
            </div>
            <c:remove var="msgErrores" scope="session" />
        </td></tr>                         
</c:if>          
    <tr><td colspan="6">&nbsp;</td></tr>
<tr align="center">     
    <td><form name="datosFactura" action="ControlFactura" method="POST">
            <table width="800px">
                <tbody>
                <input type="hidden" name="accion"/>
                <tr>
                    <td colspan="6" rowspan="1"></td>
                </tr>
                <tr>
                    <td>Folio</td>
                    <td><input type="text" size="20" name="folio" disabled="true" value="${detalleFactura.folio}"/></td>
                    <td>Fecha de compra</td>
                    <td>
                        <input type="text" size="20" name="fechaAdquisicion" disabled="true" value="${detalleFactura.fechaAdquisicion}"/>
                    </td>
                </tr>
                <tr>
                    <td>Art&iacute;culos</td>
                    <td><input type="text" size="20" name="articulos" disabled="true" value="${detalleFactura.articulos}"/></td>
                    <td>Monto total pagado</td>
                    <td><input type="text" size="20" name="montoConIVA" disabled="true" value="${detalleFactura.montoConIVA}"/></td>
                </tr>
                <tr>
                    <td>Proveedor</td>
                    <td>
                        <input type="text" size="20" name="razonSocial" disabled="true" value="${detalleFactura.proveedor.razonSocial}"/>
                    </td>
                    <td>Contacto proveedor</td>
                    <td>
                        <input type="text" size="20" name="correoContacto" disabled="true" value="${detalleFactura.proveedor.correoContacto}"/>
                    </td>
                </tr>
                <tr><td>&nbsp;</td></tr>
                <c:if test="${sessionScope.usuarioActual != null && sessionScope.usuarioActual.privilegio == 'Administrador' || sessionScope.usuarioActual.privilegio == 'Super'}">
                    <tr>
                        <td align="center" colspan="6" ><a onclick="return enviar(datosFactura,'seleccionarFactura:11');" class="aHref" href="#cambiarFacturaRecurso"> Cambiar </a></td>
                    </tr>
                </c:if>
                </tbody>
            </table>
        </form>
    </td></tr>
</table>

<c:if test="${filtroFactura!=null && filtroFactura=='filtroFactura'}">
    <script>
                                            window.location.href = '#cambiarFacturaRecurso';
    </script>
</c:if>                                            
</c:if> 
    
</c:if>
<script>
    function muestraModal(link) {
        window.location.href = link;
        return false;
    }
</script>                            


</body>
<br><br>
</html>
</c:when>
<c:otherwise>
    <c:redirect url="index.htm" />
</c:otherwise>
</c:choose>
