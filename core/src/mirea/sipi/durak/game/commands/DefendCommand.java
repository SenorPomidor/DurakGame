package mirea.sipi.durak.game.commands;

import mirea.sipi.durak.game.model.Card;
import mirea.sipi.durak.game.controller.Controller;
import mirea.sipi.durak.game.model.GameState;

/**
 * Команда, отправляемая защищающимся игроком, чтобы отбиться от атакующих карт
 */
public class DefendCommand extends Command {
    private Card defender;
    private Card attacker;

    public DefendCommand(int playerID, Card defender, Card attacker) {
        super(playerID);
        this.defender = defender;
        this.attacker = attacker;
    }

    /**
     * Проверяет наличие разыгрываемой карты в руке игрока и атакующей карты на поле
     * @param gameState Текущее игровое состояние
     * @return Легален ли ход
     */
    @Override
    public boolean verify(GameState gameState) {
        return checkHandForCard(defender, gameState) && checkAttackersForCard(attacker, gameState);
    }

    /**
     * Отбивает атакующую карту разыгранной
     * @param controller Контроллер, исполняющий команду
     */
    @Override
    public void execute(Controller controller) {
        GameState gameState = controller.gameState;

        if (gameState.defenderPlayerID != playerID)
            return;

        if (checkDefence(defender, attacker, gameState.trumpSuit))
            controller.addDefender(playerID, defender, attacker);
    }

    /**
     * Проверяет, сильнее может ли разыгранная карта отбиться от атакующей
     * @param defender Разыгранная карта
     * @param attacker Атакующая карта
     * @param trump Козырь
     * @return Может ли разыгранная карта отбиться от атакующей
     */
    private boolean checkDefence(Card defender, Card attacker, Card.Suit trump) {
        if (defender.getSuit() == attacker.getSuit())
            return defender.getValue() > attacker.getValue();
        return defender.getSuit() == trump;
    }
}
