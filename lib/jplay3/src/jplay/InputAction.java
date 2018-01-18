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

/**
 * Class used for handle actions: release, press or keep pressed a button or a key.
 * This class is not associated with a key or a button.
 * @see Keyboard and a Mouse.
 */

class InputAction {

    private static final int STATE_RELEASED = 0;
    private static final int STATE_PRESSED = 1;
    private static final int STATE_WAITING_FOR_RELEASE = 2;
    private int behavior;
    private int quantity;
    private int state;

    /**
     * Create a new InputAction with specific behavior, with the state = released and amount of clicks equal 0.
     * @param behavior - it's can be DETECT_EVERY_PRESS or DETECT_INITIAL_PRESS_ONLY
     * @version 1.0
     */
    public InputAction(int behavior)
    {
            this.behavior = behavior;
            state = STATE_RELEASED;
            quantity = 0;
    }

    
    /**
     * Set the behavior of the key or of the button.
     * @param behavior - it's can be DETECT_EVERY_PRESS or DETECT_INITIAL_PRESS_ONLY.
     * @see InputBase
     * @version 1.0
     */
    public synchronized void setBehavior(int behavior) {
        this.behavior = behavior;
    }


    /**
     * It's an overloaded method of the method press(int amount).
     * Its parameter is amount = 1.
     * @see InputBase
     * @version 1.0
     */    
    public synchronized void press() {
        press(1);
    }

    /**
     * Put the state of the button or of the key how pressed and associated with them an amount of pressed.
     * @param amout - How many times the key went pressed.
     * @version 1.0
     */   
    public synchronized void press(int amount) {
        if (state != STATE_WAITING_FOR_RELEASE) {
            this.quantity += amount;
            state = STATE_PRESSED;
        }
    }

    
    /**
     * Put the state of the button or of the key how released.
     * @version 1.0
     */   
    public synchronized void release() {
        state = STATE_RELEASED;
    }

    
    /**
    * Method used for knowing if a key is pressed.
    * @return boolean - true when a key is pressed, false otherwise.
    * @version 1.0
    */    
    public synchronized boolean isPressed() {
        return (getAmount() != 0);
    }

    
    /**
    * Return a quantity of clicks for the mouse
    * and for the keyboard the quantity of times button have been pressed.
    * If the behavior is DETECT_INITAL_PRESS_ONLY, this method will return only
    * the initial click. For return that the mouse clicked again the user needs release
    * If the behavior is DETECT_EVERY_PRESS, this method will return the amount
    * of the click or of the how many times the key have been pressed.
    * @return int - amount of pressed.
    * @see void press(int amount);
    * @version 1.0
    * */
    public synchronized int getAmount() {
        int quant = quantity;
        if (quant != 0) {
            if (state == STATE_RELEASED) {
                quantity = 0;
            } else if (behavior == InputBase.DETECT_INITIAL_PRESS_ONLY) {
                state = STATE_WAITING_FOR_RELEASE;
                quantity = 0;
            }
        }
        return quant;
    }
}
