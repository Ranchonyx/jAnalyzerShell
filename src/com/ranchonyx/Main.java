package com.ranchonyx;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        while(true) {
            System.out.print(">>>");

            String[] fullCmd = input.nextLine().split(" ");
            String[] cmdArgs = Arrays.copyOfRange(fullCmd, 1, fullCmd.length);
            String baseCmd = fullCmd[0];

            CommandProcessor.processCommand(baseCmd, cmdArgs);
            System.out.println();
        }
    }
}
