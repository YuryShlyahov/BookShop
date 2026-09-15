import console.ConsoleUI;
import service.Library;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ConsoleUI consoleUI = new ConsoleUI(new Library(), new Scanner(System.in));
        consoleUI.start();
    }
}
