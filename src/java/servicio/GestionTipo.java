/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio;

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
public class GestionTipo {

    static synchronized public Map getDetallesTipo(String idTipo) {
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
            Query += " from `tipo`";
            Query += " where `idTipo`='" + idTipo + "' ";

            ResultSet rstSQLServer = statement.executeQuery(Query);

            while (rstSQLServer.next()) {
                resultado = new HashMap();
                resultado.put("idTipo", idTipo);
                resultado.put("tipo", rstSQLServer.getString("tipo"));
                resultado.put("marca", rstSQLServer.getString("marca"));
                resultado.put("modelo", rstSQLServer.getString("modelo"));
                resultado.put("descripcion", rstSQLServer.getString("descripcion"));
            }
            rstSQLServer.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Exception:gestionTipo-getDetallesTipo():" + e.toString());
            resultado = null;
        } finally {
            instanciaConexion.Desconecta(connection);
        }
        return resultado;
    }

    static synchronized public String getDescripcionesPorIdRecurso(String listaRecursosId) {
        String descripciones = "";
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";

        try {

            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            Query = " select rec.idRecurso, tipo.idTipo, tipo.descripcion";
            Query += " from `recurso` as rec, `tipo` as tipo ";
            Query += " where `idRecurso` IN (" + listaRecursosId + ") ";
            Query += " AND rec.tipo_idTipo= tipo.idTipo ";
            Query += " order by idRecurso ";

            ResultSet rstSQLServer = statement.executeQuery(Query);

            while (rstSQLServer.next()) {
                String descripcion = rstSQLServer.getString("descripcion");
                if (descripcion != null && !descripcion.isEmpty() && descripcion.length()>4) {
                    descripciones += " [" + descripcion + "]";
                }
            }
            rstSQLServer.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Exception:gestionTipo-getDescripcionesPorIdRecurso():" + e.toString());
            descripciones = "";
        } finally {
            instanciaConexion.Desconecta(connection);
        }
        return descripciones;
    }

    static synchronized public Vector filtraTipoSelect(String tipo, String marca, String modelo) {
        Vector tipos = new Vector();
        Vector idTipo = new Vector();
        Vector marcas = new Vector();
        Vector modelos = new Vector();
        Vector result = new Vector();
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";

        try {

            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            Query = " select idTipo,tipo,marca,modelo";
            Query += " from `tipo` ";
            Query += " where (tipo= '" + tipo + "' ";
            if (marca != null && !marca.equals("")) {
                Query += " AND marca='" + marca + "'  ";
            }
            if (modelo != null && !modelo.equals("")) {
                Query += " AND modelo='" + modelo + "' ";
            }
            Query += " ) ORDER BY tipo ";
            Query += " LIMIT 0 , 100 ";


            ResultSet rstSQLServer = statement.executeQuery(Query);

            while (rstSQLServer.next()) {
                idTipo.add(rstSQLServer.getString("idTipo"));
                tipos.add(rstSQLServer.getString("tipo"));
                marcas.add(rstSQLServer.getString("marca"));
                modelos.add(rstSQLServer.getString("modelo"));
            }
            rstSQLServer.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("gestionTipo-filtraTipoSelect():" + e.toString());
            result = null;
        } finally {
            instanciaConexion.Desconecta(connection);
            result.add(idTipo);
            result.add(tipos);
            result.add(marcas);
            result.add(modelos);
        }
        return result;
    }

    static synchronized public Vector filtraTipoInput(String tipo, String marca, String modelo) {
        Vector tipos = new Vector();
        Vector idTipo = new Vector();
        Vector marcas = new Vector();
        Vector modelos = new Vector();
        Vector result = new Vector();
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";

        try {

            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            Query = " select *";
            Query += " from `tipo` ";
            Query += " where (tipo LIKE '%" + tipo + "%' ";
            Query += " AND marca LIKE '%" + marca + "%'  ";
            Query += " AND modelo LIKE '%" + modelo + "%' ";
            Query += " ) ORDER BY tipo ";
            Query += " LIMIT 0 , 100 ";


            ResultSet rstSQLServer = statement.executeQuery(Query);

            while (rstSQLServer.next()) {
                idTipo.add(rstSQLServer.getString("idTipo"));
                tipos.add(rstSQLServer.getString("tipo"));
                marcas.add(rstSQLServer.getString("marca"));
                modelos.add(rstSQLServer.getString("modelo"));
            }
            rstSQLServer.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("gestionTipo-filtraTipoSelect():" + e.toString());
            result = null;
        } finally {
            instanciaConexion.Desconecta(connection);
            result.add(idTipo);
            result.add(tipos);
            result.add(marcas);
            result.add(modelos);
        }
        return result;
    }

    static synchronized public Vector getAllTipos() {
        Vector tipos = new Vector();
        Vector marcas = new Vector();
        Vector modelos = new Vector();
        Vector result = new Vector();
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";

        try {

            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            Query = " select tipo,marca,modelo";
            Query += " from `tipo` ";
            Query += " ORDER BY tipo ";


            ResultSet rstSQLServer = statement.executeQuery(Query);

            while (rstSQLServer.next()) {
                tipos.add(rstSQLServer.getString("tipo"));
                marcas.add(rstSQLServer.getString("marca"));
                modelos.add(rstSQLServer.getString("modelo"));
            }
            rstSQLServer.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("gestionTipo-filtraTipoSelect():" + e.toString());
            result = null;
        } finally {
            instanciaConexion.Desconecta(connection);
            result.add(tipos);
            result.add(marcas);
            result.add(modelos);
        }
        return result;
    }

    static synchronized public boolean modificaDatosTipoRecurso(Tipo tipoIn) {
        boolean resultado = false;
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";

        try {

            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            Query = " update tipo set ";
            Query += " tipo='" + tipoIn.getTipo() + "' ,";
            Query += " marca='" + tipoIn.getMarca() + "' ,";
            Query += " modelo='" + tipoIn.getModelo() + "', ";
            Query += " descripcion='" + tipoIn.getDescripcion() + "' ";
            Query += " where idTipo=" + tipoIn.getIdTipo() + " ";

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

    static synchronized public boolean insertaNuevoTipoRecurso(Tipo tipoIn) {
        boolean resultado = false;
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";

        try {

            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            Query = " INSERT INTO tipo (`idTipo`, `tipo`, `marca`, `modelo`, `descripcion`)";
            Query += " VALUES (NULL, '" + tipoIn.getTipo() + "', ";
            Query += " '" + tipoIn.getMarca() + "', ";
            Query += " '" + tipoIn.getModelo() + "', ";
            Query += " '" + tipoIn.getDescripcion() + "') ";

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

    static synchronized public boolean existeUnicoTipoRecurso(Tipo tipoIn) {
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";
        int count = 0;

        try {

            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            Query = " select idTipo from tipo ";
            Query += " WHERE tipo='" + tipoIn.getTipo() + "' ";
            Query += " AND   marca='" + tipoIn.getMarca() + "' ";
            Query += " AND   modelo='" + tipoIn.getModelo() + "' ";

            ResultSet rstSQLServer = statement.executeQuery(Query);

            while (rstSQLServer.next()) {
                count++;
            }
            rstSQLServer.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("gestionTipo-existeUnicoTipoRecurso():" + e.toString());
            return false;
        } finally {
            instanciaConexion.Desconecta(connection);
        }
        return count != 0;
    }
}
