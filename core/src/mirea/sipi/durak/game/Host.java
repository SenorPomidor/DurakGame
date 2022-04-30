package mirea.sipi.durak.game;

public class Host extends Player {
    Controller controller;

    @Override
    public void MakeTurn(Command command) {
        gameState = controller.ExecuteTurn(command);

        //TODO: отправить информацию о новом состоянии всем клиентам
    }
}
