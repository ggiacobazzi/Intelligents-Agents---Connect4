import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.*;

public class Connect4 {

        /**
         *       Program:        Connect4.java
         *       Purpose:        Stacking disk game for 2 players
         *       Creator:        Chris Clarke
         *       Created:        19.08.2007
         *       Modified:       29.11.2012 (JFrame)
         */

        public static void main(String[] args) {
                Connect4JFrame frame = new Connect4JFrame();
                frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
        }
}

class Connect4JFrame extends JFrame implements ActionListener {

        private final Button          btn1;
        private final Button btn2;
        private final Button btn3;
        private final Button btn4;
        private final Button btn5;
        private final Button btn6;
        private final Button btn7;
        private Label           lblSpacer;
        private final MovesPanel      movesPanel;
        MenuItem                newMI, exitMI, redMI, yellowMI, playerMI, randomPlayerMI;
        int[][]                 theArray;
        boolean                 end=false;
        boolean                 gameStart;
        boolean                 agent=false;
        public static String    agentMode;
        public static final int BLANK = 0;
        public static final int RED = 1;
        public static final int YELLOW = 2;

        public static final int MAXROW = 6;     // 6 rows
        public static final int MAXCOL = 7;     // 7 columns

        public static final String SPACE = "                  "; // 18 spaces

        int activeColour = RED;

        // Counters for RED/YELLOW players' moves updated throughout the game
        public static int REDMOVES;
        public static int YELLOWMOVES;
        public static int TOTALMOVES;

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
                mbar.add(optMenu);
                Menu playerMenu = new Menu("Player");
                playerMI = new MenuItem("Normal");
                playerMI.addActionListener(this);
                playerMenu.add(playerMI);
                randomPlayerMI = new MenuItem("Random");
                randomPlayerMI.addActionListener(this);
                playerMenu.add(randomPlayerMI);
                mbar.add(playerMenu);
                setMenuBar(mbar);

                // Build control panel.
                Panel panel = new Panel();

                btn1 = new Button("1");
                btn1.addActionListener(this);
                panel.add(btn1);
                lblSpacer = new Label(SPACE);
                panel.add(lblSpacer);

                btn2 = new Button("2");
                btn2.addActionListener(this);
                panel.add(btn2);
                lblSpacer = new Label(SPACE);
                panel.add(lblSpacer);

                btn3 = new Button("3");
                btn3.addActionListener(this);
                panel.add(btn3);
                lblSpacer = new Label(SPACE);
                panel.add(lblSpacer);

                btn4 = new Button("4");
                btn4.addActionListener(this);
                panel.add(btn4);
                lblSpacer = new Label(SPACE);
                panel.add(lblSpacer);

                btn5 = new Button("5");
                btn5.addActionListener(this);
                panel.add(btn5);
                lblSpacer = new Label(SPACE);
                panel.add(lblSpacer);

                btn6 = new Button("6");
                btn6.addActionListener(this);
                panel.add(btn6);
                lblSpacer = new Label(SPACE);
                panel.add(lblSpacer);

                btn7 = new Button("7");
                btn7.addActionListener(this);
                panel.add(btn7);

                initialize();

                this.movesPanel = new MovesPanel(REDMOVES,YELLOWMOVES,TOTALMOVES);

                add(panel, BorderLayout.NORTH);
                add(this.movesPanel, BorderLayout.SOUTH);

                // Set to a reasonable size.
                setSize(1024, 768);
        } // Connect4

        public void initialize() {
                theArray=new int[MAXROW][MAXCOL];
                for (int row=0; row<MAXROW; row++)
                        for (int col=0; col<MAXCOL; col++)
                                theArray[row][col]=BLANK;
                gameStart=false;
                REDMOVES = 0;
                YELLOWMOVES = 0;
                TOTALMOVES = 0;
        } // initialize

        public void paint(Graphics g) {
                g.setColor(Color.BLUE);
                g.fillRect(110, 50, 100+100*MAXCOL, 100+100*MAXROW);
                for (int row=0; row<MAXROW; row++)
                        for (int col=0; col<MAXCOL; col++) {
                                if (theArray[row][col]==BLANK) g.setColor(Color.WHITE);
                                if (theArray[row][col]==RED) g.setColor(Color.RED);
                                if (theArray[row][col]==YELLOW) g.setColor(Color.YELLOW);
                                g.fillOval(160+100*col, 100+100*row, 100, 100);
                        }
                check4(g);
        } // paint

        public void putDisk(int n) {
                // put a disk on top of column n
                // if game is won, do nothing
                if (end) return;
                gameStart=true;
                int row;
                n--;
                for (row=0; row<MAXROW; row++)
                        if (theArray[row][n]>0) break;
                if (row>0) {
                        theArray[--row][n]=activeColour;
                        if (activeColour==RED) {
                                REDMOVES++;
                                System.out.println("Red Moves: " + REDMOVES);
                                activeColour = YELLOW;
                                TOTALMOVES++;
                                this.movesPanel.updateLabel(false,REDMOVES,YELLOWMOVES,TOTALMOVES);
                        }else {
                                YELLOWMOVES++;
                                System.out.println("Yellow Moves: " + YELLOWMOVES);
                                activeColour = RED;
                                TOTALMOVES++;
                                this.movesPanel.updateLabel(true,REDMOVES,YELLOWMOVES,TOTALMOVES);
                        }
                        repaint();
                }
        }

        public void displayWinner(Graphics g, int n) {
                g.setColor(Color.BLACK);
                g.setFont(new Font("Courier", Font.BOLD, 100));
                if (n==RED)
                        g.drawString("Red wins!", 100, 400);
                else
                        g.drawString("Yellow wins!", 100, 400);
                System.out.println("Total moves done: " + TOTALMOVES);
                end=true;
        }

        public void check4(Graphics g) {
                // see if there are 4 disks in a row: horizontal, vertical or diagonal
                // horizontal rows
                for (int row=0; row<MAXROW; row++) {
                        for (int col=0; col<MAXCOL-3; col++) {
                                int curr = theArray[row][col];
                                if (curr>0
                                        && curr == theArray[row][col+1]
                                        && curr == theArray[row][col+2]
                                        && curr == theArray[row][col+3]) {
                                        displayWinner(g, theArray[row][col]);
                                }
                        }
                }
                // vertical columns
                for (int col=0; col<MAXCOL; col++) {
                        for (int row=0; row<MAXROW-3; row++) {
                                int curr = theArray[row][col];
                                if (curr>0
                                        && curr == theArray[row+1][col]
                                        && curr == theArray[row+2][col]
                                        && curr == theArray[row+3][col])
                                        displayWinner(g, theArray[row][col]);
                        }
                }
                // diagonal lower left to upper right
                for (int row=0; row<MAXROW-3; row++) {
                        for (int col=0; col<MAXCOL-3; col++) {
                                int curr = theArray[row][col];
                                if (curr>0
                                        && curr == theArray[row+1][col+1]
                                        && curr == theArray[row+2][col+2]
                                        && curr == theArray[row+3][col+3])
                                        displayWinner(g, theArray[row][col]);
                        }
                }
                // diagonal upper left to lower right
                for (int row=MAXROW-1; row>=3; row--) {
                        for (int col=0; col<MAXCOL-3; col++) {
                                int curr = theArray[row][col];
                                if (curr>0
                                        && curr == theArray[row-1][col+1]
                                        && curr == theArray[row-2][col+2]
                                        && curr == theArray[row-3][col+3])
                                        displayWinner(g, theArray[row][col]);
                        }
                }
        } // end check4

        public void actionPerformed(ActionEvent e) {
                if (e.getSource() == btn1)
                        putDisk(1);
                else if (e.getSource() == btn2)
                        putDisk(2);
                else if (e.getSource() == btn3)
                        putDisk(3);
                else if (e.getSource() == btn4)
                        putDisk(4);
                else if (e.getSource() == btn5)
                        putDisk(5);
                else if (e.getSource() == btn6)
                        putDisk(6);
                else if (e.getSource() == btn7)
                        putDisk(7);
                else if (e.getSource() == newMI) {
                        end=false;
                        initialize();
                        repaint();
                } else if (e.getSource() == exitMI) {
                        System.exit(0);
                } else if (e.getSource() == redMI) {
                        // don't change colour to play in middle of game
                        if (!gameStart) activeColour=RED;
                } else if (e.getSource() == yellowMI) {
                        if (!gameStart) activeColour=YELLOW;
                } else if(e.getSource() == playerMI){
                        if(!gameStart){
                                System.out.println("Player playing");
                        }
                } else if(e.getSource() == randomPlayerMI){
                        if(!gameStart){
                                this.agent = true;
                                agentMode = "random";
                                System.out.println("Random player playing");
                        }
                }

                if(this.agent){
                        agentPlaying();
                }
        } // end ActionPerformed

        public void agentPlaying(){
                switch (agentMode){
                        case "random":{
                                int nextMove = ThreadLocalRandom.current().nextInt(1, 8);
                                putDisk(nextMove);
                        }
                        case "min-max":{
                                //
                        }
                        case "min-max_a-b":{

                        }
                }
        }
} // class