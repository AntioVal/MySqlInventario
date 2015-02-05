/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 *
 * @author Luis-Valerio
 */
public class GestionLicencias {
    
    
    /**
     * Obtiene las licencia distinguidas por el nombre de licencias, solo los nombres de todas las posibilidades
     * @return 
     */
        static synchronized public Vector getDistinctLicencias(){
        Vector nombres=new Vector();
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";
        
        try {
              
            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            Query = " select DISTINCT(nombre)";
            Query += " from `datoslicencia` ";
            Query += " ORDER BY nombre ";
            Query += " LIMIT 0 , 100 ";
            
            
            ResultSet rstSQLServer = statement.executeQuery(Query);
            
            while (rstSQLServer.next()) {
                nombres.add(rstSQLServer.getString("nombre"));
            }
            rstSQLServer.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("gestionLicencias-getDistinctLicencias():" + e.toString());
            nombres = null;
        }
        finally{
            instanciaConexion.Desconecta(connection);
        }  
        return nombres;
    }
        
        
    static synchronized public Vector filterLicInput(String idDatosLicencia,String nombre,String clave) {
        Map licencia = null;
        Vector result = new Vector();
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";

        try {

            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            Query = " select idDatosLicencia,nombre,clave,observaciones";
            Query += " from `datosLicencia` ";
            Query += " where idDatosLicencia LIKE '%" + idDatosLicencia + "%' ";
            Query += " and nombre LIKE '%" + nombre + "%' ";
            Query += " and clave LIKE '%" + clave + "%' ";
            Query += " ORDER BY idDatosLicencia ";
            Query += " LIMIT 0 , 100 ";


            ResultSet rstSQLServer = statement.executeQuery(Query);

            while (rstSQLServer.next()) {
                licencia = new HashMap();
                licencia.put("idDatosLicencia", rstSQLServer.getString("idDatosLicencia"));
                licencia.put("nombre", rstSQLServer.getString("nombre"));
                licencia.put("clave", rstSQLServer.getString("clave"));
                licencia.put("observaciones", rstSQLServer.getString("observaciones"));
                result.add(licencia);
            }
            rstSQLServer.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("gestionLicencias-filterLicInput():" + e.toString());
            result = null;
        } finally {
            instanciaConexion.Desconecta(connection);
        }
        return result;
    }        
    
    static synchronized public boolean modificaDatosLicencia(String idDatosLicencia,String nombre, String clave,String observaciones){
        boolean resultado = false;
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";        
        
        try {
              
            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            Query = " update datosLicencia set ";
            Query += " nombre='" + nombre + "', ";
            Query += " clave='" + clave + "', ";
            Query += " observaciones='" + observaciones + "' ";
            Query += " where idDatosLicencia='" + idDatosLicencia + "' ";
            
            if (statement.executeUpdate(Query) == 1) {
                resultado = true;
            }
            statement.close();
        } catch (Exception e) {
            System.out.println("GestionEmpleados-modificaDatosEmpleado():" + e.toString());
            resultado = false;
        }
        finally{
            instanciaConexion.Desconecta(connection);
        }  
        return resultado;
    }    
    
    static synchronized public boolean existeUnicaLicencia(Map licencia){
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";        
        int count = 0;
        
        try {
              
            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            Query = " select idDatosLicencia from datosLicencia ";
            Query += " WHERE nombre='" + licencia.get("nombre") + "' ";
            Query += " AND   clave='" + licencia.get("claveLicencia") + "' ";
            
            ResultSet rstSQLServer = statement.executeQuery(Query);
            
            while (rstSQLServer.next()) {
                count++;
            }
            rstSQLServer.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("gestionTipo-existeUnicoTipoRecurso():" + e.toString());
            return false;
        }
        finally{
            instanciaConexion.Desconecta(connection);
        }  
        return count!=0;
    }         
    
    static synchronized public boolean insertaNuevaLicencia(Map licencia){
        boolean resultado = false;
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";        
        
        try {
              
            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            Query = " INSERT INTO datosLicencia (`idDatosLicencia`, `nombre`, `clave`, `observaciones`)" ;
            Query += " VALUES (NULL, '" + licencia.get("nombre") + "', ";
            Query += " '"+ licencia.get("claveLicencia") + "', ";
            Query += " '"+ licencia.get("observaciones") + "' ) ";
            
            if (statement.executeUpdate(Query) == 1) {
                resultado = true;
            }

            statement.close();
        } catch (Exception e) {
            System.out.println("gestionTipo-modificaDatosTipoRecurso():" + e.toString());
            resultado = false;
        }
        finally{
            instanciaConexion.Desconecta(connection);
        }  
        return resultado;
    }           
    
    /**
     * Crea una nueva relacion recurso_has_licencia, esto es, se vincula una licencia a un recurso
     * @param licencia
     * @return 
     */
    static synchronized public boolean vincularLicencia(int idRecurso, int idLicencia){
        boolean resultado = false;
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";        
        
        try {
              
            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            Query = " INSERT INTO recurso_has_datoslicencia (`recurso_idRecurso`, `datosLicencia_idDatosLicencia`)" ;
            Query += " VALUES ( ";
            Query += " '" + idRecurso + "', ";
            Query += " '" + idLicencia + "' ) ";
            
            if (statement.executeUpdate(Query) == 1) {
                resultado = true;
            }

            statement.close();
        } catch (Exception e) {
            System.out.println("gestionLicencias-vincularLicencia():" + e.toString());
            resultado = false;
        }
        finally{
            instanciaConexion.Desconecta(connection);
        }  
        return resultado;
    }     
    
    
    static synchronized public boolean desvincularLicencia(int idRecurso, int idLicencia){
        boolean resultado = false;
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";        
        
        try {
              
            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            Query = " DELETE FROM recurso_has_datoslicencia " ;
            Query += " WHERE recurso_idRecurso = '" + idRecurso + "' ";
            Query += " AND datosLicencia_idDatosLicencia = '" + idLicencia + "' ";
            
            if (statement.executeUpdate(Query) == 1) {
                resultado = true;
            }

            statement.close();
        } catch (Exception e) {
            System.out.println("gestionLicencias-vincularLicencia():" + e.toString());
            resultado = false;
        }
        finally{
            instanciaConexion.Desconecta(connection);
        }  
        return resultado;
    }           
    
}
