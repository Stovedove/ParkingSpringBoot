package com.parqueaderoservice.parqueadero;

import java.time.Duration;
import java.time.LocalDateTime;

public class Moto {
    private String placa;
    private LocalDateTime horaEntrada;
    private LocalDateTime horaSalida;
    private boolean traeCasco;

    // Constructor
    public Moto(String placa, boolean traeCasco) {
        this.placa = placa;
        this.horaEntrada = LocalDateTime.now();
        this.traeCasco = traeCasco;
    }

    // Métodos
    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public LocalDateTime getHoraEntrada() {
        return horaEntrada;
    }

    public LocalDateTime getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(LocalDateTime horaSalida) {
        this.horaSalida = horaSalida;
    }

    public void setHoraSalida() {
        this.horaSalida = LocalDateTime.now();
    }

    public boolean isTraeCasco() {
        return traeCasco;
    }

    public void setTraeCasco(boolean traeCasco) {
        this.traeCasco = traeCasco;
    }

    // Método para calcular el tiempo de permanencia en el parqueadero
    public long calcularTiempoPermanencia() {
        if (horaSalida != null) {
            return Duration.between(horaEntrada, horaSalida).toHours();
        } else {
            return Duration.between(horaEntrada, LocalDateTime.now()).toHours();
        }
    }

    // Método para calcular el valor a cobrar
    public double calcularValorCobro() {
        long horas = calcularTiempoPermanencia();
        double valorFraccion = 2500; // Valor por fracción de tiempo
        return horas * valorFraccion;
    }
}

