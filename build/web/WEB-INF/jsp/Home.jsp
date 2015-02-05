
<%-- 
    Document   : Home
    Created on : 15/01/2014, 05:58:01 PM
    Author     : luis-valerio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:choose>
    <c:when test="${sessionScope.usuarioActual!=null && sessionScope.usuarioActual.nombreU !=null && sessionScope.usuarioActual.nombreCompleto!=''}">
        <%--<c:out value="${sessionScope.myUser.privilegio}"/>--%>
        <%--<jsp:useBean id="usuarioActual" scope="session" class="beans.Usuario"></jsp:useBean>--%>

        <!DOCTYPE html>
        <html>
            <head>
                <title> Home </title>
                <!-- Especificamos el Logo en pequeño -->
                <link rel="shortcut icon" href="Imagen/gp_logo.png">
                <!-- Especificamos las palabras claves al realizar la busqueda en algun buscador -->
                <!--<meta name="keywords" content=""/>-->
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <!-- Aqui importamos los estilos que necesitemos-->
                <link rel="stylesheet" type="text/css" href="css/Menu.css">
                <!-- Aqui importamos los scripts que necesitemos-->
                <!--<script language="javascript" type="text/javascript" src="scripts/login.js"></script>-->  
            </head>
            <body  <c:if test="${sessionScope.msgErrores != null && sessionScope.msgErrores != ''}">
                    onLoad="alert('<c:out value="${sessionScope.msgErrores}"/>');"
                    <%--c:remove var="msgErrores" scope="session" /--%>
                </c:if>
                >        
                <!-- se inserta la tabla de menu-->
                <div class="recuadroMenu">
                    <table align="center" width="50%">                                  
                        <tr><td colspan="3">&nbsp;</td></tr>
                        <tr align="center"> 
                            <td>
                                <form action="Home.htm">
                                    <button class="myButton">
                                        <img src="Imagen/homeIcon.png" width="50" height="50"> </img>
                                        <br><br>
                                        Home
                                    </button> 
                                </form>
                            </td>
                            <td>
                                <form action="ControlSesion" method="POST">
                                    <input type="hidden" name="accion" value="panelPrincipalBusqueda:2"/>
                                    <button class="myButton">
                                        <img src="Imagen/buscarIcon.png" width="50" height="50"> </img>
                                        <br><br>
                                        Buscar
                                    </button> 
                                </form>  
                            </td>
                            <c:if test="${sessionScope.usuarioActual != null && (sessionScope.usuarioActual.privilegio == 'Administrador') || sessionScope.usuarioActual.privilegio == 'Super'}">
                                <td>
                                    <form action="ControlSesion" method="POST">
                                        <input type="hidden" name="accion" value="panelPrincipalModificacion:1"/>
                                        <button class="myButton">
                                            <img src="Imagen/modificarIcon.png" width="50" height="50"> </img>
                                            <br><br>
                                            Modificar
                                        </button> 
                                    </form> 
                                </td>
                                <td>
                                    <form action="Nuevo.htm">
                                        <button class="myButton">
                                            <img src="Imagen/nuevoIcon.png" width="50" height="50"> </img>
                                            <br><br>
                                            Nuevo
                                        </button> 
                                    </form>
                                </td>
                            </c:if>
                            <td>
                                <form action="ControlSesion" method="POST">
                                    <input type="hidden" name="accion" value="panelPrincipalBusqueda:2"/>
                                    <button class="myButton">
                                        <img src="Imagen/buscarIcon.png" width="50" height="50"> </img>
                                        <br><br>
                                        Inventario
                                    </button> 
                                </form>  
                            </td>     
                            <td>
                                <form action="ControlSesion" method="POST">
                                    <input type="hidden" name="accion" value="panelPrincipalBusqueda:2"/>
                                    <button class="myButton">
                                        <img src="Imagen/buscarIcon.png" width="50" height="50"> </img>
                                        <br><br>
                                        Cat&aacute;logos
                                    </button> 
                                </form>  
                            </td>                            
                            <td>
                                <form action="ControlCuenta">
                                    <input type="hidden" name="accion" value="obtenDatosCuenta:1"/>
                                    <button class="myButton">
                                        <img src="Imagen/cuentaIcon.png" width="50" height="50"> </img>
                                        <br><br>
                                        Cuenta
                                    </button>

                                </form>
                            </td>
                            <td>
                                <form action="ControlSesion" method="POST">
                                    <input type="hidden" name="accion" value="cerrarSesion:9"/>
                                    <button class="myButton" onclick='window.history.forward(1);'>
                                        <img src="Imagen/salirIcon.png" width="50" height="50"> </img>
                                        <br><br>
                                        Salir
                                    </button> 
                                </form>
                            </td>                   
                        </tr>
                        <tr><td colspan="3">&nbsp;</td></tr>
                    </table>
                </div>

                <c:if test="${sessionScope.usuarioActual.nombreU !='' && sessionScope.usuarioActual.nombreU !=null && sessionScope.usuarioActual.nombreCompleto!=''}">
                    <div class = "Titulo" align="CENTER">             
                        <c:out value="${sessionScope.Mensaje}"/> 
                        <c:out value=" ${sessionScope.usuarioActual.nombreCompleto}"/> <c:out value="         "/>    
                    </div>
                </c:if>

                
                
            </body>
        </html>
    </c:when>
    <c:otherwise>
        <c:redirect url="index.htm" />
    </c:otherwise>
</c:choose>