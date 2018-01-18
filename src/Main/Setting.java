/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import static Main.Main.SELECT_FILE;
import static Main.Main.keyboard;
import static Main.Main.window;
import static Main.Main.sky;
import static Main.Main.select;
import static Main.Main.levelSpeed;
import static Main.Main.levelDistancePipesX;
import static Main.Main.levelDistancePipesY;
import static Main.Main.xml;
import static Main.Main.cheatEnable;
import static Main.DeltaTime.allThreadSleep;
import static Main.DeltaTime.deltaTime;

import jplay.GameImage;
import jplay.Keyboard;
import jplay.Sound;
import jplay.Animation;

import com.sun.glass.events.KeyEvent;
import static java.lang.Thread.sleep;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rodrigo Fernando da Silva
 */
public class Setting {
    
    Animation lettes = new Animation("Images/GUI/Setting/WhiteLetters.png", 12);
    
    GameImage righArrow;
    GameImage leftArrow;
    
    @SuppressWarnings("SleepWhileInLoop")
    public void Setting () {
        
        int i;
        
        GameImage[] upPipe = new GameImage[2];
        GameImage[] downPipe = new GameImage[2];
        
        GameImage bird = new GameImage("Images/Logo/FlappyBird.png");
        bird.width *= 0.1;
        bird.height *= 0.1;
        bird.x = -bird.width;
        
        GameImage floor = new GameImage("Images/Scene/LongFloor.png");
        floor.y = window.getHeight() - floor.height;
        
        //Those are the information that are on the scene
        GameImage pipes = new GameImage("Images/GUI/Setting/Pipes.png");
        pipes.x = 50;
        pipes.y = floor.y;
        
        GameImage distanceInX = new GameImage("Images/GUI/Setting/DistanceInX.png");
        distanceInX.x = 15;
        distanceInX.y = pipes.y + 18;
        
        GameImage distanceInY = new GameImage("Images/GUI/Setting/DistanceInY.png");
        distanceInY.x = distanceInX.x;
        distanceInY.y = distanceInX.y + 49;
        
        GameImage speed = new GameImage("Images/GUI/Setting/Speed.png");
        speed.x = distanceInY.x + (distanceInY.width / 2) - 3;
        speed.x -= speed.width / 2;
        speed.y = distanceInY.y + (distanceInY.y - distanceInX.y);
        
        GameImage score = new GameImage("Images/GUI/Setting/Score.png");
        score.x = 250;
        score.y = pipes.y;
        int valueScore;
        
        GameImage eliminationMode = new GameImage("Images/GUI/BeforePlay/Elimination Mode.png");
        GameImage punctuationMode = new GameImage("Images/GUI/BeforePlay/Punctuation Mode.png");
        eliminationMode.width -= eliminationMode.width * 0.15;
        eliminationMode.x = SpriteMiddle(eliminationMode, score);
        eliminationMode.y = score.y + score.height;
        punctuationMode.width -= punctuationMode.width * 0.15;
        punctuationMode.x = eliminationMode.x - 7;
        punctuationMode.y = eliminationMode.y;
        
        GameImage save = new GameImage("Images/GUI/Setting/Save.png");
        save.x = SpriteMiddle(save, score) - 20;
        save.y = eliminationMode.y + (distanceInY.y - distanceInX.y) + 14;
        
        GameImage cancel = new GameImage("Images/GUI/Setting/Cancel.png");
        GameImage back = new GameImage("Images/GUI/Setting/Back.png");
        cancel.x = SpriteMiddle(cancel, score) - 20;
        cancel.y = save.y + cancel.height + 3;
        back.x = SpriteMiddle(back, score) - 20;
        back.y = save.y + back.height + 3;
        
        GameImage restoreDefaults = new GameImage("Images/GUI/Setting/Restore Defaults.png");
        restoreDefaults.x = SpriteMiddle(restoreDefaults, score) - 20;
        restoreDefaults.y = cancel.y + restoreDefaults.height + 3;
        
        //Load the pipes that is going to be used how reference for the user
        for (i = 0; i < upPipe.length; i ++) {
            downPipe[i] = new GameImage("Images/Obstacle/DownPipe.png");
            upPipe[i] = new GameImage("Images/Obstacle/UpPipe.png");
        }
        
        //Load the arrows and resize they
        righArrow = new  GameImage("Images/GUI/RighArrow.png");
        righArrow.height = 23;
        righArrow.width = 19;
        leftArrow = new  GameImage("Images/GUI/LeftArrow.png");
        leftArrow.height = righArrow.height;
        leftArrow.width = righArrow.width;
        
        int tempLevelDistancePipesX = levelDistancePipesX;
        int tempLevelDistancePipesY = levelDistancePipesY;
        int tempLevelSpeed = levelSpeed;
        
        boolean isElimination = true;
        
        GameImage resetScore = new GameImage("Images/GUI/Setting/Reset Score.png");
        resetScore.x = (window.getWidth() / 2) - (resetScore.width / 2);
        resetScore.y = window.getHeight() * 0.3;
        boolean isResetScore = false;
        
        String folther = null;
        String line = null;
        
        boolean isSetting = true;
        int index = 0;
        while (isSetting) {
            
            if ((bird.x - bird.width) > window.getWidth()) {
                bird.x = -bird.width;
            }
            bird.x += tempLevelSpeed * deltaTime;
            bird.y = upPipe[0].y + upPipe[0].height;
            bird.y += (downPipe[0].y - bird.y) / 2;
            bird.y -= bird.height / 2;
            
            if (cheatEnable) {
                valueScore = 0;
            } else {
                if (isElimination) {
                    folther = "Setting/ScoreElimination.xml";
                } else {
                    folther = "Setting/ScorePunctuation.xml";
                }
                line = "/score/DistanceX_" + String.valueOf(levelDistancePipesX) + "/DistanceY_" + String.valueOf(levelDistancePipesY) +
                       "/Speed_" + String.valueOf(levelSpeed);
                valueScore = xml.GetXmlScore(folther, line);
            }
            
            //Put the tubes showing how they are going to stay in the game
            upPipe[0].x = 3.9;
            upPipe[0].y = -upPipe[0].height + 55;
            downPipe[0].x = upPipe[0].x;
            downPipe[0].y = upPipe[0].y + tempLevelDistancePipesY + downPipe[0].height;
            
            upPipe[1].x = upPipe[0].x + tempLevelDistancePipesX;
            upPipe[1].y = upPipe[0].y;
            downPipe[1].x = upPipe[1].x;
            downPipe[1].y = downPipe[0].y;
            
            sky.draw();
            
            for (i = 0; i < upPipe.length; i ++) {
                downPipe[i].draw();
                upPipe[i].draw();
            }
            
            floor.draw();
            
            distanceInX.draw();
            DrawLetter(distanceInX, tempLevelDistancePipesX, 0);
            distanceInY.draw();
            DrawLetter(distanceInY, tempLevelDistancePipesY, 0);
            speed.draw();
            DrawLetter(speed, tempLevelSpeed, 0);
            score.draw();
            
            if (isElimination){
                DrawLetter(score, valueScore, eliminationMode.height);
                eliminationMode.draw();
            }
            else{
                DrawLetter(score, valueScore, eliminationMode.height);
                punctuationMode.draw();
            }
            
            save.draw();
            if (tempLevelDistancePipesX != levelDistancePipesX | tempLevelDistancePipesY != levelDistancePipesY | tempLevelSpeed != levelSpeed)
                cancel.draw();
            else
                back.draw();
            restoreDefaults.draw();
            
            pipes.draw();
            
            //Put the arrows over the index
            switch (index) {
                case 0:
                    PutArrowOverTheSprite(distanceInX, tempLevelDistancePipesX);
                    break;
                case 1:
                    PutArrowOverTheSprite(distanceInY, tempLevelDistancePipesY);
                    break;
                case 2:
                    PutArrowOverTheSprite(speed, tempLevelSpeed);
                    break;
                case 3:
                    PutArrowOverTheSprite(score);
                    break;
                case 4:
                    PutArrowOverTheSprite(save);
                    break;
                case 5:
                    PutArrowOverTheSprite(cancel);
                    break;
                case 6:
                    PutArrowOverTheSprite(restoreDefaults);
                    break;
            }
            
            righArrow.draw();
            leftArrow.draw();
            
            bird.draw();
            
            if (isResetScore) {
                resetScore.draw();
            }
            
            window.update();
            
            if (keyboard.keyDown(Keyboard.UP_KEY)) {
                select = new Sound(SELECT_FILE);
                select.play();
                
                if (!isResetScore) {
                    index --;
                    if (index < 0) {
                        index = 6;
                    }
                }
            } else if (keyboard.keyDown(KeyEvent.VK_CONTROL)) {
                //Make nothing
            } else if (keyboard.keyDown(Keyboard.DOWN_KEY)) {
                select = new Sound(SELECT_FILE);
                select.play();
                
                if (!isResetScore) {
                    index ++;
                    if (index > 6) {
                        index = 0;
                    }
                }
            } else if (keyboard.keyDown(Keyboard.LEFT_KEY)) {
                select = new Sound(SELECT_FILE);
                select.play();
                
                if (!isResetScore) {
                    switch (index) {
                        case 0: //Distance in X
                            if (tempLevelDistancePipesX > 210 || cheatEnable)
                                tempLevelDistancePipesX -= 30;
                            break;
                        case 1: //Distance in Y
                            if (tempLevelDistancePipesY > 130 || cheatEnable)
                                tempLevelDistancePipesY -= 10;
                            break;
                        case 2: //Speed
                            if (tempLevelSpeed > 160 || cheatEnable)
                                tempLevelSpeed -= 20;
                            break;
                        case 3: //Score
                            isElimination = !isElimination;
                            break;
                    }
                }
            } else if (keyboard.keyDown(Keyboard.RIGHT_KEY)) {
                select = new Sound(SELECT_FILE);
                select.play();

                if (!isResetScore) {
                    switch (index) {
                        case 0:  //Distance in X
                            if (tempLevelDistancePipesX < 330 || cheatEnable)
                                tempLevelDistancePipesX += 30;
                            break;
                        case 1: //Distance in Y
                            if (tempLevelDistancePipesY < 170 || cheatEnable)
                                tempLevelDistancePipesY += 10;
                            break;
                        case 2: //Speed
                            if (tempLevelSpeed < 240 || cheatEnable)
                                tempLevelSpeed += 20;
                            break;
                        case 3: //Score
                            isElimination = !isElimination;
                            break;
                    }
                }
            } else if (keyboard.keyDown(Keyboard.ENTER_KEY)) {
                select = new Sound(SELECT_FILE);
                select.play();
                
                switch (index) {
                    case 3: //Score
                        if (!cheatEnable) {
                            if (!isResetScore) {
                                isResetScore = true;
                            } else {
                                xml.WriteInXml(folther, line, 0);
                                isResetScore = false;
                            }
                         }
                        break;
                    case 4: //Save
                        xml.WtriteInXml(tempLevelDistancePipesX, tempLevelDistancePipesY, tempLevelSpeed);
                        Main.LoadSettingToGameVar();
                        break;
                    case 5: //Cancel//Back
                        isSetting = false;
                        break;
                    case 6: //Restore Default
                        xml.ResetXmlSetting();
                        Main.LoadSettingToGameVar();
                        tempLevelDistancePipesX = levelDistancePipesX;
                        tempLevelDistancePipesY = levelDistancePipesY;
                        tempLevelSpeed = levelSpeed;
                        break;
                }
            } else if (keyboard.keyDown(Keyboard.ESCAPE_KEY)) {
                select = new Sound(SELECT_FILE);
                select.play();
                
                if (!isResetScore) {
                    isSetting = false;
                } else {
                    isResetScore = false;
                }
            }
            
            try {
                sleep(allThreadSleep);
            } catch (InterruptedException ex) {
                Logger.getLogger(Setting.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private double SpriteMiddle (GameImage originalImage, GameImage referenceImage) {
        double value;
        
        value = referenceImage.x + (referenceImage.width / 2);
        value -= originalImage.width / 2;
        
        return value;
    }
    
    private void PutArrowOverTheSprite (GameImage image) {
        leftArrow.x = image.x - leftArrow.width;
        leftArrow.x -= 3;
        leftArrow.y = image.y - 1.5;
        
        righArrow.x = leftArrow.x + leftArrow.width + image.width + 3;
        righArrow.x += 3;
        righArrow.y = leftArrow.y;
    }
    
    private void PutArrowOverTheSprite (GameImage image, int value) {
        leftArrow.x = image.x + (image.width / 2) - (((String.valueOf(value).length() + 1) * 18) / 1.6);
        leftArrow.x -= 3;
        leftArrow.y = image.y + image.height - 1;
        
        righArrow.x = leftArrow.x + ((String.valueOf(value).length() + 1) * 18);
        righArrow.x += 3;
        righArrow.y = leftArrow.y;
    }
    
    private void DrawLetter(GameImage image, int scoreString, int SAT) {
        String scoreStringStr = String.valueOf(scoreString);
        double x;
        x = image.x + (image.width / 2);
        x -= ((scoreStringStr.length() + 1) * 18) / 2;
        x += 5;
        
        for (int i = 0; i < scoreStringStr.length(); i ++) {
            if (scoreStringStr.charAt(i) == '-') {
                lettes.setCurrFrame(10);
            } else {
                lettes.setCurrFrame((int) (scoreStringStr.charAt(i) - 48));
            }
            lettes.x = x;
            lettes.y = image.y + (lettes.height / 2) + SAT;
            lettes.draw();
            x += 16;
        }
    }
}
