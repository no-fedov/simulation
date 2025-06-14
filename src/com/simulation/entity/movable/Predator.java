package com.simulation.entity.movable;

import com.simulation.entity.Entity;
import com.simulation.field.Field;
import com.simulation.field.Position;

public class Predator extends Creature {

    private final int attackPower;

    public Predator(int speed, int health, int attackPower) {
        super(speed, health);
        this.attackPower = attackPower;
    }

    @Override
    public Class<? extends Entity> moveCondition() {
        return Herbivore.class;
    }

    @Override
    protected final void attack(Position enemyPosition, Field field) {
        Entity enemy = field.getEntity(enemyPosition).orElseThrow();
        if (enemy.getClass().equals(moveCondition())) {
            Creature creature = (Creature) enemy;
            if ((creature.health -= attackPower) <= 0) {
                field.remove(enemyPosition);
            }
        }
    }

    @Override
    public String toString() {
        return "P";
    }
}
