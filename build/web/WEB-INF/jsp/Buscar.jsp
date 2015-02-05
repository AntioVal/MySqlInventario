<%-- 
    Document   : Buscar
    Created on : 22/01/2014, 12:20:48 PM
    Author     : luis-valerio
--%>

<%@page import="java.util.Vector"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<c:choose>
    <c:when test="${sessionScope.usuarioActual != null && sessionScope.usuarioActual.nombreU != null}">

        <html>
            <head>
                <link rel="stylesheet" type="text/css" href="css/VentanasModales.css">
                <!--<link rel="stylesheet" type="text/css" href="css/menuDesplegable.css">-->
                <!--<link rel="stylesheet" type="text/css" href="css/Buscar.css">-->
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <title>Buscar</title>
                <!-- Especificamos el Logo en pequeño -->
                <link rel="shortcut icon" href="Imagen/gp_logo.png">
                <%--<jsp:include page="scripts/popCalendar.js" flush="true"></jsp:include>--%>
            </head>
            <body>
                <%@include file="Menu.jsp"%>
                <script language="javascript" type="text/javascript" src="scripts/popCalendar.js"></script>
                <script async  language="javascript" type="text/javascript" src="scripts/login.js"></script>
                <c:if test="${busqueda != null && busqueda== 'principal'}">
                    <table align="center" width="50%" class='tablaBuscar'>
                        <tr><td colspan="8">
                                <div class="TituloBuscar" align='center'> 
                                    B&uacute;squeda <br><br>
                                </div>
                            </td></tr>
                        <tr><td colspan="6"></td></tr>
                        <tr align='center'>
                            <td colspan="2">
                                <form action="ControlFactura" method="POST">
                                    <input type="hidden" name="accion" value="busquedaFactura:2"/>
                                    <button class="buttonBuscar">
                                        <img src="Imagen/facturaIcon.png" width="90" height="90"> </img>
                                        <br><br>
                                        Factura
                                    </button> 
                                </form>
                            </td>
                            <td colspan="2">
                                <form action="ControlRecurso">
                                    <input type="hidden" name="accionRec" value="busquedaRecurso:2"/>
                                    <button class="buttonBuscar">
                                        <img src="Imagen/recursoIcon.png" width="90" height="90"> </img>
                                        <br><br>
                                        Recurso
                                    </button> 
                                </form> 
                            </td>
                            <td colspan="2">
                                <form action="ControlTipo">
                                    <input type="hidden" name="accion" value="busquedaTipoRecurso:1"/>
                                    <button class="buttonBuscar">
                                        <img src="Imagen/tipoRecursoIcon.png" width="90" height="90"> </img>
                                        <br><br>
                                        Tipo Recurso
                                    </button> 
                                </form>
                            </td>    
                            <td colspan="2">
                                <form action="ControlUbicacion">
                                    <input type="hidden" name="accion" value="busquedaUbicacion:1"/>
                                    <button class="buttonBuscar">
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
                                    <button class="buttonBuscar">
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
                                        <button class="buttonBuscar">
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
                                    <button class="buttonBuscar">
                                        <img src="Imagen/estatusIcon.png" width="90" height="90"> </img>
                                        <br><br>
                                        Estatus
                                    </button> 
                                </form>
                            </td>   
                            <td colspan="1">&nbsp;</td>
                        </tr>
                        <tr><td colspan="6">&nbsp;</td></tr>
                        <tr align="center"> 
                            <td colspan="2"> &nbsp; </td> 
                            <td colspan="2"> &nbsp; </td>
                        </tr>
                        <tr><td colspan="6">&nbsp;</td></tr>
                    </table>
                </c:if>

                <c:if test="${busqueda != null && busqueda== 'factura'}">
                    <br><br>
                    <form action="ControlFactura" name="formBuscarFactura">
                        <input type="hidden" name="accion" value=""/>
                        <table align="center" class="cuadroContenido" width="60%" cellspacing="10">
                            <tr align="center"><td colspan="4">
                            <Titulo> 
                                B&uacute;squeda de Factura<br><br>
                            </Titulo>
                            </td></tr>
                            <tr align="left" colspan="4" width="10">
                                <td align="left"> N&uacute;mero de folio</td>
                                <td align="left"> <input size="10" type="text" name="Folio" value="${Folio}" onchange="return enviarBusquedaFac(formBuscarFactura, 'busquedaFactura:3');" /> </td>
                                <td align="left"> Proveedor</td>
                                <td>
                                    <select name="proveedor">
                                        <c:forEach items="${provedores}" var="proveedor">
                                            <option>${proveedor}</option>
                                        </c:forEach>
                                    </select>
                                </td>
                            </tr>       
                            <tr align="left" colspan="4" width="10">
                                <td align="left"> Fecha de adquisici&oacute;n entre </td>
                                <td align="left"> <input  size="10" type="text" name="fechaIni" value="${fechaIni}" style='cursor: pointer;' onchange="return enviarBusquedaFac(formBuscarFactura, 'busquedaFactura:3');" onclick="return popUpCalendar(this, formBuscarFactura.fechaIni, 'yyyy-mm-dd');" /> </td>
                                <td align="center"> y </td>
                                <td align="left"> <input size="10" type="text" name="fechaFin" value="${fechaFin}" style='cursor: pointer;' onclick="return popUpCalendar(this, formBuscarFactura.fechaFin, 'yyyy-mm-dd');"/> </td> 
                            </tr>
                            <tr>
                                <td colspan="4" align="right">
                                    <input type="submit" value="Buscar" onclick="return enviarBusquedaFac(formBuscarFactura, 'busquedaFactura:3');"/> 
                                </td></tr>
                        </table>
                    </form>
                </c:if>
                <!--Mostrar resultados de la busqueda solicitada-->
                <c:if test="${resultado != null && resultado== 'factura' && resultBusqueda.size() gt 0}">
                    <br>
                    <table align="center" class="cuadroContenido" width="60%" cellspacing="10">
                        <tr align="center"><td>
                        <Titulo> 
                            Resultado de b&uacute;squeda<br><br>
                        </Titulo></td>
                </tr>
                <tr> <td colspan="4" align="center">
                <subtitulo>${Coincidencias}</subtitulo>
            </td> 
        </tr>
        <tr>
            <td colspan="4">
                <table class="showResult" align="center" width="100%" border="1">
                    <thead>
                        <tr>
                            <th>Folio</th>
                            <th>Fecha de adquisici&oacute;n</th>
                            <th>Proveedor</th>
                            <th>Monto sin I.V.A </th>
                            <th>I.V.A</th>
                            <th>Monto con I.V.A </th>
                            <th>Detalle</th>
                        </tr>
                    </thead>                                    
                    <tbody>
                        <c:forEach items='${resultBusqueda}' var='fac'>
                            <tr>
                                <c:forEach items='${fac}' var='campo' varStatus='index'>
                                    <c:if test='${index.index ne 0}'>
                                        <td>${campo}</td>
                                    </c:if>
                                </c:forEach>                                            
                                <td><form action="ControlFactura" method="POST">
                                        <input type="hidden" name="accion" value="mostrarDetallesFactura:4"/>
                                        <input type="hidden" name="idFactura" value="${fac[0]}"/>
                                        <input type="hidden" name="Folio" value="${fac[1]}"/>
                                        <input type="hidden" name="proveedor" value="${fac[3]}"/>
                                        <input type="submit" value="Ver"/>
                                    </form>
                                </td>
                            </tr>                                         
                        </c:forEach>
                    </tbody>
                </table>  
            </td>
        </tr>
    </table>
    <br><br>
</c:if>


<c:if test="${busqueda != null && busqueda== 'recurso'}">
    <form action="ControlRecurso" name="formBuscarRecurso" method="POST">
        <table align="center" class="cuadroContenido" width="60%" cellspacing="10">
            <input type="hidden" name="accionRec" value="busquedaRecurso:6"/>
            <tr align="center"><td colspan="6">
            <titulo> B&uacute;squeda de Recurso </titulo>
            </td></tr>
            <c:if test='${sessionScope.msgErrores!=null}'>                            
                <tr align="center"><td colspan="6">
                        <div class="msgErr">
                            <c:forEach items='${sessionScope.msgErrores}' var='error'>
                                ${error} <br>
                            </c:forEach>
                            <c:remove var="msgErrores" scope="session" />
                        </div>
                    </td></tr>                            
                </c:if>                            
            <tr align="left" width="10">
                <td align="left"> Usuario anterior</td>
                <td align="left"> <input size="10" type="text" name="usuarioAnteriorF" value="${usuarioAnteriorF}"/> </td>                         
                <td align="left"> Usuario actual</td>
                <td align="left"> <input size="10" type="text" name="usuarioActualF" value="${usuarioActualF}"/> </td>                                              
                <td align="left"> N&uacute;mero de Serie</td>
                <td align="left"> <input size="10" type="text" name="noSerie" value="${noSerie}" /> </td>                                                              
            </tr> 
            <tr align="left" width="10">
                <td align="left"> Tipo</td>
                <td align="left"> <input size="10" type="text" name="tipo" value="${filtroTipo.tipo}"/> </td>                         
                <td align="left"> Marca</td>
                <td align="left"> <input size="10" type="text" name="marca" value="${filtroTipo.marca}"/> </td>                                              
                <td align="left"> Modelo</td>
                <td align="left"> <input size="10" type="text" name="modelo" value="${filtroTipo.modelo}" /> </td>                                                              
            </tr> 
            <tr align="left" width="10">
                <td align="left"> Oficina</td>
                <td align="left"> <input size="10" type="text" name="oficina" value="${filtroRec.usuarioAnterior}"/> </td>                         
                <td align="left"> Area</td>
                <td align="left"> <input size="10" type="text" name="area" value="${filtroRec.usuarioActual}"/> </td>                                              
                <td align="left"> Proveedor</td>
                <td align="left"> <input size="10" type="text" name="proveedor" value="${filtroRec.noSerie}" /> </td>                                                              
            </tr>                              
            <tr align="left" width="10">
                <td align="left"> Estatus</td>
                <td align="left">
                    <select class="SelectStyle" name="estatus" style="width:160px;" onchange="return enviarBusquedaRec(formBuscarRecurso, 'busquedaRecurso:6');" >
                        <option>-- Todo --</option>
                        <c:forEach items="${estatus}" var="stat">
                            <c:choose>    
                                <c:when test='${stat eq selSt}'>
                                    <option selected>${stat}</option>
                                </c:when>
                                <c:otherwise>
                                    <option>${stat}</option>               
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>                                        
                    </select>                                    
                </td>                                
                <td align="left"> Licencia</td>
                <td align="left">
                    <select class="SelectStyle" name="licencia" style="width:160px;" onchange="return enviarBusquedaRec(formBuscarRecurso, 'busquedaRecurso:6');" >
                        <option>-- Todo --</option>
                        <c:forEach items="${licencias}" var="licencia">
                            <c:choose>    
                                <c:when test='${licencia eq selLic}'>
                                    <option selected>${licencia}</option>
                                </c:when>
                                <c:otherwise>
                                    <option>${licencia}</option>               
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>                                        
                    </select>     
                </td>
                <td align="left"> Costo </td>
                <td align="left"><select name="costo" style="width:160px;" onchange="return enviarBusquedaRec(formBuscarRecurso, 'busquedaRecurso:6');" >
                        <option>-- Todo --</option>
                        <option value="0-2999"> de $0 a $2,999</option>
                        <option value="3000-4999"> de $3,000 a $4,999</option>
                        <option value="5000-9999"> de $5,000 a $9,999</option>
                        <option value="10000-mas"> $10,000 &oacute; m&aacute;s</option>
                    </select>
                </td>                                 
            </tr>                            
            <tr align="left"width="10">                                
                <td align="left"> Fecha de adquisici&oacute;n</td>
                <td align="left"> <input size="10" type="text" name="fechaAdqIni" value="${fechaAdqIni}" style='cursor: pointer;' onchange="return enviarBusquedaRec(formBuscarRecurso, 'busquedaRecurso:6');" onclick="return popUpCalendar(this, formBuscarRecurso.fechaAdqIni, 'yyyy-mm-dd');" /> </td>                        
                <td align="left"> hasta </td>
                <td align="left"> <input size="10" type="text" name="fechaAdqFin" value="${fechaAdqFin}" style='cursor: pointer;' onchange="return enviarBusquedaRec(formBuscarRecurso, 'busquedaRecurso:6');" onclick="return popUpCalendar(this, formBuscarRecurso.fechaAdqFin, 'yyyy-mm-dd');" /> </td>
            </tr>                        
            <tr align="left" width="10">
                <td align="left"> Fecha de renova&oacute;n</td>
                <td align="left"> <input size="10" type="text" name="fechaRenIni" value="${fechaRenIni}" style='cursor: pointer;' onchange="return enviarBusquedaRec(formBuscarRecurso, 'busquedaRecurso:6');" onclick="return popUpCalendar(this, formBuscarRecurso.fechaRenIni, 'yyyy-mm-dd');" /> </td>                         
                <td align="left"> hasta </td>
                <td align="left"> <input size="10" type="text" name="fechaRenFin" value="${fechaRenFin}" style='cursor: pointer;' onchange="return enviarBusquedaRec(formBuscarRecurso, 'busquedaRecurso:6');" onclick="return popUpCalendar(this, formBuscarRecurso.fechaRenFin, 'yyyy-mm-dd');" /> </td>                      
                <td>&nbsp;</td>                     
            </tr>    
            <tr align="left" width="10">
                <td align="left"> Mostrar primeros</td>
                <td align="left"> <input size="10" type="text" name="limite" value="${limite}" /> </td>                         
                <td align="left"> registros </td>
                <td>&nbsp;</td>
                <td align="right">
                    <input type="submit" value="Buscar" onclick="return enviarBusquedaRec(formBuscarRecurso, 'busquedaRecurso:6');"/> 
                </td>                        
            </tr>    
        </table>
    </form>

    <c:if test="${resultado != null && resultado== 'recurso'}"> 
        <br>
        <table class="cuadroContenido" width="90%" cellspacing="20">
            <tr colspan="6">
                <td align="center">
            <titulo> Recursos encontrados ... </titulo>                                  
        </td>
    </tr>                            
    <tr colspan="14">
        <td>
            <form name="modificaMasiva" action="ControlRecurso" method="POST">
                <input type="hidden" name="accionRec" value="obtenDatosMasiva:7"/>                                        
                <input type="submit" value="Modificar varios" name="modificarVarios" />
                &nbsp; &nbsp; &nbsp; &nbsp;
                <input type="button" onclick="window.location.href = 'DescargaArchivo?tc=recurso';" value="Exportar a Excel" name="exportar" />
            </form>          
        </td> 
    </tr>                            
    <tr><td>
            <table class="showResult" align="center" width="100%">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>No. Serie</th>
                        <th>Tipo</th>
                        <th>Marca</th>
                        <th>Modelo</th>
                        <th>Anterior</th>
                        <th>Actual</th>
                        <th>Estatus</th>
                        <th>Oficina</th>
                        <th>Folio Factura</th>
                        <th>Costo</th>                                                
                        <th>Proveedor</th>
                        <th>Adquisici&oacute;n</th>
                        <th>Renovaci&oacute;n</th>
                        <th>Detalles</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${recursosEncontrados}" var="recurso">
                        <tr>
                            <c:forEach items="${recurso}" var="columna">
                                <td>${columna}</td>
                            </c:forEach>
                            <td><form action="ControlRecurso" name="formBuscarRecurso" method="POST">
                                    <input type="hidden" name="accionRec" value="mostrarDetallesRecurso:1"/>
                                    <input type="hidden" name="idRecurso" value="${recurso[0]}"/>
                                    <input type="hidden" name="noSerie" value="${recurso[1]}"/>
                                    <input type="submit" value="Ver"/>
                                </form>
                            </td>                                                        
                        </tr>                                            
                    </c:forEach> 
                </tbody>
            </table>  
        </td></tr>
</table>                     

<c:if test="${modificacionMasiva!=null && modificacionMasiva=='modificacionMasiva'}">
    <script>
                                    window.location.href = '#modificacionMasiva';
//                                window.open('_self');
    </script>
</c:if>                        

</c:if>

</c:if>    


<c:if test="${busqueda != null && busqueda== 'tipoRecurso'}">
    <form action="ControlTipo" name="formBuscarTipoRecurso" method="POST">
        <table align="center" class="cuadroContenido" width="60%" cellspacing="10">
            <br><br>
            <input type="hidden" name="accion" value=""/>
            <tr align="center"><td colspan="6">
            <titulo> B&uacute;squeda de tipos de producto </titulo>
            </td></tr>
            <c:if test='${sessionScope.msgErrores!=null}'>                            
                <tr align="center"><td colspan="6">
                        <div class="msgErr">
                            <c:forEach items='${sessionScope.msgErrores}' var='error'>
                                ${error} <br>
                            </c:forEach>
                            <c:remove var="msgErrores" scope="session" />
                        </div>
                    </td></tr>                            
                </c:if>                            
            <tr align="left" width="10">
                <td align="left"> Tipo</td>
                <td align="left"> <input size="10" type="text" name="tipo" value="${filtroTipoRec.tipo}" onchange="return enviar(formBuscarTipoRecurso, 'busquedaTipoRecurso:5');" /> </td>                         
                <td align="left"> Marca</td>
                <td align="left"> <input size="10" type="text" name="marca" value="${filtroTipoRec.marca}" onchange="return enviar(formBuscarTipoRecurso, 'busquedaTipoRecurso:5');" /> </td>
                <td align="left"> Modelo</td>
                <td align="left"> <input size="10" type="text" name="modelo" value="${filtroTipoRec.modelo}" onchange="return enviar(formBuscarTipoRecurso, 'busquedaTipoRecurso:5');" /> </td>                            
            </tr> 
            <tr>
                <td align="right" colspan="6">
                    <input type="submit" value="Buscar" onclick="return enviar(formBuscarTipoRecurso, 'busquedaTipoRecurso:5');"/> 
                </td>   
            </tr>                            
        </table>
    </form>

    <c:if test="${resultado != null && resultado== 'tipoRecurso'}"> 
        <br><br>                             
        <table class="cuadroContenido" width="60%" cellspacing="20" align="center">
            <tr colspan="6">
                <td align="center" colspan="1">
            <titulo> Elementos encontrados ... </titulo>                                   
        </td>                                                       
    </tr>                                                     
    <tr><td>
            <table class="showResult" width="100%">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Tipo</th>
                        <th>Marca</th>
                        <th>Modelo</th>
                        <th>Detalles</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${idTipos}" var="elemento" varStatus="indice">
                        <tr>
                            <c:forEach items="${elemento}">
                                <td>${idTipos[indice.index]}</td>
                                <td>${tipos[indice.index]}</td>
                                <td>${marcas[indice.index]}</td>
                                <td>${modelos[indice.index]}</td>
                            </c:forEach>
                            <td><form action="ControlTipo" name="detallesTipoRecurso" method="POST">
                                    <input type="hidden" name="accion" value="mostrarDetallesTipoRecurso:4"/>
                                    <input type="hidden" name="idTipo" value="${idTipos[indice.index]}"/>
                                    <input type="hidden" name="tipo" value="${tipos[indice.index]}"/>
                                    <input type="submit" value="Ver"/>
                                </form>
                            </td>                                                        
                        </tr>                                            
                    </c:forEach> 
                </tbody>
            </table>  
        </td></tr>
</table>                                        
</c:if>

</c:if>         
<!-- Formulario para la busqueda de empleados -->
<c:if test="${param.busqueda != null && param.busqueda== 'empleado'}">
    <form action="ControlRecurso" name="formBuscarEmpleado" method="POST">
        <table align="center" class="cuadroContenido" width="60%" cellspacing="10">
            <input type="hidden" name="accionRec" value=""/>
            <tr align="center"><td colspan="4">
            <titulo> B&uacute;squeda de empleados </titulo>
            </td></tr>
            <c:if test='${sessionScope.msgErrores!=null}'>                            
                <tr align="center"><td colspan="4">
                        <div class="msgErr">
                            <c:forEach items='${sessionScope.msgErrores}' var='error'>
                                ${error} <br>
                            </c:forEach>
                            <c:remove var="msgErrores" scope="session" />
                        </div>
                    </td></tr>                            
                </c:if>                            
            <tr align="left" width="10">
                <td align="left"> N&uacute;mero de empleado</td>
                <td align="left"> <input size="10" type="text" name="numeroEmpleado" value="${numeroEmpleado}" onchange="return enviarRec(formBuscarEmpleado, 'busquedaEmpleado:12');" /> </td>                         
                <td align="left"> Nombre </td>
                <td align="left"> <input size="10" type="text" name="nombre" value="${nombre}" onchange="return enviarRec(formBuscarEmpleado, 'busquedaEmpleado:12');" /> </td>                         
            </tr> 
            <tr>
                <td align="right" colspan="4">
                    <input type="submit" value="Buscar" onclick="return enviarRec(formBuscarEmpleado, 'busquedaEmpleado:12');"/> 
                </td>   
            </tr>                            
        </table>
    </form>

    <c:if test="${resultado != null && resultado== 'empleado'}"> 
        <br><br>                             
        <table class="cuadroContenido" width="60%" cellspacing="20" align="center">
            <tr colspan="6">
                <td align="center" colspan="1">
            <titulo> Elementos encontrados ... </titulo>                                   
        </td>                                                       
    </tr>                                                     
    <tr><td>
            <table class="showResult" width="100%">
                <thead>
                    <tr>
                        <th>No. Empleado</th>
                        <th>Nombre completo</th>
                        <th>&nbsp;</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${empleados}" var="elemento" varStatus="indice">
                        <tr>
                            <td>${elemento.idEmpleado}</td>
                            <td>${elemento.nombreCompleto}</td>
                            <td><form action="ControlRecurso" name="detallesEmpleado" method="POST">
                                    <input type="hidden" name="accionRec" value="mostrarDetallesEmpleado:13"/>
                                    <input type="hidden" name="idEmpleado" value="${elemento.idEmpleado}"/>
                                    <input type="hidden" name="nombreCompleto" value="${elemento.nombreCompleto}"/>
                                    <input type="submit" value="Ver"/>
                                </form>
                            </td>                                                                                                      
                        </tr>                                            
                    </c:forEach> 
                </tbody>
            </table>  
        </td></tr>
</table>                                        
</c:if>
</c:if>       


<!-- Formulario para la busqueda de empleados -->
<c:if test="${param.busqueda != null && param.busqueda== 'licencia'}">
    <form action="ControlRecurso" name="formBuscarLicencia" method="POST">
        <table align="center" class="cuadroContenido" width="60%" cellspacing="10">
            <input type="hidden" name="accionRec" value=""/>
            <tr align="center"><td colspan="4">
            <titulo> B&uacute;squeda de licencias </titulo>
            </td></tr>
            <c:if test='${sessionScope.msgErrores!=null}'>                            
                <tr align="center"><td colspan="4">
                        <div class="msgErr">
                            <c:forEach items='${sessionScope.msgErrores}' var='error'>
                                ${error} <br>
                            </c:forEach>
                            <c:remove var="msgErrores" scope="session" />
                        </div>
                    </td></tr>                            
                </c:if>                            
            <tr align="left">
                <td align="left"> N&uacute;mero de licencia</td>
                <td align="left"> <input size="10" type="text" name="idDatosLicencia" value="${idDatosLicencia}" onchange="return enviarRec(formBuscarLicencia, 'busquedaLicencia:15');" /> </td>                         
                <td align="left"> Tipo de licencia </td>
                <td align="left"> <input size="10" type="text" name="nombre" value="${nombre}" onchange="return enviarRec(formBuscarLicencia, 'busquedaLicencia:15');" /> </td>                         
            </tr> 
            <tr align="left" width="10">
                <td align="left"> Clave de licencia</td>
                <td align="left"> <input size="10" type="text" name="clave" value="${clave}" onchange="return enviarRec(formBuscarLicencia, 'busquedaLicencia:15');" /> </td>                         
            </tr> 
            <tr>
                <td align="right" colspan="4">
                    <input type="submit" value="Buscar" onclick="return enviarRec(formBuscarLicencia, 'busquedaLicencia:15');"/> 
                </td>   
            </tr>                            
        </table>
    </form>

    <c:if test="${resultado != null && resultado== 'licencia'}"> 
        <br><br>                             
        <table class="cuadroContenido" width="60%" cellspacing="20" align="center">
            <tr colspan="6">
                <td align="center" colspan="1">
            <titulo> Elementos encontrados ... </titulo>                                   
        </td>                                                       
    </tr>                                                     
    <tr><td>
            <table class="showResult" width="100%">
                <thead>
                    <tr>
                        <th>No. licencia</th>
                        <th>Tipo licencia</th>
                        <th>Clave</th>
                        <th>Observaciones</th>
                        <th>&nbsp;</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${licencias}" var="elemento" varStatus="indice">
                        <tr>
                            <td>${elemento.idDatosLicencia}</td>
                            <td>${elemento.nombre}</td>
                            <td>${elemento.clave}</td>
                            <td>${elemento.observaciones}</td>
                            <td><form action="ControlRecurso" name="detallesLicencia" method="POST">
                                    <input type="hidden" name="accionRec" value="mostrarDetallesLicencia:16"/>
                                    <input type="hidden" name="idDatosLicencia" value="${elemento.idDatosLicencia}"/>
                                    <input type="hidden" name="nombre" value="${elemento.nombre}"/>
                                    <input type="hidden" name="clave" value="${elemento.clave}"/>
                                    <input type="hidden" name="observaciones" value="${elemento.observaciones}"/>
                                    <input type="submit" value="Ver"/>
                                </form>
                            </td>                                                                                                      
                        </tr>                                            
                    </c:forEach> 
                </tbody>
            </table>  
        </td></tr>
</table>                                        
</c:if>

</c:if>                 


<c:if test="${busqueda != null && busqueda== 'proveedor'}">

    <br><br>
    <form action="ControlProveedor" name="formBuscarProveedor" method="POST">
        <input type="hidden" name="accion" value=""/>
        <table align="center" class="cuadroContenido" width="60%" cellspacing="10">
            <tr align="center"><td colspan="6">
            <titulo> B&uacute;squeda de proveedor </titulo>
            </td></tr>
            <tr align="left" width="10">
                <td align="left"> Razon Social</td>
                <td align="left"> <input size="10" type="text" name="razonSocial" value="${razonSocial}" onchange="return enviar(formBuscarProveedor, 'busquedaProveedor:3');"/> </td>                         
                <td align="left"> RFC</td>
                <td align="left"> <input size="10" type="text" name="rfc" value="${rfc}" onchange="return enviar(formBuscarProveedor, 'busquedaProveedor:3');" /> </td>
                <td align="left"> Domicilio Fiscal</td>
                <td align="left"> <input size="10" type="text" name="domicilioFiscal" value="${domicilioFiscal}" onchange="return enviar(formBuscarProveedor, 'busquedaProveedor:3');" /> </td>
            </tr>
            <td colspan="6" align="right">
                <input type="submit" value="Buscar" onclick="return enviar(formBuscarProveedor, 'busquedaProveedor:3');"/> 
            </td>                     
        </table>
    </form>          

    <br><br>

    <c:if test="${resultado != null && resultado== 'proveedor'}">
        <table class="cuadroContenido" width="60%" cellspacing="20" align="center">
            <tr colspan="6">
                <td align="center" colspan="1">
            <titulo> Elementos encontrados  ...  </titulo>                                   
        </td>                                                       
    </tr>                                                     
    <tr><td>
            <table class="showResult" align="center" width="100%">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Razon social</th>
                        <th>R.F.C.</th>
                        <th>Domicilio Fiscal</th>
                        <th>Detalles</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${resultBusqueda}" var="proveedor" varStatus="indice">
                        <tr>
                            <td>${proveedor.idProveedor}</td>
                            <td>${proveedor.razonSocial}</td>
                            <td>${proveedor.rfc}</td>
                            <td>${proveedor.domicilioFiscal}</td>
                            <td><form action="ControlProveedor" name="detallesProveedor" method="POST">
                                    <input type="hidden" name="accion" value="verDetallesProveedor:4"/>
                                    <input type="hidden" name="idProveedor" value="${proveedor.idProveedor}"/>
                                    <input type="hidden" name="razonSocial" value="${proveedor.razonSocial}"/>
                                    <input type="submit" value="Ver"/>
                                </form>
                            </td>                                                        
                        </tr>                                            
                    </c:forEach> 
                </tbody>
            </table>  
        </td></tr>
</table> 
</c:if>                        
</c:if>   



<c:if test="${busqueda != null && busqueda== 'ubicacion'}">

    <br><br>
    <form action="ControlUbicacion" name="formBuscarUbicacion" method="POST">
        <input type="hidden" name="accion" value=""/>
        <table align="center" class="cuadroContenido" width="60%" cellspacing="10">
            <tr align="center"><td colspan="4">
            <titulo> B&uacute;squeda de oficina </titulo>
            </td></tr>
            <tr align="left">
                <td align="left"> Oficina</td>
                <td align="left"> <input size="10" type="text" name="oficina" value="${oficina}" onchange="return enviar(formBuscarUbicacion, 'busquedaUbicacion:4');"/> </td>                         
                <td align="left"> &Aacute;rea</td>
                <td align="left"> <input size="10" type="text" name="area" value="${area}" onchange="return enviar(formBuscarUbicacion, 'busquedaUbicacion:4');" /> </td>
            </tr>
            <tr>
                <td align="right" colspan="4">
                    <input type="submit" value="Buscar" onclick="return enviar(formBuscarUbicacion, 'busquedaUbicacion:4');"/> 
                </td>                     
            </tr>
        </table>
    </form>          

    <br><br>

    <c:if test="${resultado != null && resultado== 'ubicacion'}">
        <table class="cuadroContenido" width="60%" cellspacing="20" align="center">
            <tr colspan="6">
                <td align="center" colspan="1">
            <titulo> Elementos encontrados ... </titulo>                                    
        </td>                                                       
    </tr>                                                     
    <tr><td>
            <table class="showResult" align="center" width="50%">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Oficina</th>
                        <th>&Aacute;rea</th>
                        <th>Detalles</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${resultBusqueda}" var="ubicacion" varStatus="indice">
                        <tr>
                            <td>${ubicacion.idUbicacion}</td>
                            <td>${ubicacion.oficina}</td>
                            <td>${ubicacion.area}</td>
                            <td><form action="ControlUbicacion" name="detallesUbicacion" method="POST">
                                    <input type="hidden" name="accion" value="verDetallesUbicacionRecurso:5"/>
                                    <input type="hidden" name="idUbicacion" value="${ubicacion.idUbicacion}"/>
                                    <input type="submit" value="Ver"/>
                                </form>
                            </td>                                                        
                        </tr>                                            
                    </c:forEach> 
                </tbody>
            </table>  
        </td></tr>
</table> 
</c:if>                        
</c:if>        


<c:if test="${busqueda != null && busqueda== 'estatus'}">

    <br><br>
    <form action="ControlEstado" name="formBuscarEstado" method="POST">
        <input type="hidden" name="accion" value=""/>
        <table align="center" class="cuadroContenido" width="60%" cellspacing="10">
            <tr align="center"><td colspan="4">
            <titulo> B&uacute;squeda de estatus </titulo>
            </td></tr>
            <tr align="left">
                <td align="left"> Nombre</td>
                <td align="left"> <input size="10" type="text" name="nombre" value="${nombre}" onchange="return enviar(formBuscarEstado, 'busquedaEstatus:4');"/> </td>                         
                <td align="left"> Descripci&oacute;n</td>
                <td align="left"> <input size="10" type="text" name="descripcion" value="${descripcion}" onchange="return enviar(formBuscarEstado, 'busquedaEstatus:4');" /> </td>
            </tr>
            <tr>
                <td align="right" colspan="4">
                    <input type="submit" value="Buscar" onclick="return enviar(formBuscarEstado, 'busquedaEstatus:4');"/> 
                </td>                     
            </tr>
        </table>
    </form>          

    <br><br>

    <c:if test="${resultado != null && resultado== 'estatus'}">
        <table class="cuadroContenido" width="60%" cellspacing="20" align="center">
            <tr colspan="6">
                <td align="center" colspan="1">
            <titulo> Elementos encontrados  ... </titulo>                                  
        </td>                                                       
    </tr>                                                     
    <tr><td>
            <table class="showResult" align="center" width="50%">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Descripcion</th>
                        <th>Detalles</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${resultBusqueda}" var="status" varStatus="indice">
                        <tr>
                            <td>${status.idEstadoRecurso}</td>
                            <td>${status.nombre}</td>
                            <td>${status.descripcion}</td>
                            <td><form action="ControlEstado" name="detallesEstado" method="POST">
                                    <input type="hidden" name="accion" value="verDetallesEstado:3"/>
                                    <input type="hidden" name="nombre" value="${status.nombre}"/>
                                    <input type="submit" value="Ver"/>
                                </form>
                            </td>                                                        
                        </tr>                                            
                    </c:forEach> 
                </tbody>
            </table>  
        </td></tr>
</table> 
</c:if>                        
</c:if>                        


<c:if test="${busqueda != null && busqueda== 'cuenta'}">

    <form action="ControlCuenta" name="formBuscarCuenta" method="POST">
        <input type="hidden" name="accion" value=""/>
        <table align="center" class="cuadroContenido" width="60%" cellspacing="10">
            <tr><td colspan="4" align="center">
            <titulo> B&uacute;squeda de cuentas de usuario </titulo>
            </td></tr>
            <tr><td>&nbsp;</td></tr>
            <tr align="left">
                <td align="left"> Usuario</td>
                <td align="left"> <input size="10" type="text" name="usuario" value="${usuario}" onchange="return enviar(formBuscarCuenta, 'busquedaCuenta:9');"/> </td>                         
                <td align="left"> Nombre</td>
                <td align="left"> <input size="10" type="text" name="nombre" value="${nombre}" onchange="return enviar(formBuscarCuenta, 'busquedaCuenta:9');" /> </td>
            </tr>
            <tr align="left">
                <td align="left"> Apellido paterno</td>
                <td align="left"> <input size="10" type="text" name="apellidoPaterno" value="${apellidoPaterno}" onchange="return enviar(formBuscarCuenta, 'busquedaCuenta:9');" /> </td>                
                <td align="left"> Apellido materno</td>
                <td align="left"> <input size="10" type="text" name="apellidoMaterno" value="${apellidoMaterno}" onchange="return enviar(formBuscarCuenta, 'busquedaCuenta:9');" /> </td>                                
            </tr>
            <tr>
                <td align="right" colspan="4">
                    <input type="submit" value="Buscar" onclick="return enviar(formBuscarCuenta, 'busquedaCuenta:9');"/> 
                </td>                     
            </tr>
        </table>
    </form>          



    <c:if test="${resultado != null && resultado== 'cuenta'}">
        <br><br>
        <table class="cuadroContenido" width="60%" cellspacing="20" align="center">
            <tr colspan="6">
                <td align="center" colspan="1">
            <titulo> Elementos encontrados  ... </titulo>                                  
        </td>                                                       
    </tr>                                                     
    <tr><td>
            <table class="showResult" align="center" width="100%">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Usuario</th>
                        <th>Nombre</th>
                        <th>Apellido Paterno</th>
                        <th>Privilegio</th>
                        <th>Correo</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${resultBusqueda}" var="cuenta" varStatus="indice">
                        <tr>
                            <td>${cuenta.idUsuario}</td>
                            <td>${cuenta.nombreU}</td>
                            <td>${cuenta.nombreP}</td>
                            <td>${cuenta.apellidoPP}</td>
                            <td>${cuenta.privilegio}</td>
                            <td>${cuenta.correo}</td>
                            <td><form action="ControlCuenta" name="detallesCuenta" method="POST">
                                    <input type="hidden" name="accion" value="verDetallesCuenta:10"/>
                                    <input type="hidden" name="idUsuario" value="${cuenta.idUsuario}"/>
                                    <input type="submit" value="Ver"/>
                                </form>
                            </td>                                                        
                        </tr>                                            
                    </c:forEach> 
                </tbody>
            </table>  
        </td></tr>
</table> 
</c:if>                        
</c:if>  
<%-- Ventana modal para aplicar modificacón masiva de dispositivos --%>
<c:if test="${modificacionMasiva!=null && modificacionMasiva=='modificacionMasiva'}">
    <c:if test="${sessionScope.msgErroresMasiva != null && sessionScope.msgErroresMasiva != ''}">
        <script>
                                    alert('<c:out value="${sessionScope.msgErroresMasiva}"/>');
        </script>                  
        <c:remove var="msgErroresMasiva" scope="session" />
    </c:if>
    <div id="modificacionMasiva" class="modalmask">
            <div class="modalboxBig movedown" style="width: 1200px;">
                    <a href="#close" title="Cerrar" class="close">X</a>
            <form action="ControlRecurso" name="formModificacionMasivaRecurso" method="POST">    
                <input type="hidden" name="accionRec" value="modificacionMasiva:8"/>
                <input type="hidden" name="accion" value=""/>
                <input type="hidden" name="nombre"/>
                <input type="hidden" name="idRecursos" value=""/>
                <table><tbody>                                
                        <tr>
                            <td colspan="6" align="center"> 
                                <h3 align="center"> ELEGIR RECURSOS A MODIFICAR </h3>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="6"> &nbsp;</td>
                        </tr>                                
                        <tr><td>Oficina: </td>
                            <td>
                                <select name="oficina" onchange="return cambiaAction(formModificacionMasivaRecurso, 'filtraUbicaciones:3', 'ControlUbicacion', 'modificacionMasiva');" style="width:200px;">
                                    <option>---seleccione---</option>
                                    <c:forEach items="${oficinas}" var="oficina">
                                        <c:choose>
                                            <c:when test="${detalleUbicacion.oficina == oficina}">
                                                <option selected>${oficina}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option>${oficina}</option>
                                            </c:otherwise>
                                        </c:choose>                                        
                                    </c:forEach>
                                </select>
                            </td>
                            <td>&Aacute;rea</td>
                            <td>
                                <select name="area"  style="width:200px;">
                                    <option>---seleccione---</option>
                                    <c:forEach items="${areas}" var="area">
                                        <c:choose>
                                            <c:when test="${detalleUbicacion.area == area}">
                                                <option selected>${area}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option>${area}</option>
                                            </c:otherwise>
                                        </c:choose>                                        
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>Estado recurso: </td>
                            <td>
                                <select name="estado" style="width:200px;">
                                    <option>---seleccione---</option>
                                    <c:forEach items="${estatus}" var="est">
                                        <c:choose>
                                            <c:when test="${estadoSelect == est}">
                                                <option selected>${est}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option>${est}</option>
                                            </c:otherwise>
                                        </c:choose>                                        
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>                               
                        <tr>                      
                            <td>Usuario Anterior : </td>
                            <td>                        
                                <input type="text" name="usuarioAnteriorF" id="usuarioAnteriorF" onchange="return buscaUsuario(formModificacionMasivaRecurso, 'usuarioAnteriorF:22', 'usuarioAnteriorF');"  value="${usuarioAnteriorFM}" style="width:200px;" />
                                <div class="dropdown">
                                    <ul class="result" id="sugerenciaAnt">
                                        <c:forEach items="${usuarioAnteriorF_catch}" var="nombre">
                                            <li onclick="selectSugerencia(formModificacionMasivaRecurso, this.innerHTML, 'usuarioAnteriorF')">${nombre}</li>
                                            </c:forEach>
                                    </ul>                                                  
                                </div>  
                            </td>
                            <td>Usuario Actual : </td>
                            <td>
                                <input type="text" name="usuarioActualF" id="usuarioActualF" onchange="return buscaUsuario(formModificacionMasivaRecurso, 'usuarioActualF:22', 'usuarioActualF');"  value="${usuarioActualFM}" style="width:200px;" />
                                <div class="dropdown">
                                    <ul class="result" id="sugerenciaAct">
                                        <c:forEach items="${usuarioActualF_catch}" var="nombre">
                                            <li onclick="selectSugerencia(formModificacionMasivaRecurso, this.innerHTML, 'usuarioActualF')">${nombre}</li>
                                            </c:forEach>
                                    </ul>                                                  
                                </div>     

                            </td>                               
                        </tr>
                    </tbody>
                </table>
                <br>
                <table class="showResultModal" align="center" style="width: 700px;">                      
                    <thead>
                        <tr>
                            <th style="width: 15px;">Sel</th>
                            <th style="width: 15px;">ID</th>
                            <th>No. Serie</th>
                            <th style="width: 120px;">Tipo</th>
                            <th style="width: 120px;">Marca</th>
                            <th style="width: 120px;">Modelo</th>
                            <th style="width: 120px;">Anterior</th>
                            <th style="width: 120px;">Actual</th>
                            <th style="width: 120px;">Estatus</th>
                            <th style="width: 120px;">Oficina</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${recursosEncontrados}" var="recurso">
                            <tr>
                                <td><input type="checkbox" name="rec_${recurso[0]}" value="${recurso[0]}"></td>
                                    <c:forEach items="${recurso}" var="columna" varStatus='indiceRec'>
                                        <c:if test='${indiceRec.index le 8}'>
                                        <td>${columna}</td>                                                            
                                    </c:if>
                                </c:forEach>
                            </tr>                                            
                        </c:forEach> 
                    </tbody>
                </table>
                <br>
                <input type="button" value="Guardar" name="enviar" onclick="modificacionMasiva(formModificacionMasivaRecurso);"/>
            </form>
                </div>
    </div>  
</c:if>            

</body>
<br><br>
</html>
</c:when>
<c:otherwise>
    <c:redirect url="index.htm" />
</c:otherwise>
</c:choose>
