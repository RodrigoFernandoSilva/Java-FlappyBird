/*
 * The use this code commercially must only be done with permission of the author.
 * Any modification shall be advised and sent to the author.
 * The author is not responsible for any problem therefrom the use of this frameWork.
 *
 * @author Gefersom Cardoso Lima
 * Universidade Federal Fluminense - UFF - Brasil - 2010
 * Ciência da Computação
 */

package jplay;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class used to manipulate time.
 */
public class Time
{
    private javax.swing.Timer timer;
    private Font font;
    private Color color;
    private int currentSecond;
    private int hour;
    private int minute;
    private int second;
    private int x;
    private int y;
    private boolean crescentTime;

    /**
     * Creates an instance of the class Time. The time will have
     * house = 0, minute = 0, seconds = 0.
     * It will be drawn on the screen with the font = Arial, size= 20 and the color = yellow.
     * @param x value of the coordinate X.
     * @param y value of the coordinate Y.
     * @param crescentTime true if the is crescent, false otherwise.
     */
    public Time(int x, int y, boolean crescentTime)
    {
        this(0, 0, 0, x, y, crescentTime);
    }

    /**
     * Creates an instance of the class Time.
     * @param hour
     * @param minute
     * @param second
     * @param x
     * @param y
     * @param crescentTime true if the is crescent, false otherwise.
     */
    public Time( int hour, int minute, int second, int x, int y, boolean crescentTime )
    {
        this(hour, minute, second, x, y, new Font("Arial",Font.TRUETYPE_FONT, 20),Color.YELLOW, crescentTime );
    }

    /**
     * Creates an instance of the class Time.
     * @param hour
     * @param minute
     * @param second
     * @param x
     * @param y
     * @param font
     * @param color
     * @param crescentTime True if the time is crescent, false otherwise.
     */
    public Time( int hour, int minute, int second, int x, int y, Font font, Color color, boolean crescentTime )
    {
        this.x = x;
        this.y = y;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
        this.color = color;
        this.font = font;
        this.crescentTime = crescentTime;
        this.crescentTime = crescentTime;
        calculateSeconds();
        createAction();
    }

    private void createAction()
    {
        ActionListener action = new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                    if (currentSecond != 0 && (crescentTime == false) )
                        currentSecond--;
                    else
                    {
                        if (crescentTime)
                            currentSecond++;
                    }
                    hour = currentSecond / 3600;
                    minute = (currentSecond - hour * 3600) / 60;
                    second = currentSecond - hour * 3600 - minute * 60;
            }
        };
        this.timer = new javax.swing.Timer(1000, action);
        this.timer.start();
    }

    /**
     * Returns a string with the time. The time format is hour/min/second (00/00/00).
     * @return String
     */
    @Override
    public String toString()
    {
            String str = "";

            if (hour < 10) 
                str = "0" + Integer.toString(hour) + ":";
            else 
                str = Integer.toString(hour) + ":";
            
            if (minute < 10) 
                str += "0" + Integer.toString(minute) + ":";
            else 
                str += Integer.toString(minute) + ":";
            
            if (second < 10) 
                str += "0" + Integer.toString(second);
            else 
                 str += Integer.toString(second);
            
            return str;
    }

    /**
     * Draws a message before the time on the screen.
     * @param message
     */
    public void draw(String message)
    {
            Window.getInstance().drawText(message + toString(), x, y, color, font);
    }

    /**
    * Draws the time on the screen.
    */
    public void draw()
    {
          Window.getInstance().drawText(toString(), x, y, color, font);
    }

    /**
     * Sets the color used to drawn the time on the screen.
     * @param color
     */
    public void setColor(Color color)
    {
           this.color = color;
    }

    /**
     * Sets the font used to drawn the time on the screen.
     * @param color
     */
    public void setFont(Font font)
    {
          this.font = font;
    }

    private void calculateSeconds()
    {
            currentSecond = hour * 3600 + minute * 60 + second;
    }

    /**
     * Returns true when the time comes to zero, false otherwise. It's only work if the time is crescent.
     * @param boolean
     */
    public boolean timeEnded()
    {
          return (currentSecond == 0);
    }

    /**
     * Sets the hours.
     * @param hour
     */
    public void setHour( int hour )
    {
         setTime(hour, minute, this.second);
    }

    /**
     * Sets the minutes.
     * @param minute
     */
    public void setMinute( int minute )
    {
          setTime(this.hour, minute, this.second);
    }

    /**
     * Sets the seconds.
     * @param second
     */
    public void setSecond( int second )
    {
          setTime(this.hour, this.minute, second);
    }

    /**
     * Returns the hours.
     * @return int
     */
    public int getHour()
    {
          return this.hour;
    }

    /**
     * Returns the minutes.
     * @return int
     */
    public int getMinute()
    {
          return this.minute;
    }

    /**
     * Returns the seconds.
     * @return int
     */
     public int getSecond()
    {
          return this.second;
    }

    /**
     * Returns the time in seconds.
     * Example: 1h 29 min e 45 seconds will be returned as 1*60*60 + 29*60 + 45 = 217785 seconds.
     * @return long
     */
    public long getTotalSecond()
    {
         return this.currentSecond;
    }

    /**
     * Sets the current time.
     * @param hour
     * @param minute
     * @param seconds
     */
    public void setTime(int hour, int minute, int seconds)
    {
            this.hour = hour;
            this.minute = minute;
            this.second = seconds;
            calculateSeconds();
    }
}
