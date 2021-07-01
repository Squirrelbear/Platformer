import java.awt.*;

/**
 * Platformer
 * Author: Peter Mitchell (2021)
 *
 * Spikes class:
 * Represents triangular spikes that cause lethal damage to the player.
 */
public class Spikes extends GameObject implements CollisionTrigger {
    /**
     * X coordinates to draw the spikes.
     */
    private int[] polyXCoords;
    /**
     * Y coordinates to draw the spikes.
     */
    private int[] polyYCoords;

    /**
     * Creates a group of spikes that has 1 spike every 20 pixels.
     *
     * @param position Position to place the spikes.
     * @param width Width of the spike region (should be divisible by 20)
     * @param height Height of the spike region
     */
    public Spikes(Position position, int width, int height) {
        super(position, width, height);

        int spikeCount = width/20;
        canEnter = true;
        isGround = false;

        // Calculate the coordinates for the resulting polygon.
        polyXCoords = new int[spikeCount*2+1];
        for(int i = 0; i < polyXCoords.length; i++) {
            polyXCoords[i] = position.x + i * 10;
        }

        polyYCoords = new int[spikeCount*2+1];
        for(int i = 0; i < polyYCoords.length; i++) {
            polyYCoords[i] = position.y + ((i % 2 == 0) ? height : 0);
        }
    }

    /**
     * Draws the spikes as triangles using a polygon.
     *
     * @param g Reference to the Graphics object for rendering.
     */
    @Override
    public void paint(Graphics g) {
        g.setColor(new Color(160, 160, 160));
        g.fillPolygon(polyXCoords, polyYCoords, polyXCoords.length);
        g.setColor(new Color(139, 12, 12));
        g.drawPolygon(polyXCoords, polyYCoords, polyXCoords.length);
    }

    /**
     * When the player collides with the spikes it will cause lethal damage.
     *
     * @param object The object that collided with this object.
     */
    @Override
    public void collideWith(GameObject object) {
        if(object instanceof Player) {
            ((Player) object).damagePlayer(1);
        }
    }
}
