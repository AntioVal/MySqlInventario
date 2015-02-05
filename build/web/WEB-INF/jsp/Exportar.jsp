<%-- 
    Document   : Exportar
    Created on : 14/01/2015, 11:01:39 AM
    Author     : Luis-Valerio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<c:choose>
    <c:when test="${sessionScope.usuarioActual != null && sessionScope.usuarioActual.nombreU != null}">

        <html>
            <head>
                <title>Exportar</title>
                <!-- Especificamos el Logo en pequeño -->
                <link rel="shortcut icon" href="Imagen/gp_logo.png">
            </head>
            <body>       
                <%@include file="Menu.jsp"%>
                <script language="javascript" type="text/javascript" src="scripts/AccionesExportar.js"></script>
                <form name="ExportarConsultaRecurso" action="ControlRecurso" method="POST">
                    <input type="hidden" name="seleccion" value="" />
                    <input type="hidden" name="accionRec" value="generaReporteExcel:28" />
                    <table class="cuadroContenido" cellpadding="10" align="center">                    
                        <tbody>
                            <tr align="center">
                                <td colspan="4" align="center">
                        <titulo> Personaliza tu reporte </titulo>
                        </td>
                        </tr>
                        <tr align="right">
                            <td align="right" colspan="4">
                               <input type="button" onclick="return marcarTodo(ExportarConsultaRecurso);" value="Seleccionar todo" name="seleccionarTodo" />
                           </td>
                        </tr>
                        <tr align="center">
                            <td colspan="4">
                        <subtitulo> Informaci&oacute;n de recurso </subtitulo>
                        <hr>
                        </td>
                        </tr>                      
                        <tr colspan="4" align="left">
                            <td><input type="checkbox" name="" value="rec.noSerie" /> serie</td>
                            <td><input type="checkbox" name="" value="estatus.nombre" /> estatus</td>
                            <td><input type="checkbox" name="" value="rec.costo" /> costo</td>
                            <td><input type="checkbox" name="" value="usuarioAnterior,usuarioActual" /> usuario</td>
                        </tr>
                        <tr colspan="4" align="left">
                            <td><input type="checkbox" name="" value="tipo.tipo" /> tipo</td>
                            <td><input type="checkbox" name="" value="tipo.marca" /> marca</td>
                            <td><input type="checkbox" name="" value="tipo.modelo" /> modelo</td>
                            <td><input type="checkbox" name="" value="tipo.descripcion" /> descripci&oacute;n</td>
                        </tr>                                  
                        <tr colspan="4" align="left">
                            <td><input type="checkbox" name="" value="licencia" /> licencia</td>
                            <td><input type="checkbox" name="" value="rec.tiempoVida" /> tiempo de vida(a&ntilde;os)</td>
                            <td><input type="checkbox" name="" value="rec.fechaRenovacion" /> fecha de renovaci&oacute;n</td>
                            <td><input type="checkbox" name="" value="rec.observacionesRec" />observaciones del recurso</td>
                        </tr>
                        <tr align="center">
                            <td colspan="4">
                        <subtitulo> Informaci&oacute;n de factura </subtitulo>
                        <hr>
                        </td>
                        </tr>                      
                        <tr>
                            <td><input type="checkbox" name="" value="fac.folio" /> folio</td>
                            <td><input type="checkbox" name="" value="fac.fechaAdquisicion" /> fecha de compra</td>
                            <td><input type="checkbox" name="" value="pro.razonSocial" /> razon social</td>
                            <td><input type="checkbox" name="" value="pro.rfc" /> rfc</td>
                        </tr>
                        <tr align="center">
                            <td colspan="4">
                        <subtitulo> Informaci&oacute;n de ubicaci&oacute;n </subtitulo>
                        <hr>
                        </td>
                        </tr>                      
                        <tr>
                            <td><input type="checkbox" name="" value="ubica.oficina" /> oficina</td>
                            <td><input type="checkbox" name="" value="ubica.area" /> &aacute;rea</td>
                        </tr>
                        <tr align="center">
                            <td colspan="4">
                        <subtitulo> Modo de ordenamiento </subtitulo>
                        <hr>
                        </td>
                        </tr>                      
                        <tr align="center">
                            <td>&nbsp;</td>
                            <td>Primero por </td>
                            <td><select name="ordenamiento1">
                                    <option value="rec.idRecurso" selected>id de recurso</option>
                                    <option value="rec.noSerie">n&uacute;mero de serie</option>
                                    <option value="ubica.oficina">oficina</option>
                                    <option value="ubica.area">area</option>                                    
                                    <option value="rec.costo">costo</option>
                                    <option value="fac.folio">folio de factura</option>
                                    <option value="fac.fechaAdquisicion">fecha de adquisici&oacute;n</option>
                                    <option value="rec.fechaRenovacion">fecha de renovaci&oacute;n</option>
                                    <option value="usuarioActual">usuario actual</option>
                                </select></td>
                                <td>&nbsp;</td>
                        </tr><tr align="center">
                            <td>&nbsp;</td>
                            <td>Despu&eacute;s por </td>
                            <td><select name="ordenamiento2">
                                    <option value="">-- seleccione --</option>
                                    <option value="rec.idRecurso">id de recurso</option>
                                    <option value="rec.noSerie">n&uacute;mero de serie</option>
                                    <option value="ubica.oficina">oficina</option>
                                    <option value="ubica.area">area</option>                                    
                                    <option value="rec.costo">costo</option>
                                    <option value="fac.folio">folio de factura</option>
                                    <option value="fac.fechaAdquisicion">fecha de adquisici&oacute;n</option>
                                    <option value="rec.fechaRenovacion">fecha de renovaci&oacute;n</option>
                                    <option value="usuarioActual">usuario actual</option>
                                </select></td>
                                <td>&nbsp;</td>
                        </tr>
                        <tr align="center">
                            <td>&nbsp;</td>
                            <td>Finalmente por </td>
                            <td><select name="ordenamiento3">
                                    <option value="" selected>-- seleccione --</option>
                                    <option value="rec.idRecurso">id de recurso</option>
                                    <option value="rec.noSerie">n&uacute;mero de serie</option>
                                    <option value="ubica.oficina">oficina</option>
                                    <option value="ubica.area">area</option>                                    
                                    <option value="rec.costo">costo</option>
                                    <option value="fac.folio">folio de factura</option>
                                    <option value="fac.fechaAdquisicion">fecha de adquisici&oacute;n</option>
                                    <option value="rec.fechaRenovacion">fecha de renovaci&oacute;n</option>
                                    <option value="usuarioActual">usuario actual</option>
                                </select></td>
                                <td>&nbsp;</td>
                        </tr>
                        <tr align="left">
                           <td align="right" colspan="4">
                               <input type="button" onclick="return enviar(ExportarConsultaRecurso);" value="Generar reporte" name="seleccionarTodo" />
                           </td>
                        </tr>                        
                        </tbody>
                    </table>
                </form>
            </body>
        </html>
    </c:when>
    <c:otherwise>
        <c:redirect url="index.htm" />
    </c:otherwise>
</c:choose>
