import javax.swing.*;
import java.awt.*;

public class MovesPanel extends Panel {

    JLabel redMovesLabel;
    JLabel yellowMovesLabel;
    JLabel totalMovesLabel;
    JLabel timeElapsed;

    public MovesPanel(int REDMOVES, int YELLOWMOVES, int TOTALMOVES, double TIMEELAPSED){
        Box box = Box.createVerticalBox();

        this.redMovesLabel = new JLabel("Red moves: " + REDMOVES, JLabel.LEFT);
        this.yellowMovesLabel = new JLabel("Yellow moves: " + YELLOWMOVES, JLabel.LEFT);
        this.totalMovesLabel = new JLabel("Total moves: " + TOTALMOVES, JLabel.LEFT);

        box.add(redMovesLabel);
        box.add(yellowMovesLabel);
        box.add(totalMovesLabel);
                this.timeElapsed = new JLabel("Time elapsed: " + TIMEELAPSED, JLabel.LEFT);
                box.add(timeElapsed);

        this.add(box);
    }

    public void updateLabel(boolean color, int REDMOVES, int YELLOWMOVES, int TOTALMOVES, double TIMEELAPSED){
        // Red player
        if(!color){
            redMovesLabel.setText("Red moves: " + REDMOVES);
        }
        // Yellow player
        else{
            yellowMovesLabel.setText("Yellow moves: " + YELLOWMOVES);
        }
        totalMovesLabel.setText("Total moves: " + TOTALMOVES);
        if(TIMEELAPSED > 0)
            timeElapsed.setText("Time elasped: " + TIMEELAPSED);
    }

    public void resetLabels(){
        redMovesLabel.setText("Red moves: " + 0);
        yellowMovesLabel.setText("Yellow moves: " + 0);
        totalMovesLabel.setText("Total moves: " + 0);
        timeElapsed.setText("Time elasped: " + 0.0);
    }
}
