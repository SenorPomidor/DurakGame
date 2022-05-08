package mirea.sipi.durak.game;

/**
 * Команда перевода карты
 */
public class RotateCommand extends Command{
    private Card rotationCard;

    public RotateCommand(int playerID, Card rotationCard) {
        super(playerID);
        this.rotationCard = rotationCard;
    }

    /**
     * Проверяет наличие разыгранной карты в руке игрока
     * @param gameState Текущее игровое состояние
     * @return Легален ли ход
     */
    @Override
    public boolean verify(GameState gameState) {
        return checkHandForCard(rotationCard, gameState);
    }

    /**
     * При успешном переводе добавляет карту на поле и передаёт ход следующему игроку.
     * @param controller Контроллер, исполняющий команду
     */
    @Override
    public void execute(Controller controller) {
        if (controller.addAttacker(playerID, rotationCard))
            controller.rotateTurn();
    }
}
