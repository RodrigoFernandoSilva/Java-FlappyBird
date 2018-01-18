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

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

/**
 * Class responsible for animating a GameImage using pieces of the image, such as frames.
 */
public class Animation extends GameImage
{
    private int initialFrame;
    private int currFrame;
    private int finalFrame;

    private int totalFrames;

    private boolean playing;
    private boolean loop;
    private boolean drawable;

    //Each frame has its own time
    private long frameDuration[];
    private long totalDuration;


    //It keeps the time when a frame was changed
    private long lastTime;

    /**
     * The constructor creates a class object animation.
     * The sequence set up to start in the frame one and goes until the lastFrame
     * that is equal to totalFrames.
     * For example: setSequence(0, totalFrames).
     * 0 = initial frame.
     * lastFrame = totalFrames.
     * @param fileName Name and image path.
     * @param totalFrames Number of frames that form the image.
     * @param It says if the animation is performed repeatedly. If the value is
     * true when the last frame is shown the next frame will be the first.
     * However, if the value is false when the last frame is shown the animation will
     * remain showing the last frame indefinitely.
     */
    public Animation(String fileName, int totalFrames, boolean loop)
    {
            super(fileName);
            this.totalFrames = totalFrames;
            this.width = super.image.getWidth(null) / totalFrames;
            this.height = super.image.getHeight(null);
            this.lastTime = System.currentTimeMillis();
            this.playing = true;
            this.drawable = true;
            this.frameDuration = new long[totalFrames];
            setSequence(0, totalFrames, loop);
    }

    /**
     * Overloading of the constructor public Animation(String fileName, int totalFrames, boolean loop).
     * The parameter 'boolean loop' has the value true.
     */
    public Animation(String fileName, int totalFrames)
    {
            this(fileName, totalFrames, true);
    }

    /**
     * Overloading of the constructor public Animation(String fileName, int totalFrames, boolean loop).
     * The parameter 'boolean loop' has the value true.
     * The parameter 'int totalFrames' is equal to 1.
     */
    public Animation(String fileName)
    {
            this(fileName, 1, true);
    }

    /**
     * Set up the time which the frame will be shown on screen.
     * @param frame Number of the frame
     * @param time Milliseconds time which the frame will be shown on screen.
     */
    public void setDuration(int frame, long time)
    {
            frameDuration[frame] = time;
    }

    /**
     * Return the time in which the frame is shown on screen.
     * @param frame number of frame
     * @return long - the time in milliseconds
     */
    public long getDuration(int frame)
    {
            return frameDuration[frame];
    }

    /**
     * Set the initial and final frame in the sequence of animation.
     * The sequence will run indefinitely.
     * @param initialFrame
     * @param finalFrame
     */
    public void setSequence(int initialFrame, int finalFrame)
    {
            setSequence(initialFrame, finalFrame, true);
    }

    /**
     * Set the initial and final frame in the sequence of animation. And if the
     * animation will run indefinitely.
     * @param initialFrame
     * @param finalFrame
     * @param loop
     */
    public void setSequence(int initialFrame, int finalFrame, boolean loop)
    {
            setInitialFrame(initialFrame);
            setCurrFrame(initialFrame);
            setFinalFrame(finalFrame);
            setLoop(loop);
    }

    /**
     * Set the initial and final frame in the sequence of animation and the runtime.
     * @param initialFrame
     * @param finalFrame
     * @param time
     */
    public void setSequenceTime(int initialFrame, int finalFrame, long time)
    {
            setSequenceTime(initialFrame, finalFrame, true, time);
    }

    /**
     * Set the initial and final frame in the sequence of animation, the runtime
     * and if it will run indefinitely.
     * @param initialFrame
     * @param finalFrame
     * @param time
     * @param loop True for indefinitely, false otherwise.
     */

    public void setSequenceTime(int initialFrame, int finalFrame, boolean loop, long time)
    {
            setSequence(initialFrame, finalFrame, loop);
            time = time / (finalFrame - initialFrame + 1);
            for (int i = initialFrame; i <= finalFrame; i++)
                this.frameDuration[i] = time;
    }

    /**
     * This method tells whether the animation is looped.
     * @return boolean
     */
    public boolean isLooping()
    {
            return loop;
    }

     /*
     * Sets the time for all frames.
     * When the time is passed, the division between totalDuration and totalFrames
     *  it would leave some rest:
     *  Example:
     *      totalDuration = 100
     *      totalFrames = 11
     *      timeFrame = 100/11 = 9
     *      rest = 100 - 11 * 9 = 1
     *
     * So, the real totalDuration is (time / numberFrames) * numberFrames
      * @param time milisecond time
     */

    public void setTotalDuration(long time)
    {
            long timeFrame = time/ totalFrames;
            totalDuration = timeFrame * totalFrames;
            for(int i=0; i < frameDuration.length; i++)
                frameDuration[i] = timeFrame;
    }

    /**
     * Returns the sum of all time frames.
     * @return long
     */

    public long getTotalDuration()
    {
           return totalDuration;
    }

    /**
     * Method responsible for performing the change of frames.
     */
    public void update()
    {
            if (playing)
            {
                    long time = System.currentTimeMillis();
                    if ( time - lastTime > frameDuration[currFrame] && finalFrame != 0)
                    {
                        currFrame++;
                        lastTime = time;
                    }

                    if (currFrame == finalFrame && loop)
                    {
                        currFrame = initialFrame;
                    }
                    else
                        if( (!loop) && (currFrame+1 >= finalFrame) )
                        {
                            currFrame = finalFrame - 1;
                            playing = false;
                        }
            }
    }

    /**
     * Stops execution and puts the initial frame as the current frame.
     */
    public void stop()
    {
            this.currFrame = initialFrame;
            this.playing = false;
    }

    /**
     * Method responsible for starting the execution of the animation.
     */
    public void play()
    {
            this.playing = true;
    }

    /**
     * Method responsible for pausing the animation.
     */
    public void pause()
    {
            this.playing = false;
    }

    /**
     * Sets the initial frame of the sequence of frames.
     * @param frame number of frame
     */
    public void setInitialFrame(int frame)
    {
            this.initialFrame = frame;
    }

    /**
     * Returns the number of initial frame.
     * @return int
     */
    public int getInitialFrame()
    {
            return initialFrame;
    }

    /**
     * Sets the final frame of the sequence of frames.
     * @param frame number of frame.
     */
    public void setFinalFrame(int frame)
    {
            this.finalFrame = frame;
    }

    /**
     * Returns the number of final frame of the sequence of frames.
     * @return int
     */
    public int getFinalFrame()
    {
            return finalFrame;
    }

    /**
     * Sets the current frame that will be drawn.
     * @param frame number of frame.
     */
    public void setCurrFrame(int frame)
    {
            currFrame = frame;
    }

    /**
     * Returns the number of current frame.
     * @return int
     */
    public int getCurrFrame()
    {
            return currFrame;
    }

    /**
     * Returns true if the animation is being executed, false otherwise.
     * @return boolean
     */
    public boolean isPlaying()
    {
            return playing;
    }

    /**
     * This method is responsible for not allowing to drawn the animation on screen.
     */
    public void hide()
    {
            this.drawable = false;
    }

    /**
     * Method responsible for allowing the animation to draw on the screen.
     */
    public void unhide()
    {
            this.drawable = true;
    }

    /**
     * Method responsible for informing the class that the animation will not be run indefinitely.
     * True to run indefinitely, false otherwise.
     */

    public void setLoop(boolean value)
    {
            this.loop = value;
    }

    /**
     * Draws the animtion on escreen.
     */
     @Override
    public void draw()
    {
        if (drawable)
        {
            //Window.instance.getGameGraphics()
            //    .drawImage(image, (int)x, (int)y, (int)x + width, (int)y + height,
            //        currFrame * width, 0, (currFrame +1) * width, height, null);
        	double rot = rotation;

            Graphics2D g2d = (Graphics2D) Window.getInstance().getGameGraphics();
            AffineTransform tx = new AffineTransform();

            tx.setToRotation( -rot, width/2,height/2);

            int newy = (int) (x*Math.sin(rot)+y*Math.cos(rot));
            int newx = (int) (x*Math.cos(rot)-y*Math.sin(rot));

            g2d.setTransform(tx);
            g2d.drawImage(image, newx, newy, newx + width, newy + height,
                            currFrame * width, 0, (currFrame +1) * width, height, null);
        }
    }


//    @Override
//    public void draw()
//    {
//            if (drawable)
//                Window.getInstance().getGameGraphics().drawImage(image, (int) x,
//                        (int) y, (int) x + width, (int) y + height, currFrame * width,
//                        0, (currFrame + 1) * width, height, null);
//    }
}
