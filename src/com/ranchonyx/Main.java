package com.ranchonyx;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static boolean running = true;
    public static String promptPrefix = "";


    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        while(running) {

                System.out.printf("%s>>>",promptPrefix);


                String[] fullCmd = input.nextLine().split(" ");
                String[] cmdArgs = Arrays.copyOfRange(fullCmd, 1, fullCmd.length);
                String baseCmd = fullCmd[0];

                CommandProcessor.processCommand(baseCmd, cmdArgs);
                System.out.println();

        }
    }
}