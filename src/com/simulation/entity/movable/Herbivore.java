package com.simulation.entity.movable;

import com.simulation.entity.Entity;
import com.simulation.entity.immovable.Grass;
import com.simulation.field.Field;
import com.simulation.field.Position;

public class Herbivore extends Creature {

    public Herbivore(int speed, int health) {
        super(speed, health);
    }

    @Override
    protected final void attack(Position enemyPosition, Field field) {
        Entity enemy = field.getEntityByPosition(enemyPosition).orElseThrow();
        if (enemy.getClass().equals(moveCondition())) {
            field.remove(enemyPosition);
        }
    }

    @Override
    public Class<Grass> moveCondition() {
        return Grass.class;
    }

    @Override
    public String toString() {
        return "H";
    }
}
