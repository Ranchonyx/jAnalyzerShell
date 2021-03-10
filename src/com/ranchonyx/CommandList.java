package com.ranchonyx;

import java.util.HashMap;

public class CommandList {
    private HashMap<String, Command> commandHashMap = new HashMap<String, Command>();

    public CommandList() {
        commandHashMap.put("hello", new Command() {

            @Override
            public void run(String[] args) {
                if(args.equals(null)) {
                    System.out.println("No arguments supplied.");
                } else {
                    System.out.println("Hello there");
                }
            }
        });
    }

    public HashMap<String, Command> getCommandHashMap() {
        return commandHashMap;
    }
}
