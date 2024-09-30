package qlt.parking.parqueaderospringboot.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Moto {
    private String plateNumber; //Número de placa a registrar
    private LocalTime entryTime; //Hora de entrada del vehiculo
    private boolean casco; // Indica si dejó casco
}