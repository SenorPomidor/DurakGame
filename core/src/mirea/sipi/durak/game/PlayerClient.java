package mirea.sipi.durak.game;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

public class PlayerClient extends Player {
    Client client;

    public PlayerClient(int playerID) throws IOException {
        super(playerID);

        // TODO: Вероятно потом создание клиента стоит перенести куда-то ещё, он ведь понадобится для создания лобби
        client = new Client();
        client.start();
        // TODO: Заменить локалхост на айпи хоста
        client.connect(5000, "127.0.0.1", 54555, 54777);

        client.addListener(new Listener() {
            public void received (Connection connection, Object object) {
                if (object instanceof GameState) {
                    gameState = (GameState)object;
                }
            }
        });

        RegisterClasses(client.getKryo());
    }

    @Override
    public void MakeTurn(Command command) {
        client.sendTCP(command);
    }
}
