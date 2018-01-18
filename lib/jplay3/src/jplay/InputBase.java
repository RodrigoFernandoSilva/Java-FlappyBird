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
 * Class used to handle actions for buttons or for keys.
 */
public class InputBase
{    
            /**
             * This behavior is responsible for detected the pressing
             * of the button or of the key while it's pressing.
             */
            public static final int DETECT_EVERY_PRESS = 0;
            
            
            /**
             * Behavior responsible for only allowing the detection of the first
             * press of the button or of the click, after that, It's necessary release
             * the button or the key for the next detection.
             */
            public static final int DETECT_INITIAL_PRESS_ONLY = 1;

}
