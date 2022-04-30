package mirea.sipi.durak.game;

public class Card {
    enum Suit {
        DIAMONDS, CLUBS, HEARTS, SPADES
    }

    Suit suit;
    int value;

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
