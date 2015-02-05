<%-- 
    Document   : Cuenta
    Created on : 22/01/2014, 12:20:48 PM
    Author     : luis-valerio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<c:choose>
    <c:when test="${sessionScope.usuarioActual != null && sessionScope.usuarioActual.nombreU != null}">

        <html>
            <head>
                <!--<link rel="stylesheet" type="text/css" href="css/Cuenta.css">-->
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <title>Cuenta</title>
                <!-- Especificamos el Logo en pequeño -->
                <link rel="shortcut icon" href="Imagen/gp_logo.png">
                <!--importamos los archivos necesarios de alertas js-->
                <%--<jsp:include page="Home.jsp" flush="true" />--%> 
                <%@include file="Menu.jsp"%>
                <script language="javascript" type="text/javascript" src="scripts/mensajes.js"></script> 
            </head>
            <body <c:if test="${sessionScope.msgErrores != null && sessionScope.msgErrores != ''}">
                    onLoad="alert('<c:out value="${sessionScope.msgErrores}"/>');"          
                    <c:remove var="msgErrores" scope="session" />
                </c:if>
                >


                <br>
           <table align="center" width="40%" class='cuadroContenido'>
                    <tr><td>&nbsp;</td></tr>
                    <tr><td colspan="6" align="center">
                    <titulo> Informaci&oacute;n de cuenta </titulo>
                </td></tr>
                <tr> <td  colspan="1" width="10%" rowspan="10"> &nbsp; </td>
                    <td  colspan="4">
                    <table>
                        <form action="ControlCuenta" method="POST" name="formDatosCuenta">
                            <input type="hidden" name="accion" value=""/>
                            <br>
                            <tr>
                                <td colspan="2"> &nbsp;</td>
                                <td colspan="2" align="center">
                                    <div class="SubtituloCuenta" style="visibility:<c:out value=" ${requestScope.visibilidad}"/>"> 
                                        <font color="red">¡Nuevos datos! </font>  
                                    </div>
                                </td>
                            </tr>
                            <tr> 
                                <!--<td colspan="2"><div class="SubtituloCuenta"> Nombre de Usuario: </div></td>-->
                                <td colspan="2"><subtitulo> Nombre de Usuario:</subtitulo></td>
                            <td colspan="2"><div class="letraError"> <c:out value=" ${sessionScope.errores[0]}"/> </div></td>
                            </tr>                   
                            <tr align="left" >
                                <td colspan="2" align="left"><c:out value=" ${sessionScope.usuarioActual.nombreU}"/></td>
                                <td colspan="2" align="center">
                                    <input name="inombreU" size="20" type="text" value="${requestScope.mnombreU}" style="visibility:<c:out value=" ${requestScope.visibilidad}"/>"/>
                                </td>         
                            </tr>
                            <tr align="left"><td colspan="2"><subtitulo> Nombre: </subtitulo></td> 
                                <td colspan="2"><div class="letraError"> <c:out value=" ${sessionScope.errores[1]}"/> </div></td>
                            </tr>
                            <tr>
                                <td colspan="2" align="left"> <c:out value=" ${requestScope.nombreP}"/> </td>
                                <td colspan="2" align="center">
                                    <input name="inombreP" size="20" type="text" value="${requestScope.mnombreP}" style="visibility:<c:out value=" ${requestScope.visibilidad}"/>"/>
                                </td>  
                            </tr>
                            <tr>
                                <td colspan="2"><subtitulo> Apellido Paterno: </subtitulo></td> 
                                <td colspan="2"><div class="letraError"> <c:out value=" ${sessionScope.errores[2]}"/> </div></td>
                            </tr>
                            <tr align="center" >
                                <td colspan="2" align="left"><c:out value=" ${requestScope.apellidoPP}"/></td>
                                <td colspan="2" align="center">
                                    <input name="iapellidoPP" size="20" type="text" value="${requestScope.mapellidoPP}" style="visibility:<c:out value=" ${requestScope.visibilidad}"/>"/>
                                </td>  
                            </tr>
                            <tr>
                                <td colspan="2"><subtitulo> Apellido Materno: </subtitulo></td> 
                                <td colspan="2"><div class="letraError"> <c:out value=" ${sessionScope.errores[3]}"/> </div></td>
                            </tr>
                            <tr align="center" >
                                <td colspan="2" align="left"><c:out value=" ${requestScope.apellidoMP}"/></td>
                                <td colspan="2" align="center">
                                    <input name="iapellidoMP" size="20" type="text" value="${requestScope.mapellidoMP}" style="visibility:<c:out value=" ${requestScope.visibilidad}"/>"/>
                                </td>  
                            </tr>
                            <tr>
                                <td colspan="2"><subtitulo> Correo: </subtitulo></td> 
                                <td colspan="2"><div class="letraError"> <c:out value=" ${sessionScope.errores[4]}"/> </div></td>
                            </tr>
                            <tr align="center">
                                <td colspan="2" align="left"><c:out value=" ${requestScope.correo}"/></td>
                                <td colspan="2">
                                    <input name="icorreo" size="20" type="text" maxlength="40" value="${requestScope.mcorreo}" style="visibility:<c:out value=" ${requestScope.visibilidad}"/>" />
                                </td>  
                            </tr>
                            <tr>
                                <td colspan="4"><subtitulo> Privilegio: </subtitulo></td> 
                            </tr>
                            <tr align="center">
                                <td colspan="2" align="left"><c:out value=" ${sessionScope.usuarioActual.privilegio}"/></td>
                                <td colspan="2">
                                    <!--<select name="iprivilegio" style="visibility:">-->
                                    <select name="iprivilegio" style="visibility:hidden">
                                        <option value="Administrador"> Administrador</option>
                                        <option value="Estandar"> Estandar</option>
                                    </select>
                                </td>  
                            </tr> 
                            <tr><td colspan="4">
                            <subtitulo> Fecha de creaci&oacute;n: </subtitulo>
                                </td> </tr>
                            <tr align="center">
                                <td colspan="4" align="left"><c:out value=" ${requestScope.fechaCreacion}"/></td>
                            </tr>
                            <tr><td colspan="4"> &nbsp; </td></tr>  
                            <tr align='center' colspan="4">
                                <td> <button onclick="return enviar(formDatosCuenta, 'HabilitarCamposModificacion:2')"> Modificar cuenta </button>  </td>
                                <td> <button onclick="return enviar(formDatosCuenta, 'ModificarPassword:5')" style="visibility:<c:out value=" ${requestScope.visibilidad}"/>" > Modificar contrase&ntilde;a </button>  </td>
                                <td> <button onclick="return enviar(formDatosCuenta, 'CancelarModificacion:4')" style="visibility:<c:out value=" ${requestScope.visibilidad}"/>" >Cancelar</button>  </td>
                                <td> <button onclick="return verificaDatosCuenta(formDatosCuenta, 'ConfirmarModificacion:3')" style="visibility:<c:out value=" ${requestScope.visibilidad}"/>" >Guardar</button>  </td>
                                <!--<td> <button value="" class="buttonCuenta" onclick="(formDatosCuenta.accion='ModificaDatosCuenta:4')">Cancelar</button>  </td>-->
                                <!--<td> &nbsp; </td>-->
                            </tr>                        
                        </form>  
                    </table>
                </td>
                <td  colspan="1" width="10%" rowspan="10"> &nbsp; </td>
            </tr>
            <tr><td colspan="6"> &nbsp; </td></tr>    
            <tr>                      
            </tr>
        </table>  


    </body>
</html>
<c:remove var="errores" scope="session" />
</c:when>
<c:otherwise>
    <c:redirect url="index.htm" />
</c:otherwise>
</c:choose>
