package mirea.sipi.durak.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import mirea.sipi.durak.game.utils.FileUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static mirea.sipi.durak.game.view.WelcomeMenu.UI_SKIN;

public class MainMenu {
    private static final String[] TEXT_BUTTON_NAMES = {"Change username", "Create game", "Enter game"};

    private final Map<String, Texture> textures;
    private final SpriteBatch batch;
    private final int windowHeight;
    private final int windowWidth;
    private final Stage stage;

    private final BitmapFont font;

    private final String username;

    private String command;

    public MainMenu(String currentUser) {
        this.windowHeight = Gdx.graphics.getHeight();
        this.windowWidth = Gdx.graphics.getWidth();
        this.username = currentUser;
        this.batch = new SpriteBatch();
        this.textures = new HashMap<>();
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        font = new BitmapFont(Gdx.files.internal("./data/default.fnt"));
        font.getData().setScale(1.3f);

        TextButton[] textButtons = new TextButton[3];

        for(final AtomicInteger i = new AtomicInteger(); i.get()< textButtons.length; i.getAndIncrement()){
            textButtons[i.get()] = new TextButton(TEXT_BUTTON_NAMES[i.get()], UI_SKIN);
            textButtons[i.get()] = new TextButton(TEXT_BUTTON_NAMES[i.get()], UI_SKIN);
            textButtons[i.get()].setPosition(windowWidth/2.0f-100, 100 + i.get() * 70);
            textButtons[i.get()].setSize(200, 50);
        }

        textButtons[0].addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                command = TEXT_BUTTON_NAMES[0];
            }
        });
        textButtons[1].addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                command = TEXT_BUTTON_NAMES[1];
            }
        });
        textButtons[2].addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                command = TEXT_BUTTON_NAMES[2];
            }
        });

        for(int i = 0; i< textButtons.length; i++){
            stage.addActor(textButtons[i]);
        }

        FileUtils.walk(textures);
    }

    public Stage getStage() {
        return stage;
    }

    public String getCommand() {
        return command;
    }

    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        drawBackground();

        stage.draw();
        stage.act();
    }


    private void drawBackground() {
        batch.begin();
        batch.draw(textures.get("menu_back.jpeg"), 0, 0);
        font.draw(batch, username + ", welcome!", windowWidth/2.0f-90-username.length(), windowHeight-70);
        font.draw(batch, "Choose an action:", windowWidth/2.0f-90, windowHeight-90);
        batch.end();
    }
}
