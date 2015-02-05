<%-- 
    Document   : Resultado
    Created on : 27/08/2014, 12:31:04 PM
    Author     : Luis-Valerio
--%>

<%@page import="java.util.Vector"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
                    <table align="center" class="tablaBuscar" width="60%" cellspacing="10">
                    <tr align="center">
                        <td colspan="4">
                            <div class="TituloBuscar">Resultado de b&uacute;squeda</div>
                        </td>
                    </tr>
                    <tr> <td colspan="4">
                            <div class="texto" align="right">${Coincidencias}</div>
                        </td> 
                    </tr>
                    <tr>
                     <td colspan="4">
                     <table class="tablaResultado" align="center" width="100%" border="1">
                        <%
                        Vector<String> vecResult = (Vector<String>)pageContext.findAttribute("resultBusqueda");
                        String[] valor=null;
                        %>
                                <tbody>
                        <%
                        for(int i=0;i<vecResult.size();i++){
//                            valor = vecResult.get(i);
                            //se escriben los titulos de las columnas
                        %>
                                   <tr>
                        <%
                                for(int j=1;j<valor.length;j++){
                        %>
                                <td>
                                <%=valor[j]%>
                                </td>
                        <%
                                }
                                if(i==0){
                        %>
                        <td>Detalles de factura</td>
                                
                        <%  }else{   %>
                        <td><form action="ControlFactura" method="POST">
                            <input type="hidden" name="accion" value="mostrarDetallesFactura:4"/>
                            <input type="hidden" name="idFactura" value="<%=valor[0].toString()%>"/>
                            <input type="hidden" name="Folio" value="<%=valor[1].toString()%>"/>
                            <input type="hidden" name="proveedor" value="<%=valor[3].toString()%>"/>
                            <input type="submit" value="Ver"/>
                            </form>
                        </td>
                        <%  }
                            }
                        %>
                        </tr> 
                        </tbody>
                    </table>  
                        </td>
                    </tr>
                </table>
    </body>
</html>
