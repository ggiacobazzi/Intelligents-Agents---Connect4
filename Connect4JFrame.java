import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Connect4JFrame extends JFrame implements ActionListener {

    private final Button btn1;
    private final Button btn2;
    private final Button btn3;
    private final Button btn4;
    private final Button btn5;
    private final Button btn6;
    private final Button btn7;
    private final Button btnStart;
    private final ArrayList<Button> btnList = new ArrayList<>();
    private final MovesPanel movesPanel;
    MenuItem newMI, exitMI, redMI, yellowMI, simulationsMI, playerMI, randomPlayerMI, miniMaxPlayerMI,
            playerPrunedMI, randomRandomMI, randomMiniMI, randomPrunedMI, miniMiniMI, miniPrunedMI, prunedPrunedMI;
    public GameBoard board;
    boolean end = false;
    boolean gameStart;
    public static String agentMode;
    public Player firstPlayer;
    public Player secondPlayer;
    public boolean playerTurn = true;
    public boolean onlyAI = false;
    public static final int BLANK = 0;
    public static final int RED = 1;
    public static final int YELLOW = 2;

    public static final int MAXROW = 6;     // 6 rows
    public static final int MAXCOL = 7;     // 7 columns
    public static int NEXTMOVE = 0;

    public static final String SPACE = "                  "; // 18 spaces
    public int gameCounter = 0;

    int activeColour = RED;

    public static int REDMOVES;
    public static int YELLOWMOVES;
    public static int TOTALMOVES;
    public double elapsedTime;

    public Connect4 parent;

    public Connect4JFrame() {
        setTitle("Connect4 by Chris Clarke");
        MenuBar mbar = new MenuBar();

        Menu fileMenu = new Menu("File");
        newMI = new MenuItem("New");
        newMI.addActionListener(this);
        fileMenu.add(newMI);
        exitMI = new MenuItem("Exit");
        exitMI.addActionListener(this);
        fileMenu.add(exitMI);
        mbar.add(fileMenu);

        Menu optMenu = new Menu("Options");

        redMI = new MenuItem("Red starts");
        redMI.addActionListener(this);
        optMenu.add(redMI);
        yellowMI = new MenuItem("Yellow starts");
        yellowMI.addActionListener(this);
        optMenu.add(yellowMI);
        simulationsMI = new MenuItem("Simulations options");
        simulationsMI.addActionListener(this);
        optMenu.add(simulationsMI);
        mbar.add(optMenu);


        Menu playerMenu = new Menu("Players");

        // Normal player
        playerMI = new MenuItem("Normal vs Normal");
        playerMI.addActionListener(this);
        playerMenu.add(playerMI);
        randomPlayerMI = new MenuItem("Normal vs Random");
        randomPlayerMI.addActionListener(this);
        playerMenu.add(randomPlayerMI);
        miniMaxPlayerMI = new MenuItem("Normal vs MiniMax");
        miniMaxPlayerMI.addActionListener(this);
        playerMenu.add(miniMaxPlayerMI);
        playerPrunedMI = new MenuItem("Normal vs Pruned");
        playerPrunedMI.addActionListener(this);
        playerMenu.add(playerPrunedMI);

        // Random player
        randomRandomMI = new MenuItem("Random vs Random");
        randomRandomMI.addActionListener(this);
        playerMenu.add(randomRandomMI);
        randomMiniMI = new MenuItem("Random vs MiniMax");
        randomMiniMI.addActionListener(this);
        playerMenu.add(randomMiniMI);
        randomPrunedMI = new MenuItem("Random vs Pruned");
        randomPrunedMI.addActionListener(this);
        playerMenu.add(randomPrunedMI);

        // Minimax player
        miniMiniMI = new MenuItem("MiniMax vs MiniMax");
        miniMiniMI.addActionListener(this);
        playerMenu.add(miniMiniMI);
        miniPrunedMI = new MenuItem("MiniMax vs Pruned");
        miniPrunedMI.addActionListener(this);
        playerMenu.add(miniPrunedMI);

        // Pruned player
        prunedPrunedMI = new MenuItem("Pruned vs Pruned");
        prunedPrunedMI.addActionListener(this);
        playerMenu.add(prunedPrunedMI);


        mbar.add(playerMenu);
        setMenuBar(mbar);

        // Build control panel.
        Panel panel = new Panel();

        btn1 = new Button("1");
        btn1.addActionListener(this);
        btn1.setEnabled(false);
        panel.add(btn1);
        Label lblSpacer = new Label(SPACE);
        panel.add(lblSpacer);

        btn2 = new Button("2");
        btn2.addActionListener(this);
        btn2.setEnabled(false);
        panel.add(btn2);
        lblSpacer = new Label(SPACE);
        panel.add(lblSpacer);

        btn3 = new Button("3");
        btn3.addActionListener(this);
        btn3.setEnabled(false);
        panel.add(btn3);
        lblSpacer = new Label(SPACE);
        panel.add(lblSpacer);

        btn4 = new Button("4");
        btn4.addActionListener(this);
        btn4.setEnabled(false);
        panel.add(btn4);
        lblSpacer = new Label(SPACE);
        panel.add(lblSpacer);

        btn5 = new Button("5");
        btn5.addActionListener(this);
        btn5.setEnabled(false);
        panel.add(btn5);
        lblSpacer = new Label(SPACE);
        panel.add(lblSpacer);

        btn6 = new Button("6");
        btn6.addActionListener(this);
        btn6.setEnabled(false);
        panel.add(btn6);
        lblSpacer = new Label(SPACE);
        panel.add(lblSpacer);

        btn7 = new Button("7");
        btn7.addActionListener(this);
        btn7.setEnabled(false);
        panel.add(btn7);

        btnList.add(btn1);
        btnList.add(btn2);
        btnList.add(btn3);
        btnList.add(btn4);
        btnList.add(btn5);
        btnList.add(btn6);
        btnList.add(btn7);

        this.movesPanel = new MovesPanel(REDMOVES, YELLOWMOVES, TOTALMOVES, elapsedTime);


        btnStart = new Button("Start");
        btnStart.addActionListener(this);
        btnStart.setEnabled(true);

        Panel bottomPanel = new Panel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.PAGE_AXIS));
        bottomPanel.add(movesPanel);
        bottomPanel.add(btnStart);

        add(panel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.SOUTH);
//        add(this.movesPanel, BorderLayout.SOUTH);
//        add(btnStart, BorderLayout.EAST);

        initialize();
        firstPlayer = new Player("human", 1, false, playerTurn);
        secondPlayer = new Player("human", 2, false, !playerTurn);
        setPlayers(firstPlayer, secondPlayer);

        setSize(1024, 768);
        setLocationRelativeTo(null);
    }

    public void initialize() {
        board = new GameBoard(new int[MAXROW][MAXCOL]);
        for (int row = 0; row < MAXROW; row++)
            for (int col = 0; col < MAXCOL; col++)
                board.getGameBoard()[row][col] = BLANK;
        gameStart = false;
        end = false;
        gameCounter = 0;
        REDMOVES = 0;
        YELLOWMOVES = 0;
        TOTALMOVES = 0;
        movesPanel.resetLabels();
    }

    public void setPlayers(Player firstPlay, Player secondPlay) {
        firstPlayer = firstPlay;
        secondPlayer = secondPlay;
        board.setFirstPlayer(firstPlayer);
        board.setSecondPlayer(secondPlayer);
    }

    public void paint(Graphics g) {
        if (!parent.simulation) {
            g.setColor(Color.BLUE);
            g.fillRect(110, 50, 100 + 100 * MAXCOL, 100 + 100 * MAXROW);
            for (int row = 0; row < MAXROW; row++)
                for (int col = 0; col < MAXCOL; col++) {
                    if (board.getGameBoard()[row][col] == BLANK) g.setColor(Color.WHITE);
                    if (board.getGameBoard()[row][col] == RED) g.setColor(Color.RED);
                    if (board.getGameBoard()[row][col] == YELLOW) g.setColor(Color.YELLOW);
                    g.fillOval(160 + 100 * col, 100 + 100 * row, 100, 100);
                }
        }
    }


    public void gameTurnAuto() {
        if (!parent.simulation)
            System.out.println("Started game");
        while (!end) {
            if (playerTurn) {
                playerTurn(firstPlayer);
                REDMOVES++;
                TOTALMOVES++;
                movesPanel.updateLabel(false, REDMOVES,
                        YELLOWMOVES, TOTALMOVES, elapsedTime);
            }
            // Second player turn
            else {
                playerTurn(secondPlayer);
                YELLOWMOVES++;
                TOTALMOVES++;
                movesPanel.updateLabel(true, REDMOVES,
                        YELLOWMOVES, TOTALMOVES, elapsedTime);

            }

            WinnerInfo wInfo = check4();
            if (wInfo.winningStatus == 1) {
                displayWinner(wInfo.getPlayerColor());
            }
            playerTurn = !playerTurn;
            gameCounter += 1;
            if (!parent.simulation)
                System.out.println("Turn: " + gameCounter);

            repaint();
        }

    }

    public void gameTurn() {
        if (end)
            return;
        gameStart = true;
        repaint();
        // First player turn
        if (playerTurn) {
            playerTurn(firstPlayer);
            REDMOVES++;
            TOTALMOVES++;
            movesPanel.updateLabel(false, REDMOVES,
                    YELLOWMOVES, TOTALMOVES, elapsedTime);

            WinnerInfo wInfo = check4();
            if (wInfo.winningStatus == 1) {
                displayWinner(wInfo.getPlayerColor());
            }
            playerTurn = !playerTurn;
            if (!secondPlayer.getPlayerType().equals("human")) {
                gameTurn();
            }
        }
        // Second player turn
        else {
            playerTurn(secondPlayer);
            YELLOWMOVES++;
            TOTALMOVES++;
            movesPanel.updateLabel(true, REDMOVES,
                    YELLOWMOVES, TOTALMOVES, elapsedTime);

            WinnerInfo wInfo = check4();
            if (wInfo.winningStatus == 1) {
                displayWinner(wInfo.getPlayerColor());
            }
            playerTurn = !playerTurn;
            if (!firstPlayer.getPlayerType().equals("human")) {
                gameTurn();
            }
        }
        gameCounter += 1;
        if (!parent.simulation)
            System.out.println("Turn: " + gameCounter);
    }

    public void playerTurn(Player player) {
        final long startMoveTime = System.nanoTime();
        switch (player.getPlayerType()) {
            case "human": {
                if (!parent.simulation)
                    System.out.println("NEXTMOVE: " + NEXTMOVE);
                break;
            }
            case "random": {
                NEXTMOVE = ThreadLocalRandom.current().nextInt(0, 7);
                if (!parent.simulation)
                    System.out.println("Random move: " + NEXTMOVE);
                break;
            }
            case "minimax": {
                NEXTMOVE = player.miniMaxAI.makeMove(board, false);
                if (!parent.simulation)
                    System.out.println("MiniMax move: " + NEXTMOVE);
                break;
            }
            case "pruned": {
                NEXTMOVE = player.miniMaxAI.makeMove(board, true);
                if (!parent.simulation)
                    System.out.println("Pruned move: " + NEXTMOVE);
                break;
            }
        }
        board.putDisk(board, NEXTMOVE, playerTurn);
        final long endMoveTime = System.nanoTime();
        long tempElapsedTime = endMoveTime - startMoveTime;
        elapsedTime = (long) (tempElapsedTime / 1E6);
        if (!parent.simulation)
            System.out.println("time: " + elapsedTime);
    }


    public void displayWinner(int n) {
        parent.endGame(n, RED, TOTALMOVES);
        setBtnStatus(false);
        btnStart.setEnabled(false);
    }

    public WinnerInfo check4() {
        // see if there are 4 disks in a row: horizontal, vertical or diagonal
        // horizontal rows
        WinnerInfo wInfo = new WinnerInfo();
        for (int row = 0; row < board.getGameBoard().length; row++) {
            for (int col = 0; col < board.getGameBoard()[1].length - 3; col++) {
                int curr = board.getGameBoard()[row][col];
                if (curr > 0
                        && curr == board.getGameBoard()[row][col + 1]
                        && curr == board.getGameBoard()[row][col + 2]
                        && curr == board.getGameBoard()[row][col + 3]) {
                    wInfo = new WinnerInfo(1, board.getGameBoard()[row][col]);
                    return wInfo;
                }
            }
        }
        // vertical columns
        for (int col = 0; col < board.getGameBoard()[1].length; col++) {
            for (int row = 0; row < board.getGameBoard().length - 3; row++) {
                int curr = board.getGameBoard()[row][col];
                if (curr > 0
                        && curr == board.getGameBoard()[row + 1][col]
                        && curr == board.getGameBoard()[row + 2][col]
                        && curr == board.getGameBoard()[row + 3][col]) {
                    wInfo = new WinnerInfo(1, board.getGameBoard()[row][col]);
                    return wInfo;
                }

            }
        }
        // diagonal lower left to upper right
        for (int row = 0; row < board.getGameBoard().length - 3; row++) {
            for (int col = 0; col < board.getGameBoard()[1].length - 3; col++) {
                int curr = board.getGameBoard()[row][col];
                if (curr > 0
                        && curr == board.getGameBoard()[row + 1][col + 1]
                        && curr == board.getGameBoard()[row + 2][col + 2]
                        && curr == board.getGameBoard()[row + 3][col + 3]) {
                    wInfo = new WinnerInfo(1, board.getGameBoard()[row][col]);
                    return wInfo;
                }
            }
        }
        // diagonal upper left to lower right
        for (int row = board.getGameBoard().length - 1; row >= 3; row--) {
            for (int col = 0; col < board.getGameBoard()[1].length - 3; col++) {
                int curr = board.getGameBoard()[row][col];
                if (curr > 0
                        && curr == board.getGameBoard()[row - 1][col + 1]
                        && curr == board.getGameBoard()[row - 2][col + 2]
                        && curr == board.getGameBoard()[row - 3][col + 3]) {
                    wInfo = new WinnerInfo(1, board.getGameBoard()[row][col]);
                    return wInfo;
                }
            }
        }

        // no win
        return wInfo;
    } // end check4

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn1) {
            if (!onlyAI) {
                NEXTMOVE = 0;
                gameTurn();
            }
        } else if (e.getSource() == btn2) {
            if (!onlyAI) {
                NEXTMOVE = 1;
                gameTurn();
            }
        } else if (e.getSource() == btn3) {
            if (!onlyAI) {
                NEXTMOVE = 2;
                gameTurn();
            }
        } else if (e.getSource() == btn4) {
            if (!onlyAI) {
                NEXTMOVE = 3;
                gameTurn();
            }
        } else if (e.getSource() == btn5) {
            if (!onlyAI) {
                NEXTMOVE = 4;
                gameTurn();
            }
        } else if (e.getSource() == btn6) {
            if (!onlyAI) {
                NEXTMOVE = 5;
                gameTurn();
            }
        } else if (e.getSource() == btn7) {
            if (!onlyAI) {
                NEXTMOVE = 6;
                gameTurn();
            }
        } else if (e.getSource() == btnStart) {
            if (!end) {
                System.out.println("start");
                if (onlyAI) {
                    parent.newGame(firstPlayer, secondPlayer, false);
                    gameTurnAuto();
                } else {
                    parent.newGame(firstPlayer, secondPlayer, false);
                    setBtnStatus(true);
                }
            }

        } else if (e.getSource() == newMI) {
            end = false;
            initialize();
            Player firstPlayer = new Player(this.firstPlayer.getPlayerType(), 1, this.firstPlayer.isNotHuman(), playerTurn);
            Player secondPlayer = new Player(this.secondPlayer.getPlayerType(), 2, this.secondPlayer.isNotHuman(), !playerTurn);

            if (firstPlayer.getPlayerType().equals("minimax") || firstPlayer.getPlayerType().equals("pruned"))
                firstPlayer.setPlayers(firstPlayer, secondPlayer);

            if (secondPlayer.getPlayerType().equals("minimax") || secondPlayer.getPlayerType().equals("pruned"))
                secondPlayer.setPlayers(secondPlayer, firstPlayer);

            setPlayers(firstPlayer, secondPlayer);
            btnStart.setEnabled(true);
            repaint();
        } else if (e.getSource() == exitMI) {
            System.exit(0);
        } else if (e.getSource() == redMI) {
            if (!gameStart) {
                playerTurn = true;
                activeColour = RED;
            }
        } else if (e.getSource() == yellowMI) {
            if (!gameStart) {
                playerTurn = false;
                activeColour = YELLOW;
            }
        } else if (e.getSource() == simulationsMI) {
            System.out.println("Opening simulations options");
            Simulations simOptionsWindow = new Simulations(parent);
        } else if (e.getSource() == playerMI) {
            if (!gameStart) {
                initialize();
                Player firstPlayer = new Player("human", 1, false, playerTurn);
                Player secondPlayer = new Player("human", 2, false, !playerTurn);
                setPlayers(firstPlayer, secondPlayer);
                JOptionPane.showMessageDialog(parent.getGuiFrame(), "Human vs Human selected");
                System.out.println("Player vs Player");
                repaint();
            }
        } else if (e.getSource() == randomPlayerMI) {
            if (!gameStart) {
                agentMode = "random";
                initialize();
                Player firstPlayer = new Player("human", 1, false, playerTurn);
                Player secondPlayer = new Player("random", 2, false, !playerTurn);
                setPlayers(firstPlayer, secondPlayer);
                JOptionPane.showMessageDialog(parent.getGuiFrame(), "Human vs Random selected");
                System.out.println("Player vs Random");
                repaint();
            }
        } else if (e.getSource() == miniMaxPlayerMI) {
            if (!gameStart) {
                agentMode = "mini-max";
                initialize();
                Player firstPlayer = new Player("human", 1, false, playerTurn);
                Player secondPlayer = new Player("minimax", 2, true, !playerTurn);
                secondPlayer.setPlayers(secondPlayer, firstPlayer);
                setPlayers(firstPlayer, secondPlayer);
                JOptionPane.showMessageDialog(parent.getGuiFrame(), "Human vs Minimax selected");
                System.out.println("Player vs MiniMax");
                repaint();
            }
        } else if (e.getSource() == playerPrunedMI) {
            if (!gameStart) {
                initialize();
                Player firstPlayer = new Player("human", 1, false, playerTurn);
                Player secondPlayer = new Player("pruned", 2, true, !playerTurn);
                secondPlayer.setPlayers(secondPlayer, firstPlayer);
                setPlayers(firstPlayer, secondPlayer);
                JOptionPane.showMessageDialog(parent.getGuiFrame(), "Human vs Pruned selected");
                System.out.println("Player vs Pruned");
                repaint();
            }
        } else if (e.getSource() == randomRandomMI) {
            if (!gameStart) {
                initialize();
                Player firstPlayer = new Player("random", 1, false, playerTurn);
                Player secondPlayer = new Player("random", 2, false, !playerTurn);
                setPlayers(firstPlayer, secondPlayer);
                onlyAI = true;
                JOptionPane.showMessageDialog(parent.getGuiFrame(), "Random vs Random selected");
                System.out.println("Random vs Random");
                repaint();
            }
        } else if (e.getSource() == randomMiniMI) {
            if (!gameStart) {
                initialize();
                Player firstPlayer = new Player("random", 1, false, playerTurn);
                Player secondPlayer = new Player("minimax", 2, true, !playerTurn);
                secondPlayer.setPlayers(secondPlayer, firstPlayer);
                setPlayers(firstPlayer, secondPlayer);
                onlyAI = true;
                JOptionPane.showMessageDialog(parent.getGuiFrame(), "Random vs Minimax selected");
                System.out.println("Random vs MiniMax");
                repaint();
            }
        } else if (e.getSource() == randomPrunedMI) {
            if (!gameStart) {
                initialize();
                Player firstPlayer = new Player("random", 1, false, playerTurn);
                Player secondPlayer = new Player("pruned", 2, true, !playerTurn);
                secondPlayer.setPlayers(secondPlayer, firstPlayer);
                setPlayers(firstPlayer, secondPlayer);
                onlyAI = true;
                JOptionPane.showMessageDialog(parent.getGuiFrame(), "Random vs Pruned selected");
                System.out.println("Random vs Pruned");
                repaint();
            }
        } else if (e.getSource() == miniMiniMI) {
            if (!gameStart) {
                initialize();
                Player firstPlayer = new Player("minimax", 1, true, playerTurn);
                Player secondPlayer = new Player("minimax", 2, true, !playerTurn);

                firstPlayer.setPlayers(firstPlayer, secondPlayer);
                secondPlayer.setPlayers(secondPlayer, firstPlayer);
                setPlayers(firstPlayer, secondPlayer);
                onlyAI = true;
                JOptionPane.showMessageDialog(parent.getGuiFrame(), "Minimax vs Minimax selected");
                System.out.println("MiniMax vs MiniMax");
                repaint();
            }
        } else if (e.getSource() == miniPrunedMI) {
            if (!gameStart) {
                initialize();
                Player firstPlayer = new Player("minimax", 1, true, playerTurn);
                Player secondPlayer = new Player("pruned", 2, true, !playerTurn);

                firstPlayer.setPlayers(firstPlayer, secondPlayer);
                secondPlayer.setPlayers(secondPlayer, firstPlayer);
                setPlayers(firstPlayer, secondPlayer);
                onlyAI = true;
                JOptionPane.showMessageDialog(parent.getGuiFrame(), "Minimax vs Pruned selected");
                System.out.println("MiniMax vs Pruned");
                repaint();
            }
        } else if (e.getSource() == prunedPrunedMI) {
            if (!gameStart) {
                initialize();
                Player firstPlayer = new Player("pruned", 1, true, playerTurn);
                Player secondPlayer = new Player("pruned", 2, true, !playerTurn);

                firstPlayer.setPlayers(firstPlayer, secondPlayer);
                secondPlayer.setPlayers(secondPlayer, firstPlayer);
                setPlayers(firstPlayer, secondPlayer);
                onlyAI = true;
                JOptionPane.showMessageDialog(parent.getGuiFrame(), "Pruned vs Pruned selected");
                System.out.println("Pruned vs Pruned");
                repaint();
            }
        }
    }


    public void setBtnStatus(boolean status){
        for(Button btn : btnList)
            btn.setEnabled(status);
    }


    public void setParent(Connect4 parent){
        this.parent = parent;
    }


} // class