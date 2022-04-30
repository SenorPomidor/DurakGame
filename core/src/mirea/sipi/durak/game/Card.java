package mirea.sipi.durak.game;

public class Card {
    public enum Suit {
        DIAMONDS, CLUBS, HEARTS, SPADES
    }

    private Suit suit;
    private int value;

    public Card(Suit suit, int value) {
        this.suit = suit;
        this.value = value;
    }

    public Suit getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }
}
