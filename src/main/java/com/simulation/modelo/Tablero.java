package com.simulation.modelo;

import java.util.Arrays;

public class Tablero {
    private final int MAX_AGENTES = 4;
    private int contadorAgentes = 0;

    public synchronized boolean puedeAgregarAgente() {
        return contadorAgentes < MAX_AGENTES;
    }

    public synchronized void incrementarContadorAgentes() {
        contadorAgentes++;
    }
    private final int size = 10;
    private final Entidad[][] grid = new Entidad[size][size];

    public Tablero() {
        for (int i = 0; i < size; i++)
            Arrays.fill(grid[i], Entidad.VACIO);
    }

    public synchronized boolean estaLibre(int row, int col) {
        return inBounds(row, col) && grid[row][col] == Entidad.VACIO;
    }

    public synchronized void setEntidad(int row, int col, Entidad entity) {
        if (inBounds(row, col)) grid[row][col] = entity;
    }

    public synchronized Entidad getEntidad(int row, int col) {
        return inBounds(row, col) ? grid[row][col] : null;
    }

    public boolean inBounds(int row, int col) {
        return row >= 0 && row < size && col >= 0 && col < size;
    }

    public synchronized void printTablero() {
        String horizontalBorder = "+" + "-".repeat(size * 3 - 1) + "+";

        System.out.println(horizontalBorder);
        for (int i = 0; i < size; i++) {
            System.out.print("|");
            for (int j = 0; j < size; j++) {
                System.out.printf(" %s ", grid[i][j].getSymbol());
            }
            System.out.println("|");
        }
        System.out.println(horizontalBorder);
        System.out.println();
        System.out.println();
    }
}
