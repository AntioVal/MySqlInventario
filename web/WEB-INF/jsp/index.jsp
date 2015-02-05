


<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <title> Inventarios </title>
        <!-- Especificamos el Logo en pequeño -->
        <link rel="shortcut icon" href="Imagen/gp_logo.png">
        <!-- Especificamos las palabras claves al realizar la busqueda en algun buscador -->
        <!--<meta name="keywords" content=""/>-->
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">     
        <!-- Aqui importamos los estilos que necesitemos-->
        <link rel="stylesheet" type="text/css" href="css/formato.css">
        <!-- Aqui importamos los scripts que necesitemos-->
        <script language="javascript" type="text/javascript" src="scripts/login.js"></script>
    </head>

    <body 
        <c:if test="${sessionScope.msgErrores != null && sessionScope.msgErrores != ''}">
            onLoad="alert('<c:out value="${sessionScope.msgErrores}"/>');"
            <c:remove var="msgErrores" scope="session" />
        </c:if>
        >

        <c:remove var="usuarioActual" scope="session" />
        <!--<spring:nestedPath path="name">   -->       
        <form name="formLogin" method="post" action="CompruebaLogin">
            <input type="hidden" name="accion" value=""/>

            <div align="center">
                <table align="center"  class="form-noindent">
                    <tr><td colspan="2">&nbsp;</td></tr>
                    <tr><td align="center" colspan="2" class="Titulo"> 
                            <img src="Imagen/logoGP.png" width="150" height="70"/></td>
                    </tr>
                    <tr><td colspan="2" >&nbsp;</td></tr>
                    <tr><td colspan="2" class="Titulo" align="center"> SISTEMA DE INVENTARIO  </td></tr>
                    <tr><td colspan="2">&nbsp;</td></tr>
                    <tr>
                        <td>
                            <table align="center" >
                                <tr class="Subtitulo">
                                    <td>Usuario</td>
                                    <td align="left">
                                        <input type="text" name="usuario" style="width:120px;"/>
                                    </td>
                                </tr>
                                <tr class="Subtitulo">
                                    <td>Contraseña</td>
                                    <td align="left">
                                        <input type="password" name="contrasenna" style="width:120px;"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr><td colspan="2">&nbsp;</td></tr>
                    <tr align="center">
                        <td colspan="2">
                            <input class="myButton" type="button" name="login" value="Acceder" onClick="return loginUsuario(formLogin, 'login')"/>
                        </td>
                    </tr>

                    <tr><td colspan="2">&nbsp;</td></tr>
                </table>
            </div>
        </form>
        <%  response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1 
            response.setHeader("Pragma", "no-cache"); //HTTP 1.0 
            response.setHeader("Cache-Control", "no-store");
            response.setDateHeader("Expires", 0); //prevents caching at the proxy server  
        %>
    </body>
</html>

