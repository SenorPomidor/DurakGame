package mirea.sipi.durak.game.network;

import com.esotericsoftware.kryo.Kryo;
import mirea.sipi.durak.game.commands.*;
import mirea.sipi.durak.game.model.Card;
import mirea.sipi.durak.game.model.GameState;
import mirea.sipi.durak.game.model.Table;
import mirea.sipi.durak.game.view.View;

import java.util.ArrayList;

/**
 * Класс для описания игрока партии
 */
public abstract class Player {
    /**
     * Отображение партии
     */
    public View view;
    /**
     * Текущее игровое состояние, сохранённое у игрока
     */
    protected GameState gameState;
    /**
     * Номер игрока в партии
     */
    private int playerID;

    public Player(int playerID) {
        this.playerID = playerID;
        CommandSender.initialize(this);

        view = new View(playerID);
    }

    /**
     * Отправляет команду контроллеру для её выполнения
     * @param command Команда, которую необходимо выполнить
     */
    public abstract void makeTurn(Command command);

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
        CommandSender.isDefender = gameState.defenderPlayerID == playerID;

        view.update(gameState);
    }

    /**
     * Регистрирует все используемые в сети классы для корректной работы передачи данных
     * @param kryo
     */
    protected void registerClasses(Kryo kryo) {
        kryo.register(Card.class);
        kryo.register(GameState.class);
        kryo.register(Command.class);
        kryo.register(ArrayList.class);
        kryo.register(Card.Suit.class);
        kryo.register(Table.class);
        kryo.register(ArrayList[].class);
        kryo.register(boolean[].class);
        kryo.register(Card[].class);
        kryo.register(AttackCommand.class);
        kryo.register(DefendCommand.class);
        kryo.register(RotateCommand.class);
        kryo.register(PassCommand.class);
    }

    public int getPlayerID() {
        return playerID;
    }
}
