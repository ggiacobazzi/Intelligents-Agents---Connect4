public class GameBoard {

    private final int[][]   gameBoard;
    public Player           firstPlayer;
    public Player           secondPlayer;
    public Player           winner;
    public Player           enemy;
    public boolean          simulation;

    public GameBoard(int[][] board){
        gameBoard = board;
    }

    public boolean checkBoard(){
        for (int[] ints : gameBoard) {
            for (int anInt : ints) {
                if (anInt == 0)
                    return false;
            }
        }
        return true;
    }

    public GameBoard cloneBoard(){
        int [][] tempBoard = new int[gameBoard.length][];
        for (int i = 0; i < gameBoard.length; i++)
            tempBoard[i] = gameBoard[i].clone();

        GameBoard newGameBoard = new GameBoard(tempBoard);
        newGameBoard.setFirstPlayer(firstPlayer);
        newGameBoard.setSecondPlayer(secondPlayer);
        newGameBoard.setSimulation(simulation);
        newGameBoard.winner = winner;
        newGameBoard.enemy = enemy;
        return newGameBoard;
    }


    public void putDisk(GameBoard board, int n, boolean playerTurn) {
        for (int row = gameBoard.length -1 ; row >= 0; row--)
            if (board.getGameBoard()[row][n] == 0){
                board.getGameBoard()[row][n] = playerTurn ? firstPlayer.getPlayerColor() : secondPlayer.getPlayerColor();
                return;
            }
    }

    public boolean putDisk(GameBoard board, int n, int playerTurn) {
        for (int row = gameBoard.length -1 ; row >= 0; row--)
            if (board.getGameBoard()[row][n] == 0){
                board.getGameBoard()[row][n] = playerTurn;
                return true;
            }
        return false;
    }

    public WinnerInfo check4() {
        WinnerInfo wInfo = new WinnerInfo();
        for (int row = 0; row < gameBoard.length; row++) {
            for (int col = 0; col < gameBoard[1].length - 3; col++) {
                int curr = gameBoard[row][col];
                if (curr > 0
                        && curr == gameBoard[row][col + 1]
                        && curr == gameBoard[row][col + 2]
                        && curr == gameBoard[row][col + 3]) {
                    wInfo = new WinnerInfo(1, gameBoard[row][col]);
                    return wInfo;
                }
            }
        }
        // vertical columns
        for (int col = 0; col < gameBoard[1].length; col++) {
            for (int row = 0; row < gameBoard.length - 3; row++) {
                int curr = gameBoard[row][col];
                if (curr > 0
                        && curr == gameBoard[row + 1][col]
                        && curr == gameBoard[row + 2][col]
                        && curr == gameBoard[row + 3][col]) {
                    wInfo = new WinnerInfo(1, gameBoard[row][col]);
                    return wInfo;
                }

            }
        }
        // diagonal lower left to upper right
        for (int row = 0; row < gameBoard.length - 3; row++) {
            for (int col = 0; col < gameBoard[1].length - 3; col++) {
                int curr = gameBoard[row][col];
                if (curr > 0
                        && curr == gameBoard[row + 1][col + 1]
                        && curr == gameBoard[row + 2][col + 2]
                        && curr == gameBoard[row + 3][col + 3]) {
                    wInfo = new WinnerInfo(1, gameBoard[row][col]);
                    return wInfo;
                }
            }
        }
        // diagonal upper left to lower right
        for (int row = gameBoard.length - 1; row >= 3; row--) {
            for (int col = 0; col < gameBoard[1].length - 3; col++) {
                int curr = gameBoard[row][col];
                if (curr > 0
                        && curr == gameBoard[row - 1][col + 1]
                        && curr == gameBoard[row - 2][col + 2]
                        && curr == gameBoard[row - 3][col + 3]) {
                    wInfo = new WinnerInfo(1, gameBoard[row][col]);
                    return wInfo;
                }
            }
        }
        return wInfo;
    }


    public int evaluateBoard(){
        int totalEvaluation, verticalPoints = 0, horizontalPoints = 0, descendantDiagonalPoints = 0,
                ascendantDiagonalPoints = 0;

        // Vertical Scores
        for(int row = 0; row < gameBoard.length - 3; row++){
            for(int col = 0; col < gameBoard[1].length; col++){
                int tempScore = calcScore(row, col, 1, 0);
                verticalPoints += tempScore;
                if(tempScore >= MiniMaxAI.MAX_WINNING_SCORE || tempScore <= MiniMaxAI.MIN_WINNING_SCORE)
                    return tempScore;
            }
        }

        // Horizontal Scores
        for(int row = 0; row < gameBoard.length; row++){
            for(int col = 0; col < gameBoard[1].length - 3; col++){
                int tempScore = calcScore(row, col, 0, 1);
                horizontalPoints += tempScore;
                if(tempScore >= MiniMaxAI.MAX_WINNING_SCORE || tempScore <= MiniMaxAI.MIN_WINNING_SCORE)
                    return tempScore;
            }
        }

        // Descendant Diagonal Scores
        for(int row = 0; row < gameBoard.length - 3; row++){
            for(int col = 0; col < gameBoard[1].length - 3; col++){
                int tempScore = calcScore(row, col, 1, 1);
                descendantDiagonalPoints += tempScore;
                if(tempScore >= MiniMaxAI.MAX_WINNING_SCORE || tempScore <= MiniMaxAI.MIN_WINNING_SCORE)
                    return tempScore;
            }
        }

        // Ascendant Diagonal Scores
        for(int row = 3; row < gameBoard.length; row++){
            for(int col = 0; col < gameBoard[1].length - 4; col++){
                int tempScore = calcScore(row, col, -1, 1);
                ascendantDiagonalPoints += tempScore;
                if(tempScore >= MiniMaxAI.MAX_WINNING_SCORE || tempScore <= MiniMaxAI.MIN_WINNING_SCORE)
                    return tempScore;
            }
        }


        totalEvaluation = verticalPoints + horizontalPoints + descendantDiagonalPoints + ascendantDiagonalPoints;


        return totalEvaluation;
    }


    public int calcScore(int row, int col, int incrementNumRow, int incrementNumCol){
        int winnerPoints = 0, enemyPoints = 0;

        for(int i = 0; i < 4; i++){
            if(gameBoard[row][col] == winner.getPlayerColor()){
                winnerPoints++;
            }
            else if(gameBoard[row][col] == enemy.getPlayerColor()){
                enemyPoints++;
            }

            row += incrementNumRow;
            col += incrementNumCol;
        }

        if(enemyPoints == 4)
            return MiniMaxAI.MIN_WINNING_SCORE;
        else if(winnerPoints == 4)
            return MiniMaxAI.MAX_WINNING_SCORE;
        else
            return winnerPoints;
    }

    public int[][] getGameBoard() {
        return gameBoard;
    }

    public void setFirstPlayer(Player firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public void setSecondPlayer(Player secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    private void setSimulation(boolean simulation) {
        this.simulation = simulation;
    }
}
