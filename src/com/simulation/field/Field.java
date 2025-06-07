package com.simulation.field;

import com.simulation.entity.Entity;
import com.simulation.util.PositionUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Field {

    private final int rowCount;
    private final int columnCount;
    private final Map<Position, Entity> positionToEntity = new HashMap<>();
    private final Map<Entity, Position> entityToPosition = new HashMap<>();

    public Field(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
    }

    public void put(Position position, Entity entity) {
        if (!PositionUtil.isValid(position, this)) {
            throw new RuntimeException("вышли за рамки поля");
        }
        remove(position);
        remove(entity);
        positionToEntity.put(position, entity);
        entityToPosition.put(entity, position);
    }

    public Optional<Entity> getEntityByPosition(Position position) {
        return Optional.ofNullable(positionToEntity.get(position));
    }

    public Optional<Position> getPositionByEntity(Entity entity) {
        return Optional.ofNullable(entityToPosition.get(entity));
    }

    public void remove(Position position) {
        Entity entity = positionToEntity.remove(position);
        if (entity != null) {
            entityToPosition.remove(entity);
        }
    }

    public void remove(Entity entity) {
        Position position = entityToPosition.remove(entity);
        if (position != null) {
            positionToEntity.remove(position);
        }
    }

    public boolean isEmptyPosition(Position position) {
        return !positionToEntity.containsKey(position);
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColumnCount() {
        return columnCount;
    }

    public Collection<Entity> getEntities() {
        return Collections.unmodifiableCollection(positionToEntity.values());
    }

    public Map<Position, Entity> getEntityField() {
        return Collections.unmodifiableMap(positionToEntity);
    }

    public boolean isEmpty() {
        return (rowCount * columnCount) != entityToPosition.size();
    }

    public boolean containsEntity(Entity entity) {
        return entityToPosition.containsKey(entity);
    }

    public boolean canFill() {
        return positionToEntity.size() / (double) (rowCount * columnCount) < 0.4d;
    }
}
