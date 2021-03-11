package com.ranchonyx;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static boolean running = true;
    public static String promptPrefix = "";


    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("jAnalyzerShell by Felix Janetzki, nothing special.\n Type 'help' to get help.");
        while(running) {

                System.out.printf("%s>>>",promptPrefix);


                String[] fullCmd = input.nextLine().split(" ");
                if(fullCmd.length == 0) {
                    System.err.println("[Error] Invalid Command");
                } else {
                    String[] cmdArgs = Arrays.copyOfRange(fullCmd, 1, fullCmd.length);
                    String baseCmd = fullCmd[0];

                    CommandProcessor.processCommand(baseCmd, cmdArgs);
                }
                System.out.println();

        }
    }
}