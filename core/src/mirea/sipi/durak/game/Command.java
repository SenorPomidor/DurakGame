package mirea.sipi.durak.game;

/**
 * Класс команды
 */
public abstract class Command {
    /**
     * ID игрока, отправившего команду
     */
    private int playerID;

    public Command(int playerID) {
        this.playerID = playerID;
    }

    /**
     * Проверяет легальность команды
     * @param gameState Текущее игровое состояние
     * @return Легален ли ход
     */
    public abstract boolean Verify(GameState gameState);

    /**
     * Выполняет команды
     * @param gameState Игровое состояние, на котором будет выполнена команда
     */
    public abstract void Execute(GameState gameState);

    public int getPlayerID() {
        return playerID;
    }
}
