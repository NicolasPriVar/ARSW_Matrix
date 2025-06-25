package com.simulation.modelo;

import java.util.Objects;

public class Posicion {
    private int row, col;

    public Posicion(int row, int col) {
        this.row = row;
        this.col = col;
    }
    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }
    public void setRow(int row) {
        this.row = row;
    }
    public void setCol(int col) {
        this.col = col;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Posicion)) return false;
        Posicion posicion = (Posicion)o;
        return row == posicion.row && col == posicion.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public String toString() {
        return "("  + row + ", " + col + ")";
    }
}
