package com.ranchonyx;

import java.util.HashMap;

public class CommandList {
    private final HashMap<String, Command> commandHashMap = new HashMap<String, Command>();

    public CommandList() {

        commandHashMap.put("print", args -> {
                System.out.printf(">%s", args[0]);
        });
        commandHashMap.put("quit", args -> {
            Main.running = false;
            System.err.println("Quitting.");
        });
        commandHashMap.put("clear", args -> {
            if (System.getProperty("os.name").contains("Windows")) {
                for(int i = 0; i < 50; i++) {
                    System.out.println("\b");
                    System.out.flush();
                }
            }
            else {
                System.out.print("\033\143");
            }
        });
    }

    public HashMap<String, Command> getCommandHashMap() {
        return commandHashMap;
    }
}
