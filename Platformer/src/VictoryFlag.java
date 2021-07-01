import java.awt.*;

/**
 * Platformer
 * Author: Peter Mitchell (2021)
 *
 * VictoryFlag class:
 * Defines a flag that can be collided with by the player to win the level.
 */
public class VictoryFlag extends GameObject implements CollisionTrigger {
    /**
     * Width of the flag.
     */
    private static final int FLAG_WIDTH = 60;
    /**
     * Height of the flag.
     */
    private static final int FLAG_HEIGHT = 50;

    /**
     * Creates the flag ready for interaction.
     *
     * @param position Position to place the flag at.
     */
    public VictoryFlag(Position position) {
        super(position, FLAG_WIDTH, FLAG_HEIGHT);
        canEnter = true;
    }

    /**
     * Draws the flag with the text Win Here on it.
     *
     * @param g Reference to the Graphics object for rendering.
     */
    @Override
    public void paint(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(position.x, position.y, 5, height);
        g.fillRect(position.x, position.y, width, 20);
        g.setColor(Color.WHITE);
        g.fillRect(position.x+3, position.y+3, width-6, 20-6);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 10));
        g.drawString("Win Here!", position.x+4, position.y+4+10);
    }

    /**
     * When collided with by the player it will activate the flag on the player.
     *
     * @param object The object that collided with this object.
     */
    @Override
    public void collideWith(GameObject object) {
        if(object instanceof Player) {
            ((Player)object).activateFlag();
        }
    }
}
