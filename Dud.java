import java.util.Random;

public class Dud extends Player{
    public Dud(String name, Base base) {
        super(name, base);
    }
    //roll two 10 sided dicezs
    public RollResult roll(){
        int[] vals = new int[2];
        Random rand = new Random();
        //get random 2 random values 1-10 and return them.
        vals[0] = rand.nextInt(10) + 1;
        vals[1] = rand.nextInt(10) + 1;
        String str = String.format(" Rolled  %s %s  ",vals[0], vals[1]);
        return new RollResult(vals, str);
    }
}