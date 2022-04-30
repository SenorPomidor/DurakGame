package mirea.sipi.durak.game;

import com.esotericsoftware.kryo.Kryo;

public abstract class Player {
    View view;
    GameState gameState;
    int playerID;

    public Player(int playerID) {
        this.playerID = playerID;
    }

    public abstract void makeTurn(Command command);

    public void setGameState(GameState gameState) {
        this.gameState = gameState;

        view.draw(gameState);
    }

    void registerClasses(Kryo kryo) {
        kryo.register(Card.class);
        kryo.register(GameState.class);
        kryo.register(Command.class);
    }
}
