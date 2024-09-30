package qlt.parking.parqueaderospringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ParqueaderoSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParqueaderoSpringBootApplication.class, args);
	}
	/*Parqueadero de motos
	Restricciones:
	-Pico y placa motos Jueves Medellín(ABC 11 A)
	-Placas sin repetir
	-Cantidad maxima <=10
	-Casco si - no en casilla de selección para chulear
	-Hora de entrada modificable
	-Hora de salida no modificable
	-Placa modificable
	-Hora fracción $2.500 CO
	Al cobrar se debe eliminar el registro de la lista y mostrar mensaje del valor del cobro*/
}
