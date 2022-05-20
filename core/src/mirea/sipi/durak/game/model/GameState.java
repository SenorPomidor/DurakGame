package mirea.sipi.durak.game.model;

import com.badlogic.gdx.Game;
import mirea.sipi.durak.game.utils.DeckUtils;

import java.util.ArrayList;

/**
 * Игровое состояние
 */
public class GameState {
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
     * Сообщения, отправленные в чат
     */
    public ArrayList<String> chatHistory;

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

    /**
     * Готовность игроков к игре
     */
    public boolean[] ready;

    public GameState() {

    }

    public GameState(int playerCount) {
        this.playerCount = playerCount;

        deck = DeckUtils.createFullDeck();
        discard = new ArrayList<>();
        hands = DeckUtils.createStartHands(deck);
        table = new Table();
        trumpSuit = deck.get(0).getSuit();
        playerPass = new boolean[playerCount];
        winners = new ArrayList<>();
        chatHistory = new ArrayList<>();
        ready = new boolean[2];
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

    public void resetAllPass() {
        for (int i = 0; i < playerPass.length; i++)
            playerPass[i] = false;
    }
}
