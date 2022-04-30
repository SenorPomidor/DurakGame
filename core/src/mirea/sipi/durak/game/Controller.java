package mirea.sipi.durak.game;

public class Controller {
    GameState gameState;

    public GameState executeTurn(Command command) {
        if (command.Verify(gameState))
            command.Execute(gameState);

        return  gameState;
    }
}
