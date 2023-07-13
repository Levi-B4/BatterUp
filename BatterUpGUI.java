import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.FlowLayout;

public class BatterUpGUI extends JFrame{
    //Logic
    private BatterUp game = new BatterUp();
    
    //GUI Components
    private JLabel inningsLabel;
    private JTextField inningsTextField;

    private JLabel teamNameLabel;
    private JTextField teamNameTextField;

    private JButton startButton;
    private JButton resetButton;

    private JTextArea gameOutput;
    private JScrollPane scrollPane;

    
    public BatterUpGUI() throws IOException{
        setTitle("Batter up!");

        setLayout(new FlowLayout());
        
        //set components
        inningsLabel = new JLabel("Number of Innings");
        inningsTextField = new JTextField(20);

        teamNameLabel = new JLabel("Team Name");
        teamNameTextField = new JTextField(20);

        startButton = new JButton("Start");
        resetButton = new JButton("Reset");

        //setup output
        gameOutput = new JTextArea(20,50);
        scrollPane = new JScrollPane(gameOutput);
        gameOutput.setEditable(false);

        //add components to container
        add(inningsLabel);
        add(inningsTextField);
        add(teamNameLabel);
        add(teamNameTextField);
        add(startButton);
        add(resetButton);
        add(scrollPane);

        game.Play(9);
    }
}