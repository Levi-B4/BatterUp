//used to hold random results when rolling dice for each at bat
public class RollResult {
    private int[] vals;
    private String output;
    
    public RollResult(int[] vals, String output) {
        this.vals = vals;
        this.output = output;
    }
//getters and setters
    public int[] getVals(){
        return vals;
    }

    public void setVals(int[] vals){
        this.vals = vals;
    }

    public String getOutput(){
        return output;
    }

    public void setOutput(String output){
        this.output = output;
    }
}