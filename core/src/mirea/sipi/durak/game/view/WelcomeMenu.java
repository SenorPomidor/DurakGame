package mirea.sipi.durak.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import mirea.sipi.durak.game.utils.FileUtils;

import java.util.HashMap;
import java.util.Map;

public class WelcomeMenu {
    public static final Skin UI_SKIN = new Skin(Gdx.files.internal("./data/uiskin.json"));

    public static String currentBackground = "default_back.jpeg";

    public static String username;

    public static Map<String, Texture> textures = new HashMap<>();
    private final SpriteBatch batch;
    private final Stage stage;

    private final TextField textField;

    public WelcomeMenu() {
        int windowHeight = Gdx.graphics.getHeight();
        int windowWidth = Gdx.graphics.getWidth();
        this.batch = new SpriteBatch();
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        textField = new TextField("Enter username", UI_SKIN);
        textField.setPosition(windowWidth /2.0f-100, windowHeight /2.0f);
        textField.setSize(200, 40);
        textField.setMaxLength(8);

        TextButton textButton = new TextButton("Enter", UI_SKIN);
        textButton.setPosition(windowWidth /2.0f+120, windowHeight /2.0f);
        textButton.setSize(50, 40);

        textButton.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                username = textField.getText();
            }
        });

        TextButton exitButton = new TextButton("Exit", UI_SKIN);
        exitButton.setPosition(windowWidth /2.0f-40, windowHeight /2.0f - 200);
        exitButton.setSize(80, 40);

        exitButton.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                System.exit(0);
            }
        });

        stage.addActor(textField);
        stage.addActor(textButton);
        stage.addActor(exitButton);

        FileUtils.walk(textures);
    }

    public Stage getStage() {
        return stage;
    }

    public String getUsername() {
        return username;
    }

    public void render(){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(textures.get(currentBackground), 0, 0);
        batch.end();

        stage.draw();
        stage.act();
    }
}
