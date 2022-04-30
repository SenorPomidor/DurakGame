package mirea.sipi.durak.game;

public abstract class Player {
    View view;
    GameState gameState;

    public abstract void MakeTurn(Command command);

    public void setGameState(GameState gameState) {
        this.gameState = gameState;

        view.Draw(gameState);
    }
}
