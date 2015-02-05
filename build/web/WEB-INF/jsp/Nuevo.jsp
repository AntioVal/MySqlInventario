<%-- 
    Document   : Modificar
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
                <!--<link rel="stylesheet" type="text/css" href="css/Nuevo.css">-->
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <title>Nuevo</title>
                <!-- Especificamos el Logo en pequeño -->
                <link rel="shortcut icon" href="Imagen/gp_logo.png">
            </head>
            <body>
                <%@include file="Menu.jsp"%>
                <script language="javascript" type="text/javascript" src="scripts/popCalendar.js"></script>
                <script async  language="javascript" type="text/javascript" src="scripts/login.js"></script>
                <c:if test="${sessionScope.msgErrores != null}">
                    <table width="100%">
                        <tbody>
                            <tr align="center"><td colspan="6" align="center">
                                    <div class="msgErr">
                                        <c:forEach items='${sessionScope.msgErrores}' var='error'>
                                            ${error} <br>
                                        </c:forEach>
                                    </div>
                                </td></tr> 
                            <tr><td colspan="6">&nbsp;</td></tr>  
                        </tbody>
                    </table>
                    <c:remove var="msgErrores" scope="session" />
                </c:if>  


                <%-- CREAR NUEVA FACTURA --%>
                <c:if test="${param.nuevo != null && param.nuevo== 'factura'}">
                    <jsp:include page="ModalesSelecciona.jsp" flush="true" />
                    <form action="ControlFactura" name="nuevaFactura" method="POST">
                        <input type="hidden" name="accion" value="nuevaFsctura:10"/>
                        <table align="center" class="cuadroContenido" cellspacing="30">
                            <tr align="center">
                                <td colspan="4">
                            <titulo> Nueva factura </titulo>
                            </td>
                            </tr>
                            <tr align="center">
                                <td colspan="4">
                            <subtitulo> Datos base </subtitulo> 
                            <hr>
                            </td>
                            </tr>                
                            <tr align="center">
                                <td align="left"> Folio</td>
                                <td align="left"> <input size="15" type="text" name="folio" value="${folio}" /> </td> 
                                <td align="left"> Fecha de adquisici&oacute;n</td>
                                <td align="left"> <input size="15" type="text" name="fechaAdquisicion" value="${fechaAdquisicion}" onclick="return popUpCalendar(this, nuevaFactura.fechaAdquisicion, 'yyyy-mm-dd');" /> </td>                                
                            </tr>
                            <tr align="center">
                                <td align="left"> Monto sin I.V.A.</td>
                                <td align="left"> <input size="15" type="text" name="montoSinIVA" value="${montoSinIVA}" /> </td>
                                <td align="left"> I.V.A</td>
                                <td align="left"> <input size="15" type="text" name="iva" value="${iva}" /> </td>
                            </tr>
                            <tr align="center">
                                <td align="left"> Monto con I.V.A.</td>                                                   
                                <td align="left"> <input size="15" title="Valor numérico" type="text" name="montoConIVA" value="${montoConIVA}" /> </td>                               
                                <td align="left"> Art&iacute;culos</td>
                                <td align="left"> <input size="15" title="Valor numérico" type="text" name="articulos" value="${articulos}" /> </td>                                                   
                            </tr>
                            <tr align="center">
                                <td align="left"> Observaciones</td>
                                <td align="left"> 
                                    <textarea name="observaciones" rows="3" cols="16">${observaciones}</textarea> 
                                </td>                               
                            </tr>                           
                            <tr align="center">
                                <td colspan="4">
                            <subtitulo> Proveedor  </subtitulo>
                            <hr>                            
                            </td>
                            </tr>                                                       
                            <tr>
                                <td align="left"> Proveedor</td>
                                <td align="center"> 
                                    <button onclick="return cambiaAccionSubmit(nuevaFactura, 'selectProveedor:7', 'ControlProveedor');"> Seleccionar </button>
                                    <br>
                                </td>
                                <td align="right"> 
                                <c:if test='${proveedorSeleccionado ne null}'> 
                                    <subtitulo> Selecci&oacute;n: </subtitulo> 
                                    <input id="idProveedor" type="hidden" name="idProveedor" value="${sessionScope.idProveedor}"/>
                                </c:if> 
                                </td>                             
                            <td align="left"> ${proveedorSeleccionado} </td>                             
                            </tr>
                            <c:if test="${filtroProveedor!=null && filtroProveedor=='filtroProveedor'}">
                                <script>
                                    window.location.href = '#selectProveedor';
                                </script>
                            </c:if>                                                       
                            <tr align="center">
                                <td colspan="2" align="right">
                                    <button name="enviar"  value="enviar" onclick="return verificaNovaciosNuevaFactura(nuevaFactura);"> Crear </button> 
                                </td>  
                            </tr>
                        </table>
                    </form> 
                </c:if>                

                <%-- CREAR NUEVO RECURSO --%>
                <c:if test="${param.nuevo != null && param.nuevo== 'recurso'}">
                    <jsp:include page="ModalesSelecciona.jsp" flush="true" />
                    <form action="ControlRecurso" name="nuevoRecurso" method="POST">
                        <input type="hidden" name="accionRec" value="nuevoRecurso:19"/>
                        <input type="hidden" name="accion" value=""/>
                        <input type="hidden" name="nombre" value=""/>
                        <input type="hidden" name="nuevo" value="recurso"/>                        
                        <table align="center" class="cuadroContenido" cellspacing="30">
                            <tr align="center">
                                <td colspan="4">
                            <titulo> Nuevo recurso </titulo>
                            </td>
                            </tr>
                            <tr align="center">
                                <td colspan="4">
                            <subtitulo> Datos base </subtitulo> 
                            <hr>
                            </td>
                            </tr>                
                            <tr align="center">
                                <td align="left"> No. de serie</td>
                                <td align="left"> <input size="15" type="text" name="noSerie" value="${noSerie}" /> </td> 
                                <td align="left"> Costo</td>
                                <td align="left"> <input size="15" title="Valor numérico con dos posiciones decimales" type="text" name="costo" value="${costo}" /> </td>                                
                            </tr>
                            <tr align="center">
                                <td align="left"> Tiempo de vida</td>
                                <td align="left"> <input size="15" title="Valor numérico entero" type="text" name="tiempoVida" value="${tiempoVida}" /> </td>
                                <td align="left"> Fecha de renovaci&oacute;n</td>
                                <td align="left"> <input size="15" title="Fecha con formato AAAA-MM-DD" type="text" name="fechaRenovacion" value="${fechaRenovacion}" style='cursor: pointer;' onclick="return popUpCalendar(this, nuevoRecurso.fechaRenovacion, 'yyyy-mm-dd');" /> </td>                                 
                            </tr>
                            <tr align="center">
                                <td align="left"> Usuario anterior</td>                                                   
                                <td>
                                    <input type="text" size="15" name="usuarioAnteriorF" id="usuarioAnteriorF" onchange="return buscaUsuario(nuevoRecurso, 'usuarioAnteriorF:10', 'usuarioAnteriorF');"  value="${sessionScope.usuarioAnteriorF}" title="Presiona tecla Tab para obtener sugerencia" />
                                    <div class="dropdown">
                                        <ul class="result" id="sugerenciaAnt">
                                            <c:forEach items="${usuarioAnteriorF_catch}" var="nombre">
                                                <li onclick="selectSugerencia(nuevoRecurso, this.innerHTML, 'usuarioAnteriorF')">${nombre}</li>
                                                </c:forEach>
                                        </ul>                                                  
                                    </div>     
                                </td>                                 
                                <td align="left"> Usuario actual</td>
                                <td>
                                    <input type="text" size="15" name="usuarioActualF" id="usuarioActualF" onchange="return buscaUsuario(nuevoRecurso, 'usuarioActualF:10', 'usuarioActualF');"  value="${sessionScope.usuarioActualF}" title="Presiona tecla Tab para obtener sugerencia" />
                                    <div class="dropdown">
                                        <ul class="result" id="sugerenciaAct">
                                            <c:forEach items="${usuarioActualF_catch}" var="nombre">
                                                <li onclick="selectSugerencia(nuevoRecurso, this.innerHTML, 'usuarioActualF')">${nombre}</li>
                                                </c:forEach>
                                        </ul>                                                  
                                    </div>     

                                </td>                                                   
                            </tr>
                            <tr align="center">
                                <td colspan="4">
                            <subtitulo> Tipo de producto </subtitulo>
                            <hr>
                            </td>
                            </tr>
                            <tr>
                                <td align="left"> Tipo de producto</td>
                                <td align="center">  
                                    <button onclick="return cambiaAccionSubmit(nuevoRecurso, 'allTiposNewRec:2', 'ControlTipo');"> Seleccionar </button>
                                </td>                             
                                <td align="right"> 
                                    <c:if test='${tipoSeleccionado ne null}'> 
                                <subtitulo> Selecci&oacute;n: </subtitulo> 
                                <input type="hidden" name="idTipo" value="${idTipo}" />
                            </c:if> 
                            </td>                             
                            <td align="left"> ${tipoSeleccionado} </td>                             
                            </tr>
                            <c:if test="${filtroTipo!=null && filtroTipo=='filtroTipo'}">
                                <script>
                                    window.location.href = '#selectTipoRecurso';
                                </script>
                            </c:if>                            
                            <tr align="center">
                                <td colspan="4">
                            <subtitulo> Factura a la que pertenece </subtitulo>
                            <hr>
                            </td>
                            </tr>                            
                            <tr>
                                <td align="left"> Factura</td>
                                <td align="center"> 
                                    <!--<a class="aHref" href="ControlFactura?accion=selectFactura:7"> Seleccionar </a>--> 
                                    <button onclick="return cambiaAccionSubmit(nuevoRecurso, 'selectFactura:7', 'ControlFactura');"> Seleccionar </button>
                                </td>                             
                                <td align="right"> 
                                    <c:if test='${facturaSeleccionada ne null}'> 
                                <subtitulo> Selecci&oacute;n: </subtitulo>
                                <input type="hidden" name="idFactura" value="${idFactura}" />
                            </c:if> 
                            </td>                             
                            <td align="left"> ${facturaSeleccionada} </td>                             
                            </tr>
                            <c:if test="${filtroFactura!=null && filtroFactura=='filtroFactura'}">
                                <script>
                                    window.location.href = '#selectFactura';
                                </script>
                            </c:if> 
                            <tr align="center">
                                <td colspan="4">
                            <subtitulo> Oficina a la que pertenece </subtitulo>
                            <hr>                            
                            </td>
                            </tr>                                                       
                            <tr>
                                <td align="left"> Ubicaci&oacute;n</td>
                                <td align="center"> 
                                    <!--<a class="aHref" href="ControlUbicacion?accion=selectUbicacion:7"> Seleccionar </a>-->
                                    <button onclick="return cambiaAccionSubmit(nuevoRecurso, 'selectUbicacion:7', 'ControlUbicacion');"> Seleccionar </button>
                                </td>                             
                                <td align="right"> 
                                    <c:if test='${ubicacionSeleccionada ne null}'> 
                                <subtitulo> Selecci&oacute;n: </subtitulo> 
                                <input type="hidden" name="idUbicacion" value="${idUbicacion}" />
                            </c:if> 
                            </td>                             
                            <td align="left"> ${ubicacionSeleccionada} </td>                             
                            </tr>
                            <c:if test="${filtroUbicacion!=null && filtroUbicacion=='filtroUbicacion'}">
                                <script>
                                    window.location.href = '#selectUbicacion';
                                </script>
                            </c:if>                            
                            <tr align="center">
                                <td colspan="4">
                            <subtitulo> Estatus del producto </subtitulo>
                            <hr>
                            </td>
                            </tr>                            
                            <tr align="center">
                                <td align="left"> Estatus</td>
                                <td align="center"> 
                                    <select name="estatus">
                                        <c:forEach items='${estados}' var='element'>
                                            <option value="${element}"> ${element} </option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>   
                            <tr align="center">
                                <td align="left"> Observaciones</td>
                                <td align="left"> 
                                    <textarea name="observaciones" rows="3" cols="16" onchange="return enviarRec(nuevoRecurso, 'change:11');" >${observaciones}</textarea> 
                                </td>                               
                            </tr>                        
                            <tr align="center">
                                <td colspan="4" align="right">
                                    <button name="enviar"  value="enviar" onclick="return enviarRec(nuevaRecurso,'nuevoRecurso:19');"> Crear </button> 
                                </td> 
                            </tr>
                        </table>
                    </form> 
                </c:if>

                <%-- CREAR NUEVO PRODUCTO (TIPO DE RECURSO) --%>
                <c:if test="${param.nuevo != null && param.nuevo== 'tipoRecurso'}">
                    <form action="ControlTipo" name="nuevoTipoRecurso" method="POST">
                        <input type="hidden" name="accion" value="nuevoTipoRecurso:8"/>
                        <table align="center" class="cuadroContenido" cellspacing="30">
                            <tr align="center">
                                <td colspan="2">
                            <titulo> Nuevo tipo de producto </titulo>
                            </td></tr>
                            <tr align="center">
                                <td align="left"> Tipo</td>
                                <td align="left"> <input size="15" type="text" name="tipo" value="${detalleTipoRecurso.tipo}" /> </td> 
                            </tr>
                            <tr align="center">
                                <td align="left"> Marca</td>
                                <td align="left"> <input size="15" type="text" name="marca" value="${detalleTipoRecurso.marca}" /> </td>
                            </tr>
                            <tr align="center">
                                <td align="left"> Modelo</td>
                                <td align="left"> <input size="15" type="text" name="modelo" value="${detalleTipoRecurso.modelo}" /> </td>
                            </tr>
                            <tr align="center">
                                <td align="left"> Descripci&oacute;n</td>
                                <td align="left"> 
                                    <textarea name="descripcion" rows="4" cols="17">${detalleTipoRecurso.descripcion}</textarea>  
                                </td>
                            </tr>                            
                            <tr align="center">
                                <td colspan="2" align="right">
                                    <button name="enviar"  value="enviar" onclick="return verificaNovacios(nuevoTipoRecurso);"> Crear </button> 
                                </td>  
                            </tr>
                        </table>
                    </form> 
                </c:if>

                <%-- CREAR NUEVO EMPLEADO --%>
                <c:if test="${param.nuevo != null && param.nuevo== 'empleado'}">
                    <form action="ControlRecurso" name="nuevoEmpleado" method="POST">
                        <input type="hidden" name="accionRec" value="nuevoEmpleado:20"/>
                        <table align="center" class="cuadroContenido" cellspacing="30">
                            <tr align="center">
                                <td colspan="2">
                            <titulo> Nuevo empleado </titulo>
                            </td></tr>
                            <tr align="center">
                                <td align="left"> N&uacute;mero de empleado</td>
                                <td align="left"> <input size="15" type="text" name="idEmpleado" value="${empleado.idEmpleado}" /> </td> 
                            </tr>
                            <tr align="center">
                                <td align="left"> Nombres</td>
                                <td align="left"> <input size="15" type="text" name="nombres" value="${empleado.nombres}" /> </td>
                            </tr>
                            <tr align="center">
                                <td align="left"> Apellido Paterno</td>
                                <td align="left"> <input size="15" type="text" name="apellidoPaterno" value="${empleado.apellidoPaterno}" /> </td>
                            </tr>
                            <tr align="center">
                                <td align="left"> Apellido Materno</td>
                                <td align="left"> <input size="15" type="text" name="apellidoMaterno" value="${empleado.apellidoMaterno}" /> </td>
                            </tr>
                            <tr align="center">
                                <td colspan="2" align="right">
                                    <button name="enviar"  value="enviar" onclick="return verificaNovacios(nuevoEmpleado);"> Crear </button> 
                                </td>  
                            </tr>
                        </table>
                    </form> 
                </c:if>

                <%-- CREAR NUEVA LICENCIA --%>
                <c:if test="${param.nuevo != null && param.nuevo== 'licencia'}">
                    <form action="ControlRecurso" name="nuevaLicencia" method="POST">
                        <input type="hidden" name="accionRec" value="nuevaLicencia:21"/>
                        <table align="center" class="cuadroContenido" cellspacing="30">
                            <tr align="center">
                                <td colspan="2">
                            <titulo> Nueva licencia </titulo>
                            </td></tr>
                            <tr align="center">
                                <td align="left"> Nombre </td>
                                <td align="left"> <input size="15" type="text" name="nombre" value="${licencia.nombre}" /> </td> 
                            </tr>
                            <tr align="center">
                                <td align="left"> Clave de licencia</td>
                                <td align="left"> <input size="15" type="text" name="claveLicencia" value="${licencia.claveLicencia}" /> </td>
                            </tr>
                            <tr align="center">
                                <td align="left"> Observaciones</td>
                                <td align="left"> 
                                    <textarea name="observaciones" rows="4" cols="17"> ${licencia.observaciones}</textarea>  
                                </td>
                            </tr>
                            <tr align="center">
                                <td colspan="2" align="right">
                                    <button name="enviar"  value="enviar" onclick="return verificaNovacios(nuevaLicencia);"> Crear </button>
                                </td>  
                            </tr>
                        </table>
                    </form> 
                </c:if>

                <%-- CREAR NUEVA OFICINA --%>
                <c:if test="${param.nuevo != null && param.nuevo== 'oficina'}">
                    <form action="ControlUbicacion" name="nuevaUbicacion" method="POST">
                        <input type="hidden" name="accion" value="nuevaUbicacion:10"/>
                        <table align="center" class="cuadroContenido" cellspacing="30">
                            <tr align="center">
                                <td colspan="2">
                            <titulo> Nueva ubicaci&oacute;n de recurso </titulo>
                            </td></tr>
                            <tr align="center">
                                <td align="left"> Oficina </td>
                                <td align="left"> <input size="15" type="text" name="oficina" value="${oficina}" /> </td> 
                            </tr>
                            <tr align="center">
                                <td align="left"> &Aacute;rea</td>
                                <td align="left"> <input size="15" type="text" name="area" value="${area}" /> </td>
                            </tr>
                            <tr align="center">
                                <td colspan="2" align="right">
                                    <button name="enviar"  value="enviar" onclick="return verificaNovacios(nuevaUbicacion);"> Crear </button>
                                </td>  
                            </tr>
                        </table>
                    </form> 
                </c:if>

                <%-- CREAR NUEVO PROVEEDOR --%>
                <c:if test="${param.nuevo != null && param.nuevo== 'proveedor'}">
                    <form action="ControlProveedor" name="nuevoProveedor" method="POST">
                        <input type="hidden" name="accion" value="nuevoProveedor:10"/>
                        <table align="center" class="cuadroContenido" cellspacing="30">
                            <tr align="center">
                                <td colspan="2">
                            <titulo> Nuevo proveedor </titulo>
                            </td></tr>
                            <tr align="center">
                                <td align="left"> Razon social </td>
                                <td align="left"> <input size="20" type="text" name="razonSocial" value="${proveedorForm.razonSocial}" /> </td> 
                            </tr>
                            <tr align="center">
                                <td align="left"> R.F.C.</td>
                                <td align="left"> <input size="20" type="text" name="rfc" value="${proveedorForm.rfc}" /> </td>
                            </tr>
                            <tr align="center">
                                <td align="left"> Domicilio fiscal </td>
                                <td align="left"> <input size="20" type="text" name="domicilioFiscal" value="${proveedorForm.domicilioFiscal}" /> </td> 
                            </tr>
                            <tr align="center">
                                <td align="left"> Tel&eacute;fono de contacto</td>
                                <td align="left"> <input size="20" type="text" name="telefono" value="${proveedorForm.telefono}" /> </td>
                            </tr>
                            <tr align="center">
                                <td align="left"> Correo</td>
                                <td align="left"> <input size="20" type="text" name="correo" value="${proveedorForm.correo}" /> </td>
                            </tr>
                            <tr align="center">
                                <td colspan="2" align="right">
                                    <input type="submit" value="Crear"/>  
                                </td>  
                            </tr>
                        </table>
                    </form> 
                </c:if>

                <%-- CREAR NUEVO ESTATUS --%>
                <c:if test="${param.nuevo != null && param.nuevo== 'estatus'}">
                    <form action="ControlEstado" name="nuevoEstado" method="POST">
                        <input type="hidden" name="accion" value="nuevoEstado:6"/>
                        <table align="center" class="cuadroContenido" cellspacing="30">
                            <tr align="center">
                                <td colspan="2">
                            <titulo> Nuevo estatus de recurso posible </titulo>
                            </td></tr>
                            <tr align="center">
                                <td align="left"> Nombre de estatus </td>
                                <td align="left"> <input size="15" type="text" name="estatus" value="${estatus}" /> </td> 
                            </tr>
                            <tr align="center">
                                <td align="left"> Descripci&oacute;n</td>
                                <td align="left"> <input size="15" type="text" name="descripcion" value="${descripcion}" /> </td>
                            </tr>
                            <tr align="center">
                                <td colspan="2" align="right">
                                    <button name="enviar"  value="enviar" onclick="return verificaNovacios(nuevoEstado);"> Crear </button>
                                </td>  
                            </tr>
                        </table>
                    </form> 
                </c:if>

                <%-- CREAR NUEVA CUENTA DE USUARIO --%>
                <c:if test="${param.nuevo != null && param.nuevo== 'cuenta'}">
                    <table align="center" width="60%" class="cuadroContenido">
                        <tr><td colspan="6" align="center">
                        <titulo> 
                            Crear nueva cuenta de usuario
                        </titulo>
                    </td></tr>      
                <tr><td>&nbsp;</td></tr>                         
                <tr align="center"> 
                    <td><form name="datosUsuario" action="ControlCuenta" method="POST">
                            <table>
                                <tbody>
                                <input type="hidden" name="accion" value="nuevaCuentaUsuario:12"/>
                                <tr>                                    
                                    <td>Usuario</td>
                                    <td><input type="text" size="30" name="nombreU" value="${detalleUsuario.nombreU}"/></td>
                                </tr>
                                <tr>
                                    <td>Nombre</td>
                                    <td><input type="text" size="30" name="nombreP" value="${detalleUsuario.nombreP}"/></td>                                   
                                </tr>                              
                                <tr>                                    
                                    <td>Apellido Paterno</td>
                                    <td><input type="text"  size="30" name="apellidoPP" value="${detalleUsuario.apellidoPP}"/></td>
                                </tr>
                                <tr>                                    
                                    <td>Apellido Materno</td>
                                    <td><input type="text" size="30" name="apellidoMP" value="${detalleUsuario.apellidoMP}"/></td>
                                </tr>
                                <tr>                                    
                                    <td>Correo</td>
                                    <td><input type="text" size="30" name="correo" value="${detalleUsuario.correo}"/></td>
                                </tr>                
                                <tr>
                                    <td>Privilegio</td>
                                    <td>
                                        <select name="privilegio">
                                            <option value="Estandar">Estandar</option>
                                            <option value="Administrador">Administrador</option>
                                            <c:if test="${sessionScope.usuarioActual != null && sessionScope.usuarioActual.privilegio == 'Super'}">
                                            <option value="Super">Super</option>
                                            </c:if>
                                        </select>
                                    </td>
                                </tr>                 
                                <tr align="center">
                                    <td colspan="2" align="right">
                                        <input type="submit" value="Crear"/> 
                                    </td>  
                                </tr>
                                </tbody>
                            </table>
                        </form>
                    </td></tr>
            </table> 
        </c:if>
    </body>
</html>
</c:when>
<c:otherwise>
    <c:redirect url="index.htm" />
</c:otherwise>
</c:choose>
