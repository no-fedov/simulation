package com.simulation;

import com.simulation.console.ConsoleManagementSimulation;
import com.simulation.entity.factory.EntityFactory;
import com.simulation.field.Field;
import com.simulation.field.FieldRender;
import com.simulation.simulation.Simulation;

import java.util.Random;

public class Runner {

    public static void main(String[] args) throws InterruptedException {
        Field field = new Field(20, 100);
        FieldRender fieldRender = new FieldRender(field);
        Random random = new Random();
        Simulation simulation = new Simulation(field, fieldRender, new EntityFactory(random), random);

        Runnable simulationTask = simulation::startSimulation;
        Runnable consoleTask = () -> {
            new ConsoleManagementSimulation(simulation).waitingInputToConsole();
        };

        Thread threadSimulation = new Thread(simulationTask);
        Thread threadForListenUser = new Thread(consoleTask);
        threadForListenUser.setDaemon(true);
        threadForListenUser.start();
        threadSimulation.start();
    }
}
