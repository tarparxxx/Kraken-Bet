package app;

import database.DatabaseManager;

import java.sql.Connection;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Connection conn = DatabaseManager.getConnection();
        System.out.println("OK");
    }
}
