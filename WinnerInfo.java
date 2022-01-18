public class WinnerInfo {

    public int         winningStatus;
    public int         playerColor;


    public WinnerInfo(){
        setWinningStatus(0);
        setPlayerColor(0);
    }

    public WinnerInfo(int winningStatus, int playerColor){
        setWinningStatus(winningStatus);
        setPlayerColor(playerColor);
    }

    public void setWinningStatus(int winningStatus) {
        this.winningStatus = winningStatus;
    }

    public void setPlayerColor(int playerColor) {
        this.playerColor = playerColor;
    }

    public int getPlayerColor() {
        return playerColor;
    }
}
