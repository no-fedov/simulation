package com.simulation.entity.movable;

import com.simulation.entity.Entity;
import com.simulation.field.Field;
import com.simulation.field.Position;

import java.util.List;

import static com.simulation.util.PathSearcherUtil.searchPath;

public abstract class Creature extends Entity implements Movable {

    private static final int NO_TARGET_FOR_MOVE = 1;
    private static final int CREATURE_NEARBY = 2;

    protected final int speed;
    protected int health;

    public Creature(int speed, int health) {
        this.speed = speed;
        this.health = health;
    }

    public final void makeMove(Field field) {
        Position position = field.getPositionByEntity(this).orElseThrow();
        List<Position> path = searchPath(this, position, field);
        switch (path.size()) {
            case NO_TARGET_FOR_MOVE:
                break;
            case CREATURE_NEARBY:
                attack(path.get(path.size() - 1), field);
                break;
            default:
                if (path.size() <= speed) {
                    field.put(path.get(path.size() - 2), this);
                    attack(path.get(path.size() - 1), field);
                } else {
                    field.put(path.get(speed), this);
                }
        }
    }

    protected abstract void attack(Position enemyPosition, Field field);

    public int getSpeed() {
        return speed;
    }

    public int getHealth() {
        return health;
    }
}
