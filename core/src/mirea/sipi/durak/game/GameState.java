package mirea.sipi.durak.game;

import java.util.ArrayList;

public class GameState {
    private class Table {
        Card[] attackers;
        Card[] defenders;
    }

    private ArrayList<Card> deck, discard;
    private ArrayList<Card>[] hands;
    private Table table;
}
