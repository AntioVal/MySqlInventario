/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function marcarTodo(forma) {
    var noElementos = forma.elements.length;
    for (var i = 0; i < noElementos; i++) {
        if (forma.elements[i].type == 'checkbox') {
            if (forma.elements[i].checked) {
                forma.elements[i].checked = false;
            }
            else {
                forma.elements[i].checked = true;
            }
        }
    }
    return false;
}
function enviar(forma) {
    var contador = 0;
    var noElementos = forma.elements.length;
    for (var i = 0; i < noElementos; i++) {
        if (forma.elements[i].type == 'checkbox') {
            if (forma.elements[i].checked) {
                contador++;
            }
        }
    }
    if (contador <= 0) {
        alert('Favor de seleccionar las columnas que desea mostrar en su reporte');
    }
    else {
        var noElementos = forma.elements.length;
        var seleccion = "";
        for (var i = 0; i < noElementos; i++) {
            if (forma.elements[i].type == 'checkbox') {
                if (forma.elements[i].checked) {
                    seleccion += forma.elements[i].value + ",";
                }
            }
        }
        forma.seleccion.value = seleccion;
        forma.submit();
    }
    return false;
}

