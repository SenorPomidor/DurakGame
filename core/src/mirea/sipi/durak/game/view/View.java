package mirea.sipi.durak.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import mirea.sipi.durak.game.commands.CommandSender;
import mirea.sipi.durak.game.model.Card;
import mirea.sipi.durak.game.model.GameState;
import mirea.sipi.durak.game.utils.FileUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static mirea.sipi.durak.game.view.WelcomeMenu.*;

/**
 * Класс отображения партии
 */
public class View {
    private final Map<String, Texture> textures;

    private final SpriteBatch batch;
    private final SpriteBatch chatBatch;
    private final int currentPlayerId;

    private final int windowHeight;
    private final int windowWidth;

    private GameState gameState;

    private Stage stage;
    private BitmapFont font;

    private boolean ready;

    public View(int currentPlayerId) {
        this.windowHeight = Gdx.graphics.getHeight();
        this.windowWidth = Gdx.graphics.getWidth();
        this.batch = new SpriteBatch();
        this.chatBatch = new SpriteBatch();
        chatBatch.setColor(255, 255, 255, 0.7f);

        this.currentPlayerId = currentPlayerId;
        this.textures = WelcomeMenu.textures;

        font = new BitmapFont(Gdx.files.internal("./data/default.fnt"));
        font.setColor(Color.BLACK);
        font.getData().setScale(0.7f);

        stage = new Stage();
    }

    public Stage getStage() {
        return stage;
    }

    /**
     * Отрисовывает текущее состояние игры
     *
     * @param gameState Текущее состояние игры
     */
    public void update(GameState gameState) {
        this.gameState = gameState;

        ready = true;

        stage.clear();

        updateBackground();
        updatePass();

        updatePlayerCards(gameState.hands[0], 0);
        updatePlayerCards(gameState.hands[1], 1);

        updateAttackerCards(gameState.table.attackers);
        updateDefenderCards(gameState.table.defenders);

        updateChatHistory();
    }
    public void render() {
        batch.begin();

        stage.draw();

        drawDiscard();
        drawTrumpSuit();
        batch.end();

        drawChat();
        drawChatHistory();
    }

    private void updateBackground() {
        Button background = new Button(new TextureRegionDrawable(textures.get(currentBackground)));
        stage.addActor(background);
        background.setPosition(0, 0);
        background.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                CommandSender.sendTableClick();
            }
        });
    }

    private void updateChatHistory() {

        final TextField textField = new TextField("", UI_SKIN);
        textField.setPosition(8,  windowHeight - 325);
        textField.setSize(90, 30);

        final TextButton textButton = new TextButton("Send", UI_SKIN);
        textButton.setPosition(30,  windowHeight - 360);
        textButton.setSize(40, 30);

        textButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                CommandSender.sendChatClick(username + ": " + textField.getText());
            }
        });

        stage.addActor(textField);
        stage.addActor(textButton);
    }


    private void updatePass() {
        Texture tex = gameState.playerPass[currentPlayerId] ? textures.get("passPressed.png") : textures.get("pass.png");
        Button passButton = new Button(new TextureRegionDrawable(tex));
        stage.addActor(passButton);
        passButton.setPosition(windowWidth - 50, windowHeight - 50);
        passButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                CommandSender.sendPassClick();
            }
        });
    }

    private void updateAttackerCards(Card[] cards) {
        int currentX = (windowWidth - cards.length * 70) / 2;
        int currentY = windowHeight - 250;
        for (final Card card : cards) {
            if (card == null)
                break;

            String cardName = card.getValue() + card.getSuit().shortName() + ".gif";
            Button tableCardButton = new Button(new TextureRegionDrawable(textures.get(cardName)));
            stage.addActor(tableCardButton);
            tableCardButton.setPosition(currentX, currentY);
            tableCardButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    CommandSender.sendTableCard(card);
                }
            });
            currentX += 70;
        }
    }

    private void updateDefenderCards(Card[] cards) {
        int currentX = (windowWidth - cards.length * 70) / 2;
        int currentY = windowHeight - 330;
        for (Card card : cards) {
            if (card == null)
                break;

            String cardName = card.getValue() + card.getSuit().shortName() + ".gif";
            Image tableCardImage = new Image(new TextureRegionDrawable(textures.get(cardName)));
            stage.addActor(tableCardImage);
            tableCardImage.setPosition(currentX, currentY);
            currentX += 70;
        }
    }

    private void updatePlayerCards(ArrayList<Card> hand, int playerId) {
        int currentX = (windowWidth - hand.size() * 30) / 2;
        int currentY = playerId == currentPlayerId ? 10 : windowHeight - 110;
        for (final Card card : hand) {
            if (playerId == currentPlayerId) {
                String cardName = card.getValue() + card.getSuit().shortName() + ".gif";
                Button cardButton = new Button(new TextureRegionDrawable(textures.get(cardName)));
                stage.addActor(cardButton);
                cardButton.addListener(new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        CommandSender.sendHandCard(card);
                    }
                });
                cardButton.setPosition(currentX, currentY);
            } else {
                Image cardBack = new Image(new TextureRegionDrawable(textures.get("back.gif")));
                stage.addActor(cardBack);
                cardBack.setPosition(currentX, currentY);
            }

            currentX += 30;
        }
    }

    private void drawDiscard() {
        int currentX = windowWidth - 80;
        int currentY = (windowHeight - 50) / 2;
        batch.draw(textures.get("back.gif"), currentX, currentY);
    }

    private void drawChat() {
        chatBatch.begin();
        int currentX = 3;
        int currentY = (windowHeight - 140) / 2;
        chatBatch.draw(textures.get("white_back.png"), currentX, currentY);
        chatBatch.end();
    }

    private void drawChatHistory(){
        ArrayList<String> chat = gameState.chatHistory;
        batch.begin();
        for(int i=0; i<chat.size(); i++){
            font.draw(batch, chat.get(i), 7,  windowHeight - 150 - i*12);
        }
        batch.end();
    }

    private void drawTrumpSuit() {
        int currentX = windowWidth - 80;
        int currentY = (windowHeight - 220) / 2;
        String cardName = gameState.trumpSuit.shortName() + ".png";
        batch.draw(textures.get(cardName), currentX, currentY);
    }

    public boolean getReady() {
        return ready;
    }

    public void dispose() {
        batch.dispose();
    }
}
