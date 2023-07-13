import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUILauncher{
    public static void main(String[] args) throws IOException{
        BatterUpGUI theGame = new BatterUpGUI();
        theGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        theGame.setSize(650,450);
        theGame.setResizable(false);
        theGame.setVisible(true);
    }
}