package mirea.sipi.durak.game.model;

import mirea.sipi.durak.game.utils.DeckUtils;

import java.util.ArrayList;

/**
 * Игровое состояние
 */
public class GameState {
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

    /**
     * Список победивших игроков
     */
    public ArrayList<Integer> winners;

    public GameState(int playerCount) {
        this.playerCount = playerCount;

        deck = DeckUtils.createFullDeck();
        discard = new ArrayList<>();
        hands = DeckUtils.createStartHands(deck);
        table = new Table();
        trumpSuit = deck.get(0).getSuit();
        playerPass = new boolean[playerCount];
        winners = new ArrayList<>();
    }

    /**
     * Вспомогательный метод, с помощью которого можно узнать, спасовали ли все игроки
     * @return
     */
    public boolean getAllPass() {
        for (boolean pass : playerPass) {
            if (!pass)
                return false;
        }
        return true;
    }
}
