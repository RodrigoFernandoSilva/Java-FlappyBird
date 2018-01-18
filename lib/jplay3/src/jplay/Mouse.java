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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Class responsible for handling mouse actions.
 */
public class Mouse extends InputBase implements MouseMotionListener, MouseListener {

    private Point mousePosition;

    private InputAction leftButton;
    private InputAction middleButton;
    private InputAction rightButton;

    public static final int BUTTON_LEFT = 1;
    public static final int BUTTON_MIDDLE = 2;
    public static final int BUTTON_RIGHT = 3;

    /**
     * Class constructor.
     * Creates an instance of the mouse positioned at (x=0, y=0).
     * The behavior of the buttons (right, middle, left) is DECTECT_INITIAL_PRESS_ONLY.
     */
    public Mouse()
    {
            mousePosition = new Point(0, 0);

            //Add the default buttons
            leftButton   = new InputAction(InputBase.DETECT_INITIAL_PRESS_ONLY);
            middleButton = new InputAction(InputBase.DETECT_INITIAL_PRESS_ONLY);
            rightButton  = new InputAction(InputBase.DETECT_INITIAL_PRESS_ONLY);
    }

    /**
     *Returns the position of the mouse on the screen.
     *@return Point
     */
    public Point getPosition()
    {
            return mousePosition;
    }

    /**
     * Returns true if the left button is pressed, otherwise false.
     *@return boolean
     */
    public boolean isLeftButtonPressed()
    {
            return leftButton.isPressed();
    }

    /**
     * Returns true if the middle button is pressed, otherwise false.
     *@return boolean
     */
    public boolean isMiddleButtonPressed()
    {
            return middleButton.isPressed();
    }

    /**
     * Returns true if the right button is pressed, otherwise false.
     *@return boolean
     */
    public boolean isRightButtonPressed()
    {
            return rightButton.isPressed();
    }

    /**
     * Sets the behavior of the mouse.
     * @param numberBotton it's represent the mouse button and can be BUTTON_LEFT, BUTTON_MIDDLE or BUTTON RIGHT.
     * @param behabior it's can be DETECT_EVERY_PRESS or DETECT_INITIAL_PRESS_ONLY.
     */
    public void setBehavior(int numberBotton, int behavior)
    {
            switch(numberBotton)
            {
                case BUTTON_LEFT:   leftButton.setBehavior(behavior);   break;
                case BUTTON_MIDDLE: middleButton.setBehavior(behavior); break;
                case BUTTON_RIGHT:  rightButton.setBehavior(behavior);  break;
            }
    }

    public void mouseClicked(MouseEvent e)
    {
    }


    public void mousePressed(MouseEvent e)
    {
            switch (e.getButton())
            {
                case MouseEvent.BUTTON1: leftButton.press();   break;
                case MouseEvent.BUTTON2: middleButton.press(); break;
                case MouseEvent.BUTTON3: rightButton.press();  break;
            }
    }

    public void mouseReleased(MouseEvent e)
    {
            switch (e.getButton())
            {
                case MouseEvent.BUTTON1: leftButton.release();   break;
                case MouseEvent.BUTTON2: middleButton.release(); break;
                case MouseEvent.BUTTON3: rightButton.release();  break;
            }
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e)
    {
            mousePosition = e.getPoint();
    }

    public void mouseMoved(MouseEvent e)
    {
            mousePosition = e.getPoint();
    }

    /**
     * Returns true if the mouse is over an object, otherwise, return false.
     * @param obj any GameObject.
     * @return boolean
     */
    
    public boolean isOverObject(GameObject obj)
    {
            Point min = new Point( (int)obj.x, (int)obj.y);
            Point max = new Point( (int)(obj.x + obj.width), (int)(obj.y + obj.height));
            return isOverArea(min, max);
    }

    /**
     * Returns true if the mouse is over an object, otherwise, return false.
     * @param start initial point of the area.
     * @param end final point of the area.
     * @return boolean
     */
    public boolean isOverArea( Point start, Point end )
    {
            return  isOverArea(start.x, start.y, end.x, end.y);
    }

    /**
     * Returns true if the mouse is over an area, otherwise, return false
     * @minX minus value of the axes X.
     * @minY minus value of the axes Y.
     * @maxX minus value of the axes X.
     * @maxX minus value of the axes Y.
     * @return boolean
     */
    public boolean isOverArea( int minX, int minY, int maxX, int maxY )
    {
            if ((mousePosition.x < minX) || (mousePosition.x > maxX))
                return false;

            if ((mousePosition.y < minY) || (mousePosition.y > maxY))
                return false;

            return true;
    }

}
