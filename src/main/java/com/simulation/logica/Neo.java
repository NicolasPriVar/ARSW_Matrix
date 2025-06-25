package com.simulation.logica;

import com.simulation.MatrixApp;
import com.simulation.modelo.Entidad;
import com.simulation.modelo.Tablero;
import com.simulation.modelo.Posicion;

import java.util.ArrayList;
import java.util.List;

public class Neo implements Runnable {
    private final Tablero tablero;
    private Posicion current;
    private final Posicion telefono;

    public Neo(Tablero tablero, Posicion start, Posicion telefono) {
        this.tablero = tablero;
        this.current = start;
        this.telefono = telefono;
    }

    @Override
    public void run() {
        while (!MatrixApp.juegoTerminado) {
            try { Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("Neo interrumpido");
                return;
            }

            if (tablero.getEntidad(current.getRow(), current.getCol()) == Entidad.AGENTE) {
                System.out.println("Neo ha sido atrapado, perdió");
                MatrixApp.juegoTerminado = true;
                break;
            }

            if (current.equals(telefono)) {
                System.out.println("Neo ha llegado al teléfono, ganó");
                MatrixApp.juegoTerminado = true;
                break;
            }

            Posicion siguiente = getSiguienteMovimiento();

            if(siguiente != null) {
                synchronized (tablero) {
                    if(tablero.getEntidad(siguiente.getRow(), siguiente.getCol()) == Entidad.AGENTE) {
                        current = siguiente;
                        tablero.setEntidad(current.getRow(), current.getCol(),  Entidad.NEO);
                        System.out.println("Neo se encontró con un agente, perdió");
                        tablero.printTablero();
                        MatrixApp.juegoTerminado = true;

                        break;
                    }

                    tablero.setEntidad(current.getRow(), current.getCol(),  Entidad.VACIO);
                    current = siguiente;
                    tablero.setEntidad(current.getRow(), current.getCol(),  Entidad.NEO);
                }
            }
            tablero.printTablero();
        }
    }
    public Posicion getCurrentPosicion() {
        return current;
    }

    private Posicion getSiguienteMovimiento() {
        List<Posicion> posiblesMovimientos = new ArrayList<>();
        int[][] direcciones = {
                {-1, 0}, // arriba
                {1, 0},  // abajo
                {0, -1}, // izquierda
                {0, 1}   // derecha
        };
        for (int[] direccion : direcciones) {
            int nuevaFila = current.getRow() + direccion[0];
            int nuevaColumna = current.getCol() + direccion[1];

            if (tablero.estaLibre(nuevaFila, nuevaColumna) || (nuevaFila == telefono.getRow() && nuevaColumna == telefono.getCol())) {
                posiblesMovimientos.add(new Posicion(nuevaFila, nuevaColumna));
            }
        }
        Posicion mejorMovimiento = null;
        int distanciaMinima = Integer.MAX_VALUE;

        for (Posicion posicion : posiblesMovimientos) {
            int distancia = Math.abs(posicion.getRow() - telefono.getRow()) + Math.abs(posicion.getCol() - telefono.getCol());
            if ( distancia < distanciaMinima) {
                distanciaMinima = distancia;
                mejorMovimiento = posicion;
            }
        }

        return mejorMovimiento;
    }
}