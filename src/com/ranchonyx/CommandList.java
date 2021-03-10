package com.ranchonyx;

import java.util.HashMap;

public class CommandList {
    private final HashMap<String, Command> commandHashMap = new HashMap<String, Command>();

    public CommandList() {

        commandHashMap.put("hello", args -> {
            if(args.equals(null)) {
                System.out.println("No arguments supplied.");
            } else {
                System.out.println("Hello there");
            }
        });
        commandHashMap.put("quit", args -> {
            Main.running = false;
            System.err.println("Quitting.");
        });
    }

    public HashMap<String, Command> getCommandHashMap() {
        return commandHashMap;
    }
}
