public class Player {

    public String playerType;
    public int playerColor;
    public MiniMaxAI miniMaxAI;
    public boolean notHuman;


    public Player(String playerType, int playerColor, boolean notHuman, boolean playerTurn) {
        setPlayerType(playerType);
        setPlayerColor(playerColor);
        setNotHuman(notHuman);
        if (isNotHuman()) {
            setMiniMaxAI(new MiniMaxAI(playerTurn));
            if (playerType.equals("pruned"))
                miniMaxAI.setMaxDepth(6);
        }
    }


    public String getPlayerType() {
        return playerType;
    }

    public int getPlayerColor() {
        return playerColor;
    }

    public void setPlayerType(String playerType) {
        this.playerType = playerType;
    }


    public void setPlayerColor(int playerColor) {
        this.playerColor = playerColor;
    }


    public void setMiniMaxAI(MiniMaxAI miniMaxAI) {
        this.miniMaxAI = miniMaxAI;
    }

    public MiniMaxAI getMiniMaxAI() {
        return this.miniMaxAI;
    }

    public void setNotHuman(boolean notHuman) {
        this.notHuman = notHuman;
    }

    public boolean isNotHuman() {
        return notHuman;
    }

    public void setPlayers(Player winner, Player enemy) {
        getMiniMaxAI().winner = winner;
        getMiniMaxAI().enemy = enemy;
    }
}
