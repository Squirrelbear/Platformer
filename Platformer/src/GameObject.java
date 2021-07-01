import java.awt.*;

/**
 * Platformer
 * Author: Peter Mitchell (2021)
 *
 * GameObject class:
 * Defines a simple game object template that can be used for other objects.
 */
public abstract class GameObject extends Rectangle {
    /**
     * Defines how the object behaves for the player falling onto it.
     */
    protected boolean isGround;
    /**
     * Defines whether the player should fall/move through the object.
     */
    protected  boolean canEnter;

    /**
     * Defines the object with provided properties and defaults to
     * not being ground with no entry.
     *
     * @param position Position to place the object.
     * @param width Width of the object.
     * @param height Height of the object.
     */
    public GameObject(Position position, int width, int height) {
        super(position, width, height);
        isGround = false;
        canEnter = false;
    }

    /**
     * Empty paint method to be overloaded by classes extending from this class.
     *
     * @param g Reference to the Graphics object for rendering.
     */
    public void paint(Graphics g) {

    }

    /**
     * Empty update method to be overloaded by classes extending from this class.
     *
     * @param deltaTime Time since last update.
     */
    public void update(int deltaTime) {

    }

    /**
     * Gets whether the object is considered a surface to walk on.
     *
     * @return True if the object should act as ground.
     */
    public boolean isGround() {
        return isGround;
    }

    /**
     * Gets whether the object is considered solid.
     *
     * @return True if the object can be walked through.
     */
    public boolean canEnter() {
        return canEnter;
    }
}
