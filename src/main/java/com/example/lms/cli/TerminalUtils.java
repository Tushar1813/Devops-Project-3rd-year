package com.example.lms.cli;

public class TerminalUtils {
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String CYAN = "\u001B[36m";
    public static final String BOLD = "\u001B[1m";

    public static void printColored(String color, String message) {
        System.out.println(color + message + RESET);
    }

    public static void printHeader(String title) {
        System.out.println("\n" + BLUE + BOLD + "=== " + title + " ===" + RESET);
    }

    public static void printError(String message) {
        System.out.println(RED + "[ERROR] " + message + RESET);
    }

    public static void printSuccess(String message) {
        System.out.println(GREEN + "[SUCCESS] " + message + RESET);
    }
}
