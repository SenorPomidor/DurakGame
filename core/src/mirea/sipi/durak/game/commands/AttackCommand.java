package mirea.sipi.durak.game.commands;

import mirea.sipi.durak.game.model.Card;
import mirea.sipi.durak.game.controller.Controller;
import mirea.sipi.durak.game.model.GameState;

/**
 * Команда, которая отвечает за разыгрывание и подкидывание карт
 */
public class AttackCommand extends Command {
    /**
     * Разыгранная карта
     */
    private Card attacker;

    public AttackCommand(int playerID, Card attacker) {
        super(playerID);
        this.attacker = attacker;
    }

    /**
     * Подтверждает разыгрывание карты, если она имеется в руке игрока
     * @param gameState Текущее игровое состояние
     * @return Легален ли ход
     */
    @Override
    public boolean verify(GameState gameState) {
        return checkHandForCard(attacker, gameState);
    }

    /**
     * Проверяет способность игрока разыграть карту и отправляет информацию о розыгрыше игровому состоянию
     * @param controller Контроллер, исполняющий команду
     */
    @Override
    public void execute(Controller controller) {
        GameState gameState = controller.gameState;

        int activePlayer = gameState.attackerPlayerID;

        if (playerID != activePlayer) {
            if (!gameState.playerPass[activePlayer] || gameState.playerPass[playerID])
                return;
        }

        controller.addAttacker(playerID, attacker);
    }
}
