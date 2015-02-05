/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import javax.servlet.http.HttpSession;
import beans.Usuario;
import beans.Validador;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author luis valerio
 */
public class AccionesUsuario {

    public AccionesUsuario() {
    }

    public synchronized static Usuario CompruebaUsuario(String user, String password) throws SQLException, Exception {

        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String nombreCompleto = null, nombreU = null, privilegio = null;
        Usuario userLogeado = null;
        String Query = "";
        Integer Coincidencias = new Integer(0);
//        System.out.println("class: AccionesUsuario --> Accion:CompruebaUsuario");
        try {
            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");


            Query = " select `apellidoPP`,`nombreP`,`privilegio`";
            Query += " from `usuario`";
            Query += " where (`nombreU`='" + user + "' and";
            Query += " `contrasena`='" + password + "' ";
            Query += " and `estado`='A') ";
            ResultSet rstSQLServer = statement.executeQuery(Query);

            while (rstSQLServer.next()) {
                Coincidencias++;
                nombreU = user;
                String apellidoPP = rstSQLServer.getString(1).toString().trim();
                nombreCompleto = rstSQLServer.getString(2).toString().trim() + " " + apellidoPP;
                privilegio = rstSQLServer.getString(3).toString().trim();
            }
            rstSQLServer.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Exception. AccionesUsuario-CompruebaUsuario:" + e.toString());
        } finally {
            instanciaConexion.Desconecta(connection);
            if (Coincidencias != 1) {
                return null;
            }
            if (Coincidencias == 1 && nombreCompleto != null && nombreU != null && privilegio != null) {
                userLogeado = new Usuario(nombreU);
                userLogeado.setNombreCompleto(nombreCompleto);
                userLogeado.setPrivilegio(privilegio);
            }
        }
        return userLogeado;
    }

    static synchronized public String[] getDatosBasicosCuenta(Usuario usuarioActual) {
        String[] Resultado = null;
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";

//        System.out.println("Accediendo a BD: getDatosCuenta()");

        try {

            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            Query = " select `nombreP`,`apellidoPP`,`apellidoMP`,`correo`,`fechaCreacion`";
            Query += " from `Usuario`";
            Query += " where (`nombreU`='" + usuarioActual.getNombreU() + "') ";

            ResultSet rstSQLServer = statement.executeQuery(Query);

            while (rstSQLServer.next()) {
                Resultado = new String[5];
                Resultado[0] = rstSQLServer.getString(1).toString().trim();
                Resultado[1] = rstSQLServer.getString(2).toString().trim();
                Resultado[2] = rstSQLServer.getString(3).toString().trim();
                Resultado[3] = rstSQLServer.getString(4).toString().trim();
                Resultado[4] = rstSQLServer.getString(5).toString().trim();
            }
            rstSQLServer.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("AccionesUsuario-CompruebaAcceso:" + e.toString());
        } finally {
            instanciaConexion.Desconecta(connection);
        }
        return Resultado;
    }

    static synchronized public boolean existeUnicoUsuario(String nombreUsuario) {
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";
        int count = 0;

        try {

            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            Query = " select `idUsuario`";
            Query += " from `Usuario`";
            Query += " where (`nombreU`='" + nombreUsuario + "') ";

            ResultSet rstSQLServer = statement.executeQuery(Query);

            while (rstSQLServer.next()) {
                count++;
            }
            rstSQLServer.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("AccionesUsuario-CompruebaAcceso:" + e.toString());
            return false;
        } finally {
            instanciaConexion.Desconecta(connection);
        }
        return count != 0;
    }

    static synchronized public Map getDetallesCuenta(String idUsuario) {
        Map resultado = null;
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";

//        System.out.println("Accediendo a BD: getDatosCuenta()");

        try {

            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            Query = " select idUsuario,nombreU,nombreP,apellidoPP,apellidoMP,privilegio,fechaCreacion,correo,estado";
            Query += " from `usuario` ";
            Query += " where (`idUsuario`='" + idUsuario + "') ";

            ResultSet rstSQLServer = statement.executeQuery(Query);

            while (rstSQLServer.next()) {
                resultado = new HashMap();
                resultado.put("idUsuario", rstSQLServer.getString("idUsuario"));
                resultado.put("nombreU", rstSQLServer.getString("nombreU"));
                resultado.put("nombreP", rstSQLServer.getString("nombreP"));
                resultado.put("apellidoPP", rstSQLServer.getString("apellidoPP"));
                resultado.put("apellidoMP", rstSQLServer.getString("apellidoMP"));
                resultado.put("privilegio", rstSQLServer.getString("privilegio"));
                resultado.put("fechaCreacion", rstSQLServer.getString("fechaCreacion"));
                resultado.put("correo", rstSQLServer.getString("correo"));
                resultado.put("estado", rstSQLServer.getString("estado"));
            }
            rstSQLServer.close();
            statement.close();
        } catch (Exception e) {
            resultado = null;
            System.out.println("AccionesUsuario-CompruebaAcceso:" + e.toString());
        } finally {
            instanciaConexion.Desconecta(connection);
        }
        return resultado;
    }
    
    static synchronized public String getNombrePersona(String nombreU) {
        String resultado = "";
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";

//        System.out.println("Accediendo a BD: getDatosCuenta()");

        try {

            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            Query = " select nombreP,apellidoPP,apellidoMP";
            Query += " from `usuario` ";
            Query += " where (`nombreU`='" + nombreU + "') ";

            ResultSet rstSQLServer = statement.executeQuery(Query);

            while (rstSQLServer.next()) {
                resultado = rstSQLServer.getString("nombreP").toUpperCase() + " ";
                resultado += rstSQLServer.getString("apellidoPP").toUpperCase() + " ";
                resultado += rstSQLServer.getString("apellidoMP").toUpperCase();
            }
            rstSQLServer.close();
            statement.close();
        } catch (Exception e) {
            resultado = "";
            System.out.println("AccionesUsuario-CompruebaAcceso:" + e.toString());
        } finally {
            instanciaConexion.Desconecta(connection);
        }
        return resultado;
    }    

    static synchronized public String[] validarCamposCuenta(String nombreU, String nombreP, String apellidoPP, String apellidoMP, String correo) {
//        System.out.println("AccionesUsuario: modificacionCuenta()|| Validando informacion de campos ...");
        String[] resultado = new String[5];
        boolean conErrores = false;
        if (!Validador.validaNombreU(nombreU)) {
            resultado[0] = "Este campo debe de terminar con una letra y solo puede contener el digito (-).";
            conErrores = true;
        }
        if (!Validador.validaNombres(nombreP)) {
            resultado[1] = "El nombre solo puede contener letras.";
            conErrores = true;
        }
        if (!Validador.validaNombres(apellidoPP)) {
            resultado[2] = "El apellido paterno solo puede contener letras.";
            conErrores = true;
        }
        if (!Validador.validaNombres(apellidoMP)) {
            resultado[3] = "El apellido materno solo puede contener letras.";
            conErrores = true;
        }
        if (!Validador.validaCorreo(correo)) {
            resultado[4] = "El correo no cumple con el formato.";
            conErrores = true;
        }
        if (!conErrores) {
            return null;
        }

        return resultado;
    }

    static synchronized public boolean insertaNuevoUsuario(Map usuario) {
        boolean resultado = false;
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";
        SimpleDateFormat formato = new SimpleDateFormat("YYYY-MM-dd");

        try {

            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            Query = " INSERT INTO usuario  (`idUsuario` ,"
                    + "`nombreU` ,`contrasena` ,`nombreP` ,`apellidoPP` ,"
                    + "`apellidoMP` ,`privilegio` ,`fechaCreacion` ,`correo` ,"
                    + "`estado` ) ";
            Query += " VALUES (NULL, '" + usuario.get("nombreU") + "', ";
            Query += " '" + usuario.get("contrasena") + "', ";
            Query += " '" + usuario.get("nombreP") + "', ";
            Query += " '" + usuario.get("apellidoPP") + "', ";
            Query += " '" + usuario.get("apellidoMP") + "', ";
            Query += " '" + usuario.get("privilegio") + "', ";
            Query += " CURDATE(), ";
            Query += " '" + usuario.get("correo") + "', ";
            Query += " 'A') ";

            if (statement.executeUpdate(Query) == 1) {
                resultado = true;
            }

            statement.close();
        } catch (Exception e) {
            System.out.println("gestionTipo-insertaNuevoUsuario():" + e.toString());
            resultado = false;
        } finally {
            instanciaConexion.Desconecta(connection);
        }
        return resultado;
    }

    static synchronized public String modificarDatosCuenta(String nombreUAnt, String nombreU, String nombreP, String apellidoPP, String apellidoMP, String privilegio, String correo) {

        String resultado = null;

        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";

//        System.out.println("Accediendo a BD: modificacionDatosCuenta(...)");

        try {

            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            Query = " UPDATE Usuario";
            Query += " SET `nombreU` ='" + nombreU + "',";
            Query += " `nombreP` ='" + nombreP + "',";
            Query += " `apellidoPP` ='" + apellidoPP + "',";
            Query += " `apellidoMP` ='" + apellidoMP + "',";
            Query += " `privilegio` ='" + privilegio + "',";
            Query += " `correo` ='" + correo + "'";
            Query += " WHERE (`nombreU`='" + nombreUAnt + "') ";

            statement.executeUpdate(Query);
            statement.close();
        } catch (Exception e) {
            resultado = "Error en Base de Datos";
            System.out.println("Exception: AccionesUsuario-Modificacion de Cuenta:" + e.toString());
        } finally {
            instanciaConexion.Desconecta(connection);
        }
        return resultado;
    }

    static synchronized public boolean verificaExistenciaCampo(String tabla, String columna, String valor) {

//        System.out.println("AccionesUsuario: modificacionCuenta()|| Validando informacion de campos ...");
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";
        int cuentaResult = 9;

//        System.out.println("Accediendo a BD: validarCamposCuenta(...)");

        try {

            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            Query = " select count(*)";
            Query += " from `" + tabla + "` ";
            Query += " where (`" + columna + "`='" + valor + "') ";

            ResultSet rstSQLServer = statement.executeQuery(Query);

            while (rstSQLServer.next()) {
                cuentaResult = rstSQLServer.getInt(1);
//                System.out.println("Coincidencias: " + cuentaResult);
            }
            rstSQLServer.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Exception: AccionesUsuario-Verifica existencia de usuario:" + e.toString());
        } finally {
            instanciaConexion.Desconecta(connection);
        }
        return (cuentaResult != 0);
    }

    static synchronized public String modificaPassword(String nombreU, String password) {

        String resultado = null;
//        System.out.println("AccionesUsuario: modificacionPassword()");
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";

        try {

            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            Query = " UPDATE Usuario";
            Query += " SET `contrasena` ='" + password + "'";
            Query += " WHERE (`nombreU`='" + nombreU + "') ";

            statement.executeUpdate(Query);
            statement.close();
        } catch (Exception e) {
            System.out.println("Exception: AccionesUsuario-Modificacion de Password:" + e.toString());
            resultado = "Error en Base de Datos";
        } finally {
            instanciaConexion.Desconecta(connection);
        }
        return resultado;
    }

    static synchronized public Vector filtraCuentasInput(String usuario, String nombre, String apellidoPaterno, String apellidoMaterno, String privilegioUsrActual) {
        Map detalleCuenta = null;
        Vector resultado = new Vector();
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";

        try {

            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            Query = " select idUsuario,nombreU,nombreP,apellidoPP,privilegio,correo";
            Query += " from `usuario` ";
            Query += " where nombreU like '%" + usuario + "%'";
            Query += " and nombreP like '%" + nombre + "%'";
            Query += " and apellidoPP like '%" + apellidoPaterno + "%'";
            Query += " and apellidoMP like '%" + apellidoMaterno + "%'";
            if (privilegioUsrActual.equals("Administrador")) {
                Query += " and privilegio IN('Administrador','Estandar')";
            }
//            Query += " and privilegio like '%" + descripcion + "%'";    

            ResultSet rstSQLServer = statement.executeQuery(Query);

            while (rstSQLServer.next()) {
                detalleCuenta = new HashMap();
                detalleCuenta.put("idUsuario", rstSQLServer.getString("idUsuario"));
                detalleCuenta.put("nombreU", rstSQLServer.getString("nombreU"));
                detalleCuenta.put("nombreP", rstSQLServer.getString("nombreP"));
                detalleCuenta.put("apellidoPP", rstSQLServer.getString("apellidoPP"));
                detalleCuenta.put("privilegio", rstSQLServer.getString("privilegio"));
                detalleCuenta.put("correo", rstSQLServer.getString("correo"));
                resultado.add(detalleCuenta);
            }
            rstSQLServer.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("accionesUsuario-filtraCuentasInput():" + e.toString());
            resultado = null;
        } finally {
            instanciaConexion.Desconecta(connection);
        }
        return resultado;
    }

    static synchronized public boolean modificaDatosTipoRecurso(Map detalleUsuario) {
        boolean resultado = false;
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";

        try {

            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            Query = " update usuario set ";
            Query += " nombreU='" + detalleUsuario.get("nombreU") + "' ,";
            Query += " nombreP='" + detalleUsuario.get("nombreP") + "' ,";
            Query += " apellidoPP='" + detalleUsuario.get("apellidoPP") + "' ,";
            Query += " apellidoMP='" + detalleUsuario.get("apellidoMP") + "' ,";
            Query += " privilegio='" + detalleUsuario.get("privilegio") + "' ,";
            Query += " fechaCreacion='" + detalleUsuario.get("fechaCreacion") + "' ,";
            Query += " correo='" + detalleUsuario.get("correo") + "' ,";
            Query += " estado='" + detalleUsuario.get("estado") + "' ";
            Query += " where idUsuario=" + detalleUsuario.get("idUsuario") + " ";

            if (statement.executeUpdate(Query) == 1) {
                resultado = true;
            }

            statement.close();
        } catch (Exception e) {
            System.out.println("gestionTipo-modificaDatosTipoRecurso():" + e.toString());
            resultado = false;
        } finally {
            instanciaConexion.Desconecta(connection);
        }
        return resultado;
    }
}
