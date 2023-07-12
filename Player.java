import java.util.Random;

public class Player {
    
    private String name;
    private String output;
    private Base location;
    private int strikes;
    private int balls;
    private int hits;
    private int atBats;

    public Player(String name, Base location){
        this.name = name;
        this.output = "";
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
            output += "WALK";
            return 1;
        } else{
            output += "STRIKEOUT";
            return 0;
        }
    }

    public int bat(){
        //simulate dice rolls
        RollResult rolls = roll();

        if(rolls.getVals()[0] == rolls.getVals()[1] && rolls.getVals()[0] <= 4){
            switch (rolls.getVals()[0]) {
                case 1:
                    output += rolls.getOutput() + "SINGLE!\n";
                    break;

                case 2:
                    output += rolls.getOutput() + "DOUBLE!\n";
                    break;

                case 3:
                    output += rolls.getOutput() + "TRIPPLE!\n";
                    break;
            
                default:
                output += rolls.getOutput() + "HOMERUN!\n";
                    break;
            }
            return rolls.getVals()[0];
        } else{
            if((rolls.getVals()[0] + rolls.getVals()[1] )% 2 == 0){
                strikes++;
                output += rolls.getOutput() + "STRIKE!\n";
            } else{
                output += rolls.getOutput() + "BALL!\n";
                balls++;
            }
            return 0;
        }
    }

    public RollResult roll(){
        int[] vals = new int[2];
        Random rand = new Random();
        vals[0] = rand.nextInt(6) + 1;
        vals[1] = rand.nextInt(6) + 1;
        String str = " Rolled " + vals[0] + " " + vals[1];
        return new RollResult(vals, str);
    }

    public double getBattingAverage(){
        if(atBats == 0){
            return 0;
        } else{
            return (double)hits/(double)atBats;
        }
    }

    public int getStrikes(){
        return strikes;
    }

    public int getBalls(){
        return balls;
    }

    public String getOutput(){
        return output;
    }

    public void resetOutput(){
        this.output = "";
    }

    public String toString(){
        return name;
    }
}
