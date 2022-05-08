package mirea.sipi.durak.game;

import com.esotericsoftware.kryo.Kryo;

/**
 * Класс для описания игрока партии
 */
public abstract class Player {
    /**
     * Отображение партии
     */
    private View view;
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
    }

    /**
     * Отправляет команду контроллеру для её выполнения
     * @param command Команда, которую необходимо выполнить
     */
    public abstract void makeTurn(Command command);

    public void setGameState(GameState gameState) {
        this.gameState = gameState;

        view.draw(gameState);
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
