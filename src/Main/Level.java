/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Bird.Bird;
import static Main.DeltaTime.deltaTime;
import static Main.DeltaTime.allThreadSleep;
import static Main.Main.window;
import static Main.Main.levelOk;
import static Main.Main.playing;
import static Main.Main.levelSpeed;
import static Main.Main.levelDistancePipesX;
import static Main.Main.levelDistancePipesY;
import static Main.Main.playing;
import static java.lang.Thread.sleep;

import jplay.GameImage;

import java.util.logging.Logger;
import java.util.Random;

/**
 *
 * @author Rodrigo Fernando da silva
 */
public class Level extends Thread{
    
    public int lastPipe;
    
    GameImage floor = new GameImage("Images/Scene/Floor.png");
    GameImage[] downPipe = new GameImage[3];
    GameImage[] upPipe = new GameImage[downPipe.length];
    
    Random generator = new Random();
    
    int randomUp = levelDistancePipesY + 55;
    int randomDown = (window.getHeight() - floor.height - 55 - randomUp);
    
    @Override
    @SuppressWarnings("SleepWhileInLoop")
    public void run () {
        
        int i;
        
        StartPipes();
        
        levelOk = true;
        while (playing.isPlaying) {
            while (playing.GetPause()) {
                try {
                    sleep(1);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Bird.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                }
            }
            
            if (!playing.GetBird().GetGameOver()) {
                //Move the pipes and floor
                floor.x -= (levelSpeed * deltaTime);
                for (i = 0; i < downPipe.length; i ++) {
                    downPipe[i].x -= (levelSpeed * deltaTime);
                    upPipe[i].x -= (levelSpeed * deltaTime);
                }
                
                
                //Whem the floor is arriving on the its end, it going to be put on its initial point
                //for it do not exit the window
                if (floor.x <= -floor.width / 2) {
                    floor.x += floor.width / 2;
                }
                
                //After the pipes exit the windows, they going to be put after the last pipe
                for (i = 0; i < downPipe.length; i ++) {
                    if (downPipe[i].x < -downPipe[i].width) {
                        downPipe[i].x += (downPipe[lastPipe].x + downPipe[lastPipe].width + levelDistancePipesX);
                        upPipe[i].x += (downPipe[lastPipe].x + downPipe[lastPipe].width + levelDistancePipesX);
                        
                        downPipe[i].y = generator.nextInt(randomDown) + randomUp;
                        upPipe[i].y = downPipe[i].y - levelDistancePipesY;
                        upPipe[i].y -= upPipe[i].height;
            
                        lastPipe = i;
                        playing.ResetBirdsPassed();
                    }
                }
            }
            
            try {
                sleep(allThreadSleep);
            } catch (InterruptedException ex) {
                Logger.getLogger(Level.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        }
        
    }
    
    //################    FLOOR    ################//

    public GameImage GetFloor () {
        return floor;
    }
    
    public void DrawFloor () {
        floor.draw();
    }
    
    public void SetFloorPosition (double x, double y) {
        floor.x = x;
        floor.y = y;
    }
    
    public boolean CollisionWithFloor (GameImage image) {
        return floor.collided(image);
    }
    
    
    //################    PIPES    ################//
    
    @SuppressWarnings("SleepWhileInLoop")
    public void StartPipes () {
        int x = window.getWidth(); 
        
        lastPipe = downPipe.length - 1;
        
        for (int i0 = 0; i0 < downPipe.length; i0 ++) {
            downPipe[i0] = new GameImage("Images/Obstacle/DownPipe.png");
            upPipe[i0] = new GameImage("Images/Obstacle/UpPipe.png");
            
            downPipe[i0].x = x + 300;
            upPipe[i0].x = downPipe[i0].x;
            x += levelDistancePipesX;
            
            downPipe[i0].y = generator.nextInt(randomDown) + randomUp;
            upPipe[i0].y = downPipe[i0].y - levelDistancePipesY;
            upPipe[i0].y -= upPipe[i0].height;
            
            //If don`t have this 'sleep', the 'for' don`t work and the variable stay null
            try {
                sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(Level.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        }
    }
    
    public void DrawPipes() {
        for (int i = 0; i < downPipe.length; i ++) {
            downPipe[i].draw();
            upPipe[i].draw();
        }
    }
    
    public boolean GameImagePassed (GameImage image) {
        for (GameImage downPipe1 : downPipe) {
            if ((downPipe1.x + (downPipe1.width / 4)) < (image.x + (image.width))) {
                return true;
            }
        }
         return false;
    }
    
    public boolean CollisionWithPipes (GameImage image) {
        for (int i = 0; i < downPipe.length; i ++) {
            if (downPipe[i].collided(image) || upPipe[i].collided(image)) {
                return true;
            }
        }
        return false;
    }
    
}
