package mirea.sipi.durak.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import static mirea.sipi.durak.game.view.WelcomeMenu.*;

public class Settings {
    private static final String[] BACKGROUND_IMAGES_NAMES =
            {"default_back_mini.jpeg", "rain_back_mini.jpg", "white_back_mini.jpg", "wood_back_mini.jpg"};

    private final SpriteBatch batch;
    private final Stage stage;
    private final TextButton[] textButtons;
    private String command;

    public Settings() {
        int windowHeight = Gdx.graphics.getHeight();
        int windowWidth = Gdx.graphics.getWidth();
        this.batch = new SpriteBatch();
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        textButtons = new TextButton[5];

        for (int i = 0; i < textButtons.length; i++) {
            textButtons[i] = new TextButton("Try", UI_SKIN);
            textButtons[i].setPosition(60 + i*150, windowHeight / 2.0f-40);
            textButtons[i].setSize(70, 40);
        }

        textButtons[4] = new TextButton("Back to menu", UI_SKIN);
        textButtons[4].setPosition(windowWidth / 2.0f-80, windowHeight / 2.0f-100);
        textButtons[4].setSize(150, 40);

        textButtons[0].addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                currentBackground = BACKGROUND_IMAGES_NAMES[0].replace("_mini", "");
            }
        });
        textButtons[1].addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                currentBackground = BACKGROUND_IMAGES_NAMES[1].replace("_mini", "");
            }
        });
        textButtons[2].addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                currentBackground = BACKGROUND_IMAGES_NAMES[2].replace("_mini", "");
            }
        });
        textButtons[3].addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                currentBackground = BACKGROUND_IMAGES_NAMES[3].replace("_mini", "");
            }
        });

        textButtons[4].addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                command = "back to menu";
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

        batch.begin();
        batch.draw(textures.get(currentBackground), 0, 0);
        drawBackgroundImages();
        batch.end();

        stage.draw();
        stage.act();
    }

    private void drawBackgroundImages() {
        for (int i = 0; i < BACKGROUND_IMAGES_NAMES.length; i++) {
            batch.draw(textures.get(BACKGROUND_IMAGES_NAMES[i]), 50 + i*150, Gdx.graphics.getHeight() / 2.0f + 40);
        }
    }
}
