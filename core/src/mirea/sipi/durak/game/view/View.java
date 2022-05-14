package mirea.sipi.durak.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import mirea.sipi.durak.game.commands.CommandSender;
import mirea.sipi.durak.game.model.Card;
import mirea.sipi.durak.game.model.GameState;
import mirea.sipi.durak.game.utils.FileUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс отображения партии
 */
public class View {
    private final Map<String, Texture> textures;

    private final SpriteBatch batch;
    private final int currentPlayerId;

    private final int windowHeight;
    private final int windowWidth;

    private GameState gameState;

    private Stage stage;

    private boolean ready;

    public View(int currentPlayerId) {
        this.windowHeight = Gdx.graphics.getHeight();
        this.windowWidth = Gdx.graphics.getWidth();
        this.batch = new SpriteBatch();
        this.currentPlayerId = currentPlayerId;
        this.textures = new HashMap<>();

        stage = new Stage();

        FileUtils.walk(textures);
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
    }

    public void render() {
        batch.begin();

        stage.draw();

        drawDiscard();
        drawTrumpSuit(gameState.trumpSuit);

        batch.end();
    }

    private void updateBackground() {
        Button background = new Button(new TextureRegionDrawable(textures.get("table.jpg")));
        stage.addActor(background);
        background.setPosition(0, 0);
        background.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                CommandSender.sendTableClick();
            }
        });
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
            Texture tex;
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
        int currentY = (windowHeight - 97) / 2;
        batch.draw(textures.get("back.gif"), currentX, currentY);
    }

    private void drawTrumpSuit(Card.Suit trumpSuit) {
        int currentX = 10;
        int currentY = (windowHeight - 97) / 2;
        String cardName = trumpSuit.shortName() + ".png";
        batch.draw(textures.get(cardName), currentX, currentY);
    }

    public boolean getReady() {
        return ready;
    }

    public void dispose() {
        batch.dispose();
    }
}
