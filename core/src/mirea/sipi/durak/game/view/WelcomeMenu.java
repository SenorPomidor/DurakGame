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

    private final Map<String, Texture> textures;
    private final SpriteBatch batch;
    private final Stage stage;

    private final TextField textField;

    private String username;

    public WelcomeMenu() {
        int windowHeight = Gdx.graphics.getHeight();
        int windowWidth = Gdx.graphics.getWidth();
        this.batch = new SpriteBatch();
        this.textures = new HashMap<>();
        this.stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        textField = new TextField("Enter username", UI_SKIN);
        textField.setPosition(windowWidth /2.0f-100, windowHeight /2.0f);
        textField.setSize(200, 40);

        TextButton textButton = new TextButton("Enter", UI_SKIN);
        textButton.setPosition(windowWidth /2.0f+120, windowHeight /2.0f);
        textButton.setSize(50, 40);

        textButton.addListener(new ClickListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                username = textField.getText();
            }
        });

        stage.addActor(textField);
        stage.addActor(textButton);

        FileUtils.walk(textures);
    }

    public Stage getStage() {
        return stage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void render(){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(textures.get("menu_back.jpeg"), 0, 0);
        batch.end();

        stage.draw();
        stage.act();
    }
}
