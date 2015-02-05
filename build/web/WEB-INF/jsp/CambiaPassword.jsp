<%-- 
    Document   : Cuenta
    Created on : 22/01/2014, 12:20:48 PM
    Author     : luis-valerio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<c:choose>
    <c:when test="${sessionScope.usuarioActual != null && requestScope.passwordChange !=null}">
        
<html>
    <head>
        <!--<link rel="stylesheet" type="text/css" href="css/Cuenta.css">-->
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Modificar Contrase&ntilde;a</title>
        <!-- Especificamos el Logo en pequeño -->
        <link rel="shortcut icon" href="Imagen/gp_logo.png">
        <!--importamos los archivos necesarios de alertas js-->
        <jsp:include page="Menu.jsp" flush="true" /> 
        <script language="javascript" type="text/javascript" src="scripts/mensajes.js"></script> 
    </head>
    <body <c:if test="${sessionScope.msgErrores != null && sessionScope.msgErrores != ''}">
             onLoad="alert('<c:out value="${sessionScope.msgErrores}"/>');"          
            <c:remove var="msgErrores" scope="session" />
          </c:if>
             >
        <br><br>
          <table align="center" width="40%" class='cuadroContenido'>
                    <tr><td colspan="3" align="center">
                    <titulo> Cambiar contrase&ntilde;a </titulo>
                    </td></tr>
                    <tr> <td width="10%" rowspan="10"> &nbsp; </td>
                    <td>
                    <table>
                        <form action="ControlCuenta" method="POST" name="formDatosCuenta">
                           <input type="hidden" name="accion" value=""/>
                           <input type="hidden" name="nombreU" value="${nombreU}"/>
                        <tr> 
                            <td>Ingresa tu contrase&ntilde;a actual:</td>
                        </tr>
                        <tr> 
                            <td><input name="oldPass" size="20" type="password" value=""/></td>
                        </tr>    
                        <tr> 
                            <td>Ingresa la nueva contrase&ntilde;a:</td>
                        </tr>
                        <tr> 
                        <td><input name="newPass1" size="20" type="password" value=""/></td>
                        </tr> 
                         <tr> 
                             <td> Confirma nueva contrase&ntilde;a:</td>
                        </tr>
                        <tr> 
                        <td><input name="newPass2" size="20" type="password" value=""/></td>
                        </tr>   
                        <tr><td> &nbsp; </td></tr>  
                        <tr align='center' colspan="2">
                            <td> <button onclick="return cambiaPassword(formDatosCuenta,'GuardarModificarPassword:6')" > Guardar </button>  </td>
                            <td> <button onclick="return enviar(formDatosCuenta,'obtenDatosCuenta:1')" > Cancelar </button>  </td>
                        </tr>                        
                        </form>  
                    </table>
                    </td>
                    <td  colspan="1" width="10%" rowspan="10"> &nbsp; </td>
                    </tr>
                    <tr><td colspan="3"> &nbsp; </td></tr>    
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