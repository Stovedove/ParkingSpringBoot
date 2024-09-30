package com.parqueaderoservice.parqueadero;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ParqueaderoController {

    private List<Moto> motos = new ArrayList<>();

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("motos", motos);
        return "index";
    }

    @PostMapping("/ingresar")
    public String ingresar(@RequestParam String placa, @RequestParam boolean traeCasco) {
        if (!validarPlaca(placa)) {
            return "redirect:/?error=placaRepetida";
        }

        Moto moto = new Moto(placa, traeCasco);
        motos.add(moto);

        return "redirect:/";
    }

    @GetMapping("/modificarPlaca/{placa}")
    public String modificarPlaca(@PathVariable String placa, Model model) {
        model.addAttribute("placa", placa);
        return "modificarPlaca";
    }

    @PostMapping("/modificarPlaca")
    public String guardarPlacaModificada(@RequestParam String placaActual, @RequestParam String nuevaPlaca) {
        for (Moto moto : motos) {
            if (moto.getPlaca().equals(placaActual)) {
                moto.setPlaca(nuevaPlaca);
                break;
            }
        }
        return "redirect:/";
    }

    @PostMapping("/salir")
    public String salir(@RequestParam String placa) {
        for (Moto moto : motos) {
            if (moto.getPlaca().equals(placa)) {
                moto.setHoraSalida(LocalDateTime.now());
                double valorCobro = moto.calcularValorCobro();
                motos.remove(moto);
                return "redirect:/?mensaje=Cobro: " + valorCobro;
            }
        }
        return "redirect:/?error=motoNoEncontrada";
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
