import java.util.ArrayList;
import java.util.Scanner;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.io.IOException;

//Game Logic
public class BatterUp {
//Properties
    private int outs;
    private int score;
    private int nextPlayerIndex;
    private Field field;
    private ArrayList<Player> players = new ArrayList<Player>();

//Constructor
    public BatterUp() {
        outs = 0;
        score = 0;
        nextPlayerIndex = 0;
        this.field = new Field();
        CreatePlayers();
    }

//Methods

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
        //read and process roster line by line
        while(scan.hasNextLine()){
            String line = scan.nextLine();
            if(!line.matches("\\s*")){ //skip lines that dont match regex
                String[] playerData = line.split("(\\s*,\\s*)[Player|Ringer|Dud]");
                switch (playerData[1]) { //add players with correct subclass of player
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
    //retrieve next player in line to bat
    public Player getNextPlayer(){
        Player nextPlayer = players.get(nextPlayerIndex);
        nextPlayerIndex += 1;
        if(nextPlayerIndex == players.size()){ //reset index if whole team has bat
            nextPlayerIndex = 0;
        }
        return nextPlayer;
    }
    //main gameplay
    public String Play(int numberOfInnings) throws IOException{
        String str = "";
        int inning = 0;
        while(inning < numberOfInnings){    //game loop
            str += String.format("\n-----------------------------------------\nInning %s\n\n", inning);
            while(outs < 3){                //inning loop
                displayField();
                Player currentPlayer = getNextPlayer();
                str += String.format("%s is batting\n", currentPlayer.getName());
                //move player to bat
                currentPlayer.setLocation(field.getBatterBox());
                //processes player's turn and adds it to output string
                int battingValue = currentPlayer.takeTurn();
                str += currentPlayer.getOutput();
                currentPlayer.resetOutput();
                //move players or increase numbers of outs based off batting result
                if(battingValue == 0){
                    outs++;
                    currentPlayer.setLocation(field.getDugout());
                }else{
                    movePlayers(battingValue);
                }
                str += String.format("\nSCORE: %s\nOUTS: %s\n\n",score, outs);
            }
            outs = 0;
            inning++;
        }
        //end game, create stat file, output results and reset for next playthrough
        str += "\nGame Over!";
        printStats();
        score = 0;
        return str;
    }
    //moves players around bases
    public String movePlayers(int basesToMove){
        String str = "";
        for (Player player : players) {
            if(player.isNotInDugout()){ //only move players which are on the field
                player.setLocation(field.moveAhead(player.getLocation(), basesToMove));
                if(player.getLocation().isHome()){  //increase the score when a play reaches home base
                    score++;
                    str += String.format("%s scored\n", player.getName());
                    player.setLocation(field.getDugout());
                }
            }
        }
        return str;
    }
    //display what player is on each base, or if its empty
    public String displayField(){
        String str = "";
        String[] playersOnBase = new String[]{"Empty", "Empty", "Empty"};
        for (Player player : players) { //loop through each player and assign them by name to their current base
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
    //output stats to file
    public void printStats() throws IOException{
        String gameStatsFileName = "gameStats.txt";
        //create stats file or clear if it already exists
        File file = new File(gameStatsFileName);
        boolean fileExists = !file.createNewFile();
        PrintWriter writer = new PrintWriter(gameStatsFileName, "UTF-8");
        if(fileExists){
            writer.print("");   //clears file text
        }
        writer.println(
            "GAME STATS:\n" +
            "****************************************\n" +
            "PLAYER        HITS  AT-BATS AVERAGE"
        );
        for (Player player : players) { //loop through each player and output their batting stats to file
            if(player.getAtBats() == 0){
                writer.printf("%-12.12s %-5d %-7d %-s\n", player.getName(), player.getHits(), player.getAtBats(), "-");
            }
            writer.printf("%-12.12s %-5d %-7d %-10.3f\n", player.getName(), player.getHits(), player.getAtBats(), player.getBattingAverage());
        }
        writer.print("****************************************");
        writer.close();
    }
}
