package mirea.sipi.durak.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import mirea.sipi.durak.game.model.Card;
import mirea.sipi.durak.game.model.GameState;
import mirea.sipi.durak.game.utils.DeckUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Класс отображения партии
 */
public class View {

    private List<Texture> textures;
    private SpriteBatch batch;
    private GameState gameState;

    private boolean viewUpdated;

    private int windowHeight;
    private int windowWidth;

	/**
	 * Отрисовывает текущее состояние игры
	 * @param gameState Текущее состояние игры
	 */
    public void draw(GameState gameState) {
        windowHeight =  Gdx.graphics.getHeight();
        windowWidth = Gdx.graphics.getWidth();
        batch = new SpriteBatch();

        textures = new ArrayList<>();
        this.gameState = gameState;

        viewUpdated = true;
    }

    public void render() {
        if(!viewUpdated){
            return;
        }
        ScreenUtils.clear(1, 0, 0, 1);

        batch.begin();
        System.out.println(textures.size());

        drawBackground();

        drawPlayerCards(gameState.hands[0], 0);
        drawPlayerCards(gameState.hands[1], 1);

        drawTableCards(gameState.table.defenders, 0);
        drawTableCards(gameState.table.attackers, 1);

        drawDiscard();
        drawTrumpSuit();

        batch.end();
        viewUpdated = false;
    }

    private void drawBackground() {
        Texture t = new Texture(Gdx.files.internal("table.jpg"));
        batch.draw(t, 0, 0);
    }

    private void drawTableCards(Card[] cards, int playerId) {
        int currentX = (windowWidth - gameState.table.defenders.length * 70) / 2;
        int currentY = playerId == 0 ? windowHeight - 250 : windowHeight - 330;
        for(Card card : cards){
            String cardName = card.getValue() + card.getSuit().shortName();
            Texture tex = new Texture(Gdx.files.internal("cards/" + cardName + ".gif"));
            batch.draw(tex, currentX, currentY);
            currentX+=70;

        }
    }

    private void drawPlayerCards(ArrayList<Card> hand, int playerId){
        int currentX = (windowWidth - gameState.hands[0].size() * 30) / 2;
        int currentY = playerId == 0 ? 10 : windowHeight - 110;
        for(Card card : gameState.hands[playerId]){
            Texture tex;
            if(playerId == 0){
                String cardName = card.getValue() + card.getSuit().shortName();
                tex = new Texture(Gdx.files.internal("cards/" + cardName + ".gif"));
            }
            else {
                tex = new Texture(Gdx.files.internal("cards/back.gif"));
            }

            batch.draw(tex, currentX, currentY);
            currentX+=30;

        }
    }

    private void drawDiscard(){
        int currentX = windowWidth - 80;
        int currentY = (windowHeight - 97) / 2;
        Texture tex = new Texture(Gdx.files.internal("cards/back.gif"));
        batch.draw(tex, currentX, currentY);
    }

    private void drawTrumpSuit(){
        int currentX = 10;
        int currentY = (windowHeight - 97) / 2;
        String cardName = gameState.trumpSuit.shortName();
        Texture tex = new Texture(Gdx.files.internal("suits/" + cardName +".png"));
        batch.draw(tex, currentX, currentY);
    }


    public void dispose () {
        for(Texture texture : textures){
            texture.dispose();
        }
        batch.dispose();
    }
}
