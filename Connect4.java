import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Connect4 extends Component {

        public static Connect4JFrame  guiFrame;
        public boolean                simulation;
        public String                 outputFilePath;
        public String                 winnerOutput = "";
        public int                    simulationCounter = 1;

        public Connect4(Connect4JFrame gui){
                setGuiFrame(gui);
                setParent();
        }

        public static void main(String[] args) {
                guiFrame = new Connect4JFrame();
                guiFrame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
                guiFrame.setVisible(true);
                Connect4 game = new Connect4(guiFrame);
        }

        public void setParent(){
                guiFrame.setParent(this);
        }

        public void newGame(Player firstPlayer, Player secondPlayer, boolean simulation){
                setPlayers(firstPlayer, secondPlayer);
                guiFrame.board.simulation = simulation;
                guiFrame.gameStart = true;
                setSimulation(simulation);
                if(simulation)
                        guiFrame.gameTurnAuto();
        }

        public void endGame(int winner, int RED, int totalMoves){
                if(!simulation){
                        if(winner == RED)
                                JOptionPane.showMessageDialog(guiFrame, "Red wins");
                        else
                                JOptionPane.showMessageDialog(guiFrame, "Yellow wins");
                        System.out.println("Finished game");
                        System.out.println("Total moves done: " + totalMoves);
                }
                else{
                        System.out.println("Finished game");
                        String result;
                        if(winner == RED)
                                result = simulationCounter + ") " + "Red player wins!";
                        else
                                result = simulationCounter + ") " + "Yellow player wins!";
                        winnerOutput = winnerOutput.concat(result + "\n");
                }
                guiFrame.end=true;

        }

        public void setPlayers(Player firstPlay, Player secondPlay){
                guiFrame.firstPlayer = firstPlay;
                guiFrame.secondPlayer = secondPlay;
                guiFrame.board.setFirstPlayer(firstPlay);
                guiFrame.board.setSecondPlayer(secondPlay);
        }

        public void simulationsGames(int simNum, String firstPlayerType, String secondPlayerType){
                String fileName = "output" + firstPlayerType + secondPlayerType;
                Player firstPlayer = new Player(firstPlayerType.toLowerCase(), 1, true);
                Player secondPlayer = new Player(secondPlayerType.toLowerCase(), 2, true);

                firstPlayer.setPlayers(firstPlayer, secondPlayer);
                secondPlayer.setPlayers(secondPlayer, firstPlayer);

                this.outputFilePath = System.getProperty("user.dir") + "/" + fileName + ".txt";
                System.out.println("Path: " + outputFilePath);
                System.out.println("Started simulations");
                for(int i = 1; i <= simNum; i++){
                        newGame(firstPlayer, secondPlayer, true);
                        simulationCounter++;
                        guiFrame.initialize();
                        System.out.println("Simulation " + i + " done!");

                }
                System.out.println("Winner output: " + winnerOutput);
                saveToFile();
                simulationCounter = 1;
                winnerOutput = "";
                JOptionPane.showMessageDialog(guiFrame,"Finished simulations");

        }

        public void saveToFile(){
                System.out.println("Saving to file");
                try{
                        File outputFile = new File(outputFilePath);
                        if(!outputFile.exists()){
                                System.out.println("We had to make a new file.");
                                outputFile.createNewFile();
                        }

                        PrintWriter out = new PrintWriter(new FileWriter(outputFile, true));
                        out.append(winnerOutput);
                        out.close();
                }catch(IOException e){
                        System.out.println("Could not save!");
                }

        }

        public void setSimulation(boolean simulation) {
                this.simulation = simulation;
        }

        public void setGuiFrame(Connect4JFrame gui){
                guiFrame = gui;
        }

        public static Connect4JFrame getGuiFrame() {
                return guiFrame;
        }

}

