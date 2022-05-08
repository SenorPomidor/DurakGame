package mirea.sipi.durak.game;

import java.util.ArrayList;

/**
 * Игровое состояние
 */
public class GameState {
    /**
     * Класс для описания игрового поля
     */
    private class Table {
        /**
         * "Атакующие" карты
         */
        Card[] attackers;
        /**
         * Карты, которыми отбиваются от атаки
         */
        Card[] defenders;
    }

    /**
     * Игровая колода карт
     */
    private ArrayList<Card> deck;
    /**
     * Бита
     */
    private ArrayList<Card> discard;
    /**
     * Карты в руке каждого из игроков
     */
    private ArrayList<Card>[] hands;
    /**
     * Разыгранные на поле карты
     */
    private Table table;
}
