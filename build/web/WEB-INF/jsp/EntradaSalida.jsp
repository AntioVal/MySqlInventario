<%-- 
    Document   : EntradaSalida
    Created on : 28/01/2015, 10:05:40 AM
    Author     : Luis-Valerio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<c:choose>
    <c:when test="${sessionScope.usuarioActual != null && sessionScope.usuarioActual.nombreU != null}">

        <html>
            <head>
                <link rel="stylesheet" type="text/css" href="css/VentanasModales.css">
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
                <title>Entrada y Salida</title>
                <!-- Especificamos el Logo en pequeño -->
                <link rel="shortcut icon" href="Imagen/gp_logo.png">
            </head>
            <body>
                <%@include file="Menu.jsp"%>
                <!--<script language="javascript" type="text/javascript" src="scripts/popCalendar.js"></script>-->
                <script async  language="javascript" type="text/javascript" src="scripts/login.js"></script>              

                <c:if test="${descargar != null && descargar == 'descargar'}">
                    <table border="0" width="100%">
                        <tbody>
                            <tr>
                                <td align="center">
                                    <form action="ControlResguardo" name="imprimirResguardo" method="POST">    
                                        <input type="hidden" name="accion" value="imprimirResguardo:8"/>
                                        <input type="submit" style="color: red;" value="Descargar reporte" name="Imprimir" />
                                    </form>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                    <br>                                                                    
                </c:if>                 

                <%-- CREAR NUEVA RESGUARDO --%>
                <c:if test="${param.nuevo != null && param.nuevo== 'entradaSalida'}">
                    <form action="ControlResguardo" name="entradaSalida" method="POST">
                        <input type="hidden" name="accion" value="nuevaFsctura:10"/>
                        <input type="hidden" name="accionRec" value="nuevaFsctura:10"/>
                        <table align="center" class="cuadroContenido" cellspacing="30">
                            <tr align="center">
                                <td colspan="5">
                                    <titulo> Generar orden de Entrada/Salida </titulo>
                                </td>
                            </tr>                                        
                            <tr>
                                <td align="right">Entrada</td>
                                <td align="left"><input type="radio" name="tipoAccion" value="entrada" /> </td>
                                <td rowspan="2">Ubicacion</td>
                                <td rowspan="2"><textarea name="ubicacion" rows="4" cols="20">Escribir ubicación</textarea></td>
                                <td><input type="button" value="Seleccionar" name="ubicacionButton" /></td>
                            </tr>
                            <tr>
                                <td align="right">Salida</td> 
                                <td align="left"><input type="radio" name="tipoAccion" value="salida" /></td>
                            </tr>
                            <tr>
                                <td align="left">Descripci&oacute;n de recursos:</td>
                                <td colspan="3" align="center">
                                    <textarea name="recursos" rows="4" cols="40">Escribir detalle de recursos</textarea>    
                                </td> 
                                <td><input type="button" value="Seleccionar" name="ubicacionButton" /></td>
                            </tr>
                            <tr>
                                <td align="center" colspan="2">Destino/Origen</td>
                                <td align="left" colspan="2">
                                    <input type="text" name="destinoOrigen" value="Escribir el destino u origen del recurso, segun sea el caso." />    
                                </td> 
                                <td><input type="button" value="Seleccionar" name="destinoOrigenButton" /></td>
                            </tr>
                            <tr>
                                <td align="center" colspan="2">Propietario:</td>
                                <td align="left" colspan="2">
                                    <input type="text" name="propietario" value="Escribir propietario" />    
                                </td> 
                                <td><input type="button" value="Seleccionar" name="ubicacionButton" /></td>
                            </tr>
                            <tr>
                                <td>Mismos que ser&aacute;n:</td>
                                <td><input type="radio" name="estatus" value="prestados" />Prestados</td>
                                <td><input type="radio" name="estatus" value="donados" />Donados</td>
                                <td><input type="radio" name="estatus" value="vendidos" />Vendidos</td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td><input type="radio" name="estatus" value="desechados" />Desechados</td>
                                <td colspan="2"><input type="radio" name="estatus" value="reparados" />Reparados y devueltos</td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td><input type="radio" name="estatus" value="comprados" />Comprados</td>
                                <td colspan="3"><input type="radio" name="estatus" value="otros" />Otros
                                    &nbsp;<input type="text" name="otro" value="Escribir descripción" />
                                </td>                                
                            </tr>
                            <tr align="center">
                                <td colspan="4" align="center">
                                    <button name="enviarF"  value="enviarF" onclick="return enviar(nuevoResguardo, 'generaResguardo:4');"> Generar </button> 
                                </td>  
                            </tr>
                        </table>                                                       
                    </form> 

                    <%-- VENTANA MODAL PARA SELECCION DE RECURSOS A ASIGNAR --%>  
                    <div id="agregar" class="modalmask">
                            <div class="modalboxBig movedown" style="width: 1000px;">
                                    <a href="#close" title="Cerrar" class="close">X</a>
                            <form action="ControlRecurso" name="agregarRecurso" method="POST">    
                                <input type="hidden" name="accionRec" value="buscarBasicaRecurso:25"/>
                                <input type="hidden" name="accion" value=""/>
                                <input type="hidden" name="nombre" value=""/>
                                <input type="hidden" name="idRecursos" value=""/>
                                <h3 align="center"> BUSCAR Y SELECCIONAR RECURSOS </h3>
                                <table><tbody>                                                               
                                        <tr><td>ID: </td>
                                            <td>                        
                                                <input size="15" type="text" name="idRecurso" value="${idRecurso}" onchange="agregarRecurso.submit();" style="width:200px;"/>
                                            </td>
                                            <td width="100px"> &nbsp;</td>
                                            <td>No. Serie</td>
                                            <td>  
                                                <input size="15" type="text" name="noSerie" value="${noSerie}" onchange="agregarRecurso.submit();" style="width:200px;"/>
                                            </td>
                                        </tr>                               
                                        <tr>                      
                                            <td>Marca</td>
                                            <td>   
                                                <input size="15" type="text" name="marca" value="${marca}" onchange="agregarRecurso.submit();" style="width:200px;"/>
                                            </td> 
                                            <td width="100px"> &nbsp;</td>
                                            <td>Modelo </td>
                                            <td>
                                                <input size="15" type="text" name="modelo" value="${modelo}" onchange="agregarRecurso.submit();" style="width:200px;"/>
                                            </td>                               
                                        </tr>
                                        <tr>                      
                                            <td>Oficina</td>
                                            <td>
                                                <input size="15" type="text" name="oficina" value="${oficina}" onchange="agregarRecurso.submit();" style="width:200px;"/>
                                            </td>  
                                            <td width="100px"> &nbsp;</td>
                                            <td>&Aacute;rea</td>
                                            <td>
                                                <input size="15" type="text" name="area" value="${area}" onchange="agregarRecurso.submit();" style="width:200px;"/>
                                            </td>                             
                                        </tr>
                                        <tr>                      
                                            <%--                                            <td>Usuario Anterior : </td>
                                                                                        <td>                        
                                                                                            <input type="text" name="usuarioAnteriorF" id="usuarioAnteriorF" onchange="return buscaUsuario(agregarRecurso, 'usuarioAnteriorF:27', 'usuarioAnteriorF');"  value="${usuarioAnteriorF}" style="width:200px;" />
                                                                                            <div class="dropdown">
                                                                                                <ul class="result" id="sugerenciaAnt">
                                                                                                    <c:forEach items="${usuarioAnteriorF_catch}" var="nombre">
                                                                                                        <li onclick="selectSugerencia(agregarRecurso, this.innerHTML, 'usuarioAnteriorF')">${nombre}</li>
                                                                                                        </c:forEach>
                                                                                                </ul>                                                  
                                                                                            </div>  
                                                                                        </td>
                                                                                        <td width="100px"> &nbsp;</td> --%>
                                            <td>Empleado : </td>
                                            <td>
                                                <input type="text" name="usuarioActualF" id="usuarioActualF" onchange="return buscaUsuario(agregarRecurso, 'usuarioActualF:27', 'usuarioActualF');"  value="${usuarioActualF}" style="width:200px;" />
                                                <div class="dropdown">
                                                    <ul class="result" id="sugerenciaAct">
                                                        <c:forEach items="${usuarioActualF_catch}" var="nombre">
                                                            <li onclick="selectSugerenciaActual(this.innerHTML, 'usuarioActualF')">${nombre}</li>
                                                            </c:forEach>
                                                    </ul>                                                  
                                                </div>     

                                            </td>                               
                                        </tr> 
                                        <!--<tr><td>&nbsp;</td></tr>-->
                                        <tr>
                                            <td colspan="6" align="right"> <input type="submit" value="Buscar" name="Buscar" /></td>
                                        </tr>
                                    </tbody>
                                </table>

                                <c:if test="${recursosResguardo ne null}">
                                    <br>
                                    <table border="0" width="100%">
                                        <tbody>
                                            <tr>
                                                <td colspan="7" align="right"> &nbsp;</td>
                                                <td align="right">
                                                    <input type="button" value="Agregar selecci&oacute;n" name="enviarAdd" onclick="modificacionMasiva(agregarRecurso);"/>
                                                </td>
                                            </tr>
                                        </tbody>
                                    </table>
                                    <br>
                                    <table class="showResultModal" align="center" width="100%"> 
                                        <thead>
                                            <tr>
                                                <th style="width: 20px;">Sel</th>
                                                <th style="width: 20px;">ID</th>
                                                <th>No. Serie</th>
                                                <th>Modelo</th>
                                                <th>Marca</th>                                                
                                                <th>Oficina</th>
                                                <th>&Aacute;rea</th>
                                                <th>Empleado</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${recursosResguardo}" var="recurso">
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
                                    <%--                                    <input style="text-align: right;" align="right" type="button" value="Agregar selecci&oacute;n" name="enviarAdd" onclick="modificacionMasiva(agregarRecurso);"/>                                    
                                    --%>
                                    <br>                                  
                                </c:if>
                            </form>
                                </div>
                    </div>  
                    <%--</c:if>--%>                             


                </c:if>         

                <c:if test="${param.buscar != null && param.buscar== 'resguardo'}">
                    <br><br>
                    <form action="ControlResguardo" name="buscarResguardo" method="POST">
                        <input type="hidden" name="accion" value="buscarResguardo:12"/>
                        <table align="center" class="cuadroContenido" width="60%" cellspacing="10">
                            <tr align="center"><td colspan="4">
                            <Titulo> 
                                B&uacute;squeda de orden de resguardo<br><br>
                            </Titulo>
                            </td></tr>
                            <tr align="left" colspan="4" width="10">
                                <td align="left"> Folio</td>
                                <td align="left"> <input size="10" type="text" name="folio" value="${folio}" onchange="return enviar(buscarResguardo, 'buscarResguardo:12');" /> </td>
                                <td align="left"> Usuario de asginaci&oacute;n</td>
                                <td align="left"> <input size="10" type="text" name="usuarioAsignacion" value="${usuarioAsignacion}" onchange="return enviar(buscarResguardo, 'buscarResguardo:12');" /> </td>
                            </tr>       
                            <tr align="left" colspan="4" width="10">
                                <td align="left"> Fecha de creaci&oacute;n entre </td>
                                <td align="left"> <input  size="10" type="text" name="fechaIni" value="${fechaIni}" style='cursor: pointer;' onclick="return popUpCalendar(this, buscarResguardo.fechaIni, 'yyyy-mm-dd');" /> </td>
                                <td align="center"> y </td>
                                <td align="left"> <input size="10" type="text" name="fechaFin" value="${fechaFin}" style='cursor: pointer;' onclick="return popUpCalendar(this, buscarResguardo.fechaFin, 'yyyy-mm-dd');"/> </td> 
                            </tr>
                            <tr>
                                <td colspan="4" align="right">
                                    <input type="submit" value="Buscar" onclick="return enviar(buscarResguardo, 'buscarResguardo:12');"/> 
                                </td></tr>
                        </table>
                    </form>

                    <c:if test="${resultado != null && resultado== 'resguardo'}"> 
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
                                        <th>Folio</th>
                                        <th>Usuario Asignado</th>
                                        <th>Fecha</th>                                                                                
                                        <th>Oficina</th>
                                        <th>&Aacute;rea</th>
                                        <th>Detalle</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${resguardos}" var="elemento" varStatus="indice">
                                        <tr>
                                            <td>${elemento.folio}</td>
                                            <td>${elemento.usuarioAsignacion}</td>
                                            <td>${elemento.fecha}</td>
                                            <td>${elemento.oficina}</td>
                                            <td>${elemento.area}</td>
                                            <td><form action="ControlResguardo" name="generarResguardo" method="POST">
                                                    <input type="hidden" name="accion" value="verDetalleResguardo:13"/>
                                                    <input type="hidden" name="listaRecursosId" value="${elemento.listaRecursosId}"/>
                                                    <input type="hidden" name="folioD" value="${elemento.folio}"/>
                                                    <input type="hidden" name="usuarioD" value="${elemento.usuarioAsignacion}"/>
                                                    <input type="hidden" name="oficinaD" value="${elemento.oficina}"/>
                                                    <input type="hidden" name="areaD" value="${elemento.area}"/>
                                                    <input type="hidden" name="fechaD" value="${elemento.fecha}"/>
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

        <%-- DETALLE DE ORDEN DE RESGUARDO --%>
        <c:if test="${param.detalle != null && param.detalle== 'resguardo'}">
            <form action="ControlResguardo" name="buscarResguardo" method="POST">
                <input type="hidden" name="accion" value="generarPDF:14"/>
                <input type="hidden" name="folio" value="${folioD}"/>
                <input type="hidden" name="usuario" value="${usuarioD}"/>
                <input type="hidden" name="oficina" value="${oficinaD}"/>
                <input type="hidden" name="area" value="${areaD}"/>
                <input type="hidden" name="fecha" value="${fechaD}"/>
                <input type="hidden" name="listaRecursosId" value="${listaRecursosId}"/>
                <c:if test="${recSelect != null && recSelect.size()>0}"> 
                    <br><br>                             
                    <table class="cuadroContenido" width="60%" cellspacing="10" align="center">
                        <tr align="center"><td colspan="6">
                        <Titulo> 
                            Detalle de orden de resguardo<br><br>
                        </Titulo>
                        </td></tr>
                        <tr>
                            <td align="right" colspan="3"> Folio</td>
                            <td align="left" colspan="3"><b><c:out value="${folioD}" /></b></td>
                        </tr>
                        <tr>
                            <td align="right" colspan="3"> Usuario</td>
                            <td align="left" colspan="3"><b><c:out value="${usuarioD}" /></b></td>
                        </tr>       
                        <tr>
                            <td align="right" colspan="3">  Oficina</td>
                            <td align="left" colspan="3"> <b> <c:out value="${oficinaD}" /></b></td>
                        </tr>
                        <tr>
                            <td align="right" colspan="3">  &Aacute;rea</td>
                            <td align="left" colspan="3"> <b> <c:out value="${areaD}" /> </b> </td>
                        </tr>       
                        <tr>
                            <td align="right" colspan="3">  Fecha</td>
                            <td align="left" colspan="3"> <b> <c:out value="${fechaD}" /> </b> </td>
                        </tr>       
                        <tr colspan="6">
                            <td align="center" colspan="6">
                        <titulo> Elementos incluidos</titulo>                                   
                        </td>                                                       
                        </tr>                                                     
                        <tr><td colspan="6">
                                <table class="showResult" width="100%">
                                    <thead>
                                        <tr>
                                            <th>ID Recurso</th>
                                            <th>Serie</th>
                                            <th>Tipo</th>
                                            <th>Marca</th>
                                            <th>Modelo</th>                                        
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${recSelect}" var="elemento" varStatus="indice">
                                            <tr>
                                                <td style="alignment-adjust: central;">${elemento.idRecurso}</td>
                                                <td>${elemento.noSerie}</td>
                                                <td>${elemento.tipo}</td>
                                                <td>${elemento.marca}</td>
                                                <td>${elemento.modelo}</td>                                                                                                     
                                            </tr>                                            
                                        </c:forEach> 
                                    </tbody>
                                </table>  
                            </td></tr>
                        <tr><td>&nbsp;</td></tr>
                        <tr align="center">
                            <td colspan="1"></td>
                            <td colspan="3" align="left">
                        <subtitulo> Seleccionar logo del reporte PDF </subtitulo>
                        <br><br>
                        <input type="radio" checked="checked" name="logo" value="logoGP.png" /> Grupo Proyectos <br>
                        <input type="radio" name="logo" value="logoSofom.jpg" /> Soluciones M&uacute;ltiples Empresariales<br>
                        <input type="radio" name="logo" value="logoBeraka.jpg" /> Beraka <br>
                        </td>  
                        </tr>                        
                        <tr>
                            <td align="center" colspan="6">
                                <input type="submit" value="Generar PDF"/> 
                            </td></tr>   
                        <tr><td>&nbsp;</td></tr>
                    </table>                                        
                </c:if>                            
            </form>
        </c:if>                        
    </body>
</html>
</c:when>
<c:otherwise>
    <c:redirect url="index.htm" />
</c:otherwise>
</c:choose>
