/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio;

import beans.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 *
 * @author Luis-Valerio
 */
public class GestionRecurso {

    static synchronized public Map getDetallesRecurso(String idRecurso, String noSerie) {
        Map resultado = null;
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";

        try {

            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            Query = " select costo,tiempoVida,fechaRenovacion,observacionesRec,usuarioActual,";
            Query += " usuarioAnterior,Tipo_idTipo,Factura_idFactura,UbicacionRecurso_idUbicacion,EstadoRecurso_idEstadoRecurso ";
            Query += " from `recurso`";
            Query += " where `idRecurso`='" + idRecurso + "' ";
            Query += " and `noSerie`='" + noSerie + "' ";

            ResultSet rstSQLServer = statement.executeQuery(Query);

            while (rstSQLServer.next()) {
                resultado = new HashMap();
                resultado.put("idRecurso", idRecurso);
                resultado.put("noSerie", noSerie);
                resultado.put("costo", rstSQLServer.getString("costo"));
                resultado.put("tiempoVida", rstSQLServer.getString("tiempoVida"));
                resultado.put("fechaRenovacion", rstSQLServer.getDate("fechaRenovacion"));
                resultado.put("observacionesRec", rstSQLServer.getString("observacionesRec"));
                resultado.put("usuarioActual", rstSQLServer.getString("usuarioActual"));
                resultado.put("usuarioAnterior", rstSQLServer.getString("usuarioAnterior"));
//                recurso.put("licencias", rstSQLServer.getString("licencias"));
                resultado.put("idTipo", rstSQLServer.getString("Tipo_idTipo"));
                resultado.put("idFactura", rstSQLServer.getString("Factura_idFactura"));
                resultado.put("idUbicacion", rstSQLServer.getString("UbicacionRecurso_idUbicacion"));
                resultado.put("idEstadoRecurso", rstSQLServer.getString("EstadoRecurso_idEstadoRecurso"));
            }

            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            Query = " select nombreCompleto from empleado where idEmpleado=" + resultado.get("usuarioActual") + "";
            rstSQLServer = statement.executeQuery(Query);
            while (rstSQLServer.next()) {
                resultado.put("usuarioActual", rstSQLServer.getString("nombreCompleto"));
            }
            Query = " select nombreCompleto from empleado where idEmpleado=" + resultado.get("usuarioAnterior") + "";
            rstSQLServer = statement.executeQuery(Query);
            while (rstSQLServer.next()) {
                resultado.put("usuarioAnterior", rstSQLServer.getString("nombreCompleto"));
            }

            rstSQLServer.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Exception:gestionRecurso-getDetallesRecurso():" + e.toString());
            resultado = null;
        } finally {
            instanciaConexion.Desconecta(connection);
        }
        return resultado;

    }

    static synchronized public Vector getRecursosPorId(String idRecursos) {
        Map recurso = null;
        Vector resultado = new Vector();
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";
        int i = 0;

        try {

            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            Query = " select rec.idRecurso,rec.noSerie, tipo.tipo, tipo.marca, tipo.modelo,rec.usuarioActual,";
            Query += " rec.usuarioAnterior, estatus.nombre, rec.fechaRenovacion ";
            Query += " from `recurso` as rec, `tipo` as tipo,`estadoRecurso` as estatus";
            Query += " where `idRecurso` IN (" + idRecursos + ") ";
            Query += " AND rec.tipo_idTipo= tipo.idTipo ";
            Query += " AND rec.EstadoRecurso_idEstadoRecurso= estatus.idEstadoRecurso ";
            Query += " order by idRecurso ";

//            System.out.println("Query:" + Query);

            ResultSet rstSQLServer = statement.executeQuery(Query);

            while (rstSQLServer.next()) {
                recurso = new HashMap();
                recurso.put("idRecurso", rstSQLServer.getString("idRecurso"));
                recurso.put("noSerie", rstSQLServer.getString("noSerie"));
                recurso.put("tipo", rstSQLServer.getString("tipo"));
                recurso.put("marca", rstSQLServer.getString("marca"));
                recurso.put("modelo", rstSQLServer.getString("modelo"));
                recurso.put("usuarioActual", rstSQLServer.getString("usuarioActual"));
                recurso.put("usuarioAnterior", rstSQLServer.getString("usuarioAnterior"));
                recurso.put("estatus", rstSQLServer.getString("nombre"));
                recurso.put("fechaRenovacion", rstSQLServer.getString("fechaRenovacion"));
                resultado.add(recurso);
            }

            for (Object rec : resultado) {
                Map temp = (HashMap) rec;
                String usuario = (temp.get("usuarioActual")).toString();
                connection = instanciaConexion.ConectaMySQL();
                statement = connection.createStatement();
                statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

                Query = " select nombreCompleto from empleado where idEmpleado=" + usuario + "";
                rstSQLServer = statement.executeQuery(Query);
                while (rstSQLServer.next()) {
                    temp.put("usuarioActual", rstSQLServer.getString("nombreCompleto"));
                    resultado.set(i, temp);
                }

                usuario = (temp.get("usuarioAnterior")).toString();
                connection = instanciaConexion.ConectaMySQL();
                statement = connection.createStatement();
                statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

                Query = " select nombreCompleto from empleado where idEmpleado=" + usuario + "";
                rstSQLServer = statement.executeQuery(Query);
                while (rstSQLServer.next()) {
                    temp.put("usuarioAnterior", rstSQLServer.getString("nombreCompleto"));
                    resultado.set(i, temp);
                }
                i++;
            }

            rstSQLServer.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Exception:gestionRecurso-getRecursosPorId():" + e.toString());
            resultado = null;
        } finally {
            instanciaConexion.Desconecta(connection);
        }
        return resultado;

    }

    /**
     * Se hace busqueda de un recurso filtrando todos los parametros posibles
     * asociados a el(BUQUEDA MÁXIMA)
     *
     * @param usuarioAnterior
     * @param usuarioActual
     * @param noSerie
     * @param filtroTipo
     * @param oficina
     * @param area
     * @param proveedor
     * @param estatus
     * @param licencia
     * @param costo
     * @param fechaAdIni
     * @param fechaAdFin
     * @param fechaReIni
     * @param fechaReFin
     * @return
     */
    static synchronized public Vector buscarFiltroRecurso(String usuarioAnterior, String usuarioActual, String noSerie,
            Tipo filtroTipo, String oficina, String area, String proveedor, String estatus, String licencia, String costo,
            String fechaAdIni, String fechaAdFin, String fechaReIni, String fechaReFin, String limite) {
        Vector recursoVector = new Vector();
        Vector resultados = new Vector();
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";
        int i = 0;
        float inferior = 0;
        float superior = 0;
        boolean evalCosto = false;

        if (!costo.isEmpty()) {

            try {
                String[] rangoCosto = costo.split("-");
                if (rangoCosto.length == 2) {
                    inferior = Float.parseFloat(rangoCosto[0]);
                    if (!rangoCosto[1].equals("mas")) {
                        superior = Float.parseFloat(rangoCosto[1]);
                    } else {
                        superior = 99999;
                    }
                    evalCosto = true;
                }
            } catch (Exception exc) {
                evalCosto = false;
            }
        }

        try {

            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            Query = " select rec.idRecurso,tipo.tipo,tipo.marca,tipo.modelo,ubica.oficina,rec.noSerie,rec.costo,rec.usuarioAnterior,";
            Query += " rec.usuarioActual,estatus.nombre,fac.folio,fac.fechaAdquisicion,pro.razonSocial,rec.fechaRenovacion";
            Query += " from `factura` as fac,`proveedor` as pro,`recurso` as rec,";
            Query += " `tipo` as tipo,`ubicacionRecurso` as ubica,`estadoRecurso` as estatus,`empleado`";
            if (!licencia.isEmpty()) {
                Query += " ,`recurso_has_datoslicencia` as licRelacion, `datoslicencia`";
            }
            Query += " where (";
            Query += " rec.noSerie LIKE '%" + noSerie + "%' ";
            if (!usuarioAnterior.isEmpty() || !usuarioActual.isEmpty()) {
                if (!usuarioAnterior.isEmpty() && usuarioActual.isEmpty()) {
                    Query += " AND empleado.nombreCompleto LIKE '%" + usuarioAnterior + "%' ";
                }
                if (usuarioAnterior.isEmpty() && !usuarioActual.isEmpty()) {
                    Query += " AND empleado.nombreCompleto LIKE '%" + usuarioActual + "%' ";
                }
            }
            Query += " AND tipo.tipo like '%" + filtroTipo.getTipo() + "%' ";
            Query += " AND tipo.marca like '%" + filtroTipo.getMarca() + "%' ";
            Query += " AND tipo.modelo like '%" + filtroTipo.getModelo() + "%' ";
            Query += " AND ubica.oficina like '%" + oficina + "%' ";
            Query += " AND ubica.area like '%" + area + "%' ";
            Query += " AND pro.razonSocial like '%" + proveedor + "%' ";
            if (!estatus.isEmpty()) {
                Query += " AND estatus.nombre='" + estatus + "' ";
            }
            if (!licencia.isEmpty()) {
                Query += " AND datoslicencia.nombre='" + licencia + "' ";
            }

            if (evalCosto) {
                Query += " AND rec.costo>=" + inferior + " ";
                Query += " AND rec.costo<" + (superior++) + " ";
            }
            if (!fechaAdIni.equals("") && !fechaAdFin.equals("")) {
                Query += " AND fac.fechaAdquisicion>='" + fechaAdIni + "' ";
                Query += " AND fac.fechaAdquisicion<='" + fechaAdFin + "' ";
            }
            if (!fechaReIni.equals("") && !fechaReFin.equals("")) {
                Query += " AND rec.fechaRenovacion>='" + fechaReIni + "' ";
                Query += " AND rec.fechaRenovacion<='" + fechaReFin + "' ";
            }
            Query += " AND pro.idProveedor= fac.Proveedor_idProveedor ";
            Query += " AND rec.tipo_idTipo= tipo.idTipo ";
            Query += " AND rec.factura_idFactura= fac.idFactura ";
            Query += " AND rec.ubicacionRecurso_idUbicacion= ubica.idUbicacion ";
            if (!licencia.isEmpty()) {
                Query += " AND rec.idRecurso = licRelacion.Recurso_idRecurso ";
                Query += " AND datoslicencia.idDatosLicencia = licRelacion.DatosLicencia_idDatosLicencia ";
            }
            if (!usuarioAnterior.isEmpty() && usuarioActual.isEmpty()) {
                Query += " AND rec.usuarioAnterior= empleado.idEmpleado  ";
            }
            if (usuarioAnterior.isEmpty() && !usuarioActual.isEmpty()) {
                Query += " AND rec.usuarioActual= empleado.idEmpleado  ";
            }
            if (usuarioAnterior.isEmpty() && usuarioActual.isEmpty()) {
                Query += " AND rec.usuarioActual= empleado.idEmpleado  ";
            }
            Query += " AND rec.estadoRecurso_idEstadoRecurso= estatus.idEstadoRecurso) ";
            Query += " ORDER BY rec.idRecurso ";
            Query += " LIMIT 0 , " + limite + " ";

            ResultSet rstSQLServer = statement.executeQuery(Query);

            while (rstSQLServer.next()) {
                recursoVector = new Vector();
                recursoVector.add(rstSQLServer.getString("idRecurso"));
                recursoVector.add(rstSQLServer.getString("noSerie"));
                recursoVector.add(rstSQLServer.getString("tipo"));
                recursoVector.add(rstSQLServer.getString("marca"));
                recursoVector.add(rstSQLServer.getString("modelo"));
                recursoVector.add(rstSQLServer.getString("usuarioAnterior"));
                recursoVector.add(rstSQLServer.getString("usuarioActual"));
                recursoVector.add(rstSQLServer.getString("nombre"));
                recursoVector.add(rstSQLServer.getString("oficina"));
                recursoVector.add(rstSQLServer.getString("folio"));
                recursoVector.add(rstSQLServer.getString("costo"));
                recursoVector.add(rstSQLServer.getString("razonSocial"));
                recursoVector.add(rstSQLServer.getString("fechaAdquisicion"));
                recursoVector.add(rstSQLServer.getString("fechaRenovacion"));
                resultados.add(recursoVector);
            }
            for (Object rec : resultados) {
                Vector temp = (Vector) rec;
                for (int ii = 5; ii <= 6; ii++) {
                    String usuario = (temp.get(ii)).toString();
//                    connection = instanciaConexion.ConectaMySQL();
                    statement = connection.createStatement();
                    statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

                    Query = " select nombreCompleto from empleado where idEmpleado=" + usuario + "";
                    rstSQLServer = statement.executeQuery(Query);
                    while (rstSQLServer.next()) {
                        temp.set(ii, rstSQLServer.getString("nombreCompleto"));
                        resultados.set(i, temp);
                    }
                }
                i++;
            }

            rstSQLServer.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Exception:gestionRecurso-getDetallesRecurso():" + e.toString());
            resultados = null;
        } finally {
            instanciaConexion.Desconecta(connection);
        }
        return resultados;

    }

    /**
     * Este metodo realiza una consulta dinamica, se especifican que columnas se
     * desean mostrar y el orden del resultado de la consulta
     *
     * @param columnas
     * @param ordenamiento
     * @return
     */
    static synchronized public Vector consultaDinamica(String columnas, String ordenamiento) {
        Vector recursoVector = new Vector();
        Vector resultados = new Vector();
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        boolean mostrarUsuarios = false;
        boolean mostrarLicencia = false;
        String Query = "";
        columnas = " idRecurso, " + columnas;
        int noColumnas = columnas.split(",").length;
        if (columnas.contains("usuarioActual")) {
            mostrarUsuarios = true;
        }
        if (columnas.contains("licencia")) {
            mostrarLicencia = true;
            columnas = columnas.replace(",licencia", "");
            noColumnas--;
        }

        try {

            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            Query = " select " + columnas;
            if (mostrarLicencia) {
                Query += " ,licencias.nombre,licencias.clave,licencias.observaciones ";
            }
            Query += " from `factura` as fac,`proveedor` as pro,`recurso` as rec,";
            Query += " `tipo` as tipo,`ubicacionRecurso` as ubica,`estadoRecurso` as estatus";
            if (mostrarLicencia) {
                Query += " ,`recurso_has_datoslicencia` as licenciasRelacion, ";
                Query += " `datoslicencia` as licencias ";
            }
            Query += " where (pro.idProveedor= fac.Proveedor_idProveedor ";
            Query += " AND rec.tipo_idTipo= tipo.idTipo ";
            Query += " AND rec.factura_idFactura= fac.idFactura ";
            Query += " AND rec.ubicacionRecurso_idUbicacion= ubica.idUbicacion ";
            if (mostrarLicencia) {
                Query += " AND rec.idRecurso = licenciasRelacion.Recurso_idRecurso ";
                Query += " AND licencias.idDatosLicencia = licenciasRelacion.DatosLicencia_idDatosLicencia ";
            }
            Query += " AND rec.estadoRecurso_idEstadoRecurso= estatus.idEstadoRecurso) ";
            Query += " ORDER BY " + ordenamiento.substring(0, ordenamiento.length());

            ResultSet rstSQLServer = statement.executeQuery(Query);

            while (rstSQLServer.next()) {
                recursoVector = new Vector();
                for (int count = 1; count <= noColumnas; count++) {
                    recursoVector.add(rstSQLServer.getString(count));
                }
                resultados.add(recursoVector);
            }

            if (mostrarUsuarios) {
                noColumnas = 0;
                String[] cols = columnas.split(",");
                for (String colTemp : cols) {
                    if (colTemp.equals("usuarioAnterior")) {
                        break;
                    }
                    noColumnas++;
                }
                int indiceUsuarioActual = noColumnas;

                int noRegistroRec = 0;
                for (Object rec : resultados) {                    
                    Vector registroRecursoModificar = (Vector) rec;
                    for (int ii = indiceUsuarioActual; ii <= (indiceUsuarioActual + 1); ii++) {
                        String usuario = (registroRecursoModificar.get(ii)).toString();
                        statement = connection.createStatement();
                        statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

                        Query = " select nombreCompleto from empleado where idEmpleado=" + usuario + "";
                        rstSQLServer = statement.executeQuery(Query);
                        while (rstSQLServer.next()) {
                            registroRecursoModificar.set(ii, rstSQLServer.getString("nombreCompleto"));
                            resultados.set(noRegistroRec, registroRecursoModificar);
                        }
                    }
                    noRegistroRec++;
                }


            }

            rstSQLServer.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Exception:gestionRecurso-getDetallesRecurso():" + e.toString());
            resultados = null;
        } finally {
            instanciaConexion.Desconecta(connection);
        }
        return resultados;

    }
    
    static public String generaEncabezado(String encabezadoInput){
        if (encabezadoInput.contains("rec.noSerie")) {
            encabezadoInput = encabezadoInput.replace("rec.noSerie", "SERIE");
        }                        
        if (encabezadoInput.contains("estatus.nombre")) {
            encabezadoInput = encabezadoInput.replace("estatus.nombre", "ESTATUS");
        }        
        if (encabezadoInput.contains("rec.costo")) {
            encabezadoInput = encabezadoInput.replace("rec.costo", "COSTO");
        }        
        if (encabezadoInput.contains("usuarioAnterior")) {
            encabezadoInput = encabezadoInput.replace("usuarioAnterior", "USUARIO ANTERIOR");
        }        
        if (encabezadoInput.contains("usuarioActual")) {
            encabezadoInput = encabezadoInput.replace("usuarioActual", "USUARIO ACTUAL");
        }        
        if (encabezadoInput.contains("tipo.tipo")) {
            encabezadoInput = encabezadoInput.replace("tipo.tipo", "TIPO");
        }        
        if (encabezadoInput.contains("tipo.marca")) {
            encabezadoInput = encabezadoInput.replace("tipo.marca", "MARCA");
        }        
        if (encabezadoInput.contains("tipo.modelo")) {
            encabezadoInput = encabezadoInput.replace("tipo.modelo", "MODELO");
        }        
        if (encabezadoInput.contains("tipo.descripcion")) {
            encabezadoInput = encabezadoInput.replace("tipo.descripcion", "DESCRIPCION");
        }        
        if (encabezadoInput.contains("licencia")) {
            encabezadoInput = encabezadoInput.replace("licencia", "LICENCIA");
        }        
        if (encabezadoInput.contains("rec.tiempoVida")) {
            encabezadoInput = encabezadoInput.replace("rec.tiempoVida", "TIEMPO DE VIDA");
        }        
        if (encabezadoInput.contains("rec.fechaRenovacion")) {
            encabezadoInput = encabezadoInput.replace("rec.fechaRenovacion", "FECHA RENOVACION");
        }        
        if (encabezadoInput.contains("rec.observacionesRec")) {
            encabezadoInput = encabezadoInput.replace("rec.observacionesRec", "OBSERVACIONES");
        }        
        if (encabezadoInput.contains("fac.folio")) {
            encabezadoInput = encabezadoInput.replace("fac.folio", "FOLIO FACTURA");
        }        
        if (encabezadoInput.contains("fac.fechaAdquisicion")) {
            encabezadoInput = encabezadoInput.replace("fac.fechaAdquisicion", "FECHA DE COMPRA");
        }        
        if (encabezadoInput.contains("pro.razonSocial")) {
            encabezadoInput = encabezadoInput.replace("pro.razonSocial", "RAZON SOCIAL");
        }        
        if (encabezadoInput.contains("pro.rfc")) {
            encabezadoInput = encabezadoInput.replace("pro.rfc", "RFC PROVEEDOR");
        }   
        if (encabezadoInput.contains("ubica.oficina")) {
            encabezadoInput = encabezadoInput.replace("ubica.oficina", "OFICINA");
        }           
        if (encabezadoInput.contains("ubica.area")) {
            encabezadoInput = encabezadoInput.replace("ubica.area", "AREA");
        }         
        
        
        return encabezadoInput;
    }

    static synchronized public Vector buscarRecursoBasica(String idRecurso, String noSerie, String marca, String modelo, String oficina, String area, String usuarioActualF) {
        Vector recursoVector = new Vector();
        Vector resultados = new Vector();
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";
        int i = 0;

        try {

            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            Query = " select rec.idRecurso,rec.noSerie,tipo.modelo,tipo.marca, ";
            Query += " ubica.oficina,ubica.area,rec.usuarioActual ";
            Query += " from `recurso` as rec,`tipo` as tipo, ";
            Query += " `ubicacionRecurso` as ubica, ";
            Query += " `empleado` as empleado ";
            Query += " where (";
            Query += " rec.idRecurso LIKE '%" + idRecurso + "%' ";
            Query += " AND rec.noSerie LIKE '%" + noSerie + "%' ";
            Query += " AND tipo.marca like '%" + marca + "%' ";
            Query += " AND tipo.modelo like '%" + modelo + "%' ";
            Query += " AND ubica.oficina like '%" + oficina + "%' ";
            Query += " AND ubica.area like '%" + area + "%' ";
            Query += " AND empleado.nombreCompleto like '%" + usuarioActualF + "%' ";
            Query += " AND rec.tipo_idTipo= tipo.idTipo ";
            Query += " AND rec.usuarioActual= empleado.idEmpleado ";
            Query += " AND rec.ubicacionRecurso_idUbicacion= ubica.idUbicacion ) ";
            Query += " ORDER BY rec.idRecurso ";
            Query += " LIMIT 0 , 100";

            ResultSet rstSQLServer = statement.executeQuery(Query);

            while (rstSQLServer.next()) {
                recursoVector = new Vector();
                recursoVector.add(rstSQLServer.getString("idRecurso"));
                recursoVector.add(rstSQLServer.getString("noSerie"));
                recursoVector.add(rstSQLServer.getString("modelo"));
                recursoVector.add(rstSQLServer.getString("marca"));
                recursoVector.add(rstSQLServer.getString("oficina"));
                recursoVector.add(rstSQLServer.getString("area"));
                recursoVector.add(rstSQLServer.getString("usuarioActual"));
                resultados.add(recursoVector);
            }
            //El siguiente for cambia el numero de empleado por su nombre completo
            for (Object rec : resultados) {
                Vector temp = (Vector) rec;
                String usuario = (temp.get(6)).toString();
//                connection = instanciaConexion.ConectaMySQL();
                statement = connection.createStatement();
                statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

                Query = " select nombreCompleto from empleado where idEmpleado=" + usuario + "";
                rstSQLServer = statement.executeQuery(Query);
                while (rstSQLServer.next()) {
                    temp.set(6, rstSQLServer.getString("nombreCompleto"));
                    resultados.set(i, temp);
                }
                i++;
            }

            rstSQLServer.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Exception:gestionRecurso-buscarRecursoBasica():" + e.toString());
            resultados = null;
        } finally {
            instanciaConexion.Desconecta(connection);
        }
        return resultados;

    }

    static synchronized public boolean modificaInfoBaseRecurso(Recurso recursoForm) {
        boolean resultado = false;
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";
        SimpleDateFormat fechaFormat = new SimpleDateFormat("yyyy-MM-dd");
//        System.out.println("Fecha a insertar:" + fechaFormat.format(recursoForm.getFechaRenovacion()));
        String fechaString = fechaFormat.format(recursoForm.getFechaRenovacion());
//        System.out.println("Fecha a insertar :::::" + fechaString);

        try {

            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            Query = "  update recurso set ";
            Query += " noSerie='" + recursoForm.getNoSerie() + "' ,";
            Query += " costo='" + recursoForm.getCosto() + "' ,";
            Query += " tiempoVida='" + recursoForm.getTiempoVida() + "' ,";
            Query += " fechaRenovacion='" + fechaString + "' ,";
            Query += " observacionesRec='" + recursoForm.getObservacionesRec() + "' ,";
            Query += " usuarioActual='" + recursoForm.getUsuarioActual() + "' ,";
            Query += " usuarioAnterior='" + recursoForm.getUsuarioAnterior() + "' ";
//            Query += " licencias='" + recursoForm.getSistemaOperativo() + "' ";
            Query += " where idRecurso=" + recursoForm.getIdRecurso() + "; ";

//            System.out.println("EjecutaSQL: " + Query);

            if (statement.executeUpdate(Query) == 1) {
                resultado = true;
            }

            statement.close();
        } catch (Exception e) {
            System.out.println("gestionRecurso-modificaInfoBaseRecurso():" + e.toString());
            resultado = false;
        } finally {
            instanciaConexion.Desconecta(connection);
        }
        return resultado;
    }

    static synchronized public boolean insertaNuevoRecurso(Recurso recursoForm) {
        boolean resultado = false;
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";
        SimpleDateFormat fechaFormat = new SimpleDateFormat("yyyy-MM-dd");
        String fechaString = fechaFormat.format(recursoForm.getFechaRenovacion());

        try {

            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            Query = "  INSERT INTO recurso (`idRecurso`, `noSerie`, `costo`, `tiempoVida`,";
            Query += " `fechaRenovacion`, `usuarioActual`, `usuarioAnterior`, `observacionesRec`, ";
            Query += " `Factura_idFactura`, `EstadoRecurso_idEstadoRecurso`, `Tipo_idTipo`, ";
            Query += " `UbicacionRecurso_idUbicacion`)";
            Query += " VALUES (NULL, '" + recursoForm.getNoSerie() + "', '" + recursoForm.getCosto() + "',";
            Query += " '" + recursoForm.getTiempoVida() + "', '" + fechaString + "', ";
            Query += " '" + recursoForm.getUsuarioActual() + "', '" + recursoForm.getUsuarioAnterior() + "', ";
            Query += " '" + recursoForm.getObservacionesRec() + "', '" + recursoForm.getFactura_idFactura() + "',";
            Query += " '" + recursoForm.getEstadoRecurso_idEstadoRecurso() + "', '" + recursoForm.getTipo_idTipo() + "',";
            Query += " '" + recursoForm.getUbicacionRecurso_idUbicacion() + "') ";

//            System.out.println("EjecutaSQL: " + Query);

            if (statement.executeUpdate(Query) == 1) {
                resultado = true;
            }

            statement.close();
        } catch (Exception e) {
            System.out.println("gestionRecurso-insertaNuevoRecurso():" + e.toString());
            resultado = false;
        } finally {
            instanciaConexion.Desconecta(connection);
        }
        return resultado;
    }

    static synchronized public Map getDetallesEstadoRecurso(String idEstadoRecurso) {
        Map resultado = null;
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";

        try {

            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            Query = " select * ";
            Query += " from `estadorecurso`";
            Query += " where `idEstadoRecurso`='" + idEstadoRecurso + "' ";

            ResultSet rstSQLServer = statement.executeQuery(Query);

            while (rstSQLServer.next()) {
                resultado = new HashMap();
                resultado.put("idEstado", rstSQLServer.getString("idEstadoRecurso"));
                resultado.put("nombre", rstSQLServer.getString("nombre"));
                resultado.put("descripcion", rstSQLServer.getString("descripcion"));
            }
            rstSQLServer.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Exception:gestionRecurso-getDetallesEstadoRecurso():" + e.toString());
            resultado = null;
        } finally {
            instanciaConexion.Desconecta(connection);
        }
        return resultado;
    }

    static synchronized public Vector getLicenciasVinculadas(String idRecurso) {
        Vector resultado = new Vector();
        Map licencia = null;
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";

        try {

            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            Query = " select lic.idDatosLicencia, lic.nombre, lic.clave, lic.observaciones";
            Query += " from recurso_has_datosLicencia as relacion, recurso as rec, datosLicencia as lic";
            Query += " where rec.idRecurso=relacion.recurso_idRecurso";
            Query += " and lic.idDatosLicencia = relacion.datosLicencia_idDatosLicencia ";
            Query += " and rec.idRecurso = '" + idRecurso + "' ";
            Query += " order by lic.idDatosLicencia ASC ";

            ResultSet rstSQLServer = statement.executeQuery(Query);

            while (rstSQLServer.next()) {
                licencia = new HashMap();
                licencia.put("idDatosLicencia", rstSQLServer.getString("idDatosLicencia"));
                licencia.put("nombre", rstSQLServer.getString("nombre"));
                licencia.put("clave", rstSQLServer.getString("clave"));
                licencia.put("observaciones", rstSQLServer.getString("observaciones"));
                resultado.add(licencia);
            }
            rstSQLServer.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Exception:gestionRecurso-getDetallesEstadoRecurso():" + e.toString());
            resultado = null;
        } finally {
            instanciaConexion.Desconecta(connection);
        }
        return resultado;
    }

    static synchronized public boolean modificaRecursoTipo(String idRecurso, int tipo_idTipo) {
        boolean resultado = false;
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";

        try {

            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            statement = connection.createStatement();
            resultado = false;
            Query = " UPDATE recurso ";
            Query += " set Tipo_idTipo=" + tipo_idTipo;
            Query += " where idRecurso=" + idRecurso;
            if (statement.executeUpdate(Query) == 1) {
                resultado = true;
            }

            statement.close();
        } catch (Exception e) {
            System.out.println("Exception:gestionRecurso-modificaRecursoTipo():" + e.toString());
            resultado = false;
        } finally {
            instanciaConexion.Desconecta(connection);
        }
        return resultado;
    }

    static synchronized public boolean modificaRecursoUbicacion(String idRecurso, int ubicacionRecurso_idUbicacion) {
        boolean resultado = false;
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";

        try {

            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            statement = connection.createStatement();
            resultado = false;
            Query = " UPDATE recurso ";
            Query += " set ubicacionRecurso_idUbicacion=" + ubicacionRecurso_idUbicacion;
            Query += " where idRecurso=" + idRecurso;
//            System.out.println("Query:" + Query);
            if (statement.executeUpdate(Query) == 1) {
                resultado = true;
            }

            statement.close();
        } catch (Exception e) {
            System.out.println("Exception:gestionRecurso-modificaRecursoUbicacion():" + e.toString());
            resultado = false;
        } finally {
            instanciaConexion.Desconecta(connection);
        }
        return resultado;
    }

    static synchronized public boolean modificaRecursoFactura(String idRecurso, int idFactura) {
        boolean resultado = false;
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";

        try {

            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            statement = connection.createStatement();
            resultado = false;
            Query = " UPDATE recurso ";
            Query += " set factura_idFactura=" + idFactura;
            Query += " where idRecurso=" + idRecurso;
            if (statement.executeUpdate(Query) == 1) {
                resultado = true;
            }

            statement.close();
        } catch (Exception e) {
            System.out.println("Exception:gestionRecurso-modificaRecursoFactura():" + e.toString());
            resultado = false;
        } finally {
            instanciaConexion.Desconecta(connection);
        }
        return resultado;
    }

    static synchronized public boolean modificaRecursoEstado(String idRecurso, int estadoRecurso_idEstadoRecurso) {
        boolean resultado = false;
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";

        try {

            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            statement = connection.createStatement();
            resultado = false;
            Query = " UPDATE recurso ";
            Query += " set estadoRecurso_idEstadoRecurso=" + estadoRecurso_idEstadoRecurso;
            Query += " where idRecurso=" + idRecurso;
            if (statement.executeUpdate(Query) == 1) {
                resultado = true;
            }

            statement.close();
        } catch (Exception e) {
            System.out.println("Exception:gestionRecurso-modificaRecursoUbicacion():" + e.toString());
            resultado = false;
        } finally {
            instanciaConexion.Desconecta(connection);
        }
        return resultado;
    }

    static synchronized public boolean modificacionMasiva(int idUbicacion, int idEstado, int uAnterior, int uActual, String idRecursos) {
        boolean resultado = false;
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";

        try {

            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            statement = connection.createStatement();
            resultado = false;
            Query = " UPDATE recurso SET";
            if (idUbicacion > 0) {
                Query += " ubicacionRecurso_idUbicacion=" + idUbicacion + ",";
            }
            if (idEstado > 0) {
                Query += " estadoRecurso_idEstadoRecurso=" + idEstado + ",";
            }
            if (uAnterior > 0) {
                Query += " usuarioAnterior=" + uAnterior + ",";
            }
            if (uActual > 0) {
                Query += " usuarioActual=" + uActual + ",";
            }
            Query = Query.substring(0, Query.length() - 1);
            idRecursos = idRecursos.substring(0, idRecursos.length() - 1);
            Query += " where idRecurso IN(" + idRecursos + ")";
            if (statement.executeUpdate(Query) == 1) {
                resultado = true;
            }
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Exception:gestionRecurso-modificaRecursoUbicacion():" + e.toString());
            resultado = false;
        } finally {
            instanciaConexion.Desconecta(connection);
        }
        return resultado;
    }
}
