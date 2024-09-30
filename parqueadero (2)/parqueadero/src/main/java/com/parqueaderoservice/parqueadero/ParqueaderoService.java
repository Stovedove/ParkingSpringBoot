package com.parqueaderoservice.parqueadero;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ParqueaderoService {

    private List<Moto> motos = new ArrayList<>();

    public List<Moto> getMotos() {
        return motos;
    }

    public boolean ingresarMoto(String placa, boolean traeCasco) {
        if (!validarPlaca(placa)) {
            return false;
        }

        Moto moto = new Moto(placa, traeCasco);
        motos.add(moto);
        return true;
    }

    public boolean modificarPlaca(String placaActual, String nuevaPlaca) {
        for (Moto moto : motos) {
            if (moto.getPlaca().equals(placaActual)) {
                moto.setPlaca(nuevaPlaca);
                return true;
            }
        }
        return false;
    }

    public boolean sacarMoto(String placa) {
        Iterator<Moto> iterator = motos.iterator();
        while (iterator.hasNext()) {
            Moto moto = iterator.next();
            if (moto.getPlaca().equals(placa)) {
                moto.setHoraSalida(LocalDateTime.now());
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    // Método auxiliar para validar si una placa ya está registrada
    private boolean validarPlaca(String placa) {
        for (Moto moto : motos) {
            if (moto.getPlaca().equals(placa)) {
                return false;
            }
        }
        return true;
    }
}
