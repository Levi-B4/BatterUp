import java.util.Random;

public class Player {
    
    private String name;
    private Base location;
    private int strikes;
    private int balls;
    private int hits;
    private int atBats;

    public Player(String name, Base location){
        this.name = name;
        //location should be dugout when instantiating
        this.location = location;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Base getLocation(){
        return location;
    }

    public void setLocation(Base location){
        this.location = location;
    }

    public int getHits(){
        return hits;
    }

    public void setHits(int hits){
        this.hits = hits;
    }

    public int getAtBats(){
        return atBats;
    }

    public void setAtBats(int atBats){
        this.atBats = atBats;
    }

    public boolean isNotInDugout(){
        return !location.isDugout();
    }

    public int takeTurn(){
        atBats++;
        strikes = 0;
        balls = 0;
        
        int batterBatResult;
        int currentBalls;
        while(strikes < 3 && balls < 4){
            currentBalls = balls;
            batterBatResult = bat();
            if(batterBatResult > 0){
                if(currentBalls == balls){
                    hits++;
                }
                return batterBatResult;
            }
        }
    
        //walk or strikeout
        if(balls == 4){
            System.out.println("  WALK");
            return 1;
        } else{
            System.out.println("  STRIKEOUT");
            return 0;
        }
    }

    public int bat(){
        //simulate dice rolls
        int[] rolls = roll();

        if(rolls[0] == rolls[1] && rolls[0] <= 4){
            switch (rolls[0]) {
                case 1:
                    System.out.println("SINGLE");
                    break;

                case 2:
                    System.out.println("DOUBLE");
                    break;

                case 3:
                    System.out.println("TRIPPLE");
                    break;
            
                default:
                System.out.println("HOMERUN");
                    break;
            }
            return rolls[0];
        } else{
            if((rolls[0] + rolls[1] )% 2 == 0){
                strikes++;
                System.out.println("STRIKE");
            } else{
                System.out.println("BALL");
                balls++;
            }
            return 0;
        }
    }

    public int[] roll(){
        Random rand = new Random();
        
        //simulate two dice rolls
        int dice1 = rand.nextInt(6) + 1;
        int dice2 = rand.nextInt(6) + 1;
        
        System.out.printf("  Rolled: %d", dice1);
        System.out.printf(" %d    ", dice2);
        
        return new int[]{dice1, dice2};
    }

    public double getBattingAverage(){
        if(atBats == 0){
            return 0;
        } else{
            return (double)hits/(double)atBats;
        }
    }

    public String toString(){
        return name;
    }
}
