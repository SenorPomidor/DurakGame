package mirea.sipi.durak;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import mirea.sipi.durak.game.network.Host;
import mirea.sipi.durak.game.network.PlayerClient;
import mirea.sipi.durak.game.view.View;

import java.io.IOException;

public class DurakGameApplication extends ApplicationAdapter {
	private SpriteBatch batch;
	View view;

	@Override
	public void create () {

		Gdx.graphics.setContinuousRendering(false);
		Gdx.graphics.requestRendering();

		Host host = null;

		try {
			host = new Host(0);
		} catch (IOException e) {
			e.printStackTrace();
		}

		host.startGame(2);

		view = host.view;
	}

	@Override
	public void render () {
		view.render();
	}

	@Override
	public void dispose () {
	}
}
