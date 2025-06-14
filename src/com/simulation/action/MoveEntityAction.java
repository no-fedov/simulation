package com.simulation.action;

import com.simulation.entity.Entity;
import com.simulation.entity.movable.Creature;
import com.simulation.field.Field;
import com.simulation.util.FieldUtil;

import java.util.ArrayList;
import java.util.Collection;

public class MoveEntityAction implements Action {

    @Override
    public void execute(Field field) {
        Collection<Entity> entities = new ArrayList<>(field.getEntities());
        for (Entity entity : entities) {
            if (entity instanceof Creature creature
                    && creature.getHealth() > 0
                    && FieldUtil.getPosition(entity, field).isPresent()) {
                creature.makeMove(field);
            }
        }
    }
}
