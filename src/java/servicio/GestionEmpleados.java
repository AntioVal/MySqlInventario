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
public class GestionEmpleados {

    static synchronized public Vector filterUserInput(String name) {
        Vector nombres = new Vector();
        Vector ids = new Vector();
        Vector result = new Vector();
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";

        try {

            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            Query = " select idEmpleado,nombreCompleto";
            Query += " from `empleado` ";
            Query += " where nombreCompleto LIKE '%" + name + "%' ";
            Query += " ORDER BY nombreCompleto ";
            Query += " LIMIT 0 , 100 ";


            ResultSet rstSQLServer = statement.executeQuery(Query);

            while (rstSQLServer.next()) {
                nombres.add(rstSQLServer.getString("idEmpleado"));
                ids.add(rstSQLServer.getString("nombreCompleto"));
            }
            rstSQLServer.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("gestionEstado-getAllEstados():" + e.toString());
            result = null;
        } finally {
            instanciaConexion.Desconecta(connection);
            result.add(nombres);
            result.add(ids);
        }
//            System.out.println("result:" + result);
        return result;
    }
    
    
    static synchronized public Vector filterUserInput(String idEmpleado,String name) {
        Map empleado = null;
        Vector result = new Vector();
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";

        try {

            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            Query = " select idEmpleado,nombreCompleto";
            Query += " from `empleado` ";
            Query += " where idEmpleado LIKE '%" + idEmpleado + "%' ";
            Query += " and nombreCompleto LIKE '%" + name + "%' ";
            Query += " ORDER BY nombreCompleto ";
            Query += " LIMIT 0 , 100 ";


            ResultSet rstSQLServer = statement.executeQuery(Query);

            while (rstSQLServer.next()) {
                empleado = new HashMap();
                empleado.put("idEmpleado", rstSQLServer.getString("idEmpleado"));
                empleado.put("nombreCompleto", rstSQLServer.getString("nombreCompleto"));
                result.add(empleado);
            }
            rstSQLServer.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("gestionEstado-getAllEstados():" + e.toString());
            result = null;
        } finally {
            instanciaConexion.Desconecta(connection);
        }
        return result;
    }

    /**
     *
     * @param name: Cadena que contiene el nombre del empleado buscar, es
     * exactamente el mismo que en BD
     * @return Integer id, regresa el id del empleado, null si ocurre un error,
     * 0 si no hay coincidencias y negativo mostrando el numero de coincidencias
     * que existen
     */
    static synchronized public Integer getIdNombreUnico(String name) {
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        Integer idEmpleado = null;
        int count = 0;
        String Query = "";

        try {

            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            Query = " select idEmpleado";
            Query += " from `empleado` ";
            Query += " where nombreCompleto = '" + name + "' ";


            ResultSet rstSQLServer = statement.executeQuery(Query);

            while (rstSQLServer.next()) {
                count--;
                idEmpleado = Integer.parseInt(rstSQLServer.getString("idEmpleado"));
            }
            rstSQLServer.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("gestionEstado-getAllEstados():" + e.toString());
            idEmpleado = null;
        } finally {
            if (count != 0) {
                if (Math.abs(count) > 2) {
                    return count;
                }
            } else {
                return count;
            }
            instanciaConexion.Desconecta(connection);
        }
//            System.out.println("result:" + result);
        return idEmpleado;
    }
    
    /**
     * 
     * @param idEmpleado
     * @return 
     */
    static synchronized public String getNombreUnicoPorId(String idEmpleado) {
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String nombreCompleto= null;
        int count=0;
        String Query = "";

        try {

            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            Query = " select nombreCompleto";
            Query += " from `empleado` ";
            Query += " where idEmpleado = '" + idEmpleado + "' ";


            ResultSet rstSQLServer = statement.executeQuery(Query);

            while (rstSQLServer.next()) {
                count++;
                nombreCompleto = rstSQLServer.getString("nombreCompleto");
            }
            rstSQLServer.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("gestionEstado-getAllEstados():" + e.toString());
            nombreCompleto = null;
        } finally {
            if (count == 0) {
                return "Sin_coincidencias";
            }              
            if (count != 1) {
                return "Multiples_coincidencias";
            }            
            instanciaConexion.Desconecta(connection);
        }
//            System.out.println("result:" + result);
        return nombreCompleto;
    }    
    
    static synchronized public boolean modificaDatosEmpleado(String idEmpleadoAnterior,String idEmpleado, String nombreCompleto){
        boolean resultado = false;
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";        
        
        try {
              
            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            Query = " update empleado set ";
            Query += " idEmpleado='" + idEmpleado + "' ,";
            Query += " nombreCompleto='" + nombreCompleto + "' ";
            Query += " where idEmpleado='" + idEmpleadoAnterior + "' ";
            
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
    
    static synchronized public boolean insertaNuevoEmpleado(String idEmpleado, String nombreCompleto){
        boolean resultado = false;
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";        
        
        try {
              
            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            Query = " INSERT INTO empleado (`idEmpleado`, `nombreCompleto`)" ;
            Query += " VALUES ('" + idEmpleado + "',";
            Query += " '" + nombreCompleto + "') ";
            
            if (statement.executeUpdate(Query) == 1) {
                resultado = true;
            }

            statement.close();
        } catch (Exception e) {
            System.out.println("gestionTipo-insertaNuevoTipoRecurso():" + e.toString());
            resultado = false;
        }
        finally{
            instanciaConexion.Desconecta(connection);
        }  
        return resultado;
    }           
    
    
    
}
