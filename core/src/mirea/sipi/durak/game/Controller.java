package mirea.sipi.durak.game;

public class Controller {
    GameState gameState;

    public GameState ExecuteTurn(Command command) {
        if (command.Verify(gameState))
            command.Execute(gameState);

        return  gameState;
    }
}
