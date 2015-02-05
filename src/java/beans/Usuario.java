/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

/**
 *
 * @author luis valerio
 */
public class Usuario {
    private String nombreU;
    private String privilegio;
    private String nombreCompleto;

    public Usuario(){     
        this.nombreU = "";
        this.privilegio = "";
        this.nombreCompleto = "";
    }
    public Usuario(String NombreU) {
        this.nombreU = NombreU;
    }

    public String getNombreU() {
        return nombreU;
    }

    public void setNombreU(String NombreU) {
        this.nombreU = NombreU;
    }

    public String getPrivilegio() {
        return privilegio;
    }

    public void setPrivilegio(String privilegio) {
        this.privilegio = privilegio;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }
    
    public String toString(){
        return "NombreU: " + this.getNombreU() + "\nNombreCompleto: " + this.getNombreCompleto() +
                "\nPrivilegio: " + this.getPrivilegio();
    }
    
}
