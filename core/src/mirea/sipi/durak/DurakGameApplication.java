package mirea.sipi.durak;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import mirea.sipi.durak.game.network.Host;
import mirea.sipi.durak.game.network.PlayerClient;
import mirea.sipi.durak.game.view.*;

import java.io.IOException;

import static mirea.sipi.durak.game.view.MainMenu.isConnectionRefused;

public class DurakGameApplication extends ApplicationAdapter {
    private SpriteBatch batch;
    private View view;
    private Stage stage;
    private WelcomeMenu welcomeMenu;
    private MainMenu mainMenu;
    private Lobby lobby;
    private Settings settings;
    private Host host;
    private PlayerClient client;

    private boolean isInWelcomeMenu = true;
    private boolean isInMainMenu = false;
    private boolean isInSettings = false;

    private boolean isHostGameCreated = false;
    private boolean isClientGameCreated = false;

    @Override
    public void create() {
        this.welcomeMenu = new WelcomeMenu();
    }

    @Override
    public void render() {
        if (isInWelcomeMenu) {
            welcomeMenu.render();
            stage = welcomeMenu.getStage();

            if (welcomeMenu.getUsername() != null) {
                isInWelcomeMenu = false;
                isInMainMenu = true;
                this.mainMenu = new MainMenu(welcomeMenu.getUsername());
            }

            isConnectionRefused = false;
            return;
        }

        if (isInMainMenu) {
            mainMenu.render();
            stage = mainMenu.getStage();

            if (mainMenu.getCommand() != null) {
                String command = mainMenu.getCommand();
                mainMenu.setCommand(null);
                isInMainMenu = false;
                switch (command) {
                    case "Change username":
                        isInWelcomeMenu = true;
                        WelcomeMenu.username = null;
                        welcomeMenu = new WelcomeMenu();
                        return;
                    case "Settings":
                        isInSettings = true;
                        settings = new Settings();
                        return;
                    case "Create game":
                        createHostGame();
                        break;
                    case "Enter game":
                        createClientGame();
                }
                String player = command.equals("Create game") ? "Host" : "Client";
                this.lobby = new Lobby(player);
            }
            return;
        }

        if (isInSettings) {
            settings.render();
            stage = settings.getStage();

            if (settings.getCommand() != null) {
                settings.setCommand(null);
                isInSettings = false;
                isInMainMenu = true;
            }

            isConnectionRefused = false;
            return;
        }

        if (host != null && host.gameState.ready[0] && host.gameState.ready[1]
                || client != null && client.gameState.ready[0] && client.gameState.ready[1]) {
            if (view.getReady()) {
                view.render();
            }

            return;
        }

        if (isHostGameCreated && isClientGameCreated) {
            lobby.renderReadyScreen();
            return;
        }

        if (isHostGameCreated) {
            lobby.renderWaitingScreen();
            if (host.isClientConnected()) {
                isClientGameCreated = true;
            }
        }
    }

    @Override
    public void dispose() {
    }

    private void createHostGame() {
        try {
            host = new Host(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        host.startGame(2);

        view = host.view;
        stage = view.getStage();

        Gdx.input.setInputProcessor(stage);

        isHostGameCreated = true;
    }

    private void createClientGame() {
        client = new PlayerClient(1);

        if (client.isConnected) {
            view = client.view;
            stage = view.getStage();

            Gdx.input.setInputProcessor(stage);

            isClientGameCreated = true;
            isHostGameCreated = true;
            isConnectionRefused = false;
            return;
        }
        isInMainMenu = true;
        isConnectionRefused = true;
    }
}
