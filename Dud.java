import java.util.Random;

public class Dud extends Player{
    public Dud(String name, Base base) {
        super(name, base);
    }

    public RollResult roll(){
        String str = "";
        Random rand = new Random();
        
        //simulate two dice rolls
        int dice1 = rand.nextInt(3) + 1;
        int dice2 = rand.nextInt(10) + 1;
        
        str += String.format("  Rolled: %d", dice1);
        str += String.format(" %d    ", dice2);
        
        return new RollResult(new int[]{dice1, dice2}, str);
    }
}