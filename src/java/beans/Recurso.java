/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.Date;

/**
 *
 * @author Luis-Valerio
 */
public class Recurso {

    private Integer idRecurso;
    private String noSerie;
    private Float costo;
    private Integer tiempoVida;
    private Date fechaRenovacion;
    private String observacionesRec;
    private Integer usuarioActual;
    private Integer usuarioAnterior;
    private Integer tipo_idTipo;
    private Integer factura_idFactura;
    private Integer ubicacionRecurso_idUbicacion;
    private Integer estadoRecurso_idEstadoRecurso;

    public Recurso() {
        this.idRecurso = 0;
        this.noSerie = "";
        this.costo = 0f;
        this.tiempoVida = 0;
        this.fechaRenovacion = new Date();
        this.observacionesRec = "";
        this.usuarioActual = 0;
        this.usuarioAnterior = 0;
        this.tipo_idTipo = 0;
        this.factura_idFactura = 0;
        this.ubicacionRecurso_idUbicacion = 0;
        this.estadoRecurso_idEstadoRecurso = 0;
    }        

    public Integer getIdRecurso() {
        return idRecurso;
    }

    public void setIdRecurso(Integer idRecurso) {
        this.idRecurso = idRecurso;
    }

    public String getNoSerie() {
        return noSerie;
    }

    public void setNoSerie(String noSerie) {
        this.noSerie = noSerie;
    }

    public Float getCosto() {
        return costo;
    }

    public void setCosto(Float costo) {
        this.costo = costo;
    }

    public Integer getTiempoVida() {
        return tiempoVida;
    }

    public void setTiempoVida(Integer tiempoVida) {
        this.tiempoVida = tiempoVida;
    }

    public Date getFechaRenovacion() {
        return fechaRenovacion;
    }

    public void setFechaRenovacion(Date fechaRenovacion) {
        this.fechaRenovacion = fechaRenovacion;
    }

    public String getObservacionesRec() {
        return observacionesRec;
    }

    public void setObservacionesRec(String observacionesRec) {
        this.observacionesRec = observacionesRec;
    }

    public Integer getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(Integer usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    public Integer getUsuarioAnterior() {
        return usuarioAnterior;
    }

    public void setUsuarioAnterior(Integer usuarioAnterior) {
        this.usuarioAnterior = usuarioAnterior;
    }
    
    public Integer getTipo_idTipo() {
        return tipo_idTipo;
    }

    public void setTipo_idTipo(Integer tipo_idTipo) {
        this.tipo_idTipo = tipo_idTipo;
    }

    public Integer getFactura_idFactura() {
        return factura_idFactura;
    }

    public void setFactura_idFactura(Integer factura_idFactura) {
        this.factura_idFactura = factura_idFactura;
    }

    public Integer getUbicacionRecurso_idUbicacion() {
        return ubicacionRecurso_idUbicacion;
    }

    public void setUbicacionRecurso_idUbicacion(Integer ubicacionRecurso_idUbicacion) {
        this.ubicacionRecurso_idUbicacion = ubicacionRecurso_idUbicacion;
    }

    public Integer getEstadoRecurso_idEstadoRecurso() {
        return estadoRecurso_idEstadoRecurso;
    }

    public void setEstadoRecurso_idEstadoRecurso(Integer estadoRecurso_idEstadoRecurso) {
        this.estadoRecurso_idEstadoRecurso = estadoRecurso_idEstadoRecurso;
    }
}
