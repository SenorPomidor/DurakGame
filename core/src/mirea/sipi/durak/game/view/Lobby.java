package mirea.sipi.durak.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import mirea.sipi.durak.game.commands.CommandSender;

import java.util.Map;

import static mirea.sipi.durak.game.view.WelcomeMenu.UI_SKIN;
import static mirea.sipi.durak.game.view.WelcomeMenu.currentBackground;

public class Lobby {
    private final Map<String, Texture> textures;
    private final SpriteBatch batch;
    private final int windowHeight;
    private final int windowWidth;

    private final BitmapFont font;

    private final Stage stage;

    private final String player;

    public Lobby(final String player) {
        this.windowHeight = Gdx.graphics.getHeight();
        this.windowWidth = Gdx.graphics.getWidth();
        this.batch = new SpriteBatch();
        this.textures = WelcomeMenu.textures;
        this.player = player;
        this.stage = new Stage();

        font = new BitmapFont(Gdx.files.internal("./data/default.fnt"));
        font.getData().setScale(1.3f);

        TextButton textButton = new TextButton("Ready", UI_SKIN);
        textButton.setPosition(windowWidth /2.0f-40, windowHeight /2.0f);
        textButton.setSize(50, 40);

        textButton.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                int index = player.equalsIgnoreCase("host") ? 0 : 1;
                CommandSender.sendReadyClick(index);
            }
        });

        stage.addActor(textButton);
    }

    public Stage getStage() {
        return stage;
    }

    public void renderWaitingScreen(){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        drawBackground();
        batch.begin();
        font.draw(batch, "["+player+"]", 30, windowHeight-50);
        font.draw(batch, "Waiting for the opponent...", windowWidth/2.0f-120, windowHeight/2f);
        batch.end();
    }

    public void renderReadyScreen(){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.input.setInputProcessor(stage);

        drawBackground();

        stage.draw();
        stage.act();
    }

    private void drawBackground(){
        batch.begin();
        batch.draw(textures.get(currentBackground), 0, 0);
        batch.end();
    }
}
