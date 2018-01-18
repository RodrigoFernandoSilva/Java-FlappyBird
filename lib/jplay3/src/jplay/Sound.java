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

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;


/**
 *
 * Class responsible for controlling the execution of sounds.
 */
public class Sound
{
    private AudioFormat audioFormat;
    private AudioInputStream audioInputStream;
    private SourceDataLine sourceDataLine;
    private boolean stop;
    private boolean pause;
    private float volume;
    private boolean volumeChanged;
    private Song song;
    private boolean loop;
    private String fileName;

    /**
     * Class Constructor.
     * Create an instance of this class.
     * The sound will be executed only once.
     * @param fileName
     */
    public Sound(String fileName)
    {
            loop = false;
            this.fileName = fileName;
            load(fileName);
    }

    /**
     * Loads a sound.
     * @param fileName File path.
     */
    public void load(String fileName)
    {
            try
            {
                audioInputStream = AudioSystem.getAudioInputStream(new File(fileName));
            }
            catch (UnsupportedAudioFileException ex)
            {
                Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (IOException ex)
            {
                Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
            }

            audioFormat = audioInputStream.getFormat();
            DataLine.Info dataLineInfo = new DataLine.Info( SourceDataLine.class, audioFormat);

            try
            {
                sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
            }
            catch (LineUnavailableException ex)
            {
                Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    /**
     * Method responsible for increasing the volume.
     * @param value This value will be added to the current volume value.
     */
    public void increaseVolume(float value)
    {
            volume += value;
            volumeChanged = true;
    }

    /**
     * Method responsible for decrease the volume.
     * @param value This value will be subtracted from the current volume value.
     */
    public void decreaseVolume(float value)
    {        
            volume -= value;
            volumeChanged = true;
    }

    /**
     * Sets the current volume.
     * @param value
     */
    public void setVolume(float value)
    {
            volume = value;
            volumeChanged = true;
    }

    /**
     * Starts the execution of the sound.
     */
    public void play()
    {
            if (pause == false)
            {
                    song = new Song();
                    song.start();
            }
            else
                //if (pause == true)
                    pause = false;
    }

    /**
     * Stops the execution of the sound.
     */
    public void stop()
    {
            stop = true;
            if (song == null) song = null;
    }

    /**
     * Pauses the execution of the sound.
     */
    public void pause()
    {
            pause = true;
    }

    private class Song extends  Thread
    {
            byte tempBuffer[] = new byte[1000];

            @Override
            public void run()
            {
                try
                {
                        sourceDataLine.open(audioFormat);
                        sourceDataLine.start();

                        //while there are dates to execute and stop == false
                        int count = 0;
                        while( count != -1 && stop == false)
                        {
                                if( pause == false )
                                    count = audioInputStream.read(tempBuffer,0,tempBuffer.length);
                                else
                                    count = 0;

                                //If there are dates to execute
                                if(count > 0 )
                                {
                                    if (volumeChanged)
                                    {
                                            FloatControl volControl = (FloatControl) sourceDataLine.getControl(FloatControl.Type.MASTER_GAIN);
                                            volControl.setValue(volume);
                                            volumeChanged = false;
                                    }
                                    sourceDataLine.write(tempBuffer, 0, count);
                                }
                        }
                        
                        sourceDataLine.drain();
                        sourceDataLine.close();                       

                        if (loop && stop == false)
                        {
                            load(fileName);
                            setVolume(volume);
                            song = new Song();
                            song.start();
                        }
                        
                        stop = false;
                        song = null;
                }
                catch (Exception e)
                {
                        e.printStackTrace();
                }
            }
    }

    /**
     *This method is responsible for making the sound to run indefinitely.
     * @param value
     */
    public void setRepeat(boolean value)
    {
        loop = value;
    }
    
    /**
     * Returns true if the sound is running, false otherwise.
     * @return boolean
     */
    public boolean isExecuting()
    {
        return song != null;
    }

}

