package com.simulation.action;

import com.simulation.entity.Entity;
import com.simulation.entity.factory.EntityFactory;
import com.simulation.entity.immovable.Grass;
import com.simulation.entity.immovable.Rock;
import com.simulation.entity.immovable.Tree;
import com.simulation.entity.movable.Herbivore;
import com.simulation.entity.movable.Predator;
import com.simulation.field.Field;
import com.simulation.field.Position;
import com.simulation.util.PositionUtil;

import java.util.Random;

public class InitEntityAction implements Action {

    private final Random random;
    private final EntityFactory entityFactory;

    public InitEntityAction(EntityFactory entityFactory, Random random) {
        this.random = random;
        this.entityFactory = entityFactory;
    }

    @Override
    public void action(Field field) {
        for (int i = 0; i < 10; i++) {
            generateEntityOnMap(field, Tree.class);
        }
        for (int i = 0; i < 5; i++) {
            generateEntityOnMap(field, Grass.class);
        }
        for (int i = 0; i < 5; i++) {
            generateEntityOnMap(field, Rock.class);
        }
        for (int i = 0; i < 3; i++) {
            generateEntityOnMap(field, Herbivore.class);
        }
        for (int i = 0; i < 3; i++) {
            generateEntityOnMap(field, Predator.class);
        }
    }

    private void generateEntityOnMap(Field field, Class<?> classZ) {
        Position position = PositionUtil.getEmptyRandomPosition(field, random);
        Entity entity = entityFactory.generate(classZ);
        field.put(position, entity);
    }
}
