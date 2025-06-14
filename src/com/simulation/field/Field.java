package com.simulation.field;

import com.simulation.entity.Entity;
import com.simulation.util.FieldUtil;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Field {

    private final int height;
    private final int width;
    private final Map<Position, Entity> entities = new HashMap<>();

    public Field(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public void put(Position position, Entity entity) {
        if (!FieldUtil.isValid(position, this)) {
            throw new RuntimeException("%s goes beyond the field: height = %s width = %s"
                    .formatted(position, height, width));
        }
        remove(position);
        entities.put(position, entity);
    }

    public Optional<Entity> getEntity(Position position) {
        return Optional.ofNullable(entities.get(position));
    }

    public void remove(Position position) {
        Entity entity = entities.remove(position);
    }

    public boolean isEmptyPosition(Position position) {
        return !entities.containsKey(position);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Collection<Entity> getEntities() {
        return Collections.unmodifiableCollection(entities.values());
    }

    public Map<Position, Entity> getEntityField() {
        return Collections.unmodifiableMap(entities);
    }

    public boolean isFull() {
        return (height * width) == entities.size();
    }

    public int countEntities() {
        return entities.size();
    }
}
