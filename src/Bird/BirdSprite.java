/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bird;

import static Main.Main.keyboard;
import static Main.Main.birdsEnable;
import static Main.Main.cheatEnable;
import static Main.Main.isElimination;
import static Main.Main.levelDistancePipesX;
import static Main.Main.levelDistancePipesY;
import static Main.Main.levelSpeed;
import static Main.Main.xml;

import jplay.GameImage;
import jplay.Sprite;
import jplay.Animation;

/**
 *
 * @author Rodrigo Fernando da Silva
 */
public class BirdSprite extends Thread {
    
    public boolean[] isAlive;
    public boolean[] isImmune;
    public boolean[] birdPassed;
    public boolean[] birdsIsOut;
    public boolean gameOver;
    public boolean newRecord;
    
    double[] angle;
    
    public int[] score;
    public int numberOfBirds;
    
    Sprite[] sprite;
    Sprite[] soul;
    GameImage[] boxCollider;
    Animation[] immunity;
    Animation lettes = new Animation("Images/GUI/Letters.png", 20);
    Animation birdSpriteSheet = new Animation("Images/GUI/BirdSpriteSheet.png", 8);
    
    public void InitializeVectors () {
        sprite = new Sprite[birdsEnable.length];
        soul = new Sprite[birdsEnable.length];
        boxCollider = new GameImage[birdsEnable.length];
        immunity = new Animation[birdsEnable.length];
        score = new int[birdsEnable.length];
        birdPassed = new boolean[birdsEnable.length];
        birdsIsOut = new boolean[birdsEnable.length];
        angle = new double[birdsEnable.length];
        newRecord = false;
    }
    
    public void ResetBirdsPassed () {
        for (int line3 = 0; line3 < birdsEnable.length; line3 ++) {
            birdPassed[line3] = false;
        }
    }
    
    public boolean GetNewRecord () {
        return newRecord;
    }
    
    public boolean FindNewRecord() {
        String folther;
        String way;
        
        int higherScore;
        
        if (cheatEnable)
                return false;
        
        if (isElimination) {
            folther = "Setting/ScoreElimination.xml";
        } else {
            folther = "Setting/ScorePunctuation.xml";
        }
        way = "/score/DistanceX_" + String.valueOf(levelDistancePipesX) + "/DistanceY_" + String.valueOf(levelDistancePipesY) +
               "/Speed_" + String.valueOf(levelSpeed);
        higherScore = xml.GetXmlScore(folther, way);
        
        for (int line4 = 0; line4 < birdsEnable.length; line4 ++) {
            if (score[line4] > higherScore) {
                return true;
            }
        }
        
        return false;
    }
    
    //################ SPRITE ################//
    
    public void DrawSprite () {
        for (int line = 0; line < birdsEnable.length; line ++) {
            if (birdsEnable[line]) {
                //boxCollider[line].draw();

               /* After the bird hit the floor, the image is reload, but it make the sprite position
                * be reseted, so before i print the sprites have to see if the Sprite is under the
                * soul, but have to put a margin of error
                */
                if ((sprite[line].y) > (soul[line].y - 200)) {
                    sprite[line].draw();
                    if (isImmune[line])
                        immunity[line].draw();
                }
                //soul[line].draw();
            }
        }
    }
    
    public void UpdateSprite () {
        for (int line0 = 0; line0 < birdsEnable.length; line0 ++) {
            if (birdsEnable[line0] && !birdsIsOut[line0]) {
                sprite[line0].update();
                if (isImmune[line0])
                    immunity[line0].update();
            }
        }
    }
    
    public void DrawScores () {
        double birdXdistance = 10.4;
        double letteXinitial;
        String scoreString;
        for (int line2 = 0; line2 < birdsEnable.length; line2 ++) {
            if (birdsEnable[line2]) {
                scoreString = String.valueOf(score[line2]);
                
                birdSpriteSheet.setCurrFrame(line2);
                birdSpriteSheet.x = birdXdistance;
                birdSpriteSheet.y = 10;
                birdSpriteSheet.draw();
                
                letteXinitial = birdSpriteSheet.x + (birdSpriteSheet.width / 2);
                letteXinitial -= ((scoreString.length() + 1) * 16) / 2;
                letteXinitial += 5;
                for (int i = 0; i < scoreString.length(); i ++) {
                    if (scoreString.charAt(i) == '-') {
                        lettes.setCurrFrame(18);
                        letteXinitial -= 3.2;
                    } else {
                        lettes.setCurrFrame((int) (scoreString.charAt(i) - 48) + 7);
                    }
                    lettes.x = letteXinitial;
                    lettes.y = birdSpriteSheet.y + birdSpriteSheet.height;
                    lettes.draw();
                    letteXinitial += 16;
                }
                
                birdXdistance += 57;
            }
        }
    }
    
    public boolean getBirdPassed (int index) {
        return birdPassed[index];
    }
    
    public boolean KeyDown (int keyEvent) {
        return keyboard.keyDown(keyEvent);
    }
    
    public boolean GetGameOver () {
        return gameOver;
    }
    
}
