/**
 * Platformer
 * Author: Peter Mitchell (2021)
 *
 * CollisionTrigger interface:
 * Defines an interface that can be used to handle collisions by calling the collideWith method.
 */
public interface CollisionTrigger {
    /**
     * Call this method to handle when a collision has occurred between
     * some other object and one that has implemented this interface.
     *
     * @param object The object that collided with this object.
     */
    void collideWith(GameObject object);
}
