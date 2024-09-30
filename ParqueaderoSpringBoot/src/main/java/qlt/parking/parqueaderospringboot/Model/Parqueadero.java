package qlt.parking.parqueaderospringboot.Model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Parqueadero {
    private final List<Moto> motos; // Lista de motos en el parqueadero

    // Constructor
    public Parqueadero() {
        this.motos = new ArrayList<>();
    }

    // Método para agregar una moto al parqueadero
    public void agregarMoto(Moto moto) {
        if (motos.size() < 10) {
            motos.add(moto);
        } else {
            System.out.println("El parqueadero está lleno");
        }
    }
    // Método para verificar si una placa ya está registrada en el parqueadero
    public boolean placaRegistrada(String placa) {
        for (Moto m : motos) {
            if (m.getPlateNumber().equals(placa)) {
                return true;
            }
        }
        return false;
    }

    // Método para verificar si el parqueadero está lleno
    public boolean parqueaderoLleno() {
        return motos.size() >= 10;
    }

    // Método para validar el pico y placa de una moto
    public boolean validarPicoPlaca(String placa) {
        char digitoPicoPlaca = placa.charAt(3); // Obtiene el cuarto dígito de la placa
        if (digitoPicoPlaca == '1' || digitoPicoPlaca == '4') {
            // Pico y placa en martes
            return false;
        } else {
            // Validación estándar
            return true;
        }
    }

    //método para validar si el formato es correcto
    public boolean isValidPlateFormat(String plate) {
        // Utilizar una expresión regular para verificar el formato de la placa
        return plate.matches("[a-zA-Z]{3}\\d{2}[a-hA-H]{1}");
    }
}