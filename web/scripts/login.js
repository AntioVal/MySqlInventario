/*
 *    Creado por:                   Hernández Paredes Denis Alin
 *    Fecha:                        22/02/2011
 *    Descripción:                  Controlador : "login.js" Scripts necesarios la autentificación del usuario.
 *    Responsable:                  Carlos Altamirano
 */
// Funcion que permite simular la funcion trim() de java

String.prototype.trim = function()
{
    return this.replace(/^\s+|\s+$/g, "");
}

// Función que valida al usuario que intenta Acceder.
function loginUsuario(forma, accion)
{

    var usuario = forma.usuario.value.trim();
    var contrasenna = forma.contrasenna.value.trim();

    if (usuario == '')
    {
        alert('Favor de introducir usuario');
    }
    else
    {
        if (contrasenna == '')
        {
            alert('Favor de introducir Contraseña');
        }
        else
        {
            forma.accion.value = accion;
            forma.submit();
        }
    }

    return false;
}

function enviar(forma, accion) {
    forma.accion.value = accion;
    forma.submit();
    return false;
}

function enviarRec(forma, accion) {
    forma.accionRec.value = accion;
    forma.submit();
    return false;
}

function cambiaAccionSubmit(formulario, accion, actionForm) {
    formulario.accion.value = accion;
    formulario.action = actionForm;
    formulario.submit();
    return false;
}

function buscaUsuario(forma, accion, input) {
    forma.accionRec.value = accion;
    forma.nombre.value = document.getElementById(input).value;
    forma.submit();
    return false;
}

function selectSugerencia(formulario, userSeleccionado, input) {
    document.getElementById("sugerenciaAnt").innerHTML = "";
    document.getElementById("sugerenciaAct").innerHTML = "";
//		var palabraSeleccionada = this.className;
    var cajaTexto = document.getElementById(input);
    cajaTexto.value = userSeleccionado;
    cajaTexto.focus();
    if (formulario.name == 'nuevoRecurso') {
        formulario.accionRec.value = 'setUserSession:11';
        formulario.submit();
    }
    if (formulario.name == 'formModificacionMasivaRecurso') {
        formulario.accionRec.value = 'setUserSessionMasiva:11';
        formulario.submit();
    }
    return false;
}

function selectSugerenciaActual(userSeleccionado, input) {
    document.getElementById("sugerenciaAct").innerHTML = "";
    var cajaTexto = document.getElementById(input);
    cajaTexto.value = userSeleccionado;
    cajaTexto.focus();
    return false;
}


function selectSugerenciaUsuario(formulario, userSeleccionado) {
    document.getElementById("sugerenciaUser").innerHTML = "";
    var cajaTexto = document.getElementById("nuevoUsuario");
    cajaTexto.value = userSeleccionado;
    cajaTexto.focus();
    formulario.accion.value = 'setUserSession:11';
    formulario.submit();
    return false;
}

function modificacionMasiva(forma) {
    var noElementos = forma.elements.length;
    var idRecursos = '';
    var recursos = 0;
    for (var i = 0; i < noElementos ; i++) {
        if (forma.elements[i].type == 'checkbox') {
            if (forma.elements[i].checked) {
                recursos++;
                idRecursos += forma.elements[i].value + ',';
            }
        }
    }
    if (recursos > 0) {
        if (confirm('¿Desea hacer la modificación para los ' + recursos + ' elementos seleccionados?')) {
            if(forma.name == 'agregarRecurso'){
                forma.accion.value = 'setRecursosSession:3';
                forma.action = 'ControlResguardo';
            }
            forma.idRecursos.value = idRecursos;
            forma.submit();
        }
    }
    else {
        alert('Favor de seleccionar los elementos a modificar');
    }
    return false;
}


function enviarBusquedaFac(forma, accion)
{
    var fecha1 = forma.fechaIni;
    var fecha2 = forma.fechaFin;

    if (fecha1.value == '' || fecha2.value == '')
    {
        if (fecha1.value == '' && fecha2.value != '')
        {
            alert('La fecha de inicio no puede estar vacia');
            return false;
        }
    }
    forma.accion.value = accion;
    forma.submit();
}

function enviarBusquedaRec(forma, accion)
{
    //forma.accionRec.value = accion;
    forma.submit();
    return false;
}

function confirmar(forma) {
    var u = new beans.Validador();
    beans.Validador.validaCorreo();
}

function verificaNovacios(forma)
{
    var noElementos = forma.elements.length;
    var enviar = true;
    for (var i = 0; i < noElementos - 1; i++) {
        if (forma.elements[i].type == 'text') {
            var valor = forma.elements[i].value;
            if (valor.trim() == '') {
                forma.elements[i].style.backgroundColor = "#ffff86";
                enviar = false;
            }
        }
    }
    if (enviar == true) {
        forma.submit();
    }
    else {
        alert('Campos requeridos');
    }

    return false;
}

function verificaNovaciosNuevaFactura(forma)
{
    if (forma.elements[1].value == '') {
        forma.folio.style.backgroundColor = "#ffff86";
        alert('El folio de la factura no puede estar vacio.');
        return false;
    }
    if (document.getElementById("idProveedor").value == '') {
        alert('Favor de seleccionar un proveedor para la nueva factura');
        return false;
    }
    forma.submit();

    return false;
}

// Funcion que vuelve a validar al usuario para guardar en la BD
function loginUsuario2(forma, accion)
{

    var usuario = forma.usuario;
    var contrasenna = forma.contrasenna;

    alert('FAVOR DE PULSAR UNA SOLA VEZ ');
    if (usuario.value == '')
    {
        alert('Favor de introducir nombre de usuario');
        return false;
    }
    else
    {
        if (contrasenna.value == '')
        {
            alert('Favor de introducir el password correspondiente');
            return false;
        }
        else
        {
            forma.accion.value = accion;
            forma.submit();
        }
    }
    return false;
}

// Funcion para cambiar el Password del cliente
function cambiaPassword(formulario, accion) {

    formulario.accion.value = accion;
    formulario.submit();
    return false;
}

function habilitar(forma)
{
    var noElementos = forma.elements.length;
    for (var i = 0; i < noElementos - 1; i++) {
        if (forma.elements[i].type == 'text' || forma.elements[i].type == 'textarea' || forma.elements[i].name == 'privilegio' || forma.elements[i].name == 'estado' || forma.elements[i].name == 'razonSocial') {
            forma.elements[i].disabled = !(forma.elements[i].disabled);
        }
    }
    if (!forma.elements[3].disabled) {
        forma.botonHabilita.value = 'Cancelar';
    }
    else {
        forma.botonHabilita.value = 'Modificar';
    }

    return false;
}

function seleccionarOtro(forma)
{
    var noElementos = forma.elements.length;
    for (var i = 0; i < noElementos - 1; i++) {
        if (forma.name == 'datosProveedor') {
            if (forma.elements[i].name == 'seleccionar') {
                forma.elements[i].disabled = !(forma.elements[i].disabled);
            }
        }
        else {
            if (forma.elements[i].type == "select-one")
                forma.elements[i].disabled = !(forma.elements[i].disabled);
        }
    }
    if (!forma.elements[1].disabled) {
        forma.botonHabilita.value = 'Cancelar';
    }
    else {
        forma.botonHabilita.value = 'Cambiar';
//        location.flush();      
    }

    return false;
}


function guardarModificacion(formulario, accion, actionForm)
{
    if (formulario.botonHabilita.value == 'Modificar' || formulario.botonHabilita.value == 'Cambiar') {
        alert('Favor de habilitar los campos para su modificación.');
    } else {
        if (formulario.name != 'datosProveedor') {
            if (confirm('¿Esta seguro de realizar la operación?')) {
                if (actionForm != null && actionForm != '')
                    document.datosProveedor.action = actionForm;
                formulario.accion.value = accion;
                formulario.submit();
            }
        }
        else {
            if (confirm('¿Deseas cambiar el proveedor de esta factura?')) {
                document.datosProveedor.action = 'ControlFactura';
                formulario.accion.value = accion;
                formulario.submit();
            }
        }
    }
    return false;
}

function vincularLicenciaF(forma) {
    if (confirm('¿Deseas vincular la licencia seleccionada al recurso actual?')) {
        forma.submit();
    }
    return false;
}

function desvincularLicenciaF(forma) {
    if (confirm('¿Deseas desvincular la licencia del recurso actual?')) {
        forma.submit();
    }
    return false;
}

function confirmaModificacion(formulario, accion) {
    if (formulario.botonHabilita.value == 'Modificar' || formulario.botonHabilita.value == 'Cambiar') {
        alert('Favor de habilitar los campos para su modificación.');
        return false;
    }
    if (confirm('¿Estas seguro de realizar los cambios?')) {
        formulario.accion.value = accion;
        formulario.submit();
    }
}
function confirmaModificacionRec(formulario, accion) {
    if (formulario.botonHabilita.value == 'Modificar' || formulario.botonHabilita.value == 'Cambiar') {
        alert('Favor de habilitar los campos para su modificación.');
        return false;
    }
    if (confirm('¿Estas seguro de realizar los cambios?')) {
        formulario.accionRec.value = accion;
        formulario.submit();
    }
}

function guardarDependencia(forma, accion, dependencia)
{
    var noElementos = forma.elements.length;
    for (var i = 0; i < noElementos - 1; i++) {
        if (forma.elements[i].name != 'accionRec' && forma.elements[i].name != 'accion' && forma.elements[i].value == '') {
            alert('Favor de llenar todos los campos' + forma.elements[i].name);
            return false;
        }
    }
    forma.accion.value = accion;
    forma.accionRec.value = accion;
    forma.action = dependencia;
    forma.submit();
    return false;
}

function cambiaAction(formulario, accion, actionForm, accionRec) {
    formulario.accion.value = accion;
    formulario.accionRec.value = accionRec;
    formulario.action = actionForm;
    formulario.submit();
    return false;
}

function setAction(formulario, accion) {

    formulario.accion.value = accion;
    formulario.submit();
    return false;
}

function salir() {
    window.history.forward(1);
}

function removeAllSession(urlRedirect) {
    Session.Contents.RemoveAll();
}