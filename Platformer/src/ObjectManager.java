import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Platformer
 * Author: Peter Mitchell (2021)
 *
 * ObjectManager class:
 * Manages a collection of GameObjects and a Player object.
 */
public class ObjectManager {
    /**
     * All the game objects that make up a map.
     */
    private List<GameObject> gameObjectList;
    /**
     * The player object.
     */
    private Player player;

    /**
     * Initialises an empty map.
     */
    public ObjectManager() {
        gameObjectList = new ArrayList<>();
    }

    /**
     * Updates all the game objects and the player.
     *
     * @param deltaTime Time since last update.
     */
    public void update(int deltaTime) {
        gameObjectList.forEach(obj -> obj.update(deltaTime));
        if(player != null) {
            player.update(deltaTime);
        }
    }

    /**
     * Draws all the game objects and the player.
     *
     * @param g Reference to the Graphics object for rendering.
     */
    public void paint(Graphics g) {
        gameObjectList.forEach(obj -> obj.paint(g));
        if(player != null) {
            player.paint(g);
        }
    }

    /**
     * Gets a reference to the Player object.
     *
     * @return A reference to the Player object.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets a list of objects that have collided with a specific other object.
     *
     * @param objectToTest Object to test collisions against.
     * @return A list of all objects that are currently colliding with the specified object.
     */
    public List<GameObject> getObjectsCollidedWith(GameObject objectToTest) {
        List<GameObject> result = new ArrayList<>();
        for (GameObject gameObject : gameObjectList) {
            if(objectToTest != gameObject && objectToTest.isIntersecting(gameObject)) {
                result.add(gameObject);
            }
        }
        return result;
    }

    /**
     * Removes all objects.
     */
    public void clearObjects() {
        gameObjectList.clear();
        player = null;
    }

    /**
     * Adds the specified game object. If it is a player it will replace the current player.
     *
     * @param gameObject Game object to add.
     */
    public void addObject(GameObject gameObject) {
        if(gameObject instanceof Player) {
            player = (Player)gameObject;
        } else {
            gameObjectList.add(gameObject);
        }
    }
}
