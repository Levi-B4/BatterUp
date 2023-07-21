import java.util.Random;

public class Ringer extends Player {
    public Ringer(String name, Base base) {
        super(name, base);
    }
//roll two 3 sided dice
    public RollResult roll(){
        int[] vals = new int[2];
        Random rand = new Random();
        // two random values 1-3
        vals[0] = rand.nextInt(3) + 1;
        vals[1] = rand.nextInt(3) + 1;
        String str = String.format(" Rolled  %s %s  ",vals[0], vals[1]);
        return new RollResult(vals, str);
    }
}
