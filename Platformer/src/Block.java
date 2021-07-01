import java.awt.*;

/**
 * Platformer
 * Author: Peter Mitchell (2021)
 *
 * Block class:
 * A simple ground block with a brown colour.
 */
public class Block extends GameObject {
    /**
     * Initialises the block ready to be drawn and interacted with.
     *
     * @param position The position to place the object.
     * @param width The width of the object.
     * @param height The height of the object.
     */
    public Block(Position position, int width, int height) {
        super(position, width, height);
        isGround = true;
    }

    /**
     * Draws a simple rectangle with brown colour.
     *
     * @param g Reference to the Graphics object for rendering.
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(new Color(101, 75, 37));
        g.fillRect(position.x, position.y, width, height);
    }
}
