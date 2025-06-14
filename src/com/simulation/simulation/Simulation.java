package com.simulation.simulation;

import com.simulation.action.Action;
import com.simulation.action.InitEntityAction;
import com.simulation.action.MoveEntityAction;
import com.simulation.action.SpawnEntityAction;
import com.simulation.console.ConsoleSimulationManager;
import com.simulation.entity.factory.EntityFactory;
import com.simulation.entity.immovable.Grass;
import com.simulation.entity.movable.Herbivore;
import com.simulation.field.Field;
import com.simulation.field.FieldRender;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Simulation {

    private static final int SECONDS_TO_WAIT_AFTER_RENDER = 1;

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
        initActions.forEach(e -> e.execute(field));
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
            action.execute(field);
        }
        fieldRender.render();
        System.out.println("количество ходов: " + ++moveCounter);
        System.out.printf("""
                        Введите:
                        %s - для продолжения
                        %s - для паузы
                        %s - для выхода
                        %n""",
                ConsoleSimulationManager.START,
                ConsoleSimulationManager.PAUSE,
                ConsoleSimulationManager.QUIT);
        try {
            TimeUnit.SECONDS.sleep(SECONDS_TO_WAIT_AFTER_RENDER);
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
