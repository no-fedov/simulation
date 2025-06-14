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
import com.simulation.util.FieldUtil;

import java.util.Random;

public class InitEntityAction implements Action {

    private static final int TREE_INIT_COUNT = 10;
    private static final int GRASS_INIT_COUNT = 5;
    private static final int ROCK_INIT_COUNT = 5;
    private static final int HERBIVORE_INIT_COUNT = 3;
    private static final int PREDATOR_INIT_COUNT = 3;

    private final Random random;
    private final EntityFactory entityFactory;

    public InitEntityAction(EntityFactory entityFactory, Random random) {
        this.random = random;
        this.entityFactory = entityFactory;
    }

    @Override
    public void execute(Field field) {
        generateEntityOnMap(field, Tree.class, TREE_INIT_COUNT);
        generateEntityOnMap(field, Grass.class, GRASS_INIT_COUNT);
        generateEntityOnMap(field, Rock.class, ROCK_INIT_COUNT);
        generateEntityOnMap(field, Herbivore.class, HERBIVORE_INIT_COUNT);
        generateEntityOnMap(field, Predator.class, PREDATOR_INIT_COUNT);
    }

    private void generateEntityOnMap(Field field, Class<?> clazz, int entitiesCount) {
        for (int i = 0; i < entitiesCount; i++) {
            Position position = FieldUtil.getEmptyRandomPosition(field, random);
            Entity entity = entityFactory.generate(clazz);
            field.put(position, entity);
        }
    }
}
