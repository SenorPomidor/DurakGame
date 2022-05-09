package mirea.sipi.durak.game.controller;

import mirea.sipi.durak.game.model.Card;
import mirea.sipi.durak.game.model.GameState;
import mirea.sipi.durak.game.commands.Command;

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
        int i = 0;

        Card[] attackers = gameState.table.attackers;

        for (;i < attackers.length; i++)
            if (attackers[i].equals(attacker))
                break;

        gameState.table.defenders[i] = defender;

        removeCardFromHand(playerID, defender);
    }

    /**
     * Передаёт ход следующему игроку
     */
    public void rotateTurn() {
        gameState.attackerPlayerID++;
        gameState.defenderPlayerID++;

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
}
