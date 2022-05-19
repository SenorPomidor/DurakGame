package mirea.sipi.durak;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import mirea.sipi.durak.game.network.Host;
import mirea.sipi.durak.game.network.PlayerClient;
import mirea.sipi.durak.game.view.Lobby;
import mirea.sipi.durak.game.view.MainMenu;
import mirea.sipi.durak.game.view.WelcomeMenu;
import mirea.sipi.durak.game.view.View;

import java.io.IOException;

public class DurakGameApplication extends ApplicationAdapter {
	private SpriteBatch batch;
	private View view;
	private Stage stage;
	private WelcomeMenu welcomeMenu;
	private MainMenu mainMenu;
	private Lobby lobby;
	private Host host;

	private boolean isInWelcomeMenu = true;
	private boolean isInMainMenu = false;

	private boolean isHostGameCreated = false;
	private boolean isClientGameCreated = false;

	@Override
	public void create () {
		this.welcomeMenu = new WelcomeMenu();
	}

	@Override
	public void render () {
		if (isInWelcomeMenu) {
			welcomeMenu.render();
			stage = welcomeMenu.getStage();

			if (welcomeMenu.getUsername() != null){
				isInWelcomeMenu = false;
				isInMainMenu = true;
				this.mainMenu = new MainMenu(welcomeMenu.getUsername());
			}
			return;
		}

		if(isInMainMenu){
			mainMenu.render();
			stage = mainMenu.getStage();

			if(mainMenu.getCommand() != null){
				isInMainMenu = false;
				switch (mainMenu.getCommand()){
					case "Change username":
						isInWelcomeMenu = true;
						welcomeMenu = new WelcomeMenu();
						return;
					case "Create game":
						createHostGame();
						break;
					case "Enter game":
						createClientGame();
				}
				String player = mainMenu.getCommand().equals("Create game") ? "Host" : "Client";
				this.lobby = new Lobby(player);
			}
			return;
		}

		if (isHostGameCreated && isClientGameCreated) {
			if (view.getReady()){
				view.render();
			}
			return;
		}

		if(isHostGameCreated){
			lobby.render();
			if(host.isClientConnected()){
				isClientGameCreated = true;
			}
		}
	}

	@Override
	public void dispose () {}

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
		PlayerClient client = null;

		try {
			client = new PlayerClient(1);
		} catch (IOException e) {
			e.printStackTrace();
		}

		view = client.view;
		stage = view.getStage();

		Gdx.input.setInputProcessor(stage);

		isClientGameCreated = true;
		isHostGameCreated = true;
	}
}
