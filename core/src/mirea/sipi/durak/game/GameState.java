package mirea.sipi.durak.game;

import java.util.ArrayList;

public class GameState {
    class Table {
        Card[] attackers;
        Card[] defenders;
    }

    ArrayList<Card> deck, discard;
    ArrayList<Card>[] hands;
    Table table;
}
