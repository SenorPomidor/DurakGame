package mirea.sipi.durak.game;

import com.esotericsoftware.kryo.Kryo;

public abstract class Player {
    private View view;
    protected GameState gameState;
    private int playerID;

    public Player(int playerID) {
        this.playerID = playerID;
    }

    public abstract void makeTurn(Command command);

    public void setGameState(GameState gameState) {
        this.gameState = gameState;

        view.draw(gameState);
    }

    protected void registerClasses(Kryo kryo) {
        kryo.register(Card.class);
        kryo.register(GameState.class);
        kryo.register(Command.class);
    }
}
