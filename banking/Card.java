package banking;
import java.util.Random;

public class Card {
    private static final String IIN = "400000";
    private static final int PIN_LENGTH = 4;
    private static final int NUMBER_LENGTH = 10;
    private String cardNumber;
    private String PIN;
    private static Random generator = new Random();

    protected Card(String number, String PIN) {
        this.cardNumber = number;
        this.PIN = PIN;
    }


    public static Card generateCard() {
        System.out.println("Your card has been created");
        return new Card(IIN + generateCardNumber(), generateCardPIN());
    }

    private static String generateCardNumber() {
        String cardNumber = "";
        for (int i = 0; i < NUMBER_LENGTH - 1; i++) {
            cardNumber += generator.nextInt(10);
        }
        cardNumber += Luhn_Algorithm.validator(IIN + cardNumber);
        if (Banking_System.accounts.containsKey(cardNumber)) {
            return generateCardNumber();
        }
        return cardNumber;
    }

    private static String generateCardPIN() {
        String cardPIN = "";
        for (int i = 0; i < PIN_LENGTH; i++) {
            cardPIN += generator.nextInt(10);
        }
        return cardPIN;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPIN() {
        return PIN;
    }
}
