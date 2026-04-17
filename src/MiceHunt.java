import java.awt.*;
import javax.swing.*;

public class MiceHunt {
    //Main window dimensions.
    private static final int BOARD_WIDTH  = 600;
    private static final int BOARD_HEIGHT = 650;

    //Initalize fram, textlabels and panels for the game.
    //JFrame is the main game window, it handles title bar, minimize/maximize/close buttons.
    //JPanel is a space within the frame that holds other objects like panels, labels, buttons.
    //JLabel is used to store text, images.
    private final JFrame frame     = new JFrame("Project: Mice Hunt");
    private final JLabel textLabel = new JLabel();
    private final JPanel textPanel = new JPanel();

    //self explanatory:
    public void setupFrame() {
        frame.setSize(BOARD_WIDTH, BOARD_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
    }

    public void showStartScreen() {
        //Set up the startScreen Panel.
        //layout management is set to BorderLayout i.e. 
        // it follows the NORTH, SOUTH, EAST, WEST, CENTER positioning layout. 
        JPanel startPanel = new JPanel(new BorderLayout());
        startPanel.setBackground(Color.BLACK);

        //Initalize the titleLabel that contains the game name and set it's attributes.
        //The 'MICE HUNT' will be displayed to the center of the screen.
        JLabel titleLabel = new JLabel("MICE HUNT");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 60));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        //Add the titleLabel to the center of the startPanel.
        startPanel.add(titleLabel, BorderLayout.CENTER);

        // Difficulty selection panel
        //Crate a difficultyPanel that will hold three buttons 
        //These buttons represent difficulty modes.
        JPanel difficultyPanel = new JPanel(new GridLayout(1, 3));
        JButton easyButton   = new JButton("Easy");
        JButton mediumButton = new JButton("Medium");
        JButton hardButton   = new JButton("Hard");

        //For every difficulty button,
        //Make it irresponsive to the KeyBoard key presses.
        //The buttons go in the order: EASY, MEDIUM, DIFFICULT to the difficultyPanel.
        for (JButton btn : new JButton[]{easyButton, mediumButton, hardButton}) {
            btn.setFont(new Font("Arial", Font.PLAIN, 28));
            btn.setFocusable(false);
            difficultyPanel.add(btn);
        }

        //Add ActionListener to each diffculty Button and the function executed when the button is pressed.
        //The funciton:
        //Upon pressing one of the buttons, the frame removes the startPanel
        //startGame(Difficulty difficulty) method will be called in the respective difficulty Mode.
        easyButton.addActionListener(e   -> { frame.remove(startPanel); startGame(new Easy()); });
        mediumButton.addActionListener(e -> { frame.remove(startPanel); startGame(new Medium()); });
        hardButton.addActionListener(e   -> { frame.remove(startPanel); startGame(new Hard()); });

        //Add the difficulty panel to the bottom of the start panel(ie. bottom of the screen).
        startPanel.add(difficultyPanel, BorderLayout.SOUTH);

        //Add the startPanel to the main window frame.
        frame.add(startPanel);

        //Since the structure of frame has changed, reevaludate the panel structure and draw it to the screen.
        frame.revalidate();
        frame.repaint();
    }

    private void startGame(Difficulty difficulty) {

        //Setting up the text label to display the current score in the game.
        textLabel.setFont(new Font("Arial", Font.PLAIN, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Score: 0");
        textLabel.setOpaque(true);  //makes the background(default in this case) color visible.

        //FlowLayout allows us to put Restart Button where there is space in the textPanel
        textPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));//Set the layout manager to BorderLayout.[N,S,E,W,C]
        //Add the textlabel to the textPanel.
        textPanel.add(textLabel);

        //initialize the gameState and the gameBoard
        GameState gameState = new GameState();
        GameBoard gameBoard = new GameBoard(gameState, textLabel, difficulty);

        //Add the textPanel and the gameBoard.boardPanel to the frame.
        frame.add(textPanel, BorderLayout.NORTH);
        frame.add(gameBoard.boardPanel, BorderLayout.CENTER);
        //Since the structure of frame has changed, reevaludate the panel structure and draw it to the screen.
        frame.revalidate();
        frame.repaint();

        //This is what controls the actual gameplay:
        gameBoard.startTimers();
    }
}
