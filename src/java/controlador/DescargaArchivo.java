/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import beans.Usuario;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Vector;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import servicio.ReportesPDF;

/**
 *
 * @author Luis-Valerio
 */
public class DescargaArchivo extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * <a href="DescargaArchivo" target="_blank">Descargar Todos</a>
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Usuario userApp = null;
        String alerta = "";
        String pdfFileName = "consulta.csv";
        boolean correcto = false;
        HttpSession session = request.getSession();

        //Se obtienen los clientes que tienen movimientos pendientes por operar.             
        try {
            userApp = (Usuario) session.getAttribute("usuarioActual");
            if (userApp != null) {
                String tipo_consulta = request.getParameter("tc");
                if (tipo_consulta != null && !tipo_consulta.equals("")) {
                    if (tipo_consulta.equals("recurso")) {
                        Vector vector_consulta = (Vector) session.getAttribute("recursosEncontrados");
                        if (vector_consulta != null) {
                            String encabezado = "ID RECURSO,SERIE,TIPO,MARCA,MODELO,USUARIO ANTERIOR,"
                                    + "USUARIO ACTUAL,ESTATUS,OFICINA,FOLIO DE FACTURA,COSTO,PROVEEDOR,FECHA DE COMPRA, FECHA DE RENOVACION";
                            String urlReporte = getServletContext().getRealPath("temp") + "\\";
//                            String urlReporte = "C:\\apache-tomcat-7.0.42-windows-x64\\apache-tomcat-7.0.42\\bin\\temp\\";
                            correcto = ReportesPDF.generaArchivoDeConsulta(encabezado, vector_consulta, urlReporte, pdfFileName);
                        }
                    }
                    if (tipo_consulta.equals("recursoConEncabezado")) {
                        Vector vector_consulta = (Vector) session.getAttribute("recursosEncontrados");
                        String encabezado = (String) session.getAttribute("encabezado");
                        if (vector_consulta != null) {
                            String urlReporte = getServletContext().getRealPath("temp") + "\\";
//                            String urlReporte = "C:\\apache-tomcat-7.0.42-windows-x64\\apache-tomcat-7.0.42\\bin\\temp\\";
                            correcto = ReportesPDF.generaArchivoDeConsulta(encabezado, vector_consulta, urlReporte, pdfFileName);
                        }
                    }
                }
                if (correcto) {

                    String urlReporte = getServletContext().getRealPath("temp") + "\\";
                    File pdfFile = new File(urlReporte + pdfFileName);
                    response.setContentType("text/csv;");
                    response.addHeader("Content-Disposition", "attachment; filename=" + pdfFileName);
                    response.setContentLength((int) pdfFile.length());

                    FileInputStream fileInputStream = new FileInputStream(pdfFile);
                    OutputStream responseOutputStream = response.getOutputStream();
                    int bytes;
                    while ((bytes = fileInputStream.read()) != -1) {
                        responseOutputStream.write(bytes);
                    }
                } else {
                    alerta =" Error actualizando el archivo. ";
                    session.setAttribute("alerta", alerta);
                }                
            } else {
                alerta = " Favor de iniciar sesion. ";
                session.setAttribute("alerta", alerta);
                response.sendRedirect("login.htm");
            }
        } catch (Exception ex) {
            System.out.println("Exception MuetraMovimientosFiduciarios:" + ex.getMessage());
            response.setHeader("Content-Disposition", "");
            response.setContentType("text/plain");
            alerta = " Error actualizando el archivo. ";
            session.setAttribute("alerta", alerta);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
