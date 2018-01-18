/*
 * The use this code commercially must only be done with permission of the author.
 * Any modification shall be advised and sent to the author.
 * The author is not responsible for any problem therefrom the use of this frameWork.
 *
 * @author Gefersom Cardoso Lima
 * Federal Fluminense University
 * Computer Science
 */
package jplay;

import java.awt.Point;

/**
 * Class used to know whether two GameObjects collided.
 */
public class Collision {

    /**
    * Method used to know whether two areas on the screen are touching each other.
    * @param min1 point of the top left of the first area.
    * @param max1 point from under of the right of the first image.
    * @param min2 point of the top left of the second area.
    * @param max3 point from under of the right of the second image.
    * @return boolean - return true if they are touching each other, false otherwise.
    */
    public static boolean collided(Point min1, Point max1, Point min2, Point max2)
    {
            if (min1.x >= max2.x || max1.x <= min2.x) {
                return false;
            }
            if (min1.y >= max2.y || max1.y <= min2.y) {
                return false;
            }
            return true;
    }

    /**
    * Static method used to know whether two GameObjects are touching each other.
    * @param obj1 origin GameObject
    * @param obj2 target GameObject
    * @return boolean - return true when they are touching each other, false otherwise.
    */
    public static boolean collided(GameObject obj1, GameObject obj2)
    {
            Point minObj1 = new Point((int) obj1.x, (int) obj1.y);
            Point maxObj1 = new Point((int) (obj1.x + obj1.width), (int) (obj1.y + obj1.height));

            Point minObj2 = new Point((int) obj2.x, (int) obj2.y);
            Point maxObj2 = new Point((int) (obj2.x + obj2.width), (int) (obj2.y + obj2.height));

            return (Collision.collided(minObj1, maxObj1, minObj2, maxObj2));
        }
}
