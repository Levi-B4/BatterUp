import java.io.IOException;
import javax.swing.JFrame;

//Entry Point
public class GUILauncher{
    public static void main(String[] args) throws IOException{
        //Initialize Game GUI
        BatterUpGUI theGame = new BatterUpGUI();
        //setup GUI
        theGame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        theGame.setSize(650,450);
        theGame.setResizable(false);
        theGame.setVisible(true);
    }
}