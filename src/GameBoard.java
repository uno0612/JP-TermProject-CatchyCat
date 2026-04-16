import java.awt.*;
import java.util.*;
import javax.swing.*;

public class GameBoard {
    //If there are 5 mice, 5/36 tiles will have mice.
    private static final int TILE_COUNT = 36;

    //The panel is the space where the clickable buttons will be placed.
    JPanel boardPanel = new JPanel();
    //We want to place buttons at each tile for the user to click.
    //Thus, size of board = TILE_COUNT
    JButton[] board   = new JButton[TILE_COUNT];

    //ImageIcons store(load) the images to be displayed by Swing(Java GUI widget toolkit).
    private ImageIcon miceIcon;
    private ImageIcon milkIcon;

    //There were two time classes from javax.util and javax.swing
    //Thus we javax.swing.Timer specifies that we are using the Timer class from javax.swing
    private javax.swing.Timer setMiceTimer;
    private javax.swing.Timer setMilkTimer;
    //We will use this random object to generate a random integer[1-36](we have 36 tiles) to select a tile later on.
    private final Random random = new Random();

    //Gamestate must not be reReferenced. once generated.
    //Think of it, we wouldn't want the current gameState to be replaced by some other (possible) code.
    //for one session of the game, the gameState reference does not change until the game is over.
    private final GameState gameState;
    private final JLabel textLabel;
    private final Difficulty difficulty;//Difficulty level cannot be rereferenced either.


    public GameBoard(GameState gameState, JLabel textLabel, Difficulty difficulty) {
        this.gameState  = gameState;
        this.textLabel  = textLabel;
        this.difficulty = difficulty;
        loadIcons();
        setupBoard();
    }

    private void loadIcons() {
        //We are loading the image first.
        //Our ImageIcon objects hold the scaled down image.
        //For both icons, we are loading the image, scaling it down and storing it in respective references.
        Image milkImg = new ImageIcon(getClass().getResource("./milk.png")).getImage();
        milkIcon = new ImageIcon(milkImg.getScaledInstance(150, 150, Image.SCALE_SMOOTH));

        Image miceImg = new ImageIcon(getClass().getResource("./mice.png")).getImage();
        miceIcon = new ImageIcon(miceImg.getScaledInstance(150, 150, Image.SCALE_SMOOTH));
    }

    private void setupBoard() {//Self Explanatory, isn't it? I will take you to details below:
        //We have the main frame, and then we have the Panel.
        //The Panel is basically a space inside the frame where the actual game GUI appears.
        //Here, we are dividing our Panel to 6*6 cells to store 36 tiles.
        boardPanel.setLayout(new GridLayout(6, 6));

        //Pay attention to this one:
        //We are creating tiles and we are attaching an object to each tiles.
        //This object is of type ActionListener().
        //This ActionListner() will perform certain action(based on the method we define) when an action(button click in our case) happens.
        for (int i = 0; i < TILE_COUNT; i++) {
            JButton tile = new JButton();
            board[i] = tile;
            tile.setFocusable(false);//This is important.
                                               //Just know that, we are disabling the tile to respond to any keyboard presses.
                                               //The tile will only detect mouse click(after the ActionListener is added).
            boardPanel.add(tile);//Add the tile to a cell of the board Panel.

            //We are adding an ActionListener to the tile as mentioned above.
            //We are defining a lambda funciton that defines the system response when a tile is pressed.
            //In this case, we will check if the tile clicked on is the tile that contains the mouse.
            //If it contains mouse, increase the current game score and remove mouse tile and icon.
            //If it contains milk, end the game.
            //else, do nothing, duh.. what do you expect?, the game goes on.
            tile.addActionListener(e -> {
                if (gameState.isMiceTile(tile)) {
                    gameState.incrementScore();
                    textLabel.setText("Score: " + gameState.getScore());
                } else if (gameState.isMilkTile(tile)) {
                    textLabel.setText("Game Over: " + gameState.getScore());
                    stopTimers();
                    for (JButton b : board) b.setEnabled(false);
                }
            });
        }
    }

    public void startTimers() {
        //Pay attention for this one as well.
        // We are creating two Timers, setMiceTimer and setMilkTimer
        //The timers excute a cetain method at a definite time interval.
        //for example, if I set the interval to 2000ms, the funciton executes every 2 sec.
        //We set the interval to the delay of the current difficulty.
        //Regarding the funciton passed, on a superficial level:
        //we turn every mouse/milk tile blank,
        //then, we select difficulty.getMiceCount random tiles to display the mouse/milk again.


        setMiceTimer = new javax.swing.Timer(difficulty.getMiceInterval(), e -> {
            for (JButton tile : gameState.getCurrMiceTiles()) tile.setIcon(null);
            Set<JButton> newMice = pickRandomTiles(difficulty.getMiceCount(), gameState.getCurrMilkTiles());
            gameState.setCurrMiceTiles(newMice);
            for (JButton tile : newMice) tile.setIcon(miceIcon);
        });

        setMilkTimer = new javax.swing.Timer(difficulty.getMilkInterval(), e -> {
            for (JButton tile : gameState.getCurrMilkTiles()) tile.setIcon(null);
            Set<JButton> newMilk = pickRandomTiles(difficulty.getMilkCount(), gameState.getCurrMiceTiles());
            gameState.setCurrMilkTiles(newMilk);
            for (JButton tile : newMilk) tile.setIcon(milkIcon);
        });

        //start the timers.
        setMiceTimer.start();
        setMilkTimer.start();
    }

    // Picks 'count' random tiles that don't overlap with the occupied set of tiles.
    private Set<JButton> pickRandomTiles(int count, Set<JButton> occupied) {
        //For this one, we are creating a new randomly generated set of tiles.
        //This set of tiles will contain the milk/mouse now.
        Set<JButton> picked = new HashSet<>();
        while (picked.size() < count) {
            //pick a random tile from the board.
            JButton tile = board[random.nextInt(TILE_COUNT)];
            //if this tile is not currenlty active tile(for display) and,
            //has not been picked yet for the next slot, select it.
            if (!occupied.contains(tile) && !picked.contains(tile)) {
                picked.add(tile);
            }
        }
        return picked;
    }

    //Stop the generation of the randomized appearance of mouse/milk once the game ends.
    public void stopTimers() {
        if (setMiceTimer != null) setMiceTimer.stop();
        if (setMilkTimer != null) setMilkTimer.stop();
    }
}
