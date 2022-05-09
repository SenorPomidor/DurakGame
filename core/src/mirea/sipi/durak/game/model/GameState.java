package mirea.sipi.durak.game.model;

import java.util.ArrayList;

/**
 * Игровое состояние
 */
public class GameState {
    /**
     * Класс для описания игрового поля
     */
    public static class Table {
        /**
         * "Атакующие" карты
         */
        public Card[] attackers;
        /**
         * Карты, которыми отбиваются от атаки
         */
        public Card[] defenders;
    }

    /**
     * Игровая колода карт
     */
    public ArrayList<Card> deck;
    /**
     * Бита
     */
    public ArrayList<Card> discard;

    /**
     * Карты в руке каждого из игроков
     */
    public ArrayList<Card>[] hands;
    /**
     * Разыгранные на поле карты
     */
    public Table table;

    /**
     * Количество игроков в партии
     */
    public int playerCount;

    /**
     * Масть, являющаяся козырной
     */
    public Card.Suit trumpSuit;

    /**
     * ID атакующего игрока
     */
    public int attackerPlayerID = 0;
    /**
     * ID обороняющегося игрока
     */
    public int defenderPlayerID = 1;

    /**
     * Статус пасования игроков
     */
    public boolean[] playerPass;
}
