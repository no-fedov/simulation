package com.simulation.util;

import com.simulation.field.Field;
import com.simulation.field.Position;

import java.util.Random;

public class PositionUtil {

    public static boolean isValid(Position position, Field field) {
        return !(position.getRow() < 0
                || position.getRow() > field.getRowCount() - 1
                || position.getColumn() < 0
                || position.getColumn() > field.getColumnCount() - 1);
    }

    public static Position getEmptyRandomPosition(Field field, Random random) {
        int rows = field.getRowCount();
        int cols = field.getColumnCount();
        if (!field.isEmpty()) {
            throw new RuntimeException("Поле заполнено");
        }
        Position emptyPosition = null;
        while (emptyPosition == null) {
            Position position = new Position(random.nextInt(0, rows), random.nextInt(0, cols));
            if (field.isEmptyPosition(position)) {
                emptyPosition = position;
            }
        }
        return emptyPosition;
    }
}