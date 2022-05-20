package mirea.sipi.durak.game.commands;

import mirea.sipi.durak.game.controller.Controller;
import mirea.sipi.durak.game.model.GameState;

public class SendChatCommand extends Command {
    private String message;

    public SendChatCommand(String message) {
        this.message = message;
    }

    public SendChatCommand(){}

    @Override
    public boolean verify(GameState gameState) {
        return true;
    }

    @Override
    public void execute(Controller controller) {
        controller.addChatMessage(message);
    }
}
