package mirea.sipi.durak;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.esotericsoftware.kryonet.Client;
import mirea.sipi.durak.game.network.Host;
import mirea.sipi.durak.game.network.PlayerClient;
import mirea.sipi.durak.game.view.View;

import java.io.IOException;

public class DurakGameApplication extends ApplicationAdapter {
	private SpriteBatch batch;
	View view;
	Stage stage;

	boolean gameCreated = false;

	@Override
	public void create () {

	}

	@Override
	public void render () {
		if (gameCreated) {
			if (view.getReady())
				view.render();
		}

		if (!gameCreated) {
			if (Gdx.input.isKeyJustPressed(Input.Keys.A))
				createHostGame();
			if (Gdx.input.isKeyJustPressed(Input.Keys.D))
				createClientGame();
		}
	}

	@Override
	public void dispose () {
	}

	private void createHostGame() {
		Host host = null;

		try {
			host = new Host(0);
		} catch (IOException e) {
			e.printStackTrace();
		}

		host.startGame(2);

		view = host.view;
		stage = view.getStage();

		Gdx.input.setInputProcessor(stage);

		gameCreated = true;
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

		gameCreated = true;
	}
}
