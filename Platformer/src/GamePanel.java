import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Platformer
 * Author: Peter Mitchell (2021)
 *
 * GamePanel class:
 * Manages the game state and passes information to objects.
 */
public class GamePanel extends JPanel implements ActionListener {
    /**
     * Time between updates in ms.
     */
    public static final int TIME_INTERVAL = 20;
    /**
     * Height of the panel.
     */
    public static final int PANEL_HEIGHT = 600;
    /**
     * Width of the panel.
     */
    public static final int PANEL_WIDTH = 800;

    /**
     * Timer to keep regular updates occurring.
     */
    private Timer gameTimer;
    /**
     * The object manager containing the current GameObjects.
     */
    private ObjectManager objectManager;
    /**
     * The map loader that can be used to load a map into the ObjectManager.
     */
    private MapLoader mapLoader;
    /**
     * Number of lives remaining. Game over at 0 lives.
     */
    private int lives;
    /**
     * When true the game ends.
     */
    private boolean gameOver;
    /**
     * A message indicating whether the game over was a win or a loss.
     */
    private String gameOverMessage;

    /**
     * Initialises the game with 3 lives and loads the mpa ready to play.
     */
    public GamePanel() {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setBackground(new Color(72, 132, 125));

        objectManager = new ObjectManager();
        mapLoader = new MapLoader(objectManager);
        restart();
        lives = 3;

        gameTimer = new Timer(TIME_INTERVAL, this);
        gameTimer.start();
    }

    /**
     * Draws all the game objects on along with score and game over message if required.
     *
     * @param g Reference to the Graphics object for rendering.
     */
    public void paint(Graphics g) {
        super.paint(g);
        objectManager.paint(g);
        drawLives(g);
        drawScore(g);
        if(gameOver) {
            drawGameOver(g);
        }
    }

    /**
     * Called at regular intervals by the gameTimer. Does nothing if the game has ended.
     * Otherwise updates all the objects then checks the player's state for either a
     * death or victory and changes the state as required.
     *
     * @param e Information about the event.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(gameOver) return;

        objectManager.update(TIME_INTERVAL);
        if(objectManager.getPlayer().isDead()) {
            lives--;
            if(lives > 0) {
                restart();
            } else {
                gameOverMessage = "Game Over! No more lives. R to Restart.";
                gameOver = true;
            }
        } else if(objectManager.getPlayer().hasReachedFlag()) {
            gameOverMessage = "You won! Flag reached! R to Restart.";
            gameOver = true;
        }
        repaint();
    }

    /**
     * Escape to exit, R to restart, and otherwise handled by the player object.
     *
     * @param keyCode The key that was interacted with.
     * @param isPressed True indicates it was pressed, false means it was released.
     */
    public void handleInput(int keyCode, boolean isPressed) {
        if(keyCode == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        } else if(keyCode == KeyEvent.VK_R) {
            lives = 3;
            restart();
        } else if(!gameOver) {
            objectManager.getPlayer().handleInput(keyCode, isPressed);
        }
    }

    /**
     * Restarts the game by wiping the map and reloading it from the Map.txt.
     */
    public void restart() {
        gameOver = false;
        mapLoader.loadMap("Map.txt");
    }

    /**
     * Draws the lives left aligned in the top left corner.
     *
     * @param g Reference to the Graphics object for rendering.
     */
    private void drawLives(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        String livesStr = "Lives: " + lives;
        g.drawString(livesStr, 15, 30);
    }

    /**
     * Draws the score centred at the top of the panel.
     *
     * @param g Reference to the Graphics object for rendering.
     */
    private void drawScore(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        String scoreStr = "Score: " + objectManager.getPlayer().getScore();
        int strWidth = g.getFontMetrics().stringWidth(scoreStr);
        g.drawString(scoreStr, PANEL_WIDTH/2-strWidth/2, 30);
    }

    /**
     * Draws a background with game over message centred in the middle of the panel.
     *
     * @param g Reference to the Graphics object for rendering.
     */
    private void drawGameOver(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0,PANEL_HEIGHT/2-20, PANEL_WIDTH, 40);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        int strWidth = g.getFontMetrics().stringWidth(gameOverMessage);
        g.drawString(gameOverMessage, PANEL_WIDTH/2-strWidth/2, PANEL_HEIGHT/2+10);
    }
}
