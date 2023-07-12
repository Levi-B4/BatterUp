import java.io.IOException;

import javax.swing.JFrame;

public class BatterUpGUI extends JFrame{
    BatterUp game = new BatterUp();
    
    public BatterUpGUI() throws IOException{
        game.Play(9);
    }
}