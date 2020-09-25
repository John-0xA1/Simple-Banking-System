package banking;

import javax.xml.crypto.Data;
import java.sql.Connection;

public class User_Menu implements Banking_System{
    private String number;
    Connection connection = null;

    public User_Menu (String number, Connection connection) {
        this.number = number;
        this.connection = connection;
    }

    @Override
    public void displayMenu() {
        System.out.println("1. Balance\n" +
                "2. Add income\n" +
                "3. Do transfer\n" +
                "4. Close account\n" +
                "5. Log out\n" +
                "0. Exit\n");
        readCommand();
    }

    @Override
    public void readCommand() {
        System.out.print(">");
        int option = scanner.nextInt();
        switch (option) {
            case 1:
                System.out.println();
                showBalance();
                break;
            case 2:
                System.out.println();
                addIncome();
                break;
            case 3:
                System.out.println();
                doTransfer();
                break;
            case 4:
                System.out.println();
                closeAccount();
                break;
            case 5:
                System.out.println();
                logOut();
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

    public void showBalance() {
        Database.showBalance(connection, this.number);
        displayMenu();
    }

    public void logOut() {
        System.out.println("You have successfully logged out!");
        Main_Menu main_menu = new Main_Menu(connection);
        main_menu.displayMenu();
    }

    public void addIncome() {
        System.out.print("Enter income:\n>");
        int income = scanner.nextInt();
        Database.addIncome(this.connection, this.number, income);
        displayMenu();
    }

    public void doTransfer() {
        System.out.print("Transfer\nEnter card number:\n>");
        String receiver = scanner.next();
        if (!Luhn_Algorithm.verifyNumber(receiver)) {
            System.out.println("Probably you made mistake in the card number.\nPlease try again!\n");
            displayMenu();
        }
        else if (receiver.equals(this.number)) {
            System.out.println("You can't transfer money to the same account!\n");
            displayMenu();
        }
        else if (!Database.numberExists(this.connection, receiver)) {
            System.out.println("Such a card does not exist.");
            displayMenu();
        }

        System.out.print("Enter how much money you want to transfer:\n>");
        int amount = scanner.nextInt();
        if (!Database.amountEnough(this.connection, this.number, amount)) {
            System.out.println("Not enough money!");
            displayMenu();
        }

        Database.doTransfer(this.connection, this.number, receiver, amount);
        displayMenu();

    }

    public void closeAccount() {
        Database.closeAccount(this.connection, this.number);
        Main_Menu main_menu = new Main_Menu(this.connection);
        main_menu.displayMenu();
    }

    public void exit() {
        System.out.println("Bye!");
    }

}
