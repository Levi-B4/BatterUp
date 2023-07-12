import java.util.ArrayList;
import java.util.Scanner;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.io.IOException;

public class BatterUp {
    private int outs;
    private int score;
    private int nextPlayerIndex;
    private Field field;
    private ArrayList<Player> players = new ArrayList<Player>();

    public BatterUp() {
        outs = 0;
        score = 0;
        nextPlayerIndex = 0;
        this.field = new Field();
        CreatePlayers();
    }

    public void CreatePlayers(){
        Base dugout = field.getDugout();
        Path path = Paths.get("players.txt");
        System.out.printf("Retrieving players from %s\n", path);
        Scanner scan;
        try {
            scan = new Scanner(path);
        } catch (Exception e) {
            System.out.printf("Could not find file: %s\n, please create this file", path);
            scan = new Scanner(System.in);
        }
        System.out.println("Reading text file using Scanner");
        //read line by line
        while(scan.hasNextLine()){
            //process each line
            String line = scan.nextLine();
            if(!line.matches("\\s*")){
                String[] playerData = line.split("(\\s*,\\s*)[Player|Ringer|Dud]"/* "[\\w]+(,)[pP]layer|[rR]inger|[dD]inger\\s$"*/); //edit so that only that last ", " is removed
                switch (playerData[1]) {
                    case "Ringer":
                        players.add(new Ringer(playerData[0], dugout));
                        break;

                    case "Dud":
                        players.add(new Dud(playerData[0], dugout));
                        break;
                
                    default:
                        players.add(new Player(playerData[0], dugout));
                        break;
                }
            }
        }
        scan.close();
    }

    public Player getNextPlayer(){
        Player nextPlayer = players.get(nextPlayerIndex);
        nextPlayerIndex += 1;
        if(nextPlayerIndex == players.size()){
            nextPlayerIndex = 0;
        }
        return nextPlayer;
    }

    public String Play(int numberOfInnings) throws IOException{
        String str = "";
        int inning = 0;
        while(inning < 9){
            while(outs < 3){
                str += "\nSCORE: " + score +"\n\n";
                displayField();
                Player nextPlayer = getNextPlayer();
                str += nextPlayer.getName() + " is batting\n";
                nextPlayer.setLocation(field.getBatterBox());
                int battingValue = nextPlayer.takeTurn();
                str += nextPlayer.getOutput();
                if(battingValue == 0){
                    outs++;
                    nextPlayer.setLocation(field.getDugout());
                }else{
                    movePlayers(battingValue);
                }
                inning++;

                if(outs >= 3) {
                    nextPlayer.resetOutput();
                    }
                    if(nextPlayer.getStrikes() == 3) {
                    nextPlayer.resetOutput();
                    }
                    if(nextPlayer.getBalls() == 4) {
                    nextPlayer.resetOutput();
                    }
            }
            outs = 0;
        }
        printStats();
        score = 0;
        return str;
    }

    public String movePlayers(int basesToMove){
        String str = "";
        for (Player player : players) {
            if(player.isNotInDugout()){
                player.setLocation(field.moveAhead(player.getLocation(), basesToMove));
                if(player.getLocation().isHome()){
                    score++;
                    str += String.format("%s scored\n", player.getName());
                    player.setLocation(field.getDugout());
                }
            }
        }
        return str;
    }

    public String displayField(){
        String str = "";
        String[] playersOnBase = new String[]{"Empty", "Empty", "Empty"};
        for (Player player : players) {
            String locationName = player.getLocation().getName();
            switch (locationName) {
                case "First":
                    playersOnBase[0] = player.getName();
                    break;

                case "Second":
                    playersOnBase[1] = player.getName();
                    break;

                case "Third":
                    playersOnBase[2] = player.getName();
                    break;
            
                default:
                    break;
            }
        }
        str += String.format("[ 1 ] %s  [ 2 ] %s  [ 3 ] %s\n\n", playersOnBase[0], playersOnBase[1], playersOnBase[2]);
        return str;
    }

    public void printStats() throws IOException{
        String gameStatsFileName = "gameStats.txt";
        //create or clear the stats file
        File file = new File(gameStatsFileName);
        boolean fileExists = !file.createNewFile();
        PrintWriter writer = new PrintWriter(gameStatsFileName, "UTF-8");
        if(fileExists){
            writer.print("");
        }
        writer.println(
            "GAME STATS:\n" +
            "****************************************\n" +
            "PLAYER        HITS  AT-BATS AVERAGE"
        );
        for (Player player : players) {
            if(player.getAtBats() == 0){
                writer.printf("%-12.12s %-5d %-7d %-s\n", player.getName(), player.getHits(), player.getAtBats(), "-");
            }
            writer.printf("%-12.12s %-5d %-7d %-10.3f\n", player.getName(), player.getHits(), player.getAtBats(), player.getBattingAverage());
        }
        writer.print("****************************************");
        writer.close();
    }
}
