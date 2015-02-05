<%-- 
    Document   : Menu
    Created on : 16/10/2014, 10:38:13 AM
    Author     : Luis-Valerio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang=''>
    <head>
        <meta charset='utf-8'>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="css/menuDesplegable.css">
        <script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>
        <title>Menu</title>
        <!-- Especificamos el Logo en pequeño -->
        <link rel="shortcut icon" href="Imagen/gp_logo.png">
    </head>
    <body <c:if test="${sessionScope.alerta != null && sessionScope.alerta != ''}">
            onLoad="alert('<c:out value="${sessionScope.alerta}"/>');"          
            <c:remove var="alerta" scope="session" />
        </c:if>
        >
        <!--<script async  language="javascript" type="text/javascript" src="scripts/login.js"></script>-->
        <table width="1300px">
            <tbody>
                <tr>
                    <td width="200px">&nbsp;</td>
                    <td width="900px">
                        <table border="0" cellpadding="1">
                            <tbody>
                                <tr>
                                    <td><img src="Imagen/logo.jpg" width="200" height="100"/></td>
                                    <td>&nbsp;</td>
                                </tr>
                            </tbody>
                        </table>
                    </td>
                    <td width="200px">&nbsp;</td>
                </tr>
                <tr>
                    <td colspan="3">&nbsp;</td>
                </tr>
                <tr>
                    <td width="200px">&nbsp;</td>
                    <td width="900px">
                        <div id='cssmenu'>
                            <ul>
                                <li><a href='Menu.htm'><span>INICIO</span></a></li>
                                <li class='active has-sub'><a href='#'><span>RECURSOS</span></a>
                                    <ul>
                                        <li class='has-sub'><a href='#'><span>FACTURAS</span></a>
                                            <ul>
                                                <c:if test="${sessionScope.usuarioActual != null && (sessionScope.usuarioActual.privilegio == 'Administrador') || sessionScope.usuarioActual.privilegio == 'Super'}">
                                                    <li>
                                                        <a href='Nuevo.htm?nuevo=factura&iR=1'>
                                                            <span>
                                                                Nuevo
                                                            </span>
                                                        </a>                                                     
                                                    </li>
                                                </c:if>
                                                <li class='last'>                                        
                                                    <a href='ControlFactura?accion=busquedaFactura:2'>
                                                        <span>
                                                            Buscar y Modificar
                                                        </span>
                                                    </a>
                                                </li>                                               
                                            </ul>
                                        </li>
                                        <li class='has-sub'><a href='#'><span>EQUIPOS</span></a>
                                            <ul>
                                                <c:if test="${sessionScope.usuarioActual != null && (sessionScope.usuarioActual.privilegio == 'Administrador') || sessionScope.usuarioActual.privilegio == 'Super'}">
                                                    <li>
                                                        <a href='Nuevo.htm?nuevo=recurso&iR=1'>
                                                            <span>
                                                                Nuevo
                                                            </span>
                                                        </a>                                                    
                                                    </li>
                                                </c:if>
                                                <li>                                        
                                                    <a href='ControlRecurso?accionRec=busquedaRecurso:2'>
                                                        <span>
                                                            Buscar y Modificar
                                                        </span>
                                                    </a>
                                                </li>
                                                <li class='last'>
                                                    <a href='Exportar.htm'>
                                                        <span>
                                                            Exportar
                                                        </span>
                                                    </a>                                                     
                                                </li>                                                 
                                            </ul>
                                        </li>
                                    </ul>
                                </li> 
                                <c:if test="${sessionScope.usuarioActual != null && (sessionScope.usuarioActual.privilegio == 'Administrador') || sessionScope.usuarioActual.privilegio == 'Super'}">
                                    <li class='active has-sub'><a href='#'><span>CAT&Aacute;LOGOS</span></a>
                                        <ul>
                                            <li class='has-sub'><a href='#'><span>PRODUCTOS</span></a>
                                                <ul>
                                                    <li>
                                                        <a href='Nuevo.htm?nuevo=tipoRecurso&iR=1'>
                                                            <span>
                                                                Nuevo
                                                            </span>
                                                        </a>                                              
                                                    </li>
                                                    <li class='last'>
                                                        <a href='ControlTipo?accion=busquedaTipoRecurso:1'>
                                                            <span>
                                                                Buscar y Modificar
                                                            </span>
                                                        </a>                                                    
                                                    </li>
                                                </ul>
                                            </li>
                                            <li class='has-sub'><a href='#'><span>EMPLEADOS</span></a>
                                                <ul>
                                                    <li>
                                                        <a href='Nuevo.htm?nuevo=empleado&iR=1'>
                                                            <span>
                                                                Nuevo
                                                            </span>
                                                        </a>                                                    
                                                    </li>
                                                    <li>
                                                        <a href='Buscar.htm?busqueda=empleado&iR=1'>
                                                            <span>
                                                                Buscar y Modificar
                                                            </span>
                                                        </a>                                                    
                                                    </li>                                               
                                                </ul>
                                            </li>  
                                            <li class='has-sub'><a href='#'><span>LICENCIAS</span></a>
                                                <ul>
                                                    <li>
                                                        <a href='Nuevo.htm?nuevo=licencia&iR=1'>
                                                            <span>
                                                                Nuevo
                                                            </span>
                                                        </a>                                                    
                                                    </li>
                                                    <li>
                                                        <a href='Buscar.htm?busqueda=licencia&iR=1'>
                                                            <span>
                                                                Buscar y Modificar
                                                            </span>
                                                        </a>                                                      
                                                    </li>                                               
                                                </ul>
                                            </li>                
                                            <li class='has-sub'><a href='#'><span>OFICINAS</span></a>
                                                <ul>
                                                    <li>
                                                        <a href='Nuevo.htm?nuevo=oficina&iR=1'>
                                                            <span>
                                                                Nuevo
                                                            </span>
                                                        </a>                                                     
                                                    </li>
                                                    <li class='last'>
                                                        <a href='ControlUbicacion?accion=busquedaUbicacion:1'>
                                                            <span>
                                                                Buscar y Modificar
                                                            </span>
                                                        </a>                                                     
                                                    </li>                                                
                                                </ul>
                                            </li>
                                            <li class='has-sub'><a href='#'><span>PROVEDORES</span></a>
                                                <ul>
                                                    <li>
                                                        <a href='Nuevo.htm?nuevo=proveedor&iR=1'>
                                                            <span>
                                                                Nuevo
                                                            </span>
                                                        </a>                                                    
                                                    </li>
                                                    <li class='last'>
                                                        <a href='ControlProveedor?accion=busquedaProveedor:1'>
                                                            <span>
                                                                Buscar y Modificar
                                                            </span>
                                                        </a>                                                    
                                                    </li>
                                                </ul>
                                            </li> 
                                            <li class='has-sub'><a href='#'><span>ESTATUS</span></a>
                                                <ul>
                                                    <li>
                                                        <a href='Nuevo.htm?nuevo=estatus&iR=1'>
                                                            <span>
                                                                Nuevo
                                                            </span>
                                                        </a>                                                     
                                                    </li>
                                                    <li class='last'>
                                                        <a href='ControlEstado?accion=busquedaEstatus:1'>
                                                            <span>
                                                                Buscar y Modificar
                                                            </span>
                                                        </a>                                                     
                                                    </li>                                             
                                                </ul>
                                            </li>                               
                                        </ul>
                                    </li>
                                    <li class='active has-sub'><a href='#'><span>USUARIOS</span></a>
                                        <ul>
                                            <li class='has-sub'><a href='Nuevo.htm?nuevo=cuenta&iR=1'><span>Nuevo</span></a>
                                            </li>
                                            <li class='has-sub'><a href='ControlCuenta?accion=busquedaCuentas:7'><span>Buscar y Modificar</span></a>
                                            </li>
                                        </ul>
                                    </li> 
                                </c:if>
                                <li class='active has-sub'><a href='#'><span>RESGUARDO</span></a>
                                    <ul>
                                        <li>
                                            <a href='ControlResguardo?accion=crear:1'>
                                                <span>
                                                    Generar
                                                </span>
                                            </a>                                              
                                        </li>
                                        <li class='last'>
                                            <a href='Resguardo.htm?buscar=resguardo&iR=1'>
                                                <span>
                                                    Consultar
                                                </span>
                                            </a>                                                    
                                        </li>
                                    </ul>
                                </li>                                  
                                <li class='active has-sub'><a href='#'><span>ENTRADA/SALIDA</span></a>
                                    <ul>
                                        <li class='last'>
                                            <a href='EntradaSalida.htm?nuevo=entradaSalida&iR=1'>
                                                <span>
                                                    Crear
                                                </span>
                                            </a>                                                    
                                        </li>
                                    </ul>
                                </li>                                  
                                <li>
                                    <a href='ControlCuenta?accion=obtenDatosCuenta:1'>
                                        <span>
                                            MI CUENTA
                                        </span>
                                    </a>                                    
                                </li>                               
                                <li>
                                    <a href='ControlSesion?accion=cerrarSesion:9' onclick="window.history.forward(1);">
                                        <span>
                                            Salir
                                        </span>
                                    </a> 
                                </li>                                   
                            </ul>
                        </div>                        

                    </td>
                    <td width="200px">&nbsp;</td>
                </tr>
                <% if (!request.getServletPath().equals("/WEB-INF/jsp/Menu.jsp")) {%>
                <tr>
                    <td width="200px">&nbsp;</td>
                    <td width="900px">
                        <table width="100%">
                            <tbody>
                                <tr width="900px">
                                    <td align="left" width="50px">
                                        <div class="arrowText arrowLeft" onclick="javascript:history.go(-1);">REGRESAR</div>
                                    </td>
                                    <td width="800px">&nbsp;</td>
                                    <td width="50px" align="rigth">
                                        <div class="arrowText arrowRight" onclick="javascript:history.go(1);">SIGUIENTE</div>                                        
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </td>
                    <td width="200px">
                        &nbsp;
                    </td>
                </tr>
                <% }%>
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
            </tbody>
        </table>
        <br>        
    </body>
</html>
