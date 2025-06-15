package com.simulation.console;

import java.util.List;

public class CleanConsoleProcessBuilder {

    private static final String LINUX_OC = "linux";
    private static final String MAC_OC = "mac";
    private static final List<String> LINUX_COMMAND = List.of("/bin/bash", "-c", "clear");
    private static final List<String> WIN_COMMAND = List.of("cmd", "/c", "cls");

    private CleanConsoleProcessBuilder() {
    }

    public static ProcessBuilder getCommand() {
        String nameOS = System.getProperty("os.name").toLowerCase();
        if (nameOS.contains(LINUX_OC) || nameOS.contains(MAC_OC)) {
            return new ProcessBuilder(LINUX_COMMAND).inheritIO();
        } else {
            return new ProcessBuilder(WIN_COMMAND).inheritIO();
        }
    }
}