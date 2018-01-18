/**
 * The use this code commercially must only be done with permission of the author.
 * Any modification shall be advised and sent to the author.
 * The author is not responsible for any problem therefrom the use of this frameWork.
 * @author Gefersom Cardoso Lima
 * @version 1.0
 * @since 01/2011
 * Federal Fluminense University
 * Computer Science
 */

package jplay;

/**
 * The most basic class presents in the framework.
 * Base class for almost all classes of this framework.
 */

public class GameObject {

    /** Image position on the screen. Axis x (horizontal).
     * When its value is used for drawing it's converted to an integer.*/
    public double x;

    /** Image position on the screen. Axis y (vertical).
     * When its value is used for drawing it's converted to an integer.
     */
    public double y;

    /** The width in pixels of the image.*/
    public int width;

    /** The height in pixels of the image.*/
    public int height;

    /**
     * Create a GameObject positioned in the x =0, y=0, and its dimension is width = 0 and height = 0.
     */

    protected double rotation = 0;

    public GameObject()
    {
            this.x = 0;
            this.y = 0;
            this.width = 0;
            this.height = 0;
    }

    /**
     * Method used for knowing if a GameObject collided with other GameObject.
     * @param GameObject Target GameObject to verify if there was a collision.
     * @return boolean - true if collided, false otherwise.
     */
    public boolean collided(GameObject obj)
    {
            return Collision.collided(this, obj);
    }
}
