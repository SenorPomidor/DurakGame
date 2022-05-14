package mirea.sipi.durak.game.network;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import mirea.sipi.durak.game.commands.Command;
import mirea.sipi.durak.game.controller.Controller;
import mirea.sipi.durak.game.model.GameState;

import java.io.IOException;

/**
 * Хост партии
 */
public class Host extends Player {
    /**
     * Контроллер, выполняющий команды
     */
    private Controller controller;

    /**
     * Сервер для связи с клиентами
     */
    private Server server;

    public Host(int playerID) throws IOException {
        super(playerID);

        // TODO: Вероятно потом создание сервака стоит перенести куда-то ещё, он ведь понадобится для создания лобби
        server = new Server();
        server.start();
        server.bind(54555, 54777);

        server.addListener(new Listener() {
            public void received (Connection connection, Object object) {
                if (object instanceof Command) {
                    Command request = (Command) object;

                    makeTurn(request);
                }
            }

            @Override
            public void connected(Connection connection) {
                server.sendToAllTCP(gameState);
            }
        });

        registerClasses(server.getKryo());
    }

    public void startGame(int playerCount) {
        setGameState(new GameState(playerCount));
        controller = new Controller(gameState);
    }

    /**
     * Выполняет команду хоста или отправленную одним из клиентов
     * @param command Команда, отправленная игроком
     */
    @Override
    public void makeTurn(Command command) {
        setGameState(controller.executeTurn(command));
    }

    /**
     * Задаёт новое значение игровому состоянию, оповещея при этом всех клиентов о его изменении
     * @param gameState
     */
    @Override
    public void setGameState(GameState gameState) {
        super.setGameState(gameState);

        server.sendToAllTCP(gameState);
    }
}
