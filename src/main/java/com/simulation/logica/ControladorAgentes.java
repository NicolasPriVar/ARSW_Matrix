package com.simulation.logica;

import com.simulation.MatrixApp;
import com.simulation.modelo.Posicion;
import com.simulation.modelo.Tablero;
import java.util.List;
import java.util.function.Supplier;

public class ControladorAgentes implements Runnable {
    private final Tablero tablero;
    private final List<Agente> agentes;
    private final Supplier<Posicion> neoPosicionSupplier;

    public ControladorAgentes(Tablero tablero, List<Agente> agentes, Supplier<Posicion> neoPosicionSupplier) {
        this.tablero = tablero;
        this.agentes = agentes;
        this.neoPosicionSupplier = neoPosicionSupplier;
    }

    @Override
    public void run() {
        while (!MatrixApp.juegoTerminado) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("ControladorAgentes interrumpido");
                MatrixApp.juegoTerminado = true;
                break;
            }

            synchronized (tablero) {
                for (Agente agente : agentes) {
                    if (MatrixApp.juegoTerminado) break;

                    boolean neoCapturado = agente.mover();
                    if (neoCapturado) {
                        System.out.println("¡Un agente atrapó a Neo!");
                        MatrixApp.juegoTerminado = true;
                        tablero.printTablero();
                        break;
                    }
                }

                if (!MatrixApp.juegoTerminado) {
                    tablero.printTablero();
                }
            }
        }
    }
}