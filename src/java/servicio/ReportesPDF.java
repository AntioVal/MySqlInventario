/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

/**
 *
 * @author Luis-Valerio
 */
public class ReportesPDF {

    public static void main(String[] args) {
        clsConexionMySQL conn = new clsConexionMySQL();
        java.sql.Connection connection = null;
        connection = conn.GetConnection();
        creaReporteLiquidacion("C:\\prueba\\", "C:\\prueba\\Resguardo.jrxml", connection);
    }

    public static synchronized boolean creaReporteLiquidacion(String urlArchivoSalida, String archivo_jasper, java.sql.Connection lcnnConexion) {
        String archivo = archivo_jasper;
        String[] args = null;
        JasperReport report = null;
        OutputStream output = null;
        JasperPrint print = null;
        boolean genera = false;
        String nombreReporte = "";
        try {
            //Damos formato a la fecha de liquidación
            //Verificamos si el nombre del archivo es valido
            if (archivo == null || archivo.equals("")) {
                genera = false;
            }
            try {
                report = JasperCompileManager.compileReport(archivo);
            } catch (Exception e) {
                genera = false;
                System.out.println("cargando y compilando jasper:" + e.getMessage());
                System.exit(3);
            }
            //Parámetros
//            System.out.println("Comienza la asignacion en el MAP------");
            Map parametro = new HashMap();
            parametro.put("tipo", "multifuncional");
            parametro.put("marca", "CPU");
            parametro.put("modelo", "LaserJet Pro MFP M127FN");
            parametro.put("noSerie", "CNDIKOL929");
            parametro.put("logoGP", "C:\\prueba\\logoGP.jpg");

            nombreReporte = "ResguardoEquipo.pdf";
            output = new FileOutputStream(new File(urlArchivoSalida + nombreReporte));
            //Generamos el reporte
            print = JasperFillManager.fillReport(report, parametro, lcnnConexion);

            //Exportamos a PDF
            JasperExportManager.exportReportToPdfStream(print, output);
            output.flush();
            output.close();
            genera = true;
        } catch (Exception e) {
            genera = false;
            System.out.println("creaReporteLiquidacion:" + e.getMessage());
            System.out.println("Exception: " + e.getLocalizedMessage());
            System.out.println("Exception: " + e.toString());
            System.out.println("Exception: " + e.getCause().getMessage());
            System.out.println("Exception: " + e.getCause().toString());
        }

        return genera;
    }

    public static boolean generaArchivoDeConsulta(String encabezado,Vector vector_info,String pathProyecto, String nombre_archivo) {
        boolean correcto = false;

        Writer writer = null;

        try {
            writer = new OutputStreamWriter(new FileOutputStream(pathProyecto + nombre_archivo), "UTF-8");
            writer.write(encabezado + "\r\n");
            for (int i = 0; i < vector_info.size(); i++) {
                String linea = "";
                Vector<String> registro = (Vector)vector_info.get(i);
                for (String campo: registro) {
                    linea += "\"" + campo + "\",";
                }
                linea += "\r\n";
                writer.write(linea);
            }
            writer.close();
            correcto = true;
        } catch (Exception e) {
            System.out.println("ReportesPDF-generaArchivoDeConsulta:" + e.toString());
        }

        return correcto;
    }
}
