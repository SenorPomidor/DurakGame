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
import static mirea.sipi.durak.game.view.WelcomeMenu.currentBackground;

public class MainMenu {
    private static final String[] TEXT_BUTTON_NAMES = {"Change username", "Settings", "Create game", "Enter game"};

    public static boolean isConnectionRefused = false;

    private final Map<String, Texture> textures;
    private final SpriteBatch batch;
    private final int windowHeight;
    private final int windowWidth;
    private final Stage stage;

    private final BitmapFont font;
    private final BitmapFont errorFont;
    private final TextButton[] textButtons;

    private final String username;

    private String command;

    public MainMenu(String currentUser) {
        this.windowHeight = Gdx.graphics.getHeight();
        this.windowWidth = Gdx.graphics.getWidth();
        this.username = currentUser;
        this.batch = new SpriteBatch();
        this.textures = WelcomeMenu.textures;
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        font = new BitmapFont(Gdx.files.internal("./data/default.fnt"));
        font.getData().setScale(1.3f);

        errorFont = new BitmapFont(Gdx.files.internal("./data/default.fnt"));
        errorFont.setColor(Color.RED);

        textButtons = new TextButton[4];

        for(final AtomicInteger i = new AtomicInteger(); i.get()< textButtons.length; i.getAndIncrement()){
            textButtons[i.get()] = new TextButton(TEXT_BUTTON_NAMES[i.get()], UI_SKIN);
            textButtons[i.get()] = new TextButton(TEXT_BUTTON_NAMES[i.get()], UI_SKIN);
            textButtons[i.get()].setPosition(windowWidth/2.0f-100, 50 + i.get() * 70);
            textButtons[i.get()].setSize(200, 50);
        }

        textButtons[0].addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                command = TEXT_BUTTON_NAMES[0];
            }
        });
        textButtons[1].addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                command = TEXT_BUTTON_NAMES[1];
            }
        });
        textButtons[2].addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                command = TEXT_BUTTON_NAMES[2];
            }
        });
        textButtons[3].addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                command = TEXT_BUTTON_NAMES[3];
            }
        });

        for (TextButton textButton : textButtons) {
            stage.addActor(textButton);
        }
    }

    public Stage getStage() {
        return stage;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.input.setInputProcessor(stage);

        drawBackground();

        stage.draw();
        stage.act();
    }


    private void drawBackground() {
        batch.begin();
        batch.draw(textures.get(currentBackground), 0, 0);
        font.draw(batch, username + ", welcome!", windowWidth/2.0f-90-username.length(), windowHeight-60);
        font.draw(batch, "Choose an action:", windowWidth/2.0f-90, windowHeight-80);
        if(isConnectionRefused){
            errorFont.draw(batch, "Connection refused, try later", 30, windowHeight-20);
        }
        batch.end();
    }
}
