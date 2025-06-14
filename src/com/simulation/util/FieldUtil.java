package com.simulation.util;

import com.simulation.entity.Entity;
import com.simulation.field.Field;
import com.simulation.field.Position;

import java.util.Map;
import java.util.Optional;
import java.util.Random;

public final class FieldUtil {

    private FieldUtil() {
    }

    public static boolean isValid(Position position, Field field) {
        return !(position.row() < 0
                || position.row() > field.getHeight() - 1
                || position.column() < 0
                || position.column() > field.getWidth() - 1);
    }

    public static Position getEmptyRandomPosition(Field field, Random random) {
        int rows = field.getHeight();
        int cols = field.getWidth();
        if (field.isFull()) {
            throw new RuntimeException("Field has no vacant positions");
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

    public static Optional<Position> getPosition(Entity entity, Field field) {
        return field.getEntityField().entrySet().stream()
                .filter(e -> e.getValue() == entity)
                .map(Map.Entry::getKey)
                .findFirst();
    }
}