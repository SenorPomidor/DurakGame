package mirea.sipi.durak.game.utils;

import mirea.sipi.durak.game.model.Card;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class DeckUtils {
    private static final int INITIAL_HAND_SIZE = 6;

    private static final int START_CARD_VALUE = 6;
    private static final int FINAL_CARD_VALUE = 14;

    /**
     * Создает начальную колоду из всех 36 карт
     * @return Начальная колода из 36 карт
     */
    public static ArrayList<Card> createFullDeck() {
        ArrayList<Card> deck = new ArrayList<>();
        for (Card.Suit suit : Card.Suit.values()) {
            for (int i = START_CARD_VALUE; i <= FINAL_CARD_VALUE; i++) {
                deck.add(new Card(suit, i));
            }
        }
        shuffleDeck(deck);
        return deck;
    }

    /**
     * Выдает начальные 6 карт каждому пользователю из колоды
     * @param initDeck Начальная колода, состоящая из 36 карт
     * @return Список из 6 карт каждого игрока партии
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<Card>[] createStartHands(ArrayList<Card> initDeck) {
        ArrayList<Card> cardsFirstPlayer = new ArrayList<>();
        ArrayList<Card> cardsSecondPlayer = new ArrayList<>();

        for (int i = 0; i < INITIAL_HAND_SIZE; i++) {
            cardsFirstPlayer.add(initDeck.remove(initDeck.size() - 1));
            cardsSecondPlayer.add(initDeck.remove(initDeck.size() - 1));
        }

        ArrayList<Card>[] hands = (ArrayList<Card>[]) Array.newInstance(ArrayList.class, 2);
        hands[0] = cardsFirstPlayer;
        hands[1] = cardsSecondPlayer;

        return hands;
    }

    /**
     * Дополняет количество карт в руке каждого пользователя до 6, если это возможно
     * @param hands Карты в руке каждого из игроков
     * @param deck Текущая колода карт
     * @param isFirstPlayerTakesFirst Определяет, кто берет карты первым
     */
    public static void takeCards(ArrayList<Card>[] hands, ArrayList<Card> deck, boolean isFirstPlayerTakesFirst) {
        int playerIdTakesFirst = isFirstPlayerTakesFirst ? 0 : 1;
        int playerIdTakesSecond = playerIdTakesFirst == 0 ? 1 : 0;

        while (deck.size() > 0 && hands[playerIdTakesFirst].size() < 6) {
            hands[playerIdTakesFirst].add(deck.remove(deck.size() - 1));
        }

        while (deck.size() > 0 && hands[playerIdTakesSecond].size() < 6) {
            hands[playerIdTakesSecond].add(deck.remove(deck.size() - 1));
        }
    }

    /**
     * Перемешивает начальную колоду карт
     * @param deck Начальная упорядоченная колода карт по значениям
     */
    private static void shuffleDeck(ArrayList<Card> deck) {
        for (int i = 0; i < 5; i++) {
            Collections.shuffle(deck);
        }
    }
}
