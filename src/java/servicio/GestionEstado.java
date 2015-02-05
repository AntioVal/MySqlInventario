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
public class GestionEstado {
    
    
        static synchronized public Vector getAllEstados(){
        Vector nombres=new Vector();
        Vector descripciones=new Vector();
        Vector result=new Vector();
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";
        
        try {
              
            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            Query = " select nombre,descripcion";
            Query += " from `estadoRecurso` ";
            Query += " ORDER BY nombre ";
            
            
            ResultSet rstSQLServer = statement.executeQuery(Query);
            
            while (rstSQLServer.next()) {
                nombres.add(rstSQLServer.getString("nombre"));
                descripciones.add(rstSQLServer.getString("descripcion"));
            }
            rstSQLServer.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("gestionEstado-getAllEstados():" + e.toString());
            result = null;
        }
        finally{
            instanciaConexion.Desconecta(connection);
            result.add(nombres);
            result.add(descripciones);
        }  
        return result;
    }      
        
/**
 * Obtiene el detalle de un tipo de etatus que es un Map con llaves: idEstadoRecurso,nombre,descripcion
 * @param nombre
 * @return 
 */        
        
        static synchronized public Map getDetalleEstado(String nombre){
        Map detalleEstado = new HashMap();
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";
        
        try {
              
            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            Query = " select idEstadoRecurso,nombre,descripcion";
            Query += " from `estadoRecurso` ";
            Query += " where nombre ='" + nombre + "'";            
            
            ResultSet rstSQLServer = statement.executeQuery(Query);
            
            while (rstSQLServer.next()) {
                detalleEstado.put("idEstadoRecurso", rstSQLServer.getString("idEstadoRecurso"));
                detalleEstado.put("nombre", rstSQLServer.getString("nombre"));
                detalleEstado.put("descripcion", rstSQLServer.getString("descripcion"));
            }
            rstSQLServer.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("gestionEstado-getDetalleEstado():" + e.toString());
            detalleEstado = null;
        }
        finally{
            instanciaConexion.Desconecta(connection);
        }  
        if(detalleEstado.size()!=3)
        return null;

        return detalleEstado;
    }              
        /**
         * Regresa true si la licencia ya existe
         * @param licencia
         * @return 
         */
    static synchronized public boolean existeUnicoEstado(String nombre){
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";        
        int count = 0;
        
        try {
              
            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            Query = " select idEstadoRecurso from estadoRecurso ";
            Query += " WHERE nombre='" + nombre + "' ";
            
            ResultSet rstSQLServer = statement.executeQuery(Query);
            
            while (rstSQLServer.next()) {
                count++;
            }
            rstSQLServer.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("gestionEstado-existeUnicoEstado():" + e.toString());
            return false;
        }
        finally{
            instanciaConexion.Desconecta(connection);
        }  
        return count!=0;
    }                
    
    static synchronized public boolean insertaNuevoEstado(String nombre,String descripcion){
        boolean resultado = false;
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";        
        
        try {
              
            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            Query = " INSERT INTO estadoRecurso (`idEstadoRecurso`, `nombre`, `	descripcion`)" ;
            Query += " VALUES ( NULL, '" + nombre + "', ";
            Query += " '"+ descripcion + "') ";
            
            if (statement.executeUpdate(Query) == 1) {
                resultado = true;
            }

            statement.close();
        } catch (Exception e) {
            System.out.println("gestionEstado-insertaNuevoEstado():" + e.toString());
            resultado = false;
        }
        finally{
            instanciaConexion.Desconecta(connection);
        }  
        return resultado;
    }           
        
        static synchronized public Vector filtraEstatusInput(String nombre,String descripcion){
        Map detalleEstado = null;
        Vector resultado = new Vector();
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";
        
        try {
              
            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            Query = " select idEstadoRecurso,nombre,descripcion";
            Query += " from `estadoRecurso` ";
            Query += " where nombre like '%" + nombre + "%'";            
            Query += " and descripcion like '%" + descripcion + "%'";            
            
            ResultSet rstSQLServer = statement.executeQuery(Query);
            
            while (rstSQLServer.next()) {
                detalleEstado = new HashMap();
                detalleEstado.put("idEstadoRecurso", rstSQLServer.getString("idEstadoRecurso"));
                detalleEstado.put("nombre", rstSQLServer.getString("nombre"));
                detalleEstado.put("descripcion", rstSQLServer.getString("descripcion"));
                resultado.add(detalleEstado);
            }
            rstSQLServer.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("gestionEstado-filtraEstatusInput():" + e.toString());
            resultado = null;
        }
        finally{
            instanciaConexion.Desconecta(connection);
        }  

        return resultado;
    }        
        
    static synchronized public boolean modificaDatosEstadoRecurso(Map detalleEstado){
        boolean resultado = false;
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";        
        
        try {
              
            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            Query = " update estadoRecurso set ";
            Query += " nombre='" + detalleEstado.get("nombre") + "' ,";
            Query += " descripcion='" + detalleEstado.get("descripcion")  + "' ";
            Query += " where idEstadoRecurso=" + detalleEstado.get("idEstadoRecurso") + " ";
            
            if (statement.executeUpdate(Query) == 1) {
                resultado = true;
            }

            statement.close();
        } catch (Exception e) {
            System.out.println("gestionEstado-modificaDatosEstadoRecurso():" + e.toString());
            resultado = false;
        }
        finally{
            instanciaConexion.Desconecta(connection);
        }  
        return resultado;
    }                
    
}
