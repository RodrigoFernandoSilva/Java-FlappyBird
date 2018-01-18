/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import static Main.Main.SELECT_FILE;
import static Main.Main.window;
import static Main.Main.sky;
import static Main.Main.birdsEnable;
import static Main.Main.isElimination;
import static Main.Main.keyboard;
import static Main.Main.select;
import static Main.Main.wasBeforePlay;
import com.sun.glass.events.KeyEvent;

import jplay.Animation;
import jplay.GameImage;
import jplay.Sound;

/**
 *
 * @author Rodrigo Fernando da Silva
 */
public class BeforePlay {
    
    public Animation[] birds = new Animation[birdsEnable.length];
    public Animation letter = new Animation("Images/GUI/Letters.png", 20);
    public Animation lettersTransparent  = new Animation("Images/GUI/LettersTransparent.png", 20);
    
    @SuppressWarnings("static-access")
    public boolean BeforePlay () {
        
        int line;
        
        GameImage floor = new GameImage("Images/Scene/Floor.png");
        floor.y = window.getHeight() - floor. height;
        
        for (line = 0; line <  birds.length; line ++) {
            loadBirdImage(line);
        }
        
        GameImage enable = new GameImage("Images/GUI/BeforePlay/Enable.png");
        GameImage disable = new GameImage("Images/GUI/BeforePlay/Disable.png");
        enable.x = birds[0].x + birds[0].width + 10;
        disable.x = enable.x;
        
        GameImage righArrow = new  GameImage("Images/GUI/RighArrow.png");
        GameImage leftArrow = new  GameImage("Images/GUI/LeftArrow.png");
        
        GameImage elimination = new GameImage("Images/GUI/BeforePlay/Elimination Mode.png");
        GameImage punctuation = new GameImage("Images/GUI/BeforePlay/Punctuation Mode.png");
        elimination.x = 83;
        elimination.y = floor.y + 30;
        punctuation.x = elimination.x - 5;
        punctuation.y = elimination.y;
        
        int index = 0;
        
        while (true) {
            sky.draw();
            floor.draw();
            
            //Draw the menu on the screen
            for (line = 0; line < birdsEnable.length; line ++) {
                //Draw the birds and put the messagen (enable/disable)
                birds[line].draw();
                birds[line].update();
                if (birdsEnable[line]) {
                    enable.y = birds[line].y;
                    enable.y += (birds[line].height - disable.height) / 2;
                    enable.draw();
                } else {
                    disable.y = birds[line].y;
                    disable.y += (birds[line].height - disable.height) / 2;
                    disable.draw();
                }
                
                //Draw the letter, if the bird is not enable print the transparente letter
                if (birdsEnable[line]) {
                    letter.setCurrFrame(line);
                    letter.x = birds[line].x - 50;
                    letter.y = birds[line].y + (birds[line].height / 2);
                    letter.y -= letter.height / 2;
                    letter.draw();
                    letter.setCurrFrame(17);
                    letter.x += letter.width - 7;
                    letter.draw();
                } else {
                    lettersTransparent.setCurrFrame(line);
                    lettersTransparent.x = birds[line].x - 50;
                    lettersTransparent.y = birds[line].y + (birds[line].height / 2);
                    lettersTransparent.y -= lettersTransparent.height / 2;
                    lettersTransparent.draw();
                    lettersTransparent.setCurrFrame(17);
                    lettersTransparent.x += lettersTransparent.width - 7;
                    lettersTransparent.draw();
                }
            }
            
            if (isElimination) {
                elimination.draw();
            } else {
                punctuation.draw();
            }
            
            righArrow.height = 42;
            righArrow.width = 32;
            righArrow.x = disable.x + disable.width + 10;
            if (index < 7) {
                righArrow.y = birds[index].y;
            } else {
                righArrow.y = elimination.y - (elimination.height / 2);
            }
            leftArrow.height = righArrow.height;
            leftArrow.width = righArrow.width;
            leftArrow.x = letter.x - letter.width - leftArrow.width;
            leftArrow.y = righArrow.y;
            
            righArrow.draw();
            leftArrow.draw();
            
            //Move the arrow by the menu
            if (keyboard.keyDown(keyboard.UP_KEY)) {
                select = new Sound(SELECT_FILE);
                select.play();
                
                index --;
                if (index < 0) {
                    index = 7;
                }
            } else if (keyboard.keyDown(keyboard.DOWN_KEY)) {
                select = new Sound(SELECT_FILE);
                select.play();
                
                index ++;
                if (index > 7) {
                    index = 0;
                }
            } else if (keyboard.keyDown(KeyEvent.VK_CONTROL)) {
                //Make nothing
            } else if (keyboard.keyDown(keyboard.LEFT_KEY)) {
                select = new Sound(SELECT_FILE);
                select.play();
                
                if (index < 7) {
                    birdsEnable[index] = !birdsEnable[index];
                    loadBirdImage(index);
                } else {
                    isElimination = !isElimination;
                }
            } else if (keyboard.keyDown(keyboard.RIGHT_KEY)) {
                select = new Sound(SELECT_FILE);
                select.play();
                
                if (index < 7) {
                    birdsEnable[index] = !birdsEnable[index];
                    loadBirdImage(index);
                } else {
                    isElimination = !isElimination;
                }
            } else if (keyboard.keyDown(keyboard.ENTER_KEY)) {
                return true;
            } else if (keyboard.keyDown(keyboard.ESCAPE_KEY)) {
                select = new Sound(SELECT_FILE);
                select.play();
                
                wasBeforePlay = false;
                return false;
            }
            
            window.update();
        }
        
    }
    
    private void loadBirdImage(int line) {
        if (birdsEnable[line]) {
            birds[line] = new Animation("Images/Bird/Bird" + String.valueOf(line) + ".png", 7);
        } else {
            birds[line] = new Animation("Images/Bird/Bird" + String.valueOf(line) + "Opacity.png", 7);
        }
        birds[line].setSequenceTime(0, 4, true, 500);
        birds[line].x = 130;
        birds[line].y = 75.5 * line;
        birds[line].y += 10;
    }
}
