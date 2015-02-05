/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author luis-valerio
 */
public class Validador {
    
    public static boolean validaCorreo(String correoIn){
        boolean bandera=false;
        //expresion regular para verificacion de correo
        Pattern p = Pattern.compile("([a-zA-Z0-9]+(\\.?[\\w-]+)*)@([a-z0-9]+)(\\.[a-z]{1,5}){1,3}");
        Matcher m = p.matcher(correoIn);
        
        if(m.matches())
                if(correoIn.equals(m.group()))
                          bandera = true;        
        
        return bandera;
    }
    
        public static boolean validaNombres(String NombreIn){
        boolean bandera=false;
        //expresion regular para verificacion del nombre
        Pattern p = Pattern.compile("([a-záéíóúñA-ZÁÉÍÓÚÑ]+( ?[a-záéíóúñA-ZÁÉÍÓÚÑ])*)");
        Matcher m = p.matcher(NombreIn);
        
        if(m.matches())
                if(NombreIn.equals(m.group()))//si son iguales las cadenas, entonces el campo es correcto
                          bandera = true;        
        
        return bandera;
    }
        public static boolean validaNombreU(String NombreIn){
        boolean bandera=false;
        //expresion regular para verificacion del nombre
        Pattern p = Pattern.compile("([a-zA-Z]+(\\-?[a-zA-Z\\d]+)*)");
        Matcher m = p.matcher(NombreIn);
        
        if(m.matches())
                if(NombreIn.equals(m.group()))//si son iguales las cadenas, entonces el campo es correcto
                          bandera = true;        
        
        return bandera;
    }

        public static String validaDecimal(String cadena){
         String numero ="";
            DecimalFormat formato = new DecimalFormat("0.00");
            try{
                numero = formato.format(Double.parseDouble(cadena));
            }
            catch(Exception e){
                System.out.println("Error de formato de numero");
                return "";
            }
        return numero;
    }              
        /**
         * 
         * @param cadena
         * @return 
         */
        public static String validaEntero(String cadena){
         String numero = "";
            try{
                numero = Integer.parseInt(cadena) + "";
            }
            catch(Exception e){
                System.out.println("Error de formato de numero");
                return "";
            }
        return numero;
    }              
        
        public static boolean validaAlfanumerico(String cadena){
        boolean bandera=false;
        //expresion regular para verificacion del nombre
        Pattern p = Pattern.compile("([\\w]+(-?[\\w])*)");
        Matcher m = p.matcher(cadena);
        
        if(m.matches())
                if(cadena.equals(m.group()))//si son iguales las cadenas, entonces el campo es correcto
                          bandera = true;        
        
        return bandera;
    }        
        
        public static String creaPassword(){
            String caracteres = "0123456#789a*bcdefghi%jklmnopqrstuvw*xyzAB%CDEFGHIJ#KLMNOPQRSTUVWXYZ%#!¡*-_";
            int longitudDePassword = 8;
            String password = "";
                for(int i=0;i<longitudDePassword;i++){
                    BigDecimal posicion = BigDecimal.valueOf(Math.random()*caracteres.length());
                    password += caracteres.charAt(posicion.intValue());
                }
            
            
            return password;
        }
        
}
