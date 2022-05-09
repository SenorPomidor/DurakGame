package mirea.sipi.durak.game.commands;

import mirea.sipi.durak.game.model.Card;
import mirea.sipi.durak.game.controller.Controller;
import mirea.sipi.durak.game.model.GameState;

import java.util.ArrayList;

/**
 * Класс команды
 */
public abstract class Command {
    /**
     * ID игрока, отправившего команду
     */
    protected int playerID;

    public Command(int playerID) {
        this.playerID = playerID;
    }

    /**
     * Проверяет легальность команды
     * @param gameState Текущее игровое состояние
     * @return Легален ли ход
     */
    public abstract boolean verify(GameState gameState);

    /**
     * Выполняет команды
     * @param controller Контроллер, исполняющий команду
     */
    public abstract void execute(Controller controller);

    public int getPlayerID() {
        return playerID;
    }

    /**
     * Проверяет наличие карты у игрока в руке
     * @param cardToCheck Карта, чьё наличие нужно проверить
     * @param gameState Текущее игровое состояние
     * @return Находится ли карта в руке игрока
     */
    protected boolean checkHandForCard(Card cardToCheck, GameState gameState) {
        boolean hasCard = false;
        ArrayList<Card> hand = gameState.hands[playerID];

        for (Card card : hand) {
            if (card.equals(cardToCheck)) {
                hasCard = true;
                break;
            }
        }

        return hasCard;
    }

    /**
     * Проверяет наличие карты среди атакующих
     * @param cardToCheck Карта, чьё наличие нужно проверить
     * @param gameState Текущее игровое состояние
     * @return Находится ли карта среди атакующих
     */
    protected boolean checkAttackersForCard(Card cardToCheck, GameState gameState) {
        boolean hasCard = false;
        Card[] hand = gameState.table.attackers;

        for (Card card : hand) {
            if (card == null)
                break;
            if (card.equals(cardToCheck)) {
                hasCard = true;
                break;
            }
        }

        return hasCard;
    }
}
