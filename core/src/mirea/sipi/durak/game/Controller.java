package mirea.sipi.durak.game;

/**
 * Контроллер, обрабатывающий поступающие команды
 */
public class Controller {
    /**
     * Текущее состояние игры
     */
    private GameState gameState;

    /**
     * Выполняет переданную команду
     * @param command Команда, которую необходимо выполнить
     * @return Игровое состояние после выполнения команды
     */
    public GameState executeTurn(Command command) {
        if (command.Verify(gameState))
            command.Execute(gameState);

        return  gameState;
    }
}
