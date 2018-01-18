/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bird;

import static Main.DeltaTime.allThreadSleep;
import static Main.DeltaTime.deltaTime;
import static Main.Main.birdOk;
import static Main.Main.window;
import static Main.Main.physics;
import static Main.Main.keyboard;
import static Main.Main.playing;
import static Main.Main.birdsEnable;
import static Main.Main.levelOk;
import static Main.Main.isElimination;
import static Main.Main.levelDistancePipesX;
import static Main.Main.levelDistancePipesY;
import static Main.Main.levelSpeed;
import static Main.Main.xml;
import static Main.Main.cheatEnable;

import jplay.Sprite;
import jplay.Sound;
import jplay.GameImage;
import jplay.Animation;

import static jplay.InputBase.DETECT_INITIAL_PRESS_ONLY;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rodrigo Fernando da Silva
 */
public class Bird extends BirdSprite {
    
    public final int TIME_IMMUNE = 100;
    public int[] timeImmuneCounter;
    
    public int[] keyEvent;
    public int line;
    
    public String[] spriteFile;
    
    public Bird (int numberOfBirds, char[] keyEvent, String spriteFile) {
        this.numberOfBirds = numberOfBirds;
        this.spriteFile = new String[birdsEnable.length];
        isAlive = new boolean[birdsEnable.length];
        isImmune = new boolean[birdsEnable.length];
        timeImmuneCounter = new int[birdsEnable.length];
        this.keyEvent = new int[birdsEnable.length];
        
        //Load the correct image
        for (line = 0; line < birdsEnable.length; line ++) {
            if (birdsEnable[line]) {
                this.keyEvent[line] = (int) keyEvent[line];
                this.spriteFile[line] = spriteFile;

                if (numberOfBirds != 1) {
                    this.spriteFile[line] += (String.valueOf(line) + "Opacity.png");
                } else {
                    this.spriteFile[line] += (String.valueOf(line) + ".png");
                }
            }
        }
    }
    
    //This variables is public because have a function that use this variables, and thay are
    //used in the 'public void run ()' too
    public final int animationFlyingSpeed = 400;
    public final int animationDyingSpeed = (int) ((animationFlyingSpeed * 2) / 5);
    public final int JUMP_FORCE = 800;
    public int timeToDown = 0;
    
    public double birdPositionX = 0; //it save the middle of the soul sprite
    
    public String jumpFile = "Sounds/Jump.wav";
    
    public Sound jump = new Sound(jumpFile);
    
    String hitFile = "Sounds/Hit.wav";
    public Sound hit = new Sound(hitFile);
    
    public int higherScore;
    public String folther;
    public String way;
    
    @Override
    public void run () {
        
        InitializeVectors();
        
        gameOver = false;

        if (isElimination) {
            folther = "Setting/ScoreElimination.xml";
        } else {
            folther = "Setting/ScorePunctuation.xml";
        }
        way = "/score/DistanceX_" + String.valueOf(levelDistancePipesX) + "/DistanceY_" + String.valueOf(levelDistancePipesY) +
               "/Speed_" + String.valueOf(levelSpeed);
        higherScore = xml.GetXmlScore(folther, way);
        
        for (line = 0; line < birdsEnable.length; line ++)
            keyboard.addKey(keyEvent[line], DETECT_INITIAL_PRESS_ONLY);
        
        final int TIME_TO_DOWN = 10;
        
        final double MAX_ANGLE_UP = -35;
        final double MAX_ANGLE_DOWN = 90;
        
        double angleForceUp = 4;
        double angleForceDown = 2;
        
        String coinFile = "Sounds/Coin.wav";
        
        Sound coin;
        
        StartBirds();
        
        //Wait the level thread load to avoid problems
        while (true) {
            if (levelOk) {
                break;
            }
            
            //if do not have this sleep, the while true in same cases never gonna exit
            try {
                sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(Bird.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        birdOk = true;
        while (playing.isPlaying) {
            while (playing.GetPause()) {
                for (line = 0; line < birdsEnable.length; line ++){
                    if (birdsEnable[line]) {
                        if (KeyDown(keyEvent[line])) {
                            //It is for clean the keyboard buffer, because if the player press the
                            //jump key them the game is paused, aftter the game came back the
                            //bird is going to jump
                        }
                    }
                }
                
                try {
                    sleep(1);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Bird.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            for (line = 0; line < birdsEnable.length; line ++) {
                if (birdsEnable[line] && !birdsIsOut[line] && !gameOver) {
                    
                    //I could make it just one time, when this script is called, but the jplay rotation
                    //is not so right, and all the time the sprite move somes pixels after it rotate
                    soul[line].setX((line + 1) * (soul[line].width) + 1);

                    //spins the sprite for the direction that it is moving
                    if (soul[line].getForceY() > 2 && angle[line] > MAX_ANGLE_UP) {
                        angle[line] -= angleForceUp;
                    } else if (soul[line].getForceY() < 2 && angle[line] < MAX_ANGLE_DOWN) {
                        angle[line] += angleForceDown;
                    }
                    
                    sprite[line].setRotation((angle[line] * 1.575) / 90);

                    //Makes the bird jump
                    if (KeyDown(keyEvent[line])) {
                        //The 'ifs' is not together because if they stay together if the bird hit
                        //with the pipe and the player press the key for jump, after the birr hit
                        //the floor, the bird going to jump, because the key pressed stay in the buffer
                        if (isAlive[line] && soul[line].y > -10 && !playing.GetIsBackToMenu()) {
                            soul[line].cancelForces();
                            soul[line].applyForceY(JUMP_FORCE);
                            timeToDown = 0;
                            jump = new Sound(jumpFile);
                            jump.play();
                            sprite[line] = new Sprite(spriteFile[line], 7);
                            sprite[line].setSequenceTime(0, 4, false, animationFlyingSpeed);
                        }
                    } else {
                        //if the bird is falling, after some time it going to fall faster
                        if (timeToDown > TIME_TO_DOWN) {
                            soul[line].applyForceY(-30);
                        } else {
                            timeToDown ++;
                        }
                    }

                    //Collision with the pipes
                    if (!isImmune[line] && isAlive[line] && playing.getLevel().CollisionWithPipes(boxCollider[line])) {
                        isAlive[line] = false;
                        HitAction();
                        if (!isElimination && !playing.isBackToMenu)
                            score[line] -= 2;
                    }
                    
                    
                   /*
                    * The codigo below is after make the bird jump and others ones because after
                    * reload the sprite image, there position is reseted
                    */

                    //Put the boxCollider over the soul
                    boxCollider[line].x = birdPositionX + 6;
                    boxCollider[line].y = soul[line].y + (soul[line].height / 2);
                    boxCollider[line].y -= boxCollider[line].height / 2;
                    //Put the sprite over the soul
                    sprite[line].x = birdPositionX;
                    sprite[line].y = soul[line].y + (soul[line].height / 2);
                    sprite[line].y -= sprite[line].height / 2;
                    //Put the immunity animation over the soul
                    immunity[line].x = birdPositionX;
                    immunity[line].y = sprite[line].y + (sprite[line].height / 2);
                    immunity[line].y -= (immunity[line].height / 2) + 2;
                    
                    //Colision with the floor
                    if (soul[line].getForceY() < 0 && playing.getLevel().CollisionWithFloor(boxCollider[line])) {
                        if (isElimination | playing.isBackToMenu) {
                            EliminationMode();
                        } else {
                            PunctuationMode();
                        }
                    }
                    
                    //Time for the immunity over
                    if (isImmune[line]) {
                        if (timeImmuneCounter[line] > TIME_IMMUNE) {
                            isImmune[line] = false;
                        } else {
                            timeImmuneCounter[line] ++;
                        }
                    }
                    
                    //Win a coin
                    if (!playing.GetIsBackToMenu() && isAlive[line] && !isImmune[line] && !birdPassed[line] && playing.getLevel().GameImagePassed(boxCollider[line]) && !playing.getLevel().CollisionWithPipes(boxCollider[line])) {
                        coin = new Sound(coinFile);
                        coin.play();
                        score[line] ++;
                        birdPassed[line] = true;
                    }
                } else if (birdsEnable[line]) {
                    soul[line].cancelForces();
                    if (!gameOver && sprite[line].x > -sprite[line].width) {
                        sprite[line].x -= levelSpeed * deltaTime;
                    }
                }
            }
            
            try {
                sleep(allThreadSleep);
            } catch (InterruptedException ex) {
                Logger.getLogger(Bird.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        for (line = 0; line < birdsEnable.length; line ++) {
            if (birdsEnable[line]) {
                soul[line].cancelForces();
            }
        }
        
    }
    
    public void StartBirds () {
        for (int line0 = 0; line0 < birdsEnable.length; line0 ++) {
            if (birdsEnable[line0]) {
                angle[line0] = 0;
                isAlive[line0] = true;
                isImmune[line0] = false;
                birdsIsOut[line0] = false;
                timeImmuneCounter[line0] = 0;
                score[line0] = 0;

                //Load the immunity animation, if have more tham one player, have to load de 
                //animation with mor opacity
                if (numberOfBirds == 1) {
                    immunity[line0] = new Animation("Images/Bird/Immunity.png", 3);
                } else {
                    immunity[line0] = new Animation("Images/Bird/ImmunityOpacity.png", 3);
                }
                immunity[line0].setSequenceTime(0, 2, true, 500);

                sprite[line0] = new Sprite(spriteFile[line0], 7);
                sprite[line0].setSequenceTime(0, 4, false, animationFlyingSpeed);

                sprite[line0].setX(5);
                sprite[line0].setY(window.getHeight() / 2);
                sprite[line0].setY(sprite[line0].getY() - (sprite[line0].height / 2));

                boxCollider[line0] = new GameImage("Images/Bird/BoxCollider.jpg");
                boxCollider[line0].width = sprite[line0].width - 10;
                boxCollider[line0].height = sprite[line0].height - 4;

                soul[line0] = new Sprite("Images/Bird/Soul.jpg");
                soul[line0].setMass(5);
                soul[line0].setX(20);
                soul[line0].setY(window.getHeight() / 2);
                soul[line0].setY(soul[line0].getY() - (soul[line0].height / 2));
                physics.createBodyFromSprite(soul[line0], false);

                //Makes the bird jump before start the game for the game become more beatifull
                soul[line0].cancelForces();
                soul[line0].applyForceY(JUMP_FORCE);
                timeToDown = 0;
                jump = new Sound(jumpFile);
                jump.play();
            }
        }
        
        if (birdsEnable[0]) {
            birdPositionX = soul[0].x + (soul[0].width / 2);
            birdPositionX -= sprite[0].width / 2;
        }
    }
    
    private void HitAction () {
        hit = new Sound(hitFile);
        hit.play();
        if (sprite[line].getCurrFrame() < 4) {
            sprite[line] = new Sprite(spriteFile[line], 7);
            sprite[line].setSequenceTime(4, 6, true, animationDyingSpeed);
        }
    }
    
    private void EliminationMode () {
        if (!birdsIsOut[line]) {
            HitAction();

            //After the the method above is called, the sprite position is reseted, so is necessary
            //reposition it because after the bird collide with the floor, the elimination mode over
            //and the method that puts the sprite over the soul not going to be called
            sprite[line].setRotation((angle[line] * 1.575) / 90);
            sprite[line].y = playing.getLevel().GetFloor().y - (sprite[line].height / 1.5);
            int i;

            isAlive[line] = false;
            birdsIsOut[line] = true;
            for (i = 0; i < isAlive.length; i ++) {
                if (isAlive[i]) {
                    break;
                }
            }

            if (i == isAlive.length) {
                if (!cheatEnable) {

                    for (int index: score) {
                        if (index > higherScore) {
                            higherScore = index;
                            newRecord = true;
                        }
                    }
                    
                    if (newRecord) {
                        xml.WriteInXml(folther, way, higherScore);
                    }
                }
                gameOver = true;
            }
        }
    }
    
    
    private void PunctuationMode () {
        if (isAlive[line]) {
            HitAction();
        }
        soul[line].cancelForces();
        soul[line].applyForceY(400);
        isAlive[line] = true;
        isImmune[line] = true;
        timeImmuneCounter[line] = 0;
    }
}
