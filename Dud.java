public class Dud extends Player{
    public Dud(String name, Base base) {
        super(name, base);
    }

    public int[] roll(){
        return new int[] {0,0};
    }
}