<%-- 
    Document   : VentanasModales
    Created on : 2/09/2014, 05:00:55 PM
    Author     : Luis-Valerio
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>        
    </head>
    <body>
        <link rel="stylesheet" type="text/css" href="css/VentanasModales.css">
        <script async  language="javascript" type="text/javascript" src="scripts/login.js"></script>
        

        <%-- SELECCION CON INPUTS Y MUESTRA TABLA PARA SELECCION DE UN PROVEEDOR--%>
        <c:if test="${filtroProveedor!=null && filtroProveedor=='filtroProveedor'}">
            <div id="selectProveedor" class="modalmask">
                    <div class="modalbox movedown">
                            <a href="#close" title="Close" class="close">X</a>
                            <h3 align="center"> BUSCA Y SELECIONA EL PROVEEDOR </h3>
                    <form name="formBuscarProveedor" action="ControlProveedor" method="POST">
                        <table align="center">
                            <tbody>
                            <input type="hidden" name="accion"/>
                            <tr>
                                <td>Razon Social</td>
                                <td align="left"> <input size="10" type="text" name="razonSocial" value="${razonSocial}" onchange="return enviar(formBuscarProveedor, 'selectProveedor:8');" /> </td>                                                
                            </tr>
                            <tr>
                                <td>R.F.C.</td>
                                <td align="left"> <input size="10" type="text" name="rfc" value="${rfc}" onchange="return enviar(formBuscarProveedor, 'selectProveedor:8');" /> </td>
                            </tr>
                            <tr>
                                <td>Domicilio fiscal</td>
                                <td align="left"> <input size="10" type="text" name="domicilioFiscal" value="${domicilioFiscal}" onchange="return enviar(formBuscarProveedor, 'selectProveedor:8');" /> </td>                                               
                            </tr>
                            <tr>
                            </tr>
                            </tbody>
                        </table>
                    </form>
                    <c:if test="${resultado != null && resultado== 'proveedor'}"> 
                        <br><br>                             
                        <table class="cuadroContenido" width="100%" cellspacing="20" align="center">
                            <tr colspan="6">
                                <td align="center" colspan="1">
                            <titulo> Coincidencias ... </titulo>                                   
                            </td>                                                       
                            </tr>                                                     
                            <tr><td>
                                    <table class="showResultModal" width="100%">
                                        <thead>
                                            <tr>
                                                <th>ID</th>
                                                <th>Razon social</th>
                                                <th>R.F.C.</th>
                                                <th>Domicilio fiscal</th>
                                                <th>&nbsp;</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${provedores}" var="elemento" varStatus="indice">
                                                <tr>
                                                        <td>${elemento.idProveedor}</td>
                                                        <td>${elemento.razonSocial}</td>
                                                        <td>${elemento.rfc}</td>
                                                        <td>${elemento.domicilioFiscal}</td>
                                                    <td><form action="ControlProveedor" name="seleccionarProveedor" method="POST">
                                                            <input type="hidden" name="accion" value="seleccion:9"/>
                                                            <input type="hidden" name="idProveedor" value="${elemento.idProveedor}"/>
                                                            <input type="hidden" name="razonSocial" value="${elemento.razonSocial}"/>
                                                            <input type="submit" value="Seleccion"/>
                                                        </form>
                                                    </td>                                                        
                                                </tr>                                            
                                            </c:forEach> 
                                        </tbody>
                                    </table>  
                                </td></tr>
                        </table>                                        
                    </c:if>                            

                        </div>
            </div>
        </c:if>        

        <%-- SELECCION CON INPUTS Y MUESTRA TABLA PARA SELECCION DE TIPO DE RECURSO--%>
        <c:if test="${filtroTipo!=null && filtroTipo=='filtroTipo'}">
            <div id="selectTipoRecurso" class="modalmask">
                    <div class="modalbox movedown">
                            <a href="#close" title="Close" class="close">X</a>
                            <h3 align="center"> BUSCA Y SELECIONA EL TIPO DE PRODUCTO </h3>
                    <form name="formBuscarTipoRecurso" action="ControlTipo" method="POST">
                        <table align="center">
                            <tbody>
                            <input type="hidden" name="accion"/>
                            <tr>
                                <td colspan="6" rowspan="1"></td>
                            </tr>
                            <tr>
                                <td>Tipo</td>
                                <td align="left"> <input size="10" type="text" name="tipo" value="${filtroTipoRec.tipo}" onchange="return enviar(formBuscarTipoRecurso, 'selectTipoRecurso:5');" /> </td>                                                
                            </tr>
                            <tr>
                                <td>Marca</td>
                                <td align="left"> <input size="10" type="text" name="marca" value="${filtroTipoRec.marca}" onchange="return enviar(formBuscarTipoRecurso, 'selectTipoRecurso:5');" /> </td>
                            </tr>
                            <tr>
                                <td>Modelo</td>
                                <td align="left"> <input size="10" type="text" name="modelo" value="${filtroTipoRec.modelo}" onchange="return enviar(formBuscarTipoRecurso, 'selectTipoRecurso:5');" /> </td>                                               
                            </tr>
                            <tr><td>&nbsp;</td></tr>                            
                            <tr>
                            </tr>
                            </tbody>
                        </table>
                    </form>
                    <c:if test="${resultado != null && resultado== 'tipoRecurso'}"> 
                        <br><br>                             
                        <table class="cuadroContenido" width="60%" cellspacing="20" align="center">
                            <tr colspan="6">
                                <td align="center" colspan="1">
                            <titulo> Coincidencias ... </titulo>                                   
                            </td>                                                       
                            </tr>                                                     
                            <tr><td>
                                    <table class="showResultModal" width="100%">
                                        <thead>
                                            <tr>
                                                <th>ID</th>
                                                <th>Tipo</th>
                                                <th>Marca</th>
                                                <th>Modelo</th>
                                                <th>&nbsp;</th>
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
                                                            <input type="hidden" name="accion" value="seleccion:7"/>
                                                            <input type="hidden" name="idTipo" value="${idTipos[indice.index]}"/>
                                                            <input type="hidden" name="marca" value="${marcas[indice.index]}"/>
                                                            <input type="hidden" name="modelo" value="${modelos[indice.index]}"/>
                                                            <input type="submit" value="Seleccion"/>
                                                        </form>
                                                    </td>                                                        
                                                </tr>                                            
                                            </c:forEach> 
                                        </tbody>
                                    </table>  
                                </td></tr>
                        </table>                                        
                    </c:if>                            

                        </div>
            </div>
        </c:if>


        <%-- SELECCION CON INPUTS Y MUESTRA TABLA PARA SELECCIONAR FACTURA--%>
        <c:if test="${filtroFactura!=null && filtroFactura=='filtroFactura'}">
            <div id="selectFactura" class="modalmask">
                    <div class="modalboxBig movedown">
                            <a href="#close" title="Close" class="close">X</a>
                            <h3 align="center"> BUSCA Y SELECIONA LA FACTURA </h3>
                    <form name="formBuscarFactura" action="ControlFactura" method="POST">
                        <table align="center">
                            <tbody>
                            <input type="hidden" name="accion"/>
                            <tr>
                                <td colspan="6" rowspan="1"></td>
                            </tr>
                            <tr>
                                <td>Folio</td>
                                <td align="left"> <input size="10" type="text" name="folio" value="${filtroFac.folio}" onchange="return enviar(formBuscarFactura, 'selectFactura:8');" /> </td>                                                
                            </tr>
                            <tr>
                                <td>Fecha de compra</td>
                                <td align="left"> <input size="10" type="text" name="fecha" value="${filtroFac.fecha}" onchange="return enviar(formBuscarFactura, 'selectFactura:8');" /> </td>
                            </tr>
                            <tr>
                                <td>Proveedor</td>
                                <td align="left"> <input size="10" type="text" name="proveedor" value="${filtroFac.proveedor}" onchange="return enviar(formBuscarFactura, 'selectFactura:8');" /> </td>                                               
                            </tr>
                            <tr><td>&nbsp;</td></tr>                            
                            <tr>
                            </tr>
                            </tbody>
                        </table>
                    </form>
                    <c:if test="${resultado != null && resultado== 'factura'}"> 
                        <br><br>                             
                        <table class="cuadroContenido" width="90%" cellspacing="15" align="center">
                            <tr colspan="6">
                                <td align="center" colspan="1">
                            <titulo> Coincidencias ... </titulo>                                   
                            </td>                                                       
                            </tr>                                                     
                            <tr><td>
                                    <table class="showResultModal" width="100%">
                                        <thead>
                                            <tr>
                                                <th style="width: 15px;">ID</th>
                                                <th>Folio</th>
                                                <th>Adquisici&oacute;n</th>
                                                <th>Proveedor</th>
                                                <th>&nbsp;</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${facturas}" var="elemento">
                                                <tr>
                                                        <td>${elemento[0]}</td>
                                                        <td>${elemento[1]}</td>
                                                        <td>${elemento[2]}</td>
                                                        <td>${elemento[3]}</td>
                                                        <td><form action="ControlFactura" name="seleccionFactura" method="POST">
                                                                <input type="hidden" name="accion" value="seleccion:9"/>
                                                                <input type="hidden" name="idFactura" value="${elemento[0]}"/>
                                                                <input type="hidden" name="folio" value="${elemento[1]}"/>
                                                                <input type="submit" value="Seleccion"/>
                                                            </form>
                                                        </td>                                                
                                                </tr>                                            
                                            </c:forEach> 
                                        </tbody>
                                    </table>  
                                </td></tr>
                        </table>                                        
                    </c:if>                                                        
                        </div>
            </div>
        </c:if>


        <%-- SELECCION CON INPUTS Y MUESTRA TABLA PARA SELECCIONAR UNA UBICACION--%>
        <c:if test="${filtroUbicacion!=null && filtroUbicacion=='filtroUbicacion'}">
            <div id="selectUbicacion" class="modalmask">
                    <div class="modalbox movedown">
                            <a href="#close" title="Close" class="close">X</a>
                            <h3 align="center"> BUSCA Y SELECIONA LA UBICACI&Oacute;N </h3>
                    <form name="formBuscarUbicacion" action="ControlUbicacion" method="POST">
                        <table align="center">
                            <tbody>
                            <input type="hidden" name="accion"/>
                            <tr>
                                <td colspan="6" rowspan="1"></td>
                            </tr>
                            <tr>
                                <td>Oficina</td>
                                <td align="left"> <input size="10" type="text" name="oficina" value="${filtroUbica.oficina}" onchange="return enviar(formBuscarUbicacion, 'selectUbicacion:8');" /> </td>                                                
                            </tr>
                            <tr>
                                <td>&Aacute;rea</td>
                                <td align="left"> <input size="10" type="text" name="area" value="${filtroUbica.area}" onchange="return enviar(formBuscarUbicacion, 'selectUbicacion:8');" /> </td>
                            </tr>
                            <tr><td>&nbsp;</td></tr>                            
                            <tr>
                            </tr>
                            </tbody>
                        </table>
                    </form>
                    <c:if test="${resultado != null && resultado== 'oficina'}"> 
                        <br><br>                             
                        <table class="cuadroContenido" width="60%" cellspacing="20" align="center">
                            <tr colspan="6">
                                <td align="center" colspan="1">
                            <titulo> Coincidencias ... </titulo>                                   
                            </td>                                                       
                            </tr>                                                     
                            <tr><td>
                                    <table class="showResultModal" width="100%">
                                        <thead>
                                            <tr>
                                                <th>ID</th>
                                                <th>Oficina</th>
                                                <th>&Aacute;rea</th>
                                                <th>&nbsp;</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${oficinas}" var="elemento">
                                                <tr>
                                                        <td>${elemento.idUbicacion}</td>
                                                        <td>${elemento.oficina}</td>
                                                        <td>${elemento.area}</td>
                                                        <td><form action="ControlUbicacion" name="seleccionOficina" method="POST">
                                                                <input type="hidden" name="accion" value="seleccion:9"/>
                                                                <input type="hidden" name="idUbicacion" value="${elemento.idUbicacion}"/>
                                                                <input type="hidden" name="oficina" value="${elemento.oficina}"/>
                                                                <input type="hidden" name="area" value="${elemento.area}"/>
                                                                <input type="submit" value="Seleccion"/>
                                                            </form>
                                                        </td>                                                
                                                </tr>                                            
                                            </c:forEach> 
                                        </tbody>
                                    </table>  
                                </td></tr>
                        </table>                                        
                    </c:if>                                                        
                        </div>
            </div>
        </c:if>

    </body>
</html>

