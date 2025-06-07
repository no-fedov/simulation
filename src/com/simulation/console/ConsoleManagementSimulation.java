package com.simulation.console;

import com.simulation.simulation.Simulation;

import java.util.Scanner;

public class ConsoleManagementSimulation {

    private static final String START = "S";
    private static final String PAUSE = "P";
    private static final String QUIT = "Q";

    private final Scanner scanner = new Scanner(System.in);
    private final Simulation simulation;

    public ConsoleManagementSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    public void waitingInputToConsole() {
        while (true) {
            String command = scanner.nextLine().toUpperCase();
            switch (command) {
                case START -> {
                    System.out.println("Вы выбрали продолжить симуляцию");
                    start();
                }
                case PAUSE -> {
                    System.out.println("Вы выбрали паузу");
                    pause();
                }
                case QUIT -> {
                    System.out.println("Вы выбрали выйти из симуляции");
                    quit();
                }
                default -> System.out.println("Нет такой команды");
            }
        }
    }

    private void start() {
        simulation.start();
    }

    private void pause() {
        simulation.pause();
    }

    private void quit() {
        simulation.quit();
    }
}
