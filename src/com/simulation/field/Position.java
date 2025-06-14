package com.simulation.field;

public record Position(int row, int column) {

    @Override
    public String toString() {
        return "Position{" +
                "row=" + row +
                ", column=" + column +
                '}';
    }
}
