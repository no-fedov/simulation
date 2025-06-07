package com.simulation.entity.movable;

import com.simulation.field.Field;

public interface Movable {

    void makeMove(Field field);

    Class<?> moveCondition();
}
