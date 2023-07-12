import java.util.ArrayList;
import java.util.Arrays;

public class Field {
    private ArrayList<Base> bases;

    public Field(){
        bases = new ArrayList<Base>(Arrays.asList(
            new Base("Dugout"),
            new Base("BatterBox"),
            new Base("First"),
            new Base("Second"),
            new Base("Third"),
            new Base("Home")
        ));
    }

    public Base getDugout(){
        return bases.get(0);
    }

    public Base getBatterBox(){
        return bases.get(1);
    }

    public Base moveAhead(Base startingBase, int numberOfBases){
        int startingBaseIndexNumber = bases.indexOf(startingBase);
        //move ahead number of bases, but staying withing the range of bases
        int newBaseIndexNumber = Math.min(startingBaseIndexNumber + numberOfBases, 5);
        Base newBase = bases.get(newBaseIndexNumber);
        
        return newBase;
    }
}
