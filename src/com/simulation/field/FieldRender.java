package com.simulation.field;

import com.simulation.entity.Entity;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class FieldRender {

    private final Field field;
    private final String[][] representation;
    private final ProcessBuilder cleanConsoleProcess;

    public FieldRender(Field field, ProcessBuilder cleanConsoleProcess) {
        this.field = field;
        representation = new String[field.getHeight()][field.getWidth()];
        this.cleanConsoleProcess = cleanConsoleProcess;
    }

    public void render() {
        clean();
        init();
        for (String[] row : representation) {
            for (String represent : row) {
                System.out.print(Objects.requireNonNullElse(represent, "_"));
            }
            System.out.println();
        }
        System.out.println();
    }

    private void setRepresent(Position position, Entity entity) {
        representation[position.row()][position.column()] = entity.toString();
    }

    private void init() {
        field.getEntityField().forEach(this::setRepresent);
    }

    private void clean() {
        for (String[] row : representation) {
            Arrays.fill(row, null);
        }
        try {
            cleanConsoleProcess.start().waitFor();
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
