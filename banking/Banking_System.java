package banking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public interface Banking_System {
    static Scanner scanner = new Scanner(System.in);
    static HashMap<String, Account> accounts = new HashMap<>();

    public void displayMenu();
    public void readCommand();

}
