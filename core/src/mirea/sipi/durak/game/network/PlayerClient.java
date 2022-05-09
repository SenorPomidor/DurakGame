package mirea.sipi.durak.game.network;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import mirea.sipi.durak.game.commands.Command;
import mirea.sipi.durak.game.model.GameState;

import java.io.IOException;

/**
 * Клиент, участвующий в партии
 */
public class PlayerClient extends Player {
    /**
     * Клиент для связи с хостом
     */
    private Client client;

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

        registerClasses(client.getKryo());
    }

    /**
     * Отправляет команду хосту для дальнейшей передачи контроллеру
     * @param command Команда, которую необходимо выполнить
     */
    @Override
    public void makeTurn(Command command) {
        client.sendTCP(command);
    }
}
