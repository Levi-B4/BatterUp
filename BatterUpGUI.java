import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//GUI container for game
public class BatterUpGUI extends JFrame{
    private int numberOfInnings;

    //Logic
    private BatterUp game = new BatterUp();
    String playOutput = "";
    
    //GUI Components
    private JLabel NumberOfInningsLabel;
    private JTextField numberOfInningsTextField;

    private JLabel teamNameLabel;
    private JTextField teamNameTextField;

    private JButton playButton;
    private JButton resetButton;

    private JTextArea gameOutput;
    private JScrollPane scrollPane;

    private JPanel errorMessagePanel;

    //component functions
    private ActionListener startButtonActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent startButtonActionEvent){
            boolean validInput = false;
            //if text fields empty, notify user
            if(numberOfInningsTextField.getText().equals("")){
                SendDataMissingErrorMessage("the number of innings");
            } else if(teamNameTextField.getText().equals("")){
                SendDataMissingErrorMessage("a team name");
            } else{
                try {   //check validity of input
                    numberOfInnings = Integer.parseInt(numberOfInningsTextField.getText());
                    validInput = true;
                } catch (NumberFormatException numberFormatError) {
                    SendDataMissingErrorMessage("a number for innings");
                    validInput = false;
                }
                if(validInput){ //if inputs are entered and valid, run game logic
                    ToggleEnableableComponentsForBatterUp();
                    gameOutput.append(teamNameTextField.getText() + "is playing!\n");
                    try {
                        playOutput = game.Play(numberOfInnings);
                    } catch (IOException e) {
                        ToggleEnableableComponentsForBatterUp();
                        SendFileMissingErrorMessage("Players.txt");
                    }
                    gameOutput.append(playOutput);  //output results of game logic
                }
            }
        }
    };
    //handler for reset button interactions
    private ActionListener resetButtonActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent resetButtonActionEvent){
            ToggleEnableableComponentsForBatterUp();
            ClearGUIText();
        }
    };
    //construct GUI
    public BatterUpGUI(){
        setTitle("Batter up!");

        setLayout(new FlowLayout());
        
        //set components
        NumberOfInningsLabel = new JLabel("Number of Innings");
        numberOfInningsTextField = new JTextField(20);

        teamNameLabel = new JLabel("Team Name");
        teamNameTextField = new JTextField(20);

        playButton = new JButton("Play");
        resetButton = new JButton("Reset");
        resetButton.setEnabled(false);

        //setup output
        gameOutput = new JTextArea(20,50);
        scrollPane = new JScrollPane(gameOutput);
        gameOutput.setEditable(false);

        //setup error message panel
        errorMessagePanel = new JPanel(new FlowLayout());

        //add components to container
        add(NumberOfInningsLabel);
        add(numberOfInningsTextField);
        add(teamNameLabel);
        add(teamNameTextField);
        add(playButton);
        add(resetButton);
        add(scrollPane);

        //add component functions
        playButton.addActionListener(startButtonActionListener);
        resetButton.addActionListener(resetButtonActionListener);
    }
    //sends error message for a missing piece of data
    public void SendDataMissingErrorMessage(String missingData){
        JOptionPane.showMessageDialog(errorMessagePanel, "Please enter " + missingData,
                    "Missing Data", JOptionPane.ERROR_MESSAGE);
    }
    //sends error message for a missing file
    public void SendFileMissingErrorMessage(String missingFile){
        JOptionPane.showMessageDialog(errorMessagePanel, "The file " + missingFile + " could not be found",
                    "Missing File", JOptionPane.ERROR_MESSAGE);
    }
    //toggles toggle enabled components, for when the game is in session or not in session
    private void ToggleEnableableComponentsForBatterUp(){
        numberOfInningsTextField.setEnabled(!numberOfInningsTextField.isEnabled());
        teamNameTextField.setEnabled(!teamNameTextField.isEnabled());
        playButton.setEnabled(!playButton.isEnabled());
        resetButton.setEnabled(!resetButton.isEnabled());
    }
    //clears GUI text so that game can be ran again
    private void ClearGUIText(){
        numberOfInningsTextField.setText("");
        teamNameTextField.setText("");
        gameOutput.setText("");
    }
}