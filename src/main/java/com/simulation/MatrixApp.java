package com.simulation;

import com.simulation.logica.*;
import com.simulation.modelo.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;
import java.util.concurrent.*;

@SpringBootApplication
public class MatrixApp {
    public static volatile boolean juegoTerminado = false;
    public static void main(String[] args) {
        SpringApplication.run(MatrixApp.class, args);

        Tablero tablero = new Tablero();
        Random rand = new Random();

        // colocar muros
        for (int i = 0; i < 15; i++) {
            int r = rand.nextInt(10), c = rand.nextInt(10);
            if (tablero.estaLibre(r, c)) tablero.setEntidad(r, c, Entidad.MURO);
        }

        // teléfono al centro
        Posicion telefono = new Posicion(5, 5);
        tablero.setEntidad(5, 5, Entidad.TELEFONO);

        // Neo
        Posicion neoPos = getRandomFreePosicion(tablero);
        tablero.setEntidad(neoPos.getRow(), neoPos.getCol(), Entidad.NEO);
        Neo neo = new Neo(tablero, neoPos, telefono);

        // agentes
        List<Agente> agentes = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Posicion aPos = getRandomFreePosicion(tablero);
            tablero.setEntidad(aPos.getRow(), aPos.getCol(), Entidad.AGENTE);
            agentes.add(new Agente(tablero, aPos, neo::getCurrentPosicion));
        }

        // ejecutar en hilos
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(neo);
        executor.submit(new ControladorAgentes(tablero, agentes, neo::getCurrentPosicion));
        while (!juegoTerminado) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                break;
            }
        }

        // Detener hilos y finalizar programa
        executor.shutdownNow();
        System.out.println("Programa finalizado.");
        System.exit(0);
    }

    private static Posicion getRandomFreePosicion(Tablero tablero) {
        Random rand = new Random();
        int r, c;
        do {
            r = rand.nextInt(10);
            c = rand.nextInt(10);
        } while (!tablero.estaLibre(r, c));
        return new Posicion(r, c);
    }
}