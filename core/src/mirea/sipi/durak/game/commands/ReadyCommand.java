package mirea.sipi.durak.game.commands;

import mirea.sipi.durak.game.controller.Controller;
import mirea.sipi.durak.game.model.GameState;

public class ReadyCommand extends Command {
    private int playerId;

    public ReadyCommand(int playerId) {
        this.playerId = playerId;
    }

    public ReadyCommand(){}

    @Override
    public boolean verify(GameState gameState) {
        return true;
    }

    @Override
    public void execute(Controller controller) {
        controller.setReady(playerId);
    }
}
