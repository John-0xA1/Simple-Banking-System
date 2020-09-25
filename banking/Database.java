package banking;

import java.sql.*;

public class Database {

    public static Connection connect(String url) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + url);
            createNewTable(connection);
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }

        return connection;
    }

    public static void createDatabase(String url) {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + url)) {
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createNewTable(Connection connection) {
        String sql = "CREATE TABLE IF NOT EXISTS card (\n"
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,\n "
                + "number TEXT,\n"
                + "pin TEXT,\n"
                + "balance INTEGER DEFAULT 0\n"
                + ");";

        try (Statement statement = connection.createStatement()) {
           statement.execute(sql);
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void insert(Connection connection, String cardNumber, String pin) {
        String sql = "INSERT INTO card(number, pin) VALUES(?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, cardNumber);
            preparedStatement.setString(2, pin);
            preparedStatement.executeUpdate();
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static boolean login (Connection connection, String cardNumber, String pin) {
        String sql = "SELECT pin FROM card WHERE number = ? ";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, cardNumber);

            ResultSet resultSet = preparedStatement.executeQuery();

            String correct_pin = resultSet.getString("pin");
            if (pin.equals(correct_pin)) {
                return true;
            }
            else {
                return false;
            }
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public static void showBalance(Connection connection, String cardNumber) {
        String sql = "SELECT balance FROM card WHERE number = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, cardNumber);

            ResultSet resultSet = preparedStatement.executeQuery();

            String balance = resultSet.getString("balance");
            System.out.println("Balance: " + balance + "\n");
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void addIncome(Connection connection, String cardNumber, int amount) {
        String sql = "UPDATE card SET balance = balance + ? WHERE number = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, amount);
            preparedStatement.setString(2, cardNumber);
            preparedStatement.executeUpdate();
            System.out.println("Income was added!");
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void doTransfer(Connection connection, String sender, String receiver, int amount) {
        String sql1 = "UPDATE card SET balance = balance - ? WHERE number = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql1)) {
            preparedStatement.setInt(1, amount);
            preparedStatement.setString(2, sender);
            preparedStatement.executeUpdate();
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        String sql2 = "UPDATE card SET balance = balance + ? WHERE number = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql2)) {
            preparedStatement.setInt(1, amount);
            preparedStatement.setString(2, receiver);
            preparedStatement.executeUpdate();
            System.out.println("Success!");
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void closeAccount(Connection connection, String number) {
        String sql = "DELETE FROM card WHERE number = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, number);
            preparedStatement.executeUpdate();
            System.out.println("Your account has been closed!");
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static boolean numberExists(Connection connection, String number) {
        String sql = "SELECT * FROM card WHERE number = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, number);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.getString("number") != null) {
                return true;
            }
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public static boolean amountEnough(Connection connection, String number, int amount) {
        String sql = "SELECT balance FROM card WHERE number = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, number);
            ResultSet resultSet = preparedStatement.executeQuery();

            int total_amount = resultSet.getInt("balance");
            if (amount <= total_amount) {
                return true;
            }
        }
        catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    public static void clear(Connection connection) {
        String sql = "UPDATE card SET balance = 0";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
            connection.close();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        ;
    }

}
