package mirea.sipi.durak.game.network;

import com.esotericsoftware.kryo.Kryo;
import mirea.sipi.durak.game.commands.Command;
import mirea.sipi.durak.game.model.Card;
import mirea.sipi.durak.game.model.GameState;
import mirea.sipi.durak.game.view.View;

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

        view = new View(playerID);
    }

    /**
     * Отправляет команду контроллеру для её выполнения
     * @param command Команда, которую необходимо выполнить
     */
    public abstract void makeTurn(Command command);

    public void setGameState(GameState gameState) {
        this.gameState = gameState;

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
    }
}
