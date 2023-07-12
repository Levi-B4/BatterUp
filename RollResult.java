public class RollResult {
    private int[] vals;
    private String output;
    
    public RollResult(int[] vals, String output) {
        this.vals = vals;
        this.output = output;
    }

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