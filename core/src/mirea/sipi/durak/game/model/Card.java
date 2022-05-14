package mirea.sipi.durak.game.model;

import java.util.Objects;

/**
 * Класс игровой карты
 */
public class Card {
    /**
     * Энмератор масти карты
     */
    public enum Suit {
        DIAMONDS("d"), CLUBS("c"), HEARTS("h"), SPADES("s");

        private final String shortName;

        public String shortName() {
            return shortName;
        }

        Suit(String s) {
            shortName = s;
        }
    }

    /**
     * Масть карты
     */
    private Suit suit;
    /**
     * Значение карты от 6 до 14, где 11 - Валет, 12 - Дама, 13 - Король и 14 - Туз.
     */
    private int value;

    public Card() {

    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return value == card.value && suit == card.suit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(suit, value);
    }
}
