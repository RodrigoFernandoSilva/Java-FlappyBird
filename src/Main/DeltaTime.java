/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import static Main.Main.running;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rodrigo Fernando da Silva
 */
public class DeltaTime extends Thread{
    public static float deltaTime = 0.016f;
    public static long allThreadSleep = 16;
    
    @Override
    @SuppressWarnings({"SleepWhileInLoop", "UnusedAssignment"})
    public void run() {

        long last_time = System.nanoTime();
        long time;
        int delta_time;
        float difereca;
        
        double i;
        while(running){
            time = System.nanoTime();
            
            try {
                sleep(16);
            } catch (InterruptedException ex) {
                Logger.getLogger(DeltaTime.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            delta_time = (int) ((time - last_time) / 1000000);
            last_time = time;
            
            difereca = 16 - delta_time;
            allThreadSleep = delta_time + (int) difereca;
            
            difereca = (float) (0.016f - ((float) delta_time / 1000));
            deltaTime = ((float) delta_time / 1000) + difereca;
            
        }
    }
}

