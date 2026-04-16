import javax.swing.JButton;
import java.util.HashSet;
import java.util.Set;

//class state: score, currMiceTiles, currMilkTiles.
//class behaviour is about updating and accessing the class states
//based on the level/user Actions.

//score: current score of the player in the game.
//Regarding currMiceTiles & currMilkTiles, a Set was used.
//Since we want to hold the references to the multiple tiles containing the mice,
//Set would be an appropriate data Structure for faster lookups(searching the appropriate tile).


public class GameState {
    //Everyt time a mouse is caught, score_increment = 10
    private static final int SCORE_INCREMENT = 10;

    private int score = 0;//current score.

    //Since we have multiple instances of mice for level easy and difficult,
    //checking through lists could have cost O(n) time in worst case.
    //Hashmap cost O(1) for lookups making tiles accessing efficient.
    private Set<JButton> currMiceTiles = new HashSet<>();
    private Set<JButton> currMilkTiles = new HashSet<>();

    public void incrementScore() {
        score += SCORE_INCREMENT;
    }

    public void reset() {
        score = 0;
        currMiceTiles.clear();
        currMilkTiles.clear();
    }

    //Checks if the tile clicked by the user is the micetile or the milktile.
    public boolean isMiceTile(JButton tile) { return currMiceTiles.contains(tile); }
    public boolean isMilkTile(JButton tile) { return currMilkTiles.contains(tile); }

    //Accessor methods for game states:score, currMiceTiles & currMilkTiles.
    public int getScore()                        { return score; }
    public Set<JButton> getCurrMiceTiles()       { return currMiceTiles; }
    public Set<JButton> getCurrMilkTiles()       { return currMilkTiles; }

    //Setter methods for colleciton of miceTiles and milkTiles.
    public void setCurrMiceTiles(Set<JButton> t) { currMiceTiles = t; }
    public void setCurrMilkTiles(Set<JButton> t) { currMilkTiles = t; }
}
