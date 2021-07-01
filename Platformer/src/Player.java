import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 * Platformer
 * Author: Peter Mitchell (2021)
 *
 * Player class:
 * Defines the player class managing the movement of the player
 * within the game with checking for interactions to keep it in
 * a valid location.
 */
public class Player extends GameObject {
    /**
     * Visual width of the player.
     */
    private static final int PLAYER_WIDTH = 30;
    /**
     * Visual height of the player.
     */
    private static final int PLAYER_HEIGHT = 50;

    /**
     * When true this will jump in the next update.
     */
    private boolean jump;
    /**
     * Allows a single second jump to be used in the air.
     */
    private boolean doubleJumpUsed;
    /**
     * Status of the keys for left/right to determine if movement should happen during updates.
     */
    private boolean keyLeftIsPressed, keyRightIsPressed;
    /**
     * The magnitude of movement translation for left/right movement.
     */
    private final int moveRate = 5;

    /**
     * Gravity (Downward acceleration).
     */
    private static final int gravity = 200;
    /**
     * Force applied when a jump occurs.
     */
    private static final int jumpForce = -100;
    /**
     * Vertical velocity.
     */
    private double dY = 0;

    /**
     * When true the Player is currently on ground.
     */
    private boolean isGrounded;
    /**
     * Reference to the object manager for checking collisions.
     */
    private ObjectManager objectManager;

    /**
     * When true the player has hit something that kills them.
     */
    private boolean isDead;
    /**
     * When true the player has reached the end game flag.
     */
    private boolean reachedFlag;
    /**
     * The player's current score that increases when interacting with Collectibles.
     */
    private int score;

    /**
     * Sets up the player ready to move.
     *
     * @param position Position to place the Player at.
     * @param objectManager Reference to the ObjectManager for checking collisions.
     */
    public Player(Position position, ObjectManager objectManager) {
        super(position, PLAYER_WIDTH, PLAYER_HEIGHT);
        keyLeftIsPressed = false;
        keyRightIsPressed = false;
        jump = false;
        doubleJumpUsed = false;
        isGrounded = false;
        this.objectManager = objectManager;
        isDead = false;
    }

    /**
     * Updates the player's position by using the key presses and accounts for
     * falling when not grounded.
     *
     * @param deltaTime Time since last update.
     */
    @Override
    public void update(int deltaTime) {
        // Don't fall when grounded
        if(isGrounded) {
            dY = 0;
        } else {
            dY += gravity * deltaTime / 1000.0;
        }
        // When jump has been pressed during the time since last update
        if(jump) {
            jump = false;
            // Only apply the jump force if either currently grounded or in the air and the doubleJump has not been used.
            if(isGrounded || !doubleJumpUsed) {
                dY += jumpForce;
                doubleJumpUsed = !isGrounded;
            }
        }
        // Move left/right
        if(keyLeftIsPressed) {
            moveWithinBounds(new Position(-moveRate,0), GamePanel.PANEL_WIDTH-width, GamePanel.PANEL_HEIGHT);
        }
        if(keyRightIsPressed) {
            moveWithinBounds(new Position(moveRate,0), GamePanel.PANEL_WIDTH-width, GamePanel.PANEL_HEIGHT);
        }
        // Update the y position relative to the falling velocity.
        position.y += (dY * deltaTime / 1000.0);

        // Check for any collisions that have occurred as a result of moving
        List<GameObject> collidedWith = objectManager.getObjectsCollidedWith(this);
        // Assume not grounded
        isGrounded = false;
        for(GameObject object : collidedWith) {
            // Apply any collision event with the collided object
            if(object instanceof CollisionTrigger) {
                ((CollisionTrigger)object).collideWith(this);
            }
            if(object.isGround() && object.position.y > position.y) {
                // Object is on the ground.
                isGrounded = true;
            }
            // Bump the object out of the object if it should not be inside
            if(!object.canEnter() && object.position.y > position.y) {
                position.y = object.position.y - height;
            }
        }
    }

    /**
     * Draws the body and then offsets the eyes to either the middle,
     * or the right if the right key is held, or left if the right key is held.
     *
     * @param g Reference to the Graphics object for rendering.
     */
    @Override
    public void paint(Graphics g) {
        g.setColor(new Color(13, 29, 78));
        g.fillRect(position.x, position.y, width,height);
        g.setColor(new Color(47, 78, 184));
        g.fillRect(position.x+5, position.y+5, width-10,height-10);
        g.setColor(new Color(198, 155, 34));
        int eyeOffset = width/2+1;
        if(keyRightIsPressed) eyeOffset += 5;
        else if(keyLeftIsPressed) eyeOffset -= 5;
        g.fillRect(position.x+eyeOffset-3-5, position.y+10, 5,5);
        g.fillRect(position.x+eyeOffset+5-5, position.y+10, 5,5);
    }

    /**
     * Left/Right will trigger moves to the left/right during next update.
     * Space will apply force vertically during the next update.
     *
     * @param keyCode The key that was interacted with.
     * @param isPressed When true the key has been pressed, when false, the key has been released.
     */
    public void handleInput(int keyCode, boolean isPressed) {
        if(keyCode == KeyEvent.VK_LEFT) {
            keyLeftIsPressed = isPressed;
        } else if(keyCode == KeyEvent.VK_RIGHT) {
            keyRightIsPressed = isPressed;
        } else if(keyCode == KeyEvent.VK_SPACE && isPressed) {
            jump = true;
        }
    }

    /**
     * Does not use the amount yet. This method will just
     * cause the player to die from any damage taken.
     *
     * @param amount The amount of damage to apply.
     */
    public void damagePlayer(int amount) {
        isDead = true;
    }

    /**
     * The player will have died when they took too much damage via damagePlayer()
     *
     * @return True when the player has died.
     */
    public boolean isDead() {
        return isDead;
    }

    /**
     * Gets the current score of the player.
     *
     * @return A number representing the player's current score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Increases the score by the specified amount.
     *
     * @param score The amount to modify the score by.
     */
    public void addScore(int score) {
        this.score += score;
    }

    /**
     * Call when the level should be considered ended by the player
     * reaching the end flag.
     */
    public void activateFlag() {
        reachedFlag = true;
    }

    /**
     * Will be set true by the activateFlag() method to indicate the level has ended.
     *
     * @return True when the end flag of the level has been reached.
     */
    public boolean hasReachedFlag() {
        return reachedFlag;
    }

    /**
     * Moves based on the translationVector, but clamps the movement within the bounds of the play space.
     * Compares objects that were collided with before/after the movement. If any new objects that can't be
     * entered have been entered as a result of the movement is cancelled and not applied.
     *
     * @param translationVector Added to position to calculate the new position.
     */
    private void moveWithinBounds(Position translationVector, int maxX, int maxY) {
        List<GameObject> collidedWith = objectManager.getObjectsCollidedWith(this);
        Position originalPosition = new Position(position);
        int newX = position.x+translationVector.x;
        int newY = position.y+translationVector.y;
        if(newX < 0) newX = 0;
        else if(newX > maxX) newX = maxX;
        if(newY < 0) newY = 0;
        else if(newY > maxY) newY = maxY;
        position.setPosition(newX, newY);
        List<GameObject> collidedWithAfterMove = objectManager.getObjectsCollidedWith(this);

        // Remove all objects that were already collided with to ignore them
        for(GameObject obj : collidedWith) {
            collidedWithAfterMove.remove(obj);
        }
        // If colliding with a new object that can't be entered cancel the movement.
        for(GameObject obj : collidedWithAfterMove) {
            if(!obj.canEnter()) {
                position = originalPosition;
                return;
            }
        }
    }
}
