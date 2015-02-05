/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio;

import beans.Proveedor;
import beans.Tipo;
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
public class GestionProveedor {
    
    
    static synchronized public boolean existeUnicoProveedor(Proveedor proveedorIn){
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";        
        int count = 0;
        
        try {
              
            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            Query = " select idProveedor from proveedor ";
            Query += " WHERE rfc='" + proveedorIn.getRfc() + "' ";
            
            ResultSet rstSQLServer = statement.executeQuery(Query);
            
            while (rstSQLServer.next()) {
                count++;
            }
            rstSQLServer.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("gestionProveedor-existeUnicoProveedor():" + e.toString());
            return false;
        }
        finally{
            instanciaConexion.Desconecta(connection);
        }  
        return count!=0;
    }        
    
    static synchronized public boolean insertaNuevoProveedor(Proveedor proveedorIn){
        boolean resultado = false;
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";        
        
        try {
              
            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            Query = " INSERT INTO proveedor (`idProveedor`, `razonSocial`, `rfc`, ";
            Query += " `domicilioFiscal`, `telefonoContacto`, `correoContacto`) " ;
            Query += " VALUES (NULL, '" + proveedorIn.getRazonSocial() + "', ";
            Query += " '" + proveedorIn.getRfc() + "', ";
            Query += " '" + proveedorIn.getDomicilioFiscal()  + "', ";
            Query += " '" + proveedorIn.getTelefonoContacto() + "', ";
            Query += " '" + proveedorIn.getCorreoContacto() + "') ";
            
            if (statement.executeUpdate(Query) == 1) {
                resultado = true;
            }

            statement.close();
        } catch (Exception e) {
            System.out.println("gestionProveedor-insertaNuevoProveedor():" + e.toString());
            resultado = false;
        }
        finally{
            instanciaConexion.Desconecta(connection);
        }  
        return resultado;
    }        
    
    static synchronized public Vector getAllProvedores(){
        Vector resultado = new Vector();
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";
        
        try {
              
            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            
            Query = " select razonSocial ";
            Query += " from `proveedor` GROUP BY razonSocial ";
            
            ResultSet rstSQLServer = statement.executeQuery(Query);
            int i=0;
            while (rstSQLServer.next()) {
                if(i==0){
                resultado.add("Todos");
                }
                resultado.add(rstSQLServer.getString("razonSocial"));
                i++;
            }
            rstSQLServer.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Exception:gestionRProveedor-getAllProvedores():" + e.toString());
            resultado = null;
        }
        finally{
            instanciaConexion.Desconecta(connection);
        }  
        return resultado;
    }        
    
    static synchronized public Map getDetallesProveedor(String idProveedor,String razonSocial){
        Map resultado = null;
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";
        
        try {
              
            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            
            Query = " select *";
            Query += " from `proveedor`";
            Query += " where `razonSocial`='" + razonSocial+"' ";
            if(idProveedor!=null && !idProveedor.equals(""))
            Query += " and `idProveedor`='" + idProveedor+"' ";            
            ResultSet rstSQLServer = statement.executeQuery(Query);
            
            while (rstSQLServer.next()) {
                resultado= new HashMap();
                resultado.put("idProveedor", rstSQLServer.getString("idProveedor"));
                resultado.put("razonSocial", rstSQLServer.getString("razonSocial"));
                resultado.put("rfc", rstSQLServer.getString("rfc"));
                resultado.put("domicilioFiscal", rstSQLServer.getString("domicilioFiscal"));
                resultado.put("telefonoContacto", rstSQLServer.getString("telefonoContacto"));
                resultado.put("correoContacto", rstSQLServer.getString("correoContacto"));
            }
            rstSQLServer.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Exception:gestionProveedor-getDetallesProveedor():" + e.toString());
            resultado = null;
        }
        finally{
            instanciaConexion.Desconecta(connection);
        }  
        return resultado;
    }    
    
    
    static synchronized public Vector buscaProveedor(String razonSocial,String rfc,String domicilioFiscal){
        String Resultado[]=null;
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        Vector resultadoFinal = new Vector();
        Map proveedor = new HashMap();
        String Query = "";
        
        
        try {
              
            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            Query = " select *";
            Query += " from `proveedor` ";
            Query += " where (razonSocial LIKE '%" + razonSocial+"%' ";
            Query += " AND rfc LIKE '%" + rfc+"%'  ";
            Query += " AND domicilioFiscal LIKE '%" + domicilioFiscal+"%' ) ";
            Query += " ORDER BY razonSocial ";
            Query += " LIMIT 0 , 100 ";
            
            
            ResultSet rstSQLServer = statement.executeQuery(Query);
            
            while (rstSQLServer.next()) {
                proveedor = new HashMap();
                proveedor.put("idProveedor",rstSQLServer.getString("idProveedor"));
                proveedor.put("razonSocial",rstSQLServer.getString("razonSocial"));
                proveedor.put("rfc",rstSQLServer.getString("rfc"));
                proveedor.put("domicilioFiscal",rstSQLServer.getString("domicilioFiscal"));
                resultadoFinal.add(proveedor);
            }
            rstSQLServer.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("gestionProveedor-buscarProveedor():" + e.toString());
            resultadoFinal = null;
        }
        finally{
            instanciaConexion.Desconecta(connection);
        }  
        return resultadoFinal;
    }    
    
    
    static synchronized public boolean modificaDatosProveedor(Proveedor proveedorIn){
        boolean resultado = false;
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";        
        
        try {
              
            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            Query = " update proveedor set ";
            Query += " razonSocial='" + proveedorIn.getRazonSocial() + "' ,";
            Query += " rfc='" + proveedorIn.getRfc() + "' ,";
            Query += " domicilioFiscal='" + proveedorIn.getDomicilioFiscal() + "' ,";
            Query += " telefonoContacto='" + proveedorIn.getTelefonoContacto()+ "' ,";
            Query += " correoContacto='" + proveedorIn.getCorreoContacto() + "'"  ;
            Query += " where idProveedor=" + proveedorIn.getIdProveedor() + " ";
            
            if (statement.executeUpdate(Query) == 1) {
                resultado = true;
            }

            statement.close();
        } catch (Exception e) {
            System.out.println("gestionProveedor-modificaDatosProveedor():" + e.toString());
            resultado = false;
        }
        finally{
            instanciaConexion.Desconecta(connection);
        }  
        return resultado;
    }    
    
    
}
