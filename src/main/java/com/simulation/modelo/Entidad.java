package com.simulation.modelo;

public enum Entidad {
    VACIO("."), MURO("m"), AGENTE("A"), NEO("N"), TELEFONO("T");

    private final String symbol;
    Entidad(String symbol) { this.symbol = symbol; }
    public String getSymbol() { return symbol; }
}
