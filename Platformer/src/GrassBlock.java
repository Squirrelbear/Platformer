import java.awt.*;

/**
 * Platformer
 * Author: Peter Mitchell (2021)
 *
 * GrassBlock class:
 * A modified version of the Block class that adds a line of green
 * grass to the top of the block.
 */
public class GrassBlock extends Block {
    /**
     * Initialises the block ready to be drawn and interacted with.
     *
     * @param position The position to place the object.
     * @param width The width of the object.
     * @param height The height of the object.
     */
    public GrassBlock(Position position, int width, int height) {
        super(position, width, height);
    }

    /**
     * Draws the regular version of the brown block and then adds a line of green to the top.
     *
     * @param g Reference to the Graphics object for rendering.
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(new Color(45, 95, 23));
        g.fillRect(position.x, position.y, width, 10);
    }
}
