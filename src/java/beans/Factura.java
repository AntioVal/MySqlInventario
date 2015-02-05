/*
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.Date;

/**
 *
 * @author Luis-Valerio
 */
public class Factura {

    private int idFactura;
    private String folio;
    private Date fechaAdquisicion;
    private float montoSinIVA;
    private float iva;
    private float montoConIVA;
    private String observacionesFactura;
    private int articulos;
    private int proveedor_idProveedor;
    private Proveedor proveedor;

    public Factura() {
        this.idFactura = 0;
        this.folio = "";
        this.fechaAdquisicion = new Date();
        this.montoSinIVA = 0;
        this.iva = 0;
        this.montoConIVA = 0;
        this.observacionesFactura = "";
        this.articulos = 0;
        this.proveedor_idProveedor = 0;
        this.proveedor = new Proveedor();
    }        

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }        

    public int getProveedor_idProveedor() {
        return proveedor_idProveedor;
    }

    public void setProveedor_idProveedor(int proveedor_idProveedor) {
        this.proveedor_idProveedor = proveedor_idProveedor;
    }

    public int getArticulos() {
        return articulos;
    }

    public void setArticulos(int articulos) {
        this.articulos = articulos;
    }

    public String getObservacionesFactura() {
        return observacionesFactura;
    }

    public void setObservacionesFactura(String observacionesFactura) {
        this.observacionesFactura = observacionesFactura;
    }

    public float getMontoConIVA() {
        return montoConIVA;
    }

    public void setMontoConIVA(float montoConIVA) {
        this.montoConIVA = montoConIVA;
    }

    public float getIva() {
        return iva;
    }

    public void setIva(float iva) {
        this.iva = iva;
    }

    public float getMontoSinIVA() {
        return montoSinIVA;
    }

    public void setMontoSinIVA(float montoSinIVA) {
        this.montoSinIVA = montoSinIVA;
    }

    public Date getFechaAdquisicion() {
        return fechaAdquisicion;
    }

    public void setFechaAdquisicion(Date fechaAdquisicion) {
        this.fechaAdquisicion = fechaAdquisicion;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }

    @Override
    public String toString() {
        return "Factura{" + "idFactura=" + idFactura + ", folio=" + folio + ", fechaAdquisicion=" + fechaAdquisicion + ", montoSinIVA=" + montoSinIVA + ", iva=" + iva + ", montoConIVA=" + montoConIVA + ", observacionesFactura=" + observacionesFactura + ", articulos=" + articulos + ", proveedor_idProveedor=" + proveedor_idProveedor + '}';
    }
        
}
