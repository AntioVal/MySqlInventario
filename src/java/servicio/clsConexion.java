/*
 *    Creado por:                   Luis-valerio
 *    Fecha:                        23/02/2011
 *    Descripción:                  Controlador : "clsConexion.java" Conecta y Desconecta a la Base de Datos.
 */
package servicio;

import java.sql.*;

/*
 * Clase que proporciona los métodos necesarios
 * para conectar y desconectar a una Base de Datos.
 */
public class clsConexion {

    /**
     * Creates a new instance of clsConexion
     */
    public clsConexion() {
    }

    /**
     * Método para obtener una conexión a la base de datos mediante un ODBC
     */
    public Connection ConectaSQLServer() throws SQLException {
        Connection lcnnConexion;

        // Conexión local
        String login = "sa";
        String password = "admin";
        String classForName = "sun.jdbc.odbc.JdbcOdbcDriver";
        String url = "jdbc:odbc:Prometeo";

        // Conexión local en servidor (189.204.71.149)
//        String login = "fideicomiso";
        //      String password = "FIDEICOMISO2011X";
        //    String classForName = "sun.jdbc.odbc.JdbcOdbcDriver";
        //  String url = "jdbc:odbc:Atenea";

        try {
            Class.forName(classForName);
            // Conexión
            lcnnConexion = DriverManager.getConnection(url, login, password);
        } catch (Exception e) {
            System.out.println(e.toString());
            lcnnConexion = null;
        }
        return lcnnConexion;
    }

    public Connection ConectaMySQL() {
        Connection conexion = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            String servidor = "jdbc:mysql://localhost/inventario";
            String usuarioDB = "root";
            String passwordDB = "";
//            String usuarioDB = "Administrador";
//            String passwordDB = "sistemas#2014";
            conexion = DriverManager.getConnection(servidor, usuarioDB, passwordDB);
        } catch (ClassNotFoundException ex) {
//            JOptionPane.showMessageDialog(null, ex, "Error1 en la Conexión con la BD "+ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            conexion = null;
        } catch (SQLException ex) {
//            JOptionPane.showMessageDialog(null, ex, "Error2 en la Conexión con la BD "+ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            conexion = null;
        } catch (Exception ex) {
//            JOptionPane.showMessageDialog(null, ex, "Error3 en la Conexión con la BD "+ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            conexion = null;
        } finally {
            return conexion;
        }
    }

    /**
     * Método para desconectar una conexión existente
     */
    public boolean Desconecta(Connection lcnnConexion) {
        try {
            lcnnConexion.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
