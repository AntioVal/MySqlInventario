/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servicio;

import beans.Factura;
import beans.Proveedor;
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
 * @author luis-valerio
 */
public class gestionFacturas {

    static synchronized public Vector buscarFactura(String Folio, String fechaAdquisicion, String proveedor) {
        String Resultado[] = null;
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        int countResult = 0;
        Vector<String[]> resultadoFinal = new Vector<String[]>();
        String Query = "";

//        System.out.println("Accediendo a BD: buscarFactura()");

        try {

            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            Query = " select fac.idFactura,fac.folio,fac.fechaAdquisicion,pro.razonSocial,fac.montoSinIVA,fac.iva,fac.montoConIVA";
            Query += " from `factura` as fac,`proveedor` as pro";
            Query += " where (fac.folio LIKE '%" + Folio + "%' ";
            if (proveedor != null && !proveedor.equals("") && !proveedor.equals("Todos")) {
                Query += " AND pro.razonSocial LIKE '%" + proveedor + "%' ";
            }
            if (fechaAdquisicion != null && !fechaAdquisicion.equals("")) {
                Query += " AND fac.fechaAdquisicion='" + fechaAdquisicion + "' ";
            }
            Query += " AND pro.idProveedor= fac.Proveedor_idProveedor) ";
            Query += " ORDER BY fac.fechaAdquisicion ";
            Query += " LIMIT 0 , 100 ";


            ResultSet rstSQLServer = statement.executeQuery(Query);

            while (rstSQLServer.next()) {
                Resultado = new String[7];
                Resultado[0] = rstSQLServer.getString("idFactura");
                Resultado[1] = rstSQLServer.getString("folio");
                Resultado[2] = rstSQLServer.getString("fechaAdquisicion");
                Resultado[3] = rstSQLServer.getString("razonSocial");
                Resultado[4] = rstSQLServer.getString("montoSinIVA");
                Resultado[5] = rstSQLServer.getString("iva");
                Resultado[6] = rstSQLServer.getString("montoConIVA");
                resultadoFinal.add(Resultado);
            }
            rstSQLServer.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("gestionFacturas-buscarFactura():" + e.toString());
            resultadoFinal = null;
        } finally {
            instanciaConexion.Desconecta(connection);
        }
        return resultadoFinal;
    }

    static synchronized public Vector<Factura> buscarFacturaSelect(String idFactura, String folio, String razonSocial,
            String articulos, String montoConIVA) {
        Factura factura = null;
        Proveedor proveedor = null;
        Vector<Factura> resultado = new Vector<Factura>();
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";

        try {

            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");
            Query = " select fac.idFactura,fac.folio,fac.fechaAdquisicion,pro.razonSocial,fac.montoConIVA,fac.articulos";
            Query += " from `factura` as fac,`proveedor` as pro";
            Query += " where fac.idFactura LIKE '%" + idFactura + "%' ";
            Query += " and fac.folio LIKE '%" + folio + "%' ";
            Query += " and pro.razonSocial LIKE '%" + razonSocial + "%' ";
            if (!articulos.isEmpty()) {
                Query += " and fac.articulos ='" + articulos + "' ";
            }
            if (!montoConIVA.isEmpty()) {
                Query += " and fac.montoConIVA ='" + montoConIVA + "' ";
            }
            Query += " and fac.Proveedor_idProveedor=pro.idProveedor ";
            Query += " ORDER BY fac.idFactura ";
            Query += " LIMIT 0 , 100 ";


            ResultSet rstSQLServer = statement.executeQuery(Query);

            while (rstSQLServer.next()) {
                factura = new Factura();
                proveedor = new Proveedor();
                factura.setIdFactura(rstSQLServer.getInt("idFactura"));
                factura.setFolio(rstSQLServer.getString("folio"));
                factura.setFechaAdquisicion(rstSQLServer.getDate("fechaAdquisicion"));
                proveedor.setRazonSocial(rstSQLServer.getString("razonSocial"));
                factura.setProveedor(proveedor);
                factura.setMontoConIVA(rstSQLServer.getFloat("montoConIVA"));
                factura.setArticulos(rstSQLServer.getInt("articulos"));
                resultado.add(factura);
            }
            rstSQLServer.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("gestionFacturas-buscarFacturaSelect():" + e.toString());
            resultado = null;
        } finally {
            instanciaConexion.Desconecta(connection);
        }
        return resultado;
    }

    static synchronized public Vector buscarFacturaFiltroFecha(String Folio, String proveedor, String fecha_1, String fecha_2) {
        String Resultado[] = null;
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        int countResult = 0;
        Vector<String[]> resultadoFinal = new Vector<String[]>();;
        String Query = "";

//        System.out.println("Accediendo a BD: buscarFacturaFiltroFecha()");

        try {

            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            if (fecha_2.equals("")) {
                String fecha = (String) (DateFormat.getDateInstance().format(new Date()));
                String afecha[] = fecha.split("/");
                fecha_2 = afecha[2] + "-" + afecha[1] + "-" + afecha[0] + " 00:00:00";
            }
            fecha_1 += " 00:00:00";

            Query = " select fac.idFactura,fac.folio,fac.fechaAdquisicion,pro.razonSocial,fac.montoSinIVA,fac.iva,fac.montoConIVA";
            Query += " from `factura` as fac,`proveedor` as pro";
            Query += " where (fac.folio LIKE '%" + Folio + "%' ";
            if (proveedor != null && !proveedor.equals("") && !proveedor.equals("Todos")) {
                Query += " AND pro.razonSocial='" + proveedor + "' ";
            }
            Query += " AND fac.fechaAdquisicion BETWEEN '" + fecha_1 + "' AND '" + fecha_2 + "' ";
            Query += " AND fac.Proveedor_idProveedor = pro.idProveedor ) ";
            Query += " ORDER BY fac.fechaAdquisicion ";
            Query += " LIMIT 0 , 100 ";

            ResultSet rstSQLServer = statement.executeQuery(Query);

            while (rstSQLServer.next()) {
                Resultado = new String[7];
                Resultado[0] = rstSQLServer.getString("idFactura");
                Resultado[1] = rstSQLServer.getString("folio");
                Resultado[2] = rstSQLServer.getString("fechaAdquisicion");
                Resultado[3] = rstSQLServer.getString("razonSocial");
                Resultado[4] = rstSQLServer.getString("montoSinIVA");
                Resultado[5] = rstSQLServer.getString("iva");
                Resultado[6] = rstSQLServer.getString("montoConIVA");
                resultadoFinal.add(Resultado);
            }
            rstSQLServer.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("gestionFacturas-buscarFacturaFiltroFecha():" + e.toString());
            resultadoFinal = null;
        } finally {
            instanciaConexion.Desconecta(connection);
        }
        return resultadoFinal;
    }

    static synchronized public Vector getRecursosFactura(String idFactura, String folio) {
        Vector<String> resultado = null;
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        Vector resultadoFinal = new Vector();
        String Query = "";


        try {

            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            Query = " select rec.idRecurso,tipo.tipo,tipo.marca,ubica.oficina,";
            Query += " rec.noSerie,rec.costo,rec.usuarioActual";
            Query += " from `recurso` as rec,`factura` as fac,`tipo` as tipo,`ubicacionrecurso` as ubica";
            Query += " where rec.Factura_idFactura='" + idFactura + "' ";
            Query += " and fac.folio='" + folio + "' ";
            Query += " and tipo.idTipo=rec.tipo_idTipo ";
            Query += " and ubica.idUbicacion = rec.ubicacionRecurso_idUbicacion ";
            Query += " ORDER BY tipo.marca ";

            ResultSet rstSQLServer = statement.executeQuery(Query);

            while (rstSQLServer.next()) {
                resultado = new Vector<String>();
                resultado.add(rstSQLServer.getString("idRecurso"));
                resultado.add(rstSQLServer.getString("tipo"));
                resultado.add(rstSQLServer.getString("marca"));
                resultado.add(rstSQLServer.getString("oficina"));
                resultado.add(rstSQLServer.getString("noSerie"));
                resultado.add(rstSQLServer.getString("costo"));
                resultado.add(rstSQLServer.getString("usuarioActual"));
                resultadoFinal.add(resultado);
            }
            rstSQLServer.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Exception:gestionFacturas-buscarFactura():" + e.toString());
            resultadoFinal = null;
        } finally {
            instanciaConexion.Desconecta(connection);
        }
        return resultadoFinal;
    }

    static synchronized public Map getDetallesFactura(String idFactura, String folio) {
        Map resultado = null;
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        int countResult = 0;
        String Query = "";

        try {

            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            Query = " select folio,fechaAdquisicion,montoSinIVA,iva,montoConIVA,articulos,observacionesFactura";
            Query += " from `factura`";
            Query += " where `idFactura`='" + idFactura + "' ";
            Query += " and `folio`='" + folio + "' ";

            ResultSet rstSQLServer = statement.executeQuery(Query);

            while (rstSQLServer.next()) {
                resultado = new HashMap();
                resultado.put("idFactura", idFactura);
                resultado.put("folio", rstSQLServer.getString("folio"));
                resultado.put("fechaAdquisicion", rstSQLServer.getString("fechaAdquisicion"));
                resultado.put("montoSinIVA", rstSQLServer.getString("montoSinIVA"));
                resultado.put("iva", rstSQLServer.getString("iva"));
                resultado.put("montoConIVA", rstSQLServer.getString("montoConIVA"));
                resultado.put("articulos", rstSQLServer.getString("articulos"));
                resultado.put("observacionesFactura", rstSQLServer.getString("observacionesFactura"));
            }
            rstSQLServer.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Exception:gestionFacturas-buscarFactura():" + e.toString());
            resultado = null;
        } finally {
            instanciaConexion.Desconecta(connection);
        }
        return resultado;
    }

    static synchronized public Factura getDetalleFactura(String idFactura, String folio) {
        Factura factura = null;
        Proveedor proveedor = null;
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";

        try {

            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            Query = " select idFactura,folio,fechaAdquisicion,montoSinIVA,iva,montoConIVA,";
            Query += "articulos,observacionesFactura,Proveedor_idProveedor ";
            Query += " from `factura`";
            Query += " where `idFactura`='" + idFactura + "' ";
            if (!folio.isEmpty()) {
                Query += " and `folio`='" + folio + "' ";
            }

            ResultSet rstSQLServer = statement.executeQuery(Query);

            while (rstSQLServer.next()) {
                factura = new Factura();
                factura.setIdFactura(rstSQLServer.getInt("idFactura"));
                factura.setFolio(rstSQLServer.getString("folio"));
                factura.setFechaAdquisicion(rstSQLServer.getDate("fechaAdquisicion"));
                factura.setMontoSinIVA(rstSQLServer.getFloat("montoSinIVA"));
                factura.setIva(rstSQLServer.getFloat("iva"));
                factura.setMontoConIVA(rstSQLServer.getFloat("montoConIVA"));
                factura.setArticulos(rstSQLServer.getInt("articulos"));
                factura.setObservacionesFactura(rstSQLServer.getString("observacionesFactura"));
                factura.setProveedor_idProveedor(rstSQLServer.getInt("Proveedor_idProveedor"));
            }

            //Se realiza la busqueda del proveedor de la factura y los datos del mismo
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            Query = " select idProveedor,razonSocial,rfc,domicilioFiscal,telefonoContacto,correoContacto ";
            Query += " from `proveedor`";
            Query += " where `idProveedor`='" + factura.getProveedor_idProveedor() + "' ";

            rstSQLServer = statement.executeQuery(Query);

            while (rstSQLServer.next()) {
                proveedor = new Proveedor();
                proveedor.setIdProveedor(rstSQLServer.getInt("idProveedor"));
                proveedor.setRazonSocial(rstSQLServer.getString("razonSocial"));
                proveedor.setRfc(rstSQLServer.getString("rfc"));
                proveedor.setDomicilioFiscal(rstSQLServer.getString("domicilioFiscal"));
                proveedor.setTelefonoContacto(rstSQLServer.getString("telefonoContacto"));
                proveedor.setCorreoContacto(rstSQLServer.getString("correoContacto"));
                factura.setProveedor(proveedor);
            }

            rstSQLServer.close();
            statement.close();
        } catch (Exception e) {
            System.out.println("Exception:gestionFacturas-getDetalleFactura():" + e.toString());
            factura = null;
        } finally {
            instanciaConexion.Desconecta(connection);
        }
        return factura;
    }

    static synchronized public boolean modificaFactura(String idFactura, String folio, String fecha, String montoSin, String iva, String montoTotal, String noArt, String observaciones) {
        boolean resultado = false;
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";

        try {

            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            statement = connection.createStatement();

            resultado = false;
            Query = " UPDATE factura ";
            Query += " set folio='" + folio + "', ";
            Query += " fechaAdquisicion='" + fecha + "', ";
            Query += " montoSinIVA='" + montoSin + "', ";
            Query += " iva='" + iva + "', ";
            Query += " montoConIVA='" + montoTotal + "', ";
            Query += " articulos='" + noArt + "', ";
            Query += " observacionesFactura='" + observaciones + "' ";
            Query += " where idFactura='" + idFactura + "'";
            statement.executeUpdate(Query);
            resultado = true;

            statement.close();
        } catch (Exception e) {
            System.out.println("Exception:gestionFacturas-buscarFactura():" + e.toString());
            resultado = false;
        } finally {
            instanciaConexion.Desconecta(connection);
        }
        return resultado;
    }

    /**
     * Este metodo inserta en BD una nueva Factura
     *
     * @param folio
     * @param fechaAdquisicion
     * @param montoSinIVA
     * @param iva
     * @param montoConIVA
     * @param articulos
     * @param observaciones
     * @param idProveedor
     * @return
     */
    static synchronized public boolean insertaNuevaFactura(String folio, String fechaAdquisicion, String montoSinIVA, String iva, String montoConIVA, String articulos, String observaciones, String idProveedor) {
        boolean resultado = false;
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";

        try {

            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            statement = connection.createStatement();

            resultado = false;
            Query = " INSERT INTO factura ";
            Query += " (`idFactura`, `folio`, `fechaAdquisicion`, `montoSinIVA`,  ";
            Query += " `iva`, `montoConIVA`, `observacionesFactura`, `articulos`, `Proveedor_idProveedor`) ";
            Query += " VALUES (NULL, '" + folio + "', ";
            if (!fechaAdquisicion.equals("")) {
                Query += " '" + fechaAdquisicion + "', ";
            } else {
                Query += " NULL, ";
            }
            Query += " '" + montoSinIVA + "', ";
            Query += " '" + iva + "', ";
            Query += " '" + montoConIVA + "', ";
            Query += " '" + observaciones + "', ";
            Query += " '" + articulos + "', ";
            Query += " '" + idProveedor + "') ";
            statement.executeUpdate(Query);
            resultado = true;

            statement.close();
        } catch (Exception e) {
            System.out.println("Exception:gestionFacturas-insertaNuevaFactura():" + e.toString());
            resultado = false;
        } finally {
            instanciaConexion.Desconecta(connection);
        }
        return resultado;
    }

    static synchronized public boolean modificaFacturaProveedor(String idFactura, int proveedor_idProveedor) {
        boolean resultado = false;
        clsConexion instanciaConexion = new clsConexion();
        Connection connection = null;
        Statement statement = null;
        String Query = "";

        try {

            connection = instanciaConexion.ConectaMySQL();
            statement = connection.createStatement();
            statement.execute("SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED");

            statement = connection.createStatement();
            resultado = false;
            Query = " UPDATE factura ";
            Query += " set Proveedor_idProveedor=" + proveedor_idProveedor;
            Query += " where idFactura=" + idFactura;
            statement.executeUpdate(Query);
            resultado = true;

            statement.close();
        } catch (Exception e) {
            System.out.println("Exception:gestionFacturas-modificaFacturaProveedor():" + e.toString());
            resultado = false;
        } finally {
            instanciaConexion.Desconecta(connection);
        }
        return resultado;
    }
}
