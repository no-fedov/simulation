package com.simulation.entity.factory;

import com.simulation.entity.Entity;
import com.simulation.entity.immovable.Grass;
import com.simulation.entity.immovable.Rock;
import com.simulation.entity.immovable.Tree;
import com.simulation.entity.movable.Herbivore;
import com.simulation.entity.movable.Predator;

import java.util.Random;

public class EntityFactory {

    private static final int MAX_HEALTH = 100;
    private static final int MAX_SPEED = 10;
    private static final int MAX_ATTACK = 15;

    private final Random random;

    public EntityFactory(Random random) {
        this.random = random;
    }

    public Entity generate(Class<?> classZ) {
        return switch (classZ.getSimpleName()) {
            case "Grass" -> new Grass();
            case "Rock" -> new Rock();
            case "Tree" -> new Tree();
            case "Herbivore" -> new Herbivore(random.nextInt(1, MAX_SPEED), random.nextInt(1, MAX_HEALTH));
            case "Predator" -> new Predator(random.nextInt(1, MAX_SPEED),
                    random.nextInt(1, MAX_HEALTH), random.nextInt(1, MAX_ATTACK));
            default -> throw new IllegalStateException("Unexpected value: " + classZ.getSimpleName());
        };
    }
}
