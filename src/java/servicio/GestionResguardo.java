/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

/**
 *
 * @author Luis-Valerio
 */
public class GestionResguardo {

    public boolean generaArchivo(String formato, String[] parametros) {
        boolean correcto = false;
        Connection connection = null;

        try {
            connection = clsConexionMySQL.GetConnection();
            correcto = true;
        } catch (Exception ex) {
            System.out.println("Error al crear conexion SQL");
        }

        if (correcto && formato.equals("resguardo")) {
            correcto = creaFormatoResguardo("./Common/Resguardo.jrxml", parametros, connection);
        }


        return correcto;
    }

    private synchronized boolean creaFormatoResguardo(String archivo_jasper, String[] parametros, Connection lcnnConexion) {
        String[] args = null;
        JasperReport report = null;
        OutputStream output = null;
        JasperPrint print = null;
        boolean genera = false;
        String arrayIdsTemp = "";
        String folio = "";
        String pdfFileName = "";
        try {


            try {
                report = JasperCompileManager.compileReport(archivo_jasper);

            } catch (Exception e) {
                genera = false;
                System.out.println("Exception:Compilando jasper:" + e.getMessage());
                System.exit(3);
            }
            //Parámetros
            Map parametro = new HashMap();
            parametro.put("nuevoUsuario", parametros[0]);
            parametro.put("area", parametros[1]);
            parametro.put("oficina", parametros[2]);
            //Se obtiene el formato a 5 digitos del folio  
            folio = parametros[3];
            if (folio.length() < 5) {
                int longitud = folio.length();
                String ceros = "";
                for (int i = 1; i <= (5 - longitud); i++) {
                    ceros += "0";
                }
                folio = ceros + folio;
            }
            parametro.put("folio",folio);
            //Se obtienen los ids de los recursos de la orden de resguardo, String de entrada id1,id2,id3, ...
            try {
                String[] arrayIdRecursos = parametros[4].split(",");
                for (String idRec : arrayIdRecursos) {
                    arrayIdsTemp += "'" + idRec + "',";
                }
                if (!arrayIdsTemp.isEmpty()) {
                    arrayIdsTemp = arrayIdsTemp.substring(0, arrayIdsTemp.length() - 1);
                }
            } catch (Exception ex) {
                System.out.println("Error al obtener los id de los recursos");
            }

            parametro.put("listaRecursos", arrayIdsTemp);
            parametro.put("logo", parametros[5] +  "\\Imagen\\" + parametros[6]);
            parametro.put("logoResguardo", parametros[5] +  "\\Imagen\\logoResguardo.jpg");            
            parametro.put("descripcion", parametros[7]);            
            parametro.put("usuarioGenera", parametros[8]);            
            pdfFileName = "OrdenResguardo.pdf";
            output = new FileOutputStream(new File(parametros[5] + "\\temp\\" + pdfFileName));

            //Generamos el reporte
            //System.out.println("Antes de JasperFillManager");
//                        print = JasperFillManager.fillReport(archivo, parametro, lcnnConexion);
            print = JasperFillManager.fillReport(report, parametro, lcnnConexion);
//                        System.out.println("Antes de exportar a PDF");
//                        JRExporter exporter = new net.sf.jasperreports.engine.export.JRPdfExporter();
//                        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,urlArchivo + nombreReporte);
//                        exporter.setParameter(JRExporterParameter.JASPER_PRINT,print);
//                        exporter.exportReport();

            //Exportamos a PDF
            //System.out.println("Antes de JasperExportManager");
            JasperExportManager.exportReportToPdfStream(print, output);
            output.flush();
            output.close();

//        print = JasperFillManager.fillReport(archivo_jasper, parametro, lcnnConexion);                            
//        JRExporter exporter = new net.sf.jasperreports.engine.export.JRPdfExporter();
//        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,urlArchivo+nombreReporte);
//        exporter.setParameter(JRExporterParameter.JASPER_PRINT,print);
//        exporter.exportReport();                        

            genera = true;
//            System.out.println("Archivo pdf generado correctamente");
        } catch (Exception e) {
            genera = false;
            try {
                if (output != null) {
                    output.flush();
                    output.close();
                }
            } catch (IOException ex) {
            }
            System.out.println("creaReporteLiquidacion:" + e.getMessage());
            System.out.println("Exception: " + e.getLocalizedMessage());
            System.out.println("Exception: " + e.toString());
            System.out.println("Exception: " + e.getCause().getMessage());
            System.out.println("Exception: " + e.getCause().toString());
        }

        return genera;
    }

    /**
     * Se modifica el recurso solicitado con los nuevos datos para la orden de
     * resguardo
     *
     * @param idRecurso
     * @param noSerie
     * @param idUbicacion
     * @param nuevoUsuario
     * @param estatus
     * @return
     */
    synchronized public boolean modificaRecursoResguardo(String idRecurso, String noSerie, String idUbicacion, String idNuevoUsuario, String idEstatus) {
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
            if (idUbicacion != null && !idUbicacion.equals("")) {
                Query += " ubicacionRecurso_idUbicacion=" + idUbicacion + ",";
            }
            if (idEstatus != null && !idEstatus.equals("")) {
                Query += " estadoRecurso_idEstadoRecurso=" + idEstatus + ",";
            }
            if (idNuevoUsuario != null && !idNuevoUsuario.equals("")) {
                Query += " usuarioAnterior = usuarioActual ,";
                Query += " usuarioActual=" + idNuevoUsuario + ",";
            }
            Query = Query.substring(0, Query.length() - 1);
            Query += " where noSerie = '" + noSerie + "' ";
            Query += " AND idRecurso = " + idRecurso + " ";
            if (statement.executeUpdate(Query) == 1) {
                resultado = true;
            }
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Exception:gestionResguardo-modificaRecursoResguardo():" + e.toString());
            resultado = false;
        } finally {
            instanciaConexion.Desconecta(connection);
        }
        return resultado;
    }

    synchronized public boolean insertaResguardo(String usuarioAsignacion, String listaRecursosId, String idUbicacion,String usuarioGenera) {
        boolean resultado = false;
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";

        try {

            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            Query = " INSERT INTO resguardo (`folio`, `usuarioAsignacion`, `listaRecursosId`, ";
            Query += " `fecha`, `UbicacionRecurso_idUbicacion`,`usuarioGenera` ) ";
            Query += " VALUES (NULL, '" + usuarioAsignacion + "', ";
            Query += " '" + listaRecursosId + "', ";
            Query += " CURDATE(), ";
            Query += " '" + idUbicacion + "', ";
            Query += " '" + usuarioGenera + "') ";

            if (statement.executeUpdate(Query) == 1) {
                resultado = true;
            }

            statement.close();
        } catch (Exception e) {
            System.out.println("gestionResguardo-insertaResguardo():" + e.toString());
            resultado = false;
        } finally {
            instanciaConexion.Desconecta(connection);
        }
        return resultado;
    }

    synchronized public String lastIdResguardo() {
        String resultado = "";
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";

        try {

            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            Query = "SELECT MAX(folio) AS folio FROM resguardo";
            ResultSet rstSQLServer = statement.executeQuery(Query);

            while (rstSQLServer.next()) {
                resultado = rstSQLServer.getString("folio");
            }

            statement.close();
        } catch (Exception e) {
            System.out.println("gestionResguardo-insertaResguardo():" + e.toString());
            resultado = "";
        } finally {
            instanciaConexion.Desconecta(connection);
        }
        return resultado;
    }

    public Vector buscarResguardo(String folio,String usuarioAsignacion, String fechaIni, String fechaFin) {
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        Vector resultado = new Vector();
        Map resguardo = new HashMap();
        String Query = "";
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
//        Vector listaRecursos = new Vector();

        try {

            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            if (fechaFin.equals("")) {
                fechaFin = formato.format(new Date());
            }
            if (fechaIni.equals("")) {
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, 1970);
                fechaIni = formato.format(cal.getTime());
            }

            Query = " select folio,usuarioAsignacion,listaRecursosId,fecha,ubica.oficina,ubica.area ";
            Query += " from `resguardo`,`ubicacionRecurso` as ubica ";
            Query += " where (folio LIKE '%" + folio + "%' ";
            Query += " AND usuarioAsignacion LIKE '%" + usuarioAsignacion + "%' ";
            Query += " AND fecha >= '" + fechaIni + "' ";
            Query += " AND fecha <= '" + fechaFin + "' ";
            Query += " AND ubica.idUbicacion=UbicacionRecurso_idUbicacion ) ";
            Query += " ORDER BY folio ";
            Query += " LIMIT 0 , 100 ";

            ResultSet rstSQLServer = statement.executeQuery(Query);

            while (rstSQLServer.next()) {
                resguardo = new HashMap();
                resguardo.put("folio", rstSQLServer.getString("folio"));
                resguardo.put("usuarioAsignacion", rstSQLServer.getString("usuarioAsignacion"));
                resguardo.put("listaRecursosId", rstSQLServer.getString("listaRecursosId"));
                resguardo.put("fecha", rstSQLServer.getString("fecha"));
                resguardo.put("oficina", rstSQLServer.getString("oficina"));
                resguardo.put("area", rstSQLServer.getString("area"));
                resultado.add(resguardo);
            }
//            try{
//            for (Object rgdo : resultado) {
//                if(rgdo instanceof HashMap){
//                Map r= (HashMap)rgdo;  
//                r.put("recursos", rgdo);
//                }
//            }
//            }catch(Exception ex){
//                System.out.println("Error al agregar el recurso a la tabla");
//            }
            
            rstSQLServer.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("gestionFacturas-buscarFacturaFiltroFecha():" + e.toString());
            resultado = null;
        } finally {
            instanciaConexion.Desconecta(connection);
        }
        return resultado;
    }
}
