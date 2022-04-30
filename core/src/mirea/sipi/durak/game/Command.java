package mirea.sipi.durak.game;

public abstract class Command {
    int playerID;

    public Command(int playerID) {
        this.playerID = playerID;
    }

    public abstract boolean Verify(GameState gameState);
    public abstract void Execute(GameState gameState);

    public int getPlayerID() {
        return playerID;
    }
}
