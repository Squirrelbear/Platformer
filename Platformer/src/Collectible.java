import java.awt.*;

/**
 * Platformer
 * Author: Peter Mitchell (2021)
 *
 * Collectible class:
 * Defines an object that can be collected once by the player for score.
 */
public class Collectible extends GameObject implements CollisionTrigger {
    /**
     * Width of the object.
     */
    private static final int WIDTH = 40;
    /**
     * Height of the object.
     */
    private static final int HEIGHT = 40;

    /**
     * When false the object can be interacted with by the player and is visible.
     * Once true, the object is hidden and does nothing.
     */
    private boolean collected;

    /**
     * Configures the collectible ready for use.
     *
     * @param position Position to place the object at.
     */
    public Collectible(Position position) {
        super(position, WIDTH, HEIGHT);

        canEnter = true;
        isGround = false;
        collected = false;
    }

    /**
     * Draws the object if it hasn't been collected as a simple oval.
     *
     * @param g Reference to the Graphics object for rendering.
     */
    @Override
    public void paint(Graphics g) {
        if(collected) return;

        g.setColor(Color.YELLOW);
        g.fillOval(position.x, position.y, width, height);
        g.setColor(Color.BLACK);
        g.drawOval(position.x, position.y, width, height);
    }

    /**
     * Called when the object is collided with. Does nothing if already
     * collected. If the player collides with it they gain 5 score.
     *
     * @param object The object that was collided with.
     */
    @Override
    public void collideWith(GameObject object) {
        if(collected) return;

        if(object instanceof  Player) {
            ((Player)object).addScore(5);
            collected = true;
        }
    }
}
