/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import static Main.DeltaTime.allThreadSleep;
import static Main.Main.birdOk;
import static Main.Main.levelOk;
import static Main.Main.birdsEnable;
import static Main.Main.physics;
import static Main.Main.window;
import static java.lang.Thread.sleep;
import static Main.Main.sky;

import Bird.Bird;
import static Main.Main.SELECT_FILE;
import static Main.Main.keyboard;
import static Main.Main.select;
import com.sun.glass.events.KeyEvent;

import jplay.Keyboard;

import java.util.logging.Logger;
import jplay.GameImage;
import jplay.Sound;

/**
 *
 * @author Rodrigo Fernando da Silva
 */
public class Playing{
    
    public GameImage righArrow;
    public GameImage leftArrow;
    
    public int pauseIndex = 0;
    public GameImage newScore;
    public GameImage backToGame;
    public GameImage backToMenu;
    public GameImage pauseImage;
    
    final String SPRITES_FILE = "Images/Bird/Bird";
    
    public boolean pause;
    public boolean isPlaying;
    public boolean isBackToMenu;
    
    public int numberOfBirds;
    
    public Level level;
    
    public Bird bird;
    
    @SuppressWarnings("SleepWhileInLoop")
    public void Playing () {
        
        birdOk = false;
        levelOk = false;
        pause = false;
        isBackToMenu = false;
        
        pauseImage = new GameImage("Images/GUI/Pause/Pause.png");
        pauseImage.width = window.getWidth();
        pauseImage.height = window.getHeight();
        
        newScore = new GameImage("Images/GUI/Pause/NewScore.png");
        newScore.x = (window.getWidth() - newScore.width) / 2;
        newScore.y = window.getHeight() * 0.2;
        backToGame = new GameImage("Images/GUI/Pause/BackToGame.png");
        backToGame.x = (window.getWidth() - backToGame.width) / 2;
        backToGame.y = window.getHeight() * 0.55;
        backToMenu = new GameImage("Images/GUI/Pause/BackToMenu.png");
        backToMenu.x = (window.getWidth() - backToMenu.width) / 2;
        backToMenu.y = backToGame.y + backToMenu.height + 40;
        
        GameImage newScoreCongratulations = new GameImage("Images/GUI/GameOver/New Record Congratulation.png");
        newScoreCongratulations.x = window.getWidth() / 2;
        newScoreCongratulations.x -= newScoreCongratulations.width / 2;
        newScoreCongratulations.y = window.getHeight() * 0.2;
        
        GameImage PlayAgain = new GameImage("Images/GUI/GameOver/Play Again.png");
        PlayAgain.x = (window.getWidth() - PlayAgain.width) / 2;
        PlayAgain.y = 300;
        GameImage BackToMenu = new GameImage("Images/GUI/GameOver/Back To Menu.png");
        BackToMenu.x = (window.getWidth() - BackToMenu.width) / 2;
        BackToMenu.y = PlayAgain.y + 80;
        
        righArrow = new  GameImage("Images/GUI/RighArrow.png");
        righArrow.height = 33;
        righArrow.width = 29;
        leftArrow = new  GameImage("Images/GUI/LeftArrow.png");
        leftArrow.height = 33;
        leftArrow.width = 29;     
        
        isPlaying = true;
        
        char[] keyEvent = new char[7];
        keyEvent[0] = 'Q';
        keyEvent[1] = 'R';
        keyEvent[2] = 'U';
        keyEvent[3] = 'P';
        keyEvent[4] = 'Z';
        keyEvent[5] = 'V';
        keyEvent[6] = 'M';
        
        numberOfBirds = 0;
        for (boolean indexBoolean : birdsEnable)
            if (indexBoolean)
                numberOfBirds++;
        
        bird = new Bird(numberOfBirds, keyEvent, SPRITES_FILE);
        bird.start();
        
        level = new Level();
        level.SetFloorPosition(0, (window.getHeight() - level.GetFloor().height));
        level.start();
        
        //Wait all thread finish the images load
        while (true) {
            //If don`t have this 'sleep', the 'while' don`t work and don`t exit when the codintion
            // is true
            try {
                sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(Playing.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            
            if (levelOk && birdOk) {
                break;
            }
        }
        
        int index = 0;
        while (isPlaying) {
            sky.draw();
            
           level.DrawPipes();
            
            bird.DrawSprite();
            if (!pause)
                bird.UpdateSprite();
            
            level.DrawFloor();
            
            if (!bird.GetGameOver()) {
                if (!pause) {
                    physics.update();
                }
                //It is for clean the buffer, because if do not have this if the player press enter
                //befor the game over, whem the player lost the game, it is going to consider the
                //key as already pressed
                if (!pause)
                    if (keyboard.keyDown(Keyboard.UP_KEY) || keyboard.keyDown(Keyboard.DOWN_KEY) || keyboard.keyDown(Keyboard.ENTER_KEY)) {}
            } else {
                if (isBackToMenu) {
                    isPlaying = false;
                } else if (keyboard.keyDown(Keyboard.UP_KEY)) {
                    select = new Sound(SELECT_FILE);
                    select.play();
                    
                    index --;
                    if (index < 0) {
                        index = 1;
                    }
                } else if (keyboard.keyDown(KeyEvent.VK_CONTROL)) {
                    //Make nothing
                } else if (keyboard.keyDown(Keyboard.DOWN_KEY)) {
                    select = new Sound(SELECT_FILE);
                    select.play();
                
                    index ++;
                    if (index > 1) {
                        index = 0;
                    }
                } else if (keyboard.keyDown(Keyboard.ENTER_KEY)) {
                    if (index == 0) {
                        level.StartPipes();
                        bird.StartBirds();
                        ResetBirdsPassed();
                        bird.newRecord = false;
                        bird.gameOver = false;
                    }else if (index == 1) {
                        select = new Sound(SELECT_FILE);
                        select.play();
                        
                        isPlaying = false;
                    }
                }
                
                if (index == 0) {
                    leftArrow.y = PlayAgain.y - 2;
                    leftArrow.x = PlayAgain.x - leftArrow.width - 7;
                    righArrow.y = leftArrow.y - 2;
                    righArrow.x = PlayAgain.x + PlayAgain.width + 7;
                } else {
                    leftArrow.y = BackToMenu.y - 2;
                    leftArrow.x = BackToMenu.x - leftArrow.width - 7;
                    righArrow.y = BackToMenu.y - 2;
                    righArrow.x = BackToMenu.x + BackToMenu.width + 7;
                }
                
                if (bird.GetNewRecord()) {
                    newScoreCongratulations.draw();
                }
                
                //It is for the game stay more beautiful
                if (!isBackToMenu) {
                    PlayAgain.draw();
                    BackToMenu.draw();

                    leftArrow.draw();
                    righArrow.draw();
                }
            }
            
            bird.DrawScores();
            
            if (pause) {
                GamePaused();
            }
            
            window.update();
            
            if (keyboard.keyDown(Keyboard.ESCAPE_KEY)) {
                select = new Sound(SELECT_FILE);
                select.play();
                
                if (!bird.gameOver) {
                    pause = !pause;
                    pauseIndex = 0;
                } else {
                    select = new Sound(SELECT_FILE);
                    select.play();

                    isPlaying = false;
                }
            }
            
            try {
                sleep(allThreadSleep);
            } catch (InterruptedException ex) {
                Logger.getLogger(Playing.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
        }
        
    }
    
    private void GamePaused() {
        //Move the arrows on menu
        if (keyboard.keyDown(Keyboard.UP_KEY)) {
            select = new Sound(SELECT_FILE);
            select.play();
            
            pauseIndex --;
            
            if (pauseIndex < 0)
                pauseIndex = 1;
        } else if (keyboard.keyDown(Keyboard.DOWN_KEY)) {
            select = new Sound(SELECT_FILE);
            select.play();
            
            pauseIndex ++;
            
            if (pauseIndex > 1)
                pauseIndex = 0;
        } else if (keyboard.keyDown(Keyboard.ENTER_KEY)) {
            select = new Sound(SELECT_FILE);
            select.play();
            
            if (pauseIndex ==  0) {
                pause = false;
            } else if (pauseIndex == 1) {
                isBackToMenu = true;
                pause = false;
            }
        }
        
        if (pauseIndex == 0) {
            leftArrow.y = backToGame.y - 2;
            leftArrow.x = backToGame.x - leftArrow.width - 7;
            righArrow.y = leftArrow.y - 2;
            righArrow.x = backToGame.x + backToGame.width + 7;
        } else if (pauseIndex == 1) {
            leftArrow.y = backToMenu.y - 2;
            leftArrow.x = backToMenu.x - leftArrow.width - 7;
            righArrow.y = leftArrow.y - 2;
            righArrow.x = backToMenu.x + backToMenu.width + 7;
        }
        
        pauseImage.draw();
        
        leftArrow.draw();
        righArrow.draw();
        
        if (bird.FindNewRecord())
            newScore.draw();
        
        backToGame.draw();
        backToMenu.draw();
    }
    
    public void ResetBirdsPassed () {
        bird.ResetBirdsPassed();
    }
    
    public Bird GetBird () {
        return bird;
    }
    
    public Level getLevel () {
        return level;
    }
    
    public boolean GetPause() {
        return pause;
    }
    
    public boolean GetIsBackToMenu() {
        return isBackToMenu;
    }
}
