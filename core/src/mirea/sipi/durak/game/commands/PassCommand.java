package mirea.sipi.durak.game.commands;

import mirea.sipi.durak.game.controller.Controller;
import mirea.sipi.durak.game.model.GameState;

/**
 * Команда паса
 */
public class PassCommand extends Command{
    public PassCommand(int playerID) {
        super(playerID);
    }

    /**
     * Игрок может пасовать в любой ситуации, поэтому проверка легальности всегда возвращает истину
     * @param gameState Текущее игровое состояние
     * @return Легален ли ход
     */
    @Override
    public boolean verify(GameState gameState) {
        return true;
    }

    /**
     * Отмечает, что игрок спасовал
     * @param controller Контроллер, исполняющий команду
     */
    @Override
    public void execute(Controller controller) {
        controller.pass(playerID);
    }
}
