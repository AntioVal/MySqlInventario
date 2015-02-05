/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


function enviar(forma,accion)
{
          forma.accion.value = accion;
          forma.submit();
          return false;
}

function verificaDatosCuenta(forma,accion)
{
    
    var nombreU = forma.inombreU.value.trim();
    var nombreP = forma.inombreP.value.trim();
    var apellidoPP = forma.iapellidoPP.value.trim();
    var apellidoMP = forma.iapellidoMP.value.trim();
    var correo = forma.icorreo.value.trim();
    
        if( nombreU == '' || nombreP =='' || apellidoPP == '' || apellidoMP =='' || correo =='')
        {
            alert('Campos en blanco, favor de verificar');
        }
        else
        {
          if(confirm('¿Esta seguro de reaizar los cambios?')){
          forma.accion.value = accion;
          forma.submit();}
        }
    
    return false;
}


function atras(){
    window.history.back();
}


function cambiaPassword(formulario, accion) {

    var oldPass = formulario.oldPass;
    var newPass1 = formulario.newPass1;
    var newPass2 = formulario.newPass2;


    if (oldPass.value == '') {
        alert('Favor de introducir la contraseña actual');
        return false;
    }
    else {
        if (newPass1.value == '') {
            alert('Favor de introducir la nueva contraseña');
            return false;
        }
        else {
            if (newPass2.value == '') {
                alert('Favor de confirmar contraseña');
                return false;
            }
            else {
                if (newPass1.value == newPass2.value) {
                    formulario.accion.value = accion;
                    formulario.submit();
                } else {
                    alert(' La confirmación de la contraseña no coincide ');
                    return false;
                }
            }
        }
    }
    return false;
}