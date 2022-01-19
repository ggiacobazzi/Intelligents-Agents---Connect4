public class MiniMaxAI {

    private int             maxDepth;
    public static final int MAX_WINNING_SCORE = 999999;
    public static final int MIN_WINNING_SCORE = -999999;
    public Player           winner;
    public Player           enemy;

    public MiniMaxAI(){
        maxDepth = 6;
    }

    public boolean checkDoneStatus(int depth, GameBoard board, int score){
        return depth >= maxDepth || board.checkBoard() || score >= MAX_WINNING_SCORE || score <= MIN_WINNING_SCORE;
    }

    public int makeMove(GameBoard board, boolean pruned){
        board.winner = this.winner;
        board.enemy = this.enemy;
        int[] nextMoveDone = pruned ? maxPlayingMovePruned(board, 0, Integer.MIN_VALUE, Integer.MAX_VALUE) : maxPlayingMove(board, 0);
        if(nextMoveDone[0] == -1){
            return 0;
        }

        return nextMoveDone[0];
    }


    private int[] maxPlayingMove(GameBoard board, int depth) {
        int scoreBoard = board.evaluateBoard();

        if (checkDoneStatus(depth, board, scoreBoard)){
            return new int[]{-1, scoreBoard};
        }

            int[] maxMove = new int[]{-1, 0};

            for (int col = 0; col < board.getGameBoard()[1].length; col++) {
                GameBoard tempBoard = board.cloneBoard();

                if(tempBoard.putDisk(tempBoard, col, winner.getPlayerColor())) {
                    int[] next = minPlayingMove(tempBoard, depth + 1);
                    if (maxMove[0] == -1 || next[1] > maxMove[1]) {
                        maxMove[0] = col;
                        maxMove[1] = next[1];
                    }
                }
            }

            return maxMove;
        }

    private int[] minPlayingMove(GameBoard board, int depth){
        int scoreBoard = board.evaluateBoard();

        if (checkDoneStatus(depth, board, scoreBoard)){
            return new int[]{-1, scoreBoard};
        }

        int[] minMove = new int[]{-1,0};

        for(int col = 0; col < board.getGameBoard()[1].length; col++){
            GameBoard tempBoard = board.cloneBoard();

            if(tempBoard.putDisk(tempBoard, col, enemy.getPlayerColor())) {
                int[] next = maxPlayingMove(tempBoard, depth + 1);
                if (minMove[0] == -1 || next[1] < minMove[1]) {
                    minMove[0] = col;
                    minMove[1] = next[1];
                }
            }
        }

        return minMove;
    }

    public int[] maxPlayingMovePruned(GameBoard board, int depth, int alpha, int beta){
        int scoreBoard = board.evaluateBoard();

        if (checkDoneStatus(depth, board, scoreBoard)) {
            return new int[]{-1, scoreBoard};
        }

        int[] maxMove = new int[]{-1, 0};

        for (int col = 0; col < board.getGameBoard()[1].length; col++) {
            GameBoard tempBoard = board.cloneBoard();

            if(tempBoard.putDisk(tempBoard, col, winner.getPlayerColor())){
                int[] next = minPlayingMovePruned(tempBoard, depth+1, alpha, beta);
                if (maxMove[0] == -1 || next[1] > maxMove[1]) {
                    maxMove[0] = col;
                    maxMove[1] = next[1];
                    alpha = next[1];
                }

                if(beta <= alpha)
                    return maxMove;
            }

        }

        return maxMove;

    }

    public int[] minPlayingMovePruned(GameBoard board, int depth, int alpha, int beta){
        int scoreBoard = board.evaluateBoard();
        if (checkDoneStatus(depth, board, scoreBoard)) {
            return new int[]{-1, scoreBoard};
        }

        int[] minMove = new int[]{-1,0};

        for(int col = 0; col < board.getGameBoard()[1].length; col++){
            GameBoard tempBoard = board.cloneBoard();

            if(tempBoard.putDisk(tempBoard, col, enemy.getPlayerColor())) {
                int[] next = maxPlayingMovePruned(tempBoard, depth + 1, alpha, beta);
                if (minMove[0] == -1 || next[1] < minMove[1]) {
                    minMove[0] = col;
                    minMove[1] = next[1];
                    beta = next[1];
                }

                if (beta <= alpha)
                    return minMove;
            }
        }

        return minMove;

    }

    public void setMaxDepth(int depth) {
        this.maxDepth = depth;
    }

}
