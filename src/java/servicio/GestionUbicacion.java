/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio;

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
public class GestionUbicacion {
    
    
    static synchronized public boolean insertaNuevaUbicacion(String oficina,String area){
        boolean resultado = false;
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";        
        
        try {
              
            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            Query = " INSERT INTO ubicacionrecurso (`idUbicacion`, `oficina`, `area`)" ;
            Query += " VALUES (NULL, '" + oficina + "', ";
            Query += " '"+ area + "') ";
            
            if (statement.executeUpdate(Query) == 1) {
                resultado = true;
            }

            statement.close();
        } catch (Exception e) {
            System.out.println("gestionUbicacion-insertaNuevaUbicacion():" + e.toString());
            resultado = false;
        }
        finally{
            instanciaConexion.Desconecta(connection);
        }  
        return resultado;
    }    
    
    
    static synchronized public Map getDetallesUbicacion(String idUbicacion){
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
            Query += " from `ubicacionrecurso`";
            Query += " where `idUbicacion`='" + idUbicacion+"' ";
            
            ResultSet rstSQLServer = statement.executeQuery(Query);
            
            while (rstSQLServer.next()) {
                resultado= new HashMap();
                resultado.put("idUbicacion", idUbicacion);                
                resultado.put("oficina", rstSQLServer.getString("oficina"));
                resultado.put("area", rstSQLServer.getString("area"));
            }
            rstSQLServer.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Exception:gestionUbicacion-getDetallesUbicacion():" + e.toString());
            resultado = null;
        }
        finally{
            instanciaConexion.Desconecta(connection);
        }  
        return resultado;
    }      
    
    
    static synchronized public Map getDetallesPorOficinaArea(String oficina,String area){
        Map resultado = null;
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";
        int count=0;
        
        try {
              
            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            
            Query = " select * ";
            Query += " from `ubicacionrecurso`";
            Query += " where `oficina`='" + oficina +"' ";
            Query += " and `area`='" + area +"' ";
            
            ResultSet rstSQLServer = statement.executeQuery(Query);
            
            while (rstSQLServer.next()) {
                count++;
                resultado= new HashMap();
                resultado.put("idUbicacion", rstSQLServer.getString("idUbicacion"));                
                resultado.put("oficina", rstSQLServer.getString("oficina"));
                resultado.put("area", rstSQLServer.getString("area"));
            }
            rstSQLServer.close();
            statement.close();
            if(count!=1)
                return null;
        } catch (Exception e) {
            System.out.println("Exception:gestionUbicacion-getDetallesUbicacion():" + e.toString());
            resultado = null;
        }
        finally{
            instanciaConexion.Desconecta(connection);
        }  
        return resultado;
    }       
    
    static synchronized public Vector filtraGestionSelect(String oficina,String area){
        Vector idUbicaciones=new Vector();
        Vector oficinas=new Vector();
        Vector areas=new Vector();
        Vector result=new Vector();
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";
        
        try {
              
            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            Query = " select idUbicacion,oficina,area";
            Query += " from `ubicacionRecurso` ";
            Query += " where (oficina= '" + oficina+"' ";
            if(area!=null && !area.equals(""))
            Query += " AND area='" + area+"'  ";
            Query += " ) ORDER BY oficina ";
            
            
            ResultSet rstSQLServer = statement.executeQuery(Query);
            
            while (rstSQLServer.next()) {
                idUbicaciones.add(rstSQLServer.getString("idUbicacion"));                
                oficinas.add(rstSQLServer.getString("oficina"));
                areas.add(rstSQLServer.getString("area"));
            }
            rstSQLServer.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("gestionTipo-filtraGestionSelect():" + e.toString());
            result = null;
        }
        finally{
            instanciaConexion.Desconecta(connection);
            result.add(idUbicaciones);
            result.add(oficinas);
            result.add(areas);
        }  
        return result;
    }     
    
    
    static synchronized public Vector filtraUbicacionInput(String oficina,String area){
        Vector ubicacionesResult=new Vector();
        Map ubicacionRecurso = null;
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";
        
        try {
              
            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            Query = " select idUbicacion,oficina,area";
            Query += " from `ubicacionRecurso` ";
            Query += " where (oficina LIKE '%" + oficina+"%' ";
            Query += " AND area LIKE '%" + area+"%'  ";
            Query += " ) ORDER BY idUbicacion ";
            Query += " LIMIT 0 , 100 ";
            
            
            ResultSet rstSQLServer = statement.executeQuery(Query);
            
            while (rstSQLServer.next()) {
                ubicacionRecurso = new HashMap();
                ubicacionRecurso.put("idUbicacion", rstSQLServer.getString("idUbicacion"));
                ubicacionRecurso.put("oficina", rstSQLServer.getString("oficina"));
                ubicacionRecurso.put("area", rstSQLServer.getString("area"));
                ubicacionesResult.add(ubicacionRecurso);
            }
            rstSQLServer.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("gestionTipo-filtraGestionSelect():" + e.toString());
            ubicacionesResult = null;
        }
        finally{
            instanciaConexion.Desconecta(connection);
        }  
        return ubicacionesResult;
    }           
    
    
    static synchronized public boolean existeUnicaUbicacion(String oficina,String area){
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";        
        int count = 0;
        
        try {
              
            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            Query = " select idUbicacion from ubicacionRecurso ";
            Query += " WHERE oficina='" + oficina + "' ";
            Query += " AND   area='" + area + "' ";
            
            ResultSet rstSQLServer = statement.executeQuery(Query);
            
            while (rstSQLServer.next()) {
                count++;
            }
            rstSQLServer.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("gestionUbicacion-existeUnicaUbicacion():" + e.toString());
            return false;
        }
        finally{
            instanciaConexion.Desconecta(connection);
        }  
        return count!=0;
    }          
    
    static synchronized public Vector getAllUbicaciones(){
        Vector oficinas=new Vector();
        Vector areas=new Vector();
        Vector idUbicaciones=new Vector();
        Vector result=new Vector();
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";
        
//        System.out.println("Accediendo a BD: buscarFactura()");
        
        try {
              
            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            Query = " select idUbicacion,oficina,area";
            Query += " from `ubicacionRecurso` ";
            Query += " ORDER BY oficina ";
            
            
            ResultSet rstSQLServer = statement.executeQuery(Query);
            
            while (rstSQLServer.next()) {
                idUbicaciones.add(rstSQLServer.getString("idUbicacion"));
                oficinas.add(rstSQLServer.getString("oficina"));
                areas.add(rstSQLServer.getString("area"));
            }
            rstSQLServer.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("gestionTipo-filtraTipoSelect():" + e.toString());
            result = null;
        }
        finally{
            instanciaConexion.Desconecta(connection);
            result.add(idUbicaciones);
            result.add(oficinas);
            result.add(areas);
        }  
//        System.out.println("Encontrado:" + result);
        return result;
    }          
    
    
    /**
     * 
     * @return Vector con todas las Oficinas disponibles sin repetir
     */
    static synchronized public Vector getAllOficinasDistinct(){
        Vector oficinas=new Vector();
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";
        
//        System.out.println("Accediendo a BD: buscarFactura()");
        
        try {
              
            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            Query = " select distinct(oficina)";
            Query += " from `ubicacionRecurso` ";
            Query += " ORDER BY oficina ";
            
            
            ResultSet rstSQLServer = statement.executeQuery(Query);
            
            while (rstSQLServer.next()) {
                oficinas.add(rstSQLServer.getString("oficina"));
            }
            rstSQLServer.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("gestionTipo-filtraTipoSelect():" + e.toString());
            oficinas = null;
        }
        finally{
            instanciaConexion.Desconecta(connection);
        }  
//        System.out.println("Encontrado:" + result);
        return oficinas;
    }  

    
    static synchronized public boolean modificaDatosUbicacion(Map ubicacionRecurso){
        boolean resultado = false;
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";        
        
        try {
              
            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            Query = " update ubicacionRecurso set ";
            Query += " oficina='" + ubicacionRecurso.get("oficina") + "' ,";
            Query += " area='" + ubicacionRecurso.get("area") + "' ";
            Query += " where idUbicacion=" + ubicacionRecurso.get("idUbicacion")  + " ";
            
            if (statement.executeUpdate(Query) == 1) {
                resultado = true;
            }

            statement.close();
        } catch (Exception e) {
            System.out.println("gestionUbicacion-modificaDatosUbicacion():" + e.toString());
            resultado = false;
        }
        finally{
            instanciaConexion.Desconecta(connection);
        }  
        return resultado;
    }           
    
}
