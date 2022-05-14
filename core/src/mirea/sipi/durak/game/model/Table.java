package mirea.sipi.durak.game.model;

/**
 * Класс для описания игрового поля
 */
public class Table {
    private final int MAX_ATTACKERS = 6;

    /**
     * "Атакующие" карты
     */
    public Card[] attackers = new Card[MAX_ATTACKERS];
    /**
     * Карты, которыми отбиваются от атаки
     */
    public Card[] defenders = new Card[MAX_ATTACKERS];
}