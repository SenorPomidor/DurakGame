package mirea.sipi.durak.game.commands;

import mirea.sipi.durak.game.model.Card;
import mirea.sipi.durak.game.network.Player;

public class CommandSender {
    private static Player player;
    private static int playerID;
    public static boolean isDefender;

    private static Card activeCard;

    public static void initialize(Player player) {
        CommandSender.player = player;
        playerID = player.getPlayerID();
    }

    public static void sendHandCard(Card card) {
        if (!isDefender) {
            Command command = new AttackCommand(playerID, card);
            player.makeTurn(command);
        }
        else {
            activeCard = card;
        }
    }

    public static void sendTableCard(Card card) {
        if (!isDefender || activeCard == null)
            return;

        Command command = new DefendCommand(playerID, activeCard, card);
        activeCard = null;
        player.makeTurn(command);
    }

    public static void sendTableClick() {
        if (!isDefender || activeCard == null)
            return;

        Command command = new RotateCommand(playerID, activeCard);
        activeCard = null;
        player.makeTurn(command);
    }

    public static void sendPassClick() {
        activeCard = null;
        Command command = new PassCommand(playerID);
        player.makeTurn(command);
    }

    public static void sendChatClick(String message){
        player.makeTurn(new SendChatCommand(message));
    }

    public static void sendReadyClick(int index){
        player.makeTurn(new ReadyCommand(index));
    }
}
