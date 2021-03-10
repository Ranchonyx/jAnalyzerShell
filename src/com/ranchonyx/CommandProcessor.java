package com.ranchonyx;

import org.jetbrains.annotations.Nullable;

public class CommandProcessor {
    private static CommandList commandList = new CommandList();

    public static void processCommand(String cmd, @Nullable String[] args) {
        if(commandList.getCommandHashMap().containsKey("cmd")) {
            //If map contains command, run it
            commandList.getCommandHashMap().get(cmd).run(args);
        } else {
            System.err.println("[Error] Command not found.");
        }
    }
}
