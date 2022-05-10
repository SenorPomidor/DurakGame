package mirea.sipi.durak.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    private static final String ASSETS_DIRECTORY_PATH = "assets";

    private final Map<String, Texture> textures;

    private final SpriteBatch batch;
    private final int currentPlayerId;

    private final int windowHeight;
    private final int windowWidth;

    public View(int currentPlayerId) {
        this.windowHeight = Gdx.graphics.getHeight();
        this.windowWidth = Gdx.graphics.getWidth();
        this.batch = new SpriteBatch();
        this.currentPlayerId = currentPlayerId;
        this.textures = new HashMap<>();

        FileUtils.walk(ASSETS_DIRECTORY_PATH, textures);
    }

    /**
     * Отрисовывает текущее состояние игры
     *
     * @param gameState Текущее состояние игры
     */
    public void update(GameState gameState) {
        batch.begin();

        drawBackground();

        drawPlayerCards(gameState.hands[0], 0);
        drawPlayerCards(gameState.hands[1], 1);

        drawTableCards(gameState.table.defenders, 0);
        drawTableCards(gameState.table.attackers, 1);

        drawDiscard();
        drawTrumpSuit(gameState.trumpSuit);

        batch.end();
    }

    private void drawBackground() {
        batch.draw(textures.get("table.jpg"), 0, 0);
    }

    private void drawTableCards(Card[] cards, int playerId) {
        int currentX = (windowWidth - cards.length * 70) / 2;
        int currentY = playerId == currentPlayerId ? windowHeight - 250 : windowHeight - 330;
        for (Card card : cards) {
            String cardName = card.getValue() + card.getSuit().shortName() + ".gif";
            batch.draw(textures.get(cardName), currentX, currentY);
            currentX += 70;
        }
    }

    private void drawPlayerCards(ArrayList<Card> hand, int playerId) {
        int currentX = (windowWidth - hand.size() * 30) / 2;
        int currentY = playerId == currentPlayerId ? 10 : windowHeight - 110;
        for (Card card : hand) {
            Texture tex;
            if (playerId == currentPlayerId) {
                String cardName = card.getValue() + card.getSuit().shortName() + ".gif";
                tex = textures.get(cardName);
            } else {
                tex = textures.get("back.gif");
            }

            batch.draw(tex, currentX, currentY);
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

    public void dispose() {
        batch.dispose();
    }
}
