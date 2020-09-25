package banking;

import java.sql.Connection;

public class Main_Menu implements Banking_System {
    Connection connection;

    public Main_Menu (Connection conn) {
        this.connection = conn;
    }

    @Override
    public void displayMenu() {
        System.out.println("1. Create an account\n" +
                "2. Log into account\n" +
                "0. Exit");
        readCommand();
    }

    @Override
    public void readCommand() {
        System.out.print(">");
        int option = scanner.nextInt();
        switch (option) {
            case 1:
                System.out.println();
                createAccount();
                break;
            case 2:
                System.out.println();
                login();
                break;
            case 0:
                System.out.println();
                exit();
                break;
            default:
                readCommand();
                break;
        }
    }

    public void login() {
        System.out.print("Enter your card number:" + "\n>");
        String number = scanner.next();
        System.out.print("Enter your PIN:" + "\n>");
        String pin = scanner.next();
        if ( Database.login(this.connection, number, pin)) {
            System.out.println("You have successfully logged in!\n");
            User_Menu user_menu = new User_Menu(number, this.connection);
            user_menu.displayMenu();
        }
        else {
            System.out.println("\nWrong card number or PIN!\n");
            displayMenu();
        }
    }

    public void createAccount() {
        Account account = Account.generateAccount();
        System.out.println("Your card number: " + "\n" + account.getCard().getCardNumber()
                + "\nYour card PIN: " + "\n" + account.getCard().getPIN() + "\n");
        Database.insert(this.connection, account.getCard().getCardNumber(), account.getCard().getPIN());
        displayMenu();
    }

    public void exit() {
        Database.clear(this.connection);
        System.out.println("Bye!");
    }
}
