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

    public boolean isConnected = true;

    public PlayerClient(int playerID){
        super(playerID);

        // TODO: Вероятно потом создание клиента стоит перенести куда-то ещё, он ведь понадобится для создания лобби
        client = new Client();
        client.start();
        // TODO: Заменить локалхост на айпи хоста
        new Thread()
        {
            @Override
            public void run() {
                try {
                    client.connect(2000, "localhost", 54555, 54777);
                } catch (IOException e) {
                    isConnected = false;
                    e.printStackTrace();
                }
            }
        }.start();

        client.addListener(new Listener() {
            public void received (Connection connection, Object object) {
                if (object instanceof GameState) {
                    setGameState((GameState)object);
                }
            }
        });

        registerClasses(client.getKryo());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Отправляет команду хосту для дальнейшей передачи контроллеру
     * @param command Команда, которую необходимо выполнить
     */
    @Override
    public void makeTurn(Command command) {
        client.sendUDP(command);
    }
}
