package qlt.parking.parqueaderospringboot.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import qlt.parking.parqueaderospringboot.Model.Moto;
import qlt.parking.parqueaderospringboot.Model.Parqueadero;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

@CrossOrigin
@Controller
public class ParqueaderoController {
    private final Parqueadero parqueadero = new Parqueadero();

    @GetMapping("/parqueadero")
    public String mostrarFormulario(Model model) {
        model.addAttribute("moto", new Moto());
        return "index";
    }

    @PostMapping("/parqueadero/ingresar")
    public String ingresarMoto(Moto moto, Model model) {
        // Verificar el formato de la placa
        if (!parqueadero.isValidPlateFormat(moto.getPlateNumber())) {
            model.addAttribute("error", "El formato de la placa es incorrecto. Debe tener el formato [a-zA-Z]{3}\\d{2}[a-hA-H]{1}.");
            return "error";
        }
        if (parqueadero.placaRegistrada(moto.getPlateNumber())) {
            model.addAttribute("error", "La placa ya está registrada en el parqueadero.");
            return "error";
        }

        if (!parqueadero.validarPicoPlaca(moto.getPlateNumber())) {
            model.addAttribute("error", "La moto no puede ingresar debido al pico y placa.");
            return "error";
        }

        if (parqueadero.parqueaderoLleno()) {
            model.addAttribute("error", "El parqueadero está lleno.");
            return "error";
        }

        parqueadero.agregarMoto(moto);
        model.addAttribute("message", "Moto ingresada exitosamente.");
        return "redirect:/lista";
    }

    @GetMapping("/lista")
    public String obtenerListaMotos(Model model) {
        List<Moto> listaMotos = parqueadero.getMotos();
        model.addAttribute("motos", listaMotos);
        return "lista";
    }

    @RequestMapping(value = "/parqueadero/editar/{placa}", method = {RequestMethod.GET, RequestMethod.PUT})
    public String editarMoto(@PathVariable String placa, @ModelAttribute Moto motoEditada, Model model) {
        // Si se envía una moto editada, actualizamos la moto en la lista
        if (motoEditada != null) {
            // Validaciones de la moto editada
            if (!parqueadero.isValidPlateFormat(motoEditada.getPlateNumber())) {
                model.addAttribute("error", "El formato de la placa es incorrecto. Debe tener el formato [a-zA-Z]{3}\\d{2}[a-hA-H]{1}.");
                return "error";
            }
            if (parqueadero.placaRegistrada(motoEditada.getPlateNumber()) && !motoEditada.getPlateNumber().equals(placa)) {
                model.addAttribute("error", "La placa ya está registrada en el parqueadero.");
                return "error";
            }
            if (!parqueadero.validarPicoPlaca(motoEditada.getPlateNumber())) {
                model.addAttribute("error", "La moto no puede ingresar debido al pico y placa.");
                return "error";
            }

            // Actualizar la moto en la lista
            for (Moto moto : parqueadero.getMotos()) {
                if (moto.getPlateNumber().equals(placa)) {
                    moto.setPlateNumber(motoEditada.getPlateNumber());
                    moto.setEntryTime(motoEditada.getEntryTime());
                    break;
                }
            }
            // Redirigir a la página de lista después de la edición
            return "redirect:/lista";
        } else {
            // Si no hay moto editada, solo mostrar el formulario de edición
            for (Moto moto : parqueadero.getMotos()) {
                if (moto.getPlateNumber().equals(placa)) {
                    model.addAttribute("moto", moto);
                    break;
                }
            }
            return "editar"; // Devuelve la vista de edición
        }
    }


    @PostMapping("/parqueadero/eliminar/{placa}")
    public String eliminarMoto(@PathVariable String placa, RedirectAttributes redirectAttributes) {
        boolean motoEncontrada = false;
        Iterator<Moto> iterator = parqueadero.getMotos().iterator(); // Usar un iterador para poder eliminar elementos de la lista mientras se itera
        while (iterator.hasNext()) {
            Moto moto = iterator.next();
            if (moto.getPlateNumber().equals(placa)) {
                iterator.remove(); // Eliminar la moto de la lista
                motoEncontrada = true;
                break;
            }
        }
        // Redirigir de vuelta a la página de lista con un mensaje de confirmación
        redirectAttributes.addFlashAttribute("mensaje", motoEncontrada ? "Moto eliminada exitosamente." : "No se encontró la moto con la placa especificada.");
        return "redirect:/lista"; // Corregir la ruta de redirección
    }

    @GetMapping("/parqueadero/cobrar/{placa}")
    public ResponseEntity<Double> cobrarMoto(@PathVariable String placa) {
        for (Moto m : parqueadero.getMotos()) {
            if (m.getPlateNumber().equals(placa)) {
                LocalDateTime horaActual = LocalDateTime.now();
                Duration duracion = Duration.between(m.getEntryTime(), horaActual);
                long horasEstacionadas = duracion.toHours();
                if (duracion.toMinutes() % 60 > 0) {
                    horasEstacionadas++;
                }
                double costo = horasEstacionadas * 2500;
                return new ResponseEntity<>(costo, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(0.0, HttpStatus.NOT_FOUND);
    }
}