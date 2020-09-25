package banking;
import java.util.Scanner;
import java.sql.*;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connection connection = null;
        if (args.length > 1) {
            System.out.println("here");
            connection = Database.connect(args[1]);
            Database.createNewTable(connection);

        }
        else {
            Database.createDatabase("accounts.db");
            connection =  Database.connect("accounts.db");
        }

        Main_Menu main_menu = new Main_Menu(connection);
        main_menu.displayMenu();



    }
}
