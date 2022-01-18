import java.util.ArrayList;
import java.util.Collections;

public class MiniMaxAI {

    private int             maxDepth;
    private boolean         playerTurn;
    public static final int MAX_WINNING_SCORE = 999999;
    public static final int MIN_WINNING_SCORE = -999999;
    public Player           winner;
    public Player           enemy;


    public MiniMaxAI(boolean playerTurn){
        maxDepth = 4;
        setPlayerTurn(playerTurn);
    }

    public boolean checkDoneStatus(int depth, GameBoard board, int score){
        //System.out.println("check score : " + score);
        //System.out.println("depth: " + depth);

        return depth >= maxDepth || board.checkBoard() || score >= MAX_WINNING_SCORE || score <= MIN_WINNING_SCORE;
    }

    public int makeMove(GameBoard board, boolean pruned){
        int nextMove = 0;
        board.winner = this.winner;
        board.enemy = this.enemy;
        int[] nextMoveDone = pruned ? maxPlayingMovePruned(board, 0, Integer.MIN_VALUE, Integer.MAX_VALUE) : maxPlayingMove(board, 0);
        if(nextMoveDone[0] == -1)
            return nextMove;
        nextMove = nextMoveDone[0];

        return nextMove;
    }


    private int[] maxPlayingMove(GameBoard board, int depth) {
        int scoreBoard = board.evaluateBoard();
        //System.out.println("ScoreBoard eval: " + scoreBoard);

        if (checkDoneStatus(depth, board, scoreBoard)){
            return new int[]{-1, scoreBoard};
        }

            int[] maxMove = new int[]{-1, 0};

            ArrayList<Integer> possibleMoves = board.checkValidMoves(board);
            //System.out.println("depth in fx: " + depth);
            Collections.shuffle(possibleMoves);

            for (int col : possibleMoves) {
                GameBoard tempBoard = board.cloneBoard();
                tempBoard.putDisk(tempBoard, col, isPlayerTurn());
                setPlayerTurn(!isPlayerTurn());
                int next[] = minPlayingMove(tempBoard, depth+1);
                if (maxMove[0] == -1 || next[1] > maxMove[1]) {
                    maxMove[0] = col;
                    maxMove[1] = next[1];
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

        ArrayList<Integer> possibleMoves = board.checkValidMoves(board);
        Collections.shuffle(possibleMoves);

        for(int col : possibleMoves){
            GameBoard tempBoard = board.cloneBoard();
            tempBoard.putDisk(tempBoard, col, isPlayerTurn());
            setPlayerTurn(!isPlayerTurn());
            int next[] = maxPlayingMove(tempBoard, depth+1);
            if (minMove[0] == -1 || next[1] < minMove[1]){
                minMove[0] = col;
                minMove[1] = next[1];
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

        ArrayList<Integer> possibleMoves = board.checkValidMoves(board);
        Collections.shuffle(possibleMoves);

        for (int col : possibleMoves) {
            GameBoard tempBoard = board.cloneBoard();
            tempBoard.putDisk(tempBoard, col, isPlayerTurn());
            setPlayerTurn(!isPlayerTurn());
            int next[] = minPlayingMovePruned(tempBoard, depth+1, alpha, beta);
            if (maxMove[0] == -1 || next[1] > maxMove[1]) {
                maxMove[0] = col;
                maxMove[1] = next[1];
                alpha = next[1];
            }

            if(alpha >= beta)
                return maxMove;
        }

        return maxMove;

    }

    public int[] minPlayingMovePruned(GameBoard board, int depth, int alpha, int beta){
        int scoreBoard = board.evaluateBoard();

        if (checkDoneStatus(depth, board, scoreBoard)) {
            return new int[]{-1, scoreBoard};
        }
        int[] minMove = new int[]{-1,0};

        ArrayList<Integer> possibleMoves = board.checkValidMoves(board);
        Collections.shuffle(possibleMoves);

        for(int col : possibleMoves){
            GameBoard tempBoard = board.cloneBoard();
            tempBoard.putDisk(tempBoard, col, isPlayerTurn());
            setPlayerTurn(!isPlayerTurn());
            int next[] = maxPlayingMovePruned(tempBoard, depth+1, alpha, beta);
            if (minMove[0] == -1 || next[1] < minMove[1]){
                minMove[0] = col;
                minMove[1] = next[1];
            }

            if(alpha >= beta)
                return minMove;

        }

        return minMove;

    }


    public boolean isPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(boolean playerTurn) {
        this.playerTurn = playerTurn;
    }

    public void setMaxDepth(int depth) {
        this.maxDepth = depth;
    }

}
