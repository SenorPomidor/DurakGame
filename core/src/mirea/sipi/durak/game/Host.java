package mirea.sipi.durak.game;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

public class Host extends Player {
    Controller controller;

    Server server;

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

                    MakeTurn(request);
                }
            }
        });

        RegisterClasses(server.getKryo());
    }

    @Override
    public void MakeTurn(Command command) {
        gameState = controller.ExecuteTurn(command);
    }

    @Override
    public void setGameState(GameState gameState) {
        super.setGameState(gameState);

        server.sendToAllTCP(gameState);
    }
}
