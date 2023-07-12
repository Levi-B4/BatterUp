import java.io.IOException;

public class BatterUpGUI {
    BatterUp game = new BatterUp();
    
    public BatterUpGUI() throws IOException{
        game.Play(9);
    }
}
