package banking;

public class Account {
    private String accountNumber;
    private String accountPIN;
    private int balance;
    private Card card;


    public Account (Card card) {
        this.accountNumber = card.getCardNumber();
        this.accountPIN = card.getPIN();
        this.card = card;
    }

    public static Account generateAccount() {
        return new Account(Card.generateCard());
    }

    public Card getCard() {
        return this.card;
    }

    public int getBalance() {
        return this.balance;
    }

}