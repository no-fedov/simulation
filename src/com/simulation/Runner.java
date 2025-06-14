package com.simulation;

import com.simulation.console.ConsoleSimulationManager;
import com.simulation.entity.factory.EntityFactory;
import com.simulation.field.Field;
import com.simulation.field.FieldRender;
import com.simulation.simulation.Simulation;

import java.util.Random;

public class Runner {

    private static final int HEIGHT = 20;
    private static final int WIDTH = 100;

    public static void main(String[] args) throws InterruptedException {
        Field field = new Field(HEIGHT, WIDTH);
        FieldRender fieldRender = new FieldRender(field);
        Random random = new Random();
        Simulation simulation = new Simulation(field, fieldRender, new EntityFactory(random), random);

        Runnable simulationTask = simulation::startSimulation;
        Runnable consoleTask = () -> {
            new ConsoleSimulationManager(simulation).waitingInputToConsole();
        };

        Thread threadSimulation = new Thread(simulationTask);
        Thread threadForListenUser = new Thread(consoleTask);
        threadForListenUser.setDaemon(true);
        threadForListenUser.start();
        threadSimulation.start();
    }
}
