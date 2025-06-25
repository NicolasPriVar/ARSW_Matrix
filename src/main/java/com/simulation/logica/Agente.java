package com.simulation.logica;

import com.simulation.modelo.Entidad;
import com.simulation.modelo.Posicion;
import com.simulation.modelo.Tablero;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class Agente {
    private final Tablero tablero;
    private Posicion current;
    private final Supplier<Posicion> neoPosicionSupplier;

    public Agente(Tablero tablero, Posicion start, Supplier<Posicion> neoPosicionSupplier) {
        this.tablero = tablero;
        this.current = start;
        this.neoPosicionSupplier = neoPosicionSupplier;
    }

    public Posicion getCurrent() {
        return current;
    }

    public boolean mover() {
        Posicion neoPosicion = neoPosicionSupplier.get();
        if (neoPosicion == null) {
            return false;
        }

        Posicion siguiente = getSiguienteMovimiento(neoPosicion);
        if (siguiente == null) {
            return false;
        }

        if (siguiente.equals(neoPosicion)) {
            tablero.setEntidad(current.getRow(), current.getCol(), Entidad.VACIO);
            current = siguiente;
            tablero.setEntidad(current.getRow(), current.getCol(), Entidad.AGENTE);
            return true; // Neo fue capturado
        }

        if (tablero.estaLibre(siguiente.getRow(), siguiente.getCol())) {
            tablero.setEntidad(current.getRow(), current.getCol(), Entidad.VACIO);
            current = siguiente;
            tablero.setEntidad(current.getRow(), current.getCol(), Entidad.AGENTE);
        }

        return false;
    }

    private Posicion getSiguienteMovimiento(Posicion target) {
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

            if (tablero.inBounds(nuevaFila, nuevaColumna)) {
                Entidad e = tablero.getEntidad(nuevaFila, nuevaColumna);
                if (e == Entidad.VACIO || e == Entidad.NEO) {
                    posiblesMovimientos.add(new Posicion(nuevaFila, nuevaColumna));
                }
            }
        }

        Posicion mejorMovimiento = null;
        int minDistance = Integer.MAX_VALUE;
        for (Posicion pos : posiblesMovimientos) {
            int distance = Math.abs(pos.getRow() - target.getRow()) +
                    Math.abs(pos.getCol() - target.getCol());
            if (distance < minDistance) {
                minDistance = distance;
                mejorMovimiento = pos;
            }
        }

        return mejorMovimiento;
    }
}