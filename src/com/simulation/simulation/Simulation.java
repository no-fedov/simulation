package com.simulation.simulation;

import com.simulation.action.Action;
import com.simulation.action.InitEntityAction;
import com.simulation.action.MoveEntityAction;
import com.simulation.action.SpawnEntityAction;
import com.simulation.entity.factory.EntityFactory;
import com.simulation.entity.immovable.Grass;
import com.simulation.entity.movable.Herbivore;
import com.simulation.field.Field;
import com.simulation.field.FieldRender;

import java.util.List;
import java.util.Random;

public class Simulation {

    private int moveCounter;
    private volatile boolean isPause;
    private volatile boolean isQuit;
    private final Field field;
    private final FieldRender fieldRender;
    private final Random random;
    private final EntityFactory entityFactory;
    private List<Action> initActions;
    private List<Action> turnAction;

    public Simulation(Field field,
                      FieldRender fieldRender,
                      EntityFactory entityFactory,
                      Random random) {
        this.field = field;
        this.fieldRender = fieldRender;
        this.random = random;
        this.entityFactory = entityFactory;
        setUpActions();
    }

    public void startSimulation() {
        initActions.forEach(e -> e.action(field));
        while (!isQuit) {
            if (!isPause) {
                nextTurn();
            }
        }
    }

    public void start() {
        this.isPause = false;
    }

    public void pause() {
        this.isPause = true;
    }

    public void quit() {
        this.isQuit = true;
    }

    private void nextTurn() {
        for (Action action : turnAction) {
            action.action(field);
        }
        fieldRender.render();
        System.out.println("количество ходов: " + ++moveCounter);
        System.out.println("""
                Введите:
                S - для продолжения
                P - для паузы
                Q - для выхода
                """);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void setUpActions() {
        initActions = List.of(new InitEntityAction(entityFactory, random));
        turnAction = List.of(new MoveEntityAction(),
                new SpawnEntityAction<>(entityFactory, random, Grass.class),
                new SpawnEntityAction<>(entityFactory, random, Herbivore.class));
    }
}
