import javax.swing.*;
import java.awt.*;

public class MovesPanel extends Panel {

    JLabel redMovesLabel;
    JLabel yellowMovesLabel;
    JLabel totalMovesLabel;

    public MovesPanel(int REDMOVES, int YELLOWMOVES, int TOTALMOVES){
        Box box = Box.createVerticalBox();

        this.redMovesLabel = new JLabel("Red moves: " + REDMOVES, JLabel.LEFT);
        this.yellowMovesLabel = new JLabel("Yellow moves: " + YELLOWMOVES, JLabel.LEFT);
        this.totalMovesLabel = new JLabel("Total moves: " + TOTALMOVES, JLabel.LEFT);

        box.add(redMovesLabel);
        box.add(yellowMovesLabel);
        box.add(totalMovesLabel);
        this.add(box);
    }

    public void updateLabel(boolean color, int REDMOVES, int YELLOWMOVES, int TOTALMOVES){
        // Red player
        if(!color){
            redMovesLabel.setText("Red moves: " + REDMOVES);
        }
        // Yellow player
        else{
            yellowMovesLabel.setText("Yellow moves: " + YELLOWMOVES);
        }
        totalMovesLabel.setText("Total moves: " + TOTALMOVES);
    }
}
