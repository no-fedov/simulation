package com.simulation.action;

import com.simulation.entity.Entity;
import com.simulation.entity.factory.EntityFactory;
import com.simulation.field.Field;
import com.simulation.field.Position;
import com.simulation.util.PositionUtil;

import java.util.Random;

public class SpawnEntityAction<T extends Entity> implements Action {

    private final EntityFactory entityFactory;
    private final Random random;
    private final Class<T> entityType;

    public SpawnEntityAction(EntityFactory entityFactory, Random random, Class<T> entityType) {
        this.entityFactory = entityFactory;
        this.random = random;
        this.entityType = entityType;
    }

    @Override
    public void action(Field field) {
        if (field.getEntities().stream().filter(e-> e.getClass().equals(entityType)).count() > 3) {
            return;
        }
        if (field.canFill()) {
            Position position = PositionUtil.getEmptyRandomPosition(field, random);
            Entity entity = entityFactory.generate(entityType);
            field.put(position, entity);
        }
    }
}
