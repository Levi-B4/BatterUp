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
//getters and setters
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

    public int getStrikes(){
        return strikes;
    }

    public int getBalls(){
        return balls;
    }

    public String getOutput(){
        return output;
    }

//check if player is in dugout
    public boolean isNotInDugout(){
        return !location.isDugout();
    }
//simulate player going up to bat
    public int takeTurn(){
        atBats++;
        strikes = 0;
        balls = 0;
        //player bats until they strikeout, hit, or walk
        int batterBatResult;
        do{
            batterBatResult = bat();
            if(batterBatResult > 0){
                hits ++;
                break;
            }
        }while(strikes < 3 && balls < 4);
    
        //return batting result
        if(balls == 4){
            output += "    WALK\n";
            return 1;
        } else if(strikes == 3){
            output += "    STRIKEOUT\n";
            return 0;
        } else{
            return batterBatResult;
        }
    }

    public int bat(){
        //simulate dice rolls
        RollResult rolls = roll();

        if(rolls.getVals()[0] == rolls.getVals()[1] && rolls.getVals()[0] <= 4){    //counts as hit if dice are the same and 4 or less
            switch (rolls.getVals()[0]) {
                case 1:
                    output += String.format("%sSINGLE!\n", rolls.getOutput());
                    break;

                case 2:
                    output += String.format("%sDOUBLE!\n", rolls.getOutput());
                    break;

                case 3:
                    output += String.format("%sTRIPPLE!\n", rolls.getOutput());
                    break;
            
                default:
                output += String.format("%sHOMERUN!\n", rolls.getOutput());
                    break;
            }
            return rolls.getVals()[0];
        } else{ //an even sum is a strike and odd sum is a ball
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
//roll two 6 sided dice
    public RollResult roll(){
        int[] vals = new int[2];
        Random rand = new Random();
        vals[0] = rand.nextInt(6) + 1;
        vals[1] = rand.nextInt(6) + 1;
        String str = String.format(" Rolled  %s %s  ",vals[0], vals[1]);
        return new RollResult(vals, str);
    }
//return percent of hits per times at bat
    public double getBattingAverage(){
        if(atBats == 0){
            return 0;
        } else{
            return (double)hits/(double)atBats;
        }
    }
//resets output so it can be reused
    public void resetOutput(){
        this.output = "";
    }

    public String toString(){
        return name;
    }
}
