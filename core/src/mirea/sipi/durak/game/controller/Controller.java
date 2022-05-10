package mirea.sipi.durak.game.controller;

import mirea.sipi.durak.game.model.Card;
import mirea.sipi.durak.game.model.GameState;
import mirea.sipi.durak.game.commands.Command;

import java.util.ArrayList;

import static mirea.sipi.durak.game.utils.DeckUtils.INITIAL_HAND_SIZE;

/**
 * Контроллер, обрабатывающий поступающие команды
 */
public class Controller {
    /**
     * Текущее состояние игры
     */
    public GameState gameState;

    /**
     * Выполняет переданную команду
     * @param command Команда, которую необходимо выполнить
     * @return Игровое состояние после выполнения команды
     */
    public GameState executeTurn(Command command) {
        if (command.verify(gameState))
            command.execute(this);

        if (gameState.getAllPass())
            endTurn();

        return  gameState;
    }

    /**
     * Добавляет на поле атакующую карту
     * @param playerID ID игрока, разыгравшего карту
     * @param attacker Разыгранная карта
     * @return Успешно ли прошла атака
     */
    public boolean addAttacker(int playerID, Card attacker) {
        if (gameState.table.attackers[0] != null && !checkForAttack(attacker))
            return false;

        Card[] attackers = gameState.table.attackers;

        for (int i = 0; i < attackers.length; i++) {
            if (attackers[i] == null) {
                attackers[i] = attacker;
                removeCardFromHand(playerID, attacker);
                return true;
            }
        }

        return false;
    }

    /**
     * Проверяет легальность подрошенной карты
     * @param attacker Подброшенная карта
     * @return Легальна ли подрошенная карта
     */
    private boolean checkForAttack(Card attacker) {
        Card[] defenders = gameState.table.defenders;

        if (gameState.table.attackers[0].getValue() == attacker.getValue())
            return true;

        for (Card defender : defenders) {
            if (defender == null)
                break;
            if (defender.getValue() == attacker.getValue())
                return true;
        }

        return false;
    }

    /**
     * Отбивается разыгранной картой от атакующей
     * @param playerID ID игрока, разыгравшего карту
     * @param defender Разыгранная карта
     * @param attacker Атакующая карта
     */
    public void addDefender(int playerID, Card defender, Card attacker) {
        Card[] attackers = gameState.table.attackers;

        for (int i = 0; i < attackers.length; i++)
            if (attackers[i].equals(attacker)) {
                gameState.table.defenders[i] = defender;
                break;
            }

        removeCardFromHand(playerID, defender);
    }

    /**
     * Передаёт ход следующему игроку
     */
    public void rotateTurn() {
        do {
            gameState.attackerPlayerID++;
        } while (gameState.winners.contains(gameState.attackerPlayerID));

        do {
            gameState.defenderPlayerID++;
        } while (gameState.winners.contains(gameState.defenderPlayerID));


        gameState.attackerPlayerID %= gameState.playerCount;
        gameState.defenderPlayerID %= gameState.playerCount;
    }

    /**
     * Отмечает, что игрок спасовал
     * @param playerID Спасовавший игрок
     */
    public void pass(int playerID) {
        gameState.playerPass[playerID] = true;
    }

    /**
     * Убирает карту из руки игрока, разыгравшего её
     * @param playerID ID игрока, разыгравшего карту
     * @param cardToRemove Разыгранная карта
     */
    private void removeCardFromHand(int playerID, Card cardToRemove) {
        for (Card card : gameState.hands[playerID]) {
            if (card.equals(cardToRemove)) {
                gameState.hands[playerID].remove(card);
                return;
            }
        }
    }

    /**
     * Проверяет успешно ли игрок отбился от нападения
     * @return Успешно ли игрок отбился от нападения
     */
    private boolean isDefenseSuccessful() {
        Card[] attackers = gameState.table.attackers;
        Card[] defenders = gameState.table.defenders;

        for (int i = 0; i < attackers.length; i++) {
            if (attackers[i] == null)
                return true;
            if (defenders[i] == null)
                return false;
        }

        return true;
    }

    /**
     * Завершает текущий ход
     */
    private void endTurn() {
        boolean successfulDefense = isDefenseSuccessful();

        Card[] attackers = gameState.table.attackers;
        Card[] defenders = gameState.table.defenders;
        ArrayList<Card> cardsOnTable = new ArrayList<>();

        for (int i = 0; i < attackers.length; i++) {
            if (attackers[i] == null)
                break;
            cardsOnTable.add(attackers[i]);
            attackers[i] = null;
        }
        for (int i = 0; i < defenders.length; i++) {
            if (defenders[i] == null)
                break;
            cardsOnTable.add(defenders[i]);
            defenders[i] = null;
        }

        if (!successfulDefense) {
            int defenderPlayerID = gameState.defenderPlayerID;

            gameState.hands[defenderPlayerID].addAll(cardsOnTable);
        }
        else {
            gameState.discard.addAll(cardsOnTable);
        }

        int[] drawOrder = getDrawOrder();

        for (int drawID : drawOrder) {
            drawUntilFull(drawOrder[drawID]);
        }

        for (int i = 0; i < gameState.playerCount; i++)
            gameState.playerPass[i] = false;

        checkForWins();

        if (gameState.winners.size() >= gameState.playerCount - 1)
            endGame();
        else {
            rotateTurn();
            if (!successfulDefense)
                rotateTurn();
        }
    }

    /**
     * Получает порядок, в котором игроки должны вытягивать карты из колоды
     * @return Порядок вытягивания карт из колоды
     */
    private int[] getDrawOrder() {
        int[] initialOrder = new int[gameState.playerCount];

        for (int i = 0; i < initialOrder.length; i++)
            initialOrder[i] = (initialOrder.length - 1) - i;

        int[] finalOrder = new int[gameState.playerCount];

        for (int i = 0; i < initialOrder.length; i++)
            finalOrder[(i + gameState.defenderPlayerID) % finalOrder.length] = initialOrder[i];

        return finalOrder;
    }

    /**
     * Вытягивает карты пока рука игрока не заполнится
     * @param playerID Игрок, вытягивающий карты
     */
    private void drawUntilFull(int playerID) {
        ArrayList<Card> hand = gameState.hands[playerID];
        ArrayList<Card> deck = gameState.deck;

        while (hand.size() < INITIAL_HAND_SIZE && deck.size() > 0)
            hand.add(deck.remove(deck.size() - 1));
    }

    /**
     * Проверяет и записывает игроков, победивших в партии
     */
    private void checkForWins() {
        if (gameState.deck.size() > 0)
            return;

        for (int i = 0; i < gameState.playerCount; i++) {
            if (gameState.winners.contains(i))
                continue;

            if (gameState.hands[i].size() == 0)
                gameState.winners.add(i);
        }
    }

    /**
     * Завершает игру
     */
    private void endGame() {
        // TODO: Добавить логику окончания игры
    }
}
