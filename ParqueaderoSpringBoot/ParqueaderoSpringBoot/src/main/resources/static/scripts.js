// En el archivo "scripts.js"

// URL base para las solicitudes al backend
const baseUrl = 'http://localhost:8080';

// Función para enviar los datos del formulario al backend al ingresar una nueva moto
function ingresarMoto() {
    const plate = document.getElementById('plate').value;
    const entryTime = document.getElementById('entry-time').value;
    const casco = document.querySelector('input[name="helmet"]:checked').value === 'yes';

    const moto = { plateNumber: plate, entryTime: entryTime, casco: casco };

    fetch(`${baseUrl}/parqueadero/ingresar`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(moto),
    })
        .then(response => response.text())
        .then(data => {
            alert(data);
            cargarLista(); // Recargar la lista después de agregar una nueva moto
        })
        .catch(error => console.error('Error:', error));
}