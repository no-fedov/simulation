package com.simulation.action;

import com.simulation.entity.Entity;
import com.simulation.entity.factory.EntityFactory;
import com.simulation.field.Field;
import com.simulation.field.Position;
import com.simulation.util.FieldUtil;

import java.util.Random;

public class SpawnEntityAction<T extends Entity> implements Action {

    private static final int MAX_COUNT_ENTITY_TYPE_ON_MAP = 3;
    private static final double MAX_PERCENT_FIELD_CAPACITY = 0.4d;

    private final EntityFactory entityFactory;
    private final Random random;
    private final Class<T> entityType;

    public SpawnEntityAction(EntityFactory entityFactory, Random random, Class<T> entityType) {
        this.entityFactory = entityFactory;
        this.random = random;
        this.entityType = entityType;
    }

    @Override
    public void execute(Field field) {
        if (isAvailableSpawn(field)) {
            return;
        }
        if (isAvailableFieldCapacity(field)) {
            Position position = FieldUtil.getEmptyRandomPosition(field, random);
            Entity entity = entityFactory.generate(entityType);
            field.put(position, entity);
        }
    }

    private boolean isAvailableSpawn(Field field) {
        return field.getEntities().stream()
                .filter(e -> e.getClass().equals(entityType))
                .count() > MAX_COUNT_ENTITY_TYPE_ON_MAP;
    }

    private boolean isAvailableFieldCapacity(Field field) {
        return field.countEntities() / (double) (field.getHeight() * field.getWidth()) < MAX_PERCENT_FIELD_CAPACITY;
    }
}
