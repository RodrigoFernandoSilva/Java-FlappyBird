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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Hashtable;


/**
 * Class responsible for handling the keys of keyboard and its behavior.
 */

public final class Keyboard extends InputBase implements KeyListener
{
    /**The direction key for above of the keyboard.*/
    public static final int UP_KEY = 38;

    /**The direction key for left of the keyboard.*/
    public static final int LEFT_KEY = 37;

    /**The direction key for right of the keyboard.*/
    public static final int RIGHT_KEY = 39;

    /**The direction key for under of the keyboard.*/
    public static final int DOWN_KEY = 40;

    /**The ESC key of the keyboard.*/
    public static final int ESCAPE_KEY = 27;

    /**The space bar of the keyboard.*/
    public static final int SPACE_KEY = 32;
    
    /**The enter key of the keyboard.*/
    public static final int ENTER_KEY = 10;

    private Hashtable keysPressed;

    /**
     * Create instance of class Keyboard with the following keys and its behavior:
     * UP_KEY, LEFT_KEY, RIGHT_KEY, DOWN_KEY have the behavior DETECT_EVERY_PRESS, 
     * and the keys ESCAPE_KEY, SPACE_KEY, ENTER_KEY have the behavior
     * DETECT_INITIAL_PRESS_ONLY.
     * @version 1.0
     */
    public Keyboard()
    {
            keysPressed = new Hashtable();

            //Add the defaults keys 
            addKey(UP_KEY,     Keyboard.DETECT_EVERY_PRESS);
            addKey(LEFT_KEY,   Keyboard.DETECT_EVERY_PRESS);
            addKey(RIGHT_KEY,  Keyboard.DETECT_EVERY_PRESS);
            addKey(DOWN_KEY,   Keyboard.DETECT_EVERY_PRESS);
            addKey(ESCAPE_KEY, Keyboard.DETECT_INITIAL_PRESS_ONLY);
            addKey(SPACE_KEY,  Keyboard.DETECT_INITIAL_PRESS_ONLY);
            addKey(ENTER_KEY,  Keyboard.DETECT_INITIAL_PRESS_ONLY);
    }

    /**
     * Method used for knowing if a key is pressed.
     * @param key The key code presents in 'Keyboard.' or in 'KeyEvent.'
     * @return boolean - True when it's pressed, otherwise false.
     * @see KeyEvent
     * @see Keyboard
     * @version 1.0
     */
    public boolean keyDown(int key)
    {
            if ( keysPressed.containsKey(key) )
            {
                InputAction temp = (InputAction) keysPressed.get(key);
                return temp.isPressed();
            }
            return false;
    }

    
    /**
     * Overloading the method void addKey(int key, int behavior), the behavior will be DETECT_INITAL_PRESS_ONLY.
     * @param key The key code. The key code can be found in 'KeyEvent.'
     * @see KeyEvent
     * @version 1.0
     */
    public void addKey(int key)
    {
            addKey(key, Keyboard.DETECT_INITIAL_PRESS_ONLY);
    }


    /**
     * Method used for adding a key to an instance of the keyboard and its behavior.
     * @param key The key code. The key code can be found in 'KeyEvent.'.
     * @param behavior The behavior of the key can be found in 'Keboard.' or in 'InputBase.'.
     * @see KeyEvent
     * @see InputBase
     * @version 1.0
     */
    public void addKey(int key, int behavior)
    {
            removeKey(key);
            keysPressed.put(key, new InputAction(behavior));            
    }

    /**
     * Remove a key of the instance of the keyboard. If the key not exist in the
     * instance of the keyboard don't generate any error.
     * @pram key The key code. It can be found in 'KeyEvent.' or in 'Keyboard.'.
     * @see KeyEvent
     * @see Keyboard
     * @version 1.0
     */
    public void removeKey(int key)
    {
            keysPressed.remove(key);
    }

    /** 
     * Set a new behavior for a key. If the key not exist in the instance of the Keyboard it's not generate any error.
     * @param key The key code. It can be found in 'KeyEvent.' or in 'Keyboard.'
     * @see KeyEvent
     * @see Keyboard
     * @version 1.0
     */
    public void setBehavior(int key, int behavior)
    {
            if (keysPressed.containsKey(key))            
                addKey(key, behavior);            
    }

    public void keyTyped(KeyEvent e)
    {
            //e.consume();
    }

    public void keyPressed(KeyEvent e)
    {
            int key = e.getKeyCode();
            if ( keysPressed.containsKey(key) )
            {
                InputAction temp = (InputAction) keysPressed.get(key);
                temp.press();
            }
            //e.consume();
    }

    public void keyReleased(KeyEvent e)
    {
            int key = e.getKeyCode();
            if ( keysPressed.containsKey(key) )
            {
                InputAction temp = (InputAction) keysPressed.get(key);
                temp.release();
            }
            //e.consume();
    }
   
}
