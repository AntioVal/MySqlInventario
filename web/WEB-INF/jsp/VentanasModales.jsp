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

        <%-- SELECCION CON SELECT --%>
        <c:if test="${filtroTipo!=null && filtroTipo=='filtroTipo'}">
            <div id="cambiarTipoRecurso" class="modalmask">
                    <div class="modalbox movedown">
                            <a href="#close" title="Close" class="close">X</a>                           
                    <form name="datosTipoForm" action="ControlTipo" method="POST">
                        <table align="center">
                            <tbody>
                            <input type="hidden" name="accion"/>
                            <input type="hidden" name="accionRec"/>
                            <input type="hidden" name="idRecurso" value="${detalleRecurso.idRecurso}"/>
                            <tr>
                                <td colspan="6"> 
                                <h3 align="center"> SELECIONAR OTRO TIPO DE RECURSO </h3>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="6"> &nbsp;</td>
                            </tr>                            
                            <tr>
                                <td>Tipo</td>
                                <td><select name="tipo" onchange="return enviar(datosTipoForm, 'filtraTipos_1:3');">
                                        <option>---seleccione---</option>
                                        <c:forEach items="${tipos}" var="tipe">
                                            <c:choose>
                                                <c:when test="${detalleTipo.tipo == tipe}">
                                                    <option selected>${tipe}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option>${tipe}</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </select>
                                </td>                                                 
                            </tr>
                            <tr>
                                <%--<td><input type="text" class="textInput" size="10" name="noSerie" disabled="true" value="${detalleTipo['tipo']}"/></td>--%>
                                <td>Marca</td>
                                <td><select name="marca" onchange="return enviar(datosTipoForm, 'filtraTipos_2:3');">
                                        <option>---seleccione---</option>
                                        <c:forEach items="${marcas}" var="marc">
                                            <c:choose>
                                                <c:when test="${detalleTipo.marca == marc}">
                                                    <option selected>${marc}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option>${marc}</option>
                                                </c:otherwise>
                                            </c:choose>                                       
                                        </c:forEach>
                                    </select>
                                </td> 
                            </tr>
                            <tr>
                                <%--<td><input type="text" class="textInput" size="10" name="estado" disabled="true" value="${detalleTipo['marca']}"/></td>--%>
                                <td>Modelo</td>
                                <td><select name="modelo" onchange="return enviar(datosTipoForm, 'filtraTipos_2:3');">
                                        <option>---seleccione---</option>
                                        <c:forEach items="${modelos}" var="model">
                                            <c:choose>
                                                <c:when test="${detalleTipo.modelo == model}">
                                                    <option selected>${model}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option>${model}</option>
                                                </c:otherwise>
                                            </c:choose>                                        
                                        </c:forEach>
                                    </select>
                                </td>                                                  
                                <%--<td><input type="text" class="textInput" size="10" name="costo" disabled="true" value="${detalleTipo['modelo']}"/></td> --%>
                            </tr>
                            <tr><td>&nbsp;</td></tr>
                            <tr align="center">
                                <td colspan="2"><input type="button" value="Guardar cambios" onClick="return guardarDependencia(datosTipoForm, 'modificarTipoRecurso:3', 'ControlRecurso');" name="botonGuarda"/></td> 
                            </tr>
                            <tr>
                            </tr>
                            </tbody>
                        </table>
                    </form>
                        </div>
            </div>
        </c:if>

        <c:if test="${filtroUbicacion!=null && filtroUbicacion=='filtroUbicacion'}">
            <div id="cambiarUbicacionRecurso" class="modalmask">
                    <div class="modalbox movedown">
                            <a href="#close" title="Close" class="close">X</a>
                    <form name="datosUbicacionForm" action="ControlUbicacion" method="POST">                 
                        <table align="center">
                            <tbody>
                            <input type="hidden" name="accion"/>
                            <input type="hidden" name="accionRec"/>
                            <input type="hidden" name="idRecurso" value="${detalleRecurso.idRecurso}"/>                           
                            <tr>
                                <td colspan="6">                                     
                                    <h3 align="center"> CAMBIAR UBICACI&Oacute;N DEL RECURSO </h3>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="6"> &nbsp;</td>
                            </tr>
                            <tr>
                                <td>Oficina</td>
                                <td><select name="oficina" onchange="return enviar(datosUbicacionForm, 'filtraUbicaciones:3');">
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
                            </tr>
                            <tr>
                                <td>&Aacute;rea</td>
                                <td><select name="area" >
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
                            <tr><td>&nbsp;</td></tr>
                            <tr align="center">
                                <td colspan="2"><input type="button" value="Guardar cambios" onClick="return guardarDependencia(datosUbicacionForm, 'modificarUbicacionRecurso:4', 'ControlRecurso');" name="botonGuarda"/></td> 
                            </tr>
                            </tbody>
                        </table>
                    </form>
                        </div>
            </div>
        </c:if>
        
        <!-- VENTANA MODAL DE BUSQUEDA Y SELECCION DE FACTURA -->
        <c:if test="${filtroFactura!=null && filtroFactura=='filtroFactura'}">
            <div id="cambiarFacturaRecurso" class="modalmask">
                    <div class="modalboxBig movedown">
                            <a href="#close" title="Close" class="close">X</a>
                    <form name="datosFacturaForm" action="ControlFactura" method="POST"> 
                            <input type="hidden" name="accion"/>
                        <table align="center">
                            <tbody>                      
                            <tr>
                                <td colspan="6">                                     
                                    <h3 align="center"> BUSCAR Y SELECCIONAR FACTURA </h3>
                                </td>
                            </tr>
                            <tr>
                                <td>ID</td>
                                <td><input type="text" name="idFactura" value="${idFactura}" onchange="return enviar(datosFacturaForm, 'filtraFacturas:12');"/></td>                                                  
                            </tr>
                            <tr>
                                <td>Folio</td>
                                <td><input type="text" name="folio" value="${folio}" onchange="return enviar(datosFacturaForm, 'filtraFacturas:12');"/></td>                                                  
                            </tr>
                            <tr>
                                <td>Proveedor</td>
                                <td><input type="text" name="razonSocial" value="${razonSocial}" onchange="return enviar(datosFacturaForm, 'filtraFacturas:12');"/></td>                                                   
                            </tr>                            
                            <tr>
                                <td>Art&iacute;culos</td>
                                <td><input type="text" name="articulos" value="${articulos}" onchange="return enviar(datosFacturaForm, 'filtraFacturas:12');"/></td>                                                   
                            </tr>
                            <tr>
                                <td>Monto total</td>
                                <td><input type="text" name="montoConIVA" value="${montoConIVA}" onchange="return enviar(datosFacturaForm, 'filtraFacturas:12');"/></td>                                                 
                            </tr>                            
                            </tbody>
                        </table>
                    </form>
                    <c:if test="${resultado != null && resultado== 'factura'}"> 
                        <br>                         
                        <table class="cuadroContenido" width="90%" cellspacing="20" align="center">                                                    
                            <tr><td>
                            <table class="showResultModal" width="100%">
                                <thead>
                                    <tr>
                                        <th style="width: 50px;">Folio</th>
                                        <th style="width: 150px;">Compra</th>
                                        <th style="width: 30px;">Art&iacute;culos</th>
                                        <th style="width: 30px;">Monto</th>
                                        <th>Proveedor</th>
                                        <th>&nbsp;</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${resultBusqueda}" var="facElemento" varStatus="indice">
                                        <tr>
                                            <td>${facElemento.folio}</td>
                                            <td style="width: 200px;">${facElemento.fechaAdquisicion}</td>
                                            <td>${facElemento.articulos}</td>
                                            <td>${facElemento.montoConIVA}</td>
                                            <td>${facElemento.proveedor.razonSocial}</td>
                                            <td><form action="ControlRecurso" name="cambiarFacturaRecurso" method="POST">
                                                    <input type="hidden" name="accionRec" value="cambiarFacturaRecurso:26"/>
                                                    <input type="hidden" name="idFactura" value="${facElemento.idFactura}"/>
                                                    <input type="hidden" name="folio" value="${facElemento.folio}"/>
                                                    <input type="hidden" name="idRecurso" value="${detalleRecurso.idRecurso}"/>
                                                    <button value="enviar" onclick="return enviarRec(cambiarFacturaRecurso, 'cambiarFacturaRecurso:26');"> Vincular </button>
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


        <!--  Se busca y obtiene la licencia para vincular a un recurso -->
        <c:if test="${filtroLicencia!=null && filtroLicencia=='filtroLicencia'}">
            <div id="seleccionarLicencia" class="modalmask">
                    <div class="modalbox movedown">
                            <a href="#close" title="Close" class="close">X</a>
                    <form name="selLicencia" action="ControlRecurso" method="POST">                 
                        <table align="center">
                            <tbody>
                                <!--<input type="hidden" name="accion"/>-->
                            <input type="hidden" name="accionRec"/>
                            <tr>
                                <td colspan="6"> 
                                    <h3 align="center"> BUSCAR Y SELECCIONAR LICENCIA </h3>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="6"> &nbsp;</td>
                            </tr>
                            <tr align="left">
                                <td align="left"> N&uacute;mero de licencia</td>
                                <td align="left"> <input size="10" type="text" name="idDatosLicencia" value="${idDatosLicencia}" onchange="return enviarRec(selLicencia, 'busquedaSelectLicencia:15');" /> </td>                         
                            </tr>
                            <tr>
                                <td align="left"> Tipo de licencia </td>
                                <td align="left"> <input size="10" type="text" name="nombre" value="${nombre}" onchange="return enviarRec(selLicencia, 'busquedaSelectLicencia:15');" /> </td>                         
                            </tr> 
                            <tr align="left" width="10">
                                <td align="left"> Clave de licencia</td>
                                <td align="left"> <input size="10" type="text" name="clave" value="${clave}" onchange="return enviarRec(selLicencia, 'busquedaSelectLicencia:15');" /> </td>                         
                            </tr>    
                            <tr><td>&nbsp;</td></tr>
                            </tbody>
                        </table>
                    </form>
                            
                    <c:if test="${resultado != null && resultado== 'licencia'}"> 
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
                                        <th>No. licencia</th>
                                        <th>Tipo licencia</th>
                                        <th>Clave</th>
                                        <th>Observaciones</th>
                                        <th>&nbsp;</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${licenciasSelect}" var="elemento" varStatus="indice">
                                        <tr>
                                            <td>${elemento.idDatosLicencia}</td>
                                            <td>${elemento.nombre}</td>
                                            <td>${elemento.clave}</td>
                                            <td>${elemento.observaciones}</td>
                                            <td><form action="ControlRecurso" name="vincularLicencia" method="POST">
                                                    <input type="hidden" name="accionRec" value="vincularLicencia:23"/>
                                                    <input type="hidden" name="idDatosLicencia" value="${elemento.idDatosLicencia}"/>
                                                    <input type="hidden" name="idRecurso" value="${detalleRecurso.idRecurso}"/>
                                                    <button value="enviar" onclick="return vincularLicenciaF(vincularLicencia);"> Vincular </button>
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

        <c:if test="${filtroEstado!=null && filtroEstado=='filtroEstado'}">
            <div id="cambiarEstadoRecurso" class="modalmask">
                    <div class="modalbox movedown">
                            <a href="#close" title="Close" class="close">X</a>
                    <form name="datosEstadoForm" action="ControlEstado" method="POST">                 
                        <table align="center">
                            <tbody>
                            <input type="hidden" name="accion"/>
                            <input type="hidden" name="accionRec"/>
                            <input type="hidden" name="idRecurso" value="${detalleRecurso.idRecurso}"/>                           
                            <input type="hidden" name="idEstado" value="${detalleEstado.idEstadoRecurso}"/>                           
                            <tr>
                                <td colspan="6">                                     
                                    <h3 align="center"> CAMBIAR ESTATUS DEL RECURSO </h3>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="6"> &nbsp;</td>
                            </tr>
                            <tr>
                                <td>Nombre</td>
                                <td><select name="nombre" onchange="return enviar(datosEstadoForm, 'getEstado:3');">
                                        <c:forEach items="${nombres}" var="nombre">
                                            <c:choose>
                                                <c:when test="${detalleEstado.nombre == nombre}">
                                                    <option selected>${nombre}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option>${nombre}</option>
                                                </c:otherwise>
                                            </c:choose>                                        
                                        </c:forEach>
                                    </select>
                                </td>                                                  
                            </tr>
                            <tr>
                                <td>Descripci&oacute;n</td>
                                <td><textarea rows="3" cols="25" disabled="true" name="descripcion">${detalleEstado.descripcion}</textarea></td>                                              
                            </tr>
                            <tr align="center">
                                <td colspan="2"><input type="button" value="Guardar cambios" onClick="return guardarDependencia(datosEstadoForm, 'modificarEstadoRecurso:5', 'ControlRecurso');" name="botonGuarda"/></td> 
                            </tr>
                            </tbody>
                        </table>
                    </form>
                        </div>
            </div>
        </c:if>

        <c:if test="${selectUser!=null && selectUser=='selectUser'}">
            <div id="selectUser" class="modalmask">
                    <div class="modalbox movedown">
                            <a href="#close" title="Close" class="close">X</a>
                    <h3 align="center"> SELECCIONA EL USUARIO</h3>
                    <form name="seleccionaUsuario" action="ControlRecurso" method="POST">                 
                        <table align="center">
                            <tbody>
                            <input type="hidden" name="accionRec"/>                        
                            <tr>
                                <td colspan="6" rowspan="1"></td>
                            </tr>
                            <tr>
                                <td>Nombre</td>
                                <td>
                                    <input type="text" name="nombre" id="nombre" value="${nombre}" class="autosuggest" onkeyup="return buscaUsuario(seleccionaUsuario, 'buscaUsuario:10');"/>
                                    <div class="dropdown">
                                        <ul class="result" id="suggest">
                                            <c:forEach items="${nombres}" var="nombre">
                                                <li>${nombre}</li>
                                                </c:forEach>
                                        </ul>
                                    </div>                                     
                                </td>                                              
                            </tr>
                            <tr align="center">
                                <td colspan="2"><input type="button" class="buttonModificar" value="Seleccionar" onclick="return buscaUsuario(datosRecurso, 'buscaUsuario:10');" name="seleccionar"/></td> 
                            </tr>
                            </tbody>
                        </table>
                    </form>
                        </div>
            </div>
        </c:if>                   
        
        
        <%-- <div id="modificacionMasiva" class="modalmask">
                 <div class="modalboxBig movedown">
                         <a href="#close" title="Cerrar" class="close">X</a>
                 <h3 align="center"> Elegir recursos a modificar</h3>                
                 <form action="ControlRecurso" name="formModificacionMasivaRecurso" method="POST">    
                     <input type="hidden" name="accionRec" value="modificacionMasiva:8"/>
                     <input type="hidden" name="accion" value=""/>
                     <input type="hidden" name="idRecursos" value=""/>
                     <table><tbody>                        
                             <tr><td>Oficina: </td>
                                 <td>
                                     <select name="oficina" class="textInput" onchange="return cambiaAction(formModificacionMasivaRecurso, 'filtraUbicaciones:3', 'ControlUbicacion');" style="width:150px;">
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
                                     <select name="area" class="textInput" style="width:150px;">
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
                                     <select name="estado" class="textInput" style="width:150px;">
                                         <option>---seleccione---</option>
                                         <c:forEach items="${estados}" var="est">
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
                                 <td>Usuario Actual : </td>
                                 <td><input type="text" style="width:150px;" name="usuarioActual" value=""/> </td>
                                 <td>Usuario Anterior : </td>
                                 <td><input type="text" style="width:150px;" name="usuarioAnterior" value=""/> </td>
                             </tr>
                         </tbody>
                     </table>
                     <br>
                     <table class="tableScroll" align="center" width="100%">                      
                         <thead>
                             <tr>
                                 <th>Sel</th>
                                 <th>ID</th>
                                 <th>No. Serie</th>
                                 <th>Tipo</th>
                                 <th>Marca</th>
                                 <th>Usuario</th>
                                 <th>Estatus</th>
                                 <th>Licencias</th>
                                 <th>Oficina</th>
                             </tr>
                         </thead>
                         <tbody>
                             <c:forEach items="${recursosEncontrados}" var="recurso">
                                 <tr>
                                     <td><input type="checkbox" name="rec_${recurso[0]}" value="${recurso[0]}"></td>
                                         <c:forEach items="${recurso}" var="columna" varStatus='indiceRec'>
                                             <c:if test='${indiceRec.index le 7}'>
                                             <td>${columna}</td>                                                            
                                         </c:if>
                                     </c:forEach>
                                 </tr>                                            
                             </c:forEach> 
                         </tbody>
                     </table>
                     <br>
                     <input type="button" value="Modificar" name="enviar" onclick="modificacionMasiva(formModificacionMasivaRecurso);"/>
                 </form>
                     </div>
         </div>
         <div id="modal3" class="modalmask">
                 <div class="modalbox resize">
                         <a href="#close" title="Close" class="close">X</a>
                         <h2>REDIMENSIONAR</h2>
                         <p>También puedes redimensionar la ventana hasta hacerla desaparecer.</p>
                         <p>Las posibilidades que ofrece CSS3 son múltiples, tan solo hace falta un poco de imaginación para crear efectos realmente llamativos.</p>
                     </div>
         </div>
        --%>
    </body>
</html>
