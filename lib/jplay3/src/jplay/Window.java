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

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * Main class of the framework. Your main function is to drawn images on screen.
 */
public class Window extends JFrame{

    private static Window instance;
    private Mouse mouse;
    private Keyboard keyboard;
    private BufferStrategy buffer;
    private Graphics graphics;
    private long currTime;
    private long lastTime;
    private long totalTime;
    private DisplayMode displayMode;
    private GraphicsDevice device;

    /*
     * Creates an Window with width and height in pixels.
     */
    public Window(int width, int height)
    {
            device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            displayMode = new DisplayMode(width, height, 16, DisplayMode.REFRESH_RATE_UNKNOWN);
            mouse = new Mouse();
            keyboard = new Keyboard();

            addMouseListener(mouse);
            addMouseMotionListener(mouse);
            addKeyListener(keyboard);

            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(width,height);
            setLocationRelativeTo(null);
            setUndecorated(true);            
            setVisible(true);

            createBufferStrategy(2);
            buffer = getBufferStrategy();

            graphics = buffer.getDrawGraphics();
            currTime = System.currentTimeMillis();
            lastTime = 0;
            totalTime = 0;

            instance = this;
    }

    /**
     * Returns an instance of the current window.
     * @return Window
     */
    static Window getInstance()
    {
            return instance;
    }

    /**
     * Returns an instance of keyboard.
     * @return Keyboard
     */
    public Keyboard getKeyboard()
    {
            return keyboard;
    }

    /**
     * Returns an instance of mouse.
     * @return Mouse
     */
    public Mouse getMouse()
    {
            return mouse;
    }

    /**
     * Returns an instance of Graphics.
     * @return graphics
     */
    public Graphics getGameGraphics()
    {
            return graphics;
    }

    /**
     * Refreshes the window with the new information drawn in the buffer.
     */
    public void update()
    {
            graphics.dispose();
            buffer.show();
            Toolkit.getDefaultToolkit().sync();
            graphics = buffer.getDrawGraphics();
            lastTime = currTime;
            currTime = System.currentTimeMillis();
            totalTime += currTime - lastTime;
    }

    /**
     * Delay the execution of the program.
     * @param time Millisecond time.
     */
    public void delay(long time)
    {
            try
            {
                Thread.sleep(time);
            }
            catch (InterruptedException ex)
            {
                Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    /**
     * Returns the time passed between the previous frame and the current frame.
     * @return long Millisecond time.
     */
    public long deltaTime()
    {
            return currTime - lastTime;
    }

    /**
     * Total time passed since the start of creation of the window.
     * @return long Millisecond time.
     */
    public long timeElapsed()
    {
            return totalTime;
    }

    /**
     * Draws a message on the screen.
     * @param message
     * @param x value on the x axis.
     * @param y value on the y axis.
     * @param color
     */
    public void drawText(String message, int x, int y, Color color)
    {
            graphics.setColor(color);
            graphics.drawString(message, x, y);
    }

    /**
     * Draws a message on the screen.
     * @param message
     * @param x value on the x axis.
     * @param y value on the y axis.
     * @param color
     * @param font
     */
    public void drawText(String message, int x, int y, Color color, Font font)
    {
            Graphics2D g2 = (Graphics2D) graphics;
            g2.setFont(font);
            g2.setColor(color);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2.drawString(message, x, y);
    }

    /**
     * Closes the window and exit the program.
     */
    public void exit()
    {
            dispose();
            System.exit(0);
    }

    /**
     * Creates a mouse cursor using an image.
     * @param imageName image path.
     * @return Cursor
     */
    public Cursor createCustomCursor(String imageName)
    {
            Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(
                                    Toolkit.getDefaultToolkit().getImage(imageName),
                                    new java.awt.Point(),
                                    "cursor");
            return cursor;
    }

    /**
     * Cleans the window.
     * @param color
     */
    public void clear(Color color)
    {
            graphics.setColor(color);
            graphics.fillRect(0, 0, getWidth(), getHeight());
    }

    /**
     * Returns an array with the display modes that the screen can work.
     * @return DisplayMode[]
     * @see DisplayMode
     */
    public DisplayMode[] getCompatibleDisplayMode()
    {
            return device.getDisplayModes();
    }

    /**
     * Set the display mode.
     * @param displayMode
     * @see DisplayMode
     */
    public void setDisplayMode(DisplayMode displayMode)
    {
            if ( isDisplayModeCompatible(displayMode)  == false){
                //JOptionPane.showMessageDialog(null, "Resolution is not compatible with this display.");
            }
            else{
                this.displayMode = displayMode;
            }
    }

    /**
     * Returns true if the display is capable of work with this display mode, false otherwise.
     * @param displayMode
     * @return boolean
     */
    public boolean isDisplayModeCompatible(DisplayMode displayMode)
    {
            DisplayMode goodModes[] = device.getDisplayModes();
            int i =0;
            boolean compatible = false;
            while(!compatible && i < goodModes.length)
            {
                    if ( goodModes[i].getWidth() == displayMode.getWidth() && goodModes[i].getHeight() == displayMode.getHeight())
                        compatible = true;
                    i++;
            }
            return compatible;
    }

    /**
     * Put the screen in a full mode screen.
     */
    public void setFullScreen()
    {
            DisplayMode old = device.getDisplayMode();
            super.setIgnoreRepaint(true);
            super.setResizable(false);
            this.device.setFullScreenWindow(instance);
            try {
                device.setDisplayMode(displayMode);
            }
            catch (IllegalArgumentException ex)
            {
                device.setDisplayMode(old);
            }
            
    }

    /**
     * Disable the full display mode.
     */
    public void restoreScreen()
    {
            device.setFullScreenWindow(null);
            super.setLocationRelativeTo(null);
    }

    /**
     * Sets the size of the screen
     * @param width
     * @param height
     */
    @Override
    public void setSize(int width, int height)
    {
            setResizable(true);
            super.setSize(width, height);
            setDisplayMode(new DisplayMode(width, height, 16, DisplayMode.REFRESH_RATE_UNKNOWN));
            super.setLocationRelativeTo(null);
            setResizable(false);
    }

    /**
     * Sets the dimension of the screen.
     * @param d
     */
    @Override
    public void setSize(Dimension d)
    {
            this.setSize(d.width, d.height);
    }
    
}
