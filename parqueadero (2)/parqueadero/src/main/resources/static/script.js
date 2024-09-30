// Array para almacenar las placas registradas
var placasRegistradas = [];

// Valor por hora del parqueadero
var valorPorHora = 2500;

// Horario del pico y placa (formato 24 horas)
var picoYPlacaHorarioInicio = 5; // 5:00 AM
var picoYPlacaHorarioFin = 20; // 8:00 PM

// Días de la semana con restricción de pico y placa
var picoYPlacaDias = {
    1: [5, 8], // Lunes: 5 y 8
    2: [1, 4], // Martes: 1 y 4
    3: [2, 0], // Miércoles: 2 y 0
    4: [3, 6], // Jueves: 3 y 6
    5: [7, 9]  // Viernes: 7 y 9
};

function registrarMoto() {

    if (placasRegistradas.length >= 10) {
        alert("límite máximo de placas registradas (10).");
        return;
    }
    var placa = document.getElementById("placa").value;
    var casco = document.getElementById("casco").value;
    
    // Verificar si la placa ya está registrada
    if (placasRegistradas.includes(placa)) {
        alert("La placa ya está registrada. Por favor, ingrese una placa diferente.");
        return;
    }

    // Verificar si la placa está restringida por pico y placa
    if (esPicoYPlaca(placa)) {
        alert("La placa ingresada está restringida por pico y placa en este momento.");
        return;
    }

    // Agregar la placa al array de placas registradas
    placasRegistradas.push(placa);

    // Por ahora, solo se agrega una fila a la tabla de ejemplo
    var tabla = document.getElementById("tablaMotos");
    var fila = tabla.insertRow();
    fila.innerHTML = "<td>" + placa + "</td><td>" + obtenerHoraActual() + "</td><td>" + casco + "</td><td><button onclick='modificarPlaca(this)'>Modificar</button><button onclick='cobrar(this)'>Cobrar</button><button onclick='eliminar(this)'>Eliminar</button></td>";
}

function mostrarTabla() {
    var tabla = document.getElementById("tabla");
    if (tabla.style.display === "none") {
        tabla.style.display = "block";
    } else {
        tabla.style.display = "none";
    }
}

function modificarPlaca(btn) {
    var fila = btn.parentNode.parentNode;
    var placaActual = fila.cells[0].innerHTML;
    var opcion = prompt("¿Desea modificar la placa (P) o la hora de entrada (H)?");

    // Verificar si la opción es placa o hora de entrada
    if (opcion !== null && opcion.toUpperCase() === "P") {
        modificarPlacaPlaca(fila, placaActual);
    } else if (opcion !== null && opcion.toUpperCase() === "H") {
        modificarHoraEntrada(fila);
    } else {
        alert("Opción inválida.");
    }
}

function modificarPlacaPlaca(fila, placaActual) {
    var nuevaPlaca = prompt("Ingrese la nueva placa:");

    // Verificar si la placa está restringida por pico y placa
    if (esPicoYPlaca(nuevaPlaca)) {
        alert("La placa ingresada está restringida por pico y placa en este momento.");
        return;
    }

    if (nuevaPlaca !== null && nuevaPlaca !== "") {
        fila.cells[0].innerHTML = nuevaPlaca;
        // Reemplazar la placa antigua por la nueva en el array de placas registradas
        var index = placasRegistradas.indexOf(placaActual);
        if (index !== -1) {
            placasRegistradas[index] = nuevaPlaca;
        }
        // Aquí se enviaría la solicitud al servidor para modificar la placa
    }
}

function modificarHoraEntrada(fila) {
    var nuevaHoraEntrada = prompt("Ingrese la nueva hora de entrada (HH:MM:SS):");

    if (nuevaHoraEntrada !== null && nuevaHoraEntrada !== "") {
        fila.cells[1].innerHTML = nuevaHoraEntrada;
        // Aquí se enviaría la solicitud al servidor para modificar la hora de entrada
    }
}

function cobrar(btn) {
    var fila = btn.parentNode.parentNode;
    var placa = fila.cells[0].innerHTML;
    var horaIngreso = fila.cells[1].innerHTML;
    var horaSalida = obtenerHoraActual();

    // Verificar si la placa está restringida por pico y placa
    if (esPicoYPlaca(placa)) {
        alert("La placa ingresada está restringida por pico y placa en este momento.");
        return;
    }

    // Calcular el tiempo transcurrido en horas
    var tiempoTranscurrido = calcularTiempoTranscurrido(horaIngreso, horaSalida);

    // Calcular el valor a cobrar
    var valorCobrado = tiempoTranscurrido * valorPorHora;

    // Aquí se enviaría la solicitud al servidor para cobrar

    // Mostrar mensaje de cobro con el valor
    alert("𝗖𝗼𝗯𝗿𝗼 𝗿𝗲𝗮𝗹𝗶𝘇𝗮𝗱𝗼 𝗮 𝗹𝗮𝘀: [" + horaSalida + "] 𝗣𝗮𝗿𝗮 𝗹𝗮 𝗺𝗼𝘁𝗼: [" + placa  + "] 𝗧𝗢𝗧𝗔𝗟: [" + valorCobrado + "]");
    
    var fila = btn.parentNode.parentNode;
    var placa = fila.cells[0].innerHTML;
    // Eliminar la placa del array de placas registradas
    var index = placasRegistradas.indexOf(placa);
    if (index !== -1) {
        placasRegistradas.splice(index, 1);
    }
    // Eliminar la fila de la tabla
    fila.remove();
}

function eliminar(btn) {
    var fila = btn.parentNode.parentNode;
    var placa = fila.cells[0].innerHTML;
    // Eliminar la placa del array de placas registradas
    var index = placasRegistradas.indexOf(placa);
    if (index !== -1) {
        placasRegistradas.splice(index, 1);
    }
    // Eliminar la fila de la tabla
    fila.remove();
}


function obtenerHoraActual() {
    var fecha = new Date();
    var hora = fecha.getHours();
    var minutos = fecha.getMinutes();
    var segundos = fecha.getSeconds();
    return hora + ":" + minutos + ":" + segundos;
}

function calcularTiempoTranscurrido(horaInicio, horaFin) {
    var inicio = horaInicio.split(":");
    var fin = horaFin.split(":");
    var horasInicio = parseInt(inicio[0]);
    var minutosInicio = parseInt(inicio[1]);
    var horasFin = parseInt(fin[0]);
    var minutosFin = parseInt(fin[1]);

    var tiempoInicio = horasInicio * 60 + minutosInicio;
    var tiempoFin = horasFin * 60 + minutosFin;

    var tiempoTranscurrido = (tiempoFin - tiempoInicio) / 60; // En horas
    return tiempoTranscurrido;
}

function esPicoYPlaca(placa) {
    var fecha = new Date();
    var diaSemana = fecha.getDay(); // 0 = Domingo, 1 = Lunes, ..., 6 = Sábado
    var hora = fecha.getHours();

    // Verificar si es día y hora de pico y placa
    if (diaSemana >= 1 && diaSemana <= 5 && hora >= picoYPlacaHorarioInicio && hora <= picoYPlacaHorarioFin) {
        // Verificar el primer dígito de la placa según el día de la semana
        var primerDigitoPlaca = parseInt(placa.charAt(3));
        var restricciones = picoYPlacaDias[diaSemana];
        if (restricciones.includes(primerDigitoPlaca)) {
            return true;
        }
    }

    return false;
}

// Obtener el formulario
var formulario = document.getElementById("formulario");

// Agregar un evento de escucha para el envío del formulario
formulario.addEventListener("submit", function(event) {
    // Evitar el comportamiento predeterminado de enviar el formulario
    event.preventDefault();
    // Llamar a la función para registrar la moto
    registrarMoto();
    
    // Hacer que la página vuelva a la parte superior
    window.scrollTo(0, 0);
});
