package mirea.sipi.durak.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import mirea.sipi.durak.game.utils.FileUtils;

import java.util.HashMap;
import java.util.Map;

import static mirea.sipi.durak.game.view.WelcomeMenu.UI_SKIN;

public class Lobby {
    private final Map<String, Texture> textures;
    private final SpriteBatch batch;
    private final int windowHeight;
    private final int windowWidth;

    private final BitmapFont font;

    private final String player;

    public Lobby(String player) {
        this.windowHeight = Gdx.graphics.getHeight();
        this.windowWidth = Gdx.graphics.getWidth();
        this.batch = new SpriteBatch();
        this.textures = new HashMap<>();
        this.player = player;

        font = new BitmapFont(Gdx.files.internal("./data/default.fnt"));
        font.getData().setScale(1.3f);

        FileUtils.walk(textures);
    }

    public void render(){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        drawBackground();
    }

    private void drawBackground(){
        batch.begin();
        batch.draw(textures.get("menu_back.jpeg"), 0, 0);
        font.draw(batch, "["+player+"]", 30, windowHeight-50);
        font.draw(batch, "Waiting for the opponent...", windowWidth/2.0f-120, windowHeight/2f);
        batch.end();
    }
}
