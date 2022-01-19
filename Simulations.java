import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

public class Simulations extends JFrame{

    public static final String[] AIModes = { "Random", "MiniMax", "Pruned" };
    public Connect4 ref;
    private String firstPlayerSim = "random";
    private String secondPlayerSim = "random";

    public Simulations(Connect4 parent){
        super("Simulations options");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        setSize(512, 334);
        setLocationRelativeTo(null);
        setParent(parent);

        JPanel jPane = new JPanel();
        jPane.setLayout(new BoxLayout(jPane, BoxLayout.PAGE_AXIS));

        JPanel comboBoxPanel = new JPanel();
        comboBoxPanel.setLayout(new BoxLayout(comboBoxPanel, BoxLayout.LINE_AXIS));

        JComboBox<String> firstPlayerBox = new JComboBox<>(AIModes);
        firstPlayerBox.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED) {
                Object item = e.getItem();
                setFirstPlayerSim((String)item);
                System.out.println("First player selected: " + getFirstPlayerSim());
            }
        });

        JComboBox<String> secondPlayerBox = new JComboBox<>(AIModes);
        secondPlayerBox.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED) {
                Object item = e.getItem();
                setSecondPlayerSim((String)item);
                System.out.println("Second player selected: " + getSecondPlayerSim());
            }
        });
        comboBoxPanel.add(firstPlayerBox);
        comboBoxPanel.add(secondPlayerBox);

        JPanel btnPanel = new JPanel();
        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(e -> getRef().dispose());
        JButton confirmBtn = new JButton("Confirm");



        jPane.add(comboBoxPanel);
        jPane.add(btnPanel);
        add(jPane, BorderLayout.CENTER);
        confirmBtn.addActionListener(e -> {
            System.out.println("first : " + getFirstPlayerSim());
            System.out.println("second : " + getSecondPlayerSim());
            ref.simulationsGames(100, getFirstPlayerSim(), getSecondPlayerSim());
        });


        btnPanel.add(cancelBtn);
        btnPanel.add(confirmBtn);

    }

    public JFrame getRef(){
        return this;
    }

    public void setParent(Connect4 parent){
        this.ref = parent;
    }

    public void setFirstPlayerSim(String firstPlayerSim) {
        this.firstPlayerSim = firstPlayerSim;
    }

    public void setSecondPlayerSim(String secondPlayerSim) {
        this.secondPlayerSim = secondPlayerSim;
    }

    public String getFirstPlayerSim() {
        return firstPlayerSim;
    }

    public String getSecondPlayerSim() {
        return secondPlayerSim;
    }

}
